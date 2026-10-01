package com.example.abhyaas.tier2_boundary

import com.example.abhyaas.contract.*
import org.junit.Assert.*
import org.junit.Test

/**
 * Tier 2: Boundary & Corner Cases for Nullability, Extreme Values & Safety Clamps
 */
class ValidationBoundaryTest {

    @Test
    fun testQuestionNullableTextHandling() {
        val q = Question(
            id = 999,
            sectionId = "sec_test",
            questionNumber = 1,
            directionText = null,
            directionTextHindi = null,
            statementText = "English only question statement",
            statementTextHindi = null,
            options = listOf(
                Option(1, "Option 1"),
                Option(2, "Option 2")
            ),
            correctOptionIndex = 0,
            explanation = "English explanation",
            explanationHindi = null,
            topic = "Testing",
            subject = "Testing"
        )

        assertNull(q.directionText)
        assertNull(q.directionTextHindi)
        assertNull(q.statementTextHindi)
        assertNull(q.explanationHindi)
        assertEquals(2, q.options.size)
        assertNull(q.options[0].textHindi)
    }

    @Test
    fun testPercentileBoundaryValues() {
        // Rank 1: best candidate
        val pTop = ExamEvaluationEngine.calculatePercentile(rank = 1, totalCandidates = 24964)
        assertEquals(100.0f, pTop, 0.01f)

        // Last rank
        val pLast = ExamEvaluationEngine.calculatePercentile(rank = 24964, totalCandidates = 24964)
        assertEquals(0.0f, pLast, 0.001f)

        // Middle candidate
        val pMid = ExamEvaluationEngine.calculatePercentile(rank = 12482, totalCandidates = 24964)
        assertEquals(50.0f, pMid, 0.05f)

        // Invalid boundaries
        val pZeroRank = ExamEvaluationEngine.calculatePercentile(rank = 0, totalCandidates = 24964)
        assertEquals(0.0f, pZeroRank, 0.001f)

        val pZeroCandidates = ExamEvaluationEngine.calculatePercentile(rank = 10, totalCandidates = 0)
        assertEquals(0.0f, pZeroCandidates, 0.001f)
    }

    @Test
    fun testBackstackStackSafety() {
        val nav = BackstackSimulator("home")
        assertEquals(1, nav.size)
        assertEquals("home", nav.currentDestination)

        // Attempting to pop root returns false and does not clear root
        val popSuccess = nav.popBackStack()
        assertFalse(popSuccess)
        assertEquals(1, nav.size)
        assertEquals("home", nav.currentDestination)

        // launchSingleTop prevents duplicate destination
        nav.navigate("tests")
        assertEquals(2, nav.size)
        assertEquals("tests", nav.currentDestination)

        nav.navigate("tests", launchSingleTop = true)
        assertEquals(2, nav.size)
        assertEquals("tests", nav.currentDestination)
    }
}
