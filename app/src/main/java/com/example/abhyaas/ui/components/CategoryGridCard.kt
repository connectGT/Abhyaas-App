package com.example.abhyaas.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

object CategoryGradients {
    val StudyNotes = listOf(Color(0xFF7B2CBF), Color(0xFF4A148C))
    val PYQ = listOf(Color(0xFFB45309), Color(0xFF78350F))
    val Practice = listOf(Color(0xFF1D4ED8), Color(0xFF0F2C6E))
    val LiveTests = listOf(Color(0xFF6D28D9), Color(0xFF4314A7))
    val DailyClasses = listOf(Color(0xFFB91C1C), Color(0xFF7F1D1D))
    val Quiz = listOf(Color(0xFFBE185D), Color(0xFF831843))
    val CurrentAffairs = listOf(Color(0xFF0369A1), Color(0xFF073B6B))
}

@Composable
fun CategoryGridCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    gradientColors: List<Color>,
    modifier: Modifier = Modifier,
    badgeText: String? = null,
    badgeColor: Color = Color(0xFF10B981),
    height: Dp = 136.dp,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .height(height)
            .clip(RoundedCornerShape(14.dp))
            .background(Brush.linearGradient(colors = gradientColors))
            .clickable(onClick = onClick)
            .padding(14.dp)
    ) {
        // Decorative background watermark icon at bottom-right
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color.White.copy(alpha = 0.18f),
            modifier = Modifier
                .size(64.dp)
                .align(Alignment.BottomEnd)
                .offset(x = 8.dp, y = 8.dp)
        )

        // Card Content
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = title,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        modifier = Modifier.weight(1f)
                    )
                    if (badgeText != null) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(badgeColor)
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = badgeText,
                                color = Color.White,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.ExtraBold
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = subtitle,
                    color = Color.White.copy(alpha = 0.85f),
                    fontSize = 12.sp,
                    lineHeight = 16.sp
                )
            }

            // Small foreground action icon
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = Color.White.copy(alpha = 0.75f),
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}
