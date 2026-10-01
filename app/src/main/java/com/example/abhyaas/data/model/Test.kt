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

val defaultTestInstructionsHindi = listOf(
    "इस परीक्षा में 100 प्रश्न हैं।",
    "प्रत्येक प्रश्न के 4 विकल्प हैं जिनमें से केवल एक ही सही है।",
    "आपको यह परीक्षा 60 मिनट में पूरी करनी होगी।",
    "प्रत्येक अनुभाग में 25 प्रश्न हैं और आपको प्रत्येक अनुभाग को पूरा करने के लिए 15 मिनट दिए जाएंगे।",
    "प्रत्येक सही उत्तर के लिए आपको 2 अंक दिए जाएंगे और 0.5 नकारात्मक अंकन है।",
    "आपके द्वारा हल न किए गए प्रश्नों के लिए कोई अंक नहीं काटा जाएगा।",
    "मैंने सभी निर्देशों को ध्यानपूर्वक पढ़ लिया है और उन्हें समझ लिया है। मैं इस परीक्षा में नकल न करने या अनुचित साधनों का उपयोग न करने की सहमति देता हूँ।"
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
    val instructionsHindi: List<String> = defaultTestInstructionsHindi,
    val cutoffGeneral: String = "132-135",
    val cutoffObc: String = "128-132",
    val cutoffScSt: String = "115-120",
    val averageScore: Float = 67.75f,
    val bestScore: Float = 200.0f,
    val previousAttempt: TestAttemptSummary? = null
)
