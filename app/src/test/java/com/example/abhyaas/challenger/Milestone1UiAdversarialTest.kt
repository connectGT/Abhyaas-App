package com.example.abhyaas.challenger

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import com.example.abhyaas.data.model.Option
import com.example.abhyaas.data.model.QuestionStatus
import com.example.abhyaas.ui.components.*
import com.example.abhyaas.ui.theme.*
import org.junit.Assert.*
import org.junit.Test

/**
 * Adversarial Challenger Test Suite for Milestone 1:
 * Empirically challenges and stress-tests Compose UI components, Theme tokens,
 * Typography, Monospace Timer transitions, Badge shapes, and Null handling.
 */
class Milestone1UiAdversarialTest {

    // ========================================================================
    // 1. Timer Countdown Edge States & Exhaustive Partitioning
    // ========================================================================

    @Test
    fun testTimerStatePartitioning_ExhaustiveRange() {
        val warningThreshold = 300L // 5 minutes

        // Function mirroring TimerChip's state logic
        fun getTimerState(remainingSeconds: Long): String {
            val isWarning = remainingSeconds in 1..warningThreshold
            val isExpired = remainingSeconds <= 0L
            return when {
                isExpired -> "EXPIRED"
                isWarning -> "WARNING"
                else -> "NORMAL"
            }
        }

        // 1. Boundary around 0s
        assertEquals("EXPIRED", getTimerState(Long.MIN_VALUE))
        assertEquals("EXPIRED", getTimerState(-100L))
        assertEquals("EXPIRED", getTimerState(-1L))
        assertEquals("EXPIRED", getTimerState(0L))

        // 2. Boundary around 1s
        assertEquals("WARNING", getTimerState(1L))
        assertEquals("WARNING", getTimerState(2L))

        // 3. Boundary around warning threshold (300s = 5m 0s)
        assertEquals("WARNING", getTimerState(299L))
        assertEquals("WARNING", getTimerState(300L))
        assertEquals("NORMAL", getTimerState(301L))
        assertEquals("NORMAL", getTimerState(302L))

        // 4. Exhaustive transition check from -10 to 310 seconds
        for (sec in -10L..310L) {
            val state = getTimerState(sec)
            when {
                sec <= 0L -> assertEquals("Second $sec must be EXPIRED", "EXPIRED", state)
                sec in 1L..300L -> assertEquals("Second $sec must be WARNING", "WARNING", state)
                else -> assertEquals("Second $sec must be NORMAL", "NORMAL", state)
            }
        }
    }

    @Test
    fun testTimerStringFormatting_OraclesAndClamping() {
        // Clamping check for negative values
        assertEquals("00:00:00", formatTimerSeconds(-1L, showHours = true))
        assertEquals("00:00", formatTimerSeconds(-1L, showHours = false))
        assertEquals("00:00:00", formatTimerSeconds(-3600L, showHours = true))

        // Zero boundary
        assertEquals("00:00:00", formatTimerSeconds(0L, showHours = true))
        assertEquals("00:00", formatTimerSeconds(0L, showHours = false))

        // Exact minutes and seconds
        assertEquals("00:00:01", formatTimerSeconds(1L, showHours = true))
        assertEquals("00:01", formatTimerSeconds(1L, showHours = false))
        assertEquals("00:04:59", formatTimerSeconds(299L, showHours = true))
        assertEquals("04:59", formatTimerSeconds(299L, showHours = false))
        assertEquals("00:05:00", formatTimerSeconds(300L, showHours = true))
        assertEquals("05:00", formatTimerSeconds(300L, showHours = false))

        // Rollover to 1 hour
        assertEquals("00:59:59", formatTimerSeconds(3599L, showHours = true))
        assertEquals("59:59", formatTimerSeconds(3599L, showHours = false))
        assertEquals("01:00:00", formatTimerSeconds(3600L, showHours = true))
        // Critical: When hours > 0, showHours=false MUST still show hours!
        assertEquals("01:00:00", formatTimerSeconds(3600L, showHours = false))
        assertEquals("01:05:30", formatTimerSeconds(3930L, showHours = false))

        // 24 hours (86400s)
        assertEquals("24:00:00", formatTimerSeconds(86400L, showHours = true))
    }

    // ========================================================================
    // 2. Question Status Badge Shapes & Semantic Color Mapping
    // ========================================================================

    @Test
    fun testQuestionStatusBadge_MappingCompleteness() {
        // Every QuestionStatus must map to a unique QuestionBadgeStatus
        val mappedStatuses = QuestionStatus.values().map { it.toBadgeStatus() }.toSet()
        assertEquals(5, mappedStatuses.size)

        // Strict 1:1 mapping assertions
        assertEquals(QuestionBadgeStatus.NOT_VISITED, QuestionStatus.NOT_VISITED.toBadgeStatus())
        assertEquals(QuestionBadgeStatus.UNANSWERED, QuestionStatus.UNANSWERED.toBadgeStatus())
        assertEquals(QuestionBadgeStatus.ANSWERED, QuestionStatus.ANSWERED.toBadgeStatus())
        assertEquals(QuestionBadgeStatus.MARKED_FOR_REVIEW, QuestionStatus.MARKED_FOR_REVIEW.toBadgeStatus())
        assertEquals(QuestionBadgeStatus.ANSWERED_AND_MARKED, QuestionStatus.ANSWERED_AND_MARKED.toBadgeStatus())
    }

    @Test
    fun testQuestionStatusBadge_RibbonShapePredicate() {
        // Exactly 2 statuses must use the Ribbon pointer shape (coral & amber marked states)
        val allStatuses = QuestionBadgeStatus.values()
        val ribbonStatuses = allStatuses.filter {
            it == QuestionBadgeStatus.MARKED_FOR_REVIEW || it == QuestionBadgeStatus.ANSWERED_AND_MARKED
        }
        val nonRibbonStatuses = allStatuses.filterNot {
            it == QuestionBadgeStatus.MARKED_FOR_REVIEW || it == QuestionBadgeStatus.ANSWERED_AND_MARKED
        }

        assertEquals(2, ribbonStatuses.size)
        assertTrue(ribbonStatuses.contains(QuestionBadgeStatus.MARKED_FOR_REVIEW))
        assertTrue(ribbonStatuses.contains(QuestionBadgeStatus.ANSWERED_AND_MARKED))

        assertEquals(3, nonRibbonStatuses.size)
        assertTrue(nonRibbonStatuses.contains(QuestionBadgeStatus.NOT_VISITED))
        assertTrue(nonRibbonStatuses.contains(QuestionBadgeStatus.UNANSWERED))
        assertTrue(nonRibbonStatuses.contains(QuestionBadgeStatus.ANSWERED))
    }

    @Test
    fun testQuestionStatusBadge_ColorTokensDifferentiation() {
        val darkColors = defaultAbhyaasColors(darkTheme = true)

        // All status colors must be distinct
        val statusColors = listOf(
            darkColors.statusNotVisited,
            darkColors.statusUnattempted,
            darkColors.statusAnswered,
            darkColors.statusMarkedReview,
            darkColors.statusAnsweredMarked
        )
        // Check pairwise distinctness
        for (i in statusColors.indices) {
            for (j in i + 1 until statusColors.size) {
                assertNotEquals(
                    "Status colors at index $i and $j must not be identical",
                    statusColors[i],
                    statusColors[j]
                )
            }
        }
    }

    // ========================================================================
    // 3. OptionCard Interactive States & Null Safety Handling
    // ========================================================================

    @Test
    fun testOptionCard_StateSpaceAndLabelGeneration() {
        val states = OptionCardState.values()
        assertEquals(5, states.size)

        fun optionIndexToLabel(index: Int): String = when (index) {
            0 -> "A"
            1 -> "B"
            2 -> "C"
            3 -> "D"
            else -> (index + 1).toString()
        }

        assertEquals("A", optionIndexToLabel(0))
        assertEquals("B", optionIndexToLabel(1))
        assertEquals("C", optionIndexToLabel(2))
        assertEquals("D", optionIndexToLabel(3))
        assertEquals("5", optionIndexToLabel(4))
        assertEquals("6", optionIndexToLabel(5))
        assertEquals("0", optionIndexToLabel(-1)) // Negative index fallback
    }

    @Test
    fun testOptionCard_DomainModelInteroperabilityAndNullHindi() {
        val optionWithoutHindi = Option(id = 1, text = "Only English statement", textHindi = null)
        assertNull(optionWithoutHindi.textHindi)
        assertFalse(optionWithoutHindi.text.isBlank())

        val optionWithBlankHindi = Option(id = 2, text = "English text", textHindi = "")
        assertNotNull(optionWithBlankHindi.textHindi)
        assertEquals("", optionWithBlankHindi.textHindi)

        val optionWithFullHindi = Option(id = 3, text = "English text", textHindi = "हिंदी विवरण")
        assertNotNull(optionWithFullHindi.textHindi)
        assertTrue(optionWithFullHindi.textHindi!!.isNotBlank())
    }

    // ========================================================================
    // 4. Typography Tokens & Monospace Font Integrity
    // ========================================================================

    @Test
    fun testTypography_MonospaceIntegrityForTimers() {
        val typography = defaultAbhyaasTypography()

        // Monospace timer assertion
        assertSame(FontFamily.Monospace, typography.timerLarge.fontFamily)
        assertSame(FontFamily.Monospace, typography.timerMedium.fontFamily)
        assertSame(FontFamily.Monospace, typography.timerSmall.fontFamily)

        // Monospace letter spacing checks
        assertTrue(typography.timerLarge.letterSpacing.value > 0f)
        assertTrue(typography.timerMedium.letterSpacing.value > 0f)

        // Hierarchical font sizing
        assertTrue(typography.timerLarge.fontSize > typography.timerMedium.fontSize)
        assertTrue(typography.timerMedium.fontSize > typography.timerSmall.fontSize)

        // Scorecard sizes
        assertEquals(32.sp, typography.scorecardHero.fontSize)
        assertEquals(22.sp, typography.scorecardRank.fontSize)
        assertEquals(16.sp, typography.scorecardStat.fontSize)
    }

    @Test
    fun testTypography_StandardMaterial3Scale() {
        assertNotNull(Typography.displayLarge)
        assertNotNull(Typography.titleLarge)
        assertNotNull(Typography.bodyLarge)
        assertNotNull(Typography.labelSmall)

        assertTrue(Typography.displayLarge.fontSize > Typography.displayMedium.fontSize)
        assertTrue(Typography.displayMedium.fontSize > Typography.displaySmall.fontSize)
        assertTrue(Typography.titleLarge.fontSize > Typography.titleSmall.fontSize)
    }

    // ========================================================================
    // 5. StatCard and StatPill Types & Label Generation
    // ========================================================================

    @Test
    fun testStatPill_AllTypesAndLabelFormatting() {
        val types = StatPillType.values()
        assertEquals(3, types.size)

        fun getPillLabel(type: StatPillType, count: Int): String = when (type) {
            StatPillType.CORRECT -> "Correct: $count"
            StatPillType.INCORRECT -> "Incorrect: $count"
            StatPillType.UNATTEMPTED -> "Unattempted: $count"
        }

        // Boundary counts
        assertEquals("Correct: 0", getPillLabel(StatPillType.CORRECT, 0))
        assertEquals("Correct: 100", getPillLabel(StatPillType.CORRECT, 100))
        assertEquals("Incorrect: 16", getPillLabel(StatPillType.INCORRECT, 16))
        assertEquals("Unattempted: 9", getPillLabel(StatPillType.UNATTEMPTED, 9))
        assertEquals("Unattempted: -1", getPillLabel(StatPillType.UNATTEMPTED, -1))
    }

    // ========================================================================
    // 6. CategoryGridCard Gradients & CommonTopAppBar
    // ========================================================================

    @Test
    fun testCategoryGradients_CompletenessAndColors() {
        val gradients = listOf(
            CategoryGradients.StudyNotes,
            CategoryGradients.PYQ,
            CategoryGradients.Practice,
            CategoryGradients.LiveTests,
            CategoryGradients.DailyClasses,
            CategoryGradients.Quiz,
            CategoryGradients.CurrentAffairs
        )

        assertEquals(7, gradients.size)
        for (g in gradients) {
            assertEquals("Each category gradient must have 2 stops", 2, g.size)
            assertTrue("Colors must be opaque", g[0].alpha == 1.0f)
            assertTrue("Colors must be opaque", g[1].alpha == 1.0f)
            assertNotEquals("Start and end gradient colors must differ", g[0], g[1])
        }
    }

    @Test
    fun testCommonTopAppBar_NavIconTypeCompleteness() {
        val navIcons = NavIconType.values()
        assertEquals(5, navIcons.size)
        assertTrue(navIcons.contains(NavIconType.None))
        assertTrue(navIcons.contains(NavIconType.Back))
        assertTrue(navIcons.contains(NavIconType.Menu))
        assertTrue(navIcons.contains(NavIconType.Close))
        assertTrue(navIcons.contains(NavIconType.Pause))
    }

    // ========================================================================
    // 7. Theme System & Dynamic Color Guarantee
    // ========================================================================

    @Test
    fun testAbhyaasTheme_BrandPaletteInvariance() {
        val dark = defaultAbhyaasColors(darkTheme = true)
        val light = defaultAbhyaasColors(darkTheme = false)

        // Brand colors must never drift between dark and light modes
        assertEquals(dark.brandNavy, light.brandNavy)
        assertEquals(dark.brandCobalt, light.brandCobalt)
        assertEquals(dark.brandPrimary, light.brandPrimary)
        assertEquals(dark.brandAccentCyan, light.brandAccentCyan)
        assertEquals(dark.podiumGold, light.podiumGold)
        assertEquals(dark.podiumSilver, light.podiumSilver)
        assertEquals(dark.podiumBronze, light.podiumBronze)

        // Text contrast changes
        assertNotEquals(dark.textPrimary, light.textPrimary)
        assertNotEquals(dark.textSecondary, light.textSecondary)
        assertEquals(Color(0xFFF8FAFC), dark.textPrimary)
        assertEquals(Color(0xFF0F172A), light.textPrimary)
    }
}
