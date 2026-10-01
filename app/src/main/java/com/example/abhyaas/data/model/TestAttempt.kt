package com.example.abhyaas.data.model

data class TestAttempt(
    val attemptId: String,
    val testId: String,
    val completedAt: Long = System.currentTimeMillis(),
    val timeTakenSeconds: Long = 0,
    val selectedAnswers: Map<Int, Int> = emptyMap(),          // questionId -> optionIndex
    val questionStatus: Map<Int, QuestionStatus> = emptyMap(), // questionId -> QuestionStatus
    val questionTimeSpent: Map<Int, Long> = emptyMap(),       // questionId -> seconds
    val bookmarkedQuestions: Set<Int> = emptySet()
)
