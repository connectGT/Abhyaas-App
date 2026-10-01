package com.example.abhyaas.tier1_coverage

import com.example.abhyaas.contract.defaultTestInstructions
import org.junit.Assert.*
import org.junit.Test

/**
 * Tier 1: Feature Coverage for Pre-Test Instructions & Marking Scheme Declaration
 */
class TestInstructionsCoverageTest {

    @Test
    fun testDefaultTestInstructionsCount() {
        assertEquals(7, defaultTestInstructions.size)
    }

    @Test
    fun testQuestionsAndOptionsCountRules() {
        val rule1 = defaultTestInstructions[0]
        assertTrue(rule1.contains("100 questions"))

        val rule2 = defaultTestInstructions[1]
        assertTrue(rule2.contains("4 options") && rule2.contains("only one is correct"))
    }

    @Test
    fun testDurationAndSectionDivisionRules() {
        val rule3 = defaultTestInstructions[2]
        assertTrue(rule3.contains("60 minutes"))

        val rule4 = defaultTestInstructions[3]
        assertTrue(rule4.contains("25 questions") && rule4.contains("15 minutes"))
    }

    @Test
    fun testMarkingSchemeAndPenaltyRules() {
        val rule5 = defaultTestInstructions[4]
        assertTrue(rule5.contains("2 marks") && rule5.contains("0.5 negative marking"))

        val rule6 = defaultTestInstructions[5]
        assertTrue(rule6.contains("no penalty") && rule6.contains("not attempted"))
    }

    @Test
    fun testDeclarationUnderstandingText() {
        val declaration = defaultTestInstructions[6]
        assertTrue(declaration.contains("read all the instructions carefully"))
        assertTrue(declaration.contains("agree not to cheat"))
    }
}
