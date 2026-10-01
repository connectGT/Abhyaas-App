package com.example.abhyaas.ui.screens.tests

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.abhyaas.data.model.Test
import com.example.abhyaas.ui.theme.*
import com.example.abhyaas.ui.viewmodel.TestListViewModel

/**
 * Test Listing Screen matching test look.jpeg and test view after result.png.
 * Displays:
 * - Sub-tabs: Exam Day Special, Most Saved Qs Subject Test
 * - Suggested Next Test section with Free badge, duration, marks, languages, and blue "Start Test" CTA
 * - Previously Attempted section with score, rank, progress track bar with indicator dot, and outlined "View Results" CTA
 * - Filter chip "Daily" and upcoming practice tests
 * - Bottom sticky green "Unlock Test Series" button
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TestListScreen(
    seriesId: String = "ssc_selection_post_2026",
    subCategory: String = "Exam Day Special",
    onBackClick: () -> Unit = {},
    onStartTestClick: (testId: String) -> Unit = {},
    onViewResultClick: (testId: String) -> Unit = {},
    onUnlockClick: () -> Unit = {},
    onShareClick: (testTitle: String) -> Unit = {},
    viewModel: TestListViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(seriesId, subCategory) {
        viewModel.loadTests(seriesId, subCategory)
    }

    val suggestedTest = uiState.suggestedTest
    val attemptedTest = uiState.attemptedTest
    val remainingTests = uiState.remainingTests

    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = uiState.seriesTitle,
                            color = Color.White,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = uiState.seriesSubtitle,
                            color = Color.White.copy(alpha = 0.75f),
                            fontSize = 12.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = DarkBackgroundGradientStart
                )
            )
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(DarkBackground)
                    .border(1.dp, DarkBorderSubtle, RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Button(
                    onClick = onUnlockClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = CtaGreen,
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = "Unlock Test Series",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        },
        containerColor = DarkBackground
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(AppBackgroundBrush)
                .padding(innerPadding)
        ) {
            val activeTabIndex = uiState.selectedSubTabIndex.coerceIn(0, (uiState.subTabs.size - 1).coerceAtLeast(0))

            // Sub-tabs (dynamically driven from ViewModel state)
            TabRow(
                selectedTabIndex = activeTabIndex,
                containerColor = DarkBackgroundGradientStart,
                contentColor = Color.White,
                divider = {
                    HorizontalDivider(color = DarkBorderSubtle, thickness = 1.dp)
                },
                indicator = { tabPositions ->
                    if (activeTabIndex < tabPositions.size) {
                        Box(
                            modifier = Modifier
                                .tabIndicatorOffset(tabPositions[activeTabIndex])
                                .height(3.dp)
                                .background(Color.White, RoundedCornerShape(topStart = 3.dp, topEnd = 3.dp))
                        )
                    }
                }
            ) {
                uiState.subTabs.forEachIndexed { index, title ->
                    val isSelected = activeTabIndex == index
                    Tab(
                        selected = isSelected,
                        onClick = { viewModel.selectSubTab(index) },
                        text = {
                            Text(
                                text = title,
                                fontSize = 14.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                color = if (isSelected) Color.White else TextSecondaryDark,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(horizontal = 16.dp, vertical = 14.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Section 1: Suggested Next Test (matching test view after result.png)
                if (suggestedTest != null) {
                    SectionHeaderWithCyanBar(title = "Suggested Next Test")

                    SuggestedTestCard(
                        test = suggestedTest,
                        onStartTestClick = { onStartTestClick(suggestedTest.id) },
                        onShareClick = { onShareClick(suggestedTest.title) }
                    )
                }

                // Section 2: Previously Attempted (matching test view after result.png)
                if (attemptedTest != null && attemptedTest.previousAttempt != null) {
                    SectionHeaderWithCyanBar(title = "Previously Attempted")

                    AttemptedTestCard(
                        test = attemptedTest,
                        onViewResultClick = { onViewResultClick(attemptedTest.id) },
                        onShareClick = { onShareClick(attemptedTest.title) }
                    )
                }

                // Filter Pill (matching test view after result.png: "Daily")
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = BrandPrimary,
                        modifier = Modifier.clickable { /* Toggle filter */ }
                    ) {
                        Text(
                            text = "Daily",
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
                        )
                    }
                }

                // Additional Tests in Category (matching test look.jpeg: Day - 03, Day - 04)
                remainingTests.forEach { testItem ->
                    SuggestedTestCard(
                        test = testItem,
                        onStartTestClick = { onStartTestClick(testItem.id) },
                        onShareClick = { onShareClick(testItem.title) }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun SectionHeaderWithCyanBar(title: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .width(3.5.dp)
                .height(18.dp)
                .clip(RoundedCornerShape(2.dp))
                .background(BrandAccentCyan)
        )
        Text(
            text = title,
            color = Color.White,
            fontSize = 14.5.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun SuggestedTestCard(
    test: Test,
    onStartTestClick: () -> Unit,
    onShareClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, DarkBorder, RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = DarkSurface)
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Free Chip
            if (test.isFree) {
                Surface(
                    shape = RoundedCornerShape(4.dp),
                    color = CtaGreen.copy(alpha = 0.2f),
                    border = androidx.compose.foundation.BorderStroke(1.dp, CtaGreen)
                ) {
                    Text(
                        text = "FREE",
                        color = CtaGreen,
                        fontSize = 10.5.sp,
                        fontWeight = FontWeight.Black,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            // Test Title
            Column {
                Text(
                    text = "SSC Selection Post (Phase 14):",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = test.title,
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            // Metadata & Blue "Start Test" Button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${test.totalQuestions} Qs . ${test.durationMinutes} mins. ${test.totalMarks} Marks",
                    color = TextSecondaryDark,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )

                Button(
                    onClick = onStartTestClick,
                    shape = RoundedCornerShape(6.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = BrandPrimary,
                        contentColor = Color.White
                    ),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp),
                    modifier = Modifier.height(34.dp)
                ) {
                    Text(
                        text = "Start Test",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // Bottom row: Language & Share Link
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = test.supportedLanguages.joinToString(", "),
                    color = BrandSkyBlue,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )

                Row(
                    modifier = Modifier.clickable(onClick = onShareClick),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "Share",
                        tint = CtaGreen,
                        modifier = Modifier.size(15.dp)
                    )
                    Text(
                        text = "Share",
                        color = CtaGreen,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

@Composable
private fun AttemptedTestCard(
    test: Test,
    onViewResultClick: () -> Unit,
    onShareClick: () -> Unit
) {
    val attempt = test.previousAttempt ?: return

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, DarkBorder, RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = DarkSurface)
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Free Chip
            Surface(
                shape = RoundedCornerShape(4.dp),
                color = CtaGreen.copy(alpha = 0.2f),
                border = androidx.compose.foundation.BorderStroke(1.dp, CtaGreen)
            ) {
                Text(
                    text = "FREE",
                    color = CtaGreen,
                    fontSize = 10.5.sp,
                    fontWeight = FontWeight.Black,
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                )
            }

            // Test Title
            Column {
                Text(
                    text = "SSC Selection Post (Phase 14):",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = test.title,
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            // Marks and Rank Summary
            val rankText = "${String.format("%.1f", attempt.rank / 1000f)}K/${String.format("%.1f", attempt.totalCandidates / 1000f)}K Rank"
            Text(
                text = "${attempt.score.toInt()}/${attempt.maxScore} Marks . $rankText",
                color = TextSecondaryDark,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium
            )

            // Progress Slider Bar with Orange Indicator Dot (matching test view after result.png)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(14.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                // Background Track
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(3.dp)
                        .clip(RoundedCornerShape(1.5.dp))
                        .background(Color(0xFF334155))
                )

                // Orange Dot Indicator (positioned based on rank percentile or near center)
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.58f),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Box(
                        modifier = Modifier
                            .size(7.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFF97316))
                    )
                }
            }

            // Outlined "View Results" Button
            OutlinedButton(
                onClick = onViewResultClick,
                shape = RoundedCornerShape(8.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, BrandPrimary),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = BrandSkyBlue
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(38.dp)
            ) {
                Text(
                    text = "View Results",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            // Bottom row: Attempt timestamp and Share
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Attempted on ${attempt.attemptDate}",
                    color = TextSecondaryDark,
                    fontSize = 11.5.sp
                )

                Row(
                    modifier = Modifier.clickable(onClick = onShareClick),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "Share",
                        tint = CtaGreen,
                        modifier = Modifier.size(15.dp)
                    )
                    Text(
                        text = "Share",
                        color = CtaGreen,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}
