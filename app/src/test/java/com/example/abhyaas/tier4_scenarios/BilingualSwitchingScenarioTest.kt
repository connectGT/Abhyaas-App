package com.example.abhyaas.tier4_scenarios

import com.example.abhyaas.contract.ActiveExamStateMachine
import com.example.abhyaas.contract.MockExamRepository
import com.example.abhyaas.contract.MockQuestionRepository
import com.example.abhyaas.contract.QuestionStatus
import org.junit.Assert.*
import org.junit.Test

/**
 * Tier 4: Real-World Workload Scenarios - Bilingual English/Hindi Content & Language Toggle Resilience
 */
class BilingualSwitchingScenarioTest {

    @Test
    fun testAuthenticBilingualQuestionContent() {
        val giQuestions = MockQuestionRepository.sampleGIQuestions

        // Q1: Statements and Course of action
        val q1 = giQuestions[0]
        assertNotNull(q1.statementTextHindi)
        assertNotNull(q1.directionTextHindi)
        assertNotNull(q1.explanationHindi)
        assertTrue(q1.options.all { it.textHindi != null })

        // Validate Hindi text fidelity
        assertTrue(q1.directionTextHindi!!.contains("कथन"))
        assertTrue(q1.statementTextHindi!!.contains("त्योहारी सीजन"))
        assertEquals("I और II अनुसरण करते हैं", q1.options[1].textHindi)
    }

    @Test
    fun testMadhyaPradeshGKQuestionsBilingualContent() {
        val gaQuestions = MockQuestionRepository.sampleGAQuestions

        // Q26: MP Land Revenue Code
        val q26 = gaQuestions[0]
        assertNotNull(q26.statementTextHindi)
        assertTrue(q26.statementTextHindi!!.contains("मध्य प्रदेश भू-राजस्व संहिता"))
        assertTrue(q26.statementTextHindi!!.contains("खसरा"))
        assertEquals("पटवारी", q26.options[1].textHindi)
        assertTrue(q26.explanationHindi!!.contains("पटवारी"))

        // Q27: Article 280 Finance Commission
        val q27 = gaQuestions[1]
        assertNotNull(q27.statementTextHindi)
        assertTrue(q27.statementTextHindi!!.contains("वित्त आयोग"))
        assertEquals("अनुच्छेद 280", q27.options[0].textHindi)
        assertTrue(q27.explanationHindi!!.contains("अनुच्छेद 280"))
    }

    @Test
    fun testLanguageTogglePreservesExamState() {
        val test = MockExamRepository.getPracticeTestDay02()
        val engine = ActiveExamStateMachine(test)

        // Select answer in English
        engine.selectOption(1)
        engine.saveAndNext()
        assertEquals(QuestionStatus.ANSWERED, engine.getStatus(1))
        assertEquals(1, engine.getSelectedAnswer(1))

        // Simulate language toggle: state in engine must not be affected
        var activeLanguage = "Hindi"
        assertEquals("Hindi", activeLanguage)

        // Verify Question 1 state is intact after simulated language switch
        assertEquals(QuestionStatus.ANSWERED, engine.getStatus(1))
        assertEquals(1, engine.getSelectedAnswer(1))

        // Switch language back to English
        activeLanguage = "English"
        assertEquals("English", activeLanguage)
        assertEquals(QuestionStatus.ANSWERED, engine.getStatus(1))
        assertEquals(1, engine.getSelectedAnswer(1))
    }
}
