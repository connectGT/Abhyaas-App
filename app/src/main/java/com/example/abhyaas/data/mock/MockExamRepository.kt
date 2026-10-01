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

    fun getTestSeriesList(): List<TestSeries> = listOf(tehsildarSeries, mpsebSeries)

    fun getTestSeriesById(id: String): TestSeries? {
        return getTestSeriesList().find { it.id == id } ?: tehsildarSeries
    }

    fun getTestsForSubCategory(seriesId: String, subCategory: String): List<Test> {
        val isTehsildar = seriesId == "nayab_tehsildar_2026"
        val sections = if (isTehsildar)
            MockQuestionRepository.getTehsildarSections()
        else
            MockQuestionRepository.getMpsebSections()

        val isPYQ = subCategory.contains("PYQ", ignoreCase = true)
        val isGK = subCategory.contains("GK", ignoreCase = true) || subCategory.contains("Gyan", ignoreCase = true)
        val isRajasva = subCategory.contains("Rajasva", ignoreCase = true)

        val prefix = when {
            isPYQ -> "Previous Year Paper"
            isGK -> "GK Sectional Test"
            isRajasva -> "Rajasva Shabdavali Test"
            else -> "Full Mock Test"
        }

        return listOf(
            Test(
                id = "${seriesId}_${prefix.replace(" ", "_").lowercase()}_01",
                seriesId = seriesId,
                title = "$prefix - 01",
                subCategory = subCategory,
                durationMinutes = if (isGK || isRajasva) 30 else 120, // 200 questions typically takes 120-150 mins
                totalQuestions = sections.sumOf { it.questions.size }, // Dynamically read the 200 questions
                totalMarks = (sections.sumOf { it.questions.size } * 2).toFloat(), // 2 marks per Q
                isFree = true,
                sections = sections,
                previousAttempt = null
            )
        )
    }

    fun getTestById(testId: String): Test? {
        val allTests = getTestSeriesList().flatMap { series ->
            series.mockFolders.flatMap { folder ->
                getTestsForSubCategory(series.id, folder.title)
            }
        }
        return allTests.find { it.id == testId }
            ?: getTestsForSubCategory("nayab_tehsildar_2026", "Paper 1: Full Mock Tests").first()
    }

    fun getPreviousAttemptResult(testId: String): TestResult {
        val test = getTestById(testId)
        val testTitle = test?.title ?: "Full Mock Test - 01"
        val totalQ = test?.totalQuestions ?: 100
        val maxScore = test?.totalMarks ?: (totalQ * 2).toFloat()
        
        // Let's create a dynamic fallback that makes mathematical sense.
        // Assume user got exactly 65% correct, 15% incorrect, 20% unattempted.
        val correctCount = (totalQ * 0.65).toInt()
        val incorrectCount = (totalQ * 0.15).toInt()
        val unattemptedCount = totalQ - correctCount - incorrectCount
        
        val score = (correctCount * 2f) - (incorrectCount * 0.5f)
        val rawPercentage = if (maxScore > 0) (score / maxScore) * 100f else 0f
        
        val totalCandidates = 24964
        val computedRank = if (rawPercentage >= 99f) 1 else {
            val offset = (100f - rawPercentage) / 100f
            (offset * totalCandidates).toInt().coerceIn(1, totalCandidates)
        }
        val computedPercentile = if (totalCandidates > 1) {
            ((totalCandidates - computedRank).toFloat() / (totalCandidates - 1)) * 100f
        } else 100f
        
        val accuracy = if (correctCount + incorrectCount > 0) (correctCount.toFloat() / (correctCount + incorrectCount)) * 100f else 0f
        
        val avgScore = maxScore * 0.45f
        val bstScore = maxScore * 0.95f
        
        return TestResult(
            attemptId = "att_${testId}_01",
            testId = testId,
            testTitle = testTitle,
            score = score,
            totalMarks = maxScore,
            rank = computedRank,
            totalCandidates = totalCandidates,
            percentile = computedPercentile,
            accuracy = accuracy,
            correctCount = correctCount,
            incorrectCount = incorrectCount,
            unattemptedCount = unattemptedCount,
            cutoffMarks = "${(maxScore * 0.66).toInt()}-${(maxScore * 0.68).toInt()}",
            averageScore = avgScore,
            bestScore = bstScore,
            attemptDate = "Today",
            sectionBreakdowns = emptyList(), // we can omit this or generate dynamically if needed
            userAnswers = emptyMap()
        )
    }
}
