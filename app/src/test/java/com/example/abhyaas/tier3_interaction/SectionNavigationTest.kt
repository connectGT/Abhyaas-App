package com.example.abhyaas.tier3_interaction

import com.example.abhyaas.contract.ActiveExamStateMachine
import com.example.abhyaas.contract.MockExamRepository
import com.example.abhyaas.contract.QuestionStatus
import org.junit.Assert.*
import org.junit.Test

/**
 * Tier 3: Cross-Feature Interactions for Section Navigation & Cross-Section State Preservation
 */
class SectionNavigationTest {

    @Test
    fun testSectionSwitchingJumpsToFirstQuestionOfTargetSection() {
        val test = MockExamRepository.getPracticeTestDay02()
        val engine = ActiveExamStateMachine(test)

        // Initial: Section 0 (PART - A: General Intelligence)
        assertEquals(0, engine.currentSectionIndex)
        assertEquals("sec_gi", engine.currentSection.id)
        assertEquals(0, engine.currentQuestionIndex)
        assertEquals(1, engine.currentQuestion.id)

        // Switch to Section 1 (PART - B: General Awareness)
        engine.switchSection(1)
        assertEquals(1, engine.currentSectionIndex)
        assertEquals("sec_ga", engine.currentSection.id)
        assertEquals(25, engine.currentQuestionIndex)
        assertEquals(26, engine.currentQuestion.id)

        // Switch to Section 2 (PART - C: Quantitative Aptitude)
        engine.switchSection(2)
        assertEquals(2, engine.currentSectionIndex)
        assertEquals("sec_qa", engine.currentSection.id)
        assertEquals(50, engine.currentQuestionIndex)
        assertEquals(51, engine.currentQuestion.id)

        // Switch to Section 3 (PART - D: English Language)
        engine.switchSection(3)
        assertEquals(3, engine.currentSectionIndex)
        assertEquals("sec_en", engine.currentSection.id)
        assertEquals(75, engine.currentQuestionIndex)
        assertEquals(76, engine.currentQuestion.id)
    }

    @Test
    fun testStatePreservationAcrossSectionSwitches() {
        val test = MockExamRepository.getPracticeTestDay02()
        val engine = ActiveExamStateMachine(test)

        // Answer Q1 in Section A
        engine.selectOption(2)
        engine.saveAndNext()
        assertEquals(QuestionStatus.ANSWERED, engine.getStatus(1))
        assertEquals(2, engine.getSelectedAnswer(1))

        // Switch to Section B and answer Q26
        engine.switchSection(1)
        assertEquals(26, engine.currentQuestion.id)
        engine.selectOption(3)
        engine.saveAndNext()
        assertEquals(QuestionStatus.ANSWERED, engine.getStatus(26))
        assertEquals(3, engine.getSelectedAnswer(26))

        // Switch back to Section A
        engine.switchSection(0)
        assertEquals(1, engine.currentQuestion.id)
        assertEquals(QuestionStatus.ANSWERED, engine.getStatus(1))
        assertEquals(2, engine.getSelectedAnswer(1))

        // Switch back to Section B
        engine.switchSection(1)
        assertEquals(26, engine.currentQuestion.id)
        assertEquals(QuestionStatus.ANSWERED, engine.getStatus(26))
        assertEquals(3, engine.getSelectedAnswer(26))
    }

    @Test
    fun testCurrentSectionUpdatesOnQuestionJump() {
        val test = MockExamRepository.getPracticeTestDay02()
        val engine = ActiveExamStateMachine(test)

        assertEquals(0, engine.currentSectionIndex)

        // Jump directly to Question 60 (belongs to Section 2: Quantitative Aptitude)
        engine.jumpToQuestion(59)
        assertEquals(2, engine.currentSectionIndex)
        assertEquals("sec_qa", engine.currentSection.id)

        // Jump directly to Question 80 (belongs to Section 3: English Language)
        engine.jumpToQuestion(79)
        assertEquals(3, engine.currentSectionIndex)
        assertEquals("sec_en", engine.currentSection.id)
    }
}
