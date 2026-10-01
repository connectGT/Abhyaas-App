## 2026-09-30T15:36:10Z
[Message] timestamp=2026-09-30T15:36:10Z sender=775ae211-11a2-4a96-8b5f-1cf13bac3000 priority=MESSAGE_PRIORITY_HIGH content=You are explorer_remedy_viewmodels.
Working directory: C:\Users\gurut\AndroidStudioProjects\Abhyaas\.agents\teamwork\explorer_remedy_viewmodels
Your identity is: explorer_remedy_viewmodels

MANDATORY: Read C:\Users\gurut\AndroidStudioProjects\Abhyaas\.agents\teamwork\ORIGINAL_REQUEST.md before starting work.
Read C:\Users\gurut\AndroidStudioProjects\Abhyaas\.agents\teamwork\orchestrator_2\GATE_STATUS.md and challenger_dyn_1 handoff.md.

Failure Context:
1. ViewModels hardcode `AbhyaasApplication.instance.<repository>` directly, which causes NullPointerExceptions if instantiated in isolated JVM unit tests without an Android Application context.
2. `RemoteExamRepositoryImpl.getHomeCategories()` does not call Retrofit `api.getHomeCategories()`.

Your Mission:
1. Inspect all ViewModels in `app/src/main/java/com/example/abhyaas/ui/viewmodel/`:
   Specify how constructor injection can be supported with safe defaults:
   e.g. `class HomeViewModel(private val repository: ExamRepository = try { AbhyaasApplication.instance.examRepository } catch (e: Exception) { MockExamRepository }) : ViewModel()`
2. Inspect `ExamRepository.kt` and `RemoteExamRepositoryImpl.kt`:
   Specify how `getHomeCategories()` can call `api.getHomeCategories()` with fallback to `MockExamRepository.getHomeCategories()`.

Write report to `report.md` and `handoff.md`.
Do NOT modify source code. You are read-only.
Notify orchestrator_2 when done.
