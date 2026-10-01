package com.example.abhyaas.data.repository.impl

import com.example.abhyaas.data.mock.MockExamRepository
import com.example.abhyaas.data.mock.MockQuestionRepository
import com.example.abhyaas.data.model.Question
import com.example.abhyaas.data.network.ApiService
import com.example.abhyaas.data.network.RetrofitClient
import com.example.abhyaas.data.network.dto.toDomain
import com.example.abhyaas.data.repository.QuestionRepository

class RemoteQuestionRepositoryImpl(
    private val api: ApiService = RetrofitClient.apiService
) : QuestionRepository {

    private val bookmarkedIds = mutableSetOf<Int>()

    override suspend fun getQuestionsForTest(testId: String): Result<List<Question>> = runCatching {
        try {
            val response = api.getQuestions(testId)
            if (response.isSuccessful && !response.body().isNullOrEmpty()) {
                response.body()!!.map { it.toDomain() }
            } else {
                getMockQuestions(testId)
            }
        } catch (_: Exception) {
            getMockQuestions(testId)
        }
    }.recover { getMockQuestions(testId) }

    private fun getMockQuestions(testId: String): List<Question> {
        val test = MockExamRepository.getTestById(testId)
        val questionsFromSections = test?.sections?.flatMap { it.questions }
        return if (!questionsFromSections.isNullOrEmpty()) {
            questionsFromSections
        } else {
            MockQuestionRepository.getQuestionsForTest(testId)
        }
    }

    override suspend fun bookmarkQuestion(questionId: Int, bookmarked: Boolean): Result<Unit> = runCatching {
        if (bookmarked) {
            bookmarkedIds.add(questionId)
        } else {
            bookmarkedIds.remove(questionId)
        }
        Unit
    }

    override suspend fun getBookmarkedQuestions(): Result<List<Int>> = runCatching {
        bookmarkedIds.toList()
    }
}
