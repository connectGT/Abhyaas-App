package com.example.abhyaas.ui.screens.result

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.abhyaas.data.model.LeaderboardEntry
import com.example.abhyaas.ui.theme.*

@Composable
fun LeaderboardTab(
    testId: String = "default",
    currentUserRank: Int = 22789,
    leaderboard: List<LeaderboardEntry> = emptyList(),
    modifier: Modifier = Modifier
) {
    val currentLeaderboard = leaderboard

    val top3 = remember(currentLeaderboard) { currentLeaderboard.filter { it.rank <= 3 }.take(3) }
    val subsequentRanks = remember(currentLeaderboard) { currentLeaderboard.filter { it.rank > 3 && !it.isCurrentUser } }
    val currentUserEntry = remember(currentLeaderboard) { currentLeaderboard.find { it.isCurrentUser } }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(DarkBackground)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 76.dp) // space for sticky bottom card
        ) {
            // Top 3 Podium matching leaderboard.jpeg
            item {
                PodiumSection(top3 = top3)
            }

            // Subsequent Ranks list (Ranks 4 to 9)
            items(subsequentRanks) { entry ->
                LeaderboardRankItem(entry = entry)
                HorizontalDivider(
                    color = Color(0xFF1E2838),
                    thickness = 0.8.dp,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }

        // Sticky Bottom Floating Card matching leaderboard.jpeg
        StickyCurrentUserCard(
            rank = currentUserEntry?.rank ?: currentUserRank,
            score = currentUserEntry?.score ?: 0.0f,
            maxScore = currentUserEntry?.maxScore ?: 200.0f,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

/**
 * Top 3 Podium layout matching leaderboard.jpeg
 */
@Composable
private fun PodiumSection(top3: List<LeaderboardEntry>) {
    val rank1 = top3.getOrNull(0)
    val rank2 = top3.getOrNull(1)
    val rank3 = top3.getOrNull(2)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xFF221F1B),
                        Color(0xFF151821),
                        DarkBackground
                    )
                )
            )
            .padding(top = 28.dp, bottom = 24.dp, start = 16.dp, end = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Bottom
        ) {
            // 2nd Place (Silver)
            if (rank2 != null) {
                PodiumItem(
                    rank = 2,
                    name = rank2.userName,
                    score = rank2.score,
                    maxScore = rank2.maxScore,
                    ringColor = PodiumSilver,
                    badgeBg = Color(0xFFE2E8F0),
                    badgeTextColor = Color(0xFF0F172A),
                    avatarSize = 68.dp,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
            } else {
                Spacer(modifier = Modifier.width(68.dp))
            }

            // 1st Place (Gold) - Elevated
            if (rank1 != null) {
                PodiumItem(
                    rank = 1,
                    name = rank1.userName,
                    score = rank1.score,
                    maxScore = rank1.maxScore,
                    ringColor = PodiumGold,
                    badgeBg = PodiumGold,
                    badgeTextColor = Color(0xFF0F172A),
                    avatarSize = 82.dp,
                    isFirst = true,
                    modifier = Modifier.padding(bottom = 32.dp)
                )
            } else {
                Spacer(modifier = Modifier.width(82.dp))
            }

            // 3rd Place (Bronze)
            if (rank3 != null) {
                PodiumItem(
                    rank = 3,
                    name = rank3.userName,
                    score = rank3.score,
                    maxScore = rank3.maxScore,
                    ringColor = PodiumBronze,
                    badgeBg = PodiumBronze,
                    badgeTextColor = Color.White,
                    avatarSize = 68.dp,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
            } else {
                Spacer(modifier = Modifier.width(68.dp))
            }
        }
    }
}

@Composable
private fun PodiumItem(
    rank: Int,
    name: String,
    score: Float,
    maxScore: Float,
    ringColor: Color,
    badgeBg: Color,
    badgeTextColor: Color,
    avatarSize: androidx.compose.ui.unit.Dp,
    modifier: Modifier = Modifier,
    isFirst: Boolean = false
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Box(
            contentAlignment = Alignment.TopEnd
        ) {
            // Avatar Circle with Ring
            Box(
                modifier = Modifier
                    .size(avatarSize)
                    .clip(CircleShape)
                    .background(Color(0xFF334155))
                    .border(3.dp, ringColor, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = name,
                    tint = Color(0xFF94A3B8),
                    modifier = Modifier.size(avatarSize * 0.58f)
                )
            }

            // Rank Badge Number
            Box(
                modifier = Modifier
                    .size(22.dp)
                    .offset(x = 2.dp, y = (-2).dp)
                    .clip(CircleShape)
                    .background(badgeBg),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = rank.toString(),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = badgeTextColor
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Student Name
        Text(
            text = name,
            fontSize = if (isFirst) 15.sp else 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(2.dp))

        // Score (e.g. 200/200.0)
        val scoreFormatted = if (score % 1.0f == 0.0f) score.toInt().toString() else score.toString()
        Text(
            text = "$scoreFormatted/$maxScore",
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF94A3B8)
        )
    }
}

/**
 * Individual Rank Item in the scrollable list for ranks 4 to 9.
 */
@Composable
private fun LeaderboardRankItem(
    entry: LeaderboardEntry,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Rank Number
        Text(
            text = entry.rank.toString(),
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF94A3B8),
            modifier = Modifier.width(28.dp)
        )

        // Avatar
        Box(
            modifier = Modifier
                .size(38.dp)
                .clip(CircleShape)
                .background(Color(0xFF334155)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = entry.userName,
                tint = Color(0xFFCBD5E1),
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Name
        Text(
            text = entry.userName,
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.White,
            modifier = Modifier.weight(1f)
        )

        // Score
        val scoreText = if (entry.score % 1.0f == 0.0f) entry.score.toInt().toString() else entry.score.toString()
        Text(
            text = "$scoreText/${entry.maxScore.toInt()}",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}

/**
 * Sticky Bottom Floating Card matching leaderboard.jpeg.
 * Periwinkle blue `#D0E2FF` with user's rank `22789`, Avatar + `(You)`, Score `0.0/200.0 Marks`.
 */
@Composable
private fun StickyCurrentUserCard(
    rank: Int,
    score: Float,
    maxScore: Float,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        color = BrandPeriwinkle,
        shadowElevation = 12.dp,
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // User Rank
                Text(
                    text = rank.toString(),
                    fontSize = 17.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF0F172A)
                )

                // Avatar
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF8FA6C4)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Current User",
                        tint = Color(0xFF0F172A),
                        modifier = Modifier.size(22.dp)
                    )
                }

                // Name: (You)
                Text(
                    text = "(You)",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0F172A)
                )
            }

            // Score and Marks label
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "$score/$maxScore",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF0F172A)
                )
                Text(
                    text = "Marks",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF334155)
                )
            }
        }
    }
}
