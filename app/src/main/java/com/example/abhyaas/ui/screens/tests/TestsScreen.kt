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
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.abhyaas.ui.components.CommonTopAppBar
import com.example.abhyaas.ui.components.ExamTitleDropdown
import com.example.abhyaas.ui.components.NavIconType
import com.example.abhyaas.ui.theme.*
import com.example.abhyaas.ui.viewmodel.TestsViewModel

@Composable
fun TestsScreen(
    onMenuClick: () -> Unit = {},
    onAvatarClick: () -> Unit = {},
    onTestSeriesClick: (String) -> Unit = {},
    onSearchClick: () -> Unit = {},
    viewModel: TestsViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()

    val featuredSeries = uiState.featuredSeries ?: uiState.testSeriesList.firstOrNull()
    val enrolledList = if (uiState.enrolledSeries.isNotEmpty()) uiState.enrolledSeries else uiState.testSeriesList
    val otherList = if (uiState.otherSeries.isNotEmpty()) uiState.otherSeries else uiState.testSeriesList.drop(1)

    val topExamName = featuredSeries?.categoryId?.replace('_', ' ')?.uppercase() ?: "SSC CGL"

    Scaffold(
        topBar = {
            CommonTopAppBar(
                title = "ABHYAS",
                navIconType = NavIconType.Menu,
                onNavClick = onMenuClick,
                customTitleContent = {
                    ExamTitleDropdown(
                        brandName = "ABHYAS",
                        examName = topExamName,
                        onExamClick = { /* Exam switcher */ }
                    )
                },
                showSearch = true,
                onSearchClick = onSearchClick,
                showAvatar = true,
                onAvatarClick = onAvatarClick,
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
                .verticalScroll(scrollState)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(4.dp))

            // Hero Banner Carousel Card (matching tests tab.png)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(PassBannerBrush)
                    .border(1.dp, BrandPrimaryLight.copy(alpha = 0.5f), RoundedCornerShape(16.dp))
                    .padding(18.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = BrandPrimaryLight.copy(alpha = 0.3f),
                            border = androidx.compose.foundation.BorderStroke(1.dp, BrandAccentCyan)
                        ) {
                            Text(
                                text = "FEATURED EXAM",
                                color = BrandAccentCyan,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                            )
                        }

                        Text(
                            text = featuredSeries?.examDates?.let { "Exam: $it" } ?: "Exam: 2026",
                            color = Color.White.copy(alpha = 0.85f),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = featuredSeries?.title ?: "MP Nayab Tehsildar 2026",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 0.5.sp
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    val detailsText = if (featuredSeries != null) {
                        "${featuredSeries.totalTests}+ Total Tests • ${featuredSeries.fullTestsCount} Full Tests • ${featuredSeries.pyqCount}+ PYQs • ${featuredSeries.vacancies ?: "3000+ Vacancies"}"
                    } else {
                        "600+ Total Tests • 30 Full Tests • 90+ PYQs • 3000+ Vacancies"
                    }

                    Text(
                        text = detailsText,
                        color = Color.White.copy(alpha = 0.9f),
                        fontSize = 12.sp,
                        lineHeight = 16.sp
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Dynamic Carousel indicator dots
                        val dotCount = uiState.testSeriesList.size.coerceIn(1, 5)
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            repeat(dotCount) { index ->
                                Box(
                                    modifier = Modifier
                                        .size(8.dp)
                                        .clip(CircleShape)
                                        .background(if (index == 0) BrandAccentCyan else Color.White.copy(alpha = 0.4f))
                                )
                            }
                        }

                        Button(
                            onClick = {
                                val targetId = featuredSeries?.id ?: enrolledList.firstOrNull()?.id ?: "nayab_tehsildar_2026"
                                onTestSeriesClick(targetId)
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = CtaGreen,
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            Text(
                                text = "View Test Series →",
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp
                            )
                        }
                    }
                }
            }

            // Section Header: "Enrolled Test Series"
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .width(4.dp)
                            .height(20.dp)
                            .clip(RoundedCornerShape(2.dp))
                            .background(BrandAccentCyan)
                    )
                    Text(
                        text = "Enrolled Test Series",
                        color = Color.White,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Text(
                    text = "View All →",
                    color = BrandAccentCyan,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.clickable {
                        val firstId = enrolledList.firstOrNull()?.id ?: "nayab_tehsildar_2026"
                        onTestSeriesClick(firstId)
                    }
                )
            }

            // Dynamic Enrolled Test Series Cards with Progress Bar
            enrolledList.forEach { series ->
                val badge = when {
                    series.categoryId.contains("mp") || series.id.contains("tehsildar") || series.id.contains("mpseb") -> "MP"
                    else -> "SSC"
                }
                val badgeColor = if (badge == "MP") Color(0xFFF59E0B) else BrandAccentCyan
                val progressFloat = (series.attemptedCount.toFloat() / series.totalTests.coerceAtLeast(1)).coerceIn(0f, 1f)
                val progressPercent = (progressFloat * 100).toInt()

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .border(1.dp, DarkBorder, RoundedCornerShape(14.dp))
                        .clickable { onTestSeriesClick(series.id) },
                    colors = CardDefaults.cardColors(containerColor = DarkSurface)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(46.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(DarkCard)
                                    .border(1.dp, badgeColor.copy(alpha = 0.6f), RoundedCornerShape(10.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = badge,
                                    color = badgeColor,
                                    fontWeight = FontWeight.Black,
                                    fontSize = 14.sp
                                )
                            }

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = series.title,
                                    color = TextPrimaryDark,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    maxLines = 2
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = "${series.totalTests} Total Tests • ${series.fullTestsCount} Full Tests",
                                    color = TextSecondaryDark,
                                    fontSize = 12.sp
                                )
                            }

                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = "Open Series",
                                tint = BrandAccentCyan,
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "${series.attemptedCount} / ${series.totalTests} Attempted",
                                color = BrandPrimaryLight,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = "$progressPercent% Completed",
                                color = TextTertiaryDark,
                                fontSize = 11.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        LinearProgressIndicator(
                            progress = { progressFloat },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(6.dp)
                                .clip(RoundedCornerShape(3.dp)),
                            color = BrandPrimary,
                            trackColor = DarkCardElevated
                        )
                    }
                }
            }

            // Circular Quick Action Shortcuts Row (tests tab.png)
            val activeSeriesId = enrolledList.firstOrNull()?.id ?: featuredSeries?.id ?: "nayab_tehsildar_2026"
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                QuickActionShortcut(
                    icon = Icons.Default.Description,
                    label = "Study Notes",
                    badge = "NEW",
                    backgroundColor = Color(0xFF6B3A8B),
                    onClick = { onTestSeriesClick(activeSeriesId) }
                )
                QuickActionShortcut(
                    icon = Icons.Default.PlayCircle,
                    label = "Live Test",
                    backgroundColor = Color(0xFFB91C1C),
                    onClick = { onTestSeriesClick(activeSeriesId) }
                )
                QuickActionShortcut(
                    icon = Icons.Default.Quiz,
                    label = "Live Quizzes",
                    badge = "FREE",
                    backgroundColor = Color(0xFFB45309),
                    onClick = { onTestSeriesClick(activeSeriesId) }
                )
                QuickActionShortcut(
                    icon = Icons.Default.HistoryEdu,
                    label = "Prev. Papers",
                    backgroundColor = Color(0xFF1D4ED8),
                    onClick = { onTestSeriesClick(activeSeriesId) }
                )
            }

            // Other Exam Series List (dynamically rendered)
            if (otherList.isNotEmpty()) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .width(4.dp)
                            .height(18.dp)
                            .clip(RoundedCornerShape(2.dp))
                            .background(BrandAccentCyan)
                    )
                    Text(
                        text = "State & Departmental Exams",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                otherList.forEach { otherSeries ->
                    val otherBadge = when {
                        otherSeries.categoryId.contains("mp") || otherSeries.id.contains("tehsildar") || otherSeries.id.contains("mpseb") -> "MP"
                        else -> "EX"
                    }
                    val otherBadgeColor = if (otherBadge == "MP") Color(0xFFF59E0B) else BrandAccentCyan

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(14.dp))
                            .border(1.dp, DarkBorder, RoundedCornerShape(14.dp))
                            .clickable { onTestSeriesClick(otherSeries.id) },
                        colors = CardDefaults.cardColors(containerColor = DarkSurface)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(46.dp)
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(DarkCard)
                                        .border(1.dp, otherBadgeColor, RoundedCornerShape(10.dp)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = otherBadge,
                                        color = otherBadgeColor,
                                        fontWeight = FontWeight.Black,
                                        fontSize = 14.sp
                                    )
                                }

                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = otherSeries.title,
                                        color = TextPrimaryDark,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        maxLines = 2
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = "${otherSeries.vacancies ?: "Multiple Posts"} • ${otherSeries.totalTests}+ Practice Tests • ${otherSeries.examDates ?: "2026"}",
                                        color = TextSecondaryDark,
                                        fontSize = 12.sp
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            Button(
                                onClick = { onTestSeriesClick(otherSeries.id) },
                                modifier = Modifier.fillMaxWidth(),
                                colors = ButtonDefaults.buttonColors(containerColor = BrandPrimary),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text("View Test Series →", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun QuickActionShortcut(
    icon: ImageVector,
    label: String,
    backgroundColor: Color,
    badge: String? = null,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(4.dp)
    ) {
        Box(contentAlignment = Alignment.TopEnd) {
            Box(
                modifier = Modifier
                    .size(54.dp)
                    .clip(CircleShape)
                    .background(backgroundColor)
                    .border(1.5.dp, Color.White.copy(alpha = 0.2f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = label,
                    tint = Color.White,
                    modifier = Modifier.size(26.dp)
                )
            }

            if (badge != null) {
                Box(
                    modifier = Modifier
                        .offset(x = 4.dp, y = (-2).dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(if (badge == "NEW") BrandPrimaryLight else CtaGreen)
                        .padding(horizontal = 4.dp, vertical = 1.dp)
                ) {
                    Text(
                        text = badge,
                        color = Color.White,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Black
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = label,
            color = TextSecondaryDark,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium
        )
    }
}
