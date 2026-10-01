# Data Layer Architecture & Survey Report: Abhyaas Android App

**Author:** `explorer_survey_data_arch`  
**Date:** 2026-09-30  
**Project:** Abhyaas Android App (MCQ Test Preparation)  
**Location:** `c:\Users\gurut\AndroidStudioProjects\Abhyaas`

---

## 1. Executive Summary

A comprehensive survey of the Abhyaas codebase reveals that while the foundation for MVVM and Retrofit has been drafted (including Retrofit dependencies, initial models, draft `ApiService`, `RetrofitClient`, and skeleton ViewModels), the app's Compose UI screens currently **bypass the ViewModels entirely** and read directly from static objects in `data/mock/MockExamRepository.kt` and `data/mock/MockUserRepository.kt`. Furthermore, several critical UI screens contain **hardcoded in-composable lists** (notably the test folders in `TestSeriesDetailScreen.kt`, categories in `HomeScreen.kt`, and leaderboard in `LeaderboardTab.kt`).

To fulfill the requirements of making the app fully dynamic, the data architecture must be completed by:
1. Extending domain models and DTOs to support all UI requirements (folder categories, section breakdowns, leaderboard, and user preparation metrics).
2. Completing the Retrofit `ApiService` interface with full REST endpoints.
3. Implementing unified Remote Repositories with robust **Mock Data Fallback** (so that network requests execute against Retrofit, but smoothly fall back to rich local mock data upon network/server failure or offline mode).
4. Creating the missing ViewModels (`UserProfileViewModel`, `TestListViewModel`) and refining existing ViewModels to expose complete UI states via `StateFlow`.
5. Refactoring all Compose screens to observe StateFlows via `collectAsStateWithLifecycle()` and iterate dynamically over ViewModel data collections.

---

## 2. Current State Survey & Detailed Audit

### 2.1 Dependencies & BuildConfig
- **Retrofit & Moshi/Gson:** `retrofit:2.11.0`, `converter-gson:2.11.0`, `gson:2.11.0` are already in `app/build.gradle.kts`.
- **OkHttp:** `okhttp:4.12.0`, `logging-interceptor:4.12.0` are present.
- **Coroutines & Lifecycle:** `lifecycle-viewmodel-compose:2.8.7`, `lifecycle-runtime-compose:2.8.7`, `kotlinx-coroutines-android:1.8.1` are present.
- **Base URL:** Defined in `defaultConfig`: `buildConfigField("String", "BASE_URL", "\"https://api.abhyaas.app/v1/\"")`.
- **Compilation Status:** `.\gradlew compileDebugKotlin` builds cleanly with code 0.

---

### 2.2 Domain Models (`app/src/main/java/com/example/abhyaas/data/model/`)

| Model Class | File | Key Attributes | Identified Deficiencies / Additions Needed |
|---|---|---|---|
| `Question` | `Question.kt` | `id`, `sectionId`, `questionNumber`, `statementText`, `statementTextHindi`, `options`, `correctOptionIndex`, `explanation`, `explanationHindi`, `positiveMarks`, `negativeMarks`, `topic`, `subject`, `percentGotRight`, `averageTimeSeconds`, `isBookmarked` | Complete for active test and solutions. |
| `Option` | `Question.kt` | `id`, `text`, `textHindi` | Complete. |
| `QuestionStatus` | `Question.kt` | Enum: `NOT_VISITED`, `UNANSWERED`, `ANSWERED`, `MARKED_FOR_REVIEW`, `ANSWERED_AND_MARKED` | Matches 5 palette states in design. |
| `TestSection` | `Test.kt` | `id`, `partName`, `title`, `titleHindi`, `questions: List<Question>` | Complete. |
| `TestAttemptSummary`| `Test.kt` | `attemptId`, `score`, `maxScore`, `rank`, `totalCandidates`, `attemptDate`, `accuracy` | Complete. |
| `Test` | `Test.kt` | `id`, `seriesId`, `title`, `subCategory`, `durationMinutes`, `totalQuestions`, `totalMarks`, `isFree`, `supportedLanguages`, `sections`, `instructions`, `cutoffGeneral`, `cutoffObc`, `cutoffScSt`, `averageScore`, `bestScore`, `previousAttempt` | Complete. |
| `TestAttempt` | `TestAttempt.kt` | `attemptId`, `testId`, `completedAt`, `timeTakenSeconds`, `selectedAnswers`, `questionStatus`, `questionTimeSpent`, `bookmarkedQuestions` | Complete for submission payload. |
| `SectionResult` | `TestResult.kt`| `sectionId`, `sectionName`, `score`, `correctCount`, `incorrectCount`, `unattemptedCount`, `markedCount`, `accuracy`, `timeTakenSeconds`, `totalQuestions` | Complete for Analysis tab. |
| `TestResult` | `TestResult.kt`| `attemptId`, `testId`, `testTitle`, `score`, `totalMarks`, `rank`, `totalCandidates`, `percentile`, `accuracy`, `correctCount`, `incorrectCount`, `unattemptedCount`, `cutoffMarks`, `averageScore`, `bestScore`, `sectionBreakdowns`, `attemptDate` | Complete. |
| `TestSeriesFolder`| `TestSeries.kt`| `id`, `title`, `testCountText`, `freeTestsBadge`, `isLive`, `isPYQ` | Missing field for `iconName` or category type to differentiate study notes from mock/pyp. |
| `HomeCategoryItem`| `TestSeries.kt`| `id`, `title`, `badge`, `gradientStartColorHex`, `gradientEndColorHex` | Missing `subtitle` and `iconName` (currently hardcoded in `HomeScreen.kt`). |
| `TestSeries` | `TestSeries.kt`| `id`, `title`, `subtitle`, `categoryId`, `totalTests`, `fullTestsCount`, `pyqCount`, `attemptedCount`, `vacancies`, `examDates`, `isEnrolled`, `mockFolders`, `pypFolders` | **Missing `studyNotesFolders: List<TestSeriesFolder>`** needed for Tab 2 of Test Series Detail! |
| `LeaderboardEntry`| `LeaderboardEntry.kt`| `rank`, `userName`, `avatarUrl`, `score`, `maxScore`, `accuracy`, `timeTaken`, `isCurrentUser` | Complete. |
| `UserProfile` | `UserProfile.kt`| `id`, `name`, `fullName`, `mobileNumber`, `email`, `dateOfBirth`, `category`, `pinCode`, `education`, `educationQualification`, `avatarUrl`, `avatarRes`, `targetExam`, `rank`, `totalTestsAttempted`, `accuracy`, `averageScorePercent`, `totalStudyTimeHours` | Complete. |
| `PreparationDataPoint`| `UserProfile.kt`| `dateLabel`, `questionsCount`, `accuracyPercent`, `timeSpentMinutes` | Complete for trend graph. |
| `ExamUpdateItem` | `ExamUpdateItem.kt`| `id`, `title`, `description`, `date`, `category: UpdateCategory`, `isPinned`, `pdfSize`, `actionText` | Complete. |

---

### 2.3 Existing Repositories & Data Sources

#### A. Static Mock Sources (`data/mock/`)
- `MockExamRepository`: Defines two full test series (`tehsildarSeries`, `mpsebSeries`), home categories, test listings for subcategories, previous test attempts, and results.
- `MockQuestionRepository`: Provides 100 questions for Nayab Tehsildar (GK + Reasoning) and 100 questions for MPSEB (GK + Reasoning + Technical).
- `MockUpdatesRepository`: Provides 7 categorized exam updates and rulebooks.
- `MockUserRepository`: In-memory `currentUser`, `getPreparationDataPoints()`, and 8 leaderboard entries.

#### B. Repository Contracts (`data/repository/`)
- `ExamRepository.kt`:
  - `getTestSeriesList(): Result<List<TestSeries>>`
  - `getTestSeriesById(id: String): Result<TestSeries?>`
  - `getTestsForSubCategory(seriesId: String, subCategory: String): Result<List<Test>>`
  - `getTestById(testId: String): Result<Test?>`
  - `getHomeCategories(): List<HomeCategoryItem>` (Note: Synchronous, should be suspend returning `Result<List<HomeCategoryItem>>`).
- `QuestionRepository.kt`:
  - `getQuestionsForTest(testId: String): Result<List<Question>>`
  - `bookmarkQuestion(questionId: Int, bookmarked: Boolean): Result<Unit>`
  - `getBookmarkedQuestions(): Result<List<Int>>`
- `TestResultRepository.kt`:
  - `submitTest(testId: String, answers: Map<Int, Int>, timeTakenSeconds: Long): Result<TestResult>`
  - `getTestResult(testId: String): Result<TestResult>`
  - **MISSING:** `getLeaderboard(testId: String): Result<List<LeaderboardEntry>>` is omitted from the interface!
- `UpdatesRepository.kt`:
  - `getUpdates(category: UpdateCategory): Result<List<ExamUpdateItem>>`
- `UserRepository.kt`:
  - `getUserProfile(): Result<UserProfile>`
  - `updateUserProfile(profile: UserProfile): Result<UserProfile>`
  - `sendOtp(mobile: String): Result<Unit>`
  - `verifyOtp(mobile: String, otp: String): Result<Boolean>`
  - `isLoggedIn(): Boolean`
  - `logout()`
  - **MISSING:** `getPreparationDataPoints(metric: String): Result<List<PreparationDataPoint>>` is omitted from the interface!

#### C. Repository Implementations (`data/repository/impl/`)
- `MockExamRepositoryImpl`: Wraps `MockExamRepository` with `delay()`.
- `MockQuestionRepositoryImpl`: Wraps `MockExamRepository.getTestById(testId)` with in-memory bookmarking.
- `MockTestResultRepositoryImpl`: Wraps `MockExamRepository.getPreviousAttemptResult()`.
- `MockUpdatesRepositoryImpl`: Wraps `MockUpdatesRepository`.
- `MockUserRepositoryImpl`: Wraps `MockUserRepository`.
- `RemoteExamRepositoryImpl`: Implements `ExamRepository` using `RetrofitClient.apiService`. Only partially falls back to `MockExamRepository`.
- `RemoteUserRepositoryImpl`: Implements `UserRepository` using `RetrofitClient.apiService`. **Has zero fallback to mock data on failure.**
- **CRITICAL GAPS in implementations:**
  - `RemoteQuestionRepositoryImpl` **does not exist**.
  - `RemoteTestResultRepositoryImpl` **does not exist**.
  - `RemoteUpdatesRepositoryImpl` **does not exist**.
  - No unified remote-with-fallback repository strategy exists.

---

### 2.4 Existing Network Layer (`data/network/`)

#### A. `RetrofitClient.kt`
- Configured with `OkHttpClient`, `HttpLoggingInterceptor` (BODY in debug), 30s timeouts, auth header token injection, and `GsonConverterFactory`.
- Uses `BuildConfig.BASE_URL` ("https://api.abhyaas.app/v1/").

#### B. `ApiService.kt`
Defines 14 REST endpoints:
- Auth: `auth/send-otp`, `auth/verify-otp`
- User: `user/profile` (GET, PUT), `user/progress` (GET, POST)
- Exams: `exams`, `exams/{examId}/test-series`, `test-series/{seriesId}`
- Tests: `test-series/{seriesId}/tests`, `tests/{testId}`, `tests/{testId}/questions`
- Results: `tests/{testId}/submit`, `tests/{testId}/result`, `tests/{testId}/leaderboard`
- Updates: `updates`
**Missing endpoints in `ApiService`:**
- `home/categories` or `home/data`
- `user/preparation-trends`

#### C. `Dtos.kt`
Contains:
- `BaseResponse`, `SendOtpRequest`, `VerifyOtpRequest`, `AuthResponse`
- `UserProfileDto`, `UpdateProfileRequest`
- `ExamDto`, `TestSeriesDto`, `FolderDto`
- `TestDto`, `QuestionDto`, `OptionDto`
- `SubmitTestRequest`, `TestResultDto`, `LeaderboardEntryDto`
- `UpdateItemDto`, `UserProgressDto`
**Missing/Incomplete in `Dtos.kt`:**
- `TestSeriesDto` missing `notes_folders: List<FolderDto>`.
- `TestResultDto` missing `section_breakdowns: List<SectionResultDto>`.
- `TestDto` missing metadata (`instructions`, `supported_languages`, `cutoff_general`, `previous_attempt`).
- `HomeCategoryDto` does not exist.
- `PreparationDataPointDto` does not exist.
- Mappers (`toModel()`) are scattered in repository implementation files rather than centralized with DTOs.

---

### 2.5 UI Screen and ViewModel Binding Audit

| Screen Composable | Associated ViewModel | Current State in Screen | Hardcoded Static Elements to Remove |
|---|---|---|---|
| `HomeScreen.kt` | `HomeViewModel.kt` (exists) | **Does not use ViewModel**. No `collectAsStateWithLifecycle()`. | 6 Category Grid cards (Study Notes, PYQ, Practice, Live Tests, Classes, Quiz) are hardcoded into static rows; Current Affairs card is static. |
| `TestsScreen.kt` | `TestsViewModel.kt` (exists) | **Does not use ViewModel**. Reads `MockExamRepository.getTestSeriesList()` via `remember`. | `primarySeries` and `secondarySeries` are hardcoded in layout; 4 Quick Action shortcut pills are static. |
| `TestSeriesDetailScreen.kt` | `TestSeriesDetailViewModel.kt` (exists) | **Does not use ViewModel**. Reads `MockExamRepository.getTestSeriesById()` via `remember`. | **Lines 421–464:** `mockFolders = listOf(FolderItemUi(...))` is hardcoded in the composable! **Lines 477–500:** `pypFolders = listOf(FolderItemUi(...))` is hardcoded in the composable! |
| `TestListScreen.kt` | **None exists** | Calls `MockExamRepository.getTestsForSubCategory()` via `remember`. | Tests are not observed dynamically. Sub-tabs and test items are manually partitioned in the composable. |
| `UpdatesScreen.kt` | `UpdatesViewModel.kt` (exists) | **Does not use ViewModel**. Reads `MockUpdatesRepository.getUpdatesByCategory()` via `remember`. | Categories list and updates list are static. |
| `UserProfileScreen.kt` | **None exists** | Calls `MockUserRepository.getUserProfile()` and `getPreparationDataPoints()` via `remember`. | User metrics and data points for chart are fetched directly from mock object. |
| `UserSettingScreen.kt` | **None exists** | Calls `MockUserRepository.getUserProfile()` and `updateUserProfile()` directly. | Direct mock access; no StateFlow. |
| `ActiveTestScreen.kt` | `ActiveTestViewModel.kt` (exists) | **Does not use ViewModel**. Direct call to `MockExamRepository.getTestById()`. | Interactive question status, timer, and answers are held in local composable states rather than observed ViewModel state. |
| `TestResultScreen.kt` | `TestResultViewModel.kt` (exists) | **Does not use ViewModel**. Direct call to `MockExamRepository.getTestById()` and `getPreviousAttemptResult()`. | Direct mock access. |
| `LeaderboardTab.kt` | Controlled by Result | **Hardcoded list of 9 users** in composable `remember` (lines 34–46). | Static leaderboard list in composable! |
| `AppDrawer.kt` | MainScreen | Calls `MockUserRepository.getUserProfile()` directly. | Header profile is static. |

---

## 3. Target Data Architecture Design

### 3.1 Proposed Package Structure

```
com.example.abhyaas
├── AbhyaasApplication.kt
├── MainActivity.kt
│
├── data
│   ├── model/                         # Pure Kotlin Domain Models
│   │   ├── ExamUpdateItem.kt
│   │   ├── LeaderboardEntry.kt
│   │   ├── Question.kt
│   │   ├── Test.kt
│   │   ├── TestAttempt.kt
│   │   ├── TestResult.kt
│   │   ├── TestSeries.kt
│   │   └── UserProfile.kt
│   │
│   ├── mock/                          # Static fallback data sources
│   │   ├── MockExamRepository.kt
│   │   ├── MockQuestionRepository.kt
│   │   ├── MockUpdatesRepository.kt
│   │   └── MockUserRepository.kt
│   │
│   ├── network/                       # Retrofit & OkHttp networking
│   │   ├── ApiService.kt              # Comprehensive REST API contract
│   │   ├── RetrofitClient.kt          # HTTP Client singleton
│   │   └── dto/                       # DTOs and bidirectional Mappers
│   │       ├── AuthDtos.kt            # SendOtp, VerifyOtp, AuthResponse
│   │       ├── ExamDtos.kt            # ExamDto, TestSeriesDto, FolderDto, HomeCategoryDto
│   │       ├── QuestionDtos.kt        # QuestionDto, OptionDto, SectionDto
│   │       ├── ResultDtos.kt          # SubmitTestRequest, TestResultDto, SectionResultDto, LeaderboardDto
│   │       ├── UpdateDtos.kt          # UpdateItemDto
│   │       └── UserDtos.kt            # UserProfileDto, PreparationPointDto, UserProgressDto
│   │
│   └── repository/                    # Clean Repository Pattern
│       ├── ExamRepository.kt          # Interface
│       ├── QuestionRepository.kt      # Interface
│       ├── TestResultRepository.kt    # Interface
│       ├── UpdatesRepository.kt       # Interface
│       ├── UserRepository.kt          # Interface
│       │
│       ├── base/
│       │   └── BaseRemoteRepository.kt # Shared safeApiCall with automatic Mock fallback
│       │
│       └── impl/                      # Concrete repositories with Mock Fallback
│           ├── RemoteExamRepositoryImpl.kt
│           ├── RemoteQuestionRepositoryImpl.kt
│           ├── RemoteTestResultRepositoryImpl.kt
│           ├── RemoteUpdatesRepositoryImpl.kt
│           ├── RemoteUserRepositoryImpl.kt
│           ├── MockExamRepositoryImpl.kt
│           ├── MockQuestionRepositoryImpl.kt
│           ├── MockTestResultRepositoryImpl.kt
│           ├── MockUpdatesRepositoryImpl.kt
│           └── MockUserRepositoryImpl.kt
│
└── ui
    ├── navigation/
    │   ├── AppNavHost.kt
    │   └── Screen.kt
    │
    ├── viewmodel/                     # MVVM StateFlow ViewModels
    │   ├── ActiveTestViewModel.kt
    │   ├── HomeViewModel.kt
    │   ├── TestListViewModel.kt       # [NEW]
    │   ├── TestResultViewModel.kt
    │   ├── TestSeriesDetailViewModel.kt
    │   ├── TestsViewModel.kt
    │   ├── UpdatesViewModel.kt
    │   └── UserProfileViewModel.kt    # [NEW]
    │
    └── screens/                       # Jetpack Compose UI Screens
```

---

### 3.2 Repository Contracts (Interfaces)

All repositories will follow Kotlin `Result<T>` conventions and expose suspend functions:

```kotlin
// 1. ExamRepository.kt
interface ExamRepository {
    suspend fun getHomeCategories(): Result<List<HomeCategoryItem>>
    suspend fun getTestSeriesList(): Result<List<TestSeries>>
    suspend fun getTestSeriesById(id: String): Result<TestSeries?>
    suspend fun getTestsForSubCategory(seriesId: String, subCategory: String): Result<List<Test>>
    suspend fun getTestById(testId: String): Result<Test?>
}

// 2. QuestionRepository.kt
interface QuestionRepository {
    suspend fun getQuestionsForTest(testId: String): Result<List<Question>>
    suspend fun bookmarkQuestion(questionId: Int, bookmarked: Boolean): Result<Unit>
    suspend fun getBookmarkedQuestions(): Result<List<Int>>
}

// 3. TestResultRepository.kt
interface TestResultRepository {
    suspend fun submitTest(testId: String, answers: Map<Int, Int>, timeTakenSeconds: Long): Result<TestResult>
    suspend fun getTestResult(testId: String): Result<TestResult>
    suspend fun getLeaderboard(testId: String): Result<List<LeaderboardEntry>> // Added
}

// 4. UpdatesRepository.kt
interface UpdatesRepository {
    suspend fun getUpdates(category: UpdateCategory = UpdateCategory.ALL): Result<List<ExamUpdateItem>>
}

// 5. UserRepository.kt
interface UserRepository {
    suspend fun getUserProfile(): Result<UserProfile>
    suspend fun updateUserProfile(profile: UserProfile): Result<UserProfile>
    suspend fun getPreparationDataPoints(metric: String = "Questions"): Result<List<PreparationDataPoint>> // Added
    suspend fun sendOtp(mobile: String): Result<Unit>
    suspend fun verifyOtp(mobile: String, otp: String): Result<Boolean>
    fun isLoggedIn(): Boolean
    fun logout()
}
```

---

### 3.3 Retrofit ApiService Contract

The completed `ApiService` will cover all backend interactions:

```kotlin
interface ApiService {
    // ─── Authentication ───
    @POST("auth/send-otp")
    suspend fun sendOtp(@Body body: SendOtpRequest): Response<BaseResponse>

    @POST("auth/verify-otp")
    suspend fun verifyOtp(@Body body: VerifyOtpRequest): Response<AuthResponse>

    // ─── User Profile & Trends ───
    @GET("user/profile")
    suspend fun getUserProfile(): Response<UserProfileDto>

    @PUT("user/profile")
    suspend fun updateUserProfile(@Body body: UpdateProfileRequest): Response<UserProfileDto>

    @GET("user/preparation-trends")
    suspend fun getPreparationTrends(@Query("metric") metric: String? = null): Response<List<PreparationDataPointDto>>

    @GET("user/progress")
    suspend fun getUserProgress(): Response<UserProgressDto>

    // ─── Home & Categories ───
    @GET("home/categories")
    suspend fun getHomeCategories(): Response<List<HomeCategoryDto>>

    // ─── Exam & Test Series Catalog ───
    @GET("exams")
    suspend fun getExams(): Response<List<ExamDto>>

    @GET("exams/{examId}/test-series")
    suspend fun getTestSeriesForExam(@Path("examId") examId: String): Response<List<TestSeriesDto>>

    @GET("test-series")
    suspend fun getAllTestSeries(): Response<List<TestSeriesDto>>

    @GET("test-series/{seriesId}")
    suspend fun getTestSeriesDetail(@Path("seriesId") seriesId: String): Response<TestSeriesDto>

    // ─── Tests & Questions ───
    @GET("test-series/{seriesId}/tests")
    suspend fun getTests(
        @Path("seriesId") seriesId: String,
        @Query("subCategory") subCategory: String? = null
    ): Response<List<TestDto>>

    @GET("tests/{testId}")
    suspend fun getTestDetail(@Path("testId") testId: String): Response<TestDto>

    @GET("tests/{testId}/questions")
    suspend fun getQuestions(@Path("testId") testId: String): Response<List<QuestionDto>>

    // ─── Test Submission, Results & Leaderboard ───
    @POST("tests/{testId}/submit")
    suspend fun submitTest(
        @Path("testId") testId: String,
        @Body body: SubmitTestRequest
    ): Response<TestResultDto>

    @GET("tests/{testId}/result")
    suspend fun getTestResult(@Path("testId") testId: String): Response<TestResultDto>

    @GET("tests/{testId}/leaderboard")
    suspend fun getLeaderboard(@Path("testId") testId: String): Response<List<LeaderboardEntryDto>>

    // ─── Updates ───
    @GET("updates")
    suspend fun getUpdates(@Query("category") category: String? = null): Response<List<UpdateItemDto>>
}
```

---

### 3.4 DTOs & Mappers Specification

Key DTOs needed:

1. **`HomeCategoryDto`**:
   ```kotlin
   data class HomeCategoryDto(
       @SerializedName("id") val id: String,
       @SerializedName("title") val title: String,
       @SerializedName("subtitle") val subtitle: String? = null,
       @SerializedName("badge") val badge: String? = null,
       @SerializedName("gradient_start_hex") val gradientStartHex: Long = 0xFF2563EB,
       @SerializedName("gradient_end_hex") val gradientEndHex: Long = 0xFF1D4ED8
   )
   fun HomeCategoryDto.toModel(): HomeCategoryItem = HomeCategoryItem(
       id = id, title = title, badge = badge,
       gradientStartColorHex = gradientStartHex,
       gradientEndColorHex = gradientEndHex
   )
   ```

2. **`TestSeriesDto` & `FolderDto`**:
   ```kotlin
   data class TestSeriesDto(
       @SerializedName("id") val id: String,
       @SerializedName("title") val title: String,
       @SerializedName("subtitle") val subtitle: String = "",
       @SerializedName("category_id") val categoryId: String = "",
       @SerializedName("total_tests") val totalTests: Int = 0,
       @SerializedName("full_tests_count") val fullTestsCount: Int = 0,
       @SerializedName("pyq_count") val pyqCount: Int = 0,
       @SerializedName("attempted_count") val attemptedCount: Int = 0,
       @SerializedName("vacancies") val vacancies: String = "",
       @SerializedName("exam_dates") val examDates: String = "",
       @SerializedName("is_enrolled") val isEnrolled: Boolean = false,
       @SerializedName("mock_folders") val mockFolders: List<FolderDto> = emptyList(),
       @SerializedName("pyp_folders") val pypFolders: List<FolderDto> = emptyList(),
       @SerializedName("notes_folders") val notesFolders: List<FolderDto> = emptyList()
   )
   ```

3. **`TestResultDto` & `SectionResultDto`**:
   ```kotlin
   data class SectionResultDto(
       @SerializedName("section_id") val sectionId: String,
       @SerializedName("section_name") val sectionName: String,
       @SerializedName("score") val score: Float,
       @SerializedName("correct_count") val correctCount: Int,
       @SerializedName("incorrect_count") val incorrectCount: Int,
       @SerializedName("unattempted_count") val unattemptedCount: Int,
       @SerializedName("marked_count") val markedCount: Int = 0,
       @SerializedName("accuracy") val accuracy: Float,
       @SerializedName("time_taken_seconds") val timeTakenSeconds: Long,
       @SerializedName("total_questions") val totalQuestions: Int = 25
   )
   ```

4. **`PreparationDataPointDto`**:
   ```kotlin
   data class PreparationDataPointDto(
       @SerializedName("date_label") val dateLabel: String,
       @SerializedName("questions_count") val questionsCount: Int,
       @SerializedName("accuracy_percent") val accuracyPercent: Int,
       @SerializedName("time_spent_minutes") val timeSpentMinutes: Int
   )
   ```

---

### 3.5 Remote Repository Implementation with Mock Fallback Strategy

The core requirement is that remote network calls must always be attempted, but gracefully degrade to local mock data upon network failures, 404/500 errors, or offline environments:

```kotlin
abstract class BaseRemoteRepository {
    protected suspend fun <T, R> safeApiCallWithFallback(
        apiCall: suspend () -> retrofit2.Response<T>,
        transform: (T) -> R,
        fallback: suspend () -> R
    ): Result<R> = runCatching {
        try {
            val response = apiCall()
            if (response.isSuccessful && response.body() != null) {
                transform(response.body()!!)
            } else {
                fallback()
            }
        } catch (e: Exception) {
            // Network connection refused, timeout, or DNS failure -> fall back to rich mock data
            fallback()
        }
    }
}
```

Example for `RemoteExamRepositoryImpl`:
```kotlin
class RemoteExamRepositoryImpl(
    private val api: ApiService = RetrofitClient.apiService
) : BaseRemoteRepository(), ExamRepository {

    override suspend fun getHomeCategories(): Result<List<HomeCategoryItem>> {
        return safeApiCallWithFallback(
            apiCall = { api.getHomeCategories() },
            transform = { list -> list.map { it.toModel() } },
            fallback = { MockExamRepository.getHomeCategories() }
        )
    }

    override suspend fun getTestSeriesList(): Result<List<TestSeries>> {
        return safeApiCallWithFallback(
            apiCall = { api.getAllTestSeries() },
            transform = { list -> list.map { it.toModel() } },
            fallback = { MockExamRepository.getTestSeriesList() }
        )
    }

    override suspend fun getTestSeriesById(id: String): Result<TestSeries?> {
        return safeApiCallWithFallback(
            apiCall = { api.getTestSeriesDetail(id) },
            transform = { it.toModel() },
            fallback = { MockExamRepository.getTestSeriesById(id) }
        )
    }

    override suspend fun getTestsForSubCategory(seriesId: String, subCategory: String): Result<List<Test>> {
        return safeApiCallWithFallback(
            apiCall = { api.getTests(seriesId, subCategory) },
            transform = { list -> list.map { it.toModel(seriesId) } },
            fallback = { MockExamRepository.getTestsForSubCategory(seriesId, subCategory) }
        )
    }

    override suspend fun getTestById(testId: String): Result<Test?> {
        return safeApiCallWithFallback(
            apiCall = { api.getTestDetail(testId) },
            transform = { it.toModel("series_default") },
            fallback = { MockExamRepository.getTestById(testId) }
        )
    }
}
```

This pattern applies equally to:
- `RemoteQuestionRepositoryImpl` (calls `api.getQuestions` -> fallback to `MockQuestionRepository.getQuestionsForTest`)
- `RemoteTestResultRepositoryImpl` (calls `api.submitTest`, `api.getTestResult`, `api.getLeaderboard` -> fallback to `MockExamRepository` & `MockUserRepository`)
- `RemoteUpdatesRepositoryImpl` (calls `api.getUpdates` -> fallback to `MockUpdatesRepository.getUpdatesByCategory`)
- `RemoteUserRepositoryImpl` (calls `api.getUserProfile`, `api.updateUserProfile`, `api.getPreparationTrends` -> fallback to `MockUserRepository`)

---

### 3.6 ViewModel Specifications

| ViewModel | UiState Class | State Properties | User Intent / Action Methods |
|---|---|---|---|
| `HomeViewModel` | `HomeUiState` | `isLoading`, `categories: List<HomeCategoryItem>`, `testSeriesList: List<TestSeries>`, `userProfile: UserProfile?`, `error: String?` | `loadData()`, `refresh()` |
| `TestsViewModel` | `TestsUiState` | `isLoading`, `featuredSeries: TestSeries?`, `enrolledSeries: List<TestSeries>`, `otherSeries: List<TestSeries>`, `error: String?` | `loadTestSeries()`, `refresh()` |
| `TestSeriesDetailViewModel` | `TestSeriesDetailUiState`| `isLoading`, `series: TestSeries?`, `selectedTabIndex: Int`, `folders: List<TestSeriesFolder>`, `error: String?` | `loadSeries(seriesId)`, `selectTab(tabIndex)` |
| `TestListViewModel` | `TestListUiState` | `isLoading`, `seriesTitle: String`, `subTabs: List<String>`, `selectedSubTabIndex: Int`, `suggestedTest: Test?`, `attemptedTest: Test?`, `remainingTests: List<Test>`, `error: String?` | `loadTests(seriesId, subCategory)`, `selectSubTab(index)` |
| `UpdatesViewModel` | `UpdatesUiState` | `isLoading`, `updates: List<ExamUpdateItem>`, `selectedCategory: UpdateCategory`, `error: String?` | `loadUpdates(category)` |
| `UserProfileViewModel` | `UserProfileUiState` | `isLoading`, `profile: UserProfile`, `selectedTab: PrepTrackerTab`, `dataPoints: List<PreparationDataPoint>`, `isSaving: Boolean`, `saveSuccess: Boolean`, `error: String?` | `loadProfile()`, `selectTrackerTab(tab)`, `updateProfile(newProfile)` |
| `ActiveTestViewModel` | `ActiveTestUiState` | `isLoading`, `test: Test?`, `questions: List<Question>`, `currentQuestionIndex: Int`, `selectedAnswers: Map<Int, Int>`, `questionStatuses: Map<Int, QuestionStatus>`, `timeRemainingSeconds: Int`, `isPaused: Boolean`, `isHindi: Boolean`, `isSubmitted: Boolean` | `loadTest(testId)`, `selectAnswer()`, `clearAnswer()`, `markForReview()`, `navigateToQuestion()`, `toggleLanguage()`, `togglePause()`, `tickTimer()`, `submitTest()` |
| `TestResultViewModel` | `TestResultUiState` | `isLoading`, `test: Test?`, `result: TestResult?`, `leaderboard: List<LeaderboardEntry>`, `selectedTabIndex: Int`, `isHindi: Boolean`, `error: String?` | `loadResult(testId)`, `selectTab(index)`, `toggleLanguage()` |

---

### 3.7 Application Dependency Wiring

In `AbhyaasApplication.kt`:
```kotlin
class AbhyaasApplication : Application() {
    val examRepository: ExamRepository by lazy { RemoteExamRepositoryImpl() }
    val questionRepository: QuestionRepository by lazy { RemoteQuestionRepositoryImpl() }
    val testResultRepository: TestResultRepository by lazy { RemoteTestResultRepositoryImpl() }
    val updatesRepository: UpdatesRepository by lazy { RemoteUpdatesRepositoryImpl() }
    val userRepository: UserRepository by lazy { RemoteUserRepositoryImpl() }

    companion object {
        lateinit var instance: AbhyaasApplication
            private set
    }
}
```

Every ViewModel accesses its repository via `AbhyaasApplication.instance.<repository>`.

---

## 4. Compose UI Screen Dynamic Refactoring Plan

| Screen | Target ViewModel | Key Changes Required |
|---|---|---|
| `HomeScreen.kt` | `HomeViewModel` | Injected via `viewModel: HomeViewModel = viewModel()`. Observe `val uiState by viewModel.uiState.collectAsStateWithLifecycle()`. Replace hardcoded row 1, 2, 3 category grid cards with dynamic rendering over `uiState.categories`. Render user name dynamically in header if present. |
| `TestsScreen.kt` | `TestsViewModel` | Injected via `viewModel: TestsViewModel = viewModel()`. Observe `uiState`. Render `uiState.featuredSeries` in the hero banner. Dynamically iterate over `uiState.enrolledSeries` for enrolled cards instead of hardcoded `primarySeries`. |
| `TestSeriesDetailScreen.kt` | `TestSeriesDetailViewModel` | Injected via `viewModel: TestSeriesDetailViewModel = viewModel()`. Observe `uiState`. Delete lines 421–464 (`mockFolders = listOf(...)`) and lines 477–500 (`pypFolders = listOf(...)`). Replace with iteration over `uiState.folders` (which dynamically switches based on `selectedTabIndex`). |
| `TestListScreen.kt` | `TestListViewModel` | Injected via `viewModel: TestListViewModel = viewModel()`. Observe `uiState`. Dynamically render `uiState.suggestedTest`, `uiState.attemptedTest`, and `uiState.remainingTests`. |
| `UpdatesScreen.kt` | `UpdatesViewModel` | Injected via `viewModel: UpdatesViewModel = viewModel()`. Observe `uiState`. Filter tabs invoke `viewModel.loadUpdates(category)`. List iterates over `uiState.updates`. |
| `UserProfileScreen.kt` & `UserSettingScreen.kt` | `UserProfileViewModel` | Injected via `viewModel: UserProfileViewModel = viewModel()`. Observe `uiState`. Trend chart renders `uiState.dataPoints`. Form in UserSetting screen updates state and calls `viewModel.updateProfile()`. |
| `ActiveTestScreen.kt` | `ActiveTestViewModel` | Injected via `viewModel: ActiveTestViewModel = viewModel()`. Observe `uiState`. Ticker, question options, status badges, and palette grid bind directly to `uiState`. |
| `TestResultScreen.kt` | `TestResultViewModel` | Injected via `viewModel: TestResultViewModel = viewModel()`. Observe `uiState`. Passes `uiState.result` to AnalysisTab and SolutionsTab. Passes `uiState.leaderboard` to `LeaderboardTab`. |
| `LeaderboardTab.kt` | Bound via parent | Accept `leaderboard: List<LeaderboardEntry>`. Delete lines 34–46 (hardcoded list). Iterate over passed dynamic list. |

---

## 5. Architectural Verification Plan

To verify that the implementation meets all requirements:
1. **Compilation Verification**:
   - Run `.\gradlew compileDebugKotlin` and `.\gradlew assembleDebug`.
   - Must build with code 0 and zero unresolved references.
2. **Dynamic UI Verification (Agent-as-Judge)**:
   - Grep search confirming no remaining `listOf(FolderItemUi(...))` or hardcoded folders in `TestSeriesDetailScreen.kt`.
   - Grep search confirming `collectAsStateWithLifecycle()` is used in `HomeScreen`, `TestsScreen`, `TestSeriesDetailScreen`, `TestListScreen`, `UpdatesScreen`, `UserProfileScreen`, `ActiveTestScreen`, `TestResultScreen`.
   - Grep search confirming screens do not call `MockExamRepository` or `MockUserRepository` directly in their composable bodies.
3. **Fallback Verification**:
   - Verify that when the device has no internet or the mock base URL cannot be reached, the app still renders the full catalog, questions, results, and updates seamlessly from mock data fallback without throwing uncaught exceptions.
