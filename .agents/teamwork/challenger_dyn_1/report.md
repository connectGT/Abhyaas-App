# Empirical Challenge Report: Data Architecture Verification

**Agent:** `challenger_dyn_1` (Roles: `critic`, `specialist`)  
**Target:** MVVM + Retrofit Data Architecture (`com.example.abhyaas.data`, `com.example.abhyaas.ui.viewmodel`)  
**Timestamp:** 2026-09-30T15:35:00Z  

---

## 1. Executive Summary & Risk Assessment

**Overall Risk Assessment:** **HIGH**  
**Verdict:** **REQUEST_CHANGES**  

While production Kotlin compilation (`compileDebugKotlin`) and debug APK assembly (`assembleDebug`) succeed with code 0, empirical execution of unit tests via `.\gradlew testDebugUnitTest` **fails completely** due to 30+ unresolved references in legacy unit test files (`Milestone1DataIntegrityEmpiricalTest.kt` and `Milestone1UiChallengeTest.kt`). In addition, adversarial analysis reveals tight coupling in all ViewModels to `AbhyaasApplication.instance`, making unit testing in headless JVM environments crash with `UninitializedPropertyAccessException`.

| Verification Target | Expected Behavior | Observed Result | Status |
|---------------------|-------------------|-----------------|--------|
| `.\gradlew compileDebugKotlin` | Exits with code 0 | Exited with code 0 in 34s | **PASS** |
| `.\gradlew assembleDebug` | Exits with code 0, generates APK | Exited with code 0 in 3s | **PASS** |
| `.\gradlew testDebugUnitTest` | Unit tests compile and pass | `compileDebugUnitTestKotlin` failed (Exit 1) | **FAIL** |
| Repository Fallback Degradation | Degrades to mock data on network error | All 5 Remote repos return mock data | **PASS** |
| ViewModel StateFlow Non-Null Emission | ViewModels initialize non-null StateFlows | All 6 ViewModels expose non-null states | **PASS** |
| ViewModel Test Isolation | Can be instantiated in JVM unit tests | Crashes without Android Application context | **FAIL** |

---

## 2. Empirical Verification Findings

### Finding 1 (CRITICAL): `testDebugUnitTest` Compilation Failure
- **Command Executed:** `.\gradlew testDebugUnitTest` and `.\gradlew compileDebugUnitTestKotlin`
- **Output:**
  ```text
  e: file:///C:/Users/gurut/AndroidStudioProjects/Abhyaas/app/src/test/java/com/example/abhyaas/m1_empirical_challenge/Milestone1DataIntegrityEmpiricalTest.kt:369:41 Unresolved reference 'getQuestionById'.
  e: file:///C:/Users/gurut/AndroidStudioProjects/Abhyaas/app/src/test/java/com/example/abhyaas/tier2_boundary/Milestone1UiChallengeTest.kt:339:41 Unresolved reference 'getQuestionById'.
  > Task :app:compileDebugUnitTestKotlin FAILED
  BUILD FAILED in 4s
  ```
- **Root Cause Analysis:**
  During earlier refactoring, `app/src/main/java/com/example/abhyaas/data/mock/MockQuestionRepository.kt` was updated to provide Nayab Tehsildar and MPSEB questions (`getTehsildarQuestions()`, `getMpsebQuestions()`, `getQuestionsForTest()`). However, `getQuestionById(questionId: Int)` was removed from `MockQuestionRepository`. Existing unit tests from Milestone 1 (`Milestone1DataIntegrityEmpiricalTest.kt` and `Milestone1UiChallengeTest.kt`) still reference `MockQuestionRepository.getQuestionById(...)` and access deprecated properties.
- **Blast Radius:**
  Because Gradle compiles all unit tests together under `:app:compileDebugUnitTestKotlin`, **no unit tests in the entire project can compile or execute**. CI/CD pipelines relying on `./gradlew test` will fail immediately.
- **Mitigation:**
  Either:
  1. Reintroduce `fun getQuestionById(questionId: Int): Question? = getTehsildarQuestions().find { it.id == questionId } ?: getMpsebQuestions().find { it.id == questionId }` in `MockQuestionRepository.kt`, OR
  2. Update the legacy tests in `src/test/java` to reflect the updated repository APIs and models.

---

### Finding 2 (HIGH): ViewModel Hard-Coupling to `AbhyaasApplication.instance`
- **Observed Code:**
  Across `HomeViewModel`, `TestsViewModel`, `TestSeriesDetailViewModel`, `UpdatesViewModel`, `UserProfileViewModel`, `ActiveTestViewModel`:
  ```kotlin
  class HomeViewModel : ViewModel() {
      private val examRepo = AbhyaasApplication.instance.examRepository
      private val userRepo = AbhyaasApplication.instance.userRepository
      ...
  ```
- **Attack Scenario:**
  Any developer or unit test attempting to instantiate a ViewModel in a local JUnit test:
  ```kotlin
  val viewModel = HomeViewModel()
  ```
  results in an immediate crash:
  `kotlin.UninitializedPropertyAccessException: lateinit property instance has not been initialized`
  because `AbhyaasApplication.instance` is only set in `Application.onCreate()`, which does not run in standard JVM unit tests.
- **Blast Radius:**
  Zero ViewModel business logic can be unit-tested without launching Robolectric or a full Android instrumented test environment.
- **Mitigation:**
  Introduce default constructor parameters allowing dependency injection:
  ```kotlin
  class HomeViewModel(
      private val examRepo: ExamRepository = AbhyaasApplication.instance.examRepository,
      private val userRepo: UserRepository = AbhyaasApplication.instance.userRepository
  ) : ViewModel()
  ```
  This preserves the zero-argument constructor for Compose `viewModel()` while allowing unit tests to supply mocks or fakes: `HomeViewModel(mockExamRepo, mockUserRepo)`.

---

### Finding 3 (MEDIUM): Series ID Overwrite in `RemoteExamRepositoryImpl`
- **Observed Code:**
  `RemoteExamRepositoryImpl.kt` line 57:
  ```kotlin
  override suspend fun getTestById(testId: String): Result<Test?> = runCatching {
      try {
          val response = api.getTestDetail(testId)
          if (response.isSuccessful && response.body() != null) {
              response.body()!!.toDomain("series_default") // <-- HARDCODED OVERRIDE
          } else {
              MockRepo.getTestById(testId)
          }
  ```
- **Attack Scenario:**
  When a live backend is connected and returns a valid `TestDto` with `series_id = "ssc_cgl_2026"`, `RemoteExamRepositoryImpl` discards the real series ID and forces `"series_default"`. Any screen relying on `test.seriesId` for breadcrumb navigation or related test fetching will navigate to an invalid series ID.
- **Blast Radius:**
  Breaks relational integrity between tests and parent series when online.
- **Mitigation:**
  Call `response.body()!!.toDomain()` without argument, allowing `toDomain()` to use `seriesId.ifEmpty { "default_series" }`.

---

### Finding 4 (MEDIUM): Transient In-Memory Bookmarks & Concurrency Vulnerability
- **Observed Code:**
  `RemoteQuestionRepositoryImpl.kt` lines 15 & 40-51:
  ```kotlin
  private val bookmarkedIds = mutableSetOf<Int>()
  override suspend fun bookmarkQuestion(questionId: Int, bookmarked: Boolean): Result<Unit> = runCatching {
      if (bookmarked) {
          bookmarkedIds.add(questionId)
      } else {
          bookmarkedIds.remove(questionId)
      }
      Unit
  }
  ```
- **Attack Scenario:**
  1. `mutableSetOf<Int>()` (`HashSet`) is **not thread-safe**. When the user rapidly toggles bookmarks across questions or coroutines run concurrently, `ConcurrentModificationException` can be thrown.
  2. Because bookmarks are held purely in memory, backgrounding or process death wipes all bookmarks.
- **Blast Radius:**
  Potential UI crash during rapid bookmarking; loss of user bookmarks upon app restart.
- **Mitigation:**
  Use `Collections.synchronizedSet(mutableSetOf<Int>())` or `ConcurrentHashMap.newKeySet<Int>()`, and persist bookmarks via `SharedPreferences`.

---

### Finding 5 (LOW): Static Submission Evaluation on Network Failure
- **Observed Code:**
  `RemoteTestResultRepositoryImpl.kt` lines 34-39:
  ```kotlin
  override suspend fun submitTest(testId: String, answers: Map<Int, Int>, timeTakenSeconds: Long): Result<TestResult> = runCatching {
      try { ... } catch (_: Exception) {
          MockExamRepository.getPreviousAttemptResult(testId)
      }
  }.recover { MockExamRepository.getPreviousAttemptResult(testId) }
  ```
- **Attack Scenario:**
  When the backend is offline, submitting an exam returns a static `TestResult` where `score = 62.0f`, `correctCount = 62`, `incorrectCount = 13`, regardless of what answers the user selected or how much time was spent.
- **Blast Radius:**
  Functional limitation in offline demo mode. The UI does not crash, but the score displayed on the scorecard does not reflect the user's actual choices.
- **Mitigation:**
  Integrate `ExamEvaluationEngine.evaluateExam(...)` into the offline fallback path to compute real scores from the submitted `answers`.

---

## 3. Repository Fallback & ViewModel Verification Matrix

| Component | Fallback Mechanism | Exception Safety | StateFlow Emitted | Default / Offline State |
|-----------|-------------------|------------------|-------------------|--------------------------|
| `RemoteExamRepositoryImpl` | `MockExamRepository` | `runCatching + try/catch + recover` | N/A (Repository) | 2 test series (Nayab Tehsildar, MPSEB), 7 categories |
| `RemoteQuestionRepositoryImpl` | `MockQuestionRepository` | `runCatching + try/catch + recover` | N/A (Repository) | 100 questions per exam |
| `RemoteTestResultRepositoryImpl` | `MockExamRepository` & `MockUserRepository` | `runCatching + try/catch + recover` | N/A (Repository) | Full scorecards + 8 leaderboard entries |
| `RemoteUpdatesRepositoryImpl` | `MockUpdatesRepository` | `runCatching + try/catch + recover` | N/A (Repository) | 7 exam updates across 5 categories |
| `RemoteUserRepositoryImpl` | `MockUserRepository` | `runCatching + try/catch + recover` | N/A (Repository) | User "Aspirant", 6 trend data points, 6-digit OTP |
| `HomeViewModel` | Uses `examRepo` & `userRepo` | Trapped in `viewModelScope` | `StateFlow<HomeUiState>` | `isLoading = false`, 7 categories, 2 test series |
| `TestsViewModel` | Uses `examRepo` | Trapped in `viewModelScope` | `StateFlow<TestsUiState>` | `featuredSeries`, enrolled & other series |
| `TestSeriesDetailViewModel` | Uses `examRepo` | Trapped in `viewModelScope` | `StateFlow<TestSeriesDetailUiState>` | Folders by tab (Mock, PYP, Notes) |
| `UpdatesViewModel` | Uses `updatesRepo` | Trapped in `viewModelScope` | `StateFlow<UpdatesUiState>` | 7 updates, 7 categories |
| `UserProfileViewModel` | Uses `userRepo` | Trapped in `viewModelScope` | `StateFlow<UserProfileUiState>` | UserProfile, 6 trend points |
| `ActiveTestViewModel` | Uses `examRepo`, `questionRepo`, `resultRepo` | Trapped in `viewModelScope` | `StateFlow<ActiveTestUiState>` | 100 questions, timer, palette state |

---

## 4. Build & Compilation Log Summary

- **`.\gradlew compileDebugKotlin`**:
  ```text
  > Task :app:compileDebugKotlin UP-TO-DATE
  BUILD SUCCESSFUL in 34s
  7 actionable tasks: 7 up-to-date
  ```
- **`.\gradlew assembleDebug`**:
  ```text
  > Task :app:assembleDebug UP-TO-DATE
  BUILD SUCCESSFUL in 3s
  38 actionable tasks: 38 up-to-date
  ```
- **`.\gradlew testDebugUnitTest`**:
  ```text
  e: .../Milestone1DataIntegrityEmpiricalTest.kt:369:41 Unresolved reference 'getQuestionById'.
  e: .../Milestone1UiChallengeTest.kt:339:41 Unresolved reference 'getQuestionById'.
  > Task :app:compileDebugUnitTestKotlin FAILED
  BUILD FAILED in 4s
  ```

---

## 5. Required Actions for Approval

1. **Fix Unit Test Target Compilation:**
   - In `app/src/main/java/com/example/abhyaas/data/mock/MockQuestionRepository.kt`: add helper:
     ```kotlin
     fun getQuestionById(questionId: Int): Question? {
         return getTehsildarQuestions().find { it.id == questionId }
             ?: getMpsebQuestions().find { it.id == questionId }
     }
     ```
   - Update legacy unit tests in `src/test/java` so that `./gradlew testDebugUnitTest` compiles cleanly.
2. **Enable ViewModel Constructor Injection:**
   - Allow passing repository parameters to ViewModels with defaults pointing to `AbhyaasApplication.instance.*Repository`.
3. **Fix `seriesIdOverride` Bug in `RemoteExamRepositoryImpl`:**
   - Remove `"series_default"` hardcoded override on line 57 of `RemoteExamRepositoryImpl.kt`.
4. **Make `bookmarkedIds` Thread-Safe:**
   - Wrap `bookmarkedIds` in `ConcurrentHashMap.newKeySet<Int>()`.
