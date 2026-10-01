package com.example.abhyaas.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ListAlt
import androidx.compose.material.icons.filled.CardMembership
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Screen navigation routes for Abhyaas application.
 * Supports both top-level stack destinations and bottom-nav tabs.
 */
sealed class Screen(
    val route: String,
    val title: String = "",
    val icon: ImageVector = Icons.Filled.Home
) {
    // Auth & Profile Routes
    object Login : Screen("login")
    object UserSetting : Screen("user_setting")
    object UserProfile : Screen("user_profile")

    // Main Scaffold Host
    object Main : Screen("main")

    // Bottom Navigation Primary Tabs
    object Home : Screen("home", "Home", Icons.Filled.Home)
    object Tests : Screen("tests", "Tests", Icons.AutoMirrored.Filled.ListAlt)
    object Pass : Screen("pass", "Pass", Icons.Filled.CardMembership)
    object Updates : Screen("updates", "Updates", Icons.Filled.Notifications)

    // Test Catalog & Exam Details Routes
    object TestSeriesDetail : Screen("test_series_detail/{seriesId}") {
        fun createRoute(seriesId: String): String = "test_series_detail/$seriesId"
    }
    object TestList : Screen("test_list/{seriesId}/{subCategory}") {
        fun createRoute(seriesId: String, subCategory: String): String = "test_list/$seriesId/$subCategory"
    }
    object TestInstructions : Screen("test_instructions/{testId}") {
        fun createRoute(testId: String): String = "test_instructions/$testId"
    }
    object ActiveTest : Screen("active_test/{testId}?lang={lang}") {
        fun createRoute(testId: String, lang: String = "English"): String = "active_test/$testId?lang=$lang"
    }
    object TestResult : Screen("test_result/{testId}") {
        fun createRoute(testId: String): String = "test_result/$testId"
    }

    // Policy & Legal Routes
    object PrivacyPolicy : Screen("privacy_policy")
}

data class BottomNavItem(
    val screen: Screen,
    val title: String,
    val icon: ImageVector
)

val bottomNavItems = listOf(
    BottomNavItem(Screen.Home, "Home", Icons.Filled.Home),
    BottomNavItem(Screen.Tests, "Tests", Icons.AutoMirrored.Filled.ListAlt),
    BottomNavItem(Screen.Pass, "Pass", Icons.Filled.CardMembership),
    BottomNavItem(Screen.Updates, "Updates", Icons.Filled.Notifications)
)
