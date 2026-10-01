package com.example.abhyaas.tier3_interaction

import com.example.abhyaas.contract.ActiveExamStateMachine
import com.example.abhyaas.contract.MockExamRepository
import com.example.abhyaas.contract.QuestionStatus
import org.junit.Assert.*
import org.junit.Test

/**
 * Tier 3: Cross-Feature Interactions for Question State Machine Transitions
 */
class QuestionStateMachineTest {

    @Test
    fun testNotVisitedToUnansweredTransition() {
        val test = MockExamRepository.getPracticeTestDay02()
        val engine = ActiveExamStateMachine(test)

        // Q1 is initial -> UNANSWERED
        assertEquals(QuestionStatus.UNANSWERED, engine.getStatus(1))

        // Q2 has not been viewed yet -> NOT_VISITED
        assertEquals(QuestionStatus.NOT_VISITED, engine.getStatus(2))

        // Jump to Q2
        engine.jumpToQuestion(1) // 0-indexed Q2
        assertEquals(QuestionStatus.UNANSWERED, engine.getStatus(2))
    }

    @Test
    fun testSaveAndNextTransitionsToAnswered() {
        val test = MockExamRepository.getPracticeTestDay02()
        val engine = ActiveExamStateMachine(test)

        assertEquals(0, engine.currentQuestionIndex)
        engine.selectOption(1)
        assertEquals(1, engine.getSelectedAnswer(1))

        engine.saveAndNext()

        // Q1 is now ANSWERED
        assertEquals(QuestionStatus.ANSWERED, engine.getStatus(1))
        // Pointer advanced to Q2
        assertEquals(1, engine.currentQuestionIndex)
        assertEquals(QuestionStatus.UNANSWERED, engine.getStatus(2))
    }

    @Test
    fun testMarkForReviewWithoutAnswerTransitionsToMarkedForReview() {
        val test = MockExamRepository.getPracticeTestDay02()
        val engine = ActiveExamStateMachine(test)

        // No option selected for Q1
        assertNull(engine.getSelectedAnswer(1))
        engine.markForReview()

        assertEquals(QuestionStatus.MARKED_FOR_REVIEW, engine.getStatus(1))
        assertEquals(1, engine.currentQuestionIndex)
    }

    @Test
    fun testMarkForReviewWithAnswerTransitionsToAnsweredAndMarked() {
        val test = MockExamRepository.getPracticeTestDay02()
        val engine = ActiveExamStateMachine(test)

        engine.selectOption(2)
        engine.markForReview()

        assertEquals(QuestionStatus.ANSWERED_AND_MARKED, engine.getStatus(1))
        assertEquals(2, engine.getSelectedAnswer(1))
        assertEquals(1, engine.currentQuestionIndex)
    }

    @Test
    fun testUnmarkingReviewRevertsToUnderlyingAnswerState() {
        val test = MockExamRepository.getPracticeTestDay02()
        val engine = ActiveExamStateMachine(test)

        // Case 1: Answered and marked
        engine.selectOption(3)
        engine.markForReview() // advances to Q2
        assertEquals(QuestionStatus.ANSWERED_AND_MARKED, engine.getStatus(1))

        // Navigate back to Q1
        engine.jumpToQuestion(0)
        engine.unmarkReview()
        assertEquals(QuestionStatus.ANSWERED, engine.getStatus(1))

        // Case 2: Clear option and unmark review
        engine.clearOption()
        engine.unmarkReview()
        assertEquals(QuestionStatus.UNANSWERED, engine.getStatus(1))
    }

    @Test
    fun testBookmarkToggle() {
        val test = MockExamRepository.getPracticeTestDay02()
        val engine = ActiveExamStateMachine(test)

        assertFalse(engine.isBookmarked(1))
        engine.toggleBookmark(1)
        assertTrue(engine.isBookmarked(1))
        engine.toggleBookmark(1)
        assertFalse(engine.isBookmarked(1))
    }

    @Test
    fun testSummaryCountsTallying() {
        val test = MockExamRepository.getPracticeTestDay02()
        val engine = ActiveExamStateMachine(test)

        // Answer Q1
        engine.selectOption(1)
        engine.saveAndNext()

        // Mark Q2 for review without answer
        engine.markForReview()

        // Answer and mark Q3
        engine.selectOption(2)
        engine.markForReview()

        // Skip Q4
        engine.saveAndNext()

        val summary = engine.getSummaryCounts()
        assertEquals(1, summary[QuestionStatus.ANSWERED])
        assertEquals(1, summary[QuestionStatus.MARKED_FOR_REVIEW])
        assertEquals(1, summary[QuestionStatus.ANSWERED_AND_MARKED])
        // Q4 is UNANSWERED, Q5 is UNANSWERED (current)
        assertTrue(summary[QuestionStatus.UNANSWERED]!! >= 2)
        // Remainder are NOT_VISITED
        assertTrue(summary[QuestionStatus.NOT_VISITED]!! <= 96)

        // Total must sum to 100 questions
        val total = summary.values.sum()
        assertEquals(100, total)
    }
}
