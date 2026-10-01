# Project: Abhyaas Jetpack Compose UI

## Architecture
- **Framework**: Android Jetpack Compose with Material 3
- **Language / Toolchain**: Kotlin 2.2.10, AGP 9.4.1, Gradle 9.6.0, Compose BOM 2026.02.01, CompileSdk 37, MinSdk 24
- **Navigation**: `androidx.navigation:navigation-compose:2.7.7`
  - Two-tier Navigation Architecture:
    - Root `AppNavHost`: Manages top-level destinations (Login, UserSetting, UserProfile, MainTabs, TestSeriesDetail, TestList, TestInstructions, ActiveTest, TestResult, PrivacyPolicy).
    - `MainScreen`: Scaffold host managing persistent bottom navigation across 4 core tabs (Home, Tests, Pass, Updates) + Side Navigation Drawer (`AppDrawer`).
- **State Management**:
  - Unidirectional Data Flow (UDF) with Compose State (`remember`, `rememberSaveable`, `mutableStateOf`, `derivedStateOf`).
  - Active Exam Engine: state machine handling countdown timer, 4-state question palette, option selection, bookmarking, and auto-submission.
  - Solutions Review Engine: reattempt mode toggle, filters (All, Unattempted, Correct, Incorrect), section-wise breakdown.
- **Data Layer**:
  - Deterministic in-memory Mock Repositories (`MockExamRepository`, `MockQuestionRepository`, `MockUserRepository`) delivering realistic SSC CGL & MPESB Nayab Tehsildar test series, questions, and stats.

## Feature Inventory
| # | Feature | Description | Milestone | Source |
|---|---------|-------------|-----------|--------|
| 1 | Brand Theme & Design System | Dark/Light theme, brand colors (Navy, Cobalt, Emerald, Amber, Crimson), custom typography | M1 | mockups & survey |
| 2 | Reusable UI Components | CommonTopAppBar, TimerChip, QuestionStatusBadge, OptionCard, StatCard, ActionPill | M1 | mockups & survey |
| 3 | Domain Models & Mock Repositories | Question, Option, Test, TestSection, TestAttempt, TestResult, LeaderboardEntry, UserProfile | M1 | mockups & survey |
| 4 | Navigation Architecture & Shell | Root AppNavHost, MainScreen scaffold, bottom navigation bar, backstack safety | M2 | mockups & survey |
| 5 | Authentication / Login Screen | Welcome header, value proposition cards, mobile number input, OTP request, language selector | M2 | `login page.png` |
| 6 | User Registration / Settings | Multi-step profile setup form (Name, Email, DOB, Category, Education, avatar picker) | M2 | `User Setting.png` |
| 7 | Side Navigation Drawer | Profile header, User Settings link, Home, Pass, Test Series, Study Notes, Exams, Updates | M2 | `3 line pe dabane pr ye aata hai.png` |
| 8 | Home Dashboard | Exam selector dropdown, Pass hero banner, 6 category grid cards, Current Affairs, AI FAB | M2 | `home tab 1.png` |
| 9 | Tests Catalog Tab | Hero carousel, Enrolled Test Series card with progress bar, circular quick shortcuts | M2 | `tests tab.png` |
| 10 | Abhyaas Pass Subscription Tab | "Now ABHYAS is FREE" hero banner, 6 feature cards, offer card, coupon box, Get Pass CTA | M2 | `pass.png` |
| 11 | Exam Updates & Notifications Tab | Updates header, horizontal filter chips, pinned cards, PDF size, Download PDF / Notify Me | M2 | `updates section.png` |
| 12 | User Profile & Prep Dashboard | Greeting banner, mascot, preparation trend line chart (Accuracy/Time/Questions), stat cards | M2 | `photo click pr ye aaega.png` |
| 13 | Privacy Policy Screen | Collapsible/expandable card detailing user data security, terms, and privacy policies | M2 | `privacy policy.jpeg` |
| 14 | Test Series Detail (Mock Tests Tab) | Total Tests (610), Attempted (1), Progress (0%), banner, grouped folders, Unlock CTA | M3 | `enrolled test.png` |
| 15 | Test Series Detail (PYPs Tab) | Total Papers (240), Solved (0), Progress (0%), matric/higher secondary/grad folders | M3 | `enrolled test 2.png` |
| 16 | Test Category Listing | Practice tests with FREE badges, duration, marks, language, Start Test & Share buttons | M3 | `test look.jpeg` |
| 17 | Test Listing with Attempt History | Suggested Next Test vs Previously Attempted tests with score, rank, progress, View Results | M3 | `test view after result.png` |
| 18 | Pre-Test Instructions Screen | Comprehensive rules, marking scheme (+2.0/-0.5), duration, declaration, Agree & Continue | M3 | `starting test.jpeg` |
| 19 | Language Selection Bottom Sheet | Modal sheet to switch exam language between English and Hindi | M3 | `language selection.jpeg` |
| 20 | Active Test Taking Screen | Question statement, passage, live countdown timer, per-question timer, bookmark, options | M4 | `on test options.jpeg`, `qs on test timer.jpeg` |
| 21 | Question Palette Drawer | 6-column grid of 25 question number chips with color states, Section Part tabs, Submit buttons | M4 | `on test summary.jpeg` |
| 22 | Question Status Symbols Guide | Legend modal explaining all 8 palette colors, tags, and action buttons | M4 | `symbol meaning.jpeg`, `symbol meaning 2.jpeg` |
| 23 | Exam Submission Confirmation | Summary dialog showing counts of answered, unanswered, marked questions with submit action | M4 | `on test summary.jpeg` |
| 24 | Test Result Scorecard (Analysis Tab) | Rank, Score, Percentile, Accuracy, Correct/Incorrect pills, Social challenge WhatsApp button | M5 | `result analysis.jpeg` |
| 25 | Solutions List & Section Drawer | Question cards with accuracy % & time taken, floating "Sections" pill opening section drawer | M5 | `solutions.jpeg`, `test attempt summary after test.jpeg` |
| 26 | Question Solution Review (Reattempt) | Reattempt Mode toggle switch (ON: hides answer; OFF: highlights correct answer green & reasoning) | M5 | `qs look after result.jpeg`, `qs look after test 2.jpeg` |
| 27 | Leaderboard & Ranking Screen | Top 3 Podium (Gold, Silver, Bronze), ranks 4-9 list, sticky bottom card with user rank "(You)" | M5 | `leaderboard.jpeg` |
| 28 | Full E2E Clickable Integration | End-to-end user journeys connected across all destinations with `./gradlew assembleDebug` pass | M6 | Acceptance Criteria |

## Milestones
| # | Name | Scope | Dependencies | Status |
|---|------|-------|-------------|--------|
| M1 | Design System, Theme, Components & Domain Models | Brand color palette, typography tokens, updated Theme.kt (dynamicColor = false), reusable components, domain data classes, and mock repositories | none | DONE |
| M2 | Navigation Shell, Auth & Primary Tabs | Two-tier NavHost, Login, UserSetting, MainScreen (Home, Tests, Pass, Updates), Drawer, Profile Dashboard, Privacy Policy | M1 | DONE |
| M3 | Test Series Hub, Listings & Instructions Flow | TestSeriesDetailScreen (Mock & PYP tabs), TestListScreen (Free & Attempt history), TestInstructionsScreen, Language Selection Sheet | M1, M2 | DONE |
| M4 | Immersive Active Exam Engine | ActiveTestScreen, Question Palette Sheet, Symbols Guide Modal, Timer countdown, option selection, submit confirmation | M1, M2, M3 | DONE |
| M5 | Post-Exam Scorecard, Solutions Review & Leaderboard | TestResultScreen (Analysis, Solutions, Leaderboard tabs), Reattempt mode toggle, Section Drawer, Podium & Sticky Ranker card | M1, M2, M3, M4 | IN_PROGRESS |
| M6 | E2E Integration & Verification | Wire all flows seamlessly, comprehensive Clickable manual flow verification, ./gradlew assembleDebug clean compilation | M1, M2, M3, M4, M5 | PLANNED |

## Interface Contracts

### 1. Domain Models & Mock Repositories (`com.example.abhyaas.data`)
```kotlin
// Models
data class Option(val id: Int, val text: String, val textHindi: String? = null)
enum class QuestionStatus { NOT_VISITED, UNANSWERED, ANSWERED, MARKED_FOR_REVIEW, ANSWERED_AND_MARKED }
data class Question(
    val id: Int, val sectionId: String, val questionNumber: Int,
    val directionText: String? = null, val directionTextHindi: String? = null,
    val statementText: String, val statementTextHindi: String? = null,
    val options: List<Option>, val correctOptionIndex: Int,
    val explanation: String, val explanationHindi: String? = null,
    val positiveMarks: Float = 2.0f, val negativeMarks: Float = 0.5f,
    val topic: String, val subject: String, val percentGotRight: Int = 64,
    val averageTimeSeconds: Int = 45, val isBookmarked: Boolean = false
)
data class TestSection(val id: String, val partName: String, val title: String, val titleHindi: String? = null, val questions: List<Question>)
data class Test(
    val id: String, val seriesId: String, val title: String, val subCategory: String,
    val durationMinutes: Int = 60, val totalQuestions: Int = 100, val totalMarks: Float = 200.0f,
    val isFree: Boolean = true, val supportedLanguages: List<String> = listOf("English", "Hindi"),
    val sections: List<TestSection>, val instructions: List<String>,
    val cutoffGeneral: String = "132-135", val averageScore: Float = 67.75f, val bestScore: Float = 200.0f
)
data class LeaderboardEntry(val rank: Int, val userName: String, val score: Float, val maxScore: Float = 200.0f, val accuracy: Float = 95.0f, val isCurrentUser: Boolean = false)
data class UserProfile(val fullName: String, val email: String, val mobileNumber: String, val category: String, val averageScorePercent: Int = 55, val totalTestsAttempted: Int = 7)
data class ExamUpdateItem(val id: String, val title: String, val description: String, val date: String, val category: String, val isPinned: Boolean = false, val pdfSize: String? = null, val actionText: String = "Download PDF")
```

### 2. Navigation Routes (`com.example.abhyaas.ui.navigation.Screen`)
```kotlin
sealed class Screen(val route: String) {
    object Login : Screen("login")
    object UserSetting : Screen("user_setting")
    object UserProfile : Screen("user_profile")
    object Main : Screen("main")
    object Home : Screen("home")
    object Tests : Screen("tests")
    object Pass : Screen("pass")
    object Updates : Screen("updates")
    object TestSeriesDetail : Screen("test_series_detail/{seriesId}") {
        fun createRoute(seriesId: String) = "test_series_detail/$seriesId"
    }
    object TestList : Screen("test_list/{seriesId}/{subCategory}") {
        fun createRoute(seriesId: String, subCategory: String) = "test_list/$seriesId/$subCategory"
    }
    object TestInstructions : Screen("test_instructions/{testId}") {
        fun createRoute(testId: String) = "test_instructions/$testId"
    }
    object ActiveTest : Screen("active_test/{testId}") {
        fun createRoute(testId: String) = "active_test/$testId"
    }
    object TestResult : Screen("test_result/{testId}") {
        fun createRoute(testId: String) = "test_result/$testId"
    }
    object PrivacyPolicy : Screen("privacy_policy")
}
```

## Code Layout
```text
app/src/main/java/com/example/abhyaas/
├── MainActivity.kt
├── data/
│   ├── model/
│   │   ├── Question.kt
│   │   ├── Test.kt
│   │   ├── TestAttempt.kt
│   │   ├── TestResult.kt
│   │   ├── LeaderboardEntry.kt
│   │   ├── UserProfile.kt
│   │   └── ExamUpdateItem.kt
│   └── mock/
│       ├── MockExamRepository.kt
│       ├── MockQuestionRepository.kt
│       ├── MockUserRepository.kt
│       └── MockUpdatesRepository.kt
└── ui/
    ├── theme/
    │   ├── Color.kt
    │   ├── Theme.kt
    │   └── Type.kt
    ├── navigation/
    │   ├── AppNavHost.kt
    │   └── Screen.kt
    ├── components/
    │   ├── CommonTopAppBar.kt
    │   ├── TimerChip.kt
    │   ├── QuestionStatusBadge.kt
    │   ├── OptionCard.kt
    │   ├── CategoryGridCard.kt
    │   └── StatCard.kt
    └── screens/
        ├── auth/
        │   ├── LoginScreen.kt
        │   └── UserSettingScreen.kt
        ├── main/
        │   ├── MainScreen.kt
        │   └── AppDrawer.kt
        ├── home/
        │   └── HomeScreen.kt
        ├── tests/
        │   ├── TestsScreen.kt
        │   ├── TestSeriesDetailScreen.kt
        │   └── TestListScreen.kt
        ├── pass/
        │   └── PassScreen.kt
        ├── updates/
        │   └── UpdatesScreen.kt
        ├── profile/
        │   └── UserProfileScreen.kt
        ├── exam/
        │   ├── TestInstructionsScreen.kt
        │   ├── ActiveTestScreen.kt
        │   ├── QuestionPaletteSheet.kt
        │   ├── SymbolsGuideDialog.kt
        │   └── LanguageSelectionSheet.kt
        ├── result/
        │   ├── TestResultScreen.kt
        │   ├── AnalysisTab.kt
        │   ├── SolutionsTab.kt
        │   ├── QuestionSolutionView.kt
        │   ├── SolutionsSectionDrawer.kt
        │   └── LeaderboardTab.kt
        └── policy/
            └── PrivacyPolicyScreen.kt
```
