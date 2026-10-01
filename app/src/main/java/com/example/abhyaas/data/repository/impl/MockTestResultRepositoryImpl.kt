package com.example.abhyaas.data.repository.impl

import com.example.abhyaas.AbhyaasApplication
import com.example.abhyaas.data.mock.MockExamRepository
import com.example.abhyaas.data.model.*
import com.example.abhyaas.data.repository.TestResultRepository
import kotlinx.coroutines.delay

class MockTestResultRepositoryImpl : TestResultRepository {
    private var lastResult: TestResult? = null // Store it in memory for getTestResult

    override suspend fun submitTest(testId: String, answers: Map<Int, Int>, timeTakenSeconds: Long): Result<TestResult> {
        delay(600)
        val questions = AbhyaasApplication.instance.questionRepository.getQuestionsForTest(testId).getOrDefault(emptyList())
        
        var correctCount = 0
        var incorrectCount = 0
        var score = 0f
        
        questions.forEach { q ->
            val userAnswer = answers[q.id]
            if (userAnswer != null) {
                if (userAnswer == q.correctOptionIndex) {
                    correctCount++
                    score += 2f
                } else {
                    incorrectCount++
                    score -= 0.5f
                }
            }
        }
        
        val totalQ = questions.size.takeIf { it > 0 } ?: 100
        val unattemptedCount = totalQ - correctCount - incorrectCount
        val accuracy = if (correctCount + incorrectCount > 0) (correctCount.toFloat() / (correctCount + incorrectCount)) * 100f else 0f
        
        val baseResult = MockExamRepository.getPreviousAttemptResult(testId)
        val maxScore = (totalQ * 2).toFloat()
        
        // Dynamically compute mock percentiles, rank, and metrics based on actual score!
        val rawPercentage = if (maxScore > 0) (score / maxScore) * 100f else 0f
        
        // Dynamic rank between 1 and 24964 based on percentage (if 100%, rank 1)
        val totalCandidates = 24964
        val computedRank = if (rawPercentage >= 99f) 1 else {
            val offset = (100f - rawPercentage) / 100f
            (offset * totalCandidates).toInt().coerceIn(1, totalCandidates)
        }
        
        val computedPercentile = if (totalCandidates > 1) {
            ((totalCandidates - computedRank).toFloat() / (totalCandidates - 1)) * 100f
        } else 100f
        
        val avgScore = maxScore * 0.45f
        val bstScore = maxScore * 0.95f
        
        val newResult = baseResult.copy(
            score = score,
            correctCount = correctCount,
            incorrectCount = incorrectCount,
            unattemptedCount = unattemptedCount,
            accuracy = accuracy,
            totalMarks = maxScore,
            userAnswers = answers,
            rank = computedRank,
            totalCandidates = totalCandidates,
            percentile = computedPercentile,
            averageScore = avgScore,
            bestScore = bstScore
        )
        
        lastResult = newResult
        return Result.success(newResult)
    }

    override suspend fun getTestResult(testId: String): Result<TestResult> {
        delay(300)
        return Result.success(lastResult ?: MockExamRepository.getPreviousAttemptResult(testId))
    }

    override suspend fun getLeaderboard(testId: String): Result<List<LeaderboardEntry>> {
        delay(300)
        // Dynamic leaderboard based on "database" (in-memory lastResult)
        val result = lastResult
        if (result != null && result.testId == testId) {
            val userEntry = LeaderboardEntry(
                rank = 1,
                userName = "Aspirant (You)",
                score = result.score,
                maxScore = result.totalMarks,
                accuracy = result.accuracy,
                timeTaken = "Completed",
                isCurrentUser = true
            )
            return Result.success(listOf(userEntry))
        }
        return Result.success(emptyList())
    }
}
