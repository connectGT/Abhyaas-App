package com.example.abhyaas.tier4_scenarios

import com.example.abhyaas.contract.*
import org.junit.Assert.*
import org.junit.Test

/**
 * Tier 4: Real-World Workload Scenarios - Full 100-Question Exam Simulation & Section-wise Scoring
 */
class FullExamSimulationTest {

    @Test
    fun testComprehensive100QuestionsExamSimulation() {
        val test = MockExamRepository.getPracticeTestDay02()
        assertEquals(4, test.sections.size)
        assertEquals(100, test.totalQuestions)

        val engine = ActiveExamStateMachine(test)

        // Real exam answering strategy across 4 sections:
        // Section A (GI, Q1-25): 20 correct, 3 wrong, 2 unattempted
        // Section B (GA, Q26-50): 18 correct, 5 wrong, 2 unattempted
        // Section C (QA, Q51-75): 15 correct, 6 wrong, 4 unattempted
        // Section D (EN, Q76-100): 22 correct, 2 wrong, 1 unattempted

        val secA = test.sections[0]
        simulateSection(engine, secA, correctCount = 20, wrongCount = 3, unattemptedCount = 2)

        val secB = test.sections[1]
        simulateSection(engine, secB, correctCount = 18, wrongCount = 5, unattemptedCount = 2)

        val secC = test.sections[2]
        simulateSection(engine, secC, correctCount = 15, wrongCount = 6, unattemptedCount = 4)

        val secD = test.sections[3]
        simulateSection(engine, secD, correctCount = 22, wrongCount = 2, unattemptedCount = 1)

        // Simulate 45 minutes of exam elapsed time (2700s)
        engine.tick(2700)
        assertEquals(900L, engine.remainingSeconds) // 15 mins remaining
        assertFalse(engine.isSubmitted)

        // Candidate manually submits
        engine.submitManually()
        assertTrue(engine.isSubmitted)

        val attempt = engine.buildAttempt("attempt_e2e_simulation")
        val result = ExamEvaluationEngine.evaluateExam(test, attempt, rank = 185, totalCandidates = 24964)

        // Assert section counts
        assertEquals(4, result.sectionBreakdowns.size)

        // Section A: 20 * 2 - 3 * 0.5 = 40 - 1.5 = 38.5
        val secARes = result.sectionBreakdowns[0]
        assertEquals("General Intelligence", secARes.sectionName)
        assertEquals(20, secARes.correctCount)
        assertEquals(3, secARes.incorrectCount)
        assertEquals(2, secARes.unattemptedCount)
        assertEquals(38.5f, secARes.score, 0.001f)
        assertEquals(86.96f, secARes.accuracy, 0.05f) // 20/23 * 100 ~ 86.96%

        // Section B: 18 * 2 - 5 * 0.5 = 36 - 2.5 = 33.5
        val secBRes = result.sectionBreakdowns[1]
        assertEquals(18, secBRes.correctCount)
        assertEquals(5, secBRes.incorrectCount)
        assertEquals(2, secBRes.unattemptedCount)
        assertEquals(33.5f, secBRes.score, 0.001f)

        // Section C: 15 * 2 - 6 * 0.5 = 30 - 3.0 = 27.0
        val secCRes = result.sectionBreakdowns[2]
        assertEquals(15, secCRes.correctCount)
        assertEquals(6, secCRes.incorrectCount)
        assertEquals(4, secCRes.unattemptedCount)
        assertEquals(27.0f, secCRes.score, 0.001f)

        // Section D: 22 * 2 - 2 * 0.5 = 44 - 1.0 = 43.0
        val secDRes = result.sectionBreakdowns[3]
        assertEquals(22, secDRes.correctCount)
        assertEquals(2, secDRes.incorrectCount)
        assertEquals(1, secDRes.unattemptedCount)
        assertEquals(43.0f, secDRes.score, 0.001f)

        // Overall Totals:
        // Correct: 20 + 18 + 15 + 22 = 75
        // Incorrect: 3 + 5 + 6 + 2 = 16
        // Unattempted: 2 + 2 + 4 + 1 = 9
        // Total score: 38.5 + 33.5 + 27.0 + 43.0 = 142.0 marks
        assertEquals(75, result.correctCount)
        assertEquals(16, result.incorrectCount)
        assertEquals(9, result.unattemptedCount)
        assertEquals(142.0f, result.score, 0.001f)
        assertEquals(200.0f, result.totalMarks, 0.001f)

        // Overall Accuracy: 75 / (75 + 16) = 75/91 ~ 82.42%
        assertEquals(82.42f, result.accuracy, 0.05f)

        // Cutoff qualification check
        assertTrue("Score 142.0 must clear General cutoff 132-135", result.score > 135.0f)

        // Percentile for rank 185 out of 24964: (24964 - 185) / 24964 * 100 ~ 99.26%
        assertEquals(99.26f, result.percentile, 0.05f)
    }

    private fun simulateSection(
        engine: ActiveExamStateMachine,
        section: TestSection,
        correctCount: Int,
        wrongCount: Int,
        unattemptedCount: Int
    ) {
        val total = correctCount + wrongCount + unattemptedCount
        assertEquals(section.questions.size, total)

        var correctLeft = correctCount
        var wrongLeft = wrongCount

        section.questions.forEach { question ->
            val globalIdx = engine.allQuestions.indexOfFirst { it.id == question.id }
            engine.jumpToQuestion(globalIdx)

            if (correctLeft > 0) {
                engine.selectOption(question.correctOptionIndex)
                engine.saveAndNext()
                correctLeft--
            } else if (wrongLeft > 0) {
                val wrongOption = (question.correctOptionIndex + 1) % 4
                engine.selectOption(wrongOption)
                engine.saveAndNext()
                wrongLeft--
            } else {
                // Leave unattempted
                engine.saveAndNext()
            }
        }
    }
}
