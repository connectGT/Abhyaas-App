package com.example.abhyaas.tier1_coverage

import com.example.abhyaas.contract.AppRoute
import com.example.abhyaas.ui.navigation.Screen
import org.junit.Assert.*
import org.junit.Test

/**
 * Tier 1: Feature Coverage for Navigation Routes & Parameter Extraction
 */
class NavigationContractTest {

    @Test
    fun testExistingMainScreenNavigationRoutes() {
        assertEquals("home", Screen.Home.route)
        assertEquals("tests", Screen.Tests.route)
        assertEquals("pass", Screen.Pass.route)
        assertEquals("updates", Screen.Updates.route)
    }

    @Test
    fun testContractStaticRoutes() {
        assertEquals("login", AppRoute.Login.route)
        assertEquals("user_setting", AppRoute.UserSetting.route)
        assertEquals("user_profile", AppRoute.UserProfile.route)
        assertEquals("main", AppRoute.Main.route)
        assertEquals("home", AppRoute.Home.route)
        assertEquals("tests", AppRoute.Tests.route)
        assertEquals("pass", AppRoute.Pass.route)
        assertEquals("updates", AppRoute.Updates.route)
        assertEquals("privacy_policy", AppRoute.PrivacyPolicy.route)
    }

    @Test
    fun testTestSeriesDetailRouteFormattingAndParsing() {
        val seriesId = "ssc_selection_post_14"
        val route = AppRoute.TestSeriesDetail.createRoute(seriesId)
        assertEquals("test_series_detail/ssc_selection_post_14", route)

        val parsed = AppRoute.TestSeriesDetail.parseSeriesId(route)
        assertEquals(seriesId, parsed)
    }

    @Test
    fun testTestListRouteFormattingAndParsing() {
        val seriesId = "ssc_p14"
        val subCategory = "exam_day_special"
        val route = AppRoute.TestList.createRoute(seriesId, subCategory)
        assertEquals("test_list/ssc_p14/exam_day_special", route)

        val parsed = AppRoute.TestList.parseParams(route)
        assertNotNull(parsed)
        assertEquals(seriesId, parsed?.first)
        assertEquals(subCategory, parsed?.second)
    }

    @Test
    fun testTestInstructionsRouteFormattingAndParsing() {
        val testId = "test_ssc_day02"
        val route = AppRoute.TestInstructions.createRoute(testId)
        assertEquals("test_instructions/test_ssc_day02", route)

        val parsed = AppRoute.TestInstructions.parseTestId(route)
        assertEquals(testId, parsed)
    }

    @Test
    fun testActiveTestRouteFormattingAndParsing() {
        val testId = "test_ssc_day02"
        val route = AppRoute.ActiveTest.createRoute(testId)
        assertEquals("active_test/test_ssc_day02", route)

        val parsed = AppRoute.ActiveTest.parseTestId(route)
        assertEquals(testId, parsed)
    }

    @Test
    fun testTestResultRouteFormattingAndParsing() {
        val testId = "test_ssc_day02"
        val route = AppRoute.TestResult.createRoute(testId)
        assertEquals("test_result/test_ssc_day02", route)

        val parsed = AppRoute.TestResult.parseTestId(route)
        assertEquals(testId, parsed)
    }
}
