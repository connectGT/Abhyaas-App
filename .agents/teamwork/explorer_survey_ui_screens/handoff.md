# Handoff Report: UI Screens Survey & MVVM Dynamic Data Architecture

## 1. Observation
1. **HomeScreen** (`ui/screens/home/HomeScreen.kt`):
   - Lines 49–53: Dropdown exam name hardcoded to `"Nayab Tehsildar"`.
   - Lines 97–177: Static ABHYAS PASS banner with hardcoded titles and action buttons.
   - Lines 199–281: 3 static `Row` elements containing 6 hardcoded `CategoryGridCard` cards plus 1 full-width card for Current Affairs.
   - Screen takes no ViewModel parameter and observes no state flow.
2. **TestsScreen** (`ui/screens/tests/TestsScreen.kt`):
   - Lines 38–40: Directly calls `MockExamRepository.getTestSeriesList()` via `remember`.
   - Lines 75–177: Featured exam card has hardcoded strings ("FEATURED EXAM", "SSC SELECTION POST 2026", "600+ Total Tests...") and a static 3-dot row indicator.
   - Lines 214–302 & 340–419: Explicitly renders `primarySeries` and `secondarySeries` only, ignoring any additional test series.
   - Lines 304–337: Static quick action shortcuts that all route to `primarySeries.id`.
3. **TestSeriesDetailScreen** (`ui/screens/tests/TestSeriesDetailScreen.kt`):
   - Lines 50–52: Directly calls `MockExamRepository.getTestSeriesById(seriesId)` via `remember(seriesId)`.
   - Lines 205–294: Hardcoded metrics strings (`"240"`, `"48"`, `"0%"`).
   - Lines 421–464: Hardcoded `mockFolders` list of 6 `FolderItemUi` objects, ignoring `series.mockFolders`.
   - Lines 477–506: Hardcoded `pypFolders` list of 4 `FolderItemUi` objects, ignoring `series.pypFolders`.
   - Lines 519–548: Hardcoded `studyNotesFolders` list of 4 `FolderItemUi` objects.
4. **UpdatesScreen** (`ui/screens/updates/UpdatesScreen.kt`):
   - Lines 38–40: Directly calls `MockUpdatesRepository.getUpdatesByCategory(selectedCategory)` via `remember(selectedCategory)`.
   - Lines 46–54: Hardcoded category list pairs.
   - Screen does not observe `UpdatesViewModel.uiState` or show loading/error indicators.
5. **UserProfileScreen** (`ui/screens/profile/UserProfileScreen.kt`):
   - Lines 47–49: Directly calls `MockUserRepository.getUserProfile()` and `MockUserRepository.getPreparationDataPoints("Questions")` via `remember`.
   - Line 61: Hardcoded `examName = "SSC CGL"`.
   - Lines 250–268: Summary metrics bound to initial static user profile snapshot.
   - No `ProfileViewModel` exists in `ui/viewmodel/`.
6. **ActiveTestScreen** (`ui/screens/exam/ActiveTestScreen.kt`):
   - Lines 58–65: Directly fetches test from `MockExamRepository.getTestById(testId)`.
   - Lines 67–86: All state (remaining time, question statuses, selections, bookmarks) kept in composable-scoped `remember`/`rememberSaveable`.
   - Lines 98–111: Timer ticker loop runs in UI coroutine scope (`LaunchedEffect(isPaused)`).
   - Lines 703–706: On exam submission, `onSubmitTest(test.id)` is invoked without persisting answers or delegating to `ActiveTestViewModel.submitTest()`.
7. **AppNavHost & MainScreen** (`ui/navigation/AppNavHost.kt`, `ui/screens/main/MainScreen.kt`):
   - Screens are invoked without passing ViewModel instances.
   - Compose runtime dependencies `androidx.lifecycle:lifecycle-viewmodel-compose:2.8.7` and `androidx.lifecycle:lifecycle-runtime-compose:2.8.7` are already in `app/build.gradle.kts` (lines 62–63).

## 2. Logic Chain
1. Based on Observation 7, the project already includes the required Compose Lifecycle libraries, making `collectAsStateWithLifecycle()` and `viewModel()` immediately usable without modifying `build.gradle.kts`.
2. Based on Observations 1, 2, 3, 4, 5, and 6, each screen currently instantiates its data statically or from mock singleton repositories directly in the Composable function using `remember { ... }`, rather than collecting from a StateFlow.
3. Based on Observations 1, 2, 3, 4, and 6, ViewModels (`HomeViewModel`, `TestsViewModel`, `TestSeriesDetailViewModel`, `UpdatesViewModel`, `ActiveTestViewModel`) already exist but are decoupled from their corresponding Composables.
4. Based on Observation 5, `ProfileViewModel` is completely missing and must be authored with a `ProfileUiState` to supply profile and trend analytics data.
5. In `TestSeriesDetailScreen` (Observation 3), the domain model `TestSeries` already exposes `mockFolders: List<TestSeriesFolder>` and `pypFolders: List<TestSeriesFolder>`, proving that the UI can replace its static folder lists directly with model-backed lists.
6. In `HomeScreen` (Observation 1), `HomeCategoryItem` lists are already supplied by `MockExamRepository.getHomeCategories()` and loaded in `HomeViewModel`, allowing static row layouts to be replaced by dynamic iteration over `uiState.categories`.
7. In `ActiveTestScreen` (Observation 6), connecting to `ActiveTestViewModel` will centralize exam state, enable correct timer ticks, and guarantee that test answers are submitted to `TestResultRepository`.

## 3. Caveats
- No caveats. The codebase was searched exhaustively, build configurations were inspected, and all 7 target screen/navigation files were reviewed line-by-line.

## 4. Conclusion
All 6 main UI screens require straightforward refactoring to:
1. Accept an optional ViewModel parameter with default `= viewModel()` to preserve call-site compatibility.
2. Observe state via `val uiState by viewModel.uiState.collectAsStateWithLifecycle()`.
3. Replace all hardcoded lists, folders, chips, and banners with dynamic iterations over ViewModel state (`LazyColumn`, `items`, `uiState.categories`, `uiState.series.mockFolders`, etc.).
4. Create `ProfileViewModel.kt` to power `UserProfileScreen`.
5. Delegate question state and test submission from `ActiveTestScreen` to `ActiveTestViewModel`.

Full specifications, exact line-by-line replacements, and state contracts are documented in `C:\Users\gurut\AndroidStudioProjects\Abhyaas\.agents\teamwork\explorer_survey_ui_screens\report.md`.

## 5. Verification Method
1. **File Inspection**:
   - Inspect `C:\Users\gurut\AndroidStudioProjects\Abhyaas\.agents\teamwork\explorer_survey_ui_screens\report.md` to review the line-by-line breakdown and UI State classes.
   - Verify presence of `collectAsStateWithLifecycle` in `app/build.gradle.kts` (line 63).
2. **Build Verification**:
   - Once implementers apply changes, verify compilation using:
     `.\gradlew assembleDebug`
   - Invalidation condition: If any screen fails to compile or retains hardcoded `FolderItemUi` or `MockExamRepository` calls in `remember`, the task is incomplete.
