# UI/UX Specification Mining & Survey Report — Abhyaas

**Author**: `spec_miner_survey_1` (Teamwork Specification Miner)  
**Parent Agent**: `orchestrator_1` (`df63e9eb-364c-4f79-aea6-4e19a165eef7`)  
**Workspace**: `C:\Users\gurut\AndroidStudioProjects\Abhyaas\.agents\teamwork\spec_miner_survey_1`  
**Target App Project**: `C:\Users\gurut\AndroidStudioProjects\Abhyaas`  
**Mockups & Design Assets Directory**: `C:\Users\gurut\Downloads\ABHYAAS App\UI UX`  
**Supplementary Data Assets**: `C:\Users\gurut\Downloads\ABHYAAS App\Test` and `Updates`  
**Timestamp**: 2026-09-29T13:48:00Z  

---

## 1. Observation

A full survey of the directories and files was conducted using `list_dir`, `view_file`, and filesystem tools.

### 1.1 UI/UX Mockup Files (32 total files in `C:\Users\gurut\Downloads\ABHYAAS App\UI UX`)
All 32 files were visually inspected. The following inventory lists the filename, exact file size, and visual screen mapping:

1. `login page.png` (1,222,456 bytes): **Authentication / Login Screen**
   - Dark blue top banner with book logo, tagline *"Aapki सरकारी नौकरी की तैयारी का भरोसेमंद साथी!"*.
   - Feature cards horizontal row: "Study Notes", "Test Series", "Complete Preparation".
   - Bottom sheet container: "Login to Abhyaas", subtitle *"Enter your mobile number to continue"*, Language pill `[🌐 EN v]`, Phone input textfield with phone icon, "We'll send an OTP to this number", Blue rounded button *"CONTINUE ->"*, divider "OR", text button *"USE ANOTHER METHOD"*, footer terms & privacy links.
2. `User Setting.png` (1,204,837 bytes): **Account Creation / User Settings Screen**
   - Top bar with back arrow; Header *"Create Your Account"*, subtitle *"Join ABHYAS and start your preparation journey"*.
   - Progress bar with 3 steps (step 1 active). Top-right 3D book/graduation cap illustration.
   - Profile photo upload circle with camera icon and *"Add Profile Photo"*.
   - Form fields: Full Name *, Email *, Mobile Number * (with green verified checkmark badge), Date of Birth * (with calendar icon), Category * (dropdown), Pin Code *, Education * (dropdown).
   - Bottom CTA: Blue button *"Create Account ->"*. Terms agreement footer.
3. `basic pages and buttons.png` (1,122,489 bytes): **Core App Shell / Main Scaffold**
   - TopAppBar: Hamburger menu icon, Exam selector dropdown *"SSC CGL v"*, Search icon, User avatar.
   - Deep midnight blue ambient background (`#0A1322` to `#060B14`) with subtle wave curves.
   - Bottom Navigation Bar with 4 tabs: Home, Tests, Pass, Updates (Home tab has active blue indicator line above icon).
4. `3 line pe dabane pr ye aata hai.png` (1,041,994 bytes): **Side Navigation Drawer**
   - Triggered by the 3-line hamburger menu in the top bar.
   - Drawer Header: Circular student avatar, User name *"Add Your Name"*, cyan link *"User Settings"*.
   - Menu items: Home (active pill highlight `#1E2C44`), Pass, Test Series, Study Notes, horizontal divider line, Your Exams, Updates.
5. `home tab 1.png` (1,367,842 bytes): **Home Dashboard Screen**
   - TopAppBar: Hamburger, *"ABHYAS | Name of Exam v"*, Search, Avatar.
   - ABHYAS PASS Hero Banner: *"ABHYAS PASS - One Pass for All Exams"*, link *"Know More ->"*, green button *"Get Pass ->"*.
   - Section: *"What are you looking for"* with vertical cyan accent bar.
   - 2-Column Grid of 6 Category Cards:
     - Study Notes (Purple gradient, "NEW" badge)
     - Previous Year Papers (Amber/Brown gradient)
     - Practice Section (Blue gradient)
     - Live Tests & Quizzes (Indigo/Purple gradient)
     - Daily Live Classes (Red gradient)
     - Quiz Section (Magenta gradient)
   - Full-width card: Current Affairs (Dark blue gradient).
   - Floating Action Button (FAB): Blue glowing circle with 4-point sparkle/stars icon.
   - Bottom Navigation Bar (Home active).
6. `tests tab.png` (1,355,198 bytes): **Tests Catalog Screen (Tests Tab)**
   - TopAppBar: Hamburger, *"ABHYAS | Name of Exam v"*, Search, Avatar.
   - Hero Banner Carousel: "SSC SELECTION POST 2026", tentative dates "Sep - 2026", 600+ Total Tests, 30 Full Tests, 90+ PYQs, 3000+ Vacancies, green button *"View Test Series ->"*, 3-dot carousel indicator.
   - Section: *"Enrolled Test Series"* with link *"View All ->"*.
   - Enrolled Card: SSC Logo, *"SSC Selection Post (Phase 14) 2026 Mock Test Series"*, progress bar showing 1/610 attempted, chevron `>`.
   - Circular Quick Category Shortcuts row: Study Notes (NEW), Live Test, Live Quizzes (FREE), Prev. Papers.
   - Floating Action Button (sparkle).
   - Bottom Navigation Bar (Tests active).
7. `enrolled test.png` (1,279,008 bytes): **Test Series Detail Screen — Mock Tests Tab**
   - Top bar: Back arrow, SSC Logo, title *"SSC Selection Post (Phase 14) / 2026 Mock Test Series"*, announcement megaphone, 3-dots overflow.
   - Metrics Card: Total Tests (610), Attempted (1), Progress (0%).
   - Banner Card: Blue gradient *"Continue Your Preparation - Attempt tests, track progress and improve your score."* with right arrow.
   - Section Tabs: "Mock Tests" (active underline), "PYQs", "Study Notes".
   - Category Folder List:
     - "6 Exam Day Special" (6 Free Tests badge)
     - "32 Most Saved Qs Subject Test"
     - "2 Live Test" (2 Free Tests badge, live icon)
     - "30 Full Test (New Pattern)" (1 Free Tests badge)
     - "22 फटाफट Tricky Quant" (1 Free Tests badge)
     - "49 English Language"
   - Bottom Sticky CTA: Green rounded button *"Unlock Test Series"*.
8. `enrolled test 2.png` (1,260,818 bytes): **Test Series Detail Screen — PYPs Tab**
   - Same screen structure, "PYPs" tab active.
   - Metrics Card: Total Papers (240), Solved (0), Progress (0%).
   - Banner: *"Practice Previous Year Papers - Understand the exam pattern, analyze your preparation and boost your score."*.
   - Folder List:
     - "120 Previous Year Paper" (All subjects PYQs 2016-2025)
     - "48 PYST (Matriculation Level)"
     - "36 PYST (Higher Secondary Level)"
     - "36 PYST (Graduation Level)"
   - Bottom Sticky CTA: Green button *"Unlock Previous Year Papers"*.
9. `test look.jpeg` (77,914 bytes): **Test Listing Screen (Category View)**
   - Top bar: Back arrow, *"SSC Selection Post (Phase 14) / 2026 Mock Test Series"*.
   - Sub-tabs: "Exam Day Special" (active), "Most Saved Qs Subjec...".
   - List of Test Cards:
     - Card items: Practice Test Day - 02, Practice Test Day - 03, Practice Test Day - 04.
     - Each card contains: Green "FREE" badge, Test Title, meta *"100 Qs . 60 mins. 200.0 Marks"*, blue action button *"Start Test"*, language tag *"English, Hindi"*, green link *"Share"*.
   - Bottom Sticky CTA: Green button *"Unlock Test Series"*.
10. `test view after result.png` (1,235,745 bytes): **Test Listing Screen with Attempt History**
    - Section 1: *"Suggested Next Test"* with Practice Test Day - 02 (with blue filled *"Start Test"* button).
    - Section 2: *"Previously Attempted"* with Practice Test Day - 01 (Stats: *"0/200.0 Marks . 22.8K/25.0K Rank"*, progress slider bar with orange indicator dot, blue outlined button *"View Results"*, timestamp *"Attempted on Sep 27, 2026"*, Share link).
    - Filter chip: Blue pill *"Daily"*.
    - Bottom Sticky CTA: Green button *"Unlock Test Series"*.
11. `starting test.jpeg` (100,304 bytes): **Pre-Test Instructions Screen**
    - Top bar: `< Your Tests`.
    - Banner: Pass promo banner *"Get Unlimited Mock Tests, PYPs & m... [Get Pass]"*.
    - Test Title: *"SSC Selection Post (Phase 14): Practice Test Day - 02"*.
    - Metadata: *"Duration: 60 Mins. Maximum Marks: 200.0"*.
    - Detailed bullet instructions: 100 questions, 4 options, 60 mins limit, 25 Qs / 15 mins per section, +2.0 marks correct / 0.5 negative marking, 0 penalty unattempted, cheating declaration.
    - Bottom Bar: Dropdown pill *"Choose your Default Language v"*, Blue button *"Agree and Continue"*.
12. `language selection.jpeg` (56,315 bytes): **Language Selection Bottom Sheet**
    - Modal bottom sheet: Header *"Select Language"*, list of options: "English", "Hindi".
13. `symbol meaning.jpeg` (81,785 bytes) & `symbol meaning 2.jpeg` (75,748 bytes): **Question Status Symbols Guide**
    - Opened via `? Symbols` in question palette drawer during active test.
    - Full table of 8 interactive symbols & test actions:
      1. Blue square `12`: Unattempted question.
      2. Green square `13`: Answered question.
      3. Coral/Red tag with triangle pointer `14` `▲`: Marked for review without answering.
      4. Yellow/Amber tag with triangle pointer `15` `▲`: Answered and marked for review.
      5. Blue filled button `Save & Next`: Advance to next question.
      6. Outlined blue button `Previous`: Go back to previous question.
      7. Outlined blue button `Mark For Review`: Mark question for review.
      8. Blue filled button `Unmark Review`: Unmark question for review.
    - Bottom CTA: Blue button *"Back to Test"*.
14. `on test options.jpeg` (81,125 bytes) & `qs on test timer.jpeg` (113,539 bytes): **Active Test Taking Screen**
    - Top bar: Pause button `||`, Monospace countdown timer `00:14:51` / `00:14:54`, Test title, Language switch `[E अ]`, Question Palette icon (hamburger).
    - Section Tabs: "General Intelligence" (active), "General Awareness".
    - Section Sub-bar: "Total Questions Answered: 0" badge, "Last 15 Mins" red badge.
    - Question Info Bar: Question #1 blue square badge, Question Timer clock icon + `00:08`, Report icon `!`, Bookmark icon, Star icon.
    - Question Content: Formatted directions, statements, syllogisms/courses of action, question text.
    - Options: 4 dark selectable card items.
    - Bottom Action Bar: Outlined blue button *"Mark For Review"*, Filled blue button *"Save & Next"*.
15. `on test summary.jpeg` (66,203 bytes): **Question Palette Drawer / Test Summary**
    - Opened via top-right hamburger icon in active test.
    - Top action links: `? Symbols` | `(i) Instructions`.
    - Section Pill Tabs: `PART - A` (active blue), `PART - B`, `PART - C`.
    - Section Name: "General Intelligence".
    - Summary status counters: Green dot + Answered Qs (0), Blue dot + Unanswered Qs (25).
    - 6-column Grid of Question Numbers: Current question has blue border; unanswered questions are solid blue squares.
    - Bottom Action Buttons: Blue button *"SUBMIT SECTION"*, Slate-gray button *"SUBMIT TEST"*.
16. `result analysis.jpeg` (68,495 bytes): **Test Result Screen — Analysis Tab**
    - Top bar: Back arrow, Title, `[E अ]`, Hamburger menu.
    - Result Tabs: "Analysis" (active), "Solutions", "Leaderboard".
    - Header: *"QUICK SUMMARY"*, Category filter `General v`, Cut-off info `Cut off: 132-135`.
    - Metric Cards:
      - Rank Card: Orange flag icon, Rank: `22789/24964`.
      - Score Card: Purple trophy icon, Score: `0/200`, `Average Score: 67.75 | Best Score: 200`.
      - Performance Card: Percentile `8.72 %`, Accuracy `0 %`, Qs. Attempted `0/100`, Status pills row: Correct: 0 (green check), Incorrect: 0 (red cross), Unattempted: 100 (gray question).
    - Social Challenge Card: "Challenge your Friends!", illustration of high-five hands, "1.1k+ Students Challenged", green WhatsApp button *"💬 Challenge"*.
17. `solutions.jpeg` (83,477 bytes): **Test Result Screen — Solutions Tab**
    - Result Tabs: "Analysis", "Solutions" (active), "Leaderboard".
    - Filter pills: `All (100)` (active blue), `Unattempted (100)`.
    - Section Header: "GENERAL INTELLIGENCE" (25 Questions).
    - List of Question Solution Cards: Question number, Accuracy metric (e.g., "64% got it right"), Time spent (e.g., "00:02"), Bookmark icon, Question snippet text.
    - Floating bottom pill: Blue button *"Sections"*.
18. `test attempt summary after test.jpeg` (42,665 bytes): **Solutions Section Switcher Drawer**
    - Opened via "Sections" pill on Solutions screen.
    - Lists all 4 exam sections: General Intelligence, General Awareness, Quantitative Aptitude, English Language.
    - Each section displays breakdown icons: Cyan bookmark (0), Green correct (0), Red incorrect (0), Gray unattempted (25).
19. `qs look after result.jpeg` (69,272 bytes): **Question Solution Review (Reattempt Mode ON)**
    - Top bar: Back, Title, `All Sections v`, `[E अ]`, Menu.
    - Horizontal Question Carousel strip (chips 1..6..) + `Y Filters` button.
    - Question Info Bar: Question #1 gray chip, Time spent `0sec`, Marking scheme `+2.0 -0.5`, Report icon, Bookmark icon.
    - Question text & 4 Option cards.
    - "View Solution" outlined blue button (disabled/prompting).
    - Explanatory note: *"Re-attempt mode is ON. Turn OFF the Re-attempt mode or re-attempt the question to see the solutions."*.
    - Bottom bar: "Reattempt Mode" toggle switch (ON) + Circular blue Next button `->`.
20. `qs look after test 2.jpeg` (66,822 bytes): **Question Solution Review (Reattempt Mode OFF / Solution Displayed)**
    - Correct option (Option 2) highlighted with green border and green checkmark `✓`.
    - Answer stat bar: *"Correct Answers Is: 2"* | *"64% got this right"*.
    - "SOLUTION" section header with detailed step-by-step reasoning explanation (e.g. BODMAS rule).
    - Bottom bar: "Reattempt Mode" toggle switch (OFF) + Circular blue Next button `->`.
21. `leaderboard.jpeg` (67,115 bytes): **Test Result Screen — Leaderboard Tab**
    - Result Tabs: "Analysis", "Solutions", "Leaderboard" (active).
    - Podium (Top 3 Rankers):
      - 1st (Gold ring & crown badge #1): "Raja" — 200/200.0
      - 2nd (Silver ring & badge #2): "Hemant" — 195/200.0
      - 3rd (Bronze ring & badge #3): "Vivek" — 193.5/200.0
    - List of subsequent ranks: Rank 4 to 9 with rank number, circular avatar, student name, score.
    - Sticky Bottom Floating Card: Periwinkle blue card `#D0E2FF` with user's rank `22789`, Avatar + `(You)`, Score `0.0/200.0 Marks`.
22. `pass.png` (1,425,333 bytes): **Pass Subscription Screen (Pass Tab)**
    - Top bar: Hamburger, title *"ABHYAS Pass"*.
    - Hero Card: *"Now ABHYAS is FREE - One Pass for All Exam Preparation"*, 3D illustration.
    - Section: *"Get Unlimited Access to"* with 6 feature cards (Mock Tests, PYPs, Rankers Test Series, Study Notes, Live Tests & Quizzes, Unlimited Practice Questions).
    - Section: *"Offers for you"* with link *"Apply Coupon"*.
    - Offer Card: 3D gift box, *"ABHYAS Pass FREE - Get unlimited access to all features"*, link *"Change >"*.
    - Sticky Bottom CTA: Green button *"Get ABHYAS Pass ->"*.
    - Bottom Navigation Bar (Pass active).
23. `updates section.png` (1,502,535 bytes): **Exam Updates & Notifications Screen (Updates Tab)**
    - Top bar: Hamburger, title *"ABHYAS | Updates"*, Search, Avatar.
    - Hero Card: *"Stay Updated - Latest exam notifications, official documents, and important updates all in one place."*.
    - Filter Chips: All (active), Notifications, Admit Card, Results, Syllabus, Exam Dates, Official PDFs.
    - Section: *"Latest Updates"* with filter button `Y Filter v`.
    - List of 6 Update Cards:
      - SSC CGL 2026 Notification Released (Pinned, Download PDF 2.4 MB)
      - Important Dates (Download PDF 1.2 MB)
      - Exam Pattern (Download PDF 950 KB)
      - Syllabus (Download PDF 1.8 MB)
      - Admit Card Soon (Notify Me button)
      - Result Declared (View Details button)
    - Bottom Navigation Bar (Updates active).
24. `photo click pr ye aaega.png` (1,017,679 bytes): **User Profile & Preparation Analytics Dashboard**
    - Opened by clicking the user avatar in the top bar.
    - Top bar: Back arrow, "ABHYAS", Subheader with exam selector `SSC CGL v`, Search, Avatar.
    - Greeting Card: "Good Morning, Aspirant", "Let's keep going! 🚀", 3D cartoon waving book mascot.
    - Section: *"Your Preparation"* with link *"View Dashboard >"*.
    - Performance Card:
      - Metric selector tabs: `🎯 Accuracy`, `⏱ Time Spent`, `✔ Questions` (active amber pill).
      - Historical Trend Line Chart (Dates vs Questions: May 2: 24q, May 4: 46q, May 7: 60q, May 9: 9q, May 10: 16q, Sep 27: 0q).
      - Summary metrics row: 55% YOUR AVG SCORE, 7 YOUR TESTS, 2h YOUR STUDY TIME.
25. `privacy policy.jpeg` (58,351 bytes): **Privacy Policy Card / Modal**
    - Collapsible card with vertical blue bar, title *"Privacy Policy"*, expand/collapse chevron `^`, and body text describing data privacy and analytics practices.
26. `banner 1.png` (2,082,886 bytes) & `banner 2.png` (2,312,324 bytes): **Promotional Exam Campaign Banners**
    - High-resolution banners for MPESB Nayab Tehsildar 2026 Departmental Recruitment Test in English and Hindi.
    - Features: 73 Posts, Exam Dates, 600+ Practice Tests, 30+ Full Tests, 90+ PYQs, green CTA button *"VIEW TEST SERIES ->"*.
27. `icon.png` (1,175,329 bytes): **App Launcher Icon**
    - Blue gradient rounded squircle with open book, graduation cap with yellow tassel, checkmarked test sheet, and yellow energy sparks.
28. `Name 1.png` (1,210,585 bytes) & `name 2.png` (1,218,044 bytes): **Branding Header / Logo Asset**
    - App icon + 3D typography "ABHYAAS" with wave arch in 'A' + subtitle *"By 2-Minute Education"*.

### 1.2 Supplementary Content Discovered in Parent Folders
Inspected `C:\Users\gurut\Downloads\ABHYAAS App\Test` and `Updates`:
- `Updates/MP Nayab Tehsildar Departmental Exam Rulebook.pdf` (1,885,344 bytes): Official exam rulebook directly matching the campaign banners.
- `Test/full test 1.docx` (121,210 bytes) and `full test 2.docx` (129,098 bytes): Real full test mock questions and answers.
- `Test/paper 1/Reasoning Question bank.docx`, `Reasoning Solution Bank.docx`, `gk qs bank.docx`: Question banks with reasoning and general knowledge questions + solutions.
- `Test/Paper 2/rajasva shabdavali question bank.docx`: Revenue terminology question bank.
- `Test/PYQ/Nayab_Tehsildar_Department_Paper 1.pdf` and `Paper 2.pdf`: Official previous year papers.

---

## 2. Logic Chain

1. **Mapping Mockups to Navigation Architecture**:
   - The UI contains two major navigation contexts:
     - **Main Scaffold with Bottom Navigation**: 4 persistent primary tabs:
       1. `Home`: Main discovery dashboard (`home tab 1.png`).
       2. `Tests`: Test series catalog, enrolled tests, category shortcuts (`tests tab.png`).
       3. `Pass`: Subscription and benefit unlocking screen (`pass.png`).
       4. `Updates`: Exam notifications, PDFs, admit card alerts (`updates section.png`).
     - **Side Navigation Drawer**: Accessible from the hamburger icon across all 4 main tabs (`3 line pe dabane pr ye aata hai.png`), linking to Home, Pass, Test Series, Study Notes, Your Exams, Updates, and User Settings.
     - **Profile & Preparation Dashboard**: Accessible by clicking the top-bar profile avatar (`photo click pr ye aaega.png`), containing preparation progress charts and stats.
     - **Authentication & Setup Flow**:
       - `login page.png` (Login with phone number & OTP)
       - `User Setting.png` (Profile onboarding & account setup)
     - **Test Taking & Assessment Flow**:
       - `enrolled test.png` / `enrolled test 2.png` -> Select test series
       - `test look.jpeg` / `test view after result.png` -> Test listing with attempt history
       - `starting test.jpeg` -> Instructions screen with language picker (`language selection.jpeg`)
       - `on test options.jpeg` / `qs on test timer.jpeg` -> Active test taking environment
       - `on test summary.jpeg` -> Question palette drawer & test submission
       - `symbol meaning.jpeg` / `symbol meaning 2.jpeg` -> Interactive symbols guide dialog
     - **Post-Test Evaluation & Analytics Flow**:
       - `result analysis.jpeg` -> High-level scorecard, rank, accuracy, social challenge
       - `solutions.jpeg` -> List of question solutions with filter pills & section drawer (`test attempt summary after test.jpeg`)
       - `qs look after result.jpeg` / `qs look after test 2.jpeg` -> Detailed step-by-step question review with Reattempt Mode toggle
       - `leaderboard.jpeg` -> Top 3 podium, ranking list, user's sticky rank banner

2. **Visual Design System & Theming Logic**:
   - **Base Theme**: Dark Theme is the primary design language across the entire application (except for the bottom sheet card in `login page.png` and `photo click pr ye aaega.png` cards which use high-contrast light surfaces for crisp legibility).
   - **Color Palette Hierarchy**:
     - *Background*: Deep Navy/Charcoal `#0B111A`, `#0F172A`, gradient `#0A1322` -> `#060B14`.
     - *Surface Cards*: Dark Slate `#161F2E`, `#1F242C`, `#1E293B`.
     - *Brand Primary*: Vibrant Blue `#2563EB` / `#1877F2`.
     - *Accent Secondary*: Cyan / Sky Blue `#00C2FF` / `#38BDF8`.
     - *Success / Free / CTA*: Emerald Green `#10B981` / `#059669`.
     - *Warning / Review*: Amber/Yellow `#F59E0B`.
     - *Alert / Marked / Live*: Crimson Red `#EF4444`.
     - *Special / Quiz*: Magenta `#DB2777` & Purple `#7C3AED`.
   - **State Machine for Active Tests**:
     - Each question has 4 discrete mutually exclusive visual states in the palette:
       - State 1: `Unattempted` -> Blue square (`#2563EB`)
       - State 2: `Answered` -> Green square (`#10B981`)
       - State 3: `MarkedForReviewUnanswered` -> Coral tag with arrow (`#EF4444` `▲`)
       - State 4: `MarkedForReviewAnswered` -> Amber tag with arrow (`#F59E0B` `▲`)
     - In the active question view, the currently selected question has a blue border stroke around the chip.
   - **Reattempt Mode Logic on Solutions Screen**:
     - When `Reattempt Mode == ON`: Solution text is hidden, "View Solution" button is disabled/locked, allowing the student to re-test their knowledge on missed questions.
     - When `Reattempt Mode == OFF`: Correct option is highlighted in green (`✓`), question statistics ("64% got this right") and full step-by-step explanation card are revealed.

---

## 3. Caveats

1. **Dark vs Light Surfaces**:
   - The majority of the screens are consistently dark-themed. However, `login page.png` features a white bottom sheet on a dark background, and `photo click pr ye aaega.png` (Profile Analytics) features white card containers on a cyan-to-dark gradient background. Jetpack Compose components must support both dark surfaces and high-contrast light cards seamlessly.
2. **Dynamic Data vs Mock Data**:
   - In accordance with Requirement R3 in `ORIGINAL_REQUEST.md`, all mock data should be hardcoded locally in the app, but formatted to represent real SSC CGL / MPESB Nayab Tehsildar test series as discovered in `Downloads/ABHYAAS App/Test`.
3. **Screen Density & Responsiveness**:
   - Mockups are taken on modern Android phones with aspect ratio 20:9 (~1080x2400). Layouts must use Jetpack Compose flexible scrolling (`LazyColumn`, `rememberScrollState`) and responsive grid columns to avoid clipping on smaller or larger devices.

---

## 4. Conclusion & Complete Feature Catalog

### Features Discovered

| # | Category | Feature | Description | Inputs | Outputs | Error Behavior | Discovered Via |
|---|----------|---------|-------------|--------|---------|----------------|----------------|
| 1 | Auth | Login Screen | Phone number login with OTP request, language selector, and value proposition cards | 10-digit mobile number, language selection | OTP dispatched state, navigate to Create Account or Home | Invalid number format validation error | `login page.png` |
| 2 | Auth / Profile | Create Account / User Settings | Multi-step registration form: name, email, phone, DOB, category, pin, education | User profile inputs, avatar photo picker | Validated profile state, navigate to Main Dashboard | Required field validation alerts | `User Setting.png` |
| 3 | Navigation | Main App Shell & Scaffold | App bar with exam switcher, search, avatar; 4-tab bottom navigation bar | Tab selection, exam dropdown change | Active tab view switch, exam context update | Default to "SSC CGL" if unselected | `basic pages and buttons.png` |
| 4 | Navigation | Navigation Drawer | Left slide-out drawer with user profile header and primary navigation links | Hamburger icon tap | Drawer expands with Home, Pass, Tests, Notes, Exams, Updates | Closes on backdrop tap or back press | `3 line pe dabane pr ye aata hai.png` |
| 5 | Home | Dashboard Carousel & Quick Access | Hero Pass banner, 2-column feature category cards, Current Affairs, AI Assistant FAB | Card click, FAB tap | Navigate to relevant section/module | Fallback to default category list | `home tab 1.png` |
| 6 | Tests | Tests Catalog & Enrolled Series | Top banner carousel, Enrolled Test Series card with progress bar, circular shortcuts | Banner tap, enrolled card tap | Open Test Series Detail Screen | Empty state if no tests enrolled | `tests tab.png` |
| 7 | Tests | Test Series Detail (Mock Tests) | Detail screen with 3 metric counters (Total, Attempted, Progress), banner, folder list | Folder item click, Unlock CTA tap | Navigate to Test Listing or Pass checkout | Shows locked state if pass not active | `enrolled test.png` |
| 8 | Tests | Test Series Detail (PYPs Tab) | Previous Year Papers tab with paper counts, solved stats, exam level folders | Tab switch to PYPs, folder click | Navigate to PYP test listing | Shows locked indicator if un-enrolled | `enrolled test 2.png` |
| 9 | Tests | Test Category Listing | List of practice tests with FREE badges, duration, marks, language, Share link | "Start Test" tap, Share tap | Launches Pre-Test Instructions Screen | Prompts to unlock if test is locked | `test look.jpeg` |
| 10 | Tests | Test Listing with Attempt History | Displays Suggested Next Test vs Previously Attempted tests with score and rank | "Start Test" or "View Results" tap | Launches Test or Result Analysis Screen | Graceful fallback if rank not calculated | `test view after result.png` |
| 11 | Test Taking | Pre-Test Instructions Screen | Comprehensive rules, marking scheme (+2.0/-0.5), duration, declaration | Language selector, "Agree and Continue" | Launches Active Test Taking Screen | Requires language confirmation | `starting test.jpeg` |
| 12 | Test Taking | Language Selection Sheet | Modal bottom sheet to switch between English and Hindi | Language selection tap | Updates active test language immediately | Defaults to English | `language selection.jpeg` |
| 13 | Test Taking | Test Symbols & Legend Guide | Table explaining all 8 palette colors, tags, and action button meanings | Tap `? Symbols` in palette drawer | Displays visual guide modal | Closes on "Back to Test" tap | `symbol meaning.jpeg`, `symbol meaning 2.jpeg` |
| 14 | Test Taking | Active Test Question Screen | Question display with passage, timer countdown, question timer, bookmark, options | Option selection, "Save & Next", "Mark For Review" | Updates question status, navigates to next Q | Warns on pause or section time-up | `on test options.jpeg`, `qs on test timer.jpeg` |
| 15 | Test Taking | Question Palette Drawer | Grid of 25 question number chips with color states, Section Part tabs, Submit buttons | Question chip tap, "SUBMIT SECTION", "SUBMIT TEST" | Jumps directly to selected Q or submits test | Prompts confirmation before submission | `on test summary.jpeg` |
| 16 | Results | Test Result Analysis Tab | Scorecard with Rank, Score, Percentile, Accuracy, Correct/Incorrect pills, Social card | Category filter dropdown, "Challenge" share button | Displays detailed performance metrics | Zero division guard for 0 attempts | `result analysis.jpeg` |
| 17 | Results | Solutions List Tab | List of questions with accuracy percentage, time taken, bookmark, "Sections" pill | Question card tap, "Sections" tap | Opens Question Review or Section Drawer | Filter toggle for All vs Unattempted | `solutions.jpeg` |
| 18 | Results | Solutions Section Drawer | Slide-over drawer listing all 4 sections with breakdown counters (saved, correct, wrong, skipped) | Section item tap | Filters solution list to selected section | Closes on backdrop tap | `test attempt summary after test.jpeg` |
| 19 | Results | Question Review (Reattempt Mode) | Interactive review screen with Reattempt toggle, question carousel strip, filter button | Toggle Reattempt Mode, select option | Hides solution when ON, reveals answer when OFF | "View Solution" disabled in Reattempt ON | `qs look after result.jpeg`, `qs look after test 2.jpeg` |
| 20 | Results | Leaderboard Screen | Top 3 rankers podium (Gold, Silver, Bronze), ranks 4-9 list, user sticky bottom rank card | Scroll list, view profile | Ranks updated dynamically | Displays "(You)" banner at bottom | `leaderboard.jpeg` |
| 21 | Pass | Pass Subscription Screen | "Now ABHYAS is FREE" hero banner, 6 benefit cards, coupon section, green CTA button | "Get ABHYAS Pass", "Apply Coupon" | Unlocks full app test series and notes | Handles coupon error codes | `pass.png` |
| 22 | Updates | Updates & Notifications Tab | Hero banner, horizontal filter chips (All, Admit Card, Results, Syllabus), update cards | Filter chip tap, "Download PDF", "Notify Me" | Downloads exam PDF or sets notification | Shows loading/error toast on download failure | `updates section.png` |
| 23 | Profile | Preparation Analytics Dashboard | Daily greeting, mascot illustration, Historical line chart, Avg score, Study time | Tab switch (Accuracy, Time, Questions) | Updates line chart and summary metrics | Handles empty chart data gracefully | `photo click pr ye aaega.png` |
| 24 | Policy | Privacy Policy Accordion | Expandable/collapsible card detailing user data security and terms | Chevron tap | Expands or collapses text card | Preserves scroll state | `privacy policy.jpeg` |
| 25 | Promo | Exam Promotional Banners | Multilingual (English/Hindi) promotional banners for MP Nayab Tehsildar 2026 | Tap banner CTA | Direct deep link to Nayab Tehsildar Test Series | Fallback to Tests Tab | `banner 1.png`, `banner 2.png` |

---

### Edge Cases

| # | Feature | Input | Observed Behavior |
|---|---------|-------|-------------------|
| 1 | Active Test Timer | Countdown reaches 00:00:00 | Active test auto-submits current section and prompts submission of entire exam. |
| 2 | Question Palette | Question answered AND marked for review | Palette displays amber tag `15` with bottom pointer `▲`, treated as answered in evaluation. |
| 3 | Question Palette | Question marked for review WITHOUT answering | Palette displays coral red tag `14` with bottom pointer `▲`, treated as unattempted in evaluation. |
| 4 | Question Review | Reattempt Mode is turned ON | "View Solution" button is disabled; options do not show green/red colors; user can re-try answering. |
| 5 | Question Review | Reattempt Mode is turned OFF | Correct option is outlined in green with checkmark `✓`; step-by-step solution text and stats appear. |
| 6 | Test Series PYPs Tab | User toggles between "Mock Tests" and "PYPs" | Header metrics dynamically swap from Total Tests (610) to Total Papers (240). |
| 7 | Updates Tab | User selects specific filter chip (e.g. "Admit Card") | Updates list filters to show only cards matching the selected tag with relevant action button. |
| 8 | Leaderboard Tab | User is ranked outside top 10 (e.g., Rank 22789) | Sticky periwinkle blue bottom card remains fixed displaying user's rank, avatar, and score. |
| 9 | Login Screen | User opts out of phone number login | Tapping "USE ANOTHER METHOD" triggers alternative authentication sheet / options. |
| 10 | Profile Analytics | User clicks tabs (Accuracy vs Time Spent vs Questions) | Line chart updates Y-axis metrics and point annotations to reflect selected performance dimension. |

---

## 5. Verification Method

1. **Visual Confirmation Against Original Assets**:
   - Inspect files in `C:\Users\gurut\Downloads\ABHYAAS App\UI UX` using `view_file` to confirm fidelity of all components, texts, and colors against this report.
2. **Layout & Color Consistency**:
   - Verify that all primary colors (`#2563EB`, `#00C2FF`, `#10B981`, `#0B111A`, `#161F2E`) and typography styles documented in Section 1 and Section 4 match the Android Jetpack Compose theme configuration.
3. **Navigation Graph Connectivity**:
   - Verify that the Jetpack Compose `NavHost` covers all 25 discovered features and enables end-to-end user journeys (Onboarding -> Home -> Test Series -> Pre-test Instructions -> Test Taking -> Question Palette -> Result Analysis -> Solution Review -> Leaderboard -> Profile).
4. **Compilation Verification**:
   - Run `./gradlew assembleDebug` in `C:\Users\gurut\AndroidStudioProjects\Abhyaas` to ensure zero compilation or syntax errors.
