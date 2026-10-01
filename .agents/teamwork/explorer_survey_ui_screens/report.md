# UI Screens MVVM Survey & Migration Report

## 1. Executive Summary

This investigation analyzed the Jetpack Compose UI architecture of the **Abhyaas** application to identify all hardcoded UI components, static mock lists, decoupled states, and ViewModel integration points across the main application screens:
1. `ui/screens/home/HomeScreen.kt`
2. `ui/screens/tests/TestsScreen.kt`
3. `ui/screens/tests/TestSeriesDetailScreen.kt`
4. `ui/screens/updates/UpdatesScreen.kt`
5. `ui/screens/profile/UserProfileScreen.kt`
6. `ui/screens/exam/ActiveTestScreen.kt`
7. `ui/navigation/AppNavHost.kt` and `ui/screens/main/MainScreen.kt`

### Key Architectural Findings:
- **Direct Repository Bypassing**: Multiple screens (`TestsScreen`, `TestSeriesDetailScreen`, `UpdatesScreen`, `UserProfileScreen`, `ActiveTestScreen`) directly invoke `MockExamRepository`, `MockUpdatesRepository`, or `MockUserRepository` inside `remember { ... }` blocks rather than observing StateFlow from ViewModels.
- **Existing ViewModels Underutilized**: ViewModels already exist for `HomeViewModel`, `TestsViewModel`, `TestSeriesDetailViewModel`, `UpdatesViewModel`, and `ActiveTestViewModel`, but none of the Compose UI screens are currently wired to consume them.
- **Missing ViewModel**: `UserProfileScreen` has no corresponding ViewModel (`ProfileViewModel` does not exist in `ui/viewmodel/`).
- **Hardcoded Screen Lists**: `TestSeriesDetailScreen` manually hardcodes folder lists (`mockFolders`, `pypFolders`, `studyNotesFolders`) using local UI models, ignoring the `TestSeriesFolder` items present in `TestSeries`. Similarly, `HomeScreen` manually defines rows of categories rather than iterating dynamically over `HomeCategoryItem`.
- **Active Test State Decoupled**: `ActiveTestScreen` manages its question palette, answers, and countdown timer in local composable `rememberSaveable` and `remember` maps, failing to delegate to `ActiveTestViewModel` or submit results to `TestResultRepository`.
- **Runtime Dependencies Ready**: Both `androidx.lifecycle:lifecycle-viewmodel-compose:2.8.7` and `androidx.lifecycle:lifecycle-runtime-compose:2.8.7` are already included in `app/build.gradle.kts`, enabling `collectAsStateWithLifecycle()` and `viewModel()` out of the box.

---

## 2. Detailed Survey of Screen Implementations

### 2.1 `HomeScreen.kt`
- **File**: `app/src/main/java/com/example/abhyaas/ui/screens/home/HomeScreen.kt` (352 lines)
- **Current Signature**:
  ```kotlin
  @Composable
  fun HomeScreen(
      onMenuClick: () -> Unit = {},
      onAvatarClick: () -> Unit = {},
      onPassClick: () -> Unit = {},
      onCategoryClick: (String) -> Unit = {},
      onSearchClick: () -> Unit = {}
  )
  ```
- **Hardcoded Elements Observed**:
  1. **Exam Dropdown Title** (lines 49–53): TopAppBar dropdown has hardcoded `examName = "Nayab Tehsildar"`.
  2. **Hero Banner** (lines 97–177): Static "ABHYAS PASS", "One Pass for All Exams", "Know More →", "Get Pass →". Banner state is not bound to user subscription or target exam.
  3. **Hardcoded Category Cards** (lines 199–281):
     - 3 manual `Row` layouts containing 6 cards:
       - Row 1: Study Notes (`cat_notes`, badge "NEW"), Previous Year Papers (`cat_pyq`).
       - Row 2: Practice Section (`cat_practice`), Live Tests & Quizzes (`cat_live`).
       - Row 3: Daily Live Classes (`cat_classes`), Quiz Section (`cat_quiz`).
     - 1 full-width card: Current Affairs (`cat_current_affairs`).
  4. **AI Modal Bottom Sheet** (lines 287–350): Static prompt text and hardcoded navigation to `"cat_practice"`.
  5. **No State / Lifecycle Handling**: No loading spinner, empty placeholder, or error handling.
- **ViewModel Alignment**:
  - `HomeViewModel` (`ui/viewmodel/HomeViewModel.kt`) already fetches categories (`examRepo.getHomeCategories()`), test series (`examRepo.getTestSeriesList()`), and profile (`userRepo.getUserProfile()`).
  - `HomeUiState` should drive the exam name, user profile info, and category grid.

---

### 2.2 `TestsScreen.kt`
- **File**: `app/src/main/java/com/example/abhyaas/ui/screens/tests/TestsScreen.kt` (485 lines)
- **Current Signature**:
  ```kotlin
  @Composable
  fun TestsScreen(
      onMenuClick: () -> Unit = {},
      onAvatarClick: () -> Unit = {},
      onTestSeriesClick: (String) -> Unit = {},
      onSearchClick: () -> Unit = {}
  )
  ```
- **Hardcoded Elements Observed**:
  1. **Direct Repository Access** (lines 38–40):
     ```kotlin
     val testSeriesList = remember { MockExamRepository.getTestSeriesList() }
     val primarySeries = testSeriesList.firstOrNull() ?: MockExamRepository.getTestSeriesById("nayab_tehsildar_2026")!!
     val secondarySeries = testSeriesList.getOrNull(1)
     ```
  2. **TopAppBar Exam Name** (line 51): Hardcoded `examName = "SSC CGL"`.
  3. **Featured Carousel Card** (lines 75–177):
     - Hardcoded strings: "FEATURED EXAM", "Exam: Sep - 2026", "SSC SELECTION POST 2026", "600+ Total Tests • 30 Full Tests • 90+ PYQs • 3000+ Vacancies".
     - Static 3-dot carousel indicator (lines 139–158): not linked to a `HorizontalPager`.
  4. **Enrolled Series Card** (lines 214–302):
     - Hardcoded badge: "SSC".
     - Hardcoded progress calculations: `1 / primarySeries.totalTests Attempted`, `0% Completed`, `progress = 1f / primarySeries.totalTests`.
  5. **Quick Action Shortcuts** (lines 304–337):
     - 4 hardcoded items ("Study Notes", "Live Test", "Live Quizzes", "Prev. Papers").
     - All 4 onClick handlers blindly call `onTestSeriesClick(primarySeries.id)`.
  6. **Secondary Series Card** (lines 340–419):
     - Only checks `if (secondarySeries != null)` and renders a single hardcoded card with "MP", "73 Posts • 600+ Practice Tests • Nov - 2026".
     - Any third or subsequent test series returned by the repository are completely ignored.
- **ViewModel Alignment**:
  - `TestsViewModel` (`ui/viewmodel/TestsViewModel.kt`) already fetches `List<TestSeries>`.
  - State should provide `enrolledSeries`, `featuredSeries`, and `availableSeries`.

---

### 2.3 `TestSeriesDetailScreen.kt`
- **File**: `app/src/main/java/com/example/abhyaas/ui/screens/tests/TestSeriesDetailScreen.kt` (702 lines)
- **Current Signature**:
  ```kotlin
  @Composable
  fun TestSeriesDetailScreen(
      seriesId: String = "nayab_tehsildar_2026",
      onBackClick: () -> Unit = {},
      onFolderClick: (seriesId: String, subCategory: String) -> Unit = { _, _ -> },
      onUnlockClick: () -> Unit = {},
      onAnnouncementClick: () -> Unit = {}
  )
  ```
- **Hardcoded Elements Observed**:
  1. **Direct Repository Access** (lines 50–52):
     ```kotlin
     val series = remember(seriesId) {
         MockExamRepository.getTestSeriesById(seriesId) ?: MockExamRepository.getTestSeriesList().first()
     }
     ```
  2. **Hardcoded Series Emblem/Abbreviation** (lines 54–63):
     ```kotlin
     when {
         seriesId.contains("mpseb") -> "MP"
         seriesId.contains("tehsildar") -> "MP"
         else -> "EX"
     }
     ```
  3. **Hardcoded Metrics Values** (lines 205–294):
     - PYQs tab: Total Papers = "240", Solved = "0", Progress = "0%".
     - Study Notes tab: Total Notes = "48", Read = "0", Progress = "0%".
     - Mock Tests tab: Total Tests = `series.totalTests`, Attempted = `series.attemptedCount`, Progress = "0%".
  4. **Hardcoded Folder Lists**:
     - **Mock Tests Folders** (lines 421–464): 6 static `FolderItemUi` entries ("6 Exam Day Special", "32 Most Saved Qs Subject Test", "2 Live Test", "30 Full Test (New Pattern)", "22 फटाफट Tricky Quant", "49 English Language").
       *Critical issue*: `series.mockFolders` already exists in `TestSeries` model but is ignored!
     - **PYQ Folders** (lines 477–506): 4 static `FolderItemUi` entries ("120 Previous Year Paper", "48 PYST (Matriculation Level)", "36 PYST (Higher Secondary Level)", "36 PYST (Graduation Level)").
       *Critical issue*: `series.pypFolders` exists in `TestSeries` model but is ignored!
     - **Study Notes Folders** (lines 519–548): 4 static `FolderItemUi` entries ("General Intelligence Study Notes", "Quantitative Aptitude Formula Book", "General Awareness Compendium", "English Grammar & Vocabulary").
- **ViewModel Alignment**:
  - `TestSeriesDetailViewModel` (`ui/viewmodel/TestSeriesDetailViewModel.kt`) has `loadSeries(seriesId)` and `TestSeriesDetailUiState(isLoading, series, tests, selectedSubCategory, error)`.
  - Folders and metrics must be dynamically populated from `uiState.series`.

---

### 2.4 `UpdatesScreen.kt`
- **File**: `app/src/main/java/com/example/abhyaas/ui/screens/updates/UpdatesScreen.kt` (408 lines)
- **Current Signature**:
  ```kotlin
  @Composable
  fun UpdatesScreen(
      onMenuClick: () -> Unit = {},
      onAvatarClick: () -> Unit = {},
      onSearchClick: () -> Unit = {}
  )
  ```
- **Hardcoded Elements Observed**:
  1. **Direct Repository Access** (lines 38–40):
     ```kotlin
     var selectedCategory by remember { mutableStateOf(UpdateCategory.ALL) }
     val updates = remember(selectedCategory) {
         MockUpdatesRepository.getUpdatesByCategory(selectedCategory)
     }
     ```
  2. **Filter Chips Tuple** (lines 46–54): Statically defines label pairs despite `UpdateCategory.displayName` existing on the enum.
  3. **No Loading or Error States**: Screen does not indicate when updates are being fetched or when network fails.
  4. **Unused ViewModel**: `UpdatesViewModel` (`ui/viewmodel/UpdatesViewModel.kt`) already supports `loadUpdates(category)` and `UpdatesUiState`, but is completely omitted.
- **ViewModel Alignment**:
  - Connect screen to `UpdatesViewModel`.
  - Observe `uiState by viewModel.uiState.collectAsStateWithLifecycle()`.
  - On chip click, invoke `viewModel.loadUpdates(cat)`.

---

### 2.5 `UserProfileScreen.kt`
- **File**: `app/src/main/java/com/example/abhyaas/ui/screens/profile/UserProfileScreen.kt` (491 lines)
- **Current Signature**:
  ```kotlin
  @Composable
  fun UserProfileScreen(
      onNavigateBack: () -> Unit = {},
      onEditProfile: () -> Unit = {},
      onPrivacyPolicyClick: () -> Unit = {}
  )
  ```
- **Hardcoded Elements Observed**:
  1. **Direct Repository Access** (lines 47–49):
     ```kotlin
     val userProfile = remember { MockUserRepository.getUserProfile() }
     var selectedTab by remember { mutableStateOf(PrepTrackerTab.QUESTIONS) }
     val dataPoints = remember { MockUserRepository.getPreparationDataPoints("Questions") }
     ```
  2. **TopAppBar Exam Name** (line 61): Hardcoded `examName = "SSC CGL"`.
  3. **Target Exam Text** (line 113): Hardcoded `"Target: SSC CGL 2026 Tier-1"`.
  4. **Missing ViewModel**: No `ProfileViewModel` exists in the codebase.
  5. **Static Trend Data**: Preparation chart data points do not dynamically refresh when switching between Accuracy, Time Spent, and Questions tabs.
- **ViewModel Alignment**:
  - Need to create `ProfileViewModel` and `ProfileUiState` to encapsulate profile loading, tab selection, and preparation trend fetching.

---

### 2.6 `ActiveTestScreen.kt`
- **File**: `app/src/main/java/com/example/abhyaas/ui/screens/exam/ActiveTestScreen.kt` (807 lines)
- **Current Signature**:
  ```kotlin
  @Composable
  fun ActiveTestScreen(
      testId: String = "ssc_test_day_02",
      onBackClick: () -> Unit = {},
      onSubmitTest: (testId: String) -> Unit = {}
  )
  ```
- **Hardcoded / State Decoupling Observed**:
  1. **Direct Repository Access** (lines 58–65):
     ```kotlin
     val test = remember(testId) {
         MockExamRepository.getTestById(testId)
             ?: MockExamRepository.getTestsForSubCategory("nayab_tehsildar_2026", "Paper 1: Full Mock Tests (GK + Reasoning)").first()
     }
     val allQuestions: List<Question> = remember(test) {
         test.sections.flatMap { it.questions }
     }
     ```
  2. **Composable-Scoped State Management** (lines 67–86):
     - `remainingSeconds`, `currentQuestionIndex`, `isPaused`, `isHindi` in `rememberSaveable`.
     - `selectedAnswers`, `questionStatusMap`, `perQuestionSeconds`, `bookmarkedQuestions`, `favoriteQuestions` in local `remember { mutableStateMapOf() }`.
  3. **In-UI Coroutine Timer** (lines 98–111): Runs a `while(!isPaused)` loop directly inside `LaunchedEffect`, prone to drift or cancel on recomposition/lifecycle changes.
  4. **Unsubmitted Answers** (lines 703–706):
     ```kotlin
     onConfirmSubmit = {
         showSubmitDialog = false
         onSubmitTest(test.id)
     }
     ```
     Selected answers and time taken are NEVER submitted to `TestResultRepository`!
- **ViewModel Alignment**:
  - `ActiveTestViewModel` (`ui/viewmodel/ActiveTestViewModel.kt`) already contains `loadTest(testId)`, `selectAnswer()`, `clearAnswer()`, `navigateToQuestion()`, `markForReview()`, `tickTimer()`, and `submitTest()`.
  - Connect screen to `ActiveTestViewModel` to manage question state, answer selections, palette statuses, and actual submission.

---

### 2.7 `AppNavHost.kt` and `MainScreen.kt`
- **Files**:
  - `app/src/main/java/com/example/abhyaas/ui/navigation/AppNavHost.kt` (290 lines)
  - `app/src/main/java/com/example/abhyaas/ui/screens/main/MainScreen.kt` (174 lines)
- **Invocation Architecture**:
  - `AppNavHost` handles top-level routing (Login, UserSetting, Main, UserProfile, PrivacyPolicy, TestSeriesDetail, TestList, TestInstructions, ActiveTest, TestResult).
  - Screens are currently invoked as bare composables without ViewModel injection:
    - `HomeScreen(onMenuClick = ..., onAvatarClick = ..., ...)`
    - `TestsScreen(onMenuClick = ..., onAvatarClick = ..., ...)`
    - `UpdatesScreen(onMenuClick = ..., onAvatarClick = ...)`
    - `UserProfileScreen(onNavigateBack = ..., onEditProfile = ...)`
    - `TestSeriesDetailScreen(seriesId = seriesId, ...)`
    - `ActiveTestScreen(testId = testId, ...)`
  - None of the destinations currently pass ViewModels down or instantiate them.

---

## 3. UI State Data Classes Specification

To cleanly separate presentation from business logic, each screen must observe a well-defined immutable UI state data class.

### 3.1 `HomeUiState` (in `HomeViewModel.kt`)
```kotlin
data class HomeUiState(
    val isLoading: Boolean = true,
    val selectedExamName: String = "Nayab Tehsildar",
    val categories: List<HomeCategoryItem> = emptyList(),
    val testSeriesList: List<TestSeries> = emptyList(),
    val userProfile: UserProfile? = null,
    val hasActivePass: Boolean = false,
    val error: String? = null
)
```

### 3.2 `TestsUiState` (in `TestsViewModel.kt`)
```kotlin
data class TestsUiState(
    val isLoading: Boolean = true,
    val selectedExamName: String = "SSC CGL",
    val testSeriesList: List<TestSeries> = emptyList(),
    val featuredSeries: List<TestSeries> = emptyList(),
    val enrolledSeries: List<TestSeries> = emptyList(),
    val departmentalSeries: List<TestSeries> = emptyList(),
    val error: String? = null
)
```

### 3.3 `TestSeriesDetailUiState` (in `TestSeriesDetailViewModel.kt`)
```kotlin
data class TestSeriesDetailUiState(
    val isLoading: Boolean = true,
    val series: TestSeries? = null,
    val selectedTabIndex: Int = 0,
    val mockFolders: List<TestSeriesFolder> = emptyList(),
    val pypFolders: List<TestSeriesFolder> = emptyList(),
    val studyNotesFolders: List<TestSeriesFolder> = emptyList(),
    val totalTests: Int = 0,
    val attemptedTests: Int = 0,
    val progressPercent: Int = 0,
    val error: String? = null
)
```

### 3.4 `UpdatesUiState` (in `UpdatesViewModel.kt`)
```kotlin
data class UpdatesUiState(
    val isLoading: Boolean = true,
    val updates: List<ExamUpdateItem> = emptyList(),
    val selectedCategory: UpdateCategory = UpdateCategory.ALL,
    val availableCategories: List<UpdateCategory> = UpdateCategory.entries,
    val error: String? = null
)
```

### 3.5 `ProfileUiState` (NEW in `ProfileViewModel.kt`)
```kotlin
data class ProfileUiState(
    val isLoading: Boolean = true,
    val userProfile: UserProfile? = null,
    val selectedTab: PrepTrackerTab = PrepTrackerTab.QUESTIONS,
    val dataPoints: List<PreparationDataPoint> = emptyList(),
    val targetExam: String = "SSC CGL 2026 Tier-1",
    val error: String? = null
)
```

### 3.6 `ActiveTestUiState` (in `ActiveTestViewModel.kt`)
```kotlin
data class ActiveTestUiState(
    val isLoading: Boolean = true,
    val test: Test? = null,
    val questions: List<Question> = emptyList(),
    val currentQuestionIndex: Int = 0,
    val currentSectionIndex: Int = 0,
    val selectedAnswers: Map<Int, Int> = emptyMap(),
    val questionStatuses: Map<Int, QuestionStatus> = emptyMap(),
    val bookmarkedQuestions: Set<Int> = emptySet(),
    val favoriteQuestions: Set<Int> = emptySet(),
    val timeRemainingSeconds: Int = 3600,
    val isPaused: Boolean = false,
    val isHindi: Boolean = false,
    val isSubmitted: Boolean = false,
    val error: String? = null
)
```

---

## 4. State Observation & Lifecycle Migration Strategy

### 4.1 Recommended Pattern: `collectAsStateWithLifecycle()`
All screens should use the official lifecycle-aware Compose collector:
```kotlin
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun ScreenName(
    viewModel: ScreenViewModel = viewModel(),
    // Navigation callbacks...
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    // Render UI based on uiState...
}
```
**Benefits**:
- Automatically pauses flow collection when the Composable/Activity is in the background (`Lifecycle.State.STARTED`), saving battery and CPU cycles.
- Eliminates memory leaks and lingering coroutine subscriptions.

### 4.2 Handling Tri-State UI (Loading / Content / Error)
Every screen should wrap its content with standardized state handling:
```kotlin
when {
    uiState.isLoading -> {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = BrandPrimary)
        }
    }
    uiState.error != null -> {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Error: ${uiState.error}", color = Color.Red)
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = { viewModel.retry() }) { Text("Retry") }
            }
        }
    }
    else -> {
        // Content rendering iterating over uiState data
    }
}
```

---

## 5. ViewModel Injection & Provision Architecture

### 5.1 Clean Service Locator + Default Composable Parameter Pattern
Because the app does not use Dagger-Hilt, ViewModels obtain repositories via `AbhyaasApplication.instance`:
```kotlin
class HomeViewModel : ViewModel() {
    private val examRepo = AbhyaasApplication.instance.examRepository
    private val userRepo = AbhyaasApplication.instance.userRepository
    // ...
}
```
In Compose, each screen can declare its ViewModel as a default parameter:
```kotlin
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel(),
    onMenuClick: () -> Unit = {},
    // ...
)
```
**Advantages**:
1. **Preserves existing callers**: Calls in `MainScreen.kt` and `AppNavHost.kt` remain valid without forcing changes to invocation call sites.
2. **Testable & Previewable**: Unit tests and `@Preview` composables can inject mock ViewModels or pass explicit state.
3. **Lifecycle-Scoped**: The `viewModel()` call automatically scopes the ViewModel to the `NavBackStackEntry` in `NavHost`.

### 5.2 Creation of `ProfileViewModel`
A new file `ui/viewmodel/ProfileViewModel.kt` must be created:
```kotlin
package com.example.abhyaas.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.abhyaas.AbhyaasApplication
import com.example.abhyaas.data.model.PreparationDataPoint
import com.example.abhyaas.data.model.UserProfile
import com.example.abhyaas.ui.screens.profile.PrepTrackerTab
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {
    private val userRepo = AbhyaasApplication.instance.userRepository

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init { loadProfile() }

    fun loadProfile() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            userRepo.getUserProfile()
                .onSuccess { profile ->
                    val dataPoints = com.example.abhyaas.data.mock.MockUserRepository.getPreparationDataPoints()
                    _uiState.value = ProfileUiState(
                        isLoading = false,
                        userProfile = profile,
                        dataPoints = dataPoints,
                        targetExam = profile.targetExam ?: "SSC CGL 2026 Tier-1"
                    )
                }
                .onFailure { e ->
                    _uiState.value = _uiState.value.copy(isLoading = false, error = e.message)
                }
        }
    }

    fun selectTab(tab: PrepTrackerTab) {
        _uiState.value = _uiState.value.copy(selectedTab = tab)
    }
}
```

---

## 6. Static List Replacements & Dynamic Rendering Matrix

| Screen | Hardcoded Static Block | Replacement Strategy | State Flow Source |
|---|---|---|---|
| **HomeScreen** | 3 static Rows of 6 `CategoryGridCard`s (lines 199–281) | Chunk `uiState.categories` into pairs: `categories.filter { it.id != "cat_current_affairs" }.chunked(2) { rowItems -> Row { ... } }` and render full-width card for remaining. | `HomeViewModel.uiState.categories` |
| **HomeScreen** | Hardcoded `examName = "Nayab Tehsildar"` (line 51) | Dynamic title from `uiState.selectedExamName ?: uiState.userProfile?.targetExam ?: "Abhyaas Exam"` | `HomeViewModel.uiState.selectedExamName` |
| **TestsScreen** | Static `primarySeries` & `secondarySeries` cards (lines 214–302, lines 340–419) | Dynamic list rendering using `LazyColumn` or `Column` with `items(uiState.enrolledSeries)` and `items(uiState.testSeriesList)`. | `TestsViewModel.uiState.testSeriesList` |
| **TestsScreen** | Static 3-dot Carousel indicator (lines 139–158) | Connect to `HorizontalPager` with `pagerState.currentPage` or dynamic dots based on `uiState.featuredSeries.size`. | `TestsViewModel.uiState.featuredSeries` |
| **TestSeriesDetailScreen** | Hardcoded `mockFolders` list of 6 items (lines 421–464) | Iterate over `uiState.series?.mockFolders` (which is populated with real `TestSeriesFolder` items). | `TestSeriesDetailViewModel.uiState.series.mockFolders` |
| **TestSeriesDetailScreen** | Hardcoded `pypFolders` list of 4 items (lines 477–506) | Iterate over `uiState.series?.pypFolders` (from `TestSeries.pypFolders`). | `TestSeriesDetailViewModel.uiState.series.pypFolders` |
| **TestSeriesDetailScreen** | Hardcoded Metrics (`"240"`, `"48"`, `"0%"`) (lines 205–294) | Compute metrics dynamically: `series.totalTests`, `series.attemptedCount`, and `"${(series.attemptedCount * 100 / series.totalTests.coerceAtLeast(1))}%"`. | `TestSeriesDetailViewModel.uiState.series` |
| **UpdatesScreen** | Direct `MockUpdatesRepository.getUpdatesByCategory(selectedCategory)` (lines 38–40) | Bind `items(uiState.updates, key = { it.id })` to ViewModel state. | `UpdatesViewModel.uiState.updates` |
| **UserProfileScreen** | Static `userProfile` and `dataPoints` in `remember` (lines 47–49) | Bind to `uiState.userProfile` and `uiState.dataPoints`. | `ProfileViewModel.uiState` |
| **ActiveTestScreen** | Local `allQuestions`, `selectedAnswers`, `questionStatusMap`, and local timer ticker | Bind questions, answer selections, and statuses to `ActiveTestViewModel.uiState`. Submit via `viewModel.submitTest()`. | `ActiveTestViewModel.uiState` |

---

## 7. Concrete Implementation Roadmap for Downstream Agents

### Phase 1: ViewModel & Data Layer Enhancements
1. **Create `ProfileViewModel.kt`**: Implement `ProfileUiState` and `ProfileViewModel` inside `ui/viewmodel/`.
2. **Refine `TestSeriesDetailViewModel.kt`**: Ensure `loadSeries(seriesId)` properly loads folders and exposes them in `TestSeriesDetailUiState`. Add default fallback folders in `MockExamRepository` if any series has empty folders.
3. **Enhance `ActiveTestViewModel.kt`**: Support section indexing, bookmarking, and timer callbacks so the UI can be cleanly driven by ViewModel actions.

### Phase 2: Refactoring Main Bottom-Bar Screens
1. **Refactor `HomeScreen.kt`**:
   - Inject `HomeViewModel = viewModel()`.
   - Observe `uiState by viewModel.uiState.collectAsStateWithLifecycle()`.
   - Dynamically render `uiState.categories` and pass/exam info.
2. **Refactor `TestsScreen.kt`**:
   - Inject `TestsViewModel = viewModel()`.
   - Observe `uiState by viewModel.uiState.collectAsStateWithLifecycle()`.
   - Replace static `primarySeries`/`secondarySeries` with dynamic iterations.
3. **Refactor `UpdatesScreen.kt`**:
   - Inject `UpdatesViewModel = viewModel()`.
   - Observe `uiState by viewModel.uiState.collectAsStateWithLifecycle()`.
   - Wire category filter chips to `viewModel.loadUpdates(cat)`.

### Phase 3: Refactoring Detail & Interactive Screens
1. **Refactor `TestSeriesDetailScreen.kt`**:
   - Inject `TestSeriesDetailViewModel = viewModel()`.
   - Trigger `LaunchedEffect(seriesId) { viewModel.loadSeries(seriesId) }`.
   - Replace hardcoded folder lists in all 3 tabs with iterations over `uiState.series.mockFolders`, `pypFolders`, etc.
2. **Refactor `UserProfileScreen.kt`**:
   - Inject `ProfileViewModel = viewModel()`.
   - Observe `uiState by viewModel.uiState.collectAsStateWithLifecycle()`.
   - Bind user stats, greetings, and chart data points.
3. **Refactor `ActiveTestScreen.kt`**:
   - Inject `ActiveTestViewModel = viewModel()`.
   - Trigger `LaunchedEffect(testId) { viewModel.loadTest(testId) }`.
   - Wire options selection, palette navigation, and submission confirmation to ViewModel functions.

### Phase 4: Verification & Compilation
1. Run `./gradlew assembleDebug` to verify no compilation errors.
2. Inspect that all main Compose UI screens are observing state with `collectAsStateWithLifecycle()`.
3. Verify that static lists have been completely eliminated.
