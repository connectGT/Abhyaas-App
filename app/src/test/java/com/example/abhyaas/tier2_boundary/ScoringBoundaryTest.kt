package com.example.abhyaas.tier2_boundary

import com.example.abhyaas.contract.*
import org.junit.Assert.*
import org.junit.Test

/**
 * Tier 2: Boundary & Corner Cases for Exam Evaluation & Negative Marking Calculations
 */
class ScoringBoundaryTest {

    @Test
    fun testZeroAttemptsResultCalculation() {
        val test = MockExamRepository.getPracticeTestDay02()
        val emptyAttempt = TestAttempt(
            attemptId = "att_zero",
            testId = test.id,
            completedAt = System.currentTimeMillis(),
            timeTakenSeconds = 120L,
            selectedAnswers = emptyMap(),
            questionStatus = emptyMap(),
            questionTimeSpent = emptyMap()
        )

        val result = ExamEvaluationEngine.evaluateExam(test, emptyAttempt, rank = 22789, totalCandidates = 24964)

        assertEquals(0.0f, result.score, 0.001f)
        assertEquals(0.0f, result.accuracy, 0.001f)
        assertEquals(0, result.correctCount)
        assertEquals(0, result.incorrectCount)
        assertEquals(100, result.unattemptedCount)
        assertEquals(22789, result.rank)
        assertEquals(24964, result.totalCandidates)
        assertEquals(8.71f, result.percentile, 0.05f) // (24964 - 22789)/24964 * 100 ~ 8.71%
    }

    @Test
    fun testPerfectScoreResultCalculation() {
        val test = MockExamRepository.getPracticeTestDay02()
        val allQuestions = test.sections.flatMap { it.questions }
        val perfectAnswers = allQuestions.associate { it.id to it.correctOptionIndex }

        val perfectAttempt = TestAttempt(
            attemptId = "att_perfect",
            testId = test.id,
            completedAt = System.currentTimeMillis(),
            timeTakenSeconds = 2500L,
            selectedAnswers = perfectAnswers,
            questionStatus = allQuestions.associate { it.id to QuestionStatus.ANSWERED },
            questionTimeSpent = emptyMap()
        )

        val result = ExamEvaluationEngine.evaluateExam(test, perfectAttempt, rank = 1, totalCandidates = 24964)

        assertEquals(200.0f, result.score, 0.001f)
        assertEquals(100.0f, result.accuracy, 0.001f)
        assertEquals(100, result.correctCount)
        assertEquals(0, result.incorrectCount)
        assertEquals(0, result.unattemptedCount)
        assertEquals(1, result.rank)
        assertEquals(100.0f, result.percentile, 0.01f)
    }

    @Test
    fun testAllIncorrectNegativeMarkingResultCalculation() {
        val test = MockExamRepository.getPracticeTestDay02()
        val allQuestions = test.sections.flatMap { it.questions }
        // Pick an incorrect option for every question
        val wrongAnswers = allQuestions.associate { it.id to ((it.correctOptionIndex + 1) % 4) }

        val worstAttempt = TestAttempt(
            attemptId = "att_worst",
            testId = test.id,
            completedAt = System.currentTimeMillis(),
            timeTakenSeconds = 1800L,
            selectedAnswers = wrongAnswers,
            questionStatus = allQuestions.associate { it.id to QuestionStatus.ANSWERED },
            questionTimeSpent = emptyMap()
        )

        val result = ExamEvaluationEngine.evaluateExam(test, worstAttempt, rank = 24964, totalCandidates = 24964)

        // 100 incorrect answers * -0.5 = -50.0 marks
        assertEquals(-50.0f, result.score, 0.001f)
        assertEquals(0.0f, result.accuracy, 0.001f)
        assertEquals(0, result.correctCount)
        assertEquals(100, result.incorrectCount)
        assertEquals(0, result.unattemptedCount)
        assertEquals(0.0f, result.percentile, 0.01f)
    }

    @Test
    fun testFractionalMarkingCalculation() {
        val test = MockExamRepository.getPracticeTestDay02()
        val allQuestions = test.sections.flatMap { it.questions }

        // 60 correct (+120.0), 20 incorrect (-10.0), 20 unattempted (0.0) -> Expected: 110.0
        val mixedAnswers = mutableMapOf<Int, Int>()
        allQuestions.take(60).forEach { mixedAnswers[it.id] = it.correctOptionIndex }
        allQuestions.drop(60).take(20).forEach { mixedAnswers[it.id] = (it.correctOptionIndex + 1) % 4 }

        val mixedAttempt = TestAttempt(
            attemptId = "att_mixed",
            testId = test.id,
            completedAt = System.currentTimeMillis(),
            timeTakenSeconds = 3000L,
            selectedAnswers = mixedAnswers,
            questionStatus = emptyMap(),
            questionTimeSpent = emptyMap()
        )

        val result = ExamEvaluationEngine.evaluateExam(test, mixedAttempt, rank = 500, totalCandidates = 24964)

        assertEquals(110.0f, result.score, 0.001f)
        assertEquals(60, result.correctCount)
        assertEquals(20, result.incorrectCount)
        assertEquals(20, result.unattemptedCount)

        // Accuracy on attempted (80 questions attempted, 60 correct = 75.0%)
        assertEquals(75.0f, result.accuracy, 0.001f)
    }
}
