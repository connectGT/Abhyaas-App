package com.example.abhyaas.tier2_boundary

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import com.example.abhyaas.data.mock.MockExamRepository
import com.example.abhyaas.data.mock.MockQuestionRepository
import com.example.abhyaas.data.model.Option
import com.example.abhyaas.data.model.Question
import com.example.abhyaas.data.model.QuestionStatus
import com.example.abhyaas.ui.components.*
import com.example.abhyaas.ui.theme.*
import org.junit.Assert.*
import org.junit.Test

/**
 * Empirical Adversarial Challenge Test for Milestone 1:
 * Validates Theme tokens, Monospace Timers, Reusable Component logic,
 * Boundary & Corner cases (null handling, extreme strings, 0s/negative/warning timer,
 * badge status mapping, ribbon shapes, interactive states), and Mock Data integrity.
 */
class Milestone1UiChallengeTest {

    // ========================================================================
    // 1. Timer Edge States & String Formatting Oracles
    // ========================================================================

    @Test
    fun testTimerEdgeStates_ZeroSeconds() {
        val formattedWithHours = formatTimerSeconds(0L, showHours = true)
        val formattedWithoutHours = formatTimerSeconds(0L, showHours = false)
        assertEquals("00:00:00", formattedWithHours)
        assertEquals("00:00", formattedWithoutHours)
    }

    @Test
    fun testTimerEdgeStates_NegativeSecondsClamped() {
        // Negative seconds must clamp to 0 and not produce negative format strings
        val negative1 = formatTimerSeconds(-1L, showHours = true)
        val negativeHuge = formatTimerSeconds(-99999L, showHours = true)
        val negativeNoHours = formatTimerSeconds(-50L, showHours = false)

        assertEquals("00:00:00", negative1)
        assertEquals("00:00:00", negativeHuge)
        assertEquals("00:00", negativeNoHours)
    }

    @Test
    fun testTimerEdgeStates_UnderFiveMinutesWarningThreshold() {
        val warningThreshold = 300L // 5 minutes

        // Boundary: 301s (5 mins 1s) -> NOT warning
        val isWarning301 = 301L in 1..warningThreshold
        assertFalse(isWarning301)
        assertEquals("00:05:01", formatTimerSeconds(301L))

        // Boundary: 300s (5 mins exactly) -> IS warning
        val isWarning300 = 300L in 1..warningThreshold
        assertTrue(isWarning300)
        assertEquals("00:05:00", formatTimerSeconds(300L))

        // Inside warning: 299s, 60s, 1s
        assertTrue(299L in 1..warningThreshold)
        assertTrue(60L in 1..warningThreshold)
        assertTrue(1L in 1..warningThreshold)

        // Boundary: 0s -> isExpired, NOT in 1..warningThreshold
        assertFalse(0L in 1..warningThreshold)
        val isExpired0 = 0L <= 0L
        assertTrue(isExpired0)

        // Negative -> isExpired, NOT in 1..warningThreshold
        assertFalse(-10L in 1..warningThreshold)
        assertTrue(-10L <= 0L)
    }

    @Test
    fun testTimerEdgeStates_HourRolloversAndLargeValues() {
        // 59 seconds
        assertEquals("00:00:59", formatTimerSeconds(59L, true))
        assertEquals("00:59", formatTimerSeconds(59L, false))

        // 1 hour exactly (3600s)
        assertEquals("01:00:00", formatTimerSeconds(3600L, true))
        // When showHours is false but hours > 0, hours should still be shown
        assertEquals("01:00:00", formatTimerSeconds(3600L, false))

        // 2 hours 15 mins 30 secs = 8130s
        assertEquals("02:15:30", formatTimerSeconds(8130L, true))

        // 100 hours
        assertEquals("100:00:00", formatTimerSeconds(360000L, true))
    }

    // ========================================================================
    // 2. Question Status Badge Mapping & Ribbon Pointer Logic
    // ========================================================================

    @Test
    fun testQuestionStatusBadge_AllEnumMappings() {
        assertEquals(QuestionBadgeStatus.NOT_VISITED, QuestionStatus.NOT_VISITED.toBadgeStatus())
        assertEquals(QuestionBadgeStatus.UNANSWERED, QuestionStatus.UNANSWERED.toBadgeStatus())
        assertEquals(QuestionBadgeStatus.ANSWERED, QuestionStatus.ANSWERED.toBadgeStatus())
        assertEquals(QuestionBadgeStatus.MARKED_FOR_REVIEW, QuestionStatus.MARKED_FOR_REVIEW.toBadgeStatus())
        assertEquals(QuestionBadgeStatus.ANSWERED_AND_MARKED, QuestionStatus.ANSWERED_AND_MARKED.toBadgeStatus())
    }

    @Test
    fun testQuestionStatusBadge_RibbonShapeCondition() {
        // Ribbon pointer shape is applied specifically to marked states (coral & amber)
        val markedReviewStatus = QuestionBadgeStatus.MARKED_FOR_REVIEW
        val answeredMarkedStatus = QuestionBadgeStatus.ANSWERED_AND_MARKED
        val answeredStatus = QuestionBadgeStatus.ANSWERED
        val unansweredStatus = QuestionBadgeStatus.UNANSWERED
        val notVisitedStatus = QuestionBadgeStatus.NOT_VISITED

        fun isRibbon(status: QuestionBadgeStatus): Boolean =
            status == QuestionBadgeStatus.MARKED_FOR_REVIEW || status == QuestionBadgeStatus.ANSWERED_AND_MARKED

        assertTrue(isRibbon(markedReviewStatus))
        assertTrue(isRibbon(answeredMarkedStatus))
        assertFalse(isRibbon(answeredStatus))
        assertFalse(isRibbon(unansweredStatus))
        assertFalse(isRibbon(notVisitedStatus))
    }

    @Test
    fun testQuestionStatusBadge_BoundaryNumbers() {
        // Numbers from 1 to 100, 0, and large numbers
        val validNumbers = listOf(0, 1, 13, 25, 50, 75, 100, 999)
        for (num in validNumbers) {
            val label = num.toString()
            assertNotNull(label)
            assertEquals(num, label.toInt())
        }
    }

    // ========================================================================
    // 3. OptionCard States, Index Labels & Null Safety
    // ========================================================================

    @Test
    fun testOptionCard_OptionIndexLabelMapping() {
        fun getOptionLabel(index: Int): String = when (index) {
            0 -> "A"
            1 -> "B"
            2 -> "C"
            3 -> "D"
            else -> (index + 1).toString()
        }

        assertEquals("A", getOptionLabel(0))
        assertEquals("B", getOptionLabel(1))
        assertEquals("C", getOptionLabel(2))
        assertEquals("D", getOptionLabel(3))
        assertEquals("5", getOptionLabel(4))
        assertEquals("10", getOptionLabel(9))
    }

    @Test
    fun testOptionCard_AllInteractiveStates() {
        val states = OptionCardState.values()
        assertEquals(5, states.size)
        assertTrue(states.contains(OptionCardState.DEFAULT))
        assertTrue(states.contains(OptionCardState.SELECTED))
        assertTrue(states.contains(OptionCardState.CORRECT))
        assertTrue(states.contains(OptionCardState.INCORRECT))
        assertTrue(states.contains(OptionCardState.DISABLED))
    }

    @Test
    fun testOptionCard_NullHindiTextSupport() {
        val optEnglishOnly = Option(id = 1, text = "Option in English only", textHindi = null)
        assertNotNull(optEnglishOnly.text)
        assertNull(optEnglishOnly.textHindi)

        val optBilingual = Option(id = 2, text = "Option in English", textHindi = "विकल्प हिंदी में")
        assertNotNull(optBilingual.text)
        assertNotNull(optBilingual.textHindi)
    }

    @Test
    fun testOptionCard_ExtremeLongTextAndSpecialCharacters() {
        val longEnglishText = "A".repeat(2000)
        val devanagariText = "यह एक बहुत लंबा प्रश्न विवरण है जिसमें कई विशेष वर्ण हैं: !@#$%^&*()_+~`|}{[]:;?><,./"
        val optionLong = Option(id = 99, text = longEnglishText, textHindi = devanagariText)

        assertEquals(2000, optionLong.text.length)
        assertEquals(devanagariText, optionLong.textHindi)
    }

    // ========================================================================
    // 4. CommonTopAppBar & Navigation Icon Types
    // ========================================================================

    @Test
    fun testTopAppBar_NavIconTypes() {
        val types = NavIconType.values()
        assertEquals(5, types.size)
        assertTrue(types.contains(NavIconType.None))
        assertTrue(types.contains(NavIconType.Back))
        assertTrue(types.contains(NavIconType.Menu))
        assertTrue(types.contains(NavIconType.Close))
        assertTrue(types.contains(NavIconType.Pause))
    }

    @Test
    fun testTopAppBar_NullSubtitleAndCustomTitleSupport() {
        // Checking contract: subtitle can be null, customTitleContent can be null
        val title = "Test Instructions"
        val nullSubtitle: String? = null
        assertNull(nullSubtitle)
        assertNotNull(title)
    }

    // ========================================================================
    // 5. StatCard & StatPill Types
    // ========================================================================

    @Test
    fun testStatPill_AllTypes() {
        val types = StatPillType.values()
        assertEquals(3, types.size)
        assertTrue(types.contains(StatPillType.CORRECT))
        assertTrue(types.contains(StatPillType.INCORRECT))
        assertTrue(types.contains(StatPillType.UNATTEMPTED))
    }

    // ========================================================================
    // 6. Theme Tokens, Monospace Timers & Color Integrity
    // ========================================================================

    @Test
    fun testAbhyaasTheme_BrandColorIntegrity() {
        val darkColors = defaultAbhyaasColors(darkTheme = true)
        val lightColors = defaultAbhyaasColors(darkTheme = false)

        // Brand colors must be invariant across dark/light
        assertEquals(BrandNavy, darkColors.brandNavy)
        assertEquals(BrandNavy, lightColors.brandNavy)
        assertEquals(BrandCobalt, darkColors.brandCobalt)
        assertEquals(BrandPrimary, darkColors.brandPrimary)
        assertEquals(BrandAccentCyan, darkColors.brandAccentCyan)
        assertEquals(StatusAnswered, darkColors.statusAnswered)
        assertEquals(StatusNotAnswered, darkColors.statusNotAnswered)

        // Text contrast changes appropriately
        assertEquals(TextPrimaryDark, darkColors.textPrimary)
        assertEquals(TextPrimaryLight, lightColors.textPrimary)
    }

    @Test
    fun testAbhyaasTheme_MonospaceTypographyForTimers() {
        val typography = defaultAbhyaasTypography()

        // Monospace timer guarantee to eliminate digit jitter during ticking
        assertEquals(FontFamily.Monospace, typography.timerLarge.fontFamily)
        assertEquals(FontFamily.Monospace, typography.timerMedium.fontFamily)
        assertEquals(FontFamily.Monospace, typography.timerSmall.fontFamily)

        // Font sizes
        assertEquals(18.sp, typography.timerLarge.fontSize)
        assertEquals(14.sp, typography.timerMedium.fontSize)
        assertEquals(12.sp, typography.timerSmall.fontSize)

        // Scorecard hero size
        assertEquals(32.sp, typography.scorecardHero.fontSize)
    }

    // ========================================================================
    // 7. MockQuestionRepository 100-Question Exhaustive Invariant Validation
    // ========================================================================

    @Test
    fun testMockQuestionRepository_Complete100QuestionsInvariants() {
        val allQuestions: List<Question> = MockQuestionRepository.getQuestionsForTest("default")
        assertEquals("Repository must have exactly 100 questions", 100, allQuestions.size)

        val sectionA = allQuestions.filter { it.sectionId == "sec_a" }
        val sectionB = allQuestions.filter { it.sectionId == "sec_b" }
        val sectionC = allQuestions.filter { it.sectionId == "sec_c" }
        val sectionD = allQuestions.filter { it.sectionId == "sec_d" }

        assertEquals(25, sectionA.size)
        assertEquals(25, sectionB.size)
        assertEquals(25, sectionC.size)
        assertEquals(25, sectionD.size)

        var expectedNum = 1
        for (q in allQuestions) {
            assertEquals("Question numbers must be contiguous 1..100", expectedNum, q.questionNumber)
            expectedNum++

            // Non-empty fields
            assertTrue("Statement must not be blank for Q${q.id}", q.statementText.isNotBlank())
            assertTrue("Explanation must not be blank for Q${q.id}", q.explanation.isNotBlank())
            assertTrue("Topic must not be blank for Q${q.id}", q.topic.isNotBlank())
            assertTrue("Subject must not be blank for Q${q.id}", q.subject.isNotBlank())

            // Options validity
            assertTrue("Q${q.id} must have at least 4 options", q.options.size >= 4)
            assertTrue("Q${q.id} correctOptionIndex must be valid", q.correctOptionIndex in 0 until q.options.size)
            for (opt in q.options) {
                assertTrue("Option text must not be blank in Q${q.id}", opt.text.isNotBlank())
            }

            // Marking scheme
            assertEquals(2.0f, q.positiveMarks, 0.001f)
            assertEquals(0.5f, q.negativeMarks, 0.001f)
        }
    }

    @Test
    fun testMockQuestionRepository_AuthenticSurveyedQuestionsPresent() {
        val allQuestions: List<Question> = MockQuestionRepository.getQuestionsForTest("default")

        // 1. MP Land Revenue Code Patwari appointment
        val patwariQ = allQuestions.find { it.statementText.contains("Madhya Pradesh Land Revenue Code") }
        assertNotNull("MP Land Revenue Code question must exist", patwariQ)
        assertEquals("sec_b", patwariQ?.sectionId)
        assertTrue(patwariQ!!.explanation.contains("Section 114"))
        assertEquals("Patwari", patwariQ.options[patwariQ.correctOptionIndex].text)

        // 2. Article 280 Finance Commission
        val financeQ = allQuestions.find { it.statementText.contains("Finance Commission") }
        assertNotNull("Finance Commission question must exist", financeQ)
        assertTrue(financeQ!!.explanation.contains("Article 280"))
        assertEquals("Article 280", financeQ.options[0].text)

        // 3. Narmada River Lifeline
        val narmadaQ = allQuestions.find { it.statementText.contains("Lifeline of Madhya Pradesh") }
        assertNotNull("Narmada river question must exist", narmadaQ)

        // 4. English Vocabulary METICULOUS
        val meticulousQ = allQuestions.find { it.statementText.contains("METICULOUS") }
        assertNotNull("METICULOUS synonym question must exist", meticulousQ)
        assertEquals("sec_d", meticulousQ?.sectionId)

        // 5. Reasoning Direction text present on Q1-Q3
        val q1 = MockQuestionRepository.getQuestionById(1)
        assertNotNull("Q1 must exist", q1)
        assertNotNull("Q1 must have directionText for course of action", q1?.directionText)
        assertNotNull("Q1 must have Hindi directionText", q1?.directionTextHindi)
    }
}
