## 2026-09-30T14:58:52Z
You are worker_data_arch.
Working directory: C:\Users\gurut\AndroidStudioProjects\Abhyaas\.agents\teamwork\worker_data_arch
Your identity is: worker_data_arch

MANDATORY: Read C:\Users\gurut\AndroidStudioProjects\Abhyaas\.agents\teamwork\ORIGINAL_REQUEST.md before starting work (specifically the latest request under ## 2026-09-30T14:46:28Z).
Read C:\Users\gurut\AndroidStudioProjects\Abhyaas\.agents\teamwork\orchestrator_2\SCOPE.md for the milestone requirements.
Read C:\Users\gurut\AndroidStudioProjects\Abhyaas\.agents\teamwork\explorer_survey_data_arch\report.md and handoff.md for exact specifications.

MANDATORY INTEGRITY WARNING:
DO NOT CHEAT. All implementations must be genuine. DO NOT hardcode test results, create dummy/facade implementations, or circumvent the intended task. A teamwork_preview_auditor will independently verify your work. Integrity violations WILL be detected and your work WILL be rejected.

Your Exclusive Write Ownership:
1. `app/src/main/AndroidManifest.xml`
2. `app/src/main/java/com/example/abhyaas/data/model/TestSeries.kt` (and other models in `data/model/` if needed)
3. `app/src/main/java/com/example/abhyaas/data/network/Dtos.kt`
4. `app/src/main/java/com/example/abhyaas/data/network/ApiService.kt`
5. `app/src/main/java/com/example/abhyaas/data/repository/TestResultRepository.kt`
6. `app/src/main/java/com/example/abhyaas/data/repository/UserRepository.kt`
7. `app/src/main/java/com/example/abhyaas/data/repository/impl/RemoteQuestionRepositoryImpl.kt`
8. `app/src/main/java/com/example/abhyaas/data/repository/impl/RemoteTestResultRepositoryImpl.kt`
9. `app/src/main/java/com/example/abhyaas/data/repository/impl/RemoteUpdatesRepositoryImpl.kt`
10. `app/src/main/java/com/example/abhyaas/data/repository/impl/RemoteExamRepositoryImpl.kt`
11. `app/src/main/java/com/example/abhyaas/data/repository/impl/RemoteUserRepositoryImpl.kt`
12. `app/src/main/java/com/example/abhyaas/AbhyaasApplication.kt`
13. `app/src/main/java/com/example/abhyaas/ui/viewmodel/*` (all ViewModels and UiStates)

Implementation Tasks:
1. Add `<uses-permission android:name="android.permission.INTERNET" />` and `<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />` to `app/src/main/AndroidManifest.xml`.
2. Update `TestSeries.kt`: ensure `TestSeries` includes `studyNotesFolders: List<TestSeriesFolder> = emptyList()`, and `HomeCategoryItem` supports `subtitle: String = ""` and `iconName: String = ""`. Also update `MockExamRepository` mock data if necessary to provide realistic `studyNotesFolders`.
3. Update `Dtos.kt`:
   - Add `HomeCategoryDto(val id: String, val title: String, val subtitle: String?, val icon_name: String?, val test_count: Int?)`
   - Add `PreparationDataPointDto(val label: String, val value: Float, val period: String?)`
   - Add `notes_folders: List<TestSeriesFolderDto>?` to `TestSeriesDto`
   - Add `section_breakdowns: List<SectionResultDto>?` to `TestResultDto` (and `SectionResultDto` if needed)
   - Add mapping extension functions (`toDomain()`) for all new/updated DTOs.
4. Update `ApiService.kt`:
   - Add `@GET("home/categories") suspend fun getHomeCategories(): Response<List<HomeCategoryDto>>`
   - Add `@GET("user/preparation-trends") suspend fun getPreparationTrends(@Query("metric") metric: String): Response<List<PreparationDataPointDto>>`
5. Update repository interfaces:
   - `TestResultRepository`: add `suspend fun getLeaderboard(testId: String): Result<List<LeaderboardEntry>>`
   - `UserRepository`: add `suspend fun getPreparationDataPoints(metric: String): Result<List<PreparationDataPoint>>` (ensure `PreparationDataPoint` model exists or is defined in model package)
6. Implement Remote Repositories with Mock Fallback:
   - For every remote repository call: attempt Retrofit API call using `runCatching` or `try/catch`. If network fails, throws an exception, or returns non-successful response, seamlessly fall back to the existing `Mock*Repository` implementation!
   - Create `RemoteQuestionRepositoryImpl.kt` (delegates to ApiService, falls back to `MockQuestionRepository`).
   - Create `RemoteTestResultRepositoryImpl.kt` (delegates to ApiService, falls back to `MockQuestionRepository` or mock results/leaderboard).
   - Create `RemoteUpdatesRepositoryImpl.kt` (delegates to ApiService, falls back to `MockUpdatesRepository`).
   - Update `RemoteExamRepositoryImpl.kt` and `RemoteUserRepositoryImpl.kt` to ensure robust fallback to `MockExamRepository` and `MockUserRepository`.
7. Update `AbhyaasApplication.kt`:
   - Instantiate and register the remote repository implementations (backed by Retrofit client with mock fallback) as the default repositories.
8. Create and update ViewModels in `ui/viewmodel/`:
   - Create `UserProfileViewModel.kt` (manages profile and preparation trends StateFlow).
   - Create `TestListViewModel.kt` (manages tests for category/series StateFlow).
   - Verify and enhance `HomeViewModel`, `TestsViewModel`, `TestSeriesDetailViewModel`, `UpdatesViewModel`, `ActiveTestViewModel`, `TestResultViewModel`: ensure each uses `MutableStateFlow` and exposes `asStateFlow()`, containing all required data lists and actions so the UI can iterate over them completely dynamically.
9. Verify by running:
   `.\gradlew compileDebugKotlin`
   Ensure 0 errors and build succeeds.
