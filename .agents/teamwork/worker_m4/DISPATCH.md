## 2026-09-29T15:21:43Z
You are worker_m4, a teamwork_preview_worker subagent.
Your working directory is: C:\Users\gurut\AndroidStudioProjects\Abhyaas\.agents\teamwork\worker_m4
Your parent is orchestrator_1 (conversation ID: df63e9eb-364c-4f79-aea6-4e19a165eef7).

MANDATORY INPUT:
Read the authoritative user request at:
C:\Users\gurut\AndroidStudioProjects\Abhyaas\.agents\teamwork\ORIGINAL_REQUEST.md

Read the project architecture and interface contracts at:
C:\Users\gurut\AndroidStudioProjects\Abhyaas\PROJECT.md
Read the survey specifications at:
C:\Users\gurut\AndroidStudioProjects\Abhyaas\.agents\teamwork\spec_miner_survey_1\handoff.md
C:\Users\gurut\AndroidStudioProjects\Abhyaas\.agents\teamwork\explorer_survey_3\handoff.md

MANDATORY INTEGRITY WARNING:
DO NOT CHEAT. All implementations must be genuine. DO NOT hardcode test results, create dummy/facade implementations, or circumvent the intended task. A teamwork_preview_auditor will independently verify your work. Integrity violations WILL be detected and your work WILL be rejected.

EXCLUSIVE FILE OWNERSHIP:
You have exclusive write access to:
- `app/src/main/java/com/example/abhyaas/ui/screens/exam/ActiveTestScreen.kt`
- `app/src/main/java/com/example/abhyaas/ui/screens/exam/QuestionPaletteSheet.kt`
- `app/src/main/java/com/example/abhyaas/ui/screens/exam/SymbolsGuideDialog.kt`
- `app/src/main/java/com/example/abhyaas/ui/screens/exam/SubmitConfirmDialog.kt`
- `app/src/main/java/com/example/abhyaas/ui/navigation/AppNavHost.kt` (to wire active_test route)

MISSION:
Implement Milestone 4 (Immersive Active Exam Engine):
1. `ActiveTestScreen.kt` matching `qs on test timer.jpeg` and `on test options.jpeg`:
   - Full-screen examination environment (hides bottom navigation bar).
   - Top Bar:
     - Pause button `(||)` (pauses countdown timer and shows resume dialog)
     - Monospace countdown timer chip (`00:14:51` / `00:59:45`) using `TimerChip`, with warning color state when time < 5 mins
     - Test Title: "SSC Selection Post (Phase 14): Practice Test Day - 02"
     - Bilingual toggle button `[E/अ]` that switches question statement, options, direction, and explanation between English and Hindi
     - Question Palette toggle button (`≡`) in top-right that opens `QuestionPaletteSheet`
   - Section Tabs Row:
     - 4 sections: `PART - A (General Intelligence)`, `PART - B (General Awareness)`, `PART - C (Quantitative Aptitude)`, `PART - D (English Language)`
     - Switching tabs switches the active question subset
   - Sub-header:
     - "Total Questions Answered: X" counter
     - "Last 15 Mins" warning pill
   - Question Header:
     - Question number box (`1`)
     - Per-question timer clock icon + `00:08`
     - Report warning icon `!`, Bookmark ribbon icon (toggles bookmark state), Star favorite icon
   - Question Content:
     - Direction text (italic, e.g. syllogism / courses of action instructions)
     - Statement / problem text
     - 4 selectable Option Cards using `OptionCard` component with radio selection (`A`, `B`, `C`, `D`), high contrast, and responsive selection states
   - Bottom Action Bar:
     - Outlined blue button "Mark For Review" (sets status to MARKED_FOR_REVIEW if unselected, or ANSWERED_AND_MARKED if option selected, advances to next question)
     - Filled blue button "Save & Next" (saves answer, marks as ANSWERED, advances to next question)
2. `QuestionPaletteSheet.kt` matching `on test summary.jpeg`:
   - Modal drawer / bottom sheet opened via palette icon
   - Top action links: `? Symbols` (opens `SymbolsGuideDialog`) | `(i) Instructions`
   - Section pill selector: `PART - A`, `PART - B`, `PART - C`, `PART - D`
   - Section Name: "General Intelligence" (or selected section)
   - Status counters: Green dot + "Answered Qs: X", Blue dot + "Unanswered Qs: Y"
   - 6-column grid of 25 question number chips:
     - Uses `QuestionStatusBadge` matching `symbol meaning.jpeg`:
       - Blue square: Unanswered / Not yet attempted
       - Green square: Answered
       - Coral red tag with bottom pointer (`▲`): Marked for review without answering
       - Amber yellow tag with bottom pointer (`▲`): Answered and marked for review
       - Active question has blue outline halo
     - Clicking any question number directly jumps to that question in the active exam
   - Bottom Action Buttons:
     - "SUBMIT SECTION"
     - "SUBMIT TEST" (opens `SubmitConfirmDialog`)
3. `SymbolsGuideDialog.kt` matching `symbol meaning.jpeg` and `symbol meaning 2.jpeg`:
   - Full dialog/modal detailing the 8 symbols, tags, and action buttons
   - Blue button: "Back to Test" to dismiss
4. `SubmitConfirmDialog.kt`:
   - Summary of answered, unanswered, marked for review questions
   - Buttons: "Cancel" and "Submit Exam". Submitting clears the backstack:
     `navController.navigate("test_result/$testId") { popUpTo("active_test/$testId") { inclusive = true } }`
5. Wire `active_test/{testId}` route in `AppNavHost.kt`.
6. Run verification via run_command:
   - `.\gradlew.bat compileDebugKotlin`
   - `.\gradlew.bat assembleDebug`
   - `.\gradlew.bat testDebugUnitTest`
   Ensure all exit with code 0!
7. Keep `progress.md` updated in your working directory.
8. Write your handoff report in:
`C:\Users\gurut\AndroidStudioProjects\Abhyaas\.agents\teamwork\worker_m4\handoff.md`
9. Send completion message to parent (df63e9eb-364c-4f79-aea6-4e19a165eef7).
