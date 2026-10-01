package com.example.abhyaas.tier4_scenarios

import com.example.abhyaas.contract.ExamEvaluationEngine
import com.example.abhyaas.contract.MockLeaderboardRepository
import org.junit.Assert.*
import org.junit.Test

/**
 * Tier 4: Real-World Workload Scenarios - Leaderboard Ranking, Podium Separation & Percentile
 */
class LeaderboardRankingScenarioTest {

    @Test
    fun testLeaderboardPodiumAndRankingOrder() {
        val leaderboard = MockLeaderboardRepository.getLeaderboard()
        val totalCandidates = 24964

        // Split into Top 3 Podium and general ranks
        val podium = leaderboard.take(3)
        assertEquals(3, podium.size)

        // Rank 1: Gold (Raja)
        val rank1 = podium[0]
        assertEquals(1, rank1.rank)
        assertEquals("Raja", rank1.userName)
        assertEquals(200.0f, rank1.score, 0.001f)
        assertEquals(100.0f, rank1.accuracy, 0.001f)

        // Rank 2: Silver (Hemant)
        val rank2 = podium[1]
        assertEquals(2, rank2.rank)
        assertEquals("Hemant", rank2.userName)
        assertEquals(195.0f, rank2.score, 0.001f)

        // Rank 3: Bronze (Vivek)
        val rank3 = podium[2]
        assertEquals(3, rank3.rank)
        assertEquals("Vivek", rank3.userName)
        assertEquals(193.5f, rank3.score, 0.001f)

        // Monotonic decrease in top scores
        assertTrue(rank1.score >= rank2.score)
        assertTrue(rank2.score >= rank3.score)

        // Subsequent ranks 4 to 8
        val subsequentRanks = leaderboard.drop(3).dropLast(1)
        subsequentRanks.zipWithNext { a, b ->
            assertTrue(a.rank < b.rank)
            assertTrue(a.score >= b.score)
        }

        // Current User Sticky Bottom Card
        val userSticky = leaderboard.last()
        assertTrue(userSticky.isCurrentUser)
        assertEquals(22789, userSticky.rank)
        assertEquals("Aspirant (You)", userSticky.userName)
        assertEquals(0.0f, userSticky.score, 0.001f)

        // Percentile for current user: (24964 - 22789) / 24964 * 100 ~ 8.71%
        val userPercentile = ExamEvaluationEngine.calculatePercentile(userSticky.rank, totalCandidates)
        assertEquals(8.71f, userPercentile, 0.05f)
    }
}
