# Composable Mock Repository Remediation Report

**Date:** 2026-09-30  
**Investigator:** `explorer_remedy_composables`  
**Target:** Elimination of Mock Singleton Invocations from UI Layer (`app/src/main/java/com/example/abhyaas/ui/`)  
**Mode:** Read-Only Investigation  

---

## 1. Executive Summary

A comprehensive forensic audit of all 44 Kotlin files across `app/src/main/java/com/example/abhyaas/ui/` was executed. The audit identified **9 distinct UI files** containing direct invocations and imports of `MockExamRepository` and `MockUserRepository`. No occurrences of `MockQuestionRepository` or `MockUpdatesRepository` were found in the UI layer.

These 9 files violate Android Unidirectional Data Flow (UDF) and the MVVM architecture by querying mock singleton repositories directly in composable bodies (often wrapped inside `remember { ... }` blocks). This causes the UI to read stale, synchronous mock state on the main thread rather than observing asynchronous, reactive `uiState` flows emitted by ViewModels.

### Summary Table of Identified Mock Usages in UI

| # | File Path | Line(s) | Mock Target | Problematic Construct |
|---|-----------|---------|-------------|-----------------------|
| 1 | `ui/screens/tests/TestSeriesDetailScreen.kt` | 28, 60–62 | `MockExamRepository` | `remember(seriesId)` fallback for `series` |
| 2 | `ui/screens/profile/UserProfileScreen.kt` | 31, 53, 55 | `MockUserRepository` | `remember` fallback for `userProfile` & `dataPoints` |
| 3 | `ui/screens/exam/ActiveTestScreen.kt` | 37, 68–72 | `MockExamRepository` | `remember(testId)` fallback for `fallbackTest` |
| 4 | `ui/screens/exam/TestInstructionsScreen.kt` | 22, 39–41 | `MockExamRepository` | `remember(testId)` direct query without ViewModel |
| 5 | `ui/screens/result/TestResultScreen.kt` | 20, 47–54 | `MockExamRepository` | `remember(testId)` fallback for `test` and `testResult` |
| 6 | `ui/screens/result/LeaderboardTab.kt` | 24, 35–39 | `MockUserRepository` | `remember(testId)` fallback query if list empty |
| 7 | `ui/screens/main/AppDrawer.kt` | 28, 44 | `MockUserRepository` | Null-coalescing `?: MockUserRepository.getUserProfile()` |
| 8 | `ui/screens/auth/UserSettingScreen.kt` | 22, 35, 64 | `MockUserRepository` | Direct read in `remember` and synchronous mutation on save |
| 9 | `ui/screens/auth/LoginScreen.kt` | 31, 52–53 | `MockUserRepository` | Direct read and synchronous mutation on login button click |

All remaining UI screens (`HomeScreen.kt`, `TestsScreen.kt`, `TestListScreen.kt`, `UpdatesScreen.kt`, `PassScreen.kt`, `PrivacyPolicyScreen.kt`, `AnalysisTab.kt`, `SolutionsTab.kt`, `QuestionSolutionView.kt`, `SolutionsSectionDrawer.kt`) and dialog components were verified to be **100% clean** of mock repository references.

---

## 2. Detailed Findings & Refactoring Specifications

### 1. `TestSeriesDetailScreen.kt`
- **Location:** `app/src/main/java/com/example/abhyaas/ui/screens/tests/TestSeriesDetailScreen.kt`
- **Observed Code:**
  - Line 28:
    ```kotlin
    import com.example.abhyaas.data.mock.MockExamRepository
    ```
  - Lines 60–62:
    ```kotlin
    val series = uiState.series ?: remember(seriesId) {
        MockExamRepository.getTestSeriesById(seriesId) ?: MockExamRepository.getTestSeriesList().first()
    }
    ```
- **Architectural Flaw:**
  Bypasses `TestSeriesDetailViewModel.uiState`. When navigating to the screen, `uiState.series` is initially null while loading; instead of showing a loading state, it immediately calls the singleton `MockExamRepository`.
- **Refactoring Solution:**
  1. Remove `import com.example.abhyaas.data.mock.MockExamRepository`.
  2. Remove `remember(seriesId)` block.
  3. Guard screen content with loading/error UI:
     ```kotlin
     if (uiState.isLoading || uiState.series == null) {
         Box(
             modifier = Modifier
                 .fillMaxSize()
                 .background(DarkBackground),
             contentAlignment = Alignment.Center
         ) {
             CircularProgressIndicator(color = BrandAccentCyan)
         }
         return
     }
     val series = uiState.series
     ```
  4. TopAppBar title and subtitle should bind dynamically to `series.title` and `series.subtitle`.
  5. Content renders exclusively from `uiState`.

---

### 2. `UserProfileScreen.kt`
- **Location:** `app/src/main/java/com/example/abhyaas/ui/screens/profile/UserProfileScreen.kt`
- **Observed Code:**
  - Line 31:
    ```kotlin
    import com.example.abhyaas.data.mock.MockUserRepository
    ```
  - Lines 53, 55:
    ```kotlin
    val userProfile = uiState.profile ?: remember { MockUserRepository.getUserProfile() }
    var selectedTab by remember { mutableStateOf(PrepTrackerTab.QUESTIONS) }
    val dataPoints = if (uiState.trendDataPoints.isNotEmpty()) uiState.trendDataPoints else remember { MockUserRepository.getPreparationDataPoints("Questions") }
    ```
- **Architectural Flaw:**
  Directly accesses mock repository singleton methods synchronously inside `remember` blocks whenever `uiState.profile` or `uiState.trendDataPoints` are empty/loading.
- **Refactoring Solution:**
  1. Remove `import com.example.abhyaas.data.mock.MockUserRepository`.
  2. Remove lines 53 and 55.
  3. Render a loading indicator when `uiState.isLoading && uiState.profile == null`:
     ```kotlin
     if (uiState.isLoading && uiState.profile == null) {
         Box(
             modifier = Modifier
                 .fillMaxSize()
                 .background(DarkBackground),
             contentAlignment = Alignment.Center
         ) {
             CircularProgressIndicator(color = BrandAccentCyan)
         }
         return
     }
     val userProfile = uiState.profile ?: UserProfile(fullName = "User", email = "", mobileNumber = "")
     val dataPoints = uiState.trendDataPoints
     ```
  4. Connect `PrepTrackerTab` clicks to the ViewModel:
     ```kotlin
     onTabSelected = { tab ->
         selectedTab = tab
         val metric = when(tab) {
             PrepTrackerTab.ACCURACY -> "Accuracy"
             PrepTrackerTab.TIME_SPENT -> "Time"
             PrepTrackerTab.QUESTIONS -> "Questions"
         }
         viewModel.selectTrendMetric(metric)
     }
     ```
  5. UI reads `uiState.trendDataPoints` and `uiState.profile` exclusively.

---

### 3. `ActiveTestScreen.kt`
- **Location:** `app/src/main/java/com/example/abhyaas/ui/screens/exam/ActiveTestScreen.kt`
- **Observed Code:**
  - Line 37:
    ```kotlin
    import com.example.abhyaas.data.mock.MockExamRepository
    ```
  - Lines 68–72:
    ```kotlin
    val fallbackTest = remember(testId) {
        MockExamRepository.getTestById(testId)
            ?: MockExamRepository.getTestsForSubCategory("nayab_tehsildar_2026", "Paper 1: Full Mock Tests (GK + Reasoning)").first()
    }
    val test = uiState.test ?: fallbackTest
    ```
- **Architectural Flaw:**
  `ActiveTestViewModel` already loads tests and questions from `examRepo` and `questionRepo`. `fallbackTest` in `ActiveTestScreen` queries `MockExamRepository` directly on the main thread in a `remember` block.
- **Refactoring Solution:**
  1. Remove `import com.example.abhyaas.data.mock.MockExamRepository`.
  2. Remove lines 68–72 (`fallbackTest`).
  3. Update line 78 to handle loading/uninitialized test state cleanly:
     ```kotlin
     if (uiState.isLoading || uiState.test == null) {
         Box(
             modifier = Modifier
                 .fillMaxSize()
                 .background(Color(0xFF0B111A)),
             contentAlignment = Alignment.Center
         ) {
             CircularProgressIndicator(color = BrandPrimary)
         }
         return
     }
     val test = uiState.test
     ```
  4. Derive `allQuestions` strictly from `uiState.questions`:
     ```kotlin
     val allQuestions: List<Question> = remember(uiState.questions, test) {
         if (uiState.questions.isNotEmpty()) uiState.questions else test.sections.flatMap { it.questions }
     }
     ```

---

### 4. `TestInstructionsScreen.kt`
- **Location:** `app/src/main/java/com/example/abhyaas/ui/screens/exam/TestInstructionsScreen.kt`
- **Observed Code:**
  - Line 22:
    ```kotlin
    import com.example.abhyaas.data.mock.MockExamRepository
    ```
  - Lines 39–41:
    ```kotlin
    val test = remember(testId) {
        MockExamRepository.getTestById(testId) ?: MockExamRepository.getTestsForSubCategory("ssc_selection_post_2026", "Exam Day Special").first()
    }
    ```
- **Architectural Flaw:**
  `TestInstructionsScreen` does not observe any ViewModel and directly loads test instructions from `MockExamRepository` inside `remember(testId)`.
- **Refactoring Solution:**
  1. Remove `import com.example.abhyaas.data.mock.MockExamRepository`.
  2. Introduce a dedicated `TestInstructionsViewModel` (or inject `ActiveTestViewModel`):
     ```kotlin
     data class TestInstructionsUiState(
         val isLoading: Boolean = true,
         val test: Test? = null,
         val error: String? = null
     )

     class TestInstructionsViewModel(
         private val examRepo: ExamRepository = AbhyaasApplication.instance.examRepository
     ) : ViewModel() {
         private val _uiState = MutableStateFlow(TestInstructionsUiState())
         val uiState: StateFlow<TestInstructionsUiState> = _uiState.asStateFlow()

         fun loadTest(testId: String) {
             viewModelScope.launch {
                 _uiState.value = _uiState.value.copy(isLoading = true, error = null)
                 examRepo.getTestById(testId)
                     .onSuccess { test ->
                         _uiState.value = TestInstructionsUiState(isLoading = false, test = test)
                     }
                     .onFailure { e ->
                         _uiState.value = TestInstructionsUiState(isLoading = false, error = e.message)
                     }
             }
         }
     }
     ```
  3. In `TestInstructionsScreen`:
     ```kotlin
     @Composable
     fun TestInstructionsScreen(
         testId: String = "ssc_test_day_02",
         onBackClick: () -> Unit = {},
         onAgreeAndContinue: (testId: String) -> Unit = {},
         onGetPassClick: () -> Unit = {},
         viewModel: TestInstructionsViewModel = viewModel()
     ) {
         val uiState by viewModel.uiState.collectAsStateWithLifecycle()

         LaunchedEffect(testId) {
             viewModel.loadTest(testId)
         }

         if (uiState.isLoading || uiState.test == null) {
             Box(
                 modifier = Modifier.fillMaxSize().background(DarkBackground),
                 contentAlignment = Alignment.Center
             ) {
                 CircularProgressIndicator(color = BrandPrimary)
             }
             return
         }
         val test = uiState.test
         ...
     ```

---

### 5. `TestResultScreen.kt`
- **Location:** `app/src/main/java/com/example/abhyaas/ui/screens/result/TestResultScreen.kt`
- **Observed Code:**
  - Line 20:
    ```kotlin
    import com.example.abhyaas.data.mock.MockExamRepository
    ```
  - Lines 47–54:
    ```kotlin
    val test: Test = uiState.test ?: remember(testId) {
        MockExamRepository.getTestById(testId)
            ?: MockExamRepository.getTestsForSubCategory("ssc_selection_post_2026", "Exam Day Special").first()
    }

    val testResult: TestResult = uiState.result ?: remember(testId) {
        MockExamRepository.getPreviousAttemptResult(testId)
    }
    ```
- **Architectural Flaw:**
  Even though `TestResultViewModel` is provided and exposes `uiState.test` and `uiState.result`, the composable immediately falls back to querying `MockExamRepository` synchronously.
- **Refactoring Solution:**
  1. Remove `import com.example.abhyaas.data.mock.MockExamRepository`.
  2. Remove lines 47–54.
  3. Guard UI with loading state:
     ```kotlin
     if (uiState.isLoading || uiState.test == null || uiState.result == null) {
         Box(
             modifier = modifier
                 .fillMaxSize()
                 .background(DarkBackground),
             contentAlignment = Alignment.Center
         ) {
             CircularProgressIndicator(color = BrandPrimary)
         }
         return
     }
     val test: Test = uiState.test
     val testResult: TestResult = uiState.result
     ```
  4. TopAppBar title displays `test.title`. Sub-tabs receive `test`, `testResult`, and `uiState.leaderboard`.

---

### 6. `LeaderboardTab.kt`
- **Location:** `app/src/main/java/com/example/abhyaas/ui/screens/result/LeaderboardTab.kt`
- **Observed Code:**
  - Line 24:
    ```kotlin
    import com.example.abhyaas.data.mock.MockUserRepository
    ```
  - Lines 35–39:
    ```kotlin
    val currentLeaderboard = if (leaderboard.isNotEmpty()) {
        leaderboard
    } else {
        remember(testId) { MockUserRepository.getLeaderboard(testId) }
    }
    ```
- **Architectural Flaw:**
  Directly invokes `MockUserRepository.getLeaderboard(testId)` whenever the passed `leaderboard` parameter is empty.
- **Refactoring Solution:**
  1. Remove `import com.example.abhyaas.data.mock.MockUserRepository`.
  2. Replace lines 35–39 with:
     ```kotlin
     val currentLeaderboard = leaderboard
     ```
  3. If `currentLeaderboard.isEmpty()`, display a centered empty state:
     ```kotlin
     if (currentLeaderboard.isEmpty()) {
         Box(
             modifier = modifier
                 .fillMaxSize()
                 .background(DarkBackground),
             contentAlignment = Alignment.Center
         ) {
             Text(
                 text = "No leaderboard data available",
                 color = TextSecondaryDark,
                 fontSize = 14.sp
             )
         }
         return
     }
     ```

---

### 7. `AppDrawer.kt`
- **Location:** `app/src/main/java/com/example/abhyaas/ui/screens/main/AppDrawer.kt`
- **Observed Code:**
  - Line 28:
    ```kotlin
    import com.example.abhyaas.data.mock.MockUserRepository
    ```
  - Line 44:
    ```kotlin
    val userProfile = uiState.profile ?: MockUserRepository.getUserProfile()
    ```
- **Architectural Flaw:**
  Calls `MockUserRepository.getUserProfile()` as a null fallback when `uiState.profile` is null.
- **Refactoring Solution:**
  1. Remove `import com.example.abhyaas.data.mock.MockUserRepository`.
  2. Replace line 44 with:
     ```kotlin
     val userProfile = uiState.profile
     ```
  3. Safely handle nullable `userProfile` in lines 94 and 112:
     ```kotlin
     text = userProfile?.fullName?.ifBlank { "Add Your Name" } ?: "Add Your Name"
     ```
     and
     ```kotlin
     text = userProfile?.mobileNumber ?: ""
     ```
     Or define an in-memory UI default model:
     ```kotlin
     val userProfile = uiState.profile ?: UserProfile(fullName = "Add Your Name", email = "", mobileNumber = "")
     ```

---

### 8. `UserSettingScreen.kt`
- **Location:** `app/src/main/java/com/example/abhyaas/ui/screens/auth/UserSettingScreen.kt`
- **Observed Code:**
  - Line 22:
    ```kotlin
    import com.example.abhyaas.data.mock.MockUserRepository
    ```
  - Line 35:
    ```kotlin
    val existingProfile = remember { MockUserRepository.getUserProfile() }
    ```
  - Line 64:
    ```kotlin
    MockUserRepository.updateUserProfile(updated)
    ```
- **Architectural Flaw:**
  Directly reads `MockUserRepository.getUserProfile()` and executes synchronous write `MockUserRepository.updateUserProfile(updated)` without a ViewModel.
- **Refactoring Solution:**
  1. Remove `import com.example.abhyaas.data.mock.MockUserRepository`.
  2. Inject `viewModel: UserProfileViewModel = viewModel()` into `UserSettingScreen`.
  3. Collect `val uiState by viewModel.uiState.collectAsStateWithLifecycle()`.
  4. Form state variables initialize to empty/default and synchronize when `uiState.profile` loads:
     ```kotlin
     LaunchedEffect(uiState.profile) {
         uiState.profile?.let { profile ->
             fullName = profile.fullName
             email = profile.email
             mobileNumber = profile.mobileNumber
             dateOfBirth = profile.dateOfBirth ?: ""
             selectedCategory = profile.category ?: "General"
             pinCode = profile.pinCode ?: ""
             selectedEducation = profile.education
         }
     }
     ```
  5. Refactor `handleSave()`:
     ```kotlin
     val baseProfile = uiState.profile ?: UserProfile(fullName = "", email = "", mobileNumber = "")
     val updated = baseProfile.copy(
         fullName = fullName.ifBlank { "Aspirant" },
         name = fullName.ifBlank { "Aspirant" },
         email = email.ifBlank { "aspirant@abhyaas.edu" },
         mobileNumber = mobileNumber.ifBlank { "+91 98765 43210" },
         dateOfBirth = dateOfBirth.ifBlank { "15/08/2000" },
         category = selectedCategory,
         pinCode = pinCode.ifBlank { "462001" },
         education = selectedEducation,
         educationQualification = selectedEducation
     )
     viewModel.updateProfile(updated) { success ->
         if (success) onCreateAccountSuccess()
     }
     ```

---

### 9. `LoginScreen.kt`
- **Location:** `app/src/main/java/com/example/abhyaas/ui/screens/auth/LoginScreen.kt`
- **Observed Code:**
  - Line 31:
    ```kotlin
    import com.example.abhyaas.data.mock.MockUserRepository
    ```
  - Lines 52–55:
    ```kotlin
    val currentProfile = MockUserRepository.getUserProfile()
    MockUserRepository.updateUserProfile(
        currentProfile.copy(mobileNumber = "+91 $trimmed")
    )
    ```
- **Architectural Flaw:**
  Directly accesses and updates `MockUserRepository` on clicking the login button.
- **Refactoring Solution:**
  1. Remove `import com.example.abhyaas.data.mock.MockUserRepository`.
  2. Inject `viewModel: UserProfileViewModel = viewModel()` into `LoginScreen`.
  3. In `handleContinue()`:
     ```kotlin
     val current = viewModel.uiState.value.profile ?: UserProfile(fullName = "Aspirant", email = "aspirant@abhyaas.edu", mobileNumber = "")
     viewModel.updateProfile(current.copy(mobileNumber = "+91 $trimmed")) {
         onContinueClick()
     }
     ```

---

## 3. ViewModel Constructor Parameter Injection

To satisfy the Gate Status requirement for testability and DI support, all ViewModels in `app/src/main/java/com/example/abhyaas/ui/viewmodel/` should support constructor injection with default parameters pointing to `AbhyaasApplication.instance`:

```kotlin
class ActiveTestViewModel(
    private val examRepo: ExamRepository = AbhyaasApplication.instance.examRepository,
    private val questionRepo: QuestionRepository = AbhyaasApplication.instance.questionRepository,
    private val resultRepo: TestResultRepository = AbhyaasApplication.instance.testResultRepository
) : ViewModel()

class HomeViewModel(
    private val examRepo: ExamRepository = AbhyaasApplication.instance.examRepository,
    private val userRepo: UserRepository = AbhyaasApplication.instance.userRepository
) : ViewModel()

class TestListViewModel(
    private val examRepo: ExamRepository = AbhyaasApplication.instance.examRepository
) : ViewModel()

class TestResultViewModel(
    private val examRepo: ExamRepository = AbhyaasApplication.instance.examRepository,
    private val resultRepo: TestResultRepository = AbhyaasApplication.instance.testResultRepository
) : ViewModel()

class TestSeriesDetailViewModel(
    private val examRepo: ExamRepository = AbhyaasApplication.instance.examRepository
) : ViewModel()

class TestsViewModel(
    private val examRepo: ExamRepository = AbhyaasApplication.instance.examRepository
) : ViewModel()

class UpdatesViewModel(
    private val updatesRepo: UpdatesRepository = AbhyaasApplication.instance.updatesRepository
) : ViewModel()

class UserProfileViewModel(
    private val userRepo: UserRepository = AbhyaasApplication.instance.userRepository
) : ViewModel()
```

---

## 4. Verification Method

Once implemented, verify with:

1. **Verify Complete Elimination of Mock Queries in UI Layer:**
   ```powershell
   git grep -E "Mock(Exam|User|Question|Updates)Repository" app/src/main/java/com/example/abhyaas/ui/
   ```
   *Expected Result: 0 matches.*

2. **Verify Kotlin Debug Compilation:**
   ```powershell
   .\gradlew compileDebugKotlin
   ```
   *Expected Result: BUILD SUCCESSFUL.*

3. **Verify Debug APK Assembly:**
   ```powershell
   .\gradlew assembleDebug
   ```
   *Expected Result: BUILD SUCCESSFUL.*

4. **Verify Unit Tests:**
   ```powershell
   .\gradlew compileDebugUnitTestKotlin
   ```
   *Expected Result: BUILD SUCCESSFUL.*
