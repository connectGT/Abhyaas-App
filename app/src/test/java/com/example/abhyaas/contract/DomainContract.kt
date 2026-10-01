package com.example.abhyaas.contract

// --- Core Option & Question Models ---
data class Option(
    val id: Int,
    val text: String,
    val textHindi: String? = null
)

enum class QuestionStatus {
    NOT_VISITED,
    UNANSWERED,             // Blue square (Visited, no answer chosen)
    ANSWERED,               // Green square (Answer chosen and saved)
    MARKED_FOR_REVIEW,      // Red/pink ribbon (Marked without answer)
    ANSWERED_AND_MARKED     // Yellow/orange ribbon (Answer chosen and marked for review)
}

data class Question(
    val id: Int,
    val sectionId: String,
    val questionNumber: Int,
    val directionText: String? = null,
    val directionTextHindi: String? = null,
    val statementText: String,
    val statementTextHindi: String? = null,
    val options: List<Option>,
    val correctOptionIndex: Int,
    val explanation: String,
    val explanationHindi: String? = null,
    val positiveMarks: Float = 2.0f,
    val negativeMarks: Float = 0.5f,
    val topic: String,
    val subject: String,
    val percentGotRight: Int = 64,
    val averageTimeSeconds: Int = 45,
    val isBookmarked: Boolean = false
)

// --- Test Structure Models ---
data class TestSection(
    val id: String,
    val partName: String, // "PART - A", "PART - B", "PART - C", "PART - D"
    val title: String,    // "General Intelligence", "General Awareness", etc.
    val titleHindi: String? = null,
    val questions: List<Question>
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
    val bestScore: Float = 200.0f
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

// --- Test Attempt & Evaluation Models ---
data class TestAttempt(
    val attemptId: String,
    val testId: String,
    val completedAt: Long,
    val timeTakenSeconds: Long,
    val selectedAnswers: Map<Int, Int>,         // questionId -> optionIndex
    val questionStatus: Map<Int, QuestionStatus>,
    val questionTimeSpent: Map<Int, Long>,      // questionId -> seconds
    val bookmarkedQuestions: Set<Int> = emptySet()
)

data class SectionResult(
    val sectionId: String,
    val sectionName: String,
    val score: Float,
    val correctCount: Int,
    val incorrectCount: Int,
    val unattemptedCount: Int,
    val markedCount: Int,
    val accuracy: Float,
    val timeTakenSeconds: Long
)

data class TestResult(
    val attemptId: String,
    val testId: String,
    val testTitle: String,
    val score: Float,
    val totalMarks: Float,
    val rank: Int,
    val totalCandidates: Int,
    val percentile: Float,
    val accuracy: Float,
    val correctCount: Int,
    val incorrectCount: Int,
    val unattemptedCount: Int,
    val cutoffMarks: String,
    val averageScore: Float,
    val bestScore: Float,
    val sectionBreakdowns: List<SectionResult>,
    val attemptDate: String
)

// --- Leaderboard & Ranking ---
data class LeaderboardEntry(
    val rank: Int,
    val userName: String,
    val avatarUrl: String? = null,
    val score: Float,
    val maxScore: Float = 200.0f,
    val accuracy: Float = 95.0f,
    val timeTaken: String = "48m 20s",
    val isCurrentUser: Boolean = false
)

// --- Categories, Series & Updates ---
data class ExamCategory(
    val id: String,
    val name: String,
    val code: String,
    val iconName: String
)

data class TestSeries(
    val id: String,
    val title: String,
    val subtitle: String,
    val categoryId: String,
    val totalTests: Int,
    val fullTestsCount: Int,
    val pyqCount: Int,
    val attemptedCount: Int,
    val vacancies: String? = null,
    val examDates: String? = null,
    val isEnrolled: Boolean = true
)

enum class UpdateCategory {
    ALL,
    NOTIFICATIONS,
    ADMIT_CARD,
    RESULTS,
    SYLLABUS,
    EXAM_DATES,
    OFFICIAL_PDFS
}

data class ExamUpdateItem(
    val id: String,
    val title: String,
    val description: String,
    val date: String,
    val category: UpdateCategory,
    val isPinned: Boolean = false,
    val pdfSize: String? = null,
    val actionText: String = "Download PDF"
)

// --- User Profile & Preparation Stats ---
data class UserProfile(
    val fullName: String = "Aspirant",
    val email: String = "aspirant@abhyaas.edu",
    val mobileNumber: String = "+91 98765 43210",
    val dateOfBirth: String = "15/08/2000",
    val category: String = "General",
    val pinCode: String = "462001",
    val education: String = "Graduation",
    val avatarRes: String = "avatar_default",
    val averageScorePercent: Int = 55,
    val totalTestsAttempted: Int = 7,
    val totalStudyTimeHours: Int = 2
)

data class PreparationDataPoint(
    val dateLabel: String,
    val questionsCount: Int
)

enum class SolutionFilter {
    ALL,
    CORRECT,
    INCORRECT,
    UNATTEMPTED
}

// --- Scoring and Stat Engine ---
object ExamEvaluationEngine {

    fun evaluateExam(
        test: Test,
        attempt: TestAttempt,
        totalCandidates: Int = 24964,
        rank: Int = 22789,
        attemptDate: String = "29 Sep 2026"
    ): TestResult {
        var totalScore = 0.0f
        var totalCorrect = 0
        var totalIncorrect = 0
        var totalUnattempted = 0

        val sectionResults = test.sections.map { section ->
            var secScore = 0.0f
            var secCorrect = 0
            var secIncorrect = 0
            var secUnattempted = 0
            var secMarked = 0

            section.questions.forEach { question ->
                val chosenOption = attempt.selectedAnswers[question.id]
                val status = attempt.questionStatus[question.id] ?: QuestionStatus.NOT_VISITED

                if (status == QuestionStatus.MARKED_FOR_REVIEW || status == QuestionStatus.ANSWERED_AND_MARKED) {
                    secMarked++
                }

                if (chosenOption == null) {
                    secUnattempted++
                } else if (chosenOption == question.correctOptionIndex) {
                    secCorrect++
                    secScore += question.positiveMarks
                } else {
                    secIncorrect++
                    secScore -= question.negativeMarks
                }
            }

            val secAttempted = secCorrect + secIncorrect
            val secAccuracy = if (secAttempted > 0) {
                (secCorrect.toFloat() / secAttempted.toFloat()) * 100f
            } else {
                0.0f
            }

            totalScore += secScore
            totalCorrect += secCorrect
            totalIncorrect += secIncorrect
            totalUnattempted += secUnattempted

            SectionResult(
                sectionId = section.id,
                sectionName = section.title,
                score = secScore,
                correctCount = secCorrect,
                incorrectCount = secIncorrect,
                unattemptedCount = secUnattempted,
                markedCount = secMarked,
                accuracy = secAccuracy,
                timeTakenSeconds = section.questions.sumOf { attempt.questionTimeSpent[it.id] ?: 0L }
            )
        }

        val totalAttempted = totalCorrect + totalIncorrect
        val overallAccuracy = if (totalAttempted > 0) {
            (totalCorrect.toFloat() / totalAttempted.toFloat()) * 100f
        } else {
            0.0f
        }

        val percentile = calculatePercentile(rank, totalCandidates)

        return TestResult(
            attemptId = attempt.attemptId,
            testId = test.id,
            testTitle = test.title,
            score = totalScore,
            totalMarks = test.totalMarks,
            rank = rank,
            totalCandidates = totalCandidates,
            percentile = percentile,
            accuracy = overallAccuracy,
            correctCount = totalCorrect,
            incorrectCount = totalIncorrect,
            unattemptedCount = totalUnattempted,
            cutoffMarks = test.cutoffGeneral,
            averageScore = test.averageScore,
            bestScore = test.bestScore,
            sectionBreakdowns = sectionResults,
            attemptDate = attemptDate
        )
    }

    fun calculatePercentile(rank: Int, totalCandidates: Int): Float {
        if (totalCandidates <= 0 || rank <= 0) return 0.0f
        val pct = ((totalCandidates - rank).toFloat() / totalCandidates.toFloat()) * 100.0f
        return kotlin.math.round(pct * 100.0f) / 100.0f
    }
}
