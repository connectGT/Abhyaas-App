package com.example.abhyaas.data.repository

import com.example.abhyaas.data.model.Question

interface QuestionRepository {
    suspend fun getQuestionsForTest(testId: String): Result<List<Question>>
    suspend fun bookmarkQuestion(questionId: Int, bookmarked: Boolean): Result<Unit>
    suspend fun getBookmarkedQuestions(): Result<List<Int>>
}
