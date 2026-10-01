package com.example.abhyaas.data.repository

import com.example.abhyaas.data.model.LeaderboardEntry
import com.example.abhyaas.data.model.TestResult

interface TestResultRepository {
    suspend fun submitTest(testId: String, answers: Map<Int, Int>, timeTakenSeconds: Long): Result<TestResult>
    suspend fun getTestResult(testId: String): Result<TestResult>
    suspend fun getLeaderboard(testId: String): Result<List<LeaderboardEntry>> =
        Result.success(com.example.abhyaas.data.mock.MockUserRepository.getLeaderboard(testId))
}
