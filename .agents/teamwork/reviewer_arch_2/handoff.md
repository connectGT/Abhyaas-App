# Handoff Report: Architecture & ViewModel Review

**Verdict:** **REQUEST_CHANGES**  
**From:** `reviewer_arch_2`  
**To:** `orchestrator_2` (Conversation ID: `775ae211-11a2-4a96-8b5f-1cf13bac3000`)  
**Date:** 2026-09-30  
**Status:** Hard Handoff (Review Complete)

---

## 1. Observation

1. **Gradle Compilation & Assembly**:
   - Command: `.\gradlew compileDebugKotlin`  
     *Result:* Exit code 0 (`BUILD SUCCESSFUL in 1m 16s`).
   - Command: `.\gradlew assembleDebug`  
     *Result:* Exit code 0 (`BUILD SUCCESSFUL in 4s`).
   - Command: `.\gradlew compileDebugUnitTestKotlin` / `.\gradlew testDebugUnitTest`  
     *Result:* Exit code 1 (`FAILURE: Build failed with an exception. Execution failed for task ':app:compileDebugUnitTestKotlin'`). Unresolved references in `Milestone1DataIntegrityEmpiricalTest.kt` (lines 364–396) and `Milestone1UiChallengeTest.kt` (line 339) due to removed legacy `MockQuestionRepository` methods.

2. **Network Permissions in AndroidManifest.xml**:
   - `app/src/main/AndroidManifest.xml` (lines 5–6):
     ```xml
     <uses-permission android:name="android.permission.INTERNET" />
     <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
     ```
     Both required network permissions are present.

3. **ViewModels StateFlow & Immutability**:
   - All 8 ViewModels in `app/src/main/java/com/example/abhyaas/ui/viewmodel/` (`ActiveTestViewModel`, `HomeViewModel`, `TestListViewModel`, `TestResultViewModel`, `TestSeriesDetailViewModel`, `TestsViewModel`, `UpdatesViewModel`, `UserProfileViewModel`):
     - Encapsulate `private val _uiState = MutableStateFlow(...)`.
     - Expose public `val uiState: StateFlow<...> = _uiState.asStateFlow()`.
     - Maintain immutable state data classes with `val` properties.
     - State transitions use `.copy()` or newly instantiated UI state models.
   - `UserProfileViewModel` and `TestListViewModel` are fully implemented with state models, coroutine scopes, and public action methods.

4. **Direct Mock Singleton Queries in Composables and `remember { ... }` Blocks**:
   - `app/src/main/java/com/example/abhyaas/ui/screens/tests/TestSeriesDetailScreen.kt` (lines 60–62):
     ```kotlin
     val series = uiState.series ?: remember(seriesId) {
         MockExamRepository.getTestSeriesById(seriesId) ?: MockExamRepository.getTestSeriesList().first()
     }
     ```
   - `app/src/main/java/com/example/abhyaas/ui/screens/profile/UserProfileScreen.kt` (lines 53, 55):
     ```kotlin
     val userProfile = uiState.profile ?: remember { MockUserRepository.getUserProfile() }
     val dataPoints = if (uiState.trendDataPoints.isNotEmpty()) uiState.trendDataPoints else remember { MockUserRepository.getPreparationDataPoints("Questions") }
     ```
   - `app/src/main/java/com/example/abhyaas/ui/screens/exam/ActiveTestScreen.kt` (lines 68–72):
     ```kotlin
     val fallbackTest = remember(testId) {
         MockExamRepository.getTestById(testId)
             ?: MockExamRepository.getTestsForSubCategory("nayab_tehsildar_2026", "Paper 1: Full Mock Tests (GK + Reasoning)").first()
     }
     val test = uiState.test ?: fallbackTest
     ```
   - `app/src/main/java/com/example/abhyaas/ui/screens/result/TestResultScreen.kt` (lines 47–54):
     ```kotlin
     val test: Test = uiState.test ?: remember(testId) {
         MockExamRepository.getTestById(testId)
             ?: MockExamRepository.getTestsForSubCategory("ssc_selection_post_2026", "Exam Day Special").first()
     }
     val testResult: TestResult = uiState.result ?: remember(testId) {
         MockExamRepository.getPreviousAttemptResult(testId)
     }
     ```
   - `app/src/main/java/com/example/abhyaas/ui/screens/result/LeaderboardTab.kt` (lines 35–39):
     ```kotlin
     val currentLeaderboard = if (leaderboard.isNotEmpty()) {
         leaderboard
     } else {
         remember(testId) { MockUserRepository.getLeaderboard(testId) }
     }
     ```
   - `app/src/main/java/com/example/abhyaas/ui/screens/main/AppDrawer.kt` (line 44):
     ```kotlin
     val userProfile = uiState.profile ?: MockUserRepository.getUserProfile()
     ```
   - `app/src/main/java/com/example/abhyaas/ui/screens/exam/TestInstructionsScreen.kt` (lines 39–41):
     ```kotlin
     val test = remember(testId) {
         MockExamRepository.getTestById(testId) ?: MockExamRepository.getTestsForSubCategory("ssc_selection_post_2026", "Exam Day Special").first()
     }
     ```
   - `app/src/main/java/com/example/abhyaas/ui/screens/auth/UserSettingScreen.kt` (lines 35, 64):
     ```kotlin
     val existingProfile = remember { MockUserRepository.getUserProfile() }
     // ...
     MockUserRepository.updateUserProfile(updated)
     ```
   - `app/src/main/java/com/example/abhyaas/ui/screens/auth/LoginScreen.kt` (lines 52–53):
     ```kotlin
     val currentProfile = MockUserRepository.getUserProfile()
     MockUserRepository.updateUserProfile(...)
     ```

5. **Bypassed Retrofit Endpoint**:
   - `app/src/main/java/com/example/abhyaas/data/repository/impl/RemoteExamRepositoryImpl.kt` (line 66):
     ```kotlin
     override fun getHomeCategories(): List<HomeCategoryItem> = MockRepo.getHomeCategories()
     ```
   - `app/src/main/java/com/example/abhyaas/data/repository/ExamRepository.kt` (line 10):
     ```kotlin
     fun getHomeCategories(): List<HomeCategoryItem>
     ```
   - `app/src/main/java/com/example/abhyaas/data/network/ApiService.kt` (line 36):
     ```kotlin
     @GET("home/categories")
     suspend fun getHomeCategories(): Response<List<HomeCategoryDto>>
     ```

---

## 2. Logic Chain

1. **Mission Mandate R5 vs Observed Code:**  
   Mission Requirement 5 states: *"Check that no composable directly queries mock singleton repositories inside `remember { ... }` blocks where a ViewModel should be used."* Observations in Section 1.4 show that 9 distinct UI files contain direct invocations of `MockExamRepository` and `MockUserRepository` inside `remember { ... }` or directly in composable bodies.

2. **Violation of Unidirectional Data Flow (UDF) & Dual Source of Truth:**  
   In `TestSeriesDetailScreen.kt`, `UserProfileScreen.kt`, and `ActiveTestScreen.kt`, the composable triggers the ViewModel via `LaunchedEffect`, but immediately reads from `MockExamRepository` / `MockUserRepository` via `remember { ... }` whenever `uiState` is initially loading or null. This causes the UI to display stale mock singleton state before the ViewModel's state emission occurs, completely subverting the reactive state lifecycle (`collectAsStateWithLifecycle()`).

3. **Integrity Violation Tagging:**  
   Per system instructions: *"If you detect ANY of these patterns [shortcuts that bypass the intended task, dummy/facade implementations, hardcoded mock shortcuts], your verdict MUST be REQUEST_CHANGES with a Critical finding tagged as INTEGRITY VIOLATION."* The reliance on singleton mock queries inside `remember` blocks directly circumvents the architectural refactoring to an asynchronous, repository-backed MVVM pattern.

4. **Dead Network Layer for Home Categories:**  
   In Section 1.5, `ApiService` declares `@GET("home/categories")`, but `ExamRepository` defines `getHomeCategories()` as a non-suspending method. `RemoteExamRepositoryImpl` simply delegates to `MockRepo.getHomeCategories()` without invoking Retrofit.

5. **Build & Test Regression:**  
   While `compileDebugKotlin` and `assembleDebug` pass (Observation 1.1), `compileDebugUnitTestKotlin` fails with compilation errors due to unadjusted test code following Milestone A refactoring.

6. **Conclusion Deduction:**  
   Because of Critical Finding 1 (Direct mock queries in `remember` blocks), Critical Finding 2 (Bypassed Retrofit endpoint for categories), and Major Finding 3 (Broken unit test compilation), the changes cannot be approved.

---

## 3. Caveats

- Android instrumented device tests (`connectedDebugAndroidTest`) could not be run as no hardware or virtual device is attached.
- Debug APK packaging (`assembleDebug`) is functional, indicating binary syntax correctness, but architectural runtime invariants are violated by the main-thread mock queries.

---

## 4. Conclusion

The implementation is **NOT APPROVED**. The verdict is **REQUEST_CHANGES**.

Before Milestone C approval can be granted, the following remediation steps must be completed:
1. **Eliminate All Mock Repository Invocations from UI Screens**:
   - `TestSeriesDetailScreen.kt`: Remove `MockExamRepository.getTestSeriesById(...)`. When `uiState.series == null`, render a loading indicator or placeholder until `uiState` arrives.
   - `UserProfileScreen.kt`: Remove `MockUserRepository.getUserProfile()` and `MockUserRepository.getPreparationDataPoints(...)`. Drive the UI strictly from `uiState.profile` and `uiState.trendDataPoints`.
   - `ActiveTestScreen.kt`: Remove `MockExamRepository.getTestById(...)` fallback inside `remember`. Rely solely on `uiState.test`.
   - `TestResultScreen.kt`: Remove fallback `MockExamRepository` queries.
   - `LeaderboardTab.kt`: Remove `MockUserRepository.getLeaderboard(testId)`.
   - `AppDrawer.kt`: Remove `MockUserRepository.getUserProfile()`.
   - `UserSettingScreen.kt` & `LoginScreen.kt`: Wire user operations through `UserProfileViewModel` or repository abstractions rather than mutating `MockUserRepository` directly.
2. **Wire Home Categories through Retrofit**:
   - Update `ExamRepository.kt` to `suspend fun getHomeCategories(): Result<List<HomeCategoryItem>>`.
   - Implement `api.getHomeCategories()` in `RemoteExamRepositoryImpl` with proper mock fallback.
3. **Fix Unit Test Compilation**:
   - Fix compilation errors in `Milestone1DataIntegrityEmpiricalTest.kt` and `Milestone1UiChallengeTest.kt` so that `.\gradlew compileDebugUnitTestKotlin` succeeds.

---

## 5. Verification Method

1. **Verify Absence of Mock Queries in UI Layer**:
   ```powershell
   rg "MockExamRepository|MockUserRepository" app/src/main/java/com/example/abhyaas/ui/screens
   ```
   *Expected result: 0 matches found.*
2. **Verify Kotlin Debug Compilation**:
   ```powershell
   .\gradlew compileDebugKotlin
   ```
   *Expected result: BUILD SUCCESSFUL (code 0).*
3. **Verify Debug APK Assembly**:
   ```powershell
   .\gradlew assembleDebug
   ```
   *Expected result: BUILD SUCCESSFUL (code 0).*
4. **Verify Unit Test Compilation**:
   ```powershell
   .\gradlew compileDebugUnitTestKotlin
   ```
   *Expected result: BUILD SUCCESSFUL (code 0).*
