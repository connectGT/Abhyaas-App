package com.example.abhyaas.data.repository.impl

import com.example.abhyaas.data.mock.MockExamRepository
import com.example.abhyaas.data.mock.MockUserRepository
import com.example.abhyaas.data.model.LeaderboardEntry
import com.example.abhyaas.data.model.TestResult
import com.example.abhyaas.data.network.ApiService
import com.example.abhyaas.data.network.RetrofitClient
import com.example.abhyaas.data.network.dto.SubmitTestRequest
import com.example.abhyaas.data.network.dto.toDomain
import com.example.abhyaas.data.repository.TestResultRepository

class RemoteTestResultRepositoryImpl(
    private val api: ApiService = RetrofitClient.apiService
) : TestResultRepository {

    override suspend fun submitTest(
        testId: String,
        answers: Map<Int, Int>,
        timeTakenSeconds: Long
    ): Result<TestResult> = runCatching {
        try {
            val response = api.submitTest(
                testId = testId,
                body = SubmitTestRequest(
                    testId = testId,
                    answers = answers,
                    timeTakenSeconds = timeTakenSeconds
                )
            )
            if (response.isSuccessful && response.body() != null) {
                response.body()!!.toDomain()
            } else {
                MockExamRepository.getPreviousAttemptResult(testId)
            }
        } catch (_: Exception) {
            MockExamRepository.getPreviousAttemptResult(testId)
        }
    }.recover { MockExamRepository.getPreviousAttemptResult(testId) }

    override suspend fun getTestResult(testId: String): Result<TestResult> = runCatching {
        try {
            val response = api.getTestResult(testId)
            if (response.isSuccessful && response.body() != null) {
                response.body()!!.toDomain()
            } else {
                MockExamRepository.getPreviousAttemptResult(testId)
            }
        } catch (_: Exception) {
            MockExamRepository.getPreviousAttemptResult(testId)
        }
    }.recover { MockExamRepository.getPreviousAttemptResult(testId) }

    override suspend fun getLeaderboard(testId: String): Result<List<LeaderboardEntry>> = runCatching {
        try {
            val response = api.getLeaderboard(testId)
            if (response.isSuccessful && !response.body().isNullOrEmpty()) {
                response.body()!!.map { it.toDomain() }
            } else {
                MockUserRepository.getLeaderboard(testId)
            }
        } catch (_: Exception) {
            MockUserRepository.getLeaderboard(testId)
        }
    }.recover { MockUserRepository.getLeaderboard(testId) }
}
