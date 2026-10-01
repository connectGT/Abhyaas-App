package com.example.abhyaas.tier3_interaction

import com.example.abhyaas.contract.AppRoute
import com.example.abhyaas.contract.BackstackSimulator
import org.junit.Assert.*
import org.junit.Test

/**
 * Tier 3: Cross-Feature Interactions for Exam Submission & Backstack popUpTo Safety
 */
class ExamSubmissionBackstackTest {

    @Test
    fun testExamSubmissionPopsActiveTestFromBackstack() {
        val testId = "test_ssc_p14_day02"
        val nav = BackstackSimulator(AppRoute.Home.route)

        // 1. Home -> Tests tab
        nav.navigate(AppRoute.Tests.route)
        assertEquals(AppRoute.Tests.route, nav.currentDestination)

        // 2. Tests -> Test Series Detail
        nav.navigate(AppRoute.TestSeriesDetail.createRoute("ssc_selection_post_14"))
        assertEquals("test_series_detail/ssc_selection_post_14", nav.currentDestination)

        // 3. Series Detail -> Instructions
        nav.navigate(AppRoute.TestInstructions.createRoute(testId))
        assertEquals("test_instructions/$testId", nav.currentDestination)

        // 4. Instructions -> Active Test
        nav.navigate(AppRoute.ActiveTest.createRoute(testId))
        assertEquals("active_test/$testId", nav.currentDestination)
        assertTrue(nav.currentStack.contains("active_test/$testId"))

        // 5. Submit exam with popUpTo("active_test/$testId", inclusive = true)
        val activeRoute = AppRoute.ActiveTest.createRoute(testId)
        val resultRoute = AppRoute.TestResult.createRoute(testId)
        nav.navigate(resultRoute, popUpToRoute = activeRoute, inclusive = true)

        // Destination must be Test Result
        assertEquals(resultRoute, nav.currentDestination)

        // Crucial safety check: Active Test must be purged from backstack
        assertFalse(
            "Backstack must not contain active exam after submission!",
            nav.currentStack.contains(activeRoute)
        )

        // Pressing back from Result takes user back to Instructions (or parent), NOT to active exam
        val backSuccess = nav.popBackStack()
        assertTrue(backSuccess)
        assertEquals("test_instructions/$testId", nav.currentDestination)
    }

    @Test
    fun testAutoSubmitOnTimerExpiryFollowsIdenticalBackstackContract() {
        val testId = "test_ssc_p14_day02"
        val nav = BackstackSimulator("active_test/$testId")

        val resultRoute = "test_result/$testId"
        nav.navigate(resultRoute, popUpToRoute = "active_test/$testId", inclusive = true)

        assertEquals(resultRoute, nav.currentDestination)
        assertEquals(1, nav.size)
        assertFalse(nav.currentStack.contains("active_test/$testId"))
    }
}
