package com.example.abhyaas.tier1_coverage

import com.example.abhyaas.contract.*
import org.junit.Assert.*
import org.junit.Test

/**
 * Tier 1: Feature Coverage for Abhyaas Domain Models & Status Enums
 */
class DomainModelCoverageTest {

    @Test
    fun testOptionCreationAndBilingualText() {
        val option = Option(
            id = 1,
            text = "Patwari",
            textHindi = "पटवारी"
        )
        assertEquals(1, option.id)
        assertEquals("Patwari", option.text)
        assertEquals("पटवारी", option.textHindi)
    }

    @Test
    fun testQuestionStatusEnumValues() {
        val statuses = QuestionStatus.values()
        assertEquals(5, statuses.size)
        assertTrue(statuses.contains(QuestionStatus.NOT_VISITED))
        assertTrue(statuses.contains(QuestionStatus.UNANSWERED))
        assertTrue(statuses.contains(QuestionStatus.ANSWERED))
        assertTrue(statuses.contains(QuestionStatus.MARKED_FOR_REVIEW))
        assertTrue(statuses.contains(QuestionStatus.ANSWERED_AND_MARKED))
    }

    @Test
    fun testQuestionDefaultsAndMarkingScheme() {
        val q = Question(
            id = 101,
            sectionId = "sec_gi",
            questionNumber = 1,
            statementText = "Sample reasoning statement",
            options = listOf(
                Option(1, "A"), Option(2, "B"), Option(3, "C"), Option(4, "D")
            ),
            correctOptionIndex = 0,
            explanation = "Sample explanation",
            topic = "Analogy",
            subject = "General Intelligence"
        )

        assertEquals(2.0f, q.positiveMarks, 0.001f)
        assertEquals(0.5f, q.negativeMarks, 0.001f)
        assertEquals(64, q.percentGotRight)
        assertEquals(45, q.averageTimeSeconds)
        assertFalse(q.isBookmarked)
        assertNull(q.directionText)
        assertNull(q.statementTextHindi)
    }

    @Test
    fun testTestSectionModel() {
        val q1 = Question(
            id = 1, sectionId = "sec_gi", questionNumber = 1,
            statementText = "Q1", options = emptyList(), correctOptionIndex = 0,
            explanation = "Exp", topic = "Logic", subject = "GI"
        )
        val section = TestSection(
            id = "sec_gi",
            partName = "PART - A",
            title = "General Intelligence",
            titleHindi = "सामान्य बुद्धिमत्ता",
            questions = listOf(q1)
        )
        assertEquals("sec_gi", section.id)
        assertEquals("PART - A", section.partName)
        assertEquals("General Intelligence", section.title)
        assertEquals("सामान्य बुद्धिमत्ता", section.titleHindi)
        assertEquals(1, section.questions.size)
    }

    @Test
    fun testTestModelDefaultParameters() {
        val test = Test(
            id = "test_1",
            seriesId = "series_1",
            title = "SSC Selection Post Practice Test",
            subCategory = "Exam Day Special",
            sections = emptyList()
        )

        assertEquals(60, test.durationMinutes)
        assertEquals(100, test.totalQuestions)
        assertEquals(200.0f, test.totalMarks, 0.001f)
        assertTrue(test.isFree)
        assertEquals(listOf("English", "Hindi"), test.supportedLanguages)
        assertEquals("132-135", test.cutoffGeneral)
        assertEquals(67.75f, test.averageScore, 0.001f)
        assertEquals(200.0f, test.bestScore, 0.001f)
    }

    @Test
    fun testUserProfileDefaults() {
        val user = UserProfile()
        assertEquals("Aspirant", user.fullName)
        assertEquals("aspirant@abhyaas.edu", user.email)
        assertEquals("+91 98765 43210", user.mobileNumber)
        assertEquals("General", user.category)
        assertEquals(55, user.averageScorePercent)
        assertEquals(7, user.totalTestsAttempted)
        assertEquals(2, user.totalStudyTimeHours)
    }

    @Test
    fun testLeaderboardEntryCurrentUserFlag() {
        val topper = LeaderboardEntry(
            rank = 1,
            userName = "Raja",
            score = 200.0f,
            maxScore = 200.0f,
            accuracy = 100.0f,
            isCurrentUser = false
        )
        val me = LeaderboardEntry(
            rank = 22789,
            userName = "Aspirant (You)",
            score = 0.0f,
            maxScore = 200.0f,
            accuracy = 0.0f,
            isCurrentUser = true
        )

        assertFalse(topper.isCurrentUser)
        assertTrue(me.isCurrentUser)
        assertEquals(200.0f, topper.score, 0.001f)
        assertEquals(0.0f, me.score, 0.001f)
    }

    @Test
    fun testExamUpdateItemAndCategoryEnum() {
        val categories = UpdateCategory.values()
        assertEquals(7, categories.size)

        val item = ExamUpdateItem(
            id = "up_1",
            title = "SSC CGL 2026 Notification Released",
            description = "Tentative vacancies announced",
            date = "28 Sep 2026",
            category = UpdateCategory.NOTIFICATIONS,
            isPinned = true,
            pdfSize = "2.4 MB"
        )

        assertTrue(item.isPinned)
        assertEquals("2.4 MB", item.pdfSize)
        assertEquals("Download PDF", item.actionText)
    }
}
