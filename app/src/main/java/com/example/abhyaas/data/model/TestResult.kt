package com.example.abhyaas.data.model

data class SectionResult(
    val sectionId: String,
    val sectionName: String,
    val score: Float,
    val correctCount: Int,
    val incorrectCount: Int,
    val unattemptedCount: Int,
    val markedCount: Int,
    val accuracy: Float,
    val timeTakenSeconds: Long,
    val totalQuestions: Int = 25
)

data class TestResult(
    val attemptId: String,
    val testId: String,
    val testTitle: String,
    val score: Float,
    val totalMarks: Float = 200.0f,
    val rank: Int,
    val totalCandidates: Int = 24964,
    val percentile: Float,
    val accuracy: Float,
    val correctCount: Int,
    val incorrectCount: Int,
    val unattemptedCount: Int,
    val cutoffMarks: String = "132-135",
    val averageScore: Float = 67.75f,
    val bestScore: Float = 200.0f,
    val sectionBreakdowns: List<SectionResult>,
    val attemptDate: String = "Sep 27, 2026",
    val userAnswers: Map<Int, Int>? = null
)
