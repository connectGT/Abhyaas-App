import io

with io.open(r"app\src\main\java\com\example\abhyaas\ui\screens\result\LeaderboardTab.kt", "r", encoding="utf-8") as f:
    content = f.read()

# 1. Remove the fallbacks
content = content.replace(
    """val rank1 = top3.getOrNull(0) ?: LeaderboardEntry(1, "Raja", score = 200.0f)
    val rank2 = top3.getOrNull(1) ?: LeaderboardEntry(2, "Hemant", score = 195.0f)
    val rank3 = top3.getOrNull(2) ?: LeaderboardEntry(3, "Vivek", score = 193.5f)""",
    """val rank1 = top3.getOrNull(0)
    val rank2 = top3.getOrNull(1)
    val rank3 = top3.getOrNull(2)"""
)

# 2. Wrap PodiumItems in null checks
# We will do this carefully using string replacement.
old_podium_row = """        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Bottom
        ) {
            // 2nd Place: Hemant (Silver)
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

            // 1st Place: Raja (Gold) - Elevated
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

            // 3rd Place: Vivek (Bronze)
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
        }"""

new_podium_row = """        Row(
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
        }"""

content = content.replace(old_podium_row, new_podium_row)

with io.open(r"app\src\main\java\com\example\abhyaas\ui\screens\result\LeaderboardTab.kt", "w", encoding="utf-8") as f:
    f.write(content)

print("Podium patched.")
