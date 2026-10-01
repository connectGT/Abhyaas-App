# Handoff Report: Composable Mock Repository Remediation

**From:** `explorer_remedy_composables`  
**To:** `orchestrator_2` / `implementer_remedy_1` (Conversation ID: `775ae211-11a2-4a96-8b5f-1cf13bac3000`)  
**Type:** Hard Handoff (Investigation Complete)  
**Date:** 2026-09-30  

---

## 1. Observation

A full static analysis was conducted on all files in `app/src/main/java/com/example/abhyaas/ui/`.
The command `git grep -nE "Mock(Exam|User|Question|Updates)Repository" app/src/main/java/com/example/abhyaas/ui/` identified 9 distinct UI files containing mock repository dependencies:

1. **`app/src/main/java/com/example/abhyaas/ui/screens/tests/TestSeriesDetailScreen.kt`**
   - Line 28: `import com.example.abhyaas.data.mock.MockExamRepository`
   - Lines 60–62:
     ```kotlin
     val series = uiState.series ?: remember(seriesId) {
         MockExamRepository.getTestSeriesById(seriesId) ?: MockExamRepository.getTestSeriesList().first()
     }
     ```

2. **`app/src/main/java/com/example/abhyaas/ui/screens/profile/UserProfileScreen.kt`**
   - Line 31: `import com.example.abhyaas.data.mock.MockUserRepository`
   - Line 53: `val userProfile = uiState.profile ?: remember { MockUserRepository.getUserProfile() }`
   - Line 55: `val dataPoints = if (uiState.trendDataPoints.isNotEmpty()) uiState.trendDataPoints else remember { MockUserRepository.getPreparationDataPoints("Questions") }`

3. **`app/src/main/java/com/example/abhyaas/ui/screens/exam/ActiveTestScreen.kt`**
   - Line 37: `import com.example.abhyaas.data.mock.MockExamRepository`
   - Lines 68–72:
     ```kotlin
     val fallbackTest = remember(testId) {
         MockExamRepository.getTestById(testId)
             ?: MockExamRepository.getTestsForSubCategory("nayab_tehsildar_2026", "Paper 1: Full Mock Tests (GK + Reasoning)").first()
     }
     val test = uiState.test ?: fallbackTest
     ```

4. **`app/src/main/java/com/example/abhyaas/ui/screens/exam/TestInstructionsScreen.kt`**
   - Line 22: `import com.example.abhyaas.data.mock.MockExamRepository`
   - Lines 39–41:
     ```kotlin
     val test = remember(testId) {
         MockExamRepository.getTestById(testId) ?: MockExamRepository.getTestsForSubCategory("ssc_selection_post_2026", "Exam Day Special").first()
     }
     ```

5. **`app/src/main/java/com/example/abhyaas/ui/screens/result/TestResultScreen.kt`**
   - Line 20: `import com.example.abhyaas.data.mock.MockExamRepository`
   - Lines 47–50:
     ```kotlin
     val test: Test = uiState.test ?: remember(testId) {
         MockExamRepository.getTestById(testId)
             ?: MockExamRepository.getTestsForSubCategory("ssc_selection_post_2026", "Exam Day Special").first()
     }
     ```
   - Lines 52–54:
     ```kotlin
     val testResult: TestResult = uiState.result ?: remember(testId) {
         MockExamRepository.getPreviousAttemptResult(testId)
     }
     ```

6. **`app/src/main/java/com/example/abhyaas/ui/screens/result/LeaderboardTab.kt`**
   - Line 24: `import com.example.abhyaas.data.mock.MockUserRepository`
   - Lines 35–39:
     ```kotlin
     val currentLeaderboard = if (leaderboard.isNotEmpty()) {
         leaderboard
     } else {
         remember(testId) { MockUserRepository.getLeaderboard(testId) }
     }
     ```

7. **`app/src/main/java/com/example/abhyaas/ui/screens/main/AppDrawer.kt`**
   - Line 28: `import com.example.abhyaas.data.mock.MockUserRepository`
   - Line 44: `val userProfile = uiState.profile ?: MockUserRepository.getUserProfile()`

8. **`app/src/main/java/com/example/abhyaas/ui/screens/auth/UserSettingScreen.kt`**
   - Line 22: `import com.example.abhyaas.data.mock.MockUserRepository`
   - Line 35: `val existingProfile = remember { MockUserRepository.getUserProfile() }`
   - Line 64: `MockUserRepository.updateUserProfile(updated)`

9. **`app/src/main/java/com/example/abhyaas/ui/screens/auth/LoginScreen.kt`**
   - Line 31: `import com.example.abhyaas.data.mock.MockUserRepository`
   - Line 52: `val currentProfile = MockUserRepository.getUserProfile()`
   - Line 53: `MockUserRepository.updateUserProfile(currentProfile.copy(mobileNumber = "+91 $trimmed"))`

All other 35 UI files (including `HomeScreen.kt`, `TestsScreen.kt`, `TestListScreen.kt`, `UpdatesScreen.kt`, `PassScreen.kt`, `PrivacyPolicyScreen.kt`, `AnalysisTab.kt`, `SolutionsTab.kt`, `QuestionSolutionView.kt`, `SolutionsSectionDrawer.kt`, and palette/dialog components) contain zero mock references and are completely clean.

---

## 2. Logic Chain

1. **UDF and Architecture Violation:**  
   In Jetpack Compose MVVM architecture, the UI layer must be a passive consumer of `uiState` emitted as a `StateFlow` from a ViewModel. Invoking singleton mock repositories (`MockExamRepository`, `MockUserRepository`) directly inside composable bodies:
   - Breaks Unidirectional Data Flow (UDF).
   - Creates a race condition where the UI renders mock data before ViewModel state arrives.
   - Bypasses repository abstractions, dependency injection, and testability.

2. **Root Cause Analysis per File:**
   - In `TestSeriesDetailScreen`, `UserProfileScreen`, `ActiveTestScreen`, `TestResultScreen`, and `LeaderboardTab`, developers inserted `remember { Mock... }` or `?: Mock...` blocks to guard against initial nulls instead of rendering standard loading/empty states.
   - In `AppDrawer`, `uiState.profile ?: MockUserRepository.getUserProfile()` was used as a shortcut fallback instead of safe nullable rendering or an empty data model default.
   - In `UserSettingScreen` and `LoginScreen`, ViewModel injection was omitted, and static singleton methods were called directly for read and write.
   - In `TestInstructionsScreen`, ViewModel injection was omitted, relying on a `remember(testId) { MockExamRepository... }` block.

3. **Remediation Strategy:**
   - **For Screens with existing ViewModels (`TestSeriesDetailScreen`, `UserProfileScreen`, `ActiveTestScreen`, `TestResultScreen`, `LeaderboardTab`, `AppDrawer`):**
     Eliminate imports of mock repositories. Guard against `uiState` nulls with centered `CircularProgressIndicator` or empty state layouts. Consume values strictly from `uiState`.
   - **For Screens without ViewModels (`TestInstructionsScreen`, `UserSettingScreen`, `LoginScreen`):**
     Inject appropriate ViewModels (`UserProfileViewModel` for `LoginScreen` and `UserSettingScreen`; `TestInstructionsViewModel` or `ActiveTestViewModel` for `TestInstructionsScreen`) with `collectAsStateWithLifecycle()`. Route user mutations through ViewModel actions (`viewModel.updateProfile(...)`).
   - **For ViewModels:**
     Support constructor parameter injection with default values (`repository = AbhyaasApplication.instance.repository`) so both Compose `viewModel()` factory and unit test harnesses can instantiate them cleanly.

---

## 3. Caveats

- `TestInstructionsScreen` can either receive a dedicated `TestInstructionsViewModel` or share `ActiveTestViewModel`. A dedicated `TestInstructionsViewModel` is recommended to prevent coupling instruction pre-flight checks with the active test timer/engine state.
- In `UserSettingScreen`, the fields are form inputs (`OutlinedTextField`). When refactored to read from `uiState.profile`, a `LaunchedEffect(uiState.profile)` must be used to populate initial form state once without overwriting in-progress user typing on re-composition.
- In `LeaderboardTab`, when the leaderboard is genuinely empty from the repository, the UI should display an empty state indicator rather than hanging on a spinner.

---

## 4. Conclusion

All 9 instances of mock repository usage in the UI layer have been mapped down to exact file paths and line numbers. Detailed refactoring blueprints with drop-in code specifications have been documented in `report.md`. 

The implementer (`implementer_remedy_1`) can execute these changes systematically without ambiguity. When completed, zero references to `MockExamRepository`, `MockUserRepository`, `MockQuestionRepository`, or `MockUpdatesRepository` will remain anywhere in `app/src/main/java/com/example/abhyaas/ui/`.

---

## 5. Verification Method

To independently verify the remediation:

1. **Grep for Mock Repository Invocations in UI:**
   ```powershell
   git grep -E "Mock(Exam|User|Question|Updates)Repository" app/src/main/java/com/example/abhyaas/ui/
   ```
   *Expected Result: 0 matches.*

2. **Verify Kotlin Debug Compilation:**
   ```powershell
   .\gradlew compileDebugKotlin
   ```
   *Expected Result: Exit code 0 (BUILD SUCCESSFUL).*

3. **Verify Debug APK Build:**
   ```powershell
   .\gradlew assembleDebug
   ```
   *Expected Result: Exit code 0 (BUILD SUCCESSFUL).*

4. **Verify Unit Tests:**
   ```powershell
   .\gradlew compileDebugUnitTestKotlin
   ```
   *Expected Result: Exit code 0 (BUILD SUCCESSFUL).*
