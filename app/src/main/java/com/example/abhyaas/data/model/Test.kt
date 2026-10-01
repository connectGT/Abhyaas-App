package com.example.abhyaas.data.model

data class TestSection(
    val id: String,
    val partName: String, // e.g. "PART - A", "PART - B"
    val title: String,    // e.g. "General Intelligence", "General Awareness"
    val titleHindi: String? = null,
    val questions: List<Question>
)

data class TestAttemptSummary(
    val attemptId: String,
    val score: Float,
    val maxScore: Float = 200.0f,
    val rank: Int = 22789,
    val totalCandidates: Int = 24964,
    val attemptDate: String = "Sep 27, 2026",
    val accuracy: Float = 0.0f
)

val defaultTestInstructions = listOf(
    "The Test contains 100 questions.",
    "Each question has 4 options out of which only one is correct.",
    "You have to finish the test in 60 minutes.",
    "Each section contains 25 questions and you will be given 15 minutes to complete each of them.",
    "You will be awarded 2 marks for each correct answer and there is 0.5 negative marking.",
    "There is no penalty for the questions that you have not attempted.",
    "I have read all the instructions carefully and have understood them. I agree not to cheat or use unfair means in this examination."
)

data class Test(
    val id: String,
    val seriesId: String,
    val title: String,
    val subCategory: String, // "Exam Day Special", "Full Test", "PYQ"
    val durationMinutes: Int = 60,
    val totalQuestions: Int = 100,
    val totalMarks: Float = 200.0f,
    val isFree: Boolean = true,
    val supportedLanguages: List<String> = listOf("English", "Hindi"),
    val sections: List<TestSection>,
    val instructions: List<String> = defaultTestInstructions,
    val cutoffGeneral: String = "132-135",
    val cutoffObc: String = "128-132",
    val cutoffScSt: String = "115-120",
    val averageScore: Float = 67.75f,
    val bestScore: Float = 200.0f,
    val previousAttempt: TestAttemptSummary? = null
)
