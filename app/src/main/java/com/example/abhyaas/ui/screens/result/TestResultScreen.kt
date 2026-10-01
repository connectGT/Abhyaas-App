package com.example.abhyaas.ui.screens.result

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.abhyaas.data.model.Test
import com.example.abhyaas.data.model.TestResult
import com.example.abhyaas.ui.components.LanguageTogglePill
import com.example.abhyaas.ui.theme.*
import com.example.abhyaas.ui.viewmodel.TestResultViewModel

/**
 * Milestone 5 Master Container: TestResultScreen.
 * Includes top bar with bilingual toggle, 3-tab navigation bar (Analysis, Solutions, Leaderboard),
 * and hosts AnalysisTab, SolutionsTab, and LeaderboardTab.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TestResultScreen(
    testId: String = "nayab_tehsildar_2026_mock_01",
    onBackClick: () -> Unit = {},
    onHomeClick: () -> Unit = onBackClick,
    modifier: Modifier = Modifier,
    viewModel: TestResultViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(testId) {
        viewModel.loadResult(testId)
    }

    if (uiState.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = BrandPrimary)
        }
        return
    }

    val test: Test = uiState.test ?: return
    val testResult: TestResult = uiState.result ?: return

    var selectedTabIndex by rememberSaveable { mutableIntStateOf(0) }
    var isHindi by rememberSaveable { mutableStateOf(false) }
    var isMenuExpanded by remember { mutableStateOf(false) }

    val tabTitles = listOf("Analysis", "Solutions", "Leaderboard")

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = DarkBackground,
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(DarkBackgroundGradientStart)
            ) {
                // Top App Bar matching result analysis.jpeg, solutions.jpeg, leaderboard.jpeg
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = DarkBackgroundGradientStart,
                        titleContentColor = Color.White,
                        navigationIconContentColor = Color.White,
                        actionIconContentColor = Color.White
                    ),
                    navigationIcon = {
                        IconButton(onClick = onBackClick) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Navigate Back"
                            )
                        }
                    },
                    title = {
                        Text(
                            text = test.title,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    },
                    actions = {
                        LanguageTogglePill(
                            language = if (isHindi) "HI" else "EN",
                            onClick = { isHindi = !isHindi }
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Box {
                            IconButton(onClick = { isMenuExpanded = true }) {
                                Icon(
                                    imageVector = Icons.Default.Menu,
                                    contentDescription = "Menu",
                                    tint = Color.White
                                )
                            }
                            DropdownMenu(
                                expanded = isMenuExpanded,
                                onDismissRequest = { isMenuExpanded = false },
                                modifier = Modifier.background(DarkSurface)
                            ) {
                                DropdownMenuItem(
                                    text = { Text("Back to Test List", color = Color.White) },
                                    onClick = {
                                        isMenuExpanded = false
                                        onBackClick()
                                    }
                                )
                                DropdownMenuItem(
                                    text = { Text("Go to Home", color = Color.White) },
                                    onClick = {
                                        isMenuExpanded = false
                                        onHomeClick()
                                    }
                                )
                            }
                        }
                    }
                )

                // 3-Tab Bar (Analysis, Solutions, Leaderboard)
                TabRow(
                    selectedTabIndex = selectedTabIndex,
                    containerColor = DarkBackgroundGradientStart,
                    contentColor = Color.White,
                    indicator = { tabPositions ->
                        if (selectedTabIndex < tabPositions.size) {
                            TabRowDefaults.SecondaryIndicator(
                                modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                                color = Color.White,
                                height = 2.5.dp
                            )
                        }
                    },
                    divider = {
                        HorizontalDivider(color = DarkBorderSubtle, thickness = 1.dp)
                    }
                ) {
                    tabTitles.forEachIndexed { index, title ->
                        val isSelected = selectedTabIndex == index
                        Tab(
                            selected = isSelected,
                            onClick = { selectedTabIndex = index },
                            text = {
                                Text(
                                    text = title,
                                    fontSize = 15.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                    color = if (isSelected) Color.White else Color(0xFF94A3B8)
                                )
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (selectedTabIndex) {
                0 -> AnalysisTab(
                    testResult = testResult,
                    onSwitchToSolutions = { selectedTabIndex = 1 }
                )
                1 -> SolutionsTab(
                    test = test,
                    testResult = testResult,
                    isHindi = isHindi
                )
                2 -> LeaderboardTab(
                    testId = testId,
                    currentUserRank = testResult.rank,
                    leaderboard = uiState.leaderboard
                )
            }
        }
    }
}
