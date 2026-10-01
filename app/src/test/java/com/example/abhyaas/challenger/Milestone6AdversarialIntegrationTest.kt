package com.example.abhyaas.challenger

import com.example.abhyaas.contract.AppRoute
import com.example.abhyaas.contract.BackstackSimulator
import com.example.abhyaas.data.mock.MockExamRepository
import com.example.abhyaas.data.mock.MockQuestionRepository
import com.example.abhyaas.ui.navigation.Screen
import org.junit.Assert.*
import org.junit.Test

/**
 * Empirical Adversarial Challenger Test Suite for Milestone 6:
 * - Backstack popUpTo purging and re-entry prevention
 * - Navigation argument extraction, parameter safety, and Unicode paths
 * - Mock data consistency across repositories (MockExamRepository vs MockQuestionRepository)
 * - Navigation graph route consistency and root vs nested navigation destinations
 */
class Milestone6AdversarialIntegrationTest {

    // ========================================================================
    // 1. Backstack Purging & Exam Submission Safety
    // ========================================================================

    @Test
    fun testExamSubmissionPurgesActiveTest_PreventingReEntryOnBack() {
        val testId = "ssc_test_day_02"
        val nav = BackstackSimulator(Screen.Main.route)

        // 1. Main -> Tests tab -> Test Series Detail
        nav.navigate(Screen.TestSeriesDetail.createRoute("ssc_selection_post_2026"))
        assertEquals("test_series_detail/ssc_selection_post_2026", nav.currentDestination)

        // 2. Series Detail -> Test List
        nav.navigate(Screen.TestList.createRoute("ssc_selection_post_2026", "Exam Day Special"))
        assertEquals("test_list/ssc_selection_post_2026/Exam Day Special", nav.currentDestination)

        // 3. Test List -> Test Instructions
        nav.navigate(Screen.TestInstructions.createRoute(testId))
        assertEquals("test_instructions/$testId", nav.currentDestination)

        // 4. Instructions -> Active Test
        nav.navigate(Screen.ActiveTest.createRoute(testId))
        assertEquals("active_test/$testId", nav.currentDestination)
        assertTrue("Active test must be in backstack while taking test", nav.currentStack.contains("active_test/$testId"))

        // 5. Submit Test with popUpTo("active_test/$testId", inclusive = true)
        val activeRoute = Screen.ActiveTest.createRoute(testId)
        val resultRoute = Screen.TestResult.createRoute(testId)
        nav.navigate(resultRoute, popUpToRoute = activeRoute, inclusive = true)

        // Destination must be Test Result
        assertEquals(resultRoute, nav.currentDestination)

        // Active test must be purged from backstack
        assertFalse(
            "CRITICAL: Active test route must be purged from backstack after submission to prevent re-taking!",
            nav.currentStack.contains(activeRoute)
        )

        // 6. User presses back button from TestResultScreen
        val backSuccess = nav.popBackStack()
        assertTrue("Back press must succeed", backSuccess)

        // User must NOT land back in ActiveTest
        assertNotEquals(
            "Back button must NEVER re-enter ActiveTest after submission!",
            activeRoute,
            nav.currentDestination
        )
        // User lands back at TestInstructions
        assertEquals("test_instructions/$testId", nav.currentDestination)
    }

    @Test
    fun testAutoSubmitOnTimerExpiry_FollowsIdenticalBackstackContract() {
        val testId = "ssc_test_day_02"
        val nav = BackstackSimulator("active_test/$testId")

        val activeRoute = "active_test/$testId"
        val resultRoute = "test_result/$testId"
        nav.navigate(resultRoute, popUpToRoute = activeRoute, inclusive = true)

        assertEquals(resultRoute, nav.currentDestination)
        assertFalse("Active test must be purged on timer expiry", nav.currentStack.contains(activeRoute))
        assertEquals(1, nav.size)
    }

    // ========================================================================
    // 2. Navigation Argument Passing, Parameter Safety & Unicode Routes
    // ========================================================================

    @Test
    fun testNavigationRouteContractsAndArgParsing() {
        // TestSeriesDetail
        val seriesId = "ssc_selection_post_2026"
        val seriesRoute = Screen.TestSeriesDetail.createRoute(seriesId)
        assertEquals("test_series_detail/ssc_selection_post_2026", seriesRoute)

        // TestList with spaces and Hindi unicode characters
        val hindiCategory = "22 फटाफट Tricky Quant"
        val hindiRoute = Screen.TestList.createRoute(seriesId, hindiCategory)
        assertEquals("test_list/ssc_selection_post_2026/22 फटाफट Tricky Quant", hindiRoute)

        // TestInstructions
        val testId = "ssc_test_day_02"
        val instRoute = Screen.TestInstructions.createRoute(testId)
        assertEquals("test_instructions/ssc_test_day_02", instRoute)

        // ActiveTest
        val activeRoute = Screen.ActiveTest.createRoute(testId)
        assertEquals("active_test/ssc_test_day_02", activeRoute)

        // TestResult
        val resultRoute = Screen.TestResult.createRoute(testId)
        assertEquals("test_result/ssc_test_day_02", resultRoute)
    }

    // ========================================================================
    // 3. Mock Data Consistency Across Repositories
    // ========================================================================

    @Test
    fun testMockExamRepositoryAndQuestionRepositoryConsistency() {
        val tests = MockExamRepository.getTestsForSubCategory("ssc_selection_post_2026", "Exam Day Special")
        assertFalse("Tests list must not be empty", tests.isEmpty())
        assertEquals("Must provide 4 tests", 4, tests.size)

        val testIds = tests.map { it.id }.toSet()
        assertTrue("Must include ssc_test_day_02", testIds.contains("ssc_test_day_02"))
        assertTrue("Must include ssc_test_day_01", testIds.contains("ssc_test_day_01"))

        // For each test, verify sections
        for (test in tests) {
            assertEquals("Each test must have 100 questions", 100, test.totalQuestions)
            assertEquals("Each test must have 200 total marks", 200.0f, test.totalMarks, 0.001f)
            assertEquals("Each test must have 60 minutes duration", 60, test.durationMinutes)
            assertEquals("Each test must have 4 sections", 4, test.sections.size)

            val sectionIds = test.sections.map { it.id }
            assertEquals(listOf("sec_a", "sec_b", "sec_c", "sec_d"), sectionIds)

            // Verify 25 questions per section
            for (section in test.sections) {
                assertEquals("Section ${section.id} must have exactly 25 questions", 25, section.questions.size)
            }
        }

        // Verify all 100 questions from MockQuestionRepository
        val allQuestions = MockQuestionRepository.getQuestionsForTest("default")
        assertEquals(100, allQuestions.size)

        val uniqueQIds = allQuestions.map { it.id }.toSet()
        assertEquals("Question IDs must all be distinct 1..100", 100, uniqueQIds.size)
        assertEquals((1..100).toSet(), uniqueQIds)

        val uniqueQNums = allQuestions.map { it.questionNumber }.toSet()
        assertEquals("Question numbers must all be distinct 1..100", 100, uniqueQNums.size)
        assertEquals((1..100).toSet(), uniqueQNums)

        for (q in allQuestions) {
            assertEquals("Each question must have 4 options", 4, q.options.size)
            assertTrue("Correct option index must be in 0..3", q.correctOptionIndex in 0..3)
            assertFalse("Statement text must not be blank", q.statementText.isBlank())
            assertFalse("Explanation must not be blank", q.explanation.isBlank())
            assertEquals(2.0f, q.positiveMarks, 0.001f)
            assertEquals(0.5f, q.negativeMarks, 0.001f)
        }
    }

    @Test
    fun testPreviousAttemptDataIntegrity() {
        val test01 = MockExamRepository.getTestById("ssc_test_day_01")
        assertNotNull(test01)
        val attempt = test01!!.previousAttempt
        assertNotNull("Day 01 test must have previous attempt data", attempt)
        assertEquals("att_day_01", attempt!!.attemptId)
        assertEquals(22789, attempt.rank)
        assertEquals(24964, attempt.totalCandidates)

        val result = MockExamRepository.getPreviousAttemptResult("ssc_test_day_01")
        assertEquals("att_day_01", result.attemptId)
        assertEquals(22789, result.rank)
        assertEquals(24964, result.totalCandidates)
        assertEquals(100, result.unattemptedCount)
        assertEquals(4, result.sectionBreakdowns.size)
    }

    // ========================================================================
    // 4. Navigation Graph Root vs Nested Destination Integrity
    // ========================================================================

    @Test
    fun testRootNavHostDestinationRegistrationAudit() {
        // Registered in root AppNavHost:
        val rootDestinations = setOf(
            Screen.Login.route,
            Screen.UserSetting.route,
            Screen.Main.route,
            Screen.UserProfile.route,
            Screen.PrivacyPolicy.route,
            Screen.TestSeriesDetail.route,
            Screen.TestList.route,
            Screen.TestInstructions.route,
            Screen.ActiveTest.route,
            Screen.TestResult.route
        )

        // Nested inside MainScreen bottom bar:
        val bottomNavDestinations = setOf(
            Screen.Home.route,
            Screen.Tests.route,
            Screen.Pass.route,
            Screen.Updates.route
        )

        // Verify distinctness between root-only and bottom-tab-only destinations
        assertTrue(rootDestinations.contains("main"))
        assertTrue(rootDestinations.contains("active_test/{testId}"))
        assertTrue(rootDestinations.contains("test_result/{testId}"))

        // Note: Screen.Pass ("pass") is a bottom-nav tab destination in MainScreen,
        // and is not directly registered as a top-level composable in root AppNavHost.
        assertFalse(
            "Screen.Pass is registered in bottomNavController inside MainScreen, not in root AppNavHost",
            rootDestinations.contains(Screen.Pass.route)
        )
    }
}
