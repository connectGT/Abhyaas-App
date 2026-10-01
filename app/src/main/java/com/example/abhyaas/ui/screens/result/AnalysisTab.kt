package com.example.abhyaas.ui.screens.result

import android.content.Intent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.HelpOutline
import androidx.compose.material.icons.outlined.HighlightOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.abhyaas.data.model.TestResult
import com.example.abhyaas.ui.theme.*

@Composable
fun AnalysisTab(
    testResult: TestResult,
    onSwitchToSolutions: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var selectedCategory by rememberSaveable { mutableStateOf("General") }
    var isCategoryDropdownOpen by remember { mutableStateOf(false) }

    val max = testResult.totalMarks
    val cutoffText = when (selectedCategory) {
        "General" -> "Cut off: ${(max * 0.66).toInt()}-${(max * 0.68).toInt()}"
        "OBC" -> "Cut off: ${(max * 0.64).toInt()}-${(max * 0.66).toInt()}"
        "SC" -> "Cut off: ${(max * 0.57).toInt()}-${(max * 0.60).toInt()}"
        "ST" -> "Cut off: ${(max * 0.54).toInt()}-${(max * 0.56).toInt()}"
        "EWS" -> "Cut off: ${(max * 0.62).toInt()}-${(max * 0.65).toInt()}"
        else -> "Cut off: ${(max * 0.66).toInt()}-${(max * 0.68).toInt()}"
    }

    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DarkBackground)
            .verticalScroll(scrollState)
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // QUICK SUMMARY Header Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "QUICK SUMMARY",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF94A3B8),
                letterSpacing = 0.5.sp
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Category Dropdown Pill
                Box {
                    Surface(
                        onClick = { isCategoryDropdownOpen = true },
                        shape = RoundedCornerShape(16.dp),
                        color = Color(0xFF1E2638),
                        border = BorderStroke(1.dp, Color(0xFF2E384D))
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = selectedCategory,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = BrandAccentCyan
                            )
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowDown,
                                contentDescription = "Select Category",
                                tint = BrandAccentCyan,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }

                    DropdownMenu(
                        expanded = isCategoryDropdownOpen,
                        onDismissRequest = { isCategoryDropdownOpen = false },
                        modifier = Modifier.background(DarkSurface)
                    ) {
                        listOf("General", "OBC", "SC", "ST", "EWS").forEach { cat ->
                            DropdownMenuItem(
                                text = { Text(cat, color = Color.White) },
                                onClick = {
                                    selectedCategory = cat
                                    isCategoryDropdownOpen = false
                                }
                            )
                        }
                    }
                }

                // Cutoff text
                Text(
                    text = cutoffText,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF94A3B8)
                )
            }
        }

        // 1. Rank Card (Orange flag icon, Rank 22789/24964)
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = DarkSurface,
            border = BorderStroke(1.dp, DarkBorderSubtle),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color(0xFFFFECEC)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Flag,
                            contentDescription = "Rank",
                            tint = Color(0xFFFF5757),
                            modifier = Modifier.size(22.dp)
                        )
                    }

                    Text(
                        text = "Rank",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                Row(
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = testResult.rank.toString(),
                        fontSize = 22.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White
                    )
                    Text(
                        text = "/${testResult.totalCandidates}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF94A3B8),
                        modifier = Modifier.padding(bottom = 2.dp)
                    )
                }
            }
        }

        // 2. Score Card (Purple trophy icon, Score 0/200, Average & Best score)
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = DarkSurface,
            border = BorderStroke(1.dp, DarkBorderSubtle),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(42.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(Color(0xFFF3E8FF)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.EmojiEvents,
                                contentDescription = "Score",
                                tint = Color(0xFFA855F7),
                                modifier = Modifier.size(22.dp)
                            )
                        }

                        Text(
                            text = "Score",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Text(
                            text = testResult.score.toInt().toString(),
                            fontSize = 22.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White
                        )
                        Text(
                            text = "/${testResult.totalMarks.toInt()}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF94A3B8),
                            modifier = Modifier.padding(bottom = 2.dp)
                        )
                    }
                }

                HorizontalDivider(color = DarkBorderSubtle, thickness = 1.dp)

                // Sub-row: Average Score & Best Score
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF131B28))
                        .padding(vertical = 12.dp, horizontal = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Average Score: ${"%.1f".format(testResult.averageScore)}   |   Best Score: ${testResult.bestScore.toInt()}",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF94A3B8)
                    )
                }
            }
        }

        // 3. Performance Breakdown Card (Percentile, Accuracy, Qs. Attempted, Status Pills)
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = DarkSurface,
            border = BorderStroke(1.dp, DarkBorderSubtle),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Row 1: Percentile
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(Color(0xFFFCE7F3)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Percentile",
                                tint = Color(0xFFEC4899),
                                modifier = Modifier.size(22.dp)
                            )
                        }

                        Text(
                            text = "Percentile",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White
                        )
                    }

                    Text(
                        text = "${"%.1f".format(testResult.percentile)} %",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White
                    )
                }

                // Row 2: Accuracy
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(Color(0xFFDCFCE7)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Brightness7,
                                contentDescription = "Accuracy",
                                tint = Color(0xFF22C55E),
                                modifier = Modifier.size(22.dp)
                            )
                        }

                        Text(
                            text = "Accuracy",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White
                        )
                    }

                    Text(
                        text = "${testResult.accuracy.toInt()} %",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White
                    )
                }

                // Row 3: Qs. Attempted
                val totalAttempted = testResult.correctCount + testResult.incorrectCount
                val totalQuestions = totalAttempted + testResult.unattemptedCount
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(Color(0xFFE0F2FE)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.Assignment,
                                contentDescription = "Qs Attempted",
                                tint = Color(0xFF0EA5E9),
                                modifier = Modifier.size(22.dp)
                            )
                        }

                        Text(
                            text = "Qs. Attempted",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White
                        )
                    }

                    Text(
                        text = "$totalAttempted/$totalQuestions",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                // Bottom Status Pills Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Correct Pill
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = Color(0xFF1E2838),
                        border = BorderStroke(1.dp, Color(0xFF2B3A4F)),
                        modifier = Modifier.weight(1f)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = "Correct",
                                tint = Color(0xFF10B981),
                                modifier = Modifier.size(15.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Correct: ${testResult.correctCount}",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White
                            )
                        }
                    }

                    // Incorrect Pill
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = Color(0xFF1E2838),
                        border = BorderStroke(1.dp, Color(0xFF2B3A4F)),
                        modifier = Modifier.weight(1f)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Cancel,
                                contentDescription = "Incorrect",
                                tint = Color(0xFFEF4444),
                                modifier = Modifier.size(15.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Incorrect: ${testResult.incorrectCount}",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White
                            )
                        }
                    }

                    // Unattempted Pill
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = Color(0xFF1E2838),
                        border = BorderStroke(1.dp, Color(0xFF2B3A4F)),
                        modifier = Modifier.weight(1.2f)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.Help,
                                contentDescription = "Unattempted",
                                tint = Color(0xFF94A3B8),
                                modifier = Modifier.size(15.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Unattempted: ${testResult.unattemptedCount}",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }

        // 4. Social Challenge Card matching result analysis.jpeg
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color(0xFF1C2227),
            border = BorderStroke(1.dp, Color(0xFF2D353C)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.linearGradient(
                            listOf(
                                Color(0xFF242B28),
                                Color(0xFF1B2124),
                                Color(0xFF2D251C)
                            )
                        )
                    )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "Challenge your Friends!",
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "Invite your friends for a challenge &\ncompare scores!",
                            fontSize = 12.sp,
                            color = Color(0xFFCBD5E1),
                            lineHeight = 16.sp
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Group,
                                contentDescription = null,
                                tint = Color(0xFF94A3B8),
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = "1.1k+ Students Challenged",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color(0xFF94A3B8)
                            )
                        }
                    }

                    // High-five Hands Illustration Canvas + WhatsApp Button
                    Column(
                        horizontalAlignment = Alignment.End,
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        // High-five illustration Canvas
                        HighFiveIllustration(
                            modifier = Modifier
                                .size(80.dp, 64.dp)
                        )

                        // WhatsApp Challenge Button
                        Surface(
                            onClick = {
                                val sendIntent = Intent().apply {
                                    action = Intent.ACTION_SEND
                                    putExtra(
                                        Intent.EXTRA_TEXT,
                                        "Hey! I scored ${testResult.score.toInt()}/${testResult.totalMarks.toInt()} in ${testResult.testTitle} on Abhyaas. Can you beat my score? Download Abhyaas: https://abhyaas.app"
                                    )
                                    type = "text/plain"
                                }
                                val shareIntent = Intent.createChooser(sendIntent, "Challenge your friends via")
                                context.startActivity(shareIntent)
                            },
                            shape = RoundedCornerShape(20.dp),
                            color = Color(0xFF25D366),
                            shadowElevation = 4.dp
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 7.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.Chat,
                                    contentDescription = "WhatsApp",
                                    tint = Color.White,
                                    modifier = Modifier.size(16.dp)
                                )
                                Text(
                                    text = "Challenge",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

/**
 * High-five hands illustration matching result analysis.jpeg
 */
@Composable
private fun HighFiveIllustration(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height

        // Left hand (skin tone)
        val leftHandColor = Color(0xFFFFD199)
        val sleeveBlue = Color(0xFF3B82F6)

        // Right hand
        val rightHandColor = Color(0xFFFDBA74)
        val sleeveIndigo = Color(0xFF6366F1)

        // Draw left sleeve
        drawRoundRect(
            color = sleeveBlue,
            topLeft = Offset(w * 0.28f, h * 0.65f),
            size = androidx.compose.ui.geometry.Size(w * 0.22f, h * 0.35f),
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(6f, 6f)
        )

        // Draw right sleeve
        drawRoundRect(
            color = sleeveIndigo,
            topLeft = Offset(w * 0.52f, h * 0.65f),
            size = androidx.compose.ui.geometry.Size(w * 0.22f, h * 0.35f),
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(6f, 6f)
        )

        // Draw hands clapping
        val leftHandPath = Path().apply {
            moveTo(w * 0.32f, h * 0.65f)
            lineTo(w * 0.35f, h * 0.25f)
            quadraticTo(w * 0.42f, h * 0.05f, w * 0.48f, h * 0.12f)
            lineTo(w * 0.48f, h * 0.65f)
            close()
        }
        drawPath(leftHandPath, leftHandColor)

        val rightHandPath = Path().apply {
            moveTo(w * 0.70f, h * 0.65f)
            lineTo(w * 0.66f, h * 0.25f)
            quadraticTo(w * 0.58f, h * 0.05f, w * 0.52f, h * 0.12f)
            lineTo(w * 0.52f, h * 0.65f)
            close()
        }
        drawPath(rightHandPath, rightHandColor)

        // Draw sparkle stars
        drawCircle(
            color = Color(0xFFFBBF24),
            radius = 3f,
            center = Offset(w * 0.50f, h * 0.08f)
        )
        drawCircle(
            color = Color(0xFFFDE68A),
            radius = 2.5f,
            center = Offset(w * 0.22f, h * 0.20f)
        )
        drawCircle(
            color = Color(0xFFFDE68A),
            radius = 2.5f,
            center = Offset(w * 0.78f, h * 0.22f)
        )
    }
}
