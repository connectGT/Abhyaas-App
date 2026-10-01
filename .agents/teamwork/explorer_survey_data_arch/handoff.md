# Handoff Report: Data Architecture Survey & Target Design

**From:** `explorer_survey_data_arch`  
**To:** `orchestrator_2` / Implementer Agent  
**Date:** 2026-09-30  
**Status:** Investigation Complete (Hard Handoff)

---

## 1. Observation

1. **Dependencies and Gradle Configuration:**
   - `app/build.gradle.kts` lines 58–65 confirm Retrofit 2.11.0, OkHttp 4.12.0, Gson 2.11.0, `lifecycle-viewmodel-compose:2.8.7`, and `lifecycle-runtime-compose:2.8.7` are already included.
   - `app/build.gradle.kts` line 20 defines `buildConfigField("String", "BASE_URL", "\"https://api.abhyaas.app/v1/\"")`.
   - Tool execution `.\gradlew compileDebugKotlin` completed with code 0 (BUILD SUCCESSFUL).

2. **Domain Models (`data/model/`):**
   - 8 model files exist: `ExamUpdateItem.kt`, `LeaderboardEntry.kt`, `Question.kt`, `Test.kt`, `TestAttempt.kt`, `TestResult.kt`, `TestSeries.kt`, `UserProfile.kt`.
   - `TestSeries.kt` lines 20–34 defines `mockFolders` and `pypFolders`, but **lacks `studyNotesFolders: List<TestSeriesFolder>`** needed for Tab 2 ("Study Notes") of `TestSeriesDetailScreen`.
   - `HomeCategoryItem` in `TestSeries.kt` lines 12–18 lacks `subtitle` and `iconName` (which are currently hardcoded in `HomeScreen.kt`).

3. **Existing Repositories (`data/mock/` & `data/repository/`):**
   - `MockExamRepository.kt`, `MockQuestionRepository.kt`, `MockUpdatesRepository.kt`, and `MockUserRepository.kt` contain complete, rich mock data.
   - `data/repository/` defines 5 interfaces: `ExamRepository.kt`, `QuestionRepository.kt`, `TestResultRepository.kt`, `UpdatesRepository.kt`, `UserRepository.kt`.
   - `TestResultRepository.kt` lines 5–9 is missing `suspend fun getLeaderboard(testId: String): Result<List<LeaderboardEntry>>`.
   - `UserRepository.kt` lines 5–12 is missing `suspend fun getPreparationDataPoints(metric: String): Result<List<PreparationDataPoint>>`.
   - `data/repository/impl/` contains mock implementations, but remote implementations exist only for `RemoteExamRepositoryImpl.kt` and `RemoteUserRepositoryImpl.kt`.
   - `RemoteQuestionRepositoryImpl.kt`, `RemoteTestResultRepositoryImpl.kt`, and `RemoteUpdatesRepositoryImpl.kt` are completely missing.
   - `RemoteUserRepositoryImpl.kt` lines 12–52 has no mock fallback (it throws exceptions directly on HTTP/network error).

4. **Network Layer (`data/network/`):**
   - `RetrofitClient.kt` sets up an `OkHttpClient` with `HttpLoggingInterceptor`, timeouts, and auth header injection.
   - `ApiService.kt` lines 7–68 specifies 14 endpoints, but is missing `home/categories` and `user/preparation-trends`.
   - `Dtos.kt` lines 1–178 specifies base DTOs, but is missing `HomeCategoryDto`, `PreparationDataPointDto`, `notes_folders` in `TestSeriesDto`, and `section_breakdowns` in `TestResultDto`.

5. **UI Screen In-Composable Hardcoded Lists & ViewModel Disconnect:**
   - `HomeScreen.kt` line 31: `fun HomeScreen(...)` does not accept `HomeViewModel` or observe state. Lines 200–280 hardcode 6 `CategoryGridCard` composables in static `Row` blocks.
   - `TestsScreen.kt` lines 38–40: calls `remember { MockExamRepository.getTestSeriesList() }` directly without using `TestsViewModel`. Lines 311–337 hardcode quick shortcuts; primary and secondary series are statically split.
   - `TestSeriesDetailScreen.kt` lines 421–464: `mockFolders = listOf(FolderItemUi(title = "6 Exam Day Special", ...))` is hardcoded in the composable. Lines 477–500: `pypFolders = listOf(FolderItemUi(...))` is hardcoded in the composable. It does not use `TestSeriesDetailViewModel`.
   - `TestListScreen.kt` line 54: calls `remember(seriesId, subCategory) { MockExamRepository.getTestsForSubCategory(seriesId, subCategory) }`. No ViewModel exists.
   - `UpdatesScreen.kt` line 38: calls `remember(selectedCategory) { MockUpdatesRepository.getUpdatesByCategory(selectedCategory) }` directly without using `UpdatesViewModel`.
   - `UserProfileScreen.kt` lines 47–49: calls `MockUserRepository.getUserProfile()` and `MockUserRepository.getPreparationDataPoints("Questions")` directly. No ViewModel exists.
   - `UserSettingScreen.kt` lines 35 & 64: calls `MockUserRepository.getUserProfile()` and `MockUserRepository.updateUserProfile(updated)` directly.
   - `ActiveTestScreen.kt` lines 58–61: calls `MockExamRepository.getTestById(testId)` directly. Does not use `ActiveTestViewModel`.
   - `TestResultScreen.kt` lines 37–44: calls `MockExamRepository.getTestById(testId)` and `MockExamRepository.getPreviousAttemptResult(testId)` directly. Does not use `TestResultViewModel`.
   - `LeaderboardTab.kt` lines 34–46: contains a static hardcoded `listOf(LeaderboardEntry(...))` inside the composable `remember` block.
   - `AppDrawer.kt` line 38: calls `MockUserRepository.getUserProfile()` directly.

---

## 2. Logic Chain

1. **Premise 1:** The user's latest requirement (`ORIGINAL_REQUEST.md`) states:
   - "Finish implementing the MVVM + Retrofit layer for the app. This includes defining all necessary DTOs, Retrofit ApiService interfaces, Repository implementations (mock/remote fallback), and ViewModels using StateFlow to hold UI states."
   - "Refactor all main Compose UI screens (Home, Tests, Test Series Detail, Updates, Profile, Active Test) to consume data from their respective ViewModels. Remove all hardcoded, static UI lists (like the test folders, exams, quick actions, and updates) and replace them with dynamic iterations over the ViewModel state."
2. **Inference 1 (Observation 1 & 4):** Retrofit dependencies are present and the app compiles, but the network layer cannot be fully deployed until the missing DTOs (`HomeCategoryDto`, `PreparationDataPointDto`, `SectionResultDto`) and endpoints (`home/categories`, `user/preparation-trends`) are added to `ApiService.kt` and `Dtos.kt`.
3. **Inference 2 (Observation 3):** To satisfy the acceptance criterion of "mock/remote fallback" without crashes when testing offline or without a live backend server, every repository must implement a `safeApiCallWithFallback` pattern that attempts the Retrofit API call and seamlessly degrades to the existing `data/mock/` data on any exception or non-2xx response.
4. **Inference 3 (Observation 3):** Two repository interfaces must be updated:
   - `TestResultRepository` must include `getLeaderboard(testId: String): Result<List<LeaderboardEntry>>`.
   - `UserRepository` must include `getPreparationDataPoints(metric: String): Result<List<PreparationDataPoint>>`.
5. **Inference 4 (Observation 5):** Two new ViewModels are required:
   - `TestListViewModel` for `TestListScreen.kt`.
   - `UserProfileViewModel` for `UserProfileScreen.kt` and `UserSettingScreen.kt`.
6. **Inference 5 (Observation 5):** The acceptance criteria explicitly require:
   - Screens must observe state using `collectAsStateWithLifecycle()`.
   - Hardcoded in-composable lists (especially `mockFolders` and `pypFolders` in `TestSeriesDetailScreen`, categories in `HomeScreen`, and leaderboard in `LeaderboardTab`) must be replaced with iterations over ViewModel state collections.

---

## 3. Caveats

- **Network Mode:** The current project does not have a live backend server deployed at `https://api.abhyaas.app/v1/`. Therefore, the remote-with-mock-fallback strategy is essential: all network code is executed syntactically and exercised by Retrofit, while the fallback ensures zero runtime disruption.
- **Image URLs:** Avatars and icons in mock data currently use placeholder URLs or local vector resources. The DTOs support `avatar_url` as optional/nullable strings.
- **Read-Only Scope:** No source files were modified during this investigation. All findings and designs are documented in `report.md` and this handoff.

---

## 4. Conclusion

The data layer design is fully specified and ready for implementation. The architectural roadmap requires 4 sequential execution steps:
1. **Extend Models, DTOs & ApiService:** Update `TestSeries.kt`, `TestSeriesDto`, `TestResultDto`, add `HomeCategoryDto` and `PreparationDataPointDto`, and finalize `ApiService.kt`.
2. **Implement Unified Repositories with Mock Fallback:** Add `RemoteQuestionRepositoryImpl`, `RemoteTestResultRepositoryImpl`, and `RemoteUpdatesRepositoryImpl`; update `RemoteExamRepositoryImpl` and `RemoteUserRepositoryImpl` with fallback; update `AbhyaasApplication.kt` to wire remote repositories.
3. **Implement Missing ViewModels & Update StateFlows:** Add `TestListViewModel` and `UserProfileViewModel`; ensure all ViewModels expose comprehensive UiState data classes with `StateFlow`.
4. **Refactor UI Screens to Dynamic Observation:** Bind all screens to their ViewModels using `collectAsStateWithLifecycle()` and remove all static hardcoded lists.

Full technical details, code contracts, and mapping tables are available in:
`C:\Users\gurut\AndroidStudioProjects\Abhyaas\.agents\teamwork\explorer_survey_data_arch\report.md`

---

## 5. Verification Method

1. **Verify Report & Handoff Existence:**
   - Inspect `report.md` and `handoff.md` in `C:\Users\gurut\AndroidStudioProjects\Abhyaas\.agents\teamwork\explorer_survey_data_arch\`.
2. **Independent Verification Commands:**
   - Execute:
     ```powershell
     .\gradlew compileDebugKotlin
     ```
     Confirms current codebase builds cleanly.
3. **Subsequent Implementation Verification:**
   - Run:
     ```powershell
     .\gradlew assembleDebug
     ```
   - Run grep check to verify no hardcoded lists remain in `TestSeriesDetailScreen.kt`:
     ```powershell
     Select-String -Path "app\src\main\java\com\example\abhyaas\ui\screens\tests\TestSeriesDetailScreen.kt" -Pattern "FolderItemUi"
     ```
     (Must yield 0 occurrences after implementation).
   - Run grep check to verify `collectAsStateWithLifecycle` is used in all primary screens:
     ```powershell
     Select-String -Path "app\src\main\java\com\example\abhyaas\ui\screens\**\*.kt" -Pattern "collectAsStateWithLifecycle"
     ```
