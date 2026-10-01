## 2026-09-29T15:10:30Z
You are worker_m3, a teamwork_preview_worker subagent.
Your working directory is: C:\Users\gurut\AndroidStudioProjects\Abhyaas\.agents\teamwork\worker_m3
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
- `app/src/main/java/com/example/abhyaas/ui/screens/tests/TestSeriesDetailScreen.kt`
- `app/src/main/java/com/example/abhyaas/ui/screens/tests/TestListScreen.kt`
- `app/src/main/java/com/example/abhyaas/ui/screens/exam/TestInstructionsScreen.kt`
- `app/src/main/java/com/example/abhyaas/ui/screens/exam/LanguageSelectionSheet.kt`
- `app/src/main/java/com/example/abhyaas/ui/navigation/AppNavHost.kt` (to connect M3 routes)

MISSION:
Implement Milestone 3 (Test Series Hub, Category Listings & Instructions Flow):
1. Implement `TestSeriesDetailScreen.kt` matching `enrolled test.png` and `enrolled test 2.png`:
   - Top bar with Back button, SSC logo, title "SSC Selection Post (Phase 14) / 2026 Mock Test Series", announcement megaphone, overflow.
   - Metrics card: Total Tests (610), Attempted (1), Progress (0%).
   - Banner: "Continue Your Preparation" / "Practice Previous Year Papers".
   - Tabs: *Mock Tests*, *PYQs*, *Study Notes*.
   - Mock Tests tab list: "6 Exam Day Special" (6 Free Tests badge), "32 Most Saved Qs Subject Test", "2 Live Test", "30 Full Test", "22 Tricky Quant", "49 English Language". Clicking any folder navigates to `test_list/{seriesId}/{subCategory}`.
   - PYQs tab list: "120 Previous Year Paper", "48 PYST (Matriculation)", "36 PYST (Higher Secondary)", "36 PYST (Graduation)".
   - Bottom sticky green button: "Unlock Test Series".
2. Implement `TestListScreen.kt` matching `test look.jpeg` and `test view after result.png`:
   - Top bar with Back arrow and series title. Sub-tabs ("Exam Day Special", "Most Saved Qs").
   - Section 1: "Suggested Next Test" with "Practice Test Day - 02" (Free badge, 100 Qs, 60 mins, 200.0 Marks, English/Hindi, "Start Test" blue button). Clicking "Start Test" navigates to `test_instructions/{testId}`.
   - Section 2: "Previously Attempted" with "Practice Test Day - 01" (0/200.0 Marks, 22.8K/25.0K Rank, progress slider bar, "View Results" outlined button). Clicking "View Results" navigates to `test_result/{testId}`.
   - Bottom sticky green button: "Unlock Test Series".
3. Implement `TestInstructionsScreen.kt` matching `starting test.jpeg`:
   - Top bar: `< Your Tests`. Pass promo banner.
   - Test title: "SSC Selection Post (Phase 14): Practice Test Day - 02". Duration: 60 Mins, Maximum Marks: 200.0.
   - 7 detailed instruction rules (100 questions, 4 options, 60 mins limit, 25 Qs per section, +2.0/-0.5 marking scheme, 0 penalty unattempted, cheating declaration checkbox).
   - Dropdown pill: "Choose your Default Language ▾", opening `LanguageSelectionSheet`.
   - Blue button: "Agree and Continue" navigating to `active_test/{testId}`.
4. Implement `LanguageSelectionSheet.kt` matching `language selection.jpeg`:
   - Modal bottom sheet allowing selection of English or Hindi.
5. Wire these screens into `AppNavHost.kt` routes `test_series_detail/{seriesId}`, `test_list/{seriesId}/{subCategory}`, and `test_instructions/{testId}`.
6. Run verification via run_command:
   - `.\gradlew.bat compileDebugKotlin`
   - `.\gradlew.bat assembleDebug`
   - `.\gradlew.bat testDebugUnitTest`
   Ensure all exit with code 0.
7. Keep `progress.md` updated in your working directory.
8. Write your handoff report in:
`C:\Users\gurut\AndroidStudioProjects\Abhyaas\.agents\teamwork\worker_m3\handoff.md`
9. Send completion message to parent (df63e9eb-364c-4f79-aea6-4e19a165eef7).
