package com.example.abhyaas.ui.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.abhyaas.ui.navigation.Screen
import com.example.abhyaas.ui.navigation.bottomNavItems
import com.example.abhyaas.ui.screens.home.HomeScreen
import com.example.abhyaas.ui.screens.tests.TestsScreen
import com.example.abhyaas.ui.screens.updates.UpdatesScreen
import com.example.abhyaas.ui.theme.*
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    onNavigateToProfile: () -> Unit = {},
    onNavigateToUserSettings: () -> Unit = {},
    onNavigateToTestSeries: (String) -> Unit = {},
    onNavigateToPrivacyPolicy: () -> Unit = {},
    onLogout: () -> Unit = {}
) {
    val bottomNavController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val navBackStackEntry by bottomNavController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val currentRoute = currentDestination?.route ?: Screen.Home.route

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            AppDrawer(
                currentRoute = currentRoute,
                onNavigateToRoute = { route ->
                    bottomNavController.navigate(route) {
                        popUpTo(bottomNavController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                onProfileClick = onNavigateToProfile,
                onUserSettingsClick = onNavigateToUserSettings,
                onPrivacyPolicyClick = onNavigateToPrivacyPolicy,
                onLogoutClick = onLogout,
                onCloseDrawer = {
                    scope.launch { drawerState.close() }
                }
            )
        }
    ) {
        Scaffold(
            bottomBar = {
                NavigationBar(
                    containerColor = DarkSurface,
                    contentColor = TextPrimaryDark,
                    tonalElevation = 8.dp
                ) {
                    bottomNavItems.forEach { item ->
                        val selected = currentDestination?.hierarchy?.any { it.route == item.screen.route } == true

                        NavigationBarItem(
                            icon = {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = item.title,
                                    tint = if (selected) BrandAccentCyan else TextSecondaryDark
                                )
                            },
                            label = {
                                Text(
                                    text = item.title,
                                    fontSize = 11.sp,
                                    fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium,
                                    color = if (selected) Color.White else TextSecondaryDark
                                )
                            },
                            selected = selected,
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = BrandAccentCyan,
                                selectedTextColor = Color.White,
                                unselectedIconColor = TextSecondaryDark,
                                unselectedTextColor = TextSecondaryDark,
                                indicatorColor = DarkSelected
                            ),
                            onClick = {
                                bottomNavController.navigate(item.screen.route) {
                                    popUpTo(bottomNavController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            },
            containerColor = DarkBackground
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                NavHost(
                    navController = bottomNavController,
                    startDestination = Screen.Home.route
                ) {
                    composable(Screen.Home.route) {
                        HomeScreen(
                            onMenuClick = { scope.launch { drawerState.open() } },
                            onAvatarClick = onNavigateToProfile,
                            
                            onSearchClick = {
                                bottomNavController.navigate("empty_state")
                            },
                            onCategoryClick = { categoryId ->
                                if (categoryId == "cat_live") {
                                    onNavigateToTestSeries("nayab_tehsildar_2026")
                                } else {
                                    bottomNavController.navigate("empty_state")
                                }
                            }
                        )
                    }
                    composable("empty_state") {
                        EmptyStateScreen(
                            onBackClick = { bottomNavController.popBackStack() }
                        )
                    }
                    composable(Screen.Tests.route) {
                        TestsScreen(
                            onMenuClick = { scope.launch { drawerState.open() } },
                            onAvatarClick = onNavigateToProfile,
                            onTestSeriesClick = onNavigateToTestSeries
                        )
                    }
                    
                    composable(Screen.Updates.route) {
                        UpdatesScreen(
                            onMenuClick = { scope.launch { drawerState.open() } },
                            onAvatarClick = onNavigateToProfile
                        )
                    }
                }
            }
        }
    }
}
