package com.example.abhyaas.data.network

import com.example.abhyaas.data.network.dto.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    // ─── Auth ───
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
    suspend fun getPreparationTrends(@Query("metric") metric: String): Response<List<PreparationDataPointDto>>

    // ─── Home & Categories ───
    @GET("home/categories")
    suspend fun getHomeCategories(): Response<List<HomeCategoryDto>>

    // ─── Exams ───
    @GET("exams")
    suspend fun getExams(): Response<List<ExamDto>>

    @GET("exams/{examId}/test-series")
    suspend fun getTestSeries(@Path("examId") examId: String): Response<List<TestSeriesDto>>

    @GET("test-series")
    suspend fun getAllTestSeries(): Response<List<TestSeriesDto>>

    @GET("test-series/{seriesId}")
    suspend fun getTestSeriesDetail(@Path("seriesId") seriesId: String): Response<TestSeriesDto>

    // ─── Tests ───
    @GET("test-series/{seriesId}/tests")
    suspend fun getTests(
        @Path("seriesId") seriesId: String,
        @Query("subCategory") subCategory: String? = null
    ): Response<List<TestDto>>

    @GET("tests/{testId}")
    suspend fun getTestDetail(@Path("testId") testId: String): Response<TestDto>

    @GET("tests/{testId}/questions")
    suspend fun getQuestions(@Path("testId") testId: String): Response<List<QuestionDto>>

    // ─── Results & Leaderboard ───
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

    // ─── User Progress ───
    @GET("user/progress")
    suspend fun getUserProgress(): Response<UserProgressDto>

    @POST("user/progress")
    suspend fun saveUserProgress(@Body body: UserProgressDto): Response<BaseResponse>
}
