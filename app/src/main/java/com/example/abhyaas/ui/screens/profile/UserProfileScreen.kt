package com.example.abhyaas.ui.screens.profile

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.abhyaas.ui.components.CommonTopAppBar
import com.example.abhyaas.ui.components.ExamTitleDropdown
import com.example.abhyaas.ui.components.NavIconType
import com.example.abhyaas.ui.theme.*
import com.example.abhyaas.ui.viewmodel.UserProfileViewModel

enum class PrepTrackerTab {
    ACCURACY,
    TIME_SPENT,
    QUESTIONS
}

@Composable
fun UserProfileScreen(
    onNavigateBack: () -> Unit = {},
    onEditProfile: () -> Unit = {},
    onPrivacyPolicyClick: () -> Unit = {},
    viewModel: UserProfileViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    if (uiState.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = BrandPrimary)
        }
        return
    }

    val userProfile = uiState.profile ?: return
    var selectedTab by remember { mutableStateOf(PrepTrackerTab.QUESTIONS) }
    val dataPoints = uiState.trendDataPoints
    val scrollState = rememberScrollState()

    val targetExam = userProfile.targetExam ?: "Nayab Tehsildar"

    Scaffold(
        topBar = {
            CommonTopAppBar(
                title = "ABHYAS",
                navIconType = NavIconType.Back,
                onNavClick = onNavigateBack,
                customTitleContent = {
                    ExamTitleDropdown(
                        brandName = "ABHYAS",
                        examName = targetExam,
                        onExamClick = { /* Exam selection */ }
                    )
                },
                showSearch = true,
                showAvatar = true,
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
                .padding(start = 16.dp, end = 16.dp, bottom = 40.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(4.dp))

            // Greeting Card Banner with Mascot (photo click pr ye aaega.png)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(PassBannerBrush)
                    .border(1.dp, BrandPrimaryLight.copy(alpha = 0.4f), RoundedCornerShape(16.dp))
                    .padding(20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Good Morning, ${userProfile.fullName}!",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Let's keep going! 🚀",
                            color = BrandSkyBlue,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    // Mascot illustration avatar
                    Box(
                        modifier = Modifier
                            .size(68.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.15f))
                            .border(2.dp, BrandAccentCyan, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.School,
                            contentDescription = "Mascot",
                            tint = Color.White,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                }
            }

            // Section Header: "Your Preparation" with "View Dashboard >"
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
                        text = "Your Preparation",
                        color = Color.White,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Text(
                    text = "View Dashboard >",
                    color = BrandAccentCyan,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            // Preparation Tracker Card with Tabs and Trend Line Chart
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = DarkSurface),
                border = androidx.compose.foundation.BorderStroke(1.dp, DarkBorder)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    // 3 Metric Selector Tabs: Accuracy, Time Spent, Questions
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        PrepTabPill(
                            label = "🎯 Accuracy",
                            isSelected = selectedTab == PrepTrackerTab.ACCURACY,
                            activeColor = StatusCorrect,
                            modifier = Modifier.weight(1f),
                            onClick = {
                                selectedTab = PrepTrackerTab.ACCURACY
                                viewModel.selectTrendMetric("Accuracy")
                            }
                        )
                        PrepTabPill(
                            label = "⏱ Time Spent",
                            isSelected = selectedTab == PrepTrackerTab.TIME_SPENT,
                            activeColor = BrandAccentCyan,
                            modifier = Modifier.weight(1f),
                            onClick = {
                                selectedTab = PrepTrackerTab.TIME_SPENT
                                viewModel.selectTrendMetric("Time Spent")
                            }
                        )
                        PrepTabPill(
                            label = "✔ Questions",
                            isSelected = selectedTab == PrepTrackerTab.QUESTIONS,
                            activeColor = Color(0xFFF59E0B),
                            modifier = Modifier.weight(1f),
                            onClick = {
                                selectedTab = PrepTrackerTab.QUESTIONS
                                viewModel.selectTrendMetric("Questions")
                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    // Trend Line Chart
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(DarkCard)
                            .padding(horizontal = 14.dp, vertical = 12.dp)
                    ) {
                        PreparationTrendChart(
                            tab = selectedTab,
                            dataPoints = dataPoints
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Chart footer label
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = when (selectedTab) {
                                PrepTrackerTab.ACCURACY -> "Daily Accuracy Trend (%)"
                                PrepTrackerTab.TIME_SPENT -> "Daily Practice Time (Minutes)"
                                PrepTrackerTab.QUESTIONS -> "Daily Solved Questions Count"
                            },
                            color = TextSecondaryDark,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            // 3 Metric Summary Cards Row (photo click pr ye aaega.png)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                SummaryMetricCard(
                    modifier = Modifier.weight(1f),
                    metricValue = "${userProfile.averageScorePercent}%",
                    metricLabel = "YOUR AVG SCORE",
                    accentColor = BrandAccentCyan
                )
                SummaryMetricCard(
                    modifier = Modifier.weight(1f),
                    metricValue = "${userProfile.totalTestsAttempted}",
                    metricLabel = "YOUR TESTS",
                    accentColor = Color(0xFFF59E0B)
                )
                SummaryMetricCard(
                    modifier = Modifier.weight(1f),
                    metricValue = "${userProfile.totalStudyTimeHours}h",
                    metricLabel = "STUDY TIME",
                    accentColor = StatusCorrect
                )
            }

            // Profile Quick Actions
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = DarkSurface),
                border = androidx.compose.foundation.BorderStroke(1.dp, DarkBorder)
            ) {
                Column(modifier = Modifier.padding(8.dp)) {
                    ListItem(
                        headlineContent = { Text("Edit User Profile", color = TextPrimaryDark, fontWeight = FontWeight.SemiBold) },
                        supportingContent = { Text("Name, Education & Target Exam", color = TextSecondaryDark) },
                        leadingContent = {
                            Icon(Icons.Default.ManageAccounts, contentDescription = null, tint = BrandAccentCyan)
                        },
                        trailingContent = {
                            Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = TextTertiaryDark)
                        },
                        colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                        modifier = Modifier.clickable(onClick = onEditProfile)
                    )
                    HorizontalDivider(color = DarkBorderSubtle, modifier = Modifier.padding(horizontal = 8.dp))
                    ListItem(
                        headlineContent = { Text("Privacy Policy", color = TextPrimaryDark, fontWeight = FontWeight.SemiBold) },
                        supportingContent = { Text("Data security and terms", color = TextSecondaryDark) },
                        leadingContent = {
                            Icon(Icons.Default.Security, contentDescription = null, tint = BrandPrimaryLight)
                        },
                        trailingContent = {
                            Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = TextTertiaryDark)
                        },
                        colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                        modifier = Modifier.clickable(onClick = onPrivacyPolicyClick)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun PrepTabPill(
    label: String,
    isSelected: Boolean,
    activeColor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(20.dp),
        color = if (isSelected) activeColor.copy(alpha = 0.2f) else DarkCard,
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            if (isSelected) activeColor else DarkBorder
        ),
        modifier = modifier
    ) {
        Box(
            modifier = Modifier.padding(vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = label,
                color = if (isSelected) activeColor else TextSecondaryDark,
                fontSize = 11.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
            )
        }
    }
}

@Composable
private fun SummaryMetricCard(
    metricValue: String,
    metricLabel: String,
    accentColor: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = DarkSurface),
        border = androidx.compose.foundation.BorderStroke(1.dp, DarkBorder)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = metricValue,
                color = accentColor,
                fontSize = 22.sp,
                fontWeight = FontWeight.ExtraBold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = metricLabel,
                color = TextSecondaryDark,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }
    }
}

@Composable
private fun PreparationTrendChart(
    tab: PrepTrackerTab,
    dataPoints: List<com.example.abhyaas.data.model.PreparationDataPoint>
) {
    val lineColor = when (tab) {
        PrepTrackerTab.ACCURACY -> StatusCorrect
        PrepTrackerTab.TIME_SPENT -> BrandAccentCyan
        PrepTrackerTab.QUESTIONS -> Color(0xFFF59E0B)
    }

    val values = remember(tab, dataPoints) {
        dataPoints.map { dp ->
            when (tab) {
                PrepTrackerTab.ACCURACY -> dp.accuracyPercent.toFloat()
                PrepTrackerTab.TIME_SPENT -> dp.timeSpentMinutes.toFloat()
                PrepTrackerTab.QUESTIONS -> dp.questionsCount.toFloat()
            }
        }
    }

    val maxVal = remember(values) {
        (values.maxOrNull() ?: 60f).coerceAtLeast(10f)
    }

    Canvas(modifier = Modifier.fillMaxSize()) {
        val width = size.width
        val height = size.height
        val bottomPadding = 30f
        val topPadding = 20f
        val chartHeight = height - bottomPadding - topPadding

        if (values.size < 2) return@Canvas

        val stepX = width / (values.size - 1)

        val points = values.mapIndexed { index, value ->
            val x = index * stepX
            val normalizedY = (value / maxVal)
            val y = height - bottomPadding - (normalizedY * chartHeight)
            Offset(x, y)
        }

        // Draw horizontal grid lines
        val gridLines = 3
        for (i in 0..gridLines) {
            val y = topPadding + (i * chartHeight / gridLines)
            drawLine(
                color = Color(0xFF2E3D52).copy(alpha = 0.5f),
                start = Offset(0f, y),
                end = Offset(width, y),
                strokeWidth = 1f
            )
        }

        // Draw connecting line
        val path = Path().apply {
            moveTo(points.first().x, points.first().y)
            for (i in 1 until points.size) {
                lineTo(points[i].x, points[i].y)
            }
        }

        drawPath(
            path = path,
            color = lineColor,
            style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round)
        )

        // Draw data points & labels
        points.forEachIndexed { index, point ->
            // Circle outer
            drawCircle(
                color = lineColor,
                radius = 5.dp.toPx(),
                center = point
            )
            // Circle inner
            drawCircle(
                color = Color(0xFF1E293B),
                radius = 2.5.dp.toPx(),
                center = point
            )

            // Draw date label on X axis
            val label = dataPoints.getOrNull(index)?.dateLabel ?: ""
            drawContext.canvas.nativeCanvas.apply {
                val paint = android.graphics.Paint().apply {
                    color = android.graphics.Color.parseColor("#94A3B8")
                    textSize = 24f
                    textAlign = android.graphics.Paint.Align.CENTER
                    isAntiAlias = true
                }
                drawText(label, point.x, height - 4f, paint)

                // Value above point
                val valText = when (tab) {
                    PrepTrackerTab.ACCURACY -> "${values[index].toInt()}%"
                    PrepTrackerTab.TIME_SPENT -> "${values[index].toInt()}m"
                    PrepTrackerTab.QUESTIONS -> "${values[index].toInt()}q"
                }
                val valPaint = android.graphics.Paint().apply {
                    color = android.graphics.Color.WHITE
                    textSize = 22f
                    textAlign = android.graphics.Paint.Align.CENTER
                    isAntiAlias = true
                    typeface = android.graphics.Typeface.DEFAULT_BOLD
                }
                drawText(valText, point.x, (point.y - 12f).coerceAtLeast(topPadding), valPaint)
            }
        }
    }
}

