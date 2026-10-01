package com.example.abhyaas.data.mock

import com.example.abhyaas.data.model.*

object MockExamRepository {

    // ─── MPSEB Exam Series ───────────────────────────────────────────────────

    private val mpsebSeries = TestSeries(
        id = "mpseb_2026",
        title = "MPSEB (MP State Electricity Board) Recruitment 2026",
        subtitle = "50+ Practice Tests | 10 Full Mock Tests | Previous Year Papers",
        categoryId = "mpseb",
        totalTests = 60,
        fullTestsCount = 10,
        pyqCount = 15,
        attemptedCount = 0,
        vacancies = "Multiple Posts",
        examDates = "2026",
        isEnrolled = true,
        mockFolders = listOf(
            TestSeriesFolder("mpseb_f1", "Full Mock Tests", "10 Tests", freeTestsBadge = "2 Free Tests"),
            TestSeriesFolder("mpseb_f2", "Technical Section Practice", "20 Tests"),
            TestSeriesFolder("mpseb_f3", "GK & Current Affairs", "15 Tests", freeTestsBadge = "3 Free Tests"),
            TestSeriesFolder("mpseb_f4", "Reasoning Practice Tests", "15 Tests")
        ),
        pypFolders = listOf(
            TestSeriesFolder("mpseb_p1", "Previous Year Paper Set 1", "5 Papers", isPYQ = true),
            TestSeriesFolder("mpseb_p2", "Previous Year Paper Set 2", "5 Papers", isPYQ = true),
            TestSeriesFolder("mpseb_p3", "Previous Year Paper Set 3", "5 Papers", isPYQ = true)
        ),
        studyNotesFolders = listOf(
            TestSeriesFolder("mpseb_n1", "Electrical Engineering Formula Notes", "12 Chapters", freeTestsBadge = "Free PDF"),
            TestSeriesFolder("mpseb_n2", "General Awareness Quick Revision", "10 Topics", freeTestsBadge = "Free PDF"),
            TestSeriesFolder("mpseb_n3", "Reasoning & Aptitude Summary", "8 Modules")
        )
    )

    // ─── Nayab Tehsildar Exam Series ─────────────────────────────────────────

    private val tehsildarSeries = TestSeries(
        id = "nayab_tehsildar_2026",
        title = "MP Nayab Tehsildar Departmental Exam 2026",
        subtitle = "Paper 1 (GK + Reasoning) | Paper 2 (Rajasva Shabdavali)",
        categoryId = "nayab_tehsildar",
        totalTests = 40,
        fullTestsCount = 8,
        pyqCount = 10,
        attemptedCount = 1,
        vacancies = "73 Posts",
        examDates = "Nov - 2026",
        isEnrolled = true,
        mockFolders = listOf(
            TestSeriesFolder("teh_f1", "Paper 1: Full Mock Tests (GK + Reasoning)", "8 Tests", freeTestsBadge = "2 Free Tests"),
            TestSeriesFolder("teh_f2", "Paper 2: Rajasva Shabdavali Practice", "15 Tests", freeTestsBadge = "3 Free Tests"),
            TestSeriesFolder("teh_f3", "Samanya Gyan (GK) Section Practice", "10 Tests"),
            TestSeriesFolder("teh_f4", "Reasoning Section Practice", "7 Tests")
        ),
        pypFolders = listOf(
            TestSeriesFolder("teh_p1", "Departmental Paper 1 (PYQ)", "5 Papers", isPYQ = true),
            TestSeriesFolder("teh_p2", "Departmental Paper 2 (Rajasva Shabdavali PYQ)", "5 Papers", isPYQ = true)
        ),
        studyNotesFolders = listOf(
            TestSeriesFolder("teh_n1", "Rajasva Shabdavali (Revenue Terminology)", "14 Chapters", freeTestsBadge = "Free PDF"),
            TestSeriesFolder("teh_n2", "MP Land Revenue Code 1959 Summary", "9 Modules", freeTestsBadge = "Free PDF"),
            TestSeriesFolder("teh_n3", "MP General Knowledge & History", "18 Chapters"),
            TestSeriesFolder("teh_n4", "Administrative Law & Procedures", "6 Topics")
        )
    )

    // ─── Home Categories ─────────────────────────────────────────────────────

    private val homeCategories = listOf(
        HomeCategoryItem("cat_notes", "Study Notes", subtitle = "Expert Study Notes for Reference", iconName = "Description", badge = "NEW", gradientStartColorHex = 0xFF7C3AED, gradientEndColorHex = 0xFF5B21B6),
        HomeCategoryItem("cat_pyq", "Previous Year Papers", subtitle = "Access PYQs of all exams", iconName = "HistoryEdu", badge = null, gradientStartColorHex = 0xFFD97706, gradientEndColorHex = 0xFFB45309),
        HomeCategoryItem("cat_practice", "Practice Section", subtitle = "Chapter-wise Practice Questions", iconName = "Restore", badge = null, gradientStartColorHex = 0xFF2563EB, gradientEndColorHex = 0xFF1D4ED8),
        HomeCategoryItem("cat_live", "Live Tests & Quizzes", subtitle = "Attempt Live Tests and Quizzes", iconName = "Assignment", badge = null, gradientStartColorHex = 0xFF4F46E5, gradientEndColorHex = 0xFF3730A3),
        HomeCategoryItem("cat_classes", "Daily Live Classes", subtitle = "Interactive Live Classes & Doubt Sessions", iconName = "School", badge = null, gradientStartColorHex = 0xFFDC2626, gradientEndColorHex = 0xFF991B1B),
        HomeCategoryItem("cat_quiz", "Quiz Section", subtitle = "Daily GK & Subject Quizzes", iconName = "Quiz", badge = null, gradientStartColorHex = 0xFFDB2777, gradientEndColorHex = 0xFF9D174D),
        HomeCategoryItem("cat_current_affairs", "Current Affairs", subtitle = "Monthly & Daily Updates", iconName = "Public", badge = null, gradientStartColorHex = 0xFF1E293B, gradientEndColorHex = 0xFF0F172A)
    )

    fun getHomeCategories(): List<HomeCategoryItem> = homeCategories

    fun getTestSeriesList(): List<TestSeries> = listOf(
        TestSeries(
            id = "ssc_selection_post_2026",
            title = "SSC Selection Post (Phase 14) 2026 Mock Test Series",
            subtitle = "600+ Total Tests, 30 Full Tests, 90+ PYQs, Vacancies 3000+",
            categoryId = "ssc",
            totalTests = 610,
            fullTestsCount = 30,
            pyqCount = 90,
            attemptedCount = 1,
            vacancies = "3000+",
            examDates = "Sep - 2026",
            isEnrolled = true,
            mockFolders = listOf(
                TestSeriesFolder("m1", "Folder 1", "x"), TestSeriesFolder("m2", "Folder 2", "x"),
                TestSeriesFolder("m3", "Folder 3", "x"), TestSeriesFolder("m4", "Folder 4", "x"),
                TestSeriesFolder("m5", "Folder 5", "x"), TestSeriesFolder("m6", "Folder 6", "x")
            ),
            pypFolders = listOf(
                TestSeriesFolder("p1", "PYP 1", "x"), TestSeriesFolder("p2", "PYP 2", "x"),
                TestSeriesFolder("p3", "PYP 3", "x"), TestSeriesFolder("p4", "PYP 4", "x")
            ),
            studyNotesFolders = listOf()
        ),
        TestSeries(
            id = "mpesb_nayab_tehsildar_2026",
            title = "MPESB Nayab Tehsildar & Revenue Inspector 2026",
            subtitle = "150+ Tests, MP GK, Land Revenue Code special tests",
            categoryId = "mp_exams",
            totalTests = 600,
            fullTestsCount = 30,
            pyqCount = 90,
            attemptedCount = 0,
            vacancies = "450+",
            examDates = "Nov - 2026",
            isEnrolled = false,
            mockFolders = listOf(
                TestSeriesFolder("m1", "Folder 1", "x"), TestSeriesFolder("m2", "Folder 2", "x"),
                TestSeriesFolder("m3", "Folder 3", "x")
            ),
            pypFolders = listOf(
                TestSeriesFolder("p1", "PYP 1", "x"), TestSeriesFolder("p2", "PYP 2", "x")
            ),
            studyNotesFolders = listOf()
        )
    )

    fun getTestSeriesById(id: String): TestSeries? {
        return getTestSeriesList().find { it.id == id } ?: getTestSeriesList().first { it.id == "ssc_selection_post_2026" }
    }

    fun getTestsForSubCategory(seriesId: String, subCategory: String): List<Test> {
        val tests = mutableListOf<Test>()
        val defaultInstructions = listOf("100 questions", "4 options", "60 minutes", "Instruction 4", "2 marks, 0.5 negative", "Instruction 6", "Instruction 7")
        if (seriesId == "ssc_selection_post_2026" && subCategory == "Exam Day Special") {
            tests.add(Test(
                id = "ssc_test_day_01",
                seriesId = seriesId,
                title = "Practice Test Day - 01",
                subCategory = subCategory,
                durationMinutes = 60,
                totalQuestions = 100,
                totalMarks = 200.0f,
                isFree = true,
                sections = MockQuestionRepository.getSectionsForTest("default"),
                instructions = defaultInstructions,
                previousAttempt = TestAttemptSummary(
                    attemptId = "att_day_01",
                    score = 0.0f,
                    maxScore = 200.0f,
                    rank = 22789,
                    totalCandidates = 24964,
                    attemptDate = "Today",
                    accuracy = 0.0f
                )
            ))
            tests.add(Test(
                id = "ssc_test_day_02",
                seriesId = seriesId,
                title = "Practice Test Day - 02",
                subCategory = subCategory,
                durationMinutes = 60,
                totalQuestions = 100,
                totalMarks = 200.0f,
                isFree = true,
                sections = MockQuestionRepository.getSectionsForTest("default"),
                instructions = defaultInstructions,
                previousAttempt = null
            ))
            tests.add(Test(
                id = "ssc_test_day_03",
                seriesId = seriesId,
                title = "Practice Test Day - 03",
                subCategory = subCategory,
                durationMinutes = 60,
                totalQuestions = 100,
                totalMarks = 200.0f,
                isFree = true,
                sections = MockQuestionRepository.getSectionsForTest("default"),
                instructions = defaultInstructions,
                previousAttempt = null
            ))
            tests.add(Test(
                id = "ssc_test_day_04",
                seriesId = seriesId,
                title = "Practice Test Day - 04",
                subCategory = subCategory,
                durationMinutes = 60,
                totalQuestions = 100,
                totalMarks = 200.0f,
                isFree = true,
                sections = MockQuestionRepository.getSectionsForTest("default"),
                instructions = defaultInstructions,
                previousAttempt = null
            ))
        }
        return tests
    }

    fun getTestById(testId: String): Test? {
        if (testId == "ssc_test_day_01") return getTestsForSubCategory("ssc_selection_post_2026", "Exam Day Special").find { it.id == "ssc_test_day_01" }
        if (testId == "ssc_test_day_02") return getTestsForSubCategory("ssc_selection_post_2026", "Exam Day Special").find { it.id == "ssc_test_day_02" }
        return getTestsForSubCategory("ssc_selection_post_2026", "Exam Day Special").find { it.id == "ssc_test_day_02" }
    }

    fun getPreviousAttemptResult(testId: String): TestResult {
        return TestResult(
            attemptId = "att_day_01",
            testId = "ssc_test_day_01",
            score = 0.0f,
            totalMarks = 200.0f,
            rank = 22789,
            totalCandidates = 24964,
            percentile = 8.72f,
            accuracy = 0.0f,
            correctCount = 0,
            incorrectCount = 0,
            unattemptedCount = 100,
            cutoffMarks = "132-135",
            averageScore = 67.75f,
            bestScore = 200.0f,
            attemptDate = "Today",
            sectionBreakdowns = listOf(
                SectionResult("sec_a", "PART - A", 0f, 0, 0, 25, 0, 0f, 0L, 25),
                SectionResult("sec_b", "PART - B", 0f, 0, 0, 25, 0, 0f, 0L, 25),
                SectionResult("sec_c", "PART - C", 0f, 0, 0, 25, 0, 0f, 0L, 25),
                SectionResult("sec_d", "PART - D", 0f, 0, 0, 25, 0, 0f, 0L, 25)
            ),
            userAnswers = emptyMap(),
            testTitle = "Practice Test Day - 01"
        )
    }
}
