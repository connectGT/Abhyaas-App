# BRIEFING — 2026-09-30T15:43:00Z

## Mission
Analyze all ViewModels in `app/src/main/java/com/example/abhyaas/ui/viewmodel/` and `RemoteExamRepositoryImpl.kt` / `ExamRepository.kt` to design remedies for JVM unit test crash resilience (constructor injection with safe fallbacks) and missing Retrofit network call in `RemoteExamRepositoryImpl.getHomeCategories()`.

## 🔒 My Identity
- Archetype: explorer
- Roles: investigation, synthesis
- Working directory: C:\Users\gurut\AndroidStudioProjects\Abhyaas\.agents\teamwork\explorer_remedy_viewmodels
- Original parent: 775ae211-11a2-4a96-8b5f-1cf13bac3000
- Milestone: Remediation Planning for ViewModels & Repository

## 🔒 Key Constraints
- Read-only investigation — do NOT implement
- Do NOT modify source code
- File workspace convention: Write ONLY to `C:\Users\gurut\AndroidStudioProjects\Abhyaas\.agents\teamwork\explorer_remedy_viewmodels`

## Current Parent
- Conversation ID: 775ae211-11a2-4a96-8b5f-1cf13bac3000
- Updated: 2026-09-30T15:36:10Z

## Investigation State
- **Explored paths**:
  - `app/src/main/java/com/example/abhyaas/ui/viewmodel/` (all 8 ViewModels)
  - `app/src/main/java/com/example/abhyaas/data/repository/ExamRepository.kt`
  - `app/src/main/java/com/example/abhyaas/data/repository/impl/RemoteExamRepositoryImpl.kt`
  - `app/src/main/java/com/example/abhyaas/data/repository/impl/MockExamRepositoryImpl.kt`
  - `app/src/main/java/com/example/abhyaas/data/network/ApiService.kt`
  - `app/src/main/java/com/example/abhyaas/data/network/dto/Dtos.kt`
  - `app/src/main/java/com/example/abhyaas/AbhyaasApplication.kt`
  - `app/src/main/java/com/example/abhyaas/ui/screens/`
  - `app/src/test/java/com/example/abhyaas/verification/EmpiricalDataArchitectureTest.kt`
- **Key findings**:
  - Root cause of JVM unit test crashes is uninitialized `AbhyaasApplication.instance`.
  - Adding `@JvmOverloads constructor(...)` with safe try/catch default parameter fallbacks to `Mock*RepositoryImpl()` resolves crashes while retaining Compose `viewModel()` no-arg compatibility.
  - Making `ExamRepository.getHomeCategories()` `suspend` enables calling `api.getHomeCategories()` with mock fallback and domain mapping in `RemoteExamRepositoryImpl.kt` with zero disruption to existing call sites.
- **Unexplored areas**: None within the assigned mission scope.

## Key Decisions Made
- Use `@JvmOverloads constructor(...)` on all 8 ViewModels to support both parameterized testing and no-arg Compose reflection.
- Default to `Mock*RepositoryImpl()` in fallback catch blocks to provide immediate, network-free test execution.
- Recommend `suspend fun getHomeCategories(): List<HomeCategoryItem>` to maintain 100% call-site compatibility.

## Artifact Index
- DISPATCH.md — incoming instructions
- BRIEFING.md — persistent working memory
- progress.md — heartbeat
- report.md — detailed technical remediation specifications
- handoff.md — 5-component handoff report
