# Scope: Abhyaas MVVM + Retrofit Dynamic Refactoring

## Architecture
- **Framework**: Jetpack Compose (Material 3), Kotlin 2.2.10, Coroutines 1.8.1
- **Architecture Pattern**: MVVM with Unidirectional Data Flow (UDF)
- **Data Layer**: Retrofit 2.11.0 + Gson + OkHttp 4.12.0 with automatic in-memory mock fallback on network failure
- **State Observation**: `androidx.lifecycle.compose.collectAsStateWithLifecycle()`
- **Dependency / Service Locator**: `AbhyaasApplication.instance` repository registry

## Feature Inventory
| # | Feature | Description | Milestone | Source |
|---|---------|-------------|-----------|--------|
| 29 | Network Permissions | Add INTERNET and ACCESS_NETWORK_STATE to AndroidManifest.xml | Milestone A | Survey Explorer 1 |
| 30 | Extended Domain Models & DTOs | Add studyNotesFolders, HomeCategoryDto, PreparationDataPointDto, etc. | Milestone A | Survey Explorer 2 |
| 31 | Retrofit ApiService & Repositories with Mock Fallback | Complete ApiService endpoints and Remote*RepositoryImpl with automatic fallback | Milestone A | Survey Explorer 2 |
| 32 | ViewModels & StateFlow | Create UserProfileViewModel, TestListViewModel, verify existing ViewModels | Milestone A | Survey Explorer 2 |
| 33 | Dynamic Home & Tests Screens | Refactor HomeScreen and TestsScreen to observe ViewModel state via collectAsStateWithLifecycle | Milestone B | Survey Explorer 3 |
| 34 | Dynamic TestSeriesDetail & TestList | Remove static mockFolders/pypFolders, iterate over ViewModel state via collectAsStateWithLifecycle | Milestone B | Survey Explorer 3 |
| 35 | Dynamic Updates & Profile Screens | Wire UpdatesScreen and UserProfileScreen to observe ViewModel state via collectAsStateWithLifecycle | Milestone B | Survey Explorer 3 |
| 36 | Dynamic ActiveTest & TestResult | Connect ActiveTestScreen and LeaderboardTab to ViewModels, dynamic palette & leaderboard | Milestone B | Survey Explorer 3 |
| 37 | Compilation & Verification Gate | assembleDebug pass, Reviewer approval, Challenger confirmation, Forensic Auditor CLEAN | Milestone C | Acceptance Criteria |

## Milestones
| # | Name | Scope | Dependencies | Status |
|---|------|-------|-------------|--------|
| A | Complete Data Architecture | AndroidManifest permissions, Models, DTOs, ApiService, Repositories with Fallback, ViewModels | Survey | DONE |
| B | Dynamic UI Wiring & Static List Elimination | Refactor Home, Tests, TestSeriesDetail, Updates, Profile, ActiveTest, LeaderboardTab | Milestone A | DONE |
| C | Verification & Forensic Integrity Gate | assembleDebug, 2x Reviewers, 2x Challengers, 1x Forensic Auditor | Milestone B | IN_PROGRESS |

## Interface Contracts

### 1. Retrofit ApiService (`com.example.abhyaas.data.network.ApiService`)
```kotlin
interface ApiService {
    @GET("home/categories")
    suspend fun getHomeCategories(): Response<List<HomeCategoryDto>>

    @GET("test-series")
    suspend fun getTestSeries(): Response<List<TestSeriesDto>>

    @GET("test-series/{id}")
    suspend fun getTestSeriesById(@Path("id") id: String): Response<TestSeriesDto>

    @GET("test-series/{id}/tests")
    suspend fun getTestsForSubCategory(
        @Path("id") seriesId: String,
        @Query("subCategory") subCategory: String
    ): Response<List<TestDto>>

    @GET("tests/{id}")
    suspend fun getTestById(@Path("id") id: String): Response<TestDto>

    @GET("tests/{id}/questions")
    suspend fun getQuestionsForTest(@Path("id") testId: String): Response<List<QuestionDto>>

    @POST("tests/{id}/submit")
    suspend fun submitTest(
        @Path("id") testId: String,
        @Body submission: TestSubmissionDto
    ): Response<TestResultDto>

    @GET("tests/{id}/result")
    suspend fun getTestResult(@Path("id") testId: String): Response<TestResultDto>

    @GET("tests/{id}/leaderboard")
    suspend fun getLeaderboard(@Path("id") testId: String): Response<List<LeaderboardEntryDto>>

    @GET("updates")
    suspend fun getUpdates(@Query("category") category: String? = null): Response<List<ExamUpdateDto>>

    @GET("user/profile")
    suspend fun getUserProfile(): Response<UserProfileDto>

    @PUT("user/profile")
    suspend fun updateUserProfile(@Body profile: UserProfileDto): Response<UserProfileDto>

    @GET("user/preparation-trends")
    suspend fun getPreparationTrends(@Query("metric") metric: String): Response<List<PreparationDataPointDto>>
}
```

### 2. ViewModels & UI States (`com.example.abhyaas.ui.viewmodel`)
```kotlin
// HomeUiState: isLoading, error, selectedExam, passBanner, categories, currentAffairs
// TestsUiState: isLoading, error, featuredExam, testSeriesList, quickShortcuts
// TestSeriesDetailUiState: isLoading, error, series, activeTab, selectedFolder
// UpdatesUiState: isLoading, error, selectedCategory, updates, categories
// UserProfileUiState: isLoading, error, profile, selectedTrendMetric, trendDataPoints
// ActiveTestUiState: isLoading, error, test, currentQuestionIndex, selectedAnswers, questionStatuses, bookmarkedQuestions, remainingTimeSeconds, isPaused
// TestResultUiState: isLoading, error, result, test, leaderboard, selectedFilter, reattemptMode
```

### 3. Screen State Observation Contract
Every screen function signature must allow passing its ViewModel with default parameter `= viewModel()`:
```kotlin
@Composable
fun TestSeriesDetailScreen(
    seriesId: String,
    onBackClick: () -> Unit,
    onTestClick: (String) -> Unit,
    viewModel: TestSeriesDetailViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    // Dynamic rendering using uiState.series.mockFolders, etc.
}
```
