# Handoff Report — explorer_survey_build_data

## 1. Observation

1. **Gradle Build Files**:
   - `build.gradle.kts` (root, lines 1-5): Configures `libs.plugins.android.application` and `libs.plugins.kotlin.compose` with `apply false`.
   - `settings.gradle.kts` (lines 17-23): `dependencyResolutionManagement` configures `RepositoriesMode.FAIL_ON_PROJECT_REPOS` with `google()` and `mavenCentral()`.
   - `gradle/libs.versions.toml` (lines 1-31): Contains versions `agp = "9.4.1"`, `kotlin = "2.2.10"`, `composeBom = "2026.02.01"`, `lifecycleRuntimeKtx = "2.6.1"`, `activityCompose = "1.8.0"`. Does not declare Retrofit, OkHttp, or ViewModel Compose.
   - `app/build.gradle.kts` (lines 56-65): Explicitly declares:
     ```kotlin
     implementation("androidx.navigation:navigation-compose:2.7.7")
     implementation("androidx.compose.material:material-icons-extended:1.6.8")
     implementation("com.squareup.retrofit2:retrofit:2.11.0")
     implementation("com.squareup.retrofit2:converter-gson:2.11.0")
     implementation("com.squareup.okhttp3:okhttp:4.12.0")
     implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
     implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.7")
     implementation("androidx.lifecycle:lifecycle-runtime-compose:2.8.7")
     implementation("com.google.code.gson:gson:2.11.0")
     implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1")
     ```
   - `app/build.gradle.kts` (lines 20, 34-37):
     ```kotlin
     buildConfigField("String", "BASE_URL", "\"https://api.abhyaas.app/v1/\"")
     buildFeatures {
         compose = true
         buildConfig = true
     }
     ```

2. **AndroidManifest.xml**:
   - `app/src/main/AndroidManifest.xml` (lines 1-29): Contains `<manifest>` and `<application>`, but **ZERO** `<uses-permission>` tags. Neither `android.permission.INTERNET` nor `android.permission.ACCESS_NETWORK_STATE` is present.

3. **Build Status**:
   - Running `.\gradlew --dry-run` exited with code 0 (`BUILD SUCCESSFUL in 3s`).
   - Running `.\gradlew assembleDebug --dry-run` exited with code 0 (`BUILD SUCCESSFUL in 3s`).
   - Running `.\gradlew compileDebugKotlin` exited with code 0 (`BUILD SUCCESSFUL in 3s`).
   - Running `.\gradlew assembleDebug` exited with code 0 (`BUILD SUCCESSFUL in 8s`, 38 actionable tasks).

4. **Codebase Search on Compose State Observation**:
   - `grep_search` for `collectAsStateWithLifecycle` in `app/src/main/java`: 0 matches found.
   - `grep_search` for `collectAsState` in `app/src/main/java`: 0 matches found.
   - `grep_search` for `viewModel(` in `app/src/main/java`: 0 matches found.

5. **Static UI Lists in Screens**:
   - `app/src/main/java/com/example/abhyaas/ui/screens/tests/TestSeriesDetailScreen.kt` (lines 421-450): Contains a hardcoded static list `val mockFolders = listOf(FolderItemUi(...))` instead of mapping from `TestSeries.mockFolders`.
   - `app/src/main/java/com/example/abhyaas/ui/screens/tests/TestsScreen.kt` (line 38): Directly reads `MockExamRepository.getTestSeriesList()` via `remember`.
   - `app/src/main/java/com/example/abhyaas/ui/screens/updates/UpdatesScreen.kt` (lines 38-40): Directly reads `MockUpdatesRepository.getUpdatesByCategory(selectedCategory)` via `remember`.
   - `app/src/main/java/com/example/abhyaas/ui/screens/profile/UserProfileScreen.kt` (line 47): Directly reads `MockUserRepository.getUserProfile()` via `remember`.
   - `app/src/main/java/com/example/abhyaas/ui/viewmodel/` contains `HomeViewModel`, `TestsViewModel`, `TestSeriesDetailViewModel`, `UpdatesViewModel`, `ActiveTestViewModel`, `TestResultViewModel`, but **no `ProfileViewModel`**.

---

## 2. Logic Chain

1. **Retrofit and Serialization Dependency Status**:
   - Observation 1 shows that `com.squareup.retrofit2:retrofit:2.11.0`, `com.squareup.retrofit2:converter-gson:2.11.0`, `com.squareup.okhttp3:okhttp:4.12.0`, `com.squareup.okhttp3:logging-interceptor:4.12.0`, and `com.google.code.gson:gson:2.11.0` are declared in `app/build.gradle.kts`.
   - Observation 3 confirms that gradle successfully resolves and compiles these dependencies with exit code 0.
   - Therefore, no additional dependencies need to be added for Retrofit or JSON parsing.

2. **Compose Lifecycle & ViewModel Support**:
   - Observation 1 shows `androidx.lifecycle:lifecycle-viewmodel-compose:2.8.7` and `androidx.lifecycle:lifecycle-runtime-compose:2.8.7` are already in `app/build.gradle.kts`.
   - `lifecycle-runtime-compose` provides `androidx.lifecycle.compose.collectAsStateWithLifecycle()`.
   - `lifecycle-viewmodel-compose` provides `androidx.lifecycle.viewmodel.compose.viewModel()`.
   - Observation 4 shows that neither function is currently invoked anywhere in `app/src/main/java`.
   - Therefore, all necessary libraries are present and compiling, but the Compose UI layer has not yet been hooked up to use them.

3. **Manifest Permission Requirement**:
   - Observation 2 demonstrates that `app/src/main/AndroidManifest.xml` lacks `INTERNET` and `ACCESS_NETWORK_STATE` permissions.
   - Any HTTP call made via `RetrofitClient` against `https://api.abhyaas.app/v1/` will be rejected by Android runtime security if the internet permission is missing.
   - Therefore, `<uses-permission android:name="android.permission.INTERNET" />` and `<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />` must be added.

4. **Refactoring Scope for Downstream Agents**:
   - Observation 5 reveals hardcoded static lists (`mockFolders` in `TestSeriesDetailScreen`) and direct repository queries inside `remember` blocks in screens.
   - Observation 5 shows `ProfileViewModel` is missing from `ui/viewmodel`.
   - Therefore, downstream refactoring requires: (a) adding `ProfileViewModel`, (b) replacing hardcoded lists with ViewModel state mappings, and (c) consuming StateFlow using `collectAsStateWithLifecycle()`.

---

## 3. Caveats

- **Network Server Availability**: `BuildConfig.BASE_URL` is set to `https://api.abhyaas.app/v1/`. If a backend server is not active at that URL during runtime testing, `RemoteExamRepositoryImpl` and `RemoteUserRepositoryImpl` will fail over to mock data via `runCatching` fallbacks as designed in `RemoteExamRepositoryImpl.kt` lines 12-35.
- **Repository Singletons**: ViewModels currently access repositories via `AbhyaasApplication.instance.<repository>`. As long as `AbhyaasApplication` is declared in `AndroidManifest.xml` (which Observation 2 confirms is present at line 6), this service-locator pattern functions without Hilt/Dagger.
- **Version Catalog vs Direct Dependencies**: Network and lifecycle dependencies are declared directly in `app/build.gradle.kts` instead of `libs.versions.toml`. While moving them to `libs.versions.toml` is good practice, it is not required for successful compilation.

---

## 4. Conclusion

1. **No new dependencies are required** for Retrofit, OkHttp, Gson, Coroutines, or Compose Lifecycle/ViewModel. They are already declared in `app/build.gradle.kts` and verified to compile with `./gradlew assembleDebug`.
2. **Missing Internet Permissions**: `app/src/main/AndroidManifest.xml` must be updated to add `android.permission.INTERNET` and `android.permission.ACCESS_NETWORK_STATE`.
3. **UI Refactoring Action Items**:
   - Implement `ProfileViewModel` and `ProfileUiState`.
   - In screens (`HomeScreen`, `TestsScreen`, `TestSeriesDetailScreen`, `UpdatesScreen`, `UserProfileScreen`, `ActiveTestScreen`, `TestResultScreen`), inject ViewModels using `viewModel()` and observe state using `collectAsStateWithLifecycle()`.
   - Remove hardcoded lists (e.g. `mockFolders` in `TestSeriesDetailScreen`) and dynamically render from ViewModel state.

---

## 5. Verification Method

To independently verify all findings:

1. **Verify Dependencies and Compilation**:
   ```powershell
   .\gradlew assembleDebug
   ```
   *Expected output*: `BUILD SUCCESSFUL` with exit code 0.

2. **Verify Dependency Declarations**:
   Inspect `app/build.gradle.kts` lines 56-65 to verify exact dependency coordinates.

3. **Verify Missing Internet Permission**:
   Inspect `app/src/main/AndroidManifest.xml` lines 1-15 to confirm absence of `<uses-permission android:name="android.permission.INTERNET" />`.

4. **Verify Absence of `collectAsStateWithLifecycle` in Codebase**:
   Execute grep search:
   ```powershell
   git grep "collectAsStateWithLifecycle"
   ```
   *Expected output*: 0 results found (confirming UI needs refactoring).

5. **Invalidation Conditions**:
   - If `app/build.gradle.kts` fails to resolve dependencies during `assembleDebug`.
   - If `collectAsStateWithLifecycle` fails to resolve when imported from `androidx.lifecycle.compose`.
