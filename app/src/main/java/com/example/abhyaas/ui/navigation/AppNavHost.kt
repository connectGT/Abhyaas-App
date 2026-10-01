package com.example.abhyaas.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.abhyaas.ui.components.CommonTopAppBar
import com.example.abhyaas.ui.components.NavIconType
import com.example.abhyaas.ui.screens.auth.LoginScreen
import com.example.abhyaas.ui.screens.auth.UserSettingScreen
import com.example.abhyaas.ui.screens.exam.ActiveTestScreen
import com.example.abhyaas.ui.screens.exam.TestInstructionsScreen
import com.example.abhyaas.ui.screens.main.MainScreen
import com.example.abhyaas.ui.screens.policy.PrivacyPolicyScreen
import com.example.abhyaas.ui.screens.profile.UserProfileScreen
import com.example.abhyaas.ui.screens.result.TestResultScreen
import com.example.abhyaas.ui.screens.tests.TestListScreen
import com.example.abhyaas.ui.screens.tests.TestSeriesDetailScreen
import com.example.abhyaas.ui.screens.main.EmptyStateScreen
import com.example.abhyaas.ui.theme.*

import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.abhyaas.ui.viewmodel.AuthViewModel

@Composable
fun AppNavHost(
    navController: NavHostController,
    authViewModel: AuthViewModel = viewModel()
) {
    val startDest = if (authViewModel.isUserLoggedIn()) Screen.Main.route else Screen.Login.route

    NavHost(
        navController = navController,
        startDestination = startDest
    ) {
        // 1. Authentication & Onboarding
        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.Main.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        // 2. User Account Creation & Settings
        composable(Screen.UserSetting.route) {
            UserSettingScreen(
                onNavigateBack = { navController.popBackStack() },
                onCreateAccountSuccess = {
                    navController.navigate(Screen.Main.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onPrivacyPolicyClick = {
                    navController.navigate(Screen.PrivacyPolicy.route)
                }
            )
        }

        // 3. Main Scaffold (4 Bottom Tabs: Home, Tests, Pass, Updates + Drawer)
        composable(Screen.Main.route) {
            MainScreen(
                onNavigateToProfile = {
                    navController.navigate(Screen.UserProfile.route)
                },
                onNavigateToUserSettings = {
                    navController.navigate(Screen.UserSetting.route)
                },
                onNavigateToTestSeries = { seriesId ->
                    navController.navigate(Screen.TestSeriesDetail.createRoute(seriesId))
                },
                onNavigateToPrivacyPolicy = {
                    navController.navigate(Screen.PrivacyPolicy.route)
                },
                onLogout = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        // 4. User Profile & Preparation Dashboard
        composable(Screen.UserProfile.route) {
            UserProfileScreen(
                onNavigateBack = { navController.popBackStack() },
                onEditProfile = { navController.navigate(Screen.UserSetting.route) },
                onPrivacyPolicyClick = { navController.navigate(Screen.PrivacyPolicy.route) }
            )
        }

        // 5. Privacy Policy
        composable(Screen.PrivacyPolicy.route) {
            PrivacyPolicyScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // 6. Test Series Detail (Milestone 3 Screen)
        composable(
            route = Screen.TestSeriesDetail.route,
            arguments = listOf(navArgument("seriesId") { type = NavType.StringType })
        ) { backStackEntry ->
            val seriesId = backStackEntry.arguments?.getString("seriesId") ?: "ssc_selection_post_2026"
            TestSeriesDetailScreen(
                seriesId = seriesId,
                onBackClick = { navController.popBackStack() },
                onFolderClick = { sId, subCategory ->
                    if (subCategory == "empty_state") {
                        navController.navigate("empty_state")
                    } else if (subCategory == "study_notes") {
                        navController.navigate(Screen.StudyMaterialList.createRoute(sId))
                    } else {
                        navController.navigate(Screen.TestList.createRoute(sId, subCategory))
                    }
                }
            )
        }


        // Extra routes for Pass and Empty State from outside BottomNav
        composable("empty_state") {
            EmptyStateScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
        
        // Study Materials Routes
        composable(
            route = Screen.StudyMaterialList.route,
            arguments = listOf(navArgument("seriesId") { type = NavType.StringType })
        ) { backStackEntry ->
            val seriesId = backStackEntry.arguments?.getString("seriesId") ?: ""
            com.example.abhyaas.ui.screens.study.StudyMaterialListScreen(
                seriesId = seriesId,
                onBackClick = { navController.popBackStack() },
                onUnlockClick = {},
                onMaterialClick = { materialId, sId ->
                    navController.navigate(Screen.PdfViewer.createRoute(materialId, sId))
                }
            )
        }
        
        composable(
            route = Screen.PdfViewer.route,
            arguments = listOf(
                navArgument("materialId") { type = NavType.StringType },
                navArgument("seriesId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val materialId = backStackEntry.arguments?.getString("materialId") ?: ""
            val seriesId = backStackEntry.arguments?.getString("seriesId") ?: ""
            com.example.abhyaas.ui.screens.study.PdfViewerScreen(
                materialId = materialId,
                seriesId = seriesId,
                onBackClick = { navController.popBackStack() }
            )
        }

        // 7. Test List (Milestone 3 Screen)
        composable(
            route = Screen.TestList.route,
            arguments = listOf(
                navArgument("seriesId") { type = NavType.StringType },
                navArgument("subCategory") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val seriesId = backStackEntry.arguments?.getString("seriesId") ?: "ssc_selection_post_2026"
            val subCategory = backStackEntry.arguments?.getString("subCategory") ?: "Exam Day Special"
            TestListScreen(
                seriesId = seriesId,
                subCategory = subCategory,
                onBackClick = { navController.popBackStack() },
                onStartTestClick = { testId ->
                    navController.navigate(Screen.TestInstructions.createRoute(testId))
                },
                onViewResultClick = { testId ->
                    navController.navigate(Screen.TestResult.createRoute(testId))
                }
            )
        }

        // 8. Test Instructions (Milestone 3 Screen)
        composable(
            route = Screen.TestInstructions.route,
            arguments = listOf(navArgument("testId") { type = NavType.StringType })
        ) { backStackEntry ->
            val testId = backStackEntry.arguments?.getString("testId") ?: "ssc_test_day_02"
            TestInstructionsScreen(
                testId = testId,
                onBackClick = { navController.popBackStack() },
                onAgreeAndContinue = { tId, lang ->
                    navController.navigate(Screen.ActiveTest.createRoute(tId, lang))
                }
            )
        }

        // 9. Active Test Taking Screen (Milestone 4 Immersive Examination Engine)
        composable(
            route = Screen.ActiveTest.route,
            arguments = listOf(
                navArgument("testId") { type = NavType.StringType },
                navArgument("lang") { type = NavType.StringType; defaultValue = "English" }
            )
        ) { backStackEntry ->
            val testId = backStackEntry.arguments?.getString("testId") ?: "ssc_test_day_02"
            val lang = backStackEntry.arguments?.getString("lang") ?: "English"
            ActiveTestScreen(
                testId = testId,
                initialLanguage = lang,
                onBackClick = { navController.popBackStack() },
                onSubmitTest = { tId ->
                    navController.navigate(Screen.TestResult.createRoute(tId)) {
                        popUpTo(Screen.ActiveTest.createRoute(tId)) { inclusive = true }
                    }
                }
            )
        }

        // 10. Test Result Scorecard (Milestone 5 Full Destination)
        composable(
            route = Screen.TestResult.route,
            arguments = listOf(navArgument("testId") { type = NavType.StringType })
        ) { backStackEntry ->
            val testId = backStackEntry.arguments?.getString("testId") ?: "ssc_test_day_02"
            TestResultScreen(
                testId = testId,
                onBackClick = { navController.popBackStack() },
                onHomeClick = {
                    navController.navigate(Screen.Main.route) {
                        popUpTo(Screen.Main.route) { inclusive = true }
                    }
                }
            )
        }
    }
}

@Composable
private fun MilestonePlaceholderScreen(
    title: String,
    subtitle: String,
    description: String,
    actionLabel: String,
    onAction: () -> Unit,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            CommonTopAppBar(
                title = title,
                subtitle = subtitle,
                navIconType = NavIconType.Back,
                onNavClick = onBack,
                containerColor = DarkBackgroundGradientStart
            )
        },
        containerColor = DarkBackground
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(AppBackgroundBrush)
                .padding(innerPadding)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = DarkSurface),
                border = androidx.compose.foundation.BorderStroke(1.dp, DarkBorder)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = title,
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = subtitle,
                        color = BrandAccentCyan,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = description,
                        color = TextSecondaryDark,
                        fontSize = 13.sp,
                        lineHeight = 18.sp,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Button(
                        onClick = onAction,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = BrandPrimary)
                    ) {
                        Text(actionLabel, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    }
                }
            }
        }
    }
}
