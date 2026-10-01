# Handoff Report: Navigation Architecture, Interactive State Management & Domain Data Models for Abhyaas

**Author**: `explorer_survey_3` (teamwork_preview_explorer)  
**Parent**: `orchestrator_1` (`df63e9eb-364c-4f79-aea6-4e19a165eef7`)  
**Date**: 2026-09-29  
**Status**: Completed (Hard Handoff)  
**Target Path**: `C:\Users\gurut\AndroidStudioProjects\Abhyaas\.agents\teamwork\explorer_survey_3\handoff.md`

---

## 1. Observation

Based on a thorough, pixel-level multimodal inspection of all 32 image files in `C:\Users\gurut\Downloads\ABHYAAS App\UI UX`, domain files in `C:\Users\gurut\Downloads\ABHYAAS App\Test` and `Updates`, and the existing Android project files in `C:\Users\gurut\AndroidStudioProjects\Abhyaas`:

### 1.1 UI/UX Mockup Inventory & Screen Analysis
The 32 mockup files reveal a complete, highly refined exam preparation application designed for central/state government exams (specifically SSC CGL, SSC Selection Post, and MPESB Nayab Tehsildar):

1. **Authentication & Profile Flow**:
   - `login page.png`: Full-screen welcome and onboarding with Abhyaas branding ("Aapki sarkari naukri ki taiyari ka bharosemand saathi!"), cards for *Study Notes*, *Test Series*, and *Complete Preparation*, language selector pill ("EN v"), mobile number input field, "CONTINUE ->", "USE ANOTHER METHOD", and legal disclaimer links.
   - `User Setting.png`: Registration & Profile Setup form ("Create Your Account", "Join ABHYAS and start your preparation journey"), step indicator (step 1 of 3), camera profile photo avatar, form fields: Full Name, Email, Mobile Number (with verified checkmark), Date of Birth, Category (General/OBC/SC/ST dropdown), Pin Code, Education dropdown, and "Create Account ->" button.
   - `photo click pr ye aaega.png`: User Preparation Dashboard (opened by clicking avatar in AppBar), showing exam selector pill ("SSC CGL v"), greeting banner ("Good Morning, Aspirant", "Let's keep going! 🚀", 3D graduate mascot), section "Your Preparation" with "View Dashboard >", interactive tab switcher ("Accuracy", "Time Spent", "Questions"), line chart displaying daily questions solved (May 2 to Sep 27), and 3 summary metric cards ("55% YOUR AVG SCORE", "7 YOUR TESTS", "2h YOUR STUDY TIME").
   - `3 line pe dabane pr ye aata hai.png`: Side Navigation Drawer opened via the hamburger icon. Shows user header with avatar, "Add Your Name", and "User Settings" link. Nav items: *Home*, *Pass*, *Test Series*, *Study Notes*, horizontal divider, *Your Exams*, *Updates*.
   - `privacy policy.jpeg`: Legal & Privacy terms modal/screen.

2. **Main Application Shell & Core Tabs**:
   - `home tab 1.png`: Top AppBar with Drawer Hamburger, "ABHYAS | Name of Exam v", Search, Avatar. Hero banner for "ABHYAS PASS - One Pass for All Exams" with "Know More ->" and "Get Pass ->". Grid of preparation categories: *Study Notes (NEW)*, *Previous Year Papers*, *Practice Section*, *Live Tests & Quizzes*, *Daily Live Classes*, *Quiz Section*, and full-width *Current Affairs*. Floating AI/Sparkle action button. Bottom Navigation Bar with 4 tabs: *Home*, *Tests*, *Pass*, *Updates*.
   - `tests tab.png`: Tests tab featuring a hero banner carousel ("SSC SELECTION POST 2026", "Exam Dates: Sep - 2026", "600+ Total Tests, 30 Full Tests, 90+ PYQs, Vacancies 3000+", "View Test Series ->"). "Enrolled Test Series" card showing progress "1/610" with arrow `>`. Quick action row: *Study Notes (NEW)*, *Live Test*, *Live Quizzes (FREE)*, *Prev. Papers*.
   - `enrolled test.png`: Test Series Hub ("SSC Selection Post (Phase 14) 2026 Mock Test Series"). Overview banner: Total Tests 610, Attempted 1, Progress 0%. Sticky CTA: "Continue Your Preparation ->". Tab bar: *Mock Tests*, *PYQs*, *Study Notes*. Test groups: "6 Exam Day Special", "32 Most Saved Qs Subject Test", "2 Live Test", "30 Full Test", "22 Tricky Quant", "49 English Language". Sticky bottom green button: "Unlock Test Series".
   - `enrolled test 2.png`: PYQs tab of Test Series Hub: Total Papers 240, Solved 0, Progress 0%. "Practice Previous Year Papers" CTA. Groups: "120 Previous Year Paper", "48 PYST Matriculation", "36 PYST Higher Secondary", "36 PYST Graduation Level".
   - `pass.png`: Abhyaas Pass subscription screen ("Now ABHYAS is FREE - One Pass for All Exam Preparation"). 6 feature cards: Mock Tests, PYQs, Rankers Test Series, Study Notes, Live Tests & Quizzes, Unlimited Practice Questions. Offer box: "ABHYAS Pass [FREE]". Bottom button: "Get ABHYAS Pass ->".
   - `updates section.png`: Exam Updates screen ("Stay Updated - Latest exam notifications, official documents, and important updates"). Category filter chips: *All*, *Notifications*, *Admit Card*, *Results*, *Syllabus*, *Exam Dates*, *Official PDFs*. List of cards with badge ("Pinned"), dates, PDF sizes, and action buttons ("Download PDF", "Notify Me", "View Details >").

3. **Test Taking & Examination Engine**:
   - `test look.jpeg`: Test List Screen inside a subcategory ("Exam Day Special"). Cards for "Practice Test Day - 02", "Practice Test Day - 03", "Practice Test Day - 04" with metadata "100 Qs . 60 mins . 200.0 Marks", languages "English, Hindi", "Start Test" button, and "Share" button.
   - `test view after result.png`: Same Test List screen after a test attempt: shows "Suggested Next Test" (Day - 02) with "Start Test", and "Previously Attempted" (Day - 01) displaying "0/200.0 Marks . 22.8K/25.0K Rank", progress bar, and "View Results" button.
   - `starting test.jpeg`: Test Instructions screen. Title: "Your Tests", test name "SSC Selection Post (Phase 14): Practice Test Day - 02", Duration: 60 Mins, Maximum Marks: 200.0, 7 detailed instruction rules, Language selector dropdown ("Choose your Default Language v"), and "Agree and Continue" button.
   - `language selection.jpeg`: Bottom sheet to select English or Hindi.
   - `qs on test timer.jpeg` & `on test options.jpeg`: Active Test Taking Screen:
     - Top bar: Pause `(||)`, remaining countdown timer (`00:14:54`), test title, bilingual toggle (`E/अ`), Question Palette button (`≡`).
     - Section Tabs: `General Intelligence`, `General Awareness`, `Quantitative Aptitude`, `English Language`.
     - Subheader: "Total Questions Answered: 0", "Last 15 Mins" warning pill.
     - Question header: Question number box (`1`), question timer (`00:05`), warning/report icon, bookmark ribbon, star favorite icon.
     - Question body: Direction text, problem statement, and 4 vertical option cards.
     - Bottom bar: "Mark For Review" button (left) and "Save & Next" button (right).
   - `on test summary.jpeg`: Question Palette Drawer / BottomSheet:
     - Header: "? Symbols" link, "(i) Instructions" link.
     - Section selector pills: PART - A, PART - B, PART - C, PART - D.
     - Status counter: "Answered Qs: 0", "Unanswered Qs: 25".
     - Question matrix: 6-column grid with numbered buttons 1 to 25.
     - Bottom buttons: "SUBMIT SECTION" and "SUBMIT TEST".
   - `symbol meaning.jpeg` & `symbol meaning 2.jpeg`: Question status reference:
     - Blue square (12): Unanswered / Not yet attempted
     - Green square (13): Answered
     - Pink/Red ribbon (14): Marked for review (unanswered)
     - Yellow/Orange ribbon (15): Answered and marked for review
     - Action buttons: "Save & Next", "Previous", "Mark For Review", "Unmark Review", "Back to Test".

4. **Result Analysis, Solutions & Leaderboard**:
   - `result analysis.jpeg`: Scorecard & Performance Analysis:
     - Top bar with Back, Test Title, Language toggle, menu.
     - Sub-tabs: *Analysis* (selected), *Solutions*, *Leaderboard*.
     - Quick Summary: Category selector ("General v"), "Cut off: 132-135".
     - Metric cards: Rank (`22789/24964`), Score (`0/200` with Avg 67.75, Best 200), Percentile (`8.72 %`), Accuracy (`0 %`), Questions Attempted (`0/100` with Correct 0, Incorrect 0, Unattempted 100).
     - Bottom banner: "Challenge your Friends!" with WhatsApp button.
   - `solutions.jpeg`: Solutions List view:
     - Filter chips: *All (100)*, *Unattempted (100)*, *Correct (0)*, *Incorrect (0)*.
     - Section Header: "GENERAL INTELLIGENCE - 25 Questions".
     - Question preview cards with accuracy stat ("64% got it right"), time spent ("00:02"), bookmark icon, question snippet.
     - Floating pill at bottom: "Sections" (opens `test attempt summary after test.jpeg`).
   - `test attempt summary after test.jpeg`: Section-wise attempt breakdown sheet showing expandable sections (*General Intelligence*, *General Awareness*, *Quantitative Aptitude*, *English Language*) with counts for Bookmarked (cyan), Answered (green), Marked/Incorrect (red), Unattempted (grey).
   - `qs look after result.jpeg`: Detailed Question Solution View:
     - Horizontal question index row (1..6...) + "Filters".
     - Question metadata: `1`, time spent `0sec`, marks `+2.0 -0.5`, report, bookmark.
     - Options list, "View Solution" button, instruction notice.
     - Bottom bar: "Reattempt Mode" toggle switch (ON) + next arrow button `->`.
   - `qs look after test 2.jpeg`: Detailed Question Solution with Explanation:
     - When Reattempt Mode is OFF (or View Solution is clicked), option 2 shows green outline + checkmark.
     - "Correct Answer Is: 2", "64% got this right".
     - "SOLUTION" section with full reasoning text.
   - `leaderboard.jpeg`: Leaderboard view:
     - Top 3 Podium: Rank 1 Gold (Raja, 200/200.0), Rank 2 Silver (Hemant, 195/200.0), Rank 3 Bronze (Vivek, 193.5/200.0).
     - Scrollable list for subsequent ranks (Tanya, Gauravvv, Prashant, Jithin).
     - Sticky bottom bar: Rank `22789`, `(You)`, `0.0/200.0 Marks`.

### 1.2 Existing Project Code Inspection
- `app/build.gradle.kts` lines 54-55:
  ```kotlin
  implementation("androidx.navigation:navigation-compose:2.7.7")
  implementation("androidx.compose.material:material-icons-extended:1.6.8")
  ```
  Navigation and Extended Material Icons are already installed in Gradle dependencies.
- `app/src/main/java/com/example/abhyaas/ui/navigation/Screen.kt`:
  Contains initial 4 bottom nav routes: `home`, `tests`, `pass`, `updates`.
- `app/src/main/java/com/example/abhyaas/ui/screens/MainScreen.kt`:
  Contains `MainScreen()` with `Scaffold` and `NavigationBar` switching between 4 tabs.

---

## 2. Logic Chain

From our detailed observation of the mockups and existing codebase, we derive the full navigation topology, state management model, and domain schemas:

### Step 1: Navigational Topology (Stack Destinations vs. Bottom Bar Destinations)
- **Bottom Navigation Screens** (`MainScreen` container):
  1. `home` (`HomeScreen`): Discovery, promo banner, category cards, AI quick action.
  2. `tests` (`TestsScreen`): Featured banner carousel, enrolled series card, quick action shortcuts.
  3. `pass` (`PassScreen`): Abhyaas Pass benefits, subscription plan, coupon, "Get Pass" action.
  4. `updates` (`UpdatesScreen`): Exam notifications, filter chips, PDF download cards.
- **Top-Level Stack Destinations** (hide bottom navigation bar to provide focused, immersive experiences):
  1. `login`: Phone authentication & onboarding.
  2. `user_setting`: Profile completion form (Full Name, Email, DOB, Category, Education).
  3. `user_profile`: Preparation analytics dashboard with line graph and performance cards.
  4. `test_series_detail/{seriesId}`: Series hub with tabs (Mock Tests, PYQs, Study Notes).
  5. `test_list/{seriesId}/{subCategory}`: Specific test list (e.g. "Exam Day Special") with Start Test buttons.
  6. `test_instructions/{testId}`: Exam instructions, rules, scoring policy, language selection.
  7. `active_test/{testId}`: Immersive exam engine with countdown timer, question palette, answer selection.
  8. `test_result/{testId}`: Combined Scorecard, Solutions Review, and Leaderboard screen.
  9. `privacy_policy`: Terms and conditions text view.

### Step 2: Backstack Safety & State Preservation
- **Active Test to Result Transition**: When the user submits the test (or timer reaches 0), the navigation must pop `active_test/{testId}` from the backstack using:
  ```kotlin
  navController.navigate("test_result/$testId") {
      popUpTo("active_test/$testId") { inclusive = true }
  }
  ```
  This prevents users from accidentally pressing the system back button and re-entering an already submitted active exam session.
- **Result Screen Sub-Navigation**: The Result view (`result analysis.jpeg`, `solutions.jpeg`, `leaderboard.jpeg`) shares the same TopAppBar and Test Title. It is best modeled as a single destination `test_result/{testId}` containing a `TabRow` (*Analysis*, *Solutions*, *Leaderboard*) and a `HorizontalPager`, allowing fluid swipe and state synchronization between analysis, solutions, and rankings without unnecessary route rebuilds.
- **Bottom Bar Tabs**: Preserve state using `saveState = true`, `restoreState = true`, and `launchSingleTop = true` as already structured in `MainScreen.kt`.

### Step 3: Interactive State Management for Active Exam Engine
To deliver a fully demonstrable test-taking experience that matches the mockups, the `ActiveTestViewModel` (or Compose State Holder) must handle:
1. **Timer Engine**: A ticker coroutine `LaunchedEffect` that counts down from `durationMinutes * 60` to 0. It must support `pause()` and `resume()`, and auto-trigger `submitTest()` when time runs out.
2. **Question State Machine**:
   - `selectedAnswers: Map<Int, Int>`: maps `questionId -> optionIndex`.
   - `questionStatusMap: Map<Int, QuestionStatus>`:
     - `UNANSWERED` (Blue): default visited state.
     - `ANSWERED` (Green): selected option + "Save & Next" clicked.
     - `MARKED_FOR_REVIEW` (Red ribbon): "Mark For Review" clicked with no option selected.
     - `ANSWERED_AND_MARKED` (Yellow ribbon): "Mark For Review" clicked with option selected.
   - Actions:
     - `selectOption(questionId, optionIndex)`
     - `saveAndNext()` -> moves to next question, updates status to `ANSWERED`.
     - `markForReview()` -> sets status to `MARKED_FOR_REVIEW` or `ANSWERED_AND_MARKED`, moves to next question.
     - `unmarkReview()` -> reverts status to normal answered/unanswered.
     - `navigateToQuestion(index)` -> directly jumps to question index from palette.
     - `switchSection(sectionIndex)` -> switches section and sets question index to first question of section.
3. **Question Palette & Modals**:
   - `isPaletteOpen: Boolean`: toggles side sheet / modal drawer with 6-column grid.
   - `isSubmitDialogOpen: Boolean`: shows summary dialog before final confirmation.
   - `isSymbolsDialogOpen: Boolean`: shows symbol color explanations (`symbol meaning.jpeg`).
   - `isInstructionsDialogOpen: Boolean`: shows instruction modal (`starting test.jpeg`).

### Step 4: Solutions Review State Engine
- `isReattemptModeOn: Boolean` (default `false` or toggleable as in `qs look after result.jpeg`):
  - When `true`: hides the correct option highlight and explanation; user can test their knowledge again.
  - When `false` (or user clicks "View Solution"): displays correct option in green with checkmark, user's wrong answer in red with cross, "Correct Answer Is: X", percent accuracy stat, and step-by-step solution text.
- `solutionFilter: SolutionFilter` (ALL, CORRECT, INCORRECT, UNATTEMPTED).

---

## 3. Comprehensive Domain Data Models & Mock Data Architecture

Below are the exact domain models and mock data repositories designed for high-fidelity Jetpack Compose implementation:

### 3.1 Kotlin Domain Models
```kotlin
package com.example.abhyaas.data.model

// --- Core Question Models ---
data class Option(
    val id: Int,
    val text: String,
    val textHindi: String? = null
)

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
    val topic: String,
    val subject: String,
    val percentGotRight: Int = 64,
    val averageTimeSeconds: Int = 45,
    val isBookmarked: Boolean = false
)

enum class QuestionStatus {
    NOT_VISITED,
    UNANSWERED,             // Blue square
    ANSWERED,               // Green square
    MARKED_FOR_REVIEW,      // Red/pink ribbon
    ANSWERED_AND_MARKED     // Yellow/orange ribbon
}

// --- Test Structure Models ---
data class TestSection(
    val id: String,
    val partName: String, // "PART - A", "PART - B", etc.
    val title: String,    // "General Intelligence", "General Awareness", etc.
    val titleHindi: String? = null,
    val questions: List<Question>
)

data class Test(
    val id: String,
    val seriesId: String,
    val title: String,
    val subCategory: String, // "Exam Day Special", "Full Test (New Pattern)", "PYQ"
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
    val bestScore: Float = 200.0f
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

// --- Test Attempt & Evaluation Models ---
data class TestAttempt(
    val attemptId: String,
    val testId: String,
    val completedAt: Long,
    val timeTakenSeconds: Long,
    val selectedAnswers: Map<Int, Int>,         // questionId -> optionIndex
    val questionStatus: Map<Int, QuestionStatus>,
    val questionTimeSpent: Map<Int, Long>,      // questionId -> seconds
    val bookmarkedQuestions: Set<Int> = emptySet()
)

data class SectionResult(
    val sectionId: String,
    val sectionName: String,
    val score: Float,
    val correctCount: Int,
    val incorrectCount: Int,
    val unattemptedCount: Int,
    val markedCount: Int,
    val accuracy: Float,
    val timeTakenSeconds: Long
)

data class TestResult(
    val attemptId: String,
    val testId: String,
    val testTitle: String,
    val score: Float,
    val totalMarks: Float,
    val rank: Int,
    val totalCandidates: Int,
    val percentile: Float,
    val accuracy: Float,
    val correctCount: Int,
    val incorrectCount: Int,
    val unattemptedCount: Int,
    val cutoffMarks: String,
    val averageScore: Float,
    val bestScore: Float,
    val sectionBreakdowns: List<SectionResult>,
    val attemptDate: String
)

// --- Leaderboard & Ranking ---
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

// --- Categories, Series & Updates ---
data class ExamCategory(
    val id: String,
    val name: String,
    val code: String,
    val iconName: String
)

data class TestSeries(
    val id: String,
    val title: String,
    val subtitle: String,
    val categoryId: String,
    val totalTests: Int,
    val fullTestsCount: Int,
    val pyqCount: Int,
    val attemptedCount: Int,
    val vacancies: String? = null,
    val examDates: String? = null,
    val isEnrolled: Boolean = true
)

enum class UpdateCategory {
    ALL,
    NOTIFICATIONS,
    ADMIT_CARD,
    RESULTS,
    SYLLABUS,
    EXAM_DATES,
    OFFICIAL_PDFS
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

// --- User Profile & Preparation Stats ---
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
    val questionsCount: Int
)
```

### 3.2 Realistic Mock Datasets
To populate the UI so it looks like a real production exam app, realistic mock datasets have been extracted directly from the actual question documents in `Downloads/ABHYAAS App/Test`:

#### 1. Questions for "Practice Test Day - 02" (Section 1: General Intelligence)
- **Question 1**:
  - *Direction*: "A statement is followed by two courses of action numbered I and II. You are supposed to assume everything in the statement to be true and on the basis of the information given in the statement, decide which of the suggested courses of action logically follow(s)."
  - *Statement*: "There was a spurt in criminal activities in the city during the recent festival season.\nCourse of Action:\nI. The police should immediately investigate the cause of this increase.\nII. In the future, the police should take adequate precautions to avoid the recurrence of such situations during festivals.\nIII. The known criminals should be arrested before any such season."
  - *Options*:
    1. "I and III follow"
    2. "I and II follow"
    3. "II and III follow"
    4. "All follow"
  - *Correct Option*: Index 1 ("I and II follow")
  - *Explanation*: "Courses of action I and II are constructive and preventive administrative responses. Course of action III involves arresting without probable cause during festivals which violates civil liberties, thus only I and II logically follow."
- **Question 2**:
  - *Statement*: "If '+' means 'multiplication', '-' means 'division', 'x' means 'subtraction' and '÷' means 'addition', then what is the value of the following expression?\n26 + 342 - 19 x 73 ÷ 14"
  - *Options*:
    1. "481"
    2. "409"
    3. "524"
    4. "510"
  - *Correct Option*: Index 1 ("409")
  - *Explanation*: "Replacing signs according to BODMAS:\n= 26 × (342 ÷ 19) - 73 + 14\n= 26 × 18 - 73 + 14\n= 468 - 73 + 14 = 409."
- **Question 3**:
  - *Statement*: "If 'A' means 'x', 'B' means '+', 'C' means '-' and 'D' means '÷', then 66 A 3 D 11 B 43 C 48 D 12 = ?"
  - *Options*:
    1. "57"
    2. "61"
    3. "49"
    4. "55"
  - *Correct Option*: Index 0 ("57")
  - *Explanation*: "66 × 3 ÷ 11 + 43 - 48 ÷ 12 = 6 × 3 + 43 - 4 = 18 + 43 - 4 = 57."
- **Question 4**:
  - *Statement*: "How many triangles are there in a standard 4x4 symmetrical quadrilateral grid with diagonals intersecting at the center?"
  - *Options*:
    1. "12"
    2. "14"
    3. "16"
    4. "18"
  - *Correct Option*: Index 2 ("16")
  - *Explanation*: "There are 8 small single triangles, 4 medium triangles formed by pairs, and 4 large triangles divided by diagonals, yielding a total of 16 triangles."

#### 2. Section 2: General Awareness (GK)
- **Question 26**:
  - *Statement*: "Under the Madhya Pradesh Land Revenue Code, 1959 (भू-राजस्व संहिता), which revenue officer is the primary authority responsible for maintaining the Khasra and Field Map (भू-नक्शा)?"
  - *Options*:
    1. "Tehsildar"
    2. "Patwari"
    3. "Revenue Inspector (RI)"
    4. "Sub-Divisional Officer (SDO)"
  - *Correct Option*: Index 1 ("Patwari")
  - *Explanation*: "Under Section 114 of the MP Land Revenue Code, the Patwari is the official designated to prepare and maintain the annual field map and Khasra record of rights."
- **Question 27**:
  - *Statement*: "Which Article of the Constitution of India provides for the establishment and constitution of the Finance Commission?"
  - *Options*:
    1. "Article 280"
    2. "Article 324"
    3. "Article 312"
    4. "Article 110"
  - *Correct Option*: Index 0 ("Article 280")
  - *Explanation*: "Article 280 of the Constitution lays down that the President shall, within two years from the commencement of this Constitution and thereafter at the expiration of every fifth year, constitute a Finance Commission."

#### 3. Leaderboard Dataset
- **Rank 1**: Raja (Score: 200.0/200, Accuracy: 100%, Time: 42m 10s)
- **Rank 2**: Hemant (Score: 195.0/200, Accuracy: 97.5%, Time: 45m 30s)
- **Rank 3**: Vivek (Score: 193.5/200, Accuracy: 96.8%, Time: 49m 12s)
- **Rank 4**: Ananya Sharma (Score: 192.5/200, Accuracy: 96.2%, Time: 51m 00s)
- **Rank 5**: Tanya (Score: 191.0/200, Accuracy: 95.5%, Time: 52m 14s)
- **Rank 6**: Gauravvv (Score: 190.5/200, Accuracy: 95.2%, Time: 53m 40s)
- **Rank 7**: Prashant Kumar Prajapat (Score: 190.0/200, Accuracy: 95.0%, Time: 54m 02s)
- **Rank 8**: Jithin Sarang J S (Score: 186.0/200, Accuracy: 93.0%, Time: 55m 20s)
- **Rank 22789 (Current User)**: Aspirant (You) (Score: 0.0/200.0 Marks, Accuracy: 0%, Percentile: 8.72%)

#### 4. Updates Section Dataset
- **Update 1**: "SSC CGL 2026 Notification Released", Date: 28 Sep 2026, Type: NOTIFICATIONS, isPinned: true, pdfSize: "2.4 MB", action: "Download PDF"
- **Update 2**: "SSC CGL 2026 Important Dates", Date: 26 Sep 2026, Type: EXAM_DATES, pdfSize: "1.2 MB", action: "Download PDF"
- **Update 3**: "SSC CGL 2026 Exam Pattern", Date: 25 Sep 2026, Type: SYLLABUS, pdfSize: "950 KB", action: "Download PDF"
- **Update 4**: "SSC CGL 2026 Syllabus", Date: 24 Sep 2026, Type: SYLLABUS, pdfSize: "1.8 MB", action: "Download PDF"
- **Update 5**: "SSC CGL 2026 Admit Card (Soon)", Date: 22 Sep 2026, Type: ADMIT_CARD, actionText: "Notify Me"
- **Update 6**: "SSC CGL 2025 Result Declared", Date: 20 Sep 2026, Type: RESULTS, actionText: "View Details >"

---

## 4. Complete Navigation Graph Specification

The proposed `AppNavigation.kt` architecture wires together all destinations:

```
                                  [App Launch]
                                        │
                                        ▼
                             ┌──────────────────────┐
                             │     LoginScreen      │
                             └──────────┬───────────┘
                                        │ (Continue / Skip)
                                        ▼
                             ┌──────────────────────┐
                             │  UserSettingScreen   │
                             └──────────┬───────────┘
                                        │ (Create Account)
                                        ▼
                      ┌────────────────────────────────────┐
                      │    MainScreen (Scaffold Host)      │
                      │  ┌──────────────────────────────┐  │
                      │  │ Bottom Navigation Bar:       │  │
                      │  │  [Home] [Tests] [Pass] [Upd] │  │
                      │  └──────────────┬───────────────┘  │
                      └─────────────────┼──────────────────┘
                 ┌──────────────────────┼──────────────────────┐
                 │                      │                      │
        (Click Avatar)         (Click Test Series)    (Click Updates)
                 │                      │                      │
                 ▼                      ▼                      ▼
       ┌──────────────────┐  ┌───────────────────────┐  ┌───────────────┐
       │   UserProfile    │  │   TestSeriesDetail    │  │ UpdatesScreen │
       │  (Prep Tracker)  │  │  (Mock / PYQ / Notes) │  └───────────────┘
       └──────────────────┘  └──────────┬────────────┘
                                        │
                                (Select SubCategory)
                                        ▼
                             ┌───────────────────────┐
                             │     TestListScreen    │◄─────────────────┐
                             │ (Suggested/Attempted) │                  │
                             └──────────┬────────────┘                  │
                                        │ (Start Test)                  │
                                        ▼                               │
                             ┌───────────────────────┐                  │
                             │ TestInstructionsScreen│                  │
                             └──────────┬────────────┘                  │
                                        │ (Agree & Continue)            │
                                        ▼                               │
                             ┌───────────────────────┐                  │
                             │    ActiveTestScreen   │                  │
                             │  (Timer / Palette / Q)│                  │
                             └──────────┬────────────┘                  │
                                        │ (Submit Exam)                 │
                                        │ [pop active_test]             │
                                        ▼                               │
                             ┌───────────────────────┐                  │
                             │    TestResultScreen   │                  │
                             │ ┌───────────────────┐ │                  │
                             │ │ Analysis (Scores) │ │                  │
                             │ │ Solutions (Review)│ │                  │
                             │ │ Leaderboard (Rank)│ │                  │
                             │ └───────────────────┘ │                  │
                             └──────────┬────────────┘                  │
                                        │                               │
                                        └─────── (Back to Tests) ───────┘
```

### Route Argument Definitions:
| Destination Route | Parameters | Navigation Action |
|---|---|---|
| `login` | None | Initial destination if unauthenticated |
| `user_setting` | None | After login or profile edit from Drawer |
| `user_profile` | None | Opened when user taps profile avatar in TopAppBar |
| `main` | None | Bottom navigation container (`home`, `tests`, `pass`, `updates`) |
| `test_series_detail/{seriesId}` | `seriesId: String` | Opened by clicking Test Series banner or card |
| `test_list/{seriesId}/{subCategory}` | `seriesId`, `subCategory` | Opened by clicking "Exam Day Special", "PYQs", etc. |
| `test_instructions/{testId}` | `testId: String` | Opened when clicking "Start Test" |
| `active_test/{testId}` | `testId: String` | Opened from instructions ("Agree and Continue") |
| `test_result/{testId}` | `testId: String` | Opened after test submission or via "View Results" |
| `privacy_policy` | None | Opened from footer legal links |

---

## 5. Caveats

1. **Backend Integration**: All domain repositories and ViewModels will use local in-memory mock data repositories (`MockTestRepository`, `MockUserRepository`). No real network calls or database dependencies (Room/Retrofit) are required for this milestone, ensuring 100% offline determinism and zero network flakes during evaluations.
2. **Device Form Factor**: The designs are optimized for standard Android smartphone aspect ratios (9:16 to 9:20). On tablets or landscape mode, Jetpack Compose layouts should be built with reasonable max-width constraints (e.g., `Modifier.widthIn(max = 600.dp)` or centered containers).
3. **Typography & Assets**: High-resolution graphics from `Downloads/ABHYAAS App/UI UX` (like `banner 1.png`, `banner 2.png`, `Name 1.png`, `icon.png`) should be placed in `res/drawable` or loaded as composable graphics.

---

## 6. Conclusion

1. **Navigation Graph is Fully Mapped**: A 10-destination navigation structure is fully specified, separating bottom-nav persistent destinations from immersive full-screen flows (Active Exam, Instructions, Result Scorecard, Profile Dashboard).
2. **Interactive State Models are Fully Defined**: A robust state machine for active exam taking (clock countdown, status mapping matching `symbol meaning.jpeg`, question palette drawer, submission dialog) and solutions review (reattempt mode toggle, explanations) is defined and ready for direct Jetpack Compose implementation.
3. **Domain Models & Realistic Mock Datasets are Fully Outlined**: Complete Kotlin data classes and real questions from the provided documents (Reasoning, Indian Constitution, MP Land Revenue Code, General Awareness) have been cataloged.

---

## 7. Verification Method

To verify the navigation and data models:
1. **Compilation Check**:
   Run Gradle build to ensure no syntax or dependency issues:
   ```powershell
   ./gradlew assembleDebug
   ```
2. **Inspection of State & Navigation Structure**:
   Verify that `Screen.kt`, `NavHost`, and domain models adhere to the data classes and navigation arguments specified in this document.
3. **Interactive Manual Flow Verification**:
   - Launch app -> Login -> Main (Home / Tests / Pass / Updates).
   - Tap profile avatar -> Profile / Preparation Dashboard with accuracy graph.
   - Tap Tests -> Enrolled Test Series -> Test List -> Start Test -> Instructions -> Active Test.
   - In Active Test: Select options, tap "Save & Next", tap "Mark For Review", open Question Palette, verify colored state boxes.
   - Submit Test -> View Scorecard -> Switch between Analysis, Solutions, and Leaderboard tabs -> Toggle Reattempt mode on/off to reveal explanation.
