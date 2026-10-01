package com.example.abhyaas.tier2_boundary

import com.example.abhyaas.contract.*
import org.junit.Assert.*
import org.junit.Test

/**
 * Tier 2: Boundary & Corner Cases for Reattempt Mode Toggle & Solution Visibility
 */
class ReattemptModeBoundaryTest {

    @Test
    fun testReattemptModeInitialStateAndToggle() {
        val test = MockExamRepository.getPracticeTestDay02()
        val dummyAttempt = TestAttempt(
            attemptId = "att_test",
            testId = test.id,
            completedAt = System.currentTimeMillis(),
            timeTakenSeconds = 1200L,
            selectedAnswers = emptyMap(),
            questionStatus = emptyMap(),
            questionTimeSpent = emptyMap()
        )

        val reviewEngine = SolutionsReviewStateMachine(test, dummyAttempt)

        // Default state: Reattempt mode is OFF, solutions are visible
        assertFalse(reviewEngine.isReattemptModeOn)
        assertTrue(reviewEngine.isSolutionVisible(1))

        // Toggle ON: solutions must now be hidden to allow practice
        val stateAfterToggle1 = reviewEngine.toggleReattemptMode()
        assertTrue(stateAfterToggle1)
        assertTrue(reviewEngine.isReattemptModeOn)
        assertFalse(reviewEngine.isSolutionVisible(1))

        // Toggle OFF: solutions revealed again
        val stateAfterToggle2 = reviewEngine.toggleReattemptMode()
        assertFalse(stateAfterToggle2)
        assertFalse(reviewEngine.isReattemptModeOn)
        assertTrue(reviewEngine.isSolutionVisible(1))
    }

    @Test
    fun testSolutionsFilterPartitioning() {
        val test = MockExamRepository.getPracticeTestDay02()
        val allQ = test.sections.flatMap { it.questions }

        // Setup 10 correct, 10 incorrect, 80 unattempted
        val answers = mutableMapOf<Int, Int>()
        allQ.take(10).forEach { answers[it.id] = it.correctOptionIndex }
        allQ.drop(10).take(10).forEach { answers[it.id] = (it.correctOptionIndex + 1) % 4 }

        val attempt = TestAttempt(
            attemptId = "att_partition",
            testId = test.id,
            completedAt = System.currentTimeMillis(),
            timeTakenSeconds = 2000L,
            selectedAnswers = answers,
            questionStatus = emptyMap(),
            questionTimeSpent = emptyMap()
        )

        val reviewEngine = SolutionsReviewStateMachine(test, attempt)

        // ALL filter
        reviewEngine.setFilter(SolutionFilter.ALL)
        assertEquals(100, reviewEngine.getFilteredQuestions().size)

        // CORRECT filter
        reviewEngine.setFilter(SolutionFilter.CORRECT)
        val correctList = reviewEngine.getFilteredQuestions()
        assertEquals(10, correctList.size)
        assertTrue(correctList.all { answers[it.id] == it.correctOptionIndex })

        // INCORRECT filter
        reviewEngine.setFilter(SolutionFilter.INCORRECT)
        val incorrectList = reviewEngine.getFilteredQuestions()
        assertEquals(10, incorrectList.size)
        assertTrue(incorrectList.all { answers[it.id] != it.correctOptionIndex })

        // UNATTEMPTED filter
        reviewEngine.setFilter(SolutionFilter.UNATTEMPTED)
        val unattemptedList = reviewEngine.getFilteredQuestions()
        assertEquals(80, unattemptedList.size)
        assertTrue(unattemptedList.all { !answers.containsKey(it.id) })
    }
}
