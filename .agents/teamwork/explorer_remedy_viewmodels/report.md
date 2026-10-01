# Technical Remediation Specification: ViewModels & Repository

**Agent:** `explorer_remedy_viewmodels`  
**Working Directory:** `C:\Users\gurut\AndroidStudioProjects\Abhyaas\.agents\teamwork\explorer_remedy_viewmodels`  
**Date:** 2026-09-30  
**Status:** Read-Only Investigation & Remediation Design Complete  

---

## Executive Summary

This report delivers precise, production-ready remediation specifications for two critical architectural issues identified during Milestone C verification:
1. **ViewModel Unit Test Crashes / NullPointerExceptions**: All 8 ViewModels hardcode direct singleton property access (`AbhyaasApplication.instance.<repository>`). In headless JVM unit tests where the Android `Application` class is uninitialized, this triggers `kotlin.UninitializedPropertyAccessException`, preventing unit testing outside instrumented emulator environments.
2. **Bypassed Network Endpoint in `RemoteExamRepositoryImpl.getHomeCategories()`**: `RemoteExamRepositoryImpl.getHomeCategories()` does not call Retrofit `api.getHomeCategories()`, but directly delegates to `MockExamRepository.getHomeCategories()` on the main thread because the interface method was declared non-suspending.

---

## 1. Root Cause Analysis

### 1.1 ViewModel Hardcoded Singleton Access
In `AbhyaasApplication.kt`:
```kotlin
companion object {
    lateinit var instance: AbhyaasApplication
        private set
}
override fun onCreate() {
    super.onCreate()
    instance = this
}
```
In headless JVM unit tests (e.g. running under `./gradlew testDebugUnitTest`), `AbhyaasApplication.onCreate()` is never invoked. Any ViewModel field initialized as:
```kotlin
private val examRepo = AbhyaasApplication.instance.examRepository
```
immediately fails during class instantiation with:
`kotlin.UninitializedPropertyAccessException: lateinit property instance has not been initialized`

### 1.2 Bypassed Retrofit Endpoint in `RemoteExamRepositoryImpl`
In `ApiService.kt` (lines 26-27):
```kotlin
@GET("home/categories")
suspend fun getHomeCategories(): Response<List<HomeCategoryDto>>
```
In `ExamRepository.kt` (line 10):
```kotlin
fun getHomeCategories(): List<HomeCategoryItem>
```
Because `getHomeCategories()` in `ExamRepository.kt` is non-suspending, `RemoteExamRepositoryImpl` could not directly invoke the suspending Retrofit method without blocking the calling thread or launching an unconfined coroutine. The implementation took a shortcut:
```kotlin
override fun getHomeCategories(): List<HomeCategoryItem> = MockRepo.getHomeCategories()
```
This violates the dynamic architecture mandate requiring genuine Retrofit network calls with graceful mock degradation.

---

## 2. ViewModel Constructor Injection Remediation

To preserve 100% backward compatibility with Compose screens calling `viewModel<XViewModel>()` while enabling isolated unit testing and mock injection:
1. Use `@JvmOverloads constructor(...)` so Kotlin compiles a parameterless default constructor (`public XViewModel()`) in Java bytecode for `ViewModelProvider.NewInstanceFactory`.
2. Provide default constructor arguments that attempt retrieval from `AbhyaasApplication.instance.<repository>`, catching `Throwable`/`Exception` to fall back to `Mock<Type>RepositoryImpl()`.
3. Support parameter injection in unit tests: `XViewModel(examRepo = mockExamRepo)`.

### 2.1 Complete Specifications for All 8 ViewModels

#### 1. `HomeViewModel.kt`
- **File**: `app/src/main/java/com/example/abhyaas/ui/viewmodel/HomeViewModel.kt`
- **Imports Required**:
  ```kotlin
  import com.example.abhyaas.data.repository.ExamRepository
  import com.example.abhyaas.data.repository.UserRepository
  import com.example.abhyaas.data.repository.impl.MockExamRepositoryImpl
  import com.example.abhyaas.data.repository.impl.MockUserRepositoryImpl
  ```
- **Before**:
  ```kotlin
  class HomeViewModel : ViewModel() {
      private val examRepo = AbhyaasApplication.instance.examRepository
      private val userRepo = AbhyaasApplication.instance.userRepository
  ```
- **After**:
  ```kotlin
  class HomeViewModel @JvmOverloads constructor(
      private val examRepo: ExamRepository = try {
          AbhyaasApplication.instance.examRepository
      } catch (e: Exception) {
          MockExamRepositoryImpl()
      },
      private val userRepo: UserRepository = try {
          AbhyaasApplication.instance.userRepository
      } catch (e: Exception) {
          MockUserRepositoryImpl()
      }
  ) : ViewModel() {
  ```

#### 2. `ActiveTestViewModel.kt`
- **File**: `app/src/main/java/com/example/abhyaas/ui/viewmodel/ActiveTestViewModel.kt`
- **Imports Required**:
  ```kotlin
  import com.example.abhyaas.data.repository.ExamRepository
  import com.example.abhyaas.data.repository.QuestionRepository
  import com.example.abhyaas.data.repository.TestResultRepository
  import com.example.abhyaas.data.repository.impl.MockExamRepositoryImpl
  import com.example.abhyaas.data.repository.impl.MockQuestionRepositoryImpl
  import com.example.abhyaas.data.repository.impl.MockTestResultRepositoryImpl
  ```
- **Before**:
  ```kotlin
  class ActiveTestViewModel : ViewModel() {
      private val examRepo = AbhyaasApplication.instance.examRepository
      private val questionRepo = AbhyaasApplication.instance.questionRepository
      private val resultRepo = AbhyaasApplication.instance.testResultRepository
  ```
- **After**:
  ```kotlin
  class ActiveTestViewModel @JvmOverloads constructor(
      private val examRepo: ExamRepository = try {
          AbhyaasApplication.instance.examRepository
      } catch (e: Exception) {
          MockExamRepositoryImpl()
      },
      private val questionRepo: QuestionRepository = try {
          AbhyaasApplication.instance.questionRepository
      } catch (e: Exception) {
          MockQuestionRepositoryImpl()
      },
      private val resultRepo: TestResultRepository = try {
          AbhyaasApplication.instance.testResultRepository
      } catch (e: Exception) {
          MockTestResultRepositoryImpl()
      }
  ) : ViewModel() {
  ```

#### 3. `TestListViewModel.kt`
- **File**: `app/src/main/java/com/example/abhyaas/ui/viewmodel/TestListViewModel.kt`
- **Imports Required**:
  ```kotlin
  import com.example.abhyaas.data.repository.ExamRepository
  import com.example.abhyaas.data.repository.impl.MockExamRepositoryImpl
  ```
- **Before**:
  ```kotlin
  class TestListViewModel : ViewModel() {
      private val examRepo = AbhyaasApplication.instance.examRepository
  ```
- **After**:
  ```kotlin
  class TestListViewModel @JvmOverloads constructor(
      private val examRepo: ExamRepository = try {
          AbhyaasApplication.instance.examRepository
      } catch (e: Exception) {
          MockExamRepositoryImpl()
      }
  ) : ViewModel() {
  ```

#### 4. `TestResultViewModel.kt`
- **File**: `app/src/main/java/com/example/abhyaas/ui/viewmodel/TestResultViewModel.kt`
- **Imports Required**:
  ```kotlin
  import com.example.abhyaas.data.repository.ExamRepository
  import com.example.abhyaas.data.repository.TestResultRepository
  import com.example.abhyaas.data.repository.impl.MockExamRepositoryImpl
  import com.example.abhyaas.data.repository.impl.MockTestResultRepositoryImpl
  ```
- **Before**:
  ```kotlin
  class TestResultViewModel : ViewModel() {
      private val examRepo = AbhyaasApplication.instance.examRepository
      private val resultRepo = AbhyaasApplication.instance.testResultRepository
  ```
- **After**:
  ```kotlin
  class TestResultViewModel @JvmOverloads constructor(
      private val examRepo: ExamRepository = try {
          AbhyaasApplication.instance.examRepository
      } catch (e: Exception) {
          MockExamRepositoryImpl()
      },
      private val resultRepo: TestResultRepository = try {
          AbhyaasApplication.instance.testResultRepository
      } catch (e: Exception) {
          MockTestResultRepositoryImpl()
      }
  ) : ViewModel() {
  ```

#### 5. `TestSeriesDetailViewModel.kt`
- **File**: `app/src/main/java/com/example/abhyaas/ui/viewmodel/TestSeriesDetailViewModel.kt`
- **Imports Required**:
  ```kotlin
  import com.example.abhyaas.data.repository.ExamRepository
  import com.example.abhyaas.data.repository.impl.MockExamRepositoryImpl
  ```
- **Before**:
  ```kotlin
  class TestSeriesDetailViewModel : ViewModel() {
      private val examRepo = AbhyaasApplication.instance.examRepository
  ```
- **After**:
  ```kotlin
  class TestSeriesDetailViewModel @JvmOverloads constructor(
      private val examRepo: ExamRepository = try {
          AbhyaasApplication.instance.examRepository
      } catch (e: Exception) {
          MockExamRepositoryImpl()
      }
  ) : ViewModel() {
  ```

#### 6. `TestsViewModel.kt`
- **File**: `app/src/main/java/com/example/abhyaas/ui/viewmodel/TestsViewModel.kt`
- **Imports Required**:
  ```kotlin
  import com.example.abhyaas.data.repository.ExamRepository
  import com.example.abhyaas.data.repository.impl.MockExamRepositoryImpl
  ```
- **Before**:
  ```kotlin
  class TestsViewModel : ViewModel() {
      private val examRepo = AbhyaasApplication.instance.examRepository
  ```
- **After**:
  ```kotlin
  class TestsViewModel @JvmOverloads constructor(
      private val examRepo: ExamRepository = try {
          AbhyaasApplication.instance.examRepository
      } catch (e: Exception) {
          MockExamRepositoryImpl()
      }
  ) : ViewModel() {
  ```

#### 7. `UpdatesViewModel.kt`
- **File**: `app/src/main/java/com/example/abhyaas/ui/viewmodel/UpdatesViewModel.kt`
- **Imports Required**:
  ```kotlin
  import com.example.abhyaas.data.repository.UpdatesRepository
  import com.example.abhyaas.data.repository.impl.MockUpdatesRepositoryImpl
  ```
- **Before**:
  ```kotlin
  class UpdatesViewModel : ViewModel() {
      private val updatesRepo = AbhyaasApplication.instance.updatesRepository
  ```
- **After**:
  ```kotlin
  class UpdatesViewModel @JvmOverloads constructor(
      private val updatesRepo: UpdatesRepository = try {
          AbhyaasApplication.instance.updatesRepository
      } catch (e: Exception) {
          MockUpdatesRepositoryImpl()
      }
  ) : ViewModel() {
  ```

#### 8. `UserProfileViewModel.kt`
- **File**: `app/src/main/java/com/example/abhyaas/ui/viewmodel/UserProfileViewModel.kt`
- **Imports Required**:
  ```kotlin
  import com.example.abhyaas.data.repository.UserRepository
  import com.example.abhyaas.data.repository.impl.MockUserRepositoryImpl
  ```
- **Before**:
  ```kotlin
  class UserProfileViewModel : ViewModel() {
      private val userRepo = AbhyaasApplication.instance.userRepository
  ```
- **After**:
  ```kotlin
  class UserProfileViewModel @JvmOverloads constructor(
      private val userRepo: UserRepository = try {
          AbhyaasApplication.instance.userRepository
      } catch (e: Exception) {
          MockUserRepositoryImpl()
      }
  ) : ViewModel() {
  ```

---

## 3. Repository Network Integration: `getHomeCategories()`

### 3.1 Interface Specification (`ExamRepository.kt`)
Update line 10 in `app/src/main/java/com/example/abhyaas/data/repository/ExamRepository.kt` to mark the function as suspending:
```kotlin
package com.example.abhyaas.data.repository

import com.example.abhyaas.data.model.*

interface ExamRepository {
    suspend fun getTestSeriesList(): Result<List<TestSeries>>
    suspend fun getTestSeriesById(id: String): Result<TestSeries?>
    suspend fun getTestsForSubCategory(seriesId: String, subCategory: String): Result<List<Test>>
    suspend fun getTestById(testId: String): Result<Test?>
    suspend fun getHomeCategories(): List<HomeCategoryItem>
}
```

### 3.2 Network Implementation with Mock Fallback (`RemoteExamRepositoryImpl.kt`)
Replace line 66 of `app/src/main/java/com/example/abhyaas/data/repository/impl/RemoteExamRepositoryImpl.kt`:
```kotlin
    override suspend fun getHomeCategories(): List<HomeCategoryItem> = try {
        val response = api.getHomeCategories()
        if (response.isSuccessful && !response.body().isNullOrEmpty()) {
            response.body()!!.map { it.toDomain() }
        } else {
            MockRepo.getHomeCategories()
        }
    } catch (_: Exception) {
        MockRepo.getHomeCategories()
    }
```
*(Note: `HomeCategoryDto.toDomain()` is already implemented at lines 228–236 in `Dtos.kt` and maps `title`, `subtitle`, `iconName`, `badge`, `gradientStartColorHex`, and `gradientEndColorHex`).*

### 3.3 Mock Repository Implementation (`MockExamRepositoryImpl.kt`)
Update lines 29–31 in `app/src/main/java/com/example/abhyaas/data/repository/impl/MockExamRepositoryImpl.kt`:
```kotlin
    override suspend fun getHomeCategories(): List<HomeCategoryItem> {
        return MockExamRepository.getHomeCategories()
    }
```

### 3.4 Verification of Call Sites
1. **`HomeViewModel.kt`**:
   Line 38 calls `val categories = examRepo.getHomeCategories()` inside `viewModelScope.launch { ... }`. Because it is already in a coroutine block, no modification to `HomeViewModel` calling code is required.
2. **`EmpiricalDataArchitectureTest.kt`**:
   Line 58 calls `val homeCategories = repo.getHomeCategories()` inside `@Test fun testRemoteExamRepository_DegradesGracefullyToMockData() = runBlocking { ... }`. Because it is already in a `runBlocking` coroutine block, no modification is needed.

### 3.5 Alternative: `Result<List<HomeCategoryItem>>`
If project conventions mandate that all repository methods return `Result<T>`:
```kotlin
// ExamRepository.kt:
suspend fun getHomeCategories(): Result<List<HomeCategoryItem>>

// RemoteExamRepositoryImpl.kt:
override suspend fun getHomeCategories(): Result<List<HomeCategoryItem>> = runCatching {
    try {
        val response = api.getHomeCategories()
        if (response.isSuccessful && !response.body().isNullOrEmpty()) {
            response.body()!!.map { it.toDomain() }
        } else {
            MockRepo.getHomeCategories()
        }
    } catch (_: Exception) {
        MockRepo.getHomeCategories()
    }
}.recover { MockRepo.getHomeCategories() }

// MockExamRepositoryImpl.kt:
override suspend fun getHomeCategories(): Result<List<HomeCategoryItem>> {
    return Result.success(MockExamRepository.getHomeCategories())
}
```
If this alternative is adopted, two call sites must unwrap the `Result`:
- `HomeViewModel.kt`: `val categories = examRepo.getHomeCategories().getOrDefault(emptyList())`
- `EmpiricalDataArchitectureTest.kt`: `val homeCategories = repo.getHomeCategories().getOrThrow()`

---

## 4. Additional High-Value Observations

1. **`RemoteExamRepositoryImpl.kt` line 57**:
   Currently has `response.body()!!.toDomain("series_default")`. The hardcoded `"series_default"` should be replaced with `""` or the actual test's seriesId, so real server series IDs are preserved.
2. **`MockQuestionRepository.kt` legacy test helper**:
   Adding:
   ```kotlin
   fun getQuestionById(questionId: Int): Question? =
       getTehsildarQuestions().find { it.id == questionId } ?: getMpsebQuestions().find { it.id == questionId }
   ```
   resolves the unresolved reference blocking `./gradlew compileDebugUnitTestKotlin` in `Milestone1DataIntegrityEmpiricalTest.kt` and `Milestone1UiChallengeTest.kt`.
3. **Optional Helper on `AbhyaasApplication.Companion`**:
   ```kotlin
   val instanceOrNull: AbhyaasApplication?
       get() = if (::instance.isInitialized) instance else null
   ```
   This allows `AbhyaasApplication.instanceOrNull?.examRepository ?: MockExamRepositoryImpl()` as an even cleaner fallback syntax.

---

## 5. Summary Matrix of Proposed Changes

| Target File | Change Type | Purpose |
|---|---|---|
| `app/.../ui/viewmodel/HomeViewModel.kt` | `@JvmOverloads` + Constructor Defaults | Isolated JVM testability without NPE |
| `app/.../ui/viewmodel/ActiveTestViewModel.kt` | `@JvmOverloads` + Constructor Defaults | Isolated JVM testability without NPE |
| `app/.../ui/viewmodel/TestListViewModel.kt` | `@JvmOverloads` + Constructor Defaults | Isolated JVM testability without NPE |
| `app/.../ui/viewmodel/TestResultViewModel.kt` | `@JvmOverloads` + Constructor Defaults | Isolated JVM testability without NPE |
| `app/.../ui/viewmodel/TestSeriesDetailViewModel.kt` | `@JvmOverloads` + Constructor Defaults | Isolated JVM testability without NPE |
| `app/.../ui/viewmodel/TestsViewModel.kt` | `@JvmOverloads` + Constructor Defaults | Isolated JVM testability without NPE |
| `app/.../ui/viewmodel/UpdatesViewModel.kt` | `@JvmOverloads` + Constructor Defaults | Isolated JVM testability without NPE |
| `app/.../ui/viewmodel/UserProfileViewModel.kt` | `@JvmOverloads` + Constructor Defaults | Isolated JVM testability without NPE |
| `app/.../data/repository/ExamRepository.kt` | Make `getHomeCategories()` `suspend` | Allow asynchronous Retrofit network call |
| `app/.../data/repository/impl/RemoteExamRepositoryImpl.kt` | Call `api.getHomeCategories()` with mock fallback | Complete dynamic network layer |
| `app/.../data/repository/impl/MockExamRepositoryImpl.kt` | Update `getHomeCategories()` override to `suspend` | Match interface signature |
