package com.example.abhyaas.m1_empirical_challenge

import com.example.abhyaas.data.mock.MockExamRepository
import com.example.abhyaas.data.mock.MockQuestionRepository
import com.example.abhyaas.data.mock.MockUpdatesRepository
import com.example.abhyaas.data.mock.MockUserRepository
import com.example.abhyaas.data.model.*
import com.example.abhyaas.ui.components.NavIconType
import com.example.abhyaas.ui.components.OptionCardState
import com.example.abhyaas.ui.components.StatPillType
import com.example.abhyaas.ui.components.formatTimerSeconds
import org.junit.Assert.*
import org.junit.Test

/**
 * Adversarial Empirical Verification Suite for Milestone 1:
 * - Domain Models in data.model
 * - Mock Data Repositories in data.mock
 * - 100-Question Dataset Completeness and Integrity
 * - UI Tokens & Component Enums Interoperability
 */
class Milestone1DataIntegrityEmpiricalTest {

    // ====================================================================
    // 1. Question Dataset Completeness & Data Integrity
    // ====================================================================

    @Test
    fun testAll100QuestionsIntegrityAndBoundaryInvariants() {
        val questions = MockQuestionRepository.getQuestionsForTest("default")

        // Invariant 1: Exactly 100 questions must be generated
        assertEquals("Total questions in repository must be exactly 100", 100, questions.size)

        // Invariant 2: IDs must be strictly 1..100 with zero duplicates
        val distinctIds = questions.map { it.id }.toSet()
        assertEquals("All 100 question IDs must be unique", 100, distinctIds.size)
        for (expectedId in 1..100) {
            assertTrue("Question ID $expectedId must exist in questions list", distinctIds.contains(expectedId))
        }

        // Invariant 3: Validate each question's internal consistency
        for (q in questions) {
            // Textual fields must not be empty or blank
            assertFalse("Statement text for Q${q.id} must not be blank", q.statementText.isBlank())
            assertTrue("Statement text for Q${q.id} must be substantial", q.statementText.trim().length >= 5)

            assertFalse("Explanation for Q${q.id} must not be blank", q.explanation.isBlank())
            assertTrue("Explanation for Q${q.id} must be explanatory", q.explanation.trim().length >= 10)

            // Section IDs must be valid
            assertTrue(
                "SectionId '${q.sectionId}' for Q${q.id} must be valid",
                q.sectionId in listOf("sec_a", "sec_b", "sec_c", "sec_d")
            )

            // Options must have exactly 4 items with non-blank text
            assertEquals("Question Q${q.id} must have exactly 4 options", 4, q.options.size)
            val optionIds = q.options.map { it.id }.toSet()
            assertEquals("Question Q${q.id} must have unique option IDs", 4, optionIds.size)

            q.options.forEachIndexed { optIndex, opt ->
                assertFalse("Option $optIndex text for Q${q.id} must not be blank", opt.text.isBlank())
                assertTrue("Option ID for Q${q.id} must be > 0", opt.id > 0)
            }

            // Correct option index must be strictly valid
            assertTrue(
                "correctOptionIndex (${q.correctOptionIndex}) for Q${q.id} must be in range 0..3",
                q.correctOptionIndex in 0..3
            )
            assertNotNull(
                "Selected correct option for Q${q.id} must not be null",
                q.options.getOrNull(q.correctOptionIndex)
            )

            // Marking scheme invariants
            assertEquals("Positive marks must be 2.0f for Q${q.id}", 2.0f, q.positiveMarks, 0.0001f)
            assertEquals("Negative marks must be 0.5f for Q${q.id}", 0.5f, q.negativeMarks, 0.0001f)

            // Metrics bounds
            assertTrue("percentGotRight (${q.percentGotRight}%) for Q${q.id} must be in 0..100", q.percentGotRight in 0..100)
            assertTrue("averageTimeSeconds (${q.averageTimeSeconds}s) for Q${q.id} must be positive", q.averageTimeSeconds > 0)

            // Topic and subject
            assertFalse("Topic for Q${q.id} must not be blank", q.topic.isBlank())
            assertFalse("Subject for Q${q.id} must not be blank", q.subject.isBlank())
        }
    }

    @Test
    fun testSectionDistributionAndFidelity() {
        val sections = MockQuestionRepository.getSectionsForTest("default")

        // Invariant 1: Exactly 4 sections
        assertEquals("Must have exactly 4 exam sections", 4, sections.size)

        val expectedSections = listOf(
            Triple("sec_a", "PART - A", "General Intelligence"),
            Triple("sec_b", "PART - B", "General Awareness"),
            Triple("sec_c", "PART - C", "Quantitative Aptitude"),
            Triple("sec_d", "PART - D", "English Language")
        )

        var totalQuestionCount = 0
        sections.forEachIndexed { index, section ->
            val expected = expectedSections[index]
            assertEquals("Section $index id mismatch", expected.first, section.id)
            assertEquals("Section $index partName mismatch", expected.second, section.partName)
            assertEquals("Section $index title mismatch", expected.third, section.title)

            // Invariant 2: Each section must contain exactly 25 questions
            assertEquals("Section ${section.partName} must have exactly 25 questions", 25, section.questions.size)
            totalQuestionCount += section.questions.size

            // Invariant 3: Section question ID ranges
            val expectedIdRange = when (section.id) {
                "sec_a" -> 1..25
                "sec_b" -> 26..50
                "sec_c" -> 51..75
                "sec_d" -> 76..100
                else -> 0..0
            }

            section.questions.forEach { q ->
                assertEquals("Question sectionId mismatch in ${section.id}", section.id, q.sectionId)
                assertTrue("Question ID ${q.id} must be in range $expectedIdRange", q.id in expectedIdRange)
            }
        }

        assertEquals("Sum of questions across all sections must be 100", 100, totalQuestionCount)
    }

    @Test
    fun testQuestionLookupByIdEdgeCases() {
        // Valid lookup
        for (id in 1..100) {
            val q = MockQuestionRepository.getQuestionById(id)
            assertNotNull("getQuestionById($id) must return question", q)
            assertEquals("Question ID must match requested ID", id, q!!.id)
        }

        // Adversarial out-of-bounds lookup must return null
        assertNull("Lookup with id 0 must return null", MockQuestionRepository.getQuestionById(0))
        assertNull("Lookup with negative id must return null", MockQuestionRepository.getQuestionById(-1))
        assertNull("Lookup with id 101 must return null", MockQuestionRepository.getQuestionById(101))
        assertNull("Lookup with large id must return null", MockQuestionRepository.getQuestionById(9999))
    }

    // ====================================================================
    // 2. MockExamRepository Verification
    // ====================================================================

    @Test
    fun testMockExamRepositoryIntegrity() {
        val seriesList = MockExamRepository.getTestSeriesList()
        assertEquals("Must provide 2 test series (SSC and MPESB)", 2, seriesList.size)

        val ssc = seriesList.first { it.id == "ssc_selection_post_2026" }
        assertEquals(610, ssc.totalTests)
        assertEquals(30, ssc.fullTestsCount)
        assertEquals(90, ssc.pyqCount)
        assertTrue(ssc.isEnrolled)
        assertEquals(6, ssc.mockFolders.size)
        assertEquals(4, ssc.pypFolders.size)

        val mp = seriesList.first { it.id == "mpesb_nayab_tehsildar_2026" }
        assertEquals(600, mp.totalTests)
        assertEquals(30, mp.fullTestsCount)
        assertEquals(90, mp.pyqCount)
        assertEquals(3, mp.mockFolders.size)
        assertEquals(2, mp.pypFolders.size)

        // Home categories
        val homeCats = MockExamRepository.getHomeCategories()
        assertEquals("Must provide 7 home category tiles", 7, homeCats.size)
        homeCats.forEach { cat ->
            assertFalse("Category title must not be blank", cat.title.isBlank())
            assertFalse("Category id must not be blank", cat.id.isBlank())
        }

        // Subcategory test listing
        val tests = MockExamRepository.getTestsForSubCategory("ssc_selection_post_2026", "Exam Day Special")
        assertEquals(4, tests.size)

        val day01 = tests.first { it.id == "ssc_test_day_01" }
        assertNotNull("Practice Test Day 01 must have a previousAttempt record", day01.previousAttempt)
        assertEquals(0.0f, day01.previousAttempt!!.score, 0.001f)
        assertEquals(22789, day01.previousAttempt!!.rank)
        assertEquals(24964, day01.previousAttempt!!.totalCandidates)

        val day02 = tests.first { it.id == "ssc_test_day_02" }
        assertNull("Practice Test Day 02 must have no previous attempt", day02.previousAttempt)
        assertEquals(60, day02.durationMinutes)
        assertEquals(100, day02.totalQuestions)
        assertEquals(200.0f, day02.totalMarks, 0.001f)
        assertEquals(4, day02.sections.size)

        // Instructions validation
        assertEquals(7, day02.instructions.size)
        assertTrue(day02.instructions[0].contains("100 questions"))
        assertTrue(day02.instructions[1].contains("4 options"))
        assertTrue(day02.instructions[2].contains("60 minutes"))
        assertTrue(day02.instructions[4].contains("2 marks") && day02.instructions[4].contains("0.5 negative"))

        // Previous attempt scorecard
        val result = MockExamRepository.getPreviousAttemptResult("ssc_test_day_01")
        assertEquals("att_day_01", result.attemptId)
        assertEquals("ssc_test_day_01", result.testId)
        assertEquals(0.0f, result.score, 0.001f)
        assertEquals(200.0f, result.totalMarks, 0.001f)
        assertEquals(22789, result.rank)
        assertEquals(24964, result.totalCandidates)
        assertEquals(100, result.unattemptedCount)
        assertEquals(0, result.correctCount)
        assertEquals(0, result.incorrectCount)
        assertEquals(4, result.sectionBreakdowns.size)

        val totalSectionQs = result.sectionBreakdowns.sumOf { it.totalQuestions }
        assertEquals(100, totalSectionQs)
        val totalSectionUnattempted = result.sectionBreakdowns.sumOf { it.unattemptedCount }
        assertEquals(100, totalSectionUnattempted)
    }

    // ====================================================================
    // 3. MockUserRepository Verification
    // ====================================================================

    @Test
    fun testMockUserRepositoryProfileAndLeaderboardIntegrity() {
        val user = MockUserRepository.getUserProfile()
        assertEquals("Aspirant", user.fullName)
        assertEquals("aspirant@abhyaas.edu", user.email)
        assertEquals("+91 98765 43210", user.mobileNumber)
        assertEquals("General", user.category)
        assertEquals(7, user.totalTestsAttempted)
        assertEquals(55, user.averageScorePercent)

        // Update profile test
        val modified = user.copy(fullName = "Updated Aspirant", averageScorePercent = 60)
        MockUserRepository.updateUserProfile(modified)
        assertEquals("Updated Aspirant", MockUserRepository.getUserProfile().fullName)
        assertEquals(60, MockUserRepository.getUserProfile().averageScorePercent)
        // Reset to original
        MockUserRepository.updateUserProfile(user)

        // Trend points
        val trends = MockUserRepository.getPreparationDataPoints("Questions")
        assertEquals(6, trends.size)
        assertEquals("May 2", trends[0].dateLabel)
        assertEquals(24, trends[0].questionsCount)
        assertEquals(65, trends[0].accuracyPercent)

        // Leaderboard
        val leaderboard = MockUserRepository.getLeaderboard()
        assertEquals(8, leaderboard.size)

        // Top 3 Podium
        assertEquals(1, leaderboard[0].rank)
        assertEquals("Raja", leaderboard[0].userName)
        assertEquals(200.0f, leaderboard[0].score, 0.001f)
        assertEquals(100.0f, leaderboard[0].accuracy, 0.001f)

        assertEquals(2, leaderboard[1].rank)
        assertEquals("Hemant", leaderboard[1].userName)
        assertEquals(195.0f, leaderboard[1].score, 0.001f)

        assertEquals(3, leaderboard[2].rank)
        assertEquals("Vivek", leaderboard[2].userName)
        assertEquals(193.5f, leaderboard[2].score, 0.001f)

        // Monotonic non-increasing score decay for top 7
        for (i in 0 until 6) {
            assertTrue(
                "Score for rank ${leaderboard[i].rank} (${leaderboard[i].score}) must be >= rank ${leaderboard[i+1].rank} (${leaderboard[i+1].score})",
                leaderboard[i].score >= leaderboard[i + 1].score
            )
            assertFalse(leaderboard[i].isCurrentUser)
        }

        // Sticky card for current user
        val currentUserEntry = leaderboard.last()
        assertTrue("Last leaderboard entry must be current user", currentUserEntry.isCurrentUser)
        assertEquals("Aspirant (You)", currentUserEntry.userName)
        assertEquals(22789, currentUserEntry.rank)
        assertEquals(0.0f, currentUserEntry.score, 0.001f)
    }

    // ====================================================================
    // 4. MockUpdatesRepository Verification
    // ====================================================================

    @Test
    fun testMockUpdatesRepositoryIntegrityAndFiltering() {
        val allUpdates = MockUpdatesRepository.getAllUpdates()
        assertEquals(6, allUpdates.size)

        val uniqueIds = allUpdates.map { it.id }.toSet()
        assertEquals(6, uniqueIds.size)

        // Exactly 1 pinned item
        val pinned = allUpdates.filter { it.isPinned }
        assertEquals(1, pinned.size)
        assertEquals("upd_1", pinned[0].id)
        assertTrue(pinned[0].title.contains("Notification Released"))

        // Category filtering
        assertEquals(6, MockUpdatesRepository.getUpdatesByCategory(UpdateCategory.ALL).size)
        assertEquals(1, MockUpdatesRepository.getUpdatesByCategory(UpdateCategory.NOTIFICATIONS).size)
        assertEquals(1, MockUpdatesRepository.getUpdatesByCategory(UpdateCategory.EXAM_DATES).size)
        assertEquals(2, MockUpdatesRepository.getUpdatesByCategory(UpdateCategory.SYLLABUS).size)
        assertEquals(1, MockUpdatesRepository.getUpdatesByCategory(UpdateCategory.ADMIT_CARD).size)
        assertEquals(1, MockUpdatesRepository.getUpdatesByCategory(UpdateCategory.RESULTS).size)
        assertEquals(0, MockUpdatesRepository.getUpdatesByCategory(UpdateCategory.OFFICIAL_PDFS).size)
    }

    // ====================================================================
    // 5. Domain Models and UI Enums Completeness
    // ====================================================================

    @Test
    fun testDomainEnumsAndUIComponentsTokens() {
        // QuestionStatus enum completeness (all 5 states from exam engine)
        val questionStatuses = QuestionStatus.values()
        assertEquals(5, questionStatuses.size)
        assertTrue(questionStatuses.contains(QuestionStatus.NOT_VISITED))
        assertTrue(questionStatuses.contains(QuestionStatus.UNANSWERED))
        assertTrue(questionStatuses.contains(QuestionStatus.ANSWERED))
        assertTrue(questionStatuses.contains(QuestionStatus.MARKED_FOR_REVIEW))
        assertTrue(questionStatuses.contains(QuestionStatus.ANSWERED_AND_MARKED))

        // OptionCardState enum completeness
        val cardStates = OptionCardState.values()
        assertEquals(5, cardStates.size)

        // StatPillType enum completeness
        val pillTypes = StatPillType.values()
        assertEquals(3, pillTypes.size)

        // NavIconType enum completeness
        val navIcons = NavIconType.values()
        assertEquals(5, navIcons.size)

        // Timer formatting helper
        assertEquals("01:00:00", formatTimerSeconds(3600, showHours = true))
        assertEquals("00:45:30", formatTimerSeconds(2730, showHours = true))
        assertEquals("45:30", formatTimerSeconds(2730, showHours = false))
        assertEquals("00:45", formatTimerSeconds(45, showHours = false))
        assertEquals("00:00", formatTimerSeconds(0, showHours = false))
        assertEquals("00:00", formatTimerSeconds(-10, showHours = false))
    }

    // ====================================================================
    // 6. Specific Authentic Surveyed Questions Verification
    // ====================================================================

    @Test
    fun testAuthenticSurveyedQuestionsContent() {
        // Q1: Reasoning - Courses of action
        val q1 = MockQuestionRepository.getQuestionById(1)!!
        assertTrue(q1.statementText.contains("criminal activities"))
        assertEquals("Statement & Courses of Action", q1.topic)
        assertEquals(1, q1.correctOptionIndex)
        assertEquals("I and II follow", q1.options[1].text)
        assertNotNull(q1.directionText)
        assertNotNull(q1.directionTextHindi)

        // Q2: BODMAS
        val q2 = MockQuestionRepository.getQuestionById(2)!!
        assertTrue(q2.statementText.contains("26 + 342 - 19 x 73 ÷ 14"))
        assertEquals(1, q2.correctOptionIndex)
        assertEquals("409", q2.options[1].text)

        // Q26: GA - MP Land Revenue Code Patwari
        val q26 = MockQuestionRepository.getQuestionById(26)!!
        assertTrue(q26.statementText.contains("Madhya Pradesh Land Revenue Code"))
        assertEquals(1, q26.correctOptionIndex)
        assertEquals("Patwari", q26.options[1].text)

        // Q27: GA - Article 280 Finance Commission
        val q27 = MockQuestionRepository.getQuestionById(27)!!
        assertTrue(q27.statementText.contains("Finance Commission"))
        assertEquals(0, q27.correctOptionIndex)
        assertEquals("Article 280", q27.options[0].text)

        // Q99: English - Utopia
        val q99 = MockQuestionRepository.getQuestionById(99)!!
        assertTrue(q99.statementText.contains("imaginary ideal society"))
        assertEquals(0, q99.correctOptionIndex)
        assertEquals("Utopia", q99.options[0].text)

        // Q100: English - Piece of cake
        val q100 = MockQuestionRepository.getQuestionById(100)!!
        assertTrue(q100.statementText.contains("Piece of cake"))
        assertEquals(0, q100.correctOptionIndex)
        assertEquals("Something that is very easy to accomplish", q100.options[0].text)
    }

    // ====================================================================
    // 7. Adversarial Stress & Edge Case Tests
    // ====================================================================

    @Test
    fun testNoDuplicateOptionTextsWithinAnyQuestion() {
        val questions = MockQuestionRepository.getQuestionsForTest("default")
        for (q in questions) {
            val optionTexts = q.options.map { it.text.trim().lowercase() }
            assertEquals(
                "Question Q${q.id} (${q.subject}) has duplicate option texts: $optionTexts",
                q.options.size,
                optionTexts.toSet().size
            )
        }
    }

    @Test
    fun testSectionBreakdownAggregationInvariants() {
        val result = MockExamRepository.getPreviousAttemptResult("ssc_test_day_01")
        assertEquals(4, result.sectionBreakdowns.size)

        var accumulatedQuestions = 0
        var accumulatedUnattempted = 0
        var accumulatedCorrect = 0
        var accumulatedIncorrect = 0

        for (section in result.sectionBreakdowns) {
            assertEquals("Each section in breakdown must have 25 questions", 25, section.totalQuestions)
            accumulatedQuestions += section.totalQuestions
            accumulatedUnattempted += section.unattemptedCount
            accumulatedCorrect += section.correctCount
            accumulatedIncorrect += section.incorrectCount
        }

        assertEquals(result.totalMarks, 200.0f, 0.001f)
        assertEquals(100, accumulatedQuestions)
        assertEquals(result.unattemptedCount, accumulatedUnattempted)
        assertEquals(result.correctCount, accumulatedCorrect)
        assertEquals(result.incorrectCount, accumulatedIncorrect)
    }

    @Test
    fun testHighThroughputQuestionRepositoryAccess() {
        val startTime = System.currentTimeMillis()
        val iterations = 100
        for (i in 0 until iterations) {
            val list = MockQuestionRepository.getQuestionsForTest("test_$i")
            assertEquals(100, list.size)
        }
        val duration = System.currentTimeMillis() - startTime
        assertTrue("Fetching 100 questions 100 times took ${duration}ms, must be < 2000ms", duration < 2000)
    }

    @Test
    fun testMockUserRepositoryBoundaryValues() {
        val original = MockUserRepository.getUserProfile()
        try {
            val zeroState = original.copy(
                fullName = "",
                averageScorePercent = 0,
                totalTestsAttempted = 0,
                totalStudyTimeHours = 0
            )
            MockUserRepository.updateUserProfile(zeroState)
            val fetched = MockUserRepository.getUserProfile()
            assertEquals("", fetched.fullName)
            assertEquals(0, fetched.averageScorePercent)
            assertEquals(0, fetched.totalTestsAttempted)
            assertEquals(0, fetched.totalStudyTimeHours)

            val maxState = original.copy(
                averageScorePercent = 100,
                totalTestsAttempted = 9999,
                totalStudyTimeHours = 500
            )
            MockUserRepository.updateUserProfile(maxState)
            val fetchedMax = MockUserRepository.getUserProfile()
            assertEquals(100, fetchedMax.averageScorePercent)
            assertEquals(9999, fetchedMax.totalTestsAttempted)
        } finally {
            MockUserRepository.updateUserProfile(original)
        }
    }

    @Test
    fun testDevanagariUnicodeFidelity() {
        val questions = MockQuestionRepository.getQuestionsForTest("default")
        val bilingualQuestions = questions.filter { it.statementTextHindi != null || it.directionTextHindi != null }
        assertTrue("Repository must have bilingual questions with Hindi text", bilingualQuestions.isNotEmpty())

        val devanagariRegex = Regex("[\\u0900-\\u097F]")
        for (q in bilingualQuestions) {
            q.statementTextHindi?.let { hindiStatement ->
                assertTrue(
                    "Hindi statement in Q${q.id} must contain Devanagari characters: $hindiStatement",
                    devanagariRegex.containsMatchIn(hindiStatement)
                )
            }
            q.directionTextHindi?.let { hindiDirection ->
                assertTrue(
                    "Hindi direction in Q${q.id} must contain Devanagari characters: $hindiDirection",
                    devanagariRegex.containsMatchIn(hindiDirection)
                )
            }
        }
    }

    @Test
    fun testMockExamRepositoryEdgeFallbacks() {
        // Fallback on unknown test series ID
        val seriesUnknown = MockExamRepository.getTestSeriesById("nonexistent_series_xyz")
        assertNotNull("getTestSeriesById with unknown id must fallback safely to default series", seriesUnknown)
        assertEquals("ssc_selection_post_2026", seriesUnknown?.id)

        // Fallback on unknown test ID
        val testUnknown = MockExamRepository.getTestById("nonexistent_test_xyz")
        assertNotNull("getTestById with unknown id must fallback safely to default test", testUnknown)
        assertEquals("ssc_test_day_02", testUnknown?.id)
    }
}

