package com.example.abhyaas.tier1_coverage

import com.example.abhyaas.contract.*
import org.junit.Assert.*
import org.junit.Test

/**
 * Tier 1: Feature Coverage for Abhyaas Mock Repositories
 */
class MockRepositoryCoverageTest {

    @Test
    fun testMockQuestionRepositorySampleQuestions() {
        val giQuestions = MockQuestionRepository.sampleGIQuestions
        assertTrue(giQuestions.isNotEmpty())
        assertEquals(4, giQuestions.size)

        // Question 1: Course of action
        val q1 = giQuestions[0]
        assertEquals(1, q1.id)
        assertEquals("sec_gi", q1.sectionId)
        assertNotNull(q1.directionText)
        assertEquals(1, q1.correctOptionIndex) // Option 2: I and II follow
        assertEquals(4, q1.options.size)

        // Question 2: Mathematical operation
        val q2 = giQuestions[1]
        assertEquals(1, q2.correctOptionIndex) // Option 2: 409

        // Question 3: Symbol substitution
        val q3 = giQuestions[2]
        assertEquals(0, q3.correctOptionIndex) // Option 1: 57

        // Question 4: Triangles count
        val q4 = giQuestions[3]
        assertEquals(2, q4.correctOptionIndex) // Option 3: 16
    }

    @Test
    fun testMockQuestionRepositoryGeneralAwarenessQuestions() {
        val gaQuestions = MockQuestionRepository.sampleGAQuestions
        assertEquals(2, gaQuestions.size)

        // MP Land Revenue Code Patwari
        val q26 = gaQuestions[0]
        assertEquals(26, q26.id)
        assertEquals(1, q26.correctOptionIndex) // Option 2: Patwari
        assertTrue(q26.statementText.contains("Madhya Pradesh Land Revenue Code"))

        // Finance Commission Article 280
        val q27 = gaQuestions[1]
        assertEquals(27, q27.id)
        assertEquals(0, q27.correctOptionIndex) // Option 1: Article 280
    }

    @Test
    fun testMockQuestionRepositoryGenerate100Questions() {
        val sections = MockQuestionRepository.generate100QuestionsExam()
        assertEquals(4, sections.size)

        val sectionTitles = listOf("General Intelligence", "General Awareness", "Quantitative Aptitude", "English Language")
        val sectionParts = listOf("PART - A", "PART - B", "PART - C", "PART - D")

        var totalQCount = 0
        sections.forEachIndexed { index, section ->
            assertEquals(sectionParts[index], section.partName)
            assertEquals(sectionTitles[index], section.title)
            assertEquals(25, section.questions.size)
            totalQCount += section.questions.size

            section.questions.forEach { q ->
                assertEquals(4, q.options.size)
                assertTrue(q.correctOptionIndex in 0..3)
                assertEquals(2.0f, q.positiveMarks, 0.001f)
                assertEquals(0.5f, q.negativeMarks, 0.001f)
            }
        }

        assertEquals(100, totalQCount)
    }

    @Test
    fun testMockExamRepositoryTestSeries() {
        val seriesList = MockExamRepository.getTestSeriesList()
        assertEquals(2, seriesList.size)

        val ssc = seriesList.first { it.id == "ssc_selection_post_14" }
        assertEquals(610, ssc.totalTests)
        assertEquals(30, ssc.fullTestsCount)
        assertEquals(240, ssc.pyqCount)
        assertTrue(ssc.isEnrolled)

        val mpesb = seriesList.first { it.id == "mpesb_nayab_tehsildar_2026" }
        assertEquals(150, mpesb.totalTests)
        assertFalse(mpesb.isEnrolled)
    }

    @Test
    fun testMockExamRepositoryPracticeTest() {
        val test = MockExamRepository.getPracticeTestDay02()
        assertEquals("test_ssc_p14_day02", test.id)
        assertEquals(60, test.durationMinutes)
        assertEquals(100, test.totalQuestions)
        assertEquals(200.0f, test.totalMarks, 0.001f)
        assertTrue(test.isFree)
        assertEquals(4, test.sections.size)
    }

    @Test
    fun testMockUserRepositoryProfileAndTrends() {
        val user = MockUserRepository.getUserProfile()
        assertEquals("Aspirant", user.fullName)
        assertEquals(7, user.totalTestsAttempted)
        assertEquals(55, user.averageScorePercent)

        val trends = MockUserRepository.getPreparationTrend()
        assertEquals(5, trends.size)
        assertEquals("May 2", trends.first().dateLabel)
        assertEquals(85, trends.last().questionsCount)
    }

    @Test
    fun testMockUpdatesRepositoryItemsAndFiltering() {
        val updates = MockUpdatesRepository.getAllUpdates()
        assertEquals(6, updates.size)

        val pinned = updates.filter { it.isPinned }
        assertEquals(1, pinned.size)
        assertEquals("up_1", pinned[0].id)

        val syllabusUpdates = updates.filter { it.category == UpdateCategory.SYLLABUS }
        assertEquals(2, syllabusUpdates.size)
    }

    @Test
    fun testMockLeaderboardRepositoryTopPodiumAndUser() {
        val leaderboard = MockLeaderboardRepository.getLeaderboard()
        assertEquals(9, leaderboard.size)

        // Top 3 Podium
        assertEquals("Raja", leaderboard[0].userName)
        assertEquals(1, leaderboard[0].rank)
        assertEquals(200.0f, leaderboard[0].score, 0.001f)

        assertEquals("Hemant", leaderboard[1].userName)
        assertEquals(2, leaderboard[1].rank)

        assertEquals("Vivek", leaderboard[2].userName)
        assertEquals(3, leaderboard[2].rank)

        // Current User Sticky
        val currentUser = leaderboard.last()
        assertTrue(currentUser.isCurrentUser)
        assertEquals(22789, currentUser.rank)
        assertEquals(0.0f, currentUser.score, 0.001f)
    }
}
