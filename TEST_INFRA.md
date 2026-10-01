# Test Infrastructure Specification: Abhyaas Android Jetpack Compose UI

## 1. Testing Philosophy
The testing infrastructure for **Abhyaas** adheres to an **opaque-box, requirement-driven, and specification-anchored** methodology derived directly from `ORIGINAL_REQUEST.md` and `PROJECT.md`.

### 1.1 Core Principles
1. **Opaque-Box Verification**: Tests validate behavioral contracts, state machines, domain invariants, navigation graph topology, and score calculations without coupling to internal UI rendering mechanics.
2. **Authoritative Specification Anchoring**: Every expected value is derived directly from the authoritative UI/UX mockups (`Downloads/ABHYAAS App/UI UX`), raw exam content (`Downloads/ABHYAAS App/Test`), and interface contracts established in `PROJECT.md`.
3. **Deterministic & Isolated Execution**: Tests run in-memory without external network dependencies, device emulator requirements, or non-deterministic delays. Time-based operations (timers, countdowns) utilize deterministic clocks or virtual ticks.
4. **Progressive Testability & Regression Defense**: Tests are categorized into 4 tiers ensuring that base domain primitives, boundary safety, cross-feature state flows, and full end-to-end exam simulations can be independently executed and verified.

---

## 2. Test Architecture & Directory Structure

All test suites reside in `app/src/test/java/com/example/abhyaas/` executed via the local JVM unit test runner:

```text
app/src/test/java/com/example/abhyaas/
├── ExampleUnitTest.kt
├── contract/
│   ├── DomainContract.kt                # Pure domain data classes, enums, & contracts
│   ├── NavigationContract.kt            # Navigation routes, arguments, & backstack policy
│   ├── ExamEngineContract.kt            # State machine contracts (Timer, Palette, Scoring)
│   └── MockTestRepositories.kt          # Deterministic test repositories (Questions, Tests, User, Updates)
├── tier1_coverage/
│   ├── DomainModelCoverageTest.kt       # Tier 1: Question, Option, Test, UserProfile, LeaderboardEntry
│   ├── MockRepositoryCoverageTest.kt    # Tier 1: Repository queries, category filtering, questions lookup
│   ├── NavigationContractTest.kt        # Tier 1: Route formatting, route parsing, Screen enums
│   └── TestInstructionsCoverageTest.kt  # Tier 1: 7 default exam rules, marking scheme, declaration
├── tier2_boundary/
│   ├── TimerBoundaryTest.kt             # Tier 2: 00:00:00 expiry, pause/resume, 15-min warning threshold
│   ├── ScoringBoundaryTest.kt           # Tier 2: 0 attempts, perfect 200, negative score (-50), fractional marks
│   ├── ReattemptModeBoundaryTest.kt     # Tier 2: Reattempt toggle (ON=hide answer, OFF=reveal explanation)
│   └── ValidationBoundaryTest.kt        # Tier 2: Empty questions, null direction/Hindi text, boundary lengths
├── tier3_interaction/
│   ├── QuestionStateMachineTest.kt      # Tier 3: Visited -> Unanswered -> Answered -> Marked state transitions
│   ├── SectionNavigationTest.kt         # Tier 3: Switching sections (A/B/C/D), question pointer jump, state preservation
│   └── ExamSubmissionBackstackTest.kt   # Tier 3: Submit dialog confirmation, popUpTo inclusive backstack clearance
└── tier4_scenarios/
    ├── FullExamSimulationTest.kt        # Tier 4: 100-question end-to-end exam simulation with section breakdowns
    ├── LeaderboardRankingScenarioTest.kt# Tier 4: Ranker podium (Top 3) vs user sticky card in 24,964 candidates
    └── BilingualSwitchingScenarioTest.kt# Tier 4: English/Hindi toggle across statement, direction, options, solution
```

---

## 3. Feature Inventory Test Matrix (Tiers 1 - 4)

| Feature # | Feature Name | Tier 1 (Coverage) | Tier 2 (Boundary) | Tier 3 (Interaction) | Tier 4 (Workload Scenario) |
|---|---|---|---|---|---|
| F1 | Brand Theme & Design System | Color tokens, Contrast | Edge colors | Dynamic theme state | - |
| F2 | Reusable UI Components | TimerChip, StatCard specs | Boundary format values | Click & selection callbacks | Composite card layout |
| F3 | Domain Models & Mock Repos | `DomainModelCoverageTest` | `ValidationBoundaryTest` | Repository state update | `FullExamSimulationTest` |
| F4 | Navigation Architecture | `NavigationContractTest` | Malformed route args | `ExamSubmissionBackstackTest` | Full journey backstack trace |
| F5 | Authentication / Login | Phone number model | 10-digit boundary, null | Step navigation to Settings | Mock OTP verification |
| F6 | User Registration / Settings | UserProfile data fields | Empty name/email/category | Step 1 to 3 progression | Account creation flow |
| F7 | Side Navigation Drawer | Drawer route items | Unselected item handling | NavHost destination switch | Profile link transition |
| F8 | Home Dashboard | CategoryGridCard items | Zero notifications | Pass banner CTA click | Exam switch dropdown |
| F9 | Tests Catalog Tab | Series summary metadata | 0 enrolled tests | View Series button navigation | Tab state restoration |
| F10 | Abhyaas Pass Tab | Feature cards, coupon box | Expired coupon handling | Get Pass action callback | Pass activation scenario |
| F11 | Exam Updates Tab | `MockUpdatesRepository` | Empty updates category | Category filter chips | Pinned PDF download action |
| F12 | User Profile & Prep Dash | StatCard models, trend points | 0 tests attempted stats | Accuracy / Time / Qs tabs | Chart data aggregation |
| F13 | Privacy Policy Screen | Legal text contract | Collapsed/expanded state | Scroll / dismiss callback | Full terms read-through |
| F14 | Test Series Detail (Mocks) | 610 tests metadata | 0% progress calculation | Unlock Series CTA | Folder grouping validation |
| F15 | Test Series Detail (PYPs) | 240 papers metadata | 0 solved boundary | Tab switch Mocks <-> PYPs | Category paper listing |
| F16 | Test Category Listing | Free badge, test cards | Empty subcategory list | Start Test button click | Series -> Test list nav |
| F17 | Test List Attempt History | Attempt card score/rank | 0/200 marks display | View Results navigation | Suggested Next vs Previous |
| F18 | Pre-Test Instructions | `TestInstructionsCoverageTest` | Unchecked declaration | Agree & Continue activation | Language selector change |
| F19 | Language Selection Sheet | English & Hindi options | Unsupported language fallback | Bilingual toggle state | Modal dismiss / selection |
| F20 | Active Test Taking Screen | Question card layout | Single vs 4 options | `QuestionStateMachineTest` | 60-min live test engine |
| F21 | Question Palette Drawer | 25-chip grid layout | Last question (Q25/Q100) | `SectionNavigationTest` | Direct question jump |
| F22 | Question Status Guide | 8 status color symbols | Unvisited vs Unanswered | Guide dialog open/close | Legend inspection |
| F23 | Exam Submission Confirm | Question counts summary | 0 answered confirm | `ExamSubmissionBackstackTest` | Auto-submit on 00:00:00 |
| F24 | Result Scorecard (Analysis) | Score, Rank, Percentile | `ScoringBoundaryTest` | WhatsApp challenge share | `LeaderboardRankingScenarioTest` |
| F25 | Solutions List & Drawer | Solution filter chips | 0 correct / 0 incorrect | Filter chip click (All/Unatt) | Section drawer breakdown |
| F26 | Question Solution Review | Solution explanation text | `ReattemptModeBoundaryTest` | Reattempt toggle ON/OFF | Step-by-step reasoning view |
| F27 | Leaderboard & Ranking | Top 3 podium, user card | Lowest rank boundary | Sticky ranker card alignment | 24,964 candidates ranking |
| F28 | Full E2E Clickable Flow | Route connectivity | Error boundary fallback | Navigation backstack popUpTo | `FullExamSimulationTest` |

---

## 4. Verification Procedures & CLI Commands

### 4.1 Running All Unit & Behavioral Tests
```powershell
./gradlew.bat testDebugUnitTest
```

### 4.2 Running Specific Test Tiers
```powershell
# Tier 1: Feature Coverage
./gradlew.bat testDebugUnitTest --tests "com.example.abhyaas.tier1_coverage.*"

# Tier 2: Boundary & Corner Cases
./gradlew.bat testDebugUnitTest --tests "com.example.abhyaas.tier2_boundary.*"

# Tier 3: Cross-Feature Interactions
./gradlew.bat testDebugUnitTest --tests "com.example.abhyaas.tier3_interaction.*"

# Tier 4: Real-World Workload Scenarios
./gradlew.bat testDebugUnitTest --tests "com.example.abhyaas.tier4_scenarios.*"
```

### 4.3 Output Artifacts
Test execution results are generated at:
`app/build/reports/tests/testDebugUnitTest/index.html`
