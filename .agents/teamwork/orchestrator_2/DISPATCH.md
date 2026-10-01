# Dispatch Instructions

## 2026-09-30T14:47:47Z
You are the Project Orchestrator for the Abhyaas project.
Your working directory is: C:\Users\gurut\AndroidStudioProjects\Abhyaas\.agents\teamwork\orchestrator_2
Your identity is: orchestrator_2

Authoritative user request: C:\Users\gurut\AndroidStudioProjects\Abhyaas\.agents\teamwork\ORIGINAL_REQUEST.md (specifically the latest request under ## 2026-09-30T14:46:28Z).

Project details:
Project Root: C:\Users\gurut\AndroidStudioProjects\Abhyaas
Integrity mode: development

Requirements to orchestrate:
### R1. Complete the Data Architecture
Finish implementing the MVVM + Retrofit layer for the app. This includes defining all necessary DTOs, Retrofit ApiService interfaces, Repository implementations (mock/remote fallback), and ViewModels using StateFlow to hold UI states.

### R2. Wire the UI for Dynamic Data
Refactor all main Compose UI screens (Home, Tests, Test Series Detail, Updates, Profile, Active Test) to consume data from their respective ViewModels. Remove all hardcoded, static UI lists (like the test folders, exams, quick actions, and updates) and replace them with dynamic iterations over the ViewModel state.

## Acceptance Criteria
### Compilation & Execution
- The app successfully compiles and builds via `.\gradlew assembleDebug` without errors.

### Architectural Verification (Agent-as-Judge)
- An independent reviewing agent confirms that the main UI screens (Home, Tests, Test Series Detail, Updates, Profile) are observing state using `collectAsStateWithLifecycle()` or similar state observation mechanisms.
- An independent reviewing agent confirms that static placeholder lists in the UI (e.g., test folders in the Test Series Detail screen) have been replaced with iterations over the data models provided by the ViewModels.
