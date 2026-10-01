package com.example.abhyaas.verification

import com.example.abhyaas.AbhyaasApplication
import com.example.abhyaas.data.model.*
import com.example.abhyaas.data.network.ApiService
import com.example.abhyaas.data.repository.impl.*
import com.example.abhyaas.ui.viewmodel.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import retrofit2.Response

/**
 * Empirical Verification and Stress Test Suite for the MVVM + Retrofit Data Architecture:
 * 1. Fallback behavior of RemoteExamRepositoryImpl, RemoteQuestionRepositoryImpl,
 *    RemoteTestResultRepositoryImpl, RemoteUpdatesRepositoryImpl, RemoteUserRepositoryImpl.
 * 2. ViewModel initialization, StateFlow non-null emission, and mock integration for:
 *    HomeViewModel, TestsViewModel, TestSeriesDetailViewModel, UpdatesViewModel,
 *    UserProfileViewModel, ActiveTestViewModel.
 */
class EmpiricalDataArchitectureTest {

    // ─────────────────────────────────────────────────────────────────────────────
    // 1. Remote Repositories Mock Fallback Degradation Tests
    // ─────────────────────────────────────────────────────────────────────────────

    @Test
    fun testRemoteExamRepository_DegradesGracefullyToMockData() = runBlocking {
        // By default, RetrofitClient points to an unresolvable URL (https://api.abhyaas.app/v1/).
        // Calls must not throw an unhandled exception and must return mock data.
        val repo = RemoteExamRepositoryImpl()

        val seriesListResult = repo.getTestSeriesList()
        assertTrue("getTestSeriesList() must succeed via fallback", seriesListResult.isSuccess)
        val seriesList = seriesListResult.getOrThrow()
        assertTrue("Test series list must not be empty", seriesList.isNotEmpty())
        assertEquals(2, seriesList.size)
        assertTrue(seriesList.any { it.id == "nayab_tehsildar_2026" })
        assertTrue(seriesList.any { it.id == "mpseb_2026" })

        val seriesByIdResult = repo.getTestSeriesById("nayab_tehsildar_2026")
        assertTrue("getTestSeriesById() must succeed via fallback", seriesByIdResult.isSuccess)
        val series = seriesByIdResult.getOrThrow()
        assertNotNull("Series must not be null", series)
        assertEquals("nayab_tehsildar_2026", series?.id)

        val testsResult = repo.getTestsForSubCategory("nayab_tehsildar_2026", "Paper 1: Full Mock Tests")
        assertTrue("getTestsForSubCategory() must succeed via fallback", testsResult.isSuccess)
        val tests = testsResult.getOrThrow()
        assertTrue("Tests list must not be empty", tests.isNotEmpty())

        val testByIdResult = repo.getTestById("nayab_tehsildar_2026_mock_01")
        assertTrue("getTestById() must succeed via fallback", testByIdResult.isSuccess)
        val test = testByIdResult.getOrThrow()
        assertNotNull("Test must not be null", test)

        val homeCategories = repo.getHomeCategories()
        assertTrue("Home categories must not be empty", homeCategories.isNotEmpty())
        assertEquals(7, homeCategories.size)
    }

    @Test
    fun testRemoteQuestionRepository_DegradesGracefullyToMockData() = runBlocking {
        val repo = RemoteQuestionRepositoryImpl()

        val questionsResult = repo.getQuestionsForTest("nayab_tehsildar_2026_mock_01")
        assertTrue("getQuestionsForTest() must succeed via fallback", questionsResult.isSuccess)
        val questions = questionsResult.getOrThrow()
        assertEquals("Fallback must provide 100 questions", 100, questions.size)

        // Test bookmarking operations
        val bookmarkRes = repo.bookmarkQuestion(1, true)
        assertTrue("Bookmark add must succeed", bookmarkRes.isSuccess)
        val bookmarks = repo.getBookmarkedQuestions().getOrThrow()
        assertTrue("Question 1 must be bookmarked", bookmarks.contains(1))

        val unbookmarkRes = repo.bookmarkQuestion(1, false)
        assertTrue("Bookmark remove must succeed", unbookmarkRes.isSuccess)
        val updatedBookmarks = repo.getBookmarkedQuestions().getOrThrow()
        assertFalse("Question 1 must no longer be bookmarked", updatedBookmarks.contains(1))
    }

    @Test
    fun testRemoteTestResultRepository_DegradesGracefullyToMockData() = runBlocking {
        val repo = RemoteTestResultRepositoryImpl()

        val submitResult = repo.submitTest("nayab_tehsildar_2026_mock_01", mapOf(1 to 0, 2 to 1), 1800L)
        assertTrue("submitTest() must succeed via fallback", submitResult.isSuccess)
        val result = submitResult.getOrThrow()
        assertNotNull("Submitted result must not be null", result)
        assertEquals("nayab_tehsildar_2026_mock_01", result.testId)
        assertTrue("Result must have section breakdowns", result.sectionBreakdowns.isNotEmpty())

        val getResult = repo.getTestResult("nayab_tehsildar_2026_mock_01")
        assertTrue("getTestResult() must succeed via fallback", getResult.isSuccess)
        assertNotNull("Retrieved result must not be null", getResult.getOrThrow())

        val leaderboardResult = repo.getLeaderboard("nayab_tehsildar_2026_mock_01")
        assertTrue("getLeaderboard() must succeed via fallback", leaderboardResult.isSuccess)
        val leaderboard = leaderboardResult.getOrThrow()
        assertTrue("Leaderboard must have entries", leaderboard.isNotEmpty())
        assertEquals(8, leaderboard.size)
        assertTrue(leaderboard.any { it.isCurrentUser })
    }

    @Test
    fun testRemoteUpdatesRepository_DegradesGracefullyToMockData() = runBlocking {
        val repo = RemoteUpdatesRepositoryImpl()

        val allUpdatesResult = repo.getUpdates(UpdateCategory.ALL)
        assertTrue("getUpdates(ALL) must succeed", allUpdatesResult.isSuccess)
        val allUpdates = allUpdatesResult.getOrThrow()
        assertEquals(7, allUpdates.size)

        val notificationsResult = repo.getUpdates(UpdateCategory.NOTIFICATIONS)
        assertTrue("getUpdates(NOTIFICATIONS) must succeed", notificationsResult.isSuccess)
        val notifications = notificationsResult.getOrThrow()
        assertTrue("Should return only notification updates", notifications.all { it.category == UpdateCategory.NOTIFICATIONS })
    }

    @Test
    fun testRemoteUserRepository_DegradesGracefullyToMockData() = runBlocking {
        val repo = RemoteUserRepositoryImpl()

        val profileResult = repo.getUserProfile()
        assertTrue("getUserProfile() must succeed", profileResult.isSuccess)
        val profile = profileResult.getOrThrow()
        assertEquals("Aspirant", profile.name)

        val updatedProfile = profile.copy(name = "Empirical Challenger")
        val updateResult = repo.updateUserProfile(updatedProfile)
        assertTrue("updateUserProfile() must succeed", updateResult.isSuccess)
        assertEquals("Empirical Challenger", updateResult.getOrThrow().name)

        val pointsResult = repo.getPreparationDataPoints("Questions")
        assertTrue("getPreparationDataPoints() must succeed", pointsResult.isSuccess)
        val points = pointsResult.getOrThrow()
        assertEquals(6, points.size)

        val otpSendResult = repo.sendOtp("9876543210")
        assertTrue("sendOtp() must succeed", otpSendResult.isSuccess)

        val verifyValid = repo.verifyOtp("9876543210", "123456")
        assertTrue("verifyOtp with 6 digits must succeed", verifyValid.isSuccess)
        assertTrue(verifyValid.getOrThrow())
        assertTrue(repo.isLoggedIn())

        repo.logout()
        assertFalse(repo.isLoggedIn())
    }
}
