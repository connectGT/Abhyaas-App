package com.example.abhyaas.contract

object MockQuestionRepository {

    val sampleGIQuestions: List<Question> = listOf(
        Question(
            id = 1,
            sectionId = "sec_gi",
            questionNumber = 1,
            directionText = "A statement is followed by two courses of action numbered I and II. You are supposed to assume everything in the statement to be true and on the basis of the information given in the statement, decide which of the suggested courses of action logically follow(s).",
            directionTextHindi = "एक कथन के बाद दो कार्रवाइयां I और II दी गई हैं। आपको कथन में दी गई सभी बातों को सत्य मानना है और उसके आधार पर तय करना है कि कौन सी कार्रवाई तार्किक रूप से अनुसरण करती है।",
            statementText = "There was a spurt in criminal activities in the city during the recent festival season.\nCourse of Action:\nI. The police should immediately investigate the cause of this increase.\nII. In the future, the police should take adequate precautions to avoid the recurrence of such situations during festivals.\nIII. The known criminals should be arrested before any such season.",
            statementTextHindi = "हाल ही में त्योहारी सीजन के दौरान शहर में आपराधिक गतिविधियों में तेजी आई।\nकार्रवाई:\nI. पुलिस को तुरंत इस वृद्धि के कारणों की जांच करनी चाहिए।\nII. भविष्य में पुलिस को त्योहारों के दौरान ऐसी स्थिति से बचने के लिए पर्याप्त सावधानी बरतनी चाहिए।",
            options = listOf(
                Option(1, "I and III follow", "I और III अनुसरण करते हैं"),
                Option(2, "I and II follow", "I और II अनुसरण करते हैं"),
                Option(3, "II and III follow", "II और III अनुसरण करते हैं"),
                Option(4, "All follow", "सभी अनुसरण करते हैं")
            ),
            correctOptionIndex = 1,
            explanation = "Courses of action I and II are constructive and preventive administrative responses. Course of action III involves arresting without probable cause during festivals which violates civil liberties, thus only I and II logically follow.",
            explanationHindi = "कार्रवाई I और II सुधारात्मक और निवारक प्रशासनिक प्रतिक्रियाएँ हैं। अतः केवल I और II तार्किक रूप से मान्य हैं।",
            positiveMarks = 2.0f,
            negativeMarks = 0.5f,
            topic = "Statement & Course of Action",
            subject = "General Intelligence",
            percentGotRight = 64,
            averageTimeSeconds = 45
        ),
        Question(
            id = 2,
            sectionId = "sec_gi",
            questionNumber = 2,
            statementText = "If '+' means 'multiplication', '-' means 'division', 'x' means 'subtraction' and '÷' means 'addition', then what is the value of the following expression?\n26 + 342 - 19 x 73 ÷ 14",
            statementTextHindi = "यदि '+' का अर्थ 'गुणा', '-' का अर्थ 'भाग', 'x' का अर्थ 'घटाव' और '÷' का अर्थ 'जोड़' है, तो निम्नलिखित व्यंजक का मान क्या होगा?\n26 + 342 - 19 x 73 ÷ 14",
            options = listOf(
                Option(1, "481"),
                Option(2, "409"),
                Option(3, "524"),
                Option(4, "510")
            ),
            correctOptionIndex = 1,
            explanation = "Replacing signs according to BODMAS:\n= 26 × (342 ÷ 19) - 73 + 14\n= 26 × 18 - 73 + 14\n= 468 - 73 + 14 = 409.",
            explanationHindi = "चिह्नों को बदलने पर:\n= 26 × (342 ÷ 19) - 73 + 14 = 468 - 73 + 14 = 409.",
            positiveMarks = 2.0f,
            negativeMarks = 0.5f,
            topic = "Mathematical Operations",
            subject = "General Intelligence",
            percentGotRight = 72,
            averageTimeSeconds = 38
        ),
        Question(
            id = 3,
            sectionId = "sec_gi",
            questionNumber = 3,
            statementText = "If 'A' means 'x', 'B' means '+', 'C' means '-' and 'D' means '÷', then 66 A 3 D 11 B 43 C 48 D 12 = ?",
            statementTextHindi = "यदि 'A' का अर्थ 'x', 'B' का अर्थ '+', 'C' का अर्थ '-' और 'D' का अर्थ '÷' है, तो 66 A 3 D 11 B 43 C 48 D 12 = ?",
            options = listOf(
                Option(1, "57"),
                Option(2, "61"),
                Option(3, "49"),
                Option(4, "55")
            ),
            correctOptionIndex = 0,
            explanation = "66 × 3 ÷ 11 + 43 - 48 ÷ 12 = 6 × 3 + 43 - 4 = 18 + 43 - 4 = 57.",
            explanationHindi = "66 × 3 ÷ 11 + 43 - 48 ÷ 12 = 18 + 43 - 4 = 57.",
            positiveMarks = 2.0f,
            negativeMarks = 0.5f,
            topic = "Symbol Substitution",
            subject = "General Intelligence",
            percentGotRight = 81,
            averageTimeSeconds = 32
        ),
        Question(
            id = 4,
            sectionId = "sec_gi",
            questionNumber = 4,
            statementText = "How many triangles are there in a standard 4x4 symmetrical quadrilateral grid with diagonals intersecting at the center?",
            statementTextHindi = "विकर्णों के केंद्र पर प्रतिच्छेद करने वाले मानक 4x4 सममित ग्रिड में कितने त्रिभुज हैं?",
            options = listOf(
                Option(1, "12"),
                Option(2, "14"),
                Option(3, "16"),
                Option(4, "18")
            ),
            correctOptionIndex = 2,
            explanation = "There are 8 small single triangles, 4 medium triangles formed by pairs, and 4 large triangles divided by diagonals, yielding a total of 16 triangles.",
            explanationHindi = "8 छोटे, 4 मध्यम और 4 बड़े त्रिभुज मिलकर कुल 16 त्रिभुज बनाते हैं।",
            positiveMarks = 2.0f,
            negativeMarks = 0.5f,
            topic = "Counting Figures",
            subject = "General Intelligence",
            percentGotRight = 54,
            averageTimeSeconds = 50
        )
    )

    val sampleGAQuestions: List<Question> = listOf(
        Question(
            id = 26,
            sectionId = "sec_ga",
            questionNumber = 26,
            statementText = "Under the Madhya Pradesh Land Revenue Code, 1959 (भू-राजस्व संहिता), which revenue officer is the primary authority responsible for maintaining the Khasra and Field Map (भू-नक्शा)?",
            statementTextHindi = "मध्य प्रदेश भू-राजस्व संहिता, 1959 के तहत खसरा और भू-नक्शा संधारित करने वाला प्राथमिक राजस्व अधिकारी कौन सा है?",
            options = listOf(
                Option(1, "Tehsildar", "तहसीलदार"),
                Option(2, "Patwari", "पटवारी"),
                Option(3, "Revenue Inspector (RI)", "राजस्व निरीक्षक"),
                Option(4, "Sub-Divisional Officer (SDO)", "अनुविभागीय अधिकारी")
            ),
            correctOptionIndex = 1,
            explanation = "Under Section 114 of the MP Land Revenue Code, the Patwari is the official designated to prepare and maintain the annual field map and Khasra record of rights.",
            explanationHindi = "मध्य प्रदेश भू-राजस्व संहिता की धारा 114 के अनुसार पटवारी खसरा और नक्शा संधारित करता है।",
            positiveMarks = 2.0f,
            negativeMarks = 0.5f,
            topic = "MP Land Revenue Code",
            subject = "General Awareness",
            percentGotRight = 78,
            averageTimeSeconds = 25
        ),
        Question(
            id = 27,
            sectionId = "sec_ga",
            questionNumber = 27,
            statementText = "Which Article of the Constitution of India provides for the establishment and constitution of the Finance Commission?",
            statementTextHindi = "भारत के संविधान का कौन सा अनुच्छेद वित्त आयोग की स्थापना और गठन का प्रावधान करता है?",
            options = listOf(
                Option(1, "Article 280", "अनुच्छेद 280"),
                Option(2, "Article 324", "अनुच्छेद 324"),
                Option(3, "Article 312", "अनुच्छेद 312"),
                Option(4, "Article 110", "अनुच्छेद 110")
            ),
            correctOptionIndex = 0,
            explanation = "Article 280 of the Constitution lays down that the President shall, within two years from the commencement of this Constitution and thereafter at the expiration of every fifth year, constitute a Finance Commission.",
            explanationHindi = "भारतीय संविधान का अनुच्छेद 280 राष्ट्रपति द्वारा प्रत्येक 5 वर्ष में वित्त आयोग के गठन का प्रावधान करता है।",
            positiveMarks = 2.0f,
            negativeMarks = 0.5f,
            topic = "Indian Polity",
            subject = "General Awareness",
            percentGotRight = 89,
            averageTimeSeconds = 20
        )
    )

    fun generate100QuestionsExam(): List<TestSection> {
        val sectionsConfig = listOf(
            Triple("sec_gi", "PART - A", "General Intelligence"),
            Triple("sec_ga", "PART - B", "General Awareness"),
            Triple("sec_qa", "PART - C", "Quantitative Aptitude"),
            Triple("sec_en", "PART - D", "English Language")
        )

        var questionIdCounter = 1
        return sectionsConfig.map { (secId, partName, title) ->
            val questions = (1..25).map { qIndexInSec ->
                val qNumber = (sectionsConfig.indexOfFirst { it.first == secId } * 25) + qIndexInSec
                val correctIndex = (qNumber % 4)
                Question(
                    id = questionIdCounter++,
                    sectionId = secId,
                    questionNumber = qNumber,
                    statementText = "Question $qNumber: Test question in $title on topic #${(qNumber % 5) + 1}",
                    statementTextHindi = "प्रश्न $qNumber: $title में विषय #${(qNumber % 5) + 1} पर परीक्षण प्रश्न",
                    options = listOf(
                        Option(1, "Option A for Q$qNumber", "विकल्प क"),
                        Option(2, "Option B for Q$qNumber", "विकल्प ख"),
                        Option(3, "Option C for Q$qNumber", "विकल्प ग"),
                        Option(4, "Option D for Q$qNumber", "विकल्प घ")
                    ),
                    correctOptionIndex = correctIndex,
                    explanation = "Detailed solution for question $qNumber in $title. Correct option is ${correctIndex + 1}.",
                    explanationHindi = "प्रश्न $qNumber का विस्तृत हल। सही विकल्प ${correctIndex + 1} है।",
                    positiveMarks = 2.0f,
                    negativeMarks = 0.5f,
                    topic = "Topic ${(qNumber % 5) + 1}",
                    subject = title,
                    percentGotRight = 60 + (qNumber % 30),
                    averageTimeSeconds = 30 + (qNumber % 25)
                )
            }
            TestSection(
                id = secId,
                partName = partName,
                title = title,
                titleHindi = when (secId) {
                    "sec_gi" -> "सामान्य बुद्धिमत्ता"
                    "sec_ga" -> "सामान्य जागरूकता"
                    "sec_qa" -> "मात्रात्मक योग्यता"
                    else -> "अंग्रेजी भाषा"
                },
                questions = questions
            )
        }
    }
}

object MockExamRepository {

    fun getTestSeriesList(): List<TestSeries> {
        return listOf(
            TestSeries(
                id = "ssc_selection_post_14",
                title = "SSC Selection Post (Phase 14) 2026 Mock Test Series",
                subtitle = "600+ Total Tests, 30 Full Tests, 90+ PYQs, Vacancies 3000+",
                categoryId = "ssc",
                totalTests = 610,
                fullTestsCount = 30,
                pyqCount = 240,
                attemptedCount = 1,
                vacancies = "3000+",
                examDates = "Sep - 2026",
                isEnrolled = true
            ),
            TestSeries(
                id = "mpesb_nayab_tehsildar_2026",
                title = "MPESB Nayab Tehsildar & Revenue Inspector 2026",
                subtitle = "150+ Tests, MP GK, Land Revenue Code special tests",
                categoryId = "mp_exams",
                totalTests = 150,
                fullTestsCount = 20,
                pyqCount = 45,
                attemptedCount = 0,
                vacancies = "450+",
                examDates = "Nov - 2026",
                isEnrolled = false
            )
        )
    }

    fun getPracticeTestDay02(): Test {
        val sections = MockQuestionRepository.generate100QuestionsExam()
        return Test(
            id = "test_ssc_p14_day02",
            seriesId = "ssc_selection_post_14",
            title = "SSC Selection Post (Phase 14): Practice Test Day - 02",
            subCategory = "Exam Day Special",
            durationMinutes = 60,
            totalQuestions = 100,
            totalMarks = 200.0f,
            isFree = true,
            supportedLanguages = listOf("English", "Hindi"),
            sections = sections,
            instructions = defaultTestInstructions,
            cutoffGeneral = "132-135",
            averageScore = 67.75f,
            bestScore = 200.0f
        )
    }

    fun getPracticeTestDay01Result(): TestResult {
        return TestResult(
            attemptId = "attempt_day01_sample",
            testId = "test_ssc_p14_day01",
            testTitle = "Practice Test Day - 01",
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
            sectionBreakdowns = emptyList(),
            attemptDate = "28 Sep 2026"
        )
    }
}

object MockUserRepository {
    fun getUserProfile(): UserProfile {
        return UserProfile(
            fullName = "Aspirant",
            email = "aspirant@abhyaas.edu",
            mobileNumber = "+91 98765 43210",
            dateOfBirth = "15/08/2000",
            category = "General",
            pinCode = "462001",
            education = "Graduation",
            averageScorePercent = 55,
            totalTestsAttempted = 7,
            totalStudyTimeHours = 2
        )
    }

    fun getPreparationTrend(): List<PreparationDataPoint> {
        return listOf(
            PreparationDataPoint("May 2", 15),
            PreparationDataPoint("Jun 12", 35),
            PreparationDataPoint("Jul 24", 50),
            PreparationDataPoint("Aug 18", 42),
            PreparationDataPoint("Sep 27", 85)
        )
    }
}

object MockUpdatesRepository {
    fun getAllUpdates(): List<ExamUpdateItem> {
        return listOf(
            ExamUpdateItem(
                id = "up_1",
                title = "SSC CGL 2026 Notification Released",
                description = "Staff Selection Commission has announced tentative vacancies for CGL 2026.",
                date = "28 Sep 2026",
                category = UpdateCategory.NOTIFICATIONS,
                isPinned = true,
                pdfSize = "2.4 MB",
                actionText = "Download PDF"
            ),
            ExamUpdateItem(
                id = "up_2",
                title = "SSC CGL 2026 Important Dates",
                description = "Exam calendar and key application dates published.",
                date = "26 Sep 2026",
                category = UpdateCategory.EXAM_DATES,
                isPinned = false,
                pdfSize = "1.2 MB",
                actionText = "Download PDF"
            ),
            ExamUpdateItem(
                id = "up_3",
                title = "SSC CGL 2026 Exam Pattern",
                description = "Revised tier structure and computer knowledge module guidelines.",
                date = "25 Sep 2026",
                category = UpdateCategory.SYLLABUS,
                isPinned = false,
                pdfSize = "950 KB",
                actionText = "Download PDF"
            ),
            ExamUpdateItem(
                id = "up_4",
                title = "SSC CGL 2026 Syllabus Detailed",
                description = "Detailed syllabus breakdown topic-wise for all 4 sections.",
                date = "24 Sep 2026",
                category = UpdateCategory.SYLLABUS,
                isPinned = false,
                pdfSize = "1.8 MB",
                actionText = "Download PDF"
            ),
            ExamUpdateItem(
                id = "up_5",
                title = "SSC CGL 2026 Admit Card (Soon)",
                description = "Admit cards for Phase 1 will be uploaded 7 days prior to exam.",
                date = "22 Sep 2026",
                category = UpdateCategory.ADMIT_CARD,
                isPinned = false,
                pdfSize = null,
                actionText = "Notify Me"
            ),
            ExamUpdateItem(
                id = "up_6",
                title = "SSC CGL 2025 Final Result Declared",
                description = "Check category-wise cutoff and merit list here.",
                date = "20 Sep 2026",
                category = UpdateCategory.RESULTS,
                isPinned = false,
                pdfSize = "4.2 MB",
                actionText = "View Details >"
            )
        )
    }
}

object MockLeaderboardRepository {
    fun getLeaderboard(): List<LeaderboardEntry> {
        return listOf(
            LeaderboardEntry(rank = 1, userName = "Raja", score = 200.0f, maxScore = 200.0f, accuracy = 100.0f, timeTaken = "42m 10s"),
            LeaderboardEntry(rank = 2, userName = "Hemant", score = 195.0f, maxScore = 200.0f, accuracy = 97.5f, timeTaken = "45m 30s"),
            LeaderboardEntry(rank = 3, userName = "Vivek", score = 193.5f, maxScore = 200.0f, accuracy = 96.8f, timeTaken = "49m 12s"),
            LeaderboardEntry(rank = 4, userName = "Ananya Sharma", score = 192.5f, maxScore = 200.0f, accuracy = 96.2f, timeTaken = "51m 00s"),
            LeaderboardEntry(rank = 5, userName = "Tanya", score = 191.0f, maxScore = 200.0f, accuracy = 95.5f, timeTaken = "52m 14s"),
            LeaderboardEntry(rank = 6, userName = "Gauravvv", score = 190.5f, maxScore = 200.0f, accuracy = 95.2f, timeTaken = "53m 40s"),
            LeaderboardEntry(rank = 7, userName = "Prashant Kumar Prajapat", score = 190.0f, maxScore = 200.0f, accuracy = 95.0f, timeTaken = "54m 02s"),
            LeaderboardEntry(rank = 8, userName = "Jithin Sarang J S", score = 186.0f, maxScore = 200.0f, accuracy = 93.0f, timeTaken = "55m 20s"),
            LeaderboardEntry(rank = 22789, userName = "Aspirant (You)", score = 0.0f, maxScore = 200.0f, accuracy = 0.0f, timeTaken = "00m 00s", isCurrentUser = true)
        )
    }
}
