# Build Configuration & Dependencies Survey Report

**Explorer**: `explorer_survey_build_data`  
**Date**: 2026-09-30  
**Target Project**: Abhyaas Android App (`C:\Users\gurut\AndroidStudioProjects\Abhyaas`)

---

## 1. Executive Summary

A comprehensive investigation of the build configuration, dependency definitions, AndroidManifest permissions, and data architecture was conducted.

### Key Highlights:
1. **Retrofit, OkHttp, and Gson are already included**:
   - `com.squareup.retrofit2:retrofit:2.11.0`
   - `com.squareup.retrofit2:converter-gson:2.11.0`
   - `com.squareup.okhttp3:okhttp:4.12.0`
   - `com.squareup.okhttp3:logging-interceptor:4.12.0`
   - `com.google.code.gson:gson:2.11.0`
2. **ViewModel and Compose Lifecycle are already included**:
   - `androidx.lifecycle:lifecycle-viewmodel-compose:2.8.7`
   - `androidx.lifecycle:lifecycle-runtime-compose:2.8.7`
   - `org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1`
   - This allows direct use of `collectAsStateWithLifecycle()` from `androidx.lifecycle.compose` and `viewModel()` from `androidx.lifecycle.viewmodel.compose`.
3. **Current Build Status is Green**:
   - `.\gradlew --dry-run`: Successful (3s)
   - `.\gradlew compileDebugKotlin`: Successful (3s)
   - `.\gradlew assembleDebug`: Successful (8s, 38 actionable tasks)
4. **CRITICAL FINDING — Missing Internet Permissions in Manifest**:
   - `app/src/main/AndroidManifest.xml` currently has **NO** `<uses-permission android:name="android.permission.INTERNET" />`.
   - While in-memory mock repositories execute without network access, any live network requests via Retrofit will fail with `SecurityException: Permission denied (missing INTERNET permission)`.
5. **State Observation & Screen Refactoring Opportunity**:
   - Although the dependencies and ViewModels (`HomeViewModel`, `TestsViewModel`, `TestSeriesDetailViewModel`, `UpdatesViewModel`, `ActiveTestViewModel`, `TestResultViewModel`) exist, **0 Compose screens currently invoke `collectAsStateWithLifecycle()` or `viewModel()`**.
   - Screens currently query mock repositories directly in `remember { ... }` blocks and use hardcoded UI lists (e.g., `mockFolders` in `TestSeriesDetailScreen`).
   - A `ProfileViewModel` is currently missing and needs to be created for `UserProfileScreen`.

---

## 2. Build Configuration Survey

### 2.1 Root `build.gradle.kts`
Path: `C:\Users\gurut\AndroidStudioProjects\Abhyaas\build.gradle.kts`
```kotlin
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.compose) apply false
}
```

### 2.2 `settings.gradle.kts`
Path: `C:\Users\gurut\AndroidStudioProjects\Abhyaas\settings.gradle.kts`
- Repositories configured:
  - `pluginManagement`: `google` (filtered), `mavenCentral()`, `gradlePluginPortal()`
  - `dependencyResolutionManagement`: `RepositoriesMode.FAIL_ON_PROJECT_REPOS`, `google()`, `mavenCentral()`
- Modules included: `:app`

### 2.3 Version Catalog (`gradle/libs.versions.toml`)
Path: `C:\Users\gurut\AndroidStudioProjects\Abhyaas\gradle\libs.versions.toml`
- AGP: `9.4.1`
- Kotlin: `2.2.10`
- Compose BOM: `2026.02.01`
- `coreKtx`: `1.10.1`
- `lifecycleRuntimeKtx`: `2.6.1`
- `activityCompose`: `1.8.0`
- `junit`: `4.13.2`
- Compose BOM-managed libraries: `ui`, `ui-graphics`, `ui-tooling`, `ui-tooling-preview`, `material3`, `ui-test-manifest`, `ui-test-junit4`
- *Note*: Network and architecture dependencies are currently defined directly in `app/build.gradle.kts` rather than catalog aliases. This is fully functional and supported.

### 2.4 App Module Build File (`app/build.gradle.kts`)
Path: `C:\Users\gurut\AndroidStudioProjects\Abhyaas\app\build.gradle.kts`
- **SDK & Compatibility**:
  - `compileSdk = 37`
  - `minSdk = 24`
  - `targetSdk = 37`
  - `sourceCompatibility = JavaVersion.VERSION_11`
  - `targetCompatibility = JavaVersion.VERSION_11`
- **Build Features**:
  - `compose = true`
  - `buildConfig = true`
- **BuildConfig Fields**:
  - `buildConfigField("String", "BASE_URL", "\"https://api.abhyaas.app/v1/\"")`

---

## 3. Dependency Inventory & Status

| Library Category | Exact Coordinate | Version | Location Declared | Status |
| :--- | :--- | :--- | :--- | :--- |
| **Retrofit** | `com.squareup.retrofit2:retrofit` | `2.11.0` | `app/build.gradle.kts:58` | Ready & Compiling |
| **Gson Converter** | `com.squareup.retrofit2:converter-gson` | `2.11.0` | `app/build.gradle.kts:59` | Ready & Compiling |
| **OkHttp Client** | `com.squareup.okhttp3:okhttp` | `4.12.0` | `app/build.gradle.kts:60` | Ready & Compiling |
| **OkHttp Logging** | `com.squareup.okhttp3:logging-interceptor` | `4.12.0` | `app/build.gradle.kts:61` | Ready & Compiling |
| **Gson Engine** | `com.google.code.gson:gson` | `2.11.0` | `app/build.gradle.kts:64` | Ready & Compiling |
| **Coroutines Android** | `org.jetbrains.kotlinx:kotlinx-coroutines-android` | `1.8.1` | `app/build.gradle.kts:65` | Ready & Compiling |
| **ViewModel Compose** | `androidx.lifecycle:lifecycle-viewmodel-compose` | `2.8.7` | `app/build.gradle.kts:62` | Ready & Compiling |
| **Runtime Compose** | `androidx.lifecycle:lifecycle-runtime-compose` | `2.8.7` | `app/build.gradle.kts:63` | Ready & Compiling |
| **Lifecycle Runtime** | `androidx.lifecycle:lifecycle-runtime-ktx` | `2.6.1` | `libs.versions.toml` | Ready & Compiling |
| **Navigation Compose**| `androidx.navigation:navigation-compose` | `2.7.7` | `app/build.gradle.kts:56` | Ready & Compiling |
| **Material Icons** | `androidx.compose.material:material-icons-extended` | `1.6.8` | `app/build.gradle.kts:57` | Ready & Compiling |
| **Compose BOM** | `androidx.compose:compose-bom` | `2026.02.01` | `libs.versions.toml` | Ready & Compiling |

**Conclusion on Dependencies**:
No additional dependencies are required in `app/build.gradle.kts` for Retrofit, OkHttp, Gson, Coroutines, or Compose ViewModel lifecycle. The project already has all required dependencies configured and successfully compiling.

---

## 4. AndroidManifest.xml Analysis

Path: `C:\Users\gurut\AndroidStudioProjects\Abhyaas\app\src\main\AndroidManifest.xml`

### Current Content:
```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools">

    <application
        android:name=".AbhyaasApplication"
        android:allowBackup="true"
        android:dataExtractionRules="@xml/data_extraction_rules"
        android:fullBackupContent="@xml/backup_rules"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/Theme.Abhyaas">
        <activity
            android:name=".MainActivity"
            android:exported="true"
            android:label="@string/app_name"
            android:theme="@style/Theme.Abhyaas"
            android:windowSoftInputMode="adjustResize">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>
</manifest>
```

### Missing Permissions:
- `android.permission.INTERNET` is **NOT** declared.
- `android.permission.ACCESS_NETWORK_STATE` is **NOT** declared.

### Required Modification:
Before `<application>` in `app/src/main/AndroidManifest.xml`, the following must be added:
```xml
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
```

---

## 5. Build Verification Results

Commands executed:
1. `.\gradlew --dry-run`
   - Result: `BUILD SUCCESSFUL in 3s`
   - Exit code: 0
2. `.\gradlew assembleDebug --dry-run`
   - Result: `BUILD SUCCESSFUL in 3s`
   - Exit code: 0
3. `.\gradlew compileDebugKotlin`
   - Result: `BUILD SUCCESSFUL in 3s`, 7 actionable tasks up-to-date
   - Exit code: 0
4. `.\gradlew assembleDebug`
   - Result: `BUILD SUCCESSFUL in 8s`, 38 actionable tasks (7 executed, 31 up-to-date)
   - Exit code: 0

The build environment is completely healthy and all dependencies resolve with zero conflicts.

---

## 6. Architecture & Screen Wiring Survey

### 6.1 Data Layer Readiness
- **Retrofit & Network**:
  - `RetrofitClient` (`com.example.abhyaas.data.network.RetrofitClient`) is implemented with auth header injection, OkHttp logging, and Gson converter factory.
  - `ApiService` (`com.example.abhyaas.data.network.ApiService`) defines 14 API endpoints with suspend functions.
  - `Dtos.kt` (`com.example.abhyaas.data.network.dto.Dtos.kt`) defines all data transfer objects with `@SerializedName`.
- **Repositories**:
  - `ExamRepository` has both `MockExamRepositoryImpl` and `RemoteExamRepositoryImpl`.
  - `UserRepository` has both `MockUserRepositoryImpl` and `RemoteUserRepositoryImpl`.
  - `QuestionRepository`, `TestResultRepository`, and `UpdatesRepository` have mock implementations with room for remote implementations.
  - `AbhyaasApplication` provides single-instance lazy access to repositories.

### 6.2 ViewModels Status
- Present ViewModels:
  - `HomeViewModel`: Exposes `uiState: StateFlow<HomeUiState>`
  - `TestsViewModel`: Exposes `uiState: StateFlow<TestsUiState>`
  - `TestSeriesDetailViewModel`: Exposes `uiState: StateFlow<TestSeriesDetailUiState>`
  - `UpdatesViewModel`: Exposes `uiState: StateFlow<UpdatesUiState>`
  - `ActiveTestViewModel`: Exposes `uiState: StateFlow<ActiveTestUiState>`
  - `TestResultViewModel`: Exposes `uiState: StateFlow<TestResultUiState>`
- Missing ViewModel:
  - `ProfileViewModel`: Does not exist yet. `UserProfileScreen` is currently reading from `MockUserRepository` directly. Creating `ProfileViewModel` is required to complete MVVM for all screens.

### 6.3 Compose UI State Observation Gap
- Grep searches confirmed:
  - `collectAsStateWithLifecycle()` has **0 occurrences** in `app/src/main/java`.
  - `collectAsState()` has **0 occurrences** in `app/src/main/java`.
  - `viewModel()` has **0 occurrences** in `app/src/main/java`.
- Screens currently use:
  - `HomeScreen`: Parameter defaults / empty callbacks.
  - `TestsScreen`: `val testSeriesList = remember { MockExamRepository.getTestSeriesList() }`
  - `TestSeriesDetailScreen`: `val series = remember(seriesId) { ... }` and hardcoded `val mockFolders = listOf(...)`.
  - `UpdatesScreen`: `val updates = remember(selectedCategory) { MockUpdatesRepository.getUpdatesByCategory(...) }`.
  - `UserProfileScreen`: `val userProfile = remember { MockUserRepository.getUserProfile() }`.
  - `ActiveTestScreen`: `val test = remember(testId) { MockExamRepository.getTestById(testId) }`.
  - `TestResultScreen`: `val testResult = remember(testId) { MockExamRepository.getPreviousAttemptResult(testId) }`.

### 6.4 Recommendations for Downstream Agents
1. Add `<uses-permission android:name="android.permission.INTERNET" />` and `<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />` to `app/src/main/AndroidManifest.xml`.
2. Add `ProfileViewModel` and `ProfileUiState` to `com.example.abhyaas.ui.viewmodel`.
3. In Compose screens:
   - Inject ViewModel using `viewModel: <Screen>ViewModel = viewModel()`.
   - Observe state via `val uiState by viewModel.uiState.collectAsStateWithLifecycle()`.
   - Replace all static lists (such as `mockFolders` in `TestSeriesDetailScreen`) with iterations over `uiState`.
   - Add loading/error UI handling where appropriate.
