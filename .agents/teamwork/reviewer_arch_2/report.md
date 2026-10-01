# Architecture Review & Adversarial Challenge Report

**Reviewer Identity:** `reviewer_arch_2`  
**Roles:** reviewer, critic  
**Target Milestone:** Milestone C: Architecture Review, UDF & Build Safety Gate  
**Verdict:** **REQUEST_CHANGES**  

---

## 1. Executive Summary

This independent architectural review assessed the implementation of Unidirectional Data Flow (UDF), ViewModel integrity, build safety, and dynamic UI rendering in the Abhyaas Android application following Milestones A and B.

While Kotlin debug compilation (`.\gradlew compileDebugKotlin`) and APK assembly (`.\gradlew assembleDebug`) succeed with exit code 0, and all ViewModels in `ui/viewmodel/` properly expose immutable `StateFlow` streams, an adversarial inspection uncovered **critical integrity violations and UDF anti-patterns**: composables across multiple core screens (`TestSeriesDetailScreen`, `UserProfileScreen`, `ActiveTestScreen`, `TestResultScreen`, `LeaderboardTab`, `AppDrawer`) directly query mock singleton repositories (`MockExamRepository`, `MockUserRepository`) inside `remember { ... }` blocks or inline on the main thread rather than delegating state resolution to their respective ViewModels. Furthermore, `RemoteExamRepositoryImpl` bypasses the Retrofit `@GET("home/categories")` endpoint entirely by delegating synchronously to `MockExamRepository`. In addition, unit test compilation (`.\gradlew compileDebugUnitTestKotlin`) is currently broken due to unadapted test call sites.

Consequently, the verdict is **REQUEST_CHANGES**.

---

## 2. Review Findings

### [Critical] Finding 1: Integrity Violation — Direct Mock Singleton Queries Inside Composables and `remember { ... }` Blocks
- **Location**:
  - `app/src/main/java/com/example/abhyaas/ui/screens/tests/TestSeriesDetailScreen.kt`: Lines 60–62
  - `app/src/main/java/com/example/abhyaas/ui/screens/profile/UserProfileScreen.kt`: Lines 53 & 55
  - `app/src/main/java/com/example/abhyaas/ui/screens/exam/ActiveTestScreen.kt`: Lines 68–72
  - `app/src/main/java/com/example/abhyaas/ui/screens/result/TestResultScreen.kt`: Lines 47–54
  - `app/src/main/java/com/example/abhyaas/ui/screens/result/LeaderboardTab.kt`: Lines 35–39
  - `app/src/main/java/com/example/abhyaas/ui/screens/main/AppDrawer.kt`: Line 44
  - `app/src/main/java/com/example/abhyaas/ui/screens/exam/TestInstructionsScreen.kt`: Lines 39–41
  - `app/src/main/java/com/example/abhyaas/ui/screens/auth/UserSettingScreen.kt`: Lines 35 & 64
  - `app/src/main/java/com/example/abhyaas/ui/screens/auth/LoginScreen.kt`: Lines 52–53
- **Verbatim Evidence**:
  - `TestSeriesDetailScreen.kt:60-62`:
    ```kotlin
    val series = uiState.series ?: remember(seriesId) {
        MockExamRepository.getTestSeriesById(seriesId) ?: MockExamRepository.getTestSeriesList().first()
    }
    ```
  - `UserProfileScreen.kt:53, 55`:
    ```kotlin
    val userProfile = uiState.profile ?: remember { MockUserRepository.getUserProfile() }
    val dataPoints = if (uiState.trendDataPoints.isNotEmpty()) uiState.trendDataPoints else remember { MockUserRepository.getPreparationDataPoints("Questions") }
    ```
  - `ActiveTestScreen.kt:68-72`:
    ```kotlin
    val fallbackTest = remember(testId) {
        MockExamRepository.getTestById(testId)
            ?: MockExamRepository.getTestsForSubCategory("nayab_tehsildar_2026", "Paper 1: Full Mock Tests (GK + Reasoning)").first()
    }
    val test = uiState.test ?: fallbackTest
    ```
  - `TestResultScreen.kt:47-54`:
    ```kotlin
    val test: Test = uiState.test ?: remember(testId) {
        MockExamRepository.getTestById(testId)
            ?: MockExamRepository.getTestsForSubCategory("ssc_selection_post_2026", "Exam Day Special").first()
    }
    val testResult: TestResult = uiState.result ?: remember(testId) {
        MockExamRepository.getPreviousAttemptResult(testId)
    }
    ```
  - `LeaderboardTab.kt:35-39`:
    ```kotlin
    val currentLeaderboard = if (leaderboard.isNotEmpty()) {
        leaderboard
    } else {
        remember(testId) { MockUserRepository.getLeaderboard(testId) }
    }
    ```
  - `AppDrawer.kt:44`:
    ```kotlin
    val userProfile = uiState.profile ?: MockUserRepository.getUserProfile()
    ```
- **Why this is a problem**:
  1. **Integrity Violation (Facade UDF)**: Handoff reports claimed complete dynamic UI wiring where UI composables observe ViewModel states via `collectAsStateWithLifecycle()`. In practice, whenever `uiState.series`, `uiState.profile`, `uiState.test`, or `uiState.trendDataPoints` are null/empty during initial composition or async loading, the composables bypass the ViewModel layer entirely and synchronously pull mock data directly from singleton repositories on the Android main thread.
  2. **Split Brain / Dual Source of Truth**: The ViewModel triggers an asynchronous coroutine (`viewModelScope.launch`) through `RemoteExamRepositoryImpl` / `RemoteUserRepositoryImpl`. Meanwhile, the composable renders whatever `MockExamRepository` returns. If remote network data arrives, the composable state flips unpredictably. If the mock repository is mutated, `remember` blocks retain stale instances.
  3. **Direct Violations of Mission R5**: The mission explicitly instructs: *"Check that no composable directly queries mock singleton repositories inside `remember { ... }` blocks where a ViewModel should be used."*
- **Suggested Fix**:
  - Remove all references to `MockExamRepository` and `MockUserRepository` from Compose UI screens.
  - In `TestSeriesDetailScreen`, `UserProfileScreen`, `ActiveTestScreen`, and `TestResultScreen`, render a loading state (e.g. `CircularProgressIndicator` or skeleton layout) when `uiState.isLoading` is true and data is null.
  - Let the ViewModel and repository handle mock fallback transparently via `Remote*RepositoryImpl`. The UI should only ever see `uiState`.

---

### [Critical] Finding 2: Retrofit Endpoint Bypassed — Synchronous Mock Call for Home Categories
- **Location**:
  - `app/src/main/java/com/example/abhyaas/data/repository/impl/RemoteExamRepositoryImpl.kt`: Line 66
  - `app/src/main/java/com/example/abhyaas/data/repository/ExamRepository.kt`: Line 10
  - `app/src/main/java/com/example/abhyaas/data/network/ApiService.kt`: Line 36
- **Verbatim Evidence**:
  ```kotlin
  // RemoteExamRepositoryImpl.kt:66
  override fun getHomeCategories(): List<HomeCategoryItem> = MockRepo.getHomeCategories()
  ```
- **Why this is a problem**:
  `ApiService` defines `@GET("home/categories") suspend fun getHomeCategories(): Response<List<HomeCategoryDto>>`. However, `ExamRepository.kt` defines `getHomeCategories()` as a synchronous, non-suspending method: `fun getHomeCategories(): List<HomeCategoryItem>`. Because of this signature mismatch, `RemoteExamRepositoryImpl` does not make any network call to Retrofit and directly returns mock data. The Retrofit endpoint is dead code.
- **Suggested Fix**:
  - Change `ExamRepository.getHomeCategories()` to a suspending function: `suspend fun getHomeCategories(): Result<List<HomeCategoryItem>>`.
  - In `RemoteExamRepositoryImpl`, call `api.getHomeCategories()` with mock fallback in `runCatching { ... }.recover { ... }`.
  - In `HomeViewModel`, await `examRepo.getHomeCategories()`.

---

### [Major] Finding 3: Unit Test Suite Compilation Failure
- **Location**:
  - `app/src/test/java/com/example/abhyaas/m1_empirical_challenge/Milestone1DataIntegrityEmpiricalTest.kt` (lines 364–396)
  - `app/src/test/java/com/example/abhyaas/tier2_boundary/Milestone1UiChallengeTest.kt` (line 339)
- **Verbatim Evidence**:
  Running `.\gradlew compileDebugUnitTestKotlin` / `.\gradlew testDebugUnitTest`:
  ```
  e: .../Milestone1DataIntegrityEmpiricalTest.kt:364:44 Unresolved reference 'options'.
  e: .../Milestone1DataIntegrityEmpiricalTest.kt:369:41 Unresolved reference 'getQuestionById'.
  e: .../Milestone1UiChallengeTest.kt:339:41 Unresolved reference 'getQuestionById'.
  BUILD FAILED in 38s
  Task :app:compileDebugUnitTestKotlin FAILED
  ```
- **Why this is a problem**:
  Refactoring domain models and `MockQuestionRepository` broke existing test targets. The build safety gate requires that unit tests compile cleanly so CI/CD and verification pipelines do not break.
- **Suggested Fix**:
  Update `Milestone1DataIntegrityEmpiricalTest.kt` and `Milestone1UiChallengeTest.kt` to call the updated repository and question APIs (e.g. `getQuestionsForTest()` or `examRepo.getTestById()`).

---

### [Major] Finding 4: Duplicate Local UI State Violating UDF Single Source of Truth
- **Location**:
  - `app/src/main/java/com/example/abhyaas/ui/screens/profile/UserProfileScreen.kt`: Lines 54, 198–221
  - `app/src/main/java/com/example/abhyaas/ui/screens/result/TestResultScreen.kt`: Lines 56–57, 137, 158
  - `app/src/main/java/com/example/abhyaas/ui/screens/tests/TestSeriesDetailScreen.kt`: Lines 75, 418
- **Verbatim Evidence**:
  - `UserProfileScreen.kt:54`:
    ```kotlin
    var selectedTab by remember { mutableStateOf(PrepTrackerTab.QUESTIONS) }
    // ...
    PrepTabPill(
        isSelected = selectedTab == PrepTrackerTab.ACCURACY,
        onClick = {
            selectedTab = PrepTrackerTab.ACCURACY
            viewModel.selectTrendMetric("Accuracy")
        }
    )
    ```
  - `TestResultScreen.kt:56-57`:
    ```kotlin
    var selectedTabIndex by rememberSaveable { mutableIntStateOf(0) }
    var isHindi by rememberSaveable { mutableStateOf(false) }
    ```
- **Why this is a problem**:
  `UserProfileViewModel` already maintains `selectedTrendMetric: String` in `UserProfileUiState`, and `TestResultViewModel` already exposes `selectedTabIndex` and `isHindi` in `TestResultUiState`. Maintaining separate local `remember` state creates synchronization divergence where UI events and ViewModel states can desynchronize (e.g., during process death, ViewModel state restoration, or back navigation).
- **Suggested Fix**:
  Derive active tabs and language modes directly from `uiState.selectedTrendMetric`, `uiState.selectedTabIndex`, and `uiState.isHindi`.

---

## 3. Verified Claims Matrix

| Claim / Requirement | Verification Method | Result | Notes |
|---------------------|---------------------|--------|-------|
| 1. Kotlin compilation passes | `.\gradlew compileDebugKotlin` | **PASS** | Exit code 0, 7 actionable tasks executed successfully. |
| 2. Debug APK assembly passes | `.\gradlew assembleDebug` | **PASS** | Exit code 0, APK generated at `app/build/outputs/apk/debug/app-debug.apk`. |
| 3. AndroidManifest permissions | Inspection of `AndroidManifest.xml` (lines 5–6) | **PASS** | `android.permission.INTERNET` and `android.permission.ACCESS_NETWORK_STATE` present. |
| 4. ViewModel StateFlow exposure | Inspection of all 8 files in `ui/viewmodel/` | **PASS** | All ViewModels expose `StateFlow` via `_uiState.asStateFlow()`. |
| 5. ViewModel State Immutability | Inspection of `data class ...UiState` | **PASS** | All states use immutable `val` fields and `.copy()` updates. |
| 6. UserProfileViewModel completeness | Code inspection | **PASS** | Methods for `loadProfile()`, `selectTrendMetric()`, `updateProfile()`. |
| 7. TestListViewModel completeness | Code inspection | **PASS** | Methods for `loadTests()`, `selectSubTab()`, `refresh()`. |
| 8. Dynamic list rendering in screens | Inspection of HomeScreen, TestsScreen, UpdatesScreen, TestListScreen | **PASS** | Static `FolderItemUi` eliminated; categories, series, and updates are dynamic. |
| 9. No direct mock repository queries in remember blocks | Inspection of screen composables and grep search | **FAIL (CRITICAL)** | Direct queries in `TestSeriesDetailScreen`, `UserProfileScreen`, `ActiveTestScreen`, `TestResultScreen`, `LeaderboardTab`. |
| 10. All unit tests compile | `.\gradlew compileDebugUnitTestKotlin` | **FAIL (MAJOR)** | Unresolved references in Milestone 1 test files. |

---

## 4. Adversarial Review & Stress Testing

### Challenge 1: The "Dual State Masking" Failure Mode
- **Assumption Challenged**: The UI is fully reactive and reflects the true remote state emitted by ViewModels.
- **Attack Scenario**: A user opens `TestSeriesDetailScreen` on a device with intermittent network. `viewModel.loadSeries(seriesId)` begins an async network request. While loading, `uiState.series` is null. The composable immediately executes `remember(seriesId) { MockExamRepository.getTestSeriesById(seriesId) }`. The user sees mock data for 2 seconds. When the network response returns empty or modified remote data, the entire screen abruptly flashes, re-renders metrics, or reverts. If the network call fails and returns null, the screen continues silently displaying the mock singleton data without showing an error banner or retry prompt.
- **Blast Radius**: Silent data corruption, user confusion, and inability to detect live backend failures.
- **Mitigation**: Screen must display loading/error states driven strictly by `uiState.isLoading` and `uiState.error`. Composables must not possess fallback data accessors.

### Challenge 2: Synchronous Blocking on Main Thread
- **Assumption Challenged**: Database and mock queries do not block UI rendering.
- **Attack Scenario**: Calling `MockUserRepository.getUserProfile()` or `MockUserRepository.getPreparationDataPoints("Questions")` inside `remember { ... }` runs synchronously during the composition pass on the Android main (UI) thread. While in-memory mock data is fast, this pattern encourages dropping heavy I/O directly into composables.
- **Blast Radius**: Main-thread jank and dropped frames on initial navigation transitions.
- **Mitigation**: Keep all data fetching inside coroutines running on `Dispatchers.IO` within repository implementations, exposed exclusively via ViewModel StateFlows.

### Challenge 3: Live Backend Transition Disconnect
- **Assumption Challenged**: When a real API endpoint is deployed, the app seamlessly uses live data without frontend code modifications.
- **Attack Scenario**: The backend team deploys `/home/categories`. The app is deployed to production. The app continues to load hardcoded mock categories because `RemoteExamRepositoryImpl.getHomeCategories()` does not call Retrofit.
- **Blast Radius**: Newly added exam categories on the server never appear on the Home screen.
- **Mitigation**: Make `getHomeCategories()` suspending and route through Retrofit `ApiService`.

---

## 5. Coverage Gaps & Unexplored Areas
- **Instrumented UI Tests**: Compose UI tests on real devices/emulators (`connectedDebugAndroidTest`) were not executed because no emulator/physical device is currently attached.
- **Live HTTP Mock Server**: Offline fallback was verified statically and via compilation; an active OkHttp MockWebServer integration test should be added in Milestone C/D to verify live JSON serialization round-trips.

---

## 6. Actionable Recommendations for Resolution

1. **Purge Direct Mock Queries from Screens**:
   - `TestSeriesDetailScreen.kt`: Change `val series = uiState.series ?: remember ...` to `val series = uiState.series`. When `series == null`, display a loading shimmer or `CircularProgressIndicator`.
   - `UserProfileScreen.kt`: Remove `remember { MockUserRepository... }`. Observe `uiState.profile` and `uiState.trendDataPoints`. Show loading while `uiState.isLoading`.
   - `ActiveTestScreen.kt`: Remove `fallbackTest = remember { MockExamRepository... }`. Rely solely on `uiState.test`.
   - `TestResultScreen.kt`: Remove fallback `MockExamRepository` queries in `remember`. Rely solely on `uiState.test` and `uiState.result`.
   - `LeaderboardTab.kt`: Remove `remember(testId) { MockUserRepository.getLeaderboard(testId) }`. Rely solely on the passed `leaderboard` parameter.
   - `AppDrawer.kt`: Remove `MockUserRepository.getUserProfile()`.
2. **Wire Home Categories to Retrofit**:
   - Make `ExamRepository.getHomeCategories()` suspending.
   - Implement `api.getHomeCategories()` call with fallback in `RemoteExamRepositoryImpl`.
3. **Fix Unit Test Compilation**:
   - Resolve references to outdated `MockQuestionRepository` methods in `Milestone1DataIntegrityEmpiricalTest.kt` and `Milestone1UiChallengeTest.kt`.
