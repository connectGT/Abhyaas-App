## 2026-09-29T14:59:30Z
You are worker_m2, a teamwork_preview_worker subagent.
Your working directory is: C:\Users\gurut\AndroidStudioProjects\Abhyaas\.agents\teamwork\worker_m2
Your parent is orchestrator_1 (conversation ID: df63e9eb-364c-4f79-aea6-4e19a165eef7).

MANDATORY INPUT:
Read the authoritative user request at:
C:\Users\gurut\AndroidStudioProjects\Abhyaas\.agents\teamwork\ORIGINAL_REQUEST.md

Read the project architecture and interface contracts at:
C:\Users\gurut\AndroidStudioProjects\Abhyaas\PROJECT.md
Read the survey specifications and mockup mappings at:
C:\Users\gurut\AndroidStudioProjects\Abhyaas\.agents\teamwork\spec_miner_survey_1\handoff.md
C:\Users\gurut\AndroidStudioProjects\Abhyaas\.agents\teamwork\explorer_survey_3\handoff.md

EXCLUSIVE FILE OWNERSHIP:
You have exclusive write access to:
- `app/src/main/java/com/example/abhyaas/MainActivity.kt`
- `app/src/main/java/com/example/abhyaas/ui/navigation/Screen.kt`
- `app/src/main/java/com/example/abhyaas/ui/navigation/AppNavHost.kt`
- `app/src/main/java/com/example/abhyaas/ui/screens/auth/LoginScreen.kt`
- `app/src/main/java/com/example/abhyaas/ui/screens/auth/UserSettingScreen.kt`
- `app/src/main/java/com/example/abhyaas/ui/screens/main/MainScreen.kt`
- `app/src/main/java/com/example/abhyaas/ui/screens/main/AppDrawer.kt`
- `app/src/main/java/com/example/abhyaas/ui/screens/home/HomeScreen.kt`
- `app/src/main/java/com/example/abhyaas/ui/screens/tests/TestsScreen.kt`
- `app/src/main/java/com/example/abhyaas/ui/screens/pass/PassScreen.kt`
- `app/src/main/java/com/example/abhyaas/ui/screens/updates/UpdatesScreen.kt`
- `app/src/main/java/com/example/abhyaas/ui/screens/profile/UserProfileScreen.kt`
- `app/src/main/java/com/example/abhyaas/ui/screens/policy/PrivacyPolicyScreen.kt`

MISSION:
Implement Milestone 2 (Navigation Shell, Auth Flow, and Primary Tabs):
1. Wire up `MainActivity.kt` to render `AppNavHost` within `AbhyaasTheme`.
2. Expand `Screen.kt` with all top-level and nested routes (`Login`, `UserSetting`, `UserProfile`, `Main`, `Home`, `Tests`, `Pass`, `Updates`, `TestSeriesDetail`, `TestList`, `TestInstructions`, `ActiveTest`, `TestResult`, `PrivacyPolicy`).
3. Create `AppNavHost.kt` implementing the two-tier navigation structure.
4. Implement `LoginScreen.kt` matching `login page.png` with mobile number input, language toggle, value prop cards, and Continue action.
5. Implement `UserSettingScreen.kt` matching `User Setting.png` with profile fields, step indicator, category dropdown, education dropdown, and Create Account action.
6. Implement `MainScreen.kt` and `AppDrawer.kt` matching `3 line pe dabane pr ye aata hai.png` with 4-tab bottom navigation bar (`Home`, `Tests`, `Pass`, `Updates`) and side drawer.
7. Implement `HomeScreen.kt` matching `home tab 1.png` with TopAppBar ("ABHYAS | SSC CGL ▾"), Pass hero banner ("ABHYAS PASS - One Pass for All Exams"), 6 category cards using `CategoryGridCard`, full-width `CurrentAffairs` card, and glowing AI FAB.
8. Implement `TestsScreen.kt` matching `tests tab.png` with hero banner carousel ("SSC SELECTION POST 2026"), Enrolled Test Series card with progress bar, and circular quick action shortcuts row. Clicking enrolled test series navigates to `test_series_detail/{seriesId}`.
9. Implement `PassScreen.kt` matching `pass.png` with "Now ABHYAS is FREE" hero banner, 6 feature cards, offers & coupon section, and "Get ABHYAS Pass ->" green button.
10. Implement `UpdatesScreen.kt` matching `updates section.png` with filter chips (All, Notifications, Admit Card, Results, etc.) and update cards with PDF sizes and action buttons ("Download PDF", "Notify Me").
11. Implement `UserProfileScreen.kt` matching `photo click pr ye aaega.png` (opened when tapping profile avatar) with greeting banner, mascot illustration, Preparation Tracker tabs (`🎯 Accuracy`, `⏱ Time Spent`, `✔ Questions`), trend line chart, and 3 metric summary cards ("55% YOUR AVG SCORE", "7 YOUR TESTS", "2h YOUR STUDY TIME").
12. Implement `PrivacyPolicyScreen.kt` matching `privacy policy.jpeg` with expandable card layout.
13. Run verification via run_command:
    - `.\gradlew.bat compileDebugKotlin`
    - `.\gradlew.bat assembleDebug`
    - `.\gradlew.bat testDebugUnitTest`
    Verify all exit with code 0!
14. Keep `progress.md` updated in your working directory.
15. Write your handoff report in:
`C:\Users\gurut\AndroidStudioProjects\Abhyaas\.agents\teamwork\worker_m2\handoff.md`
16. Send completion message to parent (df63e9eb-364c-4f79-aea6-4e19a165eef7).
