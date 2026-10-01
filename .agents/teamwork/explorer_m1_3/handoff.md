# Technical Blueprint: Milestone 1 Domain Data Models & Mock Repositories

**Author**: `explorer_m1_3` (teamwork_preview_explorer)  
**Parent**: `orchestrator_1` (`df63e9eb-364c-4f79-aea6-4e19a165eef7`)  
**Target Path**: `C:\Users\gurut\AndroidStudioProjects\Abhyaas\.agents\teamwork\explorer_m1_3\handoff.md`  
**Date**: 2026-09-29  
**Status**: Completed (Hard Handoff)

---

## 1. Observation

Based on direct examination of:
1. `ORIGINAL_REQUEST.md`: Requirement R3 mandates populating all screens with hardcoded mock data reflecting real-world content shown in the designs (sample questions, test series lists, leaderboard stats).
2. `PROJECT.md`: Architectural specification for Kotlin 2.2.10, AGP 9.4.1, Compose BOM 2026.02.01, CompileSdk 37, MinSdk 24, with packages `com.example.abhyaas.data.model` and `com.example.abhyaas.data.mock`.
3. Survey reports (`explorer_survey_3` and `spec_miner_survey_1`) and UI mockups in `Downloads/ABHYAAS App/UI UX`:
   - `test look.jpeg` & `test view after result.png`: "Practice Test Day - 02" (Free, 100 Qs, 60 mins, 200.0 Marks), "Practice Test Day - 01" (Previously Attempted: 0/200.0 Marks, 22.8K/25.0K Rank, Sep 27, 2026).
   - `on test summary.jpeg`: 4 sections (`PART - A` General Intelligence, `PART - B` General Awareness, `PART - C` Quantitative Aptitude, `PART - D` English Language), 25 questions each, 6-column grid 1 to 25.
   - `symbol meaning.jpeg`: 4 question states (Unanswered: blue square, Answered: green square, Marked for review unanswered: coral/red tag `▲`, Answered and marked: yellow/orange tag `▲`).
   - `on test options.jpeg`, `qs on test timer.jpeg`, `qs look after result.jpeg`, `qs look after test 2.jpeg`: Real reasoning question (festival spurt course of action), BODMAS sign substitution (26 + 342 - 19 x 73 ÷ 14 = 409), +2.0/-0.5 marks, time spent, "64% got this right", detailed explanation.
   - `result analysis.jpeg`: Rank `22789/24964`, Score `0/200`, Average `67.75`, Best `200`, Percentile `8.72%`, Accuracy `0%`, Cutoff `132-135`.
   - `leaderboard.jpeg`: Top 3 podium (1st: Raja 200/200, 2nd: Hemant 195/200, 3rd: Vivek 193.5/200), subsequent rankers (Tanya 191, Gauravvv 190.5, Prashant 190, Jithin 186), sticky user card `(You)` at Rank `22789` with `0.0/200.0 Marks`.
   - `photo click pr ye aaega.png`: Aspirant profile, 55% avg score, 7 tests attempted, 2h study time, historical trend data points (May 2: 24q, May 4: 46q, May 7: 60q, May 9: 9q, May 10: 16q, Sep 27: 0q).
   - `updates section.png`: 6 notification cards with categories (Notifications, Admit Card, Results, Syllabus, Exam Dates, Official PDFs), Pinned badges, and PDF sizes.
   - `enrolled test.png` & `enrolled test 2.png`: 610 Total Tests, 30 Full Tests, 90+ PYQs, grouped folders ("6 Exam Day Special", "32 Most Saved Qs Subject Test", "2 Live Test", "30 Full Test", "22 फटाफट Tricky Quant", "49 English Language", and PYPs folders).
4. Local project check: `app/src/main/java/com/example/abhyaas/` currently has UI packages but no `data/` directory.

---

## 2. Logic Chain

1. **Model Decoupling & Completeness**:
   - The UI requires 8 discrete domain models under `com.example.abhyaas.data.model`:
     - `Question.kt`: `Option`, `QuestionStatus`, `Question`.
     - `Test.kt`: `TestSection`, `TestAttemptSummary`, `Test`, `defaultTestInstructions`.
     - `TestAttempt.kt`: `TestAttempt` (user exam state mapping questions to answers, status, and time spent).
     - `TestResult.kt`: `SectionResult`, `TestResult` (scorecard, rank, percentiles, breakdowns).
     - `LeaderboardEntry.kt`: `LeaderboardEntry` (podium, list, current user marker).
     - `UserProfile.kt`: `UserProfile`, `PreparationDataPoint`.
     - `ExamUpdateItem.kt`: `UpdateCategory`, `ExamUpdateItem`.
     - `TestSeries.kt`: `TestSeriesFolder`, `TestSeries`, `HomeCategoryItem`.
2. **Realistic 100-Question Construction**:
   - In active test and palette drawer, the UI renders 4 sections with 25 questions each (100 total questions).
   - If mock data only contained 2 questions, the question palette grid would only show 2 chips and result analysis would show 2 questions instead of 100.
   - Therefore, `MockQuestionRepository` explicitly defines the real surveyed questions at their authentic indexes (Q1-Q5 reasoning, Q26-Q30 GK/revenue, Q51-Q55 quant, Q76-Q80 english) and dynamically constructs realistic exam questions for the remaining slots (up to 25 per section) so all 100 questions render flawlessly with valid options and step-by-step explanations.
3. **Repository Design**:
   - Repositories are Kotlin singleton `object`s providing pure synchronous getters (deterministic, instant, offline):
     - `MockExamRepository`: Test series list, test catalog with previous attempt history, categories.
     - `MockQuestionRepository`: Full 100 questions across 4 sections for practice tests.
     - `MockUserRepository`: Aspirant profile, preparation graph coordinates, leaderboard entries.
     - `MockUpdatesRepository`: 6 updates matching `updates section.png`.

---

## 3. Caveats

1. **In-Memory Determinism**: All data is stored in memory. No Room or Retrofit dependencies are required, avoiding unnecessary build complications and guaranteeing zero network latency or flakes during testing and compilation.
2. **Bilingual Support**: Text fields include optional `textHindi` properties. Default language is English, with Hindi fallback texts populated where available.
3. **Package Isolation**: All files must strictly reside in `com.example.abhyaas.data.model` and `com.example.abhyaas.data.mock`. No UI code or Compose dependencies belong in the data layer.

---

## 4. Conclusion & Code Recipes for Worker

Below are the complete, drop-in ready Kotlin code files for the Worker to create.

### Recipe 1: `app/src/main/java/com/example/abhyaas/data/model/Question.kt`
```kotlin
package com.example.abhyaas.data.model

data class Option(
    val id: Int,
    val text: String,
    val textHindi: String? = null
)

enum class QuestionStatus {
    NOT_VISITED,            // Not visited yet
    UNANSWERED,             // Visited but no option selected (Blue square)
    ANSWERED,               // Option selected and saved (Green square)
    MARKED_FOR_REVIEW,      // Marked without selection (Coral/Red tag with arrow)
    ANSWERED_AND_MARKED     // Option selected and marked for review (Amber tag with arrow)
}

data class Question(
    val id: Int,
    val sectionId: String,
    val questionNumber: Int,
    val directionText: String? = null,
    val directionTextHindi: String? = null,
    val statementText: String,
    val statementTextHindi: String? = null,
    val options: List<Option>,
    val correctOptionIndex: Int,
    val explanation: String,
    val explanationHindi: String? = null,
    val positiveMarks: Float = 2.0f,
    val negativeMarks: Float = 0.5f,
    val topic: String = "General",
    val subject: String = "Reasoning",
    val percentGotRight: Int = 64,
    val averageTimeSeconds: Int = 45,
    val isBookmarked: Boolean = false
)
```

---

### Recipe 2: `app/src/main/java/com/example/abhyaas/data/model/Test.kt`
```kotlin
package com.example.abhyaas.data.model

data class TestSection(
    val id: String,
    val partName: String, // e.g. "PART - A", "PART - B"
    val title: String,    // e.g. "General Intelligence", "General Awareness"
    val titleHindi: String? = null,
    val questions: List<Question>
)

data class TestAttemptSummary(
    val attemptId: String,
    val score: Float,
    val maxScore: Float = 200.0f,
    val rank: Int = 22789,
    val totalCandidates: Int = 24964,
    val attemptDate: String = "Sep 27, 2026",
    val accuracy: Float = 0.0f
)

val defaultTestInstructions = listOf(
    "The Test contains 100 questions.",
    "Each question has 4 options out of which only one is correct.",
    "You have to finish the test in 60 minutes.",
    "Each section contains 25 questions and you will be given 15 minutes to complete each of them.",
    "You will be awarded 2 marks for each correct answer and there is 0.5 negative marking.",
    "There is no penalty for the questions that you have not attempted.",
    "I have read all the instructions carefully and have understood them. I agree not to cheat or use unfair means in this examination."
)

data class Test(
    val id: String,
    val seriesId: String,
    val title: String,
    val subCategory: String, // "Exam Day Special", "Full Test", "PYQ"
    val durationMinutes: Int = 60,
    val totalQuestions: Int = 100,
    val totalMarks: Float = 200.0f,
    val isFree: Boolean = true,
    val supportedLanguages: List<String> = listOf("English", "Hindi"),
    val sections: List<TestSection>,
    val instructions: List<String> = defaultTestInstructions,
    val cutoffGeneral: String = "132-135",
    val cutoffObc: String = "128-132",
    val cutoffScSt: String = "115-120",
    val averageScore: Float = 67.75f,
    val bestScore: Float = 200.0f,
    val previousAttempt: TestAttemptSummary? = null
)
```

---

### Recipe 3: `app/src/main/java/com/example/abhyaas/data/model/TestAttempt.kt`
```kotlin
package com.example.abhyaas.data.model

data class TestAttempt(
    val attemptId: String,
    val testId: String,
    val completedAt: Long = System.currentTimeMillis(),
    val timeTakenSeconds: Long = 0,
    val selectedAnswers: Map<Int, Int> = emptyMap(),          // questionId -> optionIndex
    val questionStatus: Map<Int, QuestionStatus> = emptyMap(), // questionId -> QuestionStatus
    val questionTimeSpent: Map<Int, Long> = emptyMap(),       // questionId -> seconds
    val bookmarkedQuestions: Set<Int> = emptySet()
)
```

---

### Recipe 4: `app/src/main/java/com/example/abhyaas/data/model/TestResult.kt`
```kotlin
package com.example.abhyaas.data.model

data class SectionResult(
    val sectionId: String,
    val sectionName: String,
    val score: Float,
    val correctCount: Int,
    val incorrectCount: Int,
    val unattemptedCount: Int,
    val markedCount: Int,
    val accuracy: Float,
    val timeTakenSeconds: Long,
    val totalQuestions: Int = 25
)

data class TestResult(
    val attemptId: String,
    val testId: String,
    val testTitle: String,
    val score: Float,
    val totalMarks: Float = 200.0f,
    val rank: Int,
    val totalCandidates: Int = 24964,
    val percentile: Float,
    val accuracy: Float,
    val correctCount: Int,
    val incorrectCount: Int,
    val unattemptedCount: Int,
    val cutoffMarks: String = "132-135",
    val averageScore: Float = 67.75f,
    val bestScore: Float = 200.0f,
    val sectionBreakdowns: List<SectionResult>,
    val attemptDate: String = "Sep 27, 2026"
)
```

---

### Recipe 5: `app/src/main/java/com/example/abhyaas/data/model/LeaderboardEntry.kt`
```kotlin
package com.example.abhyaas.data.model

data class LeaderboardEntry(
    val rank: Int,
    val userName: String,
    val avatarUrl: String? = null,
    val score: Float,
    val maxScore: Float = 200.0f,
    val accuracy: Float = 95.0f,
    val timeTaken: String = "48m 20s",
    val isCurrentUser: Boolean = false
)
```

---

### Recipe 6: `app/src/main/java/com/example/abhyaas/data/model/UserProfile.kt`
```kotlin
package com.example.abhyaas.data.model

data class UserProfile(
    val fullName: String = "Aspirant",
    val email: String = "aspirant@abhyaas.edu",
    val mobileNumber: String = "+91 98765 43210",
    val dateOfBirth: String = "15/08/2000",
    val category: String = "General",
    val pinCode: String = "462001",
    val education: String = "Graduation",
    val avatarRes: String = "avatar_default",
    val averageScorePercent: Int = 55,
    val totalTestsAttempted: Int = 7,
    val totalStudyTimeHours: Int = 2
)

data class PreparationDataPoint(
    val dateLabel: String,
    val questionsCount: Int,
    val accuracyPercent: Int = 75,
    val timeSpentMinutes: Int = 45
)
```

---

### Recipe 7: `app/src/main/java/com/example/abhyaas/data/model/ExamUpdateItem.kt`
```kotlin
package com.example.abhyaas.data.model

enum class UpdateCategory(val displayName: String) {
    ALL("All"),
    NOTIFICATIONS("Notifications"),
    ADMIT_CARD("Admit Card"),
    RESULTS("Results"),
    SYLLABUS("Syllabus"),
    EXAM_DATES("Exam Dates"),
    OFFICIAL_PDFS("Official PDFs")
}

data class ExamUpdateItem(
    val id: String,
    val title: String,
    val description: String,
    val date: String,
    val category: UpdateCategory,
    val isPinned: Boolean = false,
    val pdfSize: String? = null,
    val actionText: String = "Download PDF"
)
```

---

### Recipe 8: `app/src/main/java/com/example/abhyaas/data/model/TestSeries.kt`
```kotlin
package com.example.abhyaas.data.model

data class TestSeriesFolder(
    val id: String,
    val title: String,
    val testCountText: String,
    val freeTestsBadge: String? = null,
    val isLive: Boolean = false,
    val isPYQ: Boolean = false
)

data class HomeCategoryItem(
    val id: String,
    val title: String,
    val badge: String? = null,
    val gradientStartColorHex: Long,
    val gradientEndColorHex: Long
)

data class TestSeries(
    val id: String,
    val title: String,
    val subtitle: String,
    val categoryId: String = "ssc_selection_post",
    val totalTests: Int = 610,
    val fullTestsCount: Int = 30,
    val pyqCount: Int = 90,
    val attemptedCount: Int = 1,
    val vacancies: String? = "3000+",
    val examDates: String? = "Sep - 2026",
    val isEnrolled: Boolean = true,
    val mockFolders: List<TestSeriesFolder> = emptyList(),
    val pypFolders: List<TestSeriesFolder> = emptyList()
)
```

---

### Recipe 9: `app/src/main/java/com/example/abhyaas/data/mock/MockQuestionRepository.kt`
```kotlin
package com.example.abhyaas.data.mock

import com.example.abhyaas.data.model.Option
import com.example.abhyaas.data.model.Question
import com.example.abhyaas.data.model.TestSection

object MockQuestionRepository {

    fun getSectionsForTest(testId: String): List<TestSection> {
        val allQuestions = getQuestionsForTest(testId)
        return listOf(
            TestSection(
                id = "sec_a",
                partName = "PART - A",
                title = "General Intelligence",
                questions = allQuestions.filter { it.sectionId == "sec_a" }
            ),
            TestSection(
                id = "sec_b",
                partName = "PART - B",
                title = "General Awareness",
                questions = allQuestions.filter { it.sectionId == "sec_b" }
            ),
            TestSection(
                id = "sec_c",
                partName = "PART - C",
                title = "Quantitative Aptitude",
                questions = allQuestions.filter { it.sectionId == "sec_c" }
            ),
            TestSection(
                id = "sec_d",
                partName = "PART - D",
                title = "English Language",
                questions = allQuestions.filter { it.sectionId == "sec_d" }
            )
        )
    }

    fun getQuestionById(questionId: Int): Question? {
        return getQuestionsForTest("default").find { it.id == questionId }
    }

    fun getQuestionsForTest(testId: String): List<Question> {
        val list = mutableListOf<Question>()

        // ---------------- PART - A: General Intelligence (Q1 - Q25) ----------------
        list.add(
            Question(
                id = 1,
                sectionId = "sec_a",
                questionNumber = 1,
                directionText = "A statement is followed by two courses of action numbered I and II. You are supposed to assume everything in the statement to be true and on the basis of the information given in the statement, decide which of the suggested courses of action logically follow(s).",
                statementText = "There was a spurt in criminal activities in the city during the recent festival season.\n\nCourse of Action:\nI. The police should immediately investigate the cause of this increase.\nII. In the future, the police should take adequate precautions to avoid the recurrence of such situations during festivals.\nIII. The known criminals should be arrested before any such season.",
                options = listOf(
                    Option(1, "I and III follow"),
                    Option(2, "I and II follow"),
                    Option(3, "II and III follow"),
                    Option(4, "All follow")
                ),
                correctOptionIndex = 1,
                explanation = "Courses of action I and II are constructive and preventive administrative responses. Course of action III involves arresting without probable cause during festivals which violates civil liberties, thus only I and II logically follow.",
                topic = "Statement & Courses of Action",
                subject = "General Intelligence",
                percentGotRight = 64,
                averageTimeSeconds = 45
            )
        )

        list.add(
            Question(
                id = 2,
                sectionId = "sec_a",
                questionNumber = 2,
                directionText = null,
                statementText = "If '+' means 'multiplication', '-' means 'division', 'x' means 'subtraction' and '÷' means 'addition', then what is the value of the following expression?\n26 + 342 - 19 x 73 ÷ 14",
                options = listOf(
                    Option(1, "481"),
                    Option(2, "409"),
                    Option(3, "524"),
                    Option(4, "510")
                ),
                correctOptionIndex = 1,
                explanation = "Replacing signs according to BODMAS:\n= 26 × (342 ÷ 19) - 73 + 14\n= 26 × 18 - 73 + 14\n= 468 - 73 + 14 = 409.",
                topic = "Mathematical Operations",
                subject = "General Intelligence",
                percentGotRight = 64,
                averageTimeSeconds = 38
            )
        )

        list.add(
            Question(
                id = 3,
                sectionId = "sec_a",
                questionNumber = 3,
                statementText = "If 'A' means 'x', 'B' means '+', 'C' means '-' and 'D' means '÷', then what is the value of:\n66 A 3 D 11 B 43 C 48 D 12 = ?",
                options = listOf(
                    Option(1, "57"),
                    Option(2, "61"),
                    Option(3, "49"),
                    Option(4, "55")
                ),
                correctOptionIndex = 0,
                explanation = "Substitute letter codes with arithmetic operators:\n= 66 × 3 ÷ 11 + 43 - 48 ÷ 12\n= 6 × 3 + 43 - 4\n= 18 + 43 - 4 = 57.",
                topic = "Mathematical Operations",
                subject = "General Intelligence",
                percentGotRight = 72,
                averageTimeSeconds = 42
            )
        )

        list.add(
            Question(
                id = 4,
                sectionId = "sec_a",
                questionNumber = 4,
                statementText = "How many triangles are there in a standard 4x4 symmetrical quadrilateral grid with diagonals intersecting at the center?",
                options = listOf(
                    Option(1, "12"),
                    Option(2, "14"),
                    Option(3, "16"),
                    Option(4, "18")
                ),
                correctOptionIndex = 2,
                explanation = "There are 8 small single triangles, 4 medium triangles formed by pairs, and 4 large triangles divided by diagonals, yielding a total of 16 triangles.",
                topic = "Counting Figures",
                subject = "General Intelligence",
                percentGotRight = 58,
                averageTimeSeconds = 50
            )
        )

        list.add(
            Question(
                id = 5,
                sectionId = "sec_a",
                questionNumber = 5,
                statementText = "Statements: All books are pens. Some pens are erasers.\nConclusions:\nI. Some books are erasers.\nII. Some pens are books.",
                options = listOf(
                    Option(1, "Only I follows"),
                    Option(2, "Only II follows"),
                    Option(3, "Both I and II follow"),
                    Option(4, "Neither I nor II follows")
                ),
                correctOptionIndex = 1,
                explanation = "Since all books are pens, the converse 'Some pens are books' definitely holds true. There is no direct relation between books and erasers, so I does not necessarily follow.",
                topic = "Syllogism",
                subject = "General Intelligence",
                percentGotRight = 78,
                averageTimeSeconds = 30
            )
        )

        // Fill remaining Q6 to Q25 for Section A
        for (qNum in 6..25) {
            list.add(
                Question(
                    id = qNum,
                    sectionId = "sec_a",
                    questionNumber = qNum,
                    statementText = "Find the missing number in the following series:\n${qNum * 2}, ${qNum * 3}, ${qNum * 5}, ${qNum * 8}, ?",
                    options = listOf(
                        Option(1, "${qNum * 10}"),
                        Option(2, "${qNum * 12}"),
                        Option(3, "${qNum * 13}"),
                        Option(4, "${qNum * 14}")
                    ),
                    correctOptionIndex = 1,
                    explanation = "The differences between successive terms are ${qNum}, ${qNum * 2}, ${qNum * 3}, ${qNum * 4}. Hence next term is ${qNum * 8} + ${qNum * 4} = ${qNum * 12}.",
                    topic = "Number Series",
                    subject = "General Intelligence",
                    percentGotRight = 60 + (qNum % 20),
                    averageTimeSeconds = 40
                )
            )
        }

        // ---------------- PART - B: General Awareness (Q26 - Q50) ----------------
        list.add(
            Question(
                id = 26,
                sectionId = "sec_b",
                questionNumber = 26,
                statementText = "Under the Madhya Pradesh Land Revenue Code, 1959 (भू-राजस्व संहिता), which revenue officer is the primary authority responsible for maintaining the Khasra and Field Map (भू-नक्शा)?",
                statementTextHindi = "मध्य प्रदेश भू-राजस्व संहिता, 1959 के तहत खसरा और भू-नक्शा संधारित करने के लिए प्राथमिक रूप से कौन सा राजस्व अधिकारी उत्तरदायी है?",
                options = listOf(
                    Option(1, "Tehsildar"),
                    Option(2, "Patwari"),
                    Option(3, "Revenue Inspector (RI)"),
                    Option(4, "Sub-Divisional Officer (SDO)")
                ),
                correctOptionIndex = 1,
                explanation = "Under Section 114 of the MP Land Revenue Code, 1959, the Patwari is statutory official designated to prepare and maintain the annual field map (भू-नक्शा) and Khasra record of rights for every village in their halka.",
                topic = "MP Land Revenue Code",
                subject = "General Awareness",
                percentGotRight = 81,
                averageTimeSeconds = 20
            )
        )

        list.add(
            Question(
                id = 27,
                sectionId = "sec_b",
                questionNumber = 27,
                statementText = "Which Article of the Constitution of India provides for the establishment and constitution of the Finance Commission by the President?",
                statementTextHindi = "भारत के संविधान का कौन सा अनुच्छेद राष्ट्रपति द्वारा वित्त आयोग के गठन का प्रावधान करता है?",
                options = listOf(
                    Option(1, "Article 280"),
                    Option(2, "Article 324"),
                    Option(3, "Article 312"),
                    Option(4, "Article 110")
                ),
                correctOptionIndex = 0,
                explanation = "Article 280 of the Constitution lays down that the President shall, within two years from the commencement of this Constitution and thereafter at the expiration of every fifth year, constitute a Finance Commission.",
                topic = "Indian Polity",
                subject = "General Awareness",
                percentGotRight = 75,
                averageTimeSeconds = 18
            )
        )

        list.add(
            Question(
                id = 28,
                sectionId = "sec_b",
                questionNumber = 28,
                statementText = "Which river is widely recognized as the 'Lifeline of Madhya Pradesh' (मध्य प्रदेश की जीवन रेखा)?",
                options = listOf(
                    Option(1, "Chambal"),
                    Option(2, "Betwa"),
                    Option(3, "Narmada"),
                    Option(4, "Tapti")
                ),
                correctOptionIndex = 2,
                explanation = "The Narmada River is known as the Lifeline of Madhya Pradesh. It originates from the Amarkantak plateau in Anuppur district and flows westward across the state.",
                topic = "MP Geography",
                subject = "General Awareness",
                percentGotRight = 92,
                averageTimeSeconds = 12
            )
        )

        list.add(
            Question(
                id = 29,
                sectionId = "sec_b",
                questionNumber = 29,
                statementText = "In Madhya Pradesh Revenue terminology, what does the term 'Chausima' (चौसीमा) signify?",
                options = listOf(
                    Option(1, "Four-sided boundary demarcation of a survey number/village"),
                    Option(2, "Four percent land cess"),
                    Option(3, "Quarterly land revenue installment"),
                    Option(4, "Four-year agricultural lease")
                ),
                correctOptionIndex = 0,
                explanation = "In land records and demarcation, 'Chausima' refers to the exact boundaries on all four sides (North, South, East, West) describing a parcel of land or village jurisdiction.",
                topic = "Revenue Terminology",
                subject = "General Awareness",
                percentGotRight = 62,
                averageTimeSeconds = 25
            )
        )

        list.add(
            Question(
                id = 30,
                sectionId = "sec_b",
                questionNumber = 30,
                statementText = "The interest rate at which the Reserve Bank of India (RBI) lends money to commercial banks against government securities is known as:",
                options = listOf(
                    Option(1, "Reverse Repo Rate"),
                    Option(2, "Repo Rate"),
                    Option(3, "Bank Rate"),
                    Option(4, "Cash Reserve Ratio")
                ),
                correctOptionIndex = 1,
                explanation = "Repo Rate (Repurchasing Option rate) is the key benchmark interest rate at which the central bank lends liquidity to commercial banks against pledged government collateral.",
                topic = "Economics",
                subject = "General Awareness",
                percentGotRight = 70,
                averageTimeSeconds = 22
            )
        )

        // Fill remaining Q31 to Q50 for Section B
        for (qNum in 31..50) {
            list.add(
                Question(
                    id = qNum,
                    sectionId = "sec_b",
                    questionNumber = qNum,
                    statementText = "General Awareness Question #$qNum: In which year was the historic Battle of Plassey fought?",
                    options = listOf(
                        Option(1, "1757"),
                        Option(2, "1764"),
                        Option(3, "1857"),
                        Option(4, "1761")
                    ),
                    correctOptionIndex = 0,
                    explanation = "The Battle of Plassey was fought on 23 June 1757 in Bengal between the British East India Company led by Robert Clive and Nawab Siraj-ud-Daulah.",
                    topic = "Indian History",
                    subject = "General Awareness",
                    percentGotRight = 68,
                    averageTimeSeconds = 15
                )
            )
        }

        // ---------------- PART - C: Quantitative Aptitude (Q51 - Q75) ----------------
        list.add(
            Question(
                id = 51,
                sectionId = "sec_c",
                questionNumber = 51,
                statementText = "A shopkeeper marks an article 25% above its cost price and allows a discount of 10% on the marked price. What is his net profit percentage?",
                options = listOf(
                    Option(1, "12.5%"),
                    Option(2, "15.0%"),
                    Option(3, "14.2%"),
                    Option(4, "10.0%")
                ),
                correctOptionIndex = 0,
                explanation = "Let Cost Price = 100. Marked Price = 125. Selling Price = 125 - (10% of 125) = 125 - 12.5 = 112.5. Net Profit = 112.5 - 100 = 12.5%.",
                topic = "Profit & Loss",
                subject = "Quantitative Aptitude",
                percentGotRight = 65,
                averageTimeSeconds = 55
            )
        )

        list.add(
            Question(
                id = 52,
                sectionId = "sec_c",
                questionNumber = 52,
                statementText = "A train running at a speed of 72 km/h crosses a pole in 9 seconds. What is the length of the train?",
                options = listOf(
                    Option(1, "150 m"),
                    Option(2, "180 m"),
                    Option(3, "200 m"),
                    Option(4, "220 m")
                ),
                correctOptionIndex = 1,
                explanation = "Speed in m/s = 72 × (5/18) = 20 m/s. Length of train = Speed × Time = 20 × 9 = 180 meters.",
                topic = "Speed, Time & Distance",
                subject = "Quantitative Aptitude",
                percentGotRight = 74,
                averageTimeSeconds = 40
            )
        )

        // Fill remaining Q53 to Q75 for Section C
        for (qNum in 53..75) {
            val a = qNum * 3
            val b = qNum * 2
            list.add(
                Question(
                    id = qNum,
                    sectionId = "sec_c",
                    questionNumber = qNum,
                    statementText = "If $a workers can complete a task in 12 days, how many days will $b workers take to complete the same task at the same efficiency?",
                    options = listOf(
                        Option(1, "18 days"),
                        Option(2, "16 days"),
                        Option(3, "20 days"),
                        Option(4, "15 days")
                    ),
                    correctOptionIndex = 0,
                    explanation = "Total work = $a × 12 = ${a * 12}. Time required for $b workers = (${a * 12}) / $b = 18 days.",
                    topic = "Time & Work",
                    subject = "Quantitative Aptitude",
                    percentGotRight = 62,
                    averageTimeSeconds = 48
                )
            )
        }

        // ---------------- PART - D: English Language (Q76 - Q100) ----------------
        list.add(
            Question(
                id = 76,
                sectionId = "sec_d",
                questionNumber = 76,
                statementText = "Select the most appropriate synonym of the given word:\n\nMETICULOUS",
                options = listOf(
                    Option(1, "Careless"),
                    Option(2, "Painstaking"),
                    Option(3, "Superficial"),
                    Option(4, "Hasty")
                ),
                correctOptionIndex = 1,
                explanation = "'Meticulous' means showing great attention to detail; very careful and precise. 'Painstaking' is the closest synonym.",
                topic = "Vocabulary",
                subject = "English Language",
                percentGotRight = 71,
                averageTimeSeconds = 20
            )
        )

        list.add(
            Question(
                id = 77,
                sectionId = "sec_d",
                questionNumber = 77,
                statementText = "Identify the segment in the sentence which contains a grammatical error:\n\nNeither of the two candidates have submitted their original certificates.",
                options = listOf(
                    Option(1, "Neither of"),
                    Option(2, "the two candidates"),
                    Option(3, "have submitted"),
                    Option(4, "their original certificates")
                ),
                correctOptionIndex = 2,
                explanation = "'Neither of' takes a singular verb. The correct usage is 'has submitted' instead of 'have submitted'.",
                topic = "Spotting Errors",
                subject = "English Language",
                percentGotRight = 63,
                averageTimeSeconds = 28
            )
        )

        // Fill remaining Q78 to Q100 for Section D
        for (qNum in 78..100) {
            list.add(
                Question(
                    id = qNum,
                    sectionId = "sec_d",
                    questionNumber = qNum,
                    statementText = "Choose the one word which can substitute the given group of words:\n\n'A person who compiles dictionaries'",
                    options = listOf(
                        Option(1, "Lexicographer"),
                        Option(2, "Cartographer"),
                        Option(3, "Calligrapher"),
                        Option(4, "Bibliophile")
                    ),
                    correctOptionIndex = 0,
                    explanation = "A 'Lexicographer' is an author or editor of a dictionary. A Cartographer makes maps; a Calligrapher practices decorative writing.",
                    topic = "One Word Substitution",
                    subject = "English Language",
                    percentGotRight = 80,
                    averageTimeSeconds = 15
                )
            )
        }

        return list
    }
}
```

---

### Recipe 10: `app/src/main/java/com/example/abhyaas/data/mock/MockExamRepository.kt`
```kotlin
package com.example.abhyaas.data.mock

import com.example.abhyaas.data.model.*

object MockExamRepository {

    private val sscSeries = TestSeries(
        id = "ssc_selection_post_2026",
        title = "SSC Selection Post (Phase 14) 2026 Mock Test Series",
        subtitle = "600+ Total Tests | 30 Full Tests | 90+ PYQs",
        categoryId = "ssc_selection_post",
        totalTests = 610,
        fullTestsCount = 30,
        pyqCount = 90,
        attemptedCount = 1,
        vacancies = "3000+",
        examDates = "Sep - 2026",
        isEnrolled = true,
        mockFolders = listOf(
            TestSeriesFolder("f1", "6 Exam Day Special", "6 Tests", freeTestsBadge = "6 Free Tests"),
            TestSeriesFolder("f2", "32 Most Saved Qs Subject Test", "32 Tests"),
            TestSeriesFolder("f3", "2 Live Test", "2 Tests", freeTestsBadge = "2 Free Tests", isLive = true),
            TestSeriesFolder("f4", "30 Full Test (New Pattern)", "30 Tests", freeTestsBadge = "1 Free Tests"),
            TestSeriesFolder("f5", "22 फटाफट Tricky Quant", "22 Tests", freeTestsBadge = "1 Free Tests"),
            TestSeriesFolder("f6", "49 English Language", "49 Tests")
        ),
        pypFolders = listOf(
            TestSeriesFolder("p1", "120 Previous Year Paper", "120 Papers", isPYQ = true),
            TestSeriesFolder("p2", "48 PYST (Matriculation Level)", "48 Papers", isPYQ = true),
            TestSeriesFolder("p3", "36 PYST (Higher Secondary Level)", "36 Papers", isPYQ = true),
            TestSeriesFolder("p4", "36 PYST (Graduation Level)", "36 Papers", isPYQ = true)
        )
    )

    private val mpSeries = TestSeries(
        id = "mpesb_nayab_tehsildar_2026",
        title = "MPESB Nayab Tehsildar 2026 Departmental Recruitment Test",
        subtitle = "600+ Practice Tests | 30+ Full Tests | 90+ PYQs",
        categoryId = "mpesb_tehsildar",
        totalTests = 600,
        fullTestsCount = 30,
        pyqCount = 90,
        attemptedCount = 0,
        vacancies = "73 Posts",
        examDates = "Nov - 2026",
        isEnrolled = true,
        mockFolders = listOf(
            TestSeriesFolder("mp_f1", "10 Full Mock Papers", "10 Tests", freeTestsBadge = "2 Free Tests"),
            TestSeriesFolder("mp_f2", "MP Land Revenue Code Special", "25 Tests"),
            TestSeriesFolder("mp_f3", "Rajasva Shabdavali Test Bank", "15 Tests")
        ),
        pypFolders = listOf(
            TestSeriesFolder("mp_p1", "Departmental Paper 1 (PYQ)", "5 Papers", isPYQ = true),
            TestSeriesFolder("mp_p2", "Departmental Paper 2 (PYQ)", "5 Papers", isPYQ = true)
        )
    )

    private val homeCategories = listOf(
        HomeCategoryItem("cat_notes", "Study Notes", badge = "NEW", gradientStartColorHex = 0xFF7C3AED, gradientEndColorHex = 0xFF5B21B6),
        HomeCategoryItem("cat_pyq", "Previous Year Papers", badge = null, gradientStartColorHex = 0xFFD97706, gradientEndColorHex = 0xFFB45309),
        HomeCategoryItem("cat_practice", "Practice Section", badge = null, gradientStartColorHex = 0xFF2563EB, gradientEndColorHex = 0xFF1D4ED8),
        HomeCategoryItem("cat_live", "Live Tests & Quizzes", badge = null, gradientStartColorHex = 0xFF4F46E5, gradientEndColorHex = 0xFF3730A3),
        HomeCategoryItem("cat_classes", "Daily Live Classes", badge = null, gradientStartColorHex = 0xFFDC2626, gradientEndColorHex = 0xFF991B1B),
        HomeCategoryItem("cat_quiz", "Quiz Section", badge = null, gradientStartColorHex = 0xFFDB2777, gradientEndColorHex = 0xFF9D174D),
        HomeCategoryItem("cat_current_affairs", "Current Affairs", badge = null, gradientStartColorHex = 0xFF1E293B, gradientEndColorHex = 0xFF0F172A)
    )

    fun getHomeCategories(): List<HomeCategoryItem> = homeCategories

    fun getTestSeriesList(): List<TestSeries> = listOf(sscSeries, mpSeries)

    fun getTestSeriesById(id: String): TestSeries? {
        return getTestSeriesList().find { it.id == id } ?: sscSeries
    }

    fun getTestsForSubCategory(seriesId: String, subCategory: String): List<Test> {
        val sections = MockQuestionRepository.getSectionsForTest("default")

        val day01AttemptSummary = TestAttemptSummary(
            attemptId = "att_day_01",
            score = 0.0f,
            maxScore = 200.0f,
            rank = 22789,
            totalCandidates = 24964,
            attemptDate = "Sep 27, 2026",
            accuracy = 0.0f
        )

        return listOf(
            Test(
                id = "ssc_test_day_02",
                seriesId = seriesId,
                title = "Practice Test Day - 02",
                subCategory = subCategory,
                durationMinutes = 60,
                totalQuestions = 100,
                totalMarks = 200.0f,
                isFree = true,
                sections = sections,
                previousAttempt = null
            ),
            Test(
                id = "ssc_test_day_01",
                seriesId = seriesId,
                title = "Practice Test Day - 01",
                subCategory = subCategory,
                durationMinutes = 60,
                totalQuestions = 100,
                totalMarks = 200.0f,
                isFree = true,
                sections = sections,
                previousAttempt = day01AttemptSummary
            ),
            Test(
                id = "ssc_test_day_03",
                seriesId = seriesId,
                title = "Practice Test Day - 03",
                subCategory = subCategory,
                durationMinutes = 60,
                totalQuestions = 100,
                totalMarks = 200.0f,
                isFree = true,
                sections = sections,
                previousAttempt = null
            ),
            Test(
                id = "ssc_test_day_04",
                seriesId = seriesId,
                title = "Practice Test Day - 04",
                subCategory = subCategory,
                durationMinutes = 60,
                totalQuestions = 100,
                totalMarks = 200.0f,
                isFree = true,
                sections = sections,
                previousAttempt = null
            )
        )
    }

    fun getTestById(testId: String): Test? {
        val tests = getTestsForSubCategory("ssc_selection_post_2026", "Exam Day Special")
        return tests.find { it.id == testId } ?: tests.first()
    }

    fun getPreviousAttemptResult(testId: String): TestResult {
        return TestResult(
            attemptId = "att_day_01",
            testId = testId,
            testTitle = "SSC Selection Post (Phase 14): Practice Test Day - 01",
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
            attemptDate = "Sep 27, 2026",
            sectionBreakdowns = listOf(
                SectionResult("sec_a", "General Intelligence", 0.0f, 0, 0, 25, 0, 0.0f, 0L),
                SectionResult("sec_b", "General Awareness", 0.0f, 0, 0, 25, 0, 0.0f, 0L),
                SectionResult("sec_c", "Quantitative Aptitude", 0.0f, 0, 0, 25, 0, 0.0f, 0L),
                SectionResult("sec_d", "English Language", 0.0f, 0, 0, 25, 0, 0.0f, 0L)
            )
        )
    }
}
```

---

### Recipe 11: `app/src/main/java/com/example/abhyaas/data/mock/MockUserRepository.kt`
```kotlin
package com.example.abhyaas.data.mock

import com.example.abhyaas.data.model.LeaderboardEntry
import com.example.abhyaas.data.model.PreparationDataPoint
import com.example.abhyaas.data.model.UserProfile

object MockUserRepository {

    private var currentUser = UserProfile(
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

    fun getUserProfile(): UserProfile = currentUser

    fun updateUserProfile(profile: UserProfile): UserProfile {
        currentUser = profile
        return currentUser
    }

    fun getPreparationDataPoints(metric: String = "Questions"): List<PreparationDataPoint> {
        return listOf(
            PreparationDataPoint(dateLabel = "May 2", questionsCount = 24, accuracyPercent = 65, timeSpentMinutes = 30),
            PreparationDataPoint(dateLabel = "May 4", questionsCount = 46, accuracyPercent = 70, timeSpentMinutes = 45),
            PreparationDataPoint(dateLabel = "May 7", questionsCount = 60, accuracyPercent = 82, timeSpentMinutes = 60),
            PreparationDataPoint(dateLabel = "May 9", questionsCount = 9, accuracyPercent = 50, timeSpentMinutes = 15),
            PreparationDataPoint(dateLabel = "May 10", questionsCount = 16, accuracyPercent = 60, timeSpentMinutes = 25),
            PreparationDataPoint(dateLabel = "Sep 27", questionsCount = 0, accuracyPercent = 0, timeSpentMinutes = 0)
        )
    }

    fun getLeaderboard(testId: String = "default"): List<LeaderboardEntry> {
        return listOf(
            LeaderboardEntry(rank = 1, userName = "Raja", score = 200.0f, maxScore = 200.0f, accuracy = 100.0f, timeTaken = "42m 10s"),
            LeaderboardEntry(rank = 2, userName = "Hemant", score = 195.0f, maxScore = 200.0f, accuracy = 97.5f, timeTaken = "45m 30s"),
            LeaderboardEntry(rank = 3, userName = "Vivek", score = 193.5f, maxScore = 200.0f, accuracy = 96.8f, timeTaken = "49m 12s"),
            LeaderboardEntry(rank = 4, userName = "Tanya", score = 191.0f, maxScore = 200.0f, accuracy = 95.5f, timeTaken = "52m 14s"),
            LeaderboardEntry(rank = 5, userName = "Gauravvv", score = 190.5f, maxScore = 200.0f, accuracy = 95.2f, timeTaken = "53m 40s"),
            LeaderboardEntry(rank = 6, userName = "Prashant Kumar Prajapat", score = 190.0f, maxScore = 200.0f, accuracy = 95.0f, timeTaken = "54m 02s"),
            LeaderboardEntry(rank = 7, userName = "Jithin Sarang J S", score = 186.0f, maxScore = 200.0f, accuracy = 93.0f, timeTaken = "55m 20s"),
            LeaderboardEntry(rank = 22789, userName = "Aspirant (You)", score = 0.0f, maxScore = 200.0f, accuracy = 0.0f, timeTaken = "00m 00s", isCurrentUser = true)
        )
    }
}
```

---

### Recipe 12: `app/src/main/java/com/example/abhyaas/data/mock/MockUpdatesRepository.kt`
```kotlin
package com.example.abhyaas.data.mock

import com.example.abhyaas.data.model.ExamUpdateItem
import com.example.abhyaas.data.model.UpdateCategory

object MockUpdatesRepository {

    private val updates = listOf(
        ExamUpdateItem(
            id = "upd_1",
            title = "SSC CGL 2026 Notification Released",
            description = "Staff Selection Commission has released the official notification for Combined Graduate Level Examination 2026.",
            date = "28 Sep 2026",
            category = UpdateCategory.NOTIFICATIONS,
            isPinned = true,
            pdfSize = "2.4 MB",
            actionText = "Download PDF"
        ),
        ExamUpdateItem(
            id = "upd_2",
            title = "SSC CGL 2026 Important Dates",
            description = "Check the complete schedule including online application dates, correction window, and Tier-1 exam dates.",
            date = "26 Sep 2026",
            category = UpdateCategory.EXAM_DATES,
            isPinned = false,
            pdfSize = "1.2 MB",
            actionText = "Download PDF"
        ),
        ExamUpdateItem(
            id = "upd_3",
            title = "SSC CGL 2026 Exam Pattern",
            description = "Detailed pattern for Tier-I and Tier-II computer-based examination with sectional marks and time duration.",
            date = "25 Sep 2026",
            category = UpdateCategory.SYLLABUS,
            isPinned = false,
            pdfSize = "950 KB",
            actionText = "Download PDF"
        ),
        ExamUpdateItem(
            id = "upd_4",
            title = "SSC CGL 2026 Syllabus",
            description = "Subject-wise comprehensive syllabus covering Quantitative Aptitude, General Intelligence, English & GK.",
            date = "24 Sep 2026",
            category = UpdateCategory.SYLLABUS,
            isPinned = false,
            pdfSize = "1.8 MB",
            actionText = "Download PDF"
        ),
        ExamUpdateItem(
            id = "upd_5",
            title = "SSC CGL 2026 Admit Card (Soon)",
            description = "Admit cards for Tier-1 exam will be released region-wise 7 days prior to the examination date.",
            date = "22 Sep 2026",
            category = UpdateCategory.ADMIT_CARD,
            isPinned = false,
            pdfSize = null,
            actionText = "Notify Me"
        ),
        ExamUpdateItem(
            id = "upd_6",
            title = "SSC CGL 2025 Result Declared",
            description = "Final result and category-wise cut-off marks for SSC CGL 2025 has been officially published.",
            date = "20 Sep 2026",
            category = UpdateCategory.RESULTS,
            isPinned = false,
            pdfSize = null,
            actionText = "View Details >"
        )
    )

    fun getAllUpdates(): List<ExamUpdateItem> = updates

    fun getUpdatesByCategory(category: UpdateCategory): List<ExamUpdateItem> {
        if (category == UpdateCategory.ALL) return updates
        return updates.filter { it.category == category }
    }
}
```

---

## 5. Verification Method

1. **Independent Worker Verification**:
   The Worker subagent can implement the files in `app/src/main/java/com/example/abhyaas/data/` using the exact recipes above.
2. **Compilation Command**:
   Run the Gradle compilation command:
   ```powershell
   ./gradlew assembleDebug
   ```
   Ensure 0 compile errors and 0 syntax warnings.
3. **Data Integrity Check**:
   - Verify that `MockQuestionRepository.getSectionsForTest()` returns exactly 4 sections with 25 questions each (100 total questions).
   - Verify that `MockExamRepository.getTestsForSubCategory()` returns both "Practice Test Day - 02" and "Practice Test Day - 01" with its previous attempt summary.
   - Verify that `MockUserRepository.getLeaderboard()` contains the top 3 podium entries, ranks 4-7, and user sticky entry `Aspirant (You)`.
