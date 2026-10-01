package com.example.abhyaas.tier2_boundary

import com.example.abhyaas.contract.ActiveExamStateMachine
import com.example.abhyaas.contract.MockExamRepository
import org.junit.Assert.*
import org.junit.Test

/**
 * Tier 2: Boundary & Corner Cases for Active Exam Countdown Timer
 */
class TimerBoundaryTest {

    @Test
    fun testInitialTimerState() {
        val test = MockExamRepository.getPracticeTestDay02()
        val engine = ActiveExamStateMachine(test)

        assertEquals(3600L, engine.remainingSeconds) // 60 minutes * 60 seconds
        assertFalse(engine.isPaused)
        assertFalse(engine.isAutoSubmitted)
        assertFalse(engine.isManuallySubmitted)
        assertFalse(engine.isSubmitted)
    }

    @Test
    fun testNormalTimerTicking() {
        val test = MockExamRepository.getPracticeTestDay02()
        val engine = ActiveExamStateMachine(test)

        engine.tick(1)
        assertEquals(3599L, engine.remainingSeconds)

        engine.tick(59)
        assertEquals(3540L, engine.remainingSeconds)
        assertFalse(engine.isSubmitted)
    }

    @Test
    fun testPauseAndResumeBehavior() {
        val test = MockExamRepository.getPracticeTestDay02()
        val engine = ActiveExamStateMachine(test)

        engine.tick(10)
        assertEquals(3590L, engine.remainingSeconds)

        engine.pause()
        assertTrue(engine.isPaused)

        // Ticking while paused must not decrement time
        engine.tick(30)
        assertEquals(3590L, engine.remainingSeconds)

        engine.resume()
        assertFalse(engine.isPaused)

        engine.tick(10)
        assertEquals(3580L, engine.remainingSeconds)
    }

    @Test
    fun testTimerExpiryAtZeroTriggersAutoSubmission() {
        val test = MockExamRepository.getPracticeTestDay02()
        val engine = ActiveExamStateMachine(test)

        // Advance to 1 second before expiry
        engine.tick(3599)
        assertEquals(1L, engine.remainingSeconds)
        assertFalse(engine.isAutoSubmitted)

        // Final tick to 00:00:00
        engine.tick(1)
        assertEquals(0L, engine.remainingSeconds)
        assertTrue(engine.isAutoSubmitted)
        assertTrue(engine.isSubmitted)

        // Additional ticks must not result in negative time
        engine.tick(50)
        assertEquals(0L, engine.remainingSeconds)
    }

    @Test
    fun testLast15MinutesWarningPillThreshold() {
        val test = MockExamRepository.getPracticeTestDay02()
        val engine = ActiveExamStateMachine(test)

        // 15 minutes = 900 seconds
        val warningThresholdSeconds = 900L

        engine.tick(2699) // remaining: 901
        assertTrue(engine.remainingSeconds > warningThresholdSeconds)

        engine.tick(1) // remaining: 900
        assertTrue(engine.remainingSeconds <= warningThresholdSeconds)

        engine.tick(100) // remaining: 800
        assertTrue(engine.remainingSeconds <= warningThresholdSeconds)
    }
}
