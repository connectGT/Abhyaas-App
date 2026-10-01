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
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.abhyaas.data.model.TestSeriesFolder
import com.example.abhyaas.ui.theme.*
import com.example.abhyaas.ui.viewmodel.TestSeriesDetailViewModel
import kotlinx.coroutines.launch

/**
 * Test Series Detail Screen matching enrolled test.png and enrolled test 2.png.
 * Features:
 * - TopAppBar with back arrow, circular emblem logo, series title, announcement megaphone & overflow menu
 * - Metrics Card: Total Tests/Papers, Attempted/Solved, Progress 0%
 * - Promotional banner: Continue Your Preparation / Practice Previous Year Papers
 * - Tabs: Mock Tests, PYQs, Study Notes
 * - Folder listing with free badges, subtitles, icons and navigation
 * - Sticky bottom green CTA button: Unlock Test Series / Unlock Previous Year Papers
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TestSeriesDetailScreen(
    seriesId: String = "nayab_tehsildar_2026",
    onBackClick: () -> Unit = {},
    onFolderClick: (seriesId: String, subCategory: String) -> Unit = { _, _ -> },
    onUnlockClick: () -> Unit = {},
    onAnnouncementClick: () -> Unit = {},
    viewModel: TestSeriesDetailViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(seriesId) {
        viewModel.loadSeries(seriesId)
    }

    if (uiState.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = Color(0xFF38BDF8))
        }
        return
    }

    val series = uiState.series ?: return

    val seriesAbbr = remember(series.id) {
        when {
            series.id.contains("mpseb") -> "MP"
            series.id.contains("tehsildar") -> "MP"
            else -> "EX"
        }
    }
    val seriesAbbrColor = remember(series.id) {
        if (series.id.contains("mpseb")) 0xFFF59E0B else 0xFF38BDF8
    }

    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabs = listOf("Mock Tests", "PYQs", "Study Notes")
    val scrollState = rememberScrollState()
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        // Dynamic Series Emblem
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF1E293B))
                                .border(1.5.dp, Color(seriesAbbrColor), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = seriesAbbr,
                                color = Color(seriesAbbrColor),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Black
                            )
                        }

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = series.title,
                                color = Color.White,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(
                                text = series.subtitle,
                                color = Color.White.copy(alpha = 0.75f),
                                fontSize = 12.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
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
                actions = {
                    IconButton(onClick = onAnnouncementClick) {
                        Icon(
                            imageVector = Icons.Default.Campaign,
                            contentDescription = "Announcements",
                            tint = Color.White
                        )
                    }
                    IconButton(onClick = {
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("More options coming soon")
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "More Options",
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
                        text = if (selectedTabIndex == 1) "Unlock Previous Year Papers" else "Unlock Test Series",
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
                .verticalScroll(scrollState)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(4.dp))

            // Metrics Card (matching enrolled test.png & enrolled test 2.png)
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, DarkBorder, RoundedCornerShape(14.dp)),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = DarkSurface)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    when (selectedTabIndex) {
                        1 -> {
                            // PYQs Metrics
                            val pyqTotal = if (series.pypFolders.isNotEmpty()) "${series.pypFolders.size * 5}" else "${series.pyqCount}"
                            MetricItem(
                                icon = Icons.Default.Description,
                                iconColor = BrandPrimary,
                                iconBg = BrandPrimary.copy(alpha = 0.2f),
                                label = "Total Papers",
                                value = pyqTotal,
                                modifier = Modifier.weight(1f)
                            )
                            VerticalDivider(modifier = Modifier.height(40.dp), color = DarkBorderSubtle)
                            MetricItem(
                                icon = Icons.Default.CheckCircle,
                                iconColor = Color(0xFF14B8A6),
                                iconBg = Color(0xFF14B8A6).copy(alpha = 0.2f),
                                label = "Solved",
                                value = "0",
                                modifier = Modifier.weight(1f)
                            )
                            VerticalDivider(modifier = Modifier.height(40.dp), color = DarkBorderSubtle)
                            MetricItem(
                                icon = Icons.Default.BarChart,
                                iconColor = Color(0xFF818CF8),
                                iconBg = Color(0xFF818CF8).copy(alpha = 0.2f),
                                label = "Progress",
                                value = "0%",
                                modifier = Modifier.weight(1f)
                            )
                        }
                        2 -> {
                            // Study Notes Metrics
                            val notesCount = if (series.studyNotesFolders.isNotEmpty()) "${series.studyNotesFolders.size * 8}" else "24"
                            MetricItem(
                                icon = Icons.Default.Description,
                                iconColor = BrandPrimary,
                                iconBg = BrandPrimary.copy(alpha = 0.2f),
                                label = "Total Notes",
                                value = notesCount,
                                modifier = Modifier.weight(1f)
                            )
                            VerticalDivider(modifier = Modifier.height(40.dp), color = DarkBorderSubtle)
                            MetricItem(
                                icon = Icons.Default.CheckCircle,
                                iconColor = Color(0xFF14B8A6),
                                iconBg = Color(0xFF14B8A6).copy(alpha = 0.2f),
                                label = "Read",
                                value = "0",
                                modifier = Modifier.weight(1f)
                            )
                            VerticalDivider(modifier = Modifier.height(40.dp), color = DarkBorderSubtle)
                            MetricItem(
                                icon = Icons.Default.BarChart,
                                iconColor = Color(0xFF818CF8),
                                iconBg = Color(0xFF818CF8).copy(alpha = 0.2f),
                                label = "Progress",
                                value = "0%",
                                modifier = Modifier.weight(1f)
                            )
                        }
                        else -> {
                            // Mock Tests Metrics (Default)
                            val progressPercent = ((series.attemptedCount * 100) / series.totalTests.coerceAtLeast(1))
                            MetricItem(
                                icon = Icons.Default.Description,
                                iconColor = BrandPrimary,
                                iconBg = BrandPrimary.copy(alpha = 0.2f),
                                label = "Total Tests",
                                value = "${series.totalTests}",
                                modifier = Modifier.weight(1f)
                            )
                            VerticalDivider(modifier = Modifier.height(40.dp), color = DarkBorderSubtle)
                            MetricItem(
                                icon = Icons.Default.CheckCircle,
                                iconColor = Color(0xFF14B8A6),
                                iconBg = Color(0xFF14B8A6).copy(alpha = 0.2f),
                                label = "Attempted",
                                value = "${series.attemptedCount}",
                                modifier = Modifier.weight(1f)
                            )
                            VerticalDivider(modifier = Modifier.height(40.dp), color = DarkBorderSubtle)
                            MetricItem(
                                icon = Icons.Default.BarChart,
                                iconColor = Color(0xFF818CF8),
                                iconBg = Color(0xFF818CF8).copy(alpha = 0.2f),
                                label = "Progress",
                                value = "$progressPercent%",
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }

            // Promotional Action Banner
            val bannerTitle = when (selectedTabIndex) {
                1 -> "Practice Previous Year Papers"
                2 -> "Explore Study Notes"
                else -> "Continue Your Preparation"
            }
            val bannerSubtitle = when (selectedTabIndex) {
                1 -> "Understand the exam pattern, analyze your preparation and boost your score."
                2 -> "Quick revision notes, formulas and concept summaries for top scoring."
                else -> "Attempt tests, track progress and improve your score."
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        val targetCategory = if (selectedTabIndex == 1) "Previous Year Papers" else "Exam Day Special"
                        onFolderClick(series.id, targetCategory)
                    },
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(PassBannerBrush)
                        .border(1.dp, BrandPrimaryLight.copy(alpha = 0.4f), RoundedCornerShape(16.dp))
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        // 3D Illustration Mock Container
                        Box(
                            modifier = Modifier
                                .size(46.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(Color.White.copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = if (selectedTabIndex == 1) Icons.Default.School else Icons.AutoMirrored.Filled.Assignment,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(28.dp)
                            )
                        }

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = bannerTitle,
                                color = Color.White,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = bannerSubtitle,
                                color = Color.White.copy(alpha = 0.9f),
                                fontSize = 11.5.sp,
                                lineHeight = 15.sp
                            )
                        }

                        // Round white CTA arrow button
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(Color.White),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = "Continue",
                                tint = BrandPrimary,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }

            // Tabs Row (Mock Tests | PYQs | Study Notes)
            TabRow(
                selectedTabIndex = selectedTabIndex,
                containerColor = Color.Transparent,
                contentColor = Color.White,
                divider = {
                    HorizontalDivider(color = DarkBorderSubtle, thickness = 1.dp)
                },
                indicator = { tabPositions ->
                    if (selectedTabIndex < tabPositions.size) {
                        Box(
                            modifier = Modifier
                                .tabIndicatorOffset(tabPositions[selectedTabIndex])
                                .height(3.dp)
                                .background(Color.White, RoundedCornerShape(topStart = 3.dp, topEnd = 3.dp))
                        )
                    }
                }
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = {
                            selectedTabIndex = index
                            viewModel.selectTab(index)
                        },
                        text = {
                            Text(
                                text = title,
                                fontSize = 14.5.sp,
                                fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Medium,
                                color = if (selectedTabIndex == index) Color.White else TextSecondaryDark
                            )
                        }
                    )
                }
            }

            // Tab Content: Dynamic Folders List from ViewModel state & domain model
            val currentFolders = when (selectedTabIndex) {
                0 -> series.mockFolders
                1 -> series.pypFolders
                2 -> series.studyNotesFolders
                else -> series.mockFolders
            }

            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                currentFolders.forEach { folder ->
                    FolderCard(
                        folder = folder,
                        onClick = { 
                            if (selectedTabIndex == 2) {
                                onFolderClick(series.id, "study_notes")
                            } else {
                                onFolderClick(series.id, folder.title)
                            }
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun FolderCard(
    folder: TestSeriesFolder,
    onClick: () -> Unit
) {
    val icon: ImageVector = when {
        folder.isPYQ -> Icons.Default.School
        folder.isLive -> Icons.Default.Sensors
        folder.title.contains("Special", ignoreCase = true) -> Icons.AutoMirrored.Filled.Article
        folder.title.contains("Tricky", ignoreCase = true) || folder.title.contains("Quant", ignoreCase = true) -> Icons.Default.BarChart
        folder.title.contains("Formula", ignoreCase = true) -> Icons.Default.Functions
        folder.title.contains("Notes", ignoreCase = true) || folder.title.contains("Summary", ignoreCase = true) -> Icons.Default.Description
        folder.title.contains("General", ignoreCase = true) || folder.title.contains("GK", ignoreCase = true) || folder.title.contains("Awareness", ignoreCase = true) -> Icons.Default.Public
        folder.title.contains("Law", ignoreCase = true) || folder.title.contains("Code", ignoreCase = true) -> Icons.Default.Description
        folder.title.contains("Language", ignoreCase = true) || folder.title.contains("Vocabulary", ignoreCase = true) -> Icons.Default.Translate
        else -> Icons.AutoMirrored.Filled.Article
    }

    val iconBg: Color = when {
        folder.isLive -> Color(0xFFB91C1C)
        folder.isPYQ -> Color(0xFF1D4ED8)
        folder.freeTestsBadge != null -> Color(0xFF0D9488)
        folder.title.contains("Quant", ignoreCase = true) || folder.title.contains("Formula", ignoreCase = true) -> Color(0xFFD97706)
        folder.title.contains("Notes", ignoreCase = true) -> Color(0xFF7C3AED)
        else -> Color(0xFF2563EB)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .border(1.dp, DarkBorder, RoundedCornerShape(12.dp))
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = DarkSurface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Rounded Icon Box
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(iconBg),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = folder.title,
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }

            // Title & Subtitle
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = folder.title,
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = folder.testCountText,
                    color = TextSecondaryDark,
                    fontSize = 11.5.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            // Optional Badge + Chevron
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                if (folder.freeTestsBadge != null) {
                    Text(
                        text = folder.freeTestsBadge,
                        color = CtaGreen,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "Open",
                    tint = TextTertiaryDark,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Composable
private fun MetricItem(
    icon: ImageVector,
    iconColor: Color,
    iconBg: Color,
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.padding(horizontal = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(34.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(iconBg),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = iconColor,
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Column {
            Text(
                text = label,
                color = TextSecondaryDark,
                fontSize = 10.5.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = value,
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }
    }
}
