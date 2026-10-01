# Technical Infrastructure Survey & Architectural Handoff Report

**Agent**: explorer_survey_2  
**Target Project**: Abhyaas (`C:\Users\gurut\AndroidStudioProjects\Abhyaas`)  
**Date**: 2026-09-29  
**Parent**: orchestrator_1 (`df63e9eb-364c-4f79-aea6-4e19a165eef7`)

---

## 1. Observation

### 1.1 Build & Environment Configuration
- **Gradle Wrapper**: `gradle/wrapper/gradle-wrapper.properties`
  ```properties
  distributionUrl=https\://services.gradle.org/distributions/gradle-9.6.0-bin.zip
  ```
  Gradle version is **9.6.0**.
- **Version Catalog**: `gradle/libs.versions.toml`
  ```toml
  [versions]
  agp = "9.4.1"
  coreKtx = "1.10.1"
  junit = "4.13.2"
  junitVersion = "1.1.5"
  espressoCore = "3.5.1"
  lifecycleRuntimeKtx = "2.6.1"
  activityCompose = "1.8.0"
  kotlin = "2.2.10"
  composeBom = "2026.02.01"
  ```
  Android Gradle Plugin (AGP) version is **9.4.1**, Kotlin version is **2.2.10**, Compose BOM is **2026.02.01**.
- **Root Build Script**: `build.gradle.kts`
  ```kotlin
  plugins {
      alias(libs.plugins.android.application) apply false
      alias(libs.plugins.kotlin.compose) apply false
  }
  ```
  Uses the new Kotlin 2.0+ Compose compiler plugin `org.jetbrains.kotlin.plugin.compose` version `2.2.10`.
- **Settings**: `settings.gradle.kts`
  - `rootProject.name = "Abhyaas"`
  - `include(":app")`
  - Uses `org.gradle.toolchains.foojay-resolver-convention` version `1.0.0`
  - `dependencyResolutionManagement` repository mode: `FAIL_ON_PROJECT_REPOS` with `google()` and `mavenCentral()`.
- **App Module Configuration**: `app/build.gradle.kts`
  ```kotlin
  android {
      namespace = "com.example.abhyaas"
      compileSdk {
          version = release(37)
      }
      defaultConfig {
          applicationId = "com.example.abhyaas"
          minSdk = 24
          targetSdk = 37
          versionCode = 1
          versionName = "1.0"
          testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
      }
      buildTypes {
          release {
              optimization {
                  enable = false
              }
          }
      }
      compileOptions {
          sourceCompatibility = JavaVersion.VERSION_11
          targetCompatibility = JavaVersion.VERSION_11
      }
      buildFeatures {
          compose = true
      }
  }
  ```
  `compileSdk` and `targetSdk` are `37`, `minSdk` is `24`. Java compatibility target is `11`.
- **Gradle Properties**: `gradle.properties`
  `org.gradle.jvmargs=-Xmx2048m -Dfile.encoding=UTF-8 ...`  
  `org.gradle.configuration-cache=true`  
  `kotlin.code.style=official`

### 1.2 Dependencies Status
From `app/build.gradle.kts` (lines 38–56):
```kotlin
dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)
    implementation("androidx.navigation:navigation-compose:2.7.7")
    implementation("androidx.compose.material:material-icons-extended:1.6.8")
}
```
- **Present & Verified**:
  - `androidx.compose.material3:material3` (via BOM)
  - `androidx.navigation:navigation-compose:2.7.7`
  - `androidx.compose.material:material-icons-extended:1.6.8`
  - `androidx.activity:activity-compose:1.8.0`
  - `androidx.lifecycle:lifecycle-runtime-ktx:2.6.1`
- **Missing / Recommended**:
  - `androidx.lifecycle:lifecycle-viewmodel-compose` (for ViewModel lifecycle management if ViewModels are introduced)
  - `androidx.lifecycle:lifecycle-runtime-compose` (`collectAsStateWithLifecycle`)
  - Note: `navigation-compose` and `material-icons-extended` are directly hardcoded in `app/build.gradle.kts` rather than cataloged in `libs.versions.toml`.

### 1.3 Codebase & UI Theme Inspection
- **Package Name / Namespace**:
  `com.example.abhyaas` is configured across `app/build.gradle.kts`, `AndroidManifest.xml`, and Kotlin source directories.
- **Activity**: `app/src/main/java/com/example/abhyaas/MainActivity.kt`
  Calls `enableEdgeToEdge()`, wraps `MainScreen()` inside `AbhyaasTheme`.
- **Theme Configuration**:
  - `app/src/main/java/com/example/abhyaas/ui/theme/Theme.kt`:
    Line 38-40:
    ```kotlin
    @Composable
    fun AbhyaasTheme(
        darkTheme: Boolean = isSystemInDarkTheme(),
        // Dynamic color is available on Android 12+
        dynamicColor: Boolean = true,
        content: @Composable () -> Unit
    )
    ```
    **Critical issue**: `dynamicColor: Boolean = true` overrides brand colors with Android wallpaper color palette on API 31+.
  - `app/src/main/java/com/example/abhyaas/ui/theme/Color.kt`:
    Only contains Android Studio template colors (`Purple80`, `PurpleGrey80`, `Pink80`, `Purple40`, `PurpleGrey40`, `Pink40`). No Abhyaas brand palette exists here yet.
  - `app/src/main/java/com/example/abhyaas/ui/theme/Type.kt`:
    Only defines default `bodyLarge`. Lacks typography tokens for exam questions, timer, section badges, scorecards.
- **Resource Files**:
  - `res/values/colors.xml`: Contains default legacy XML colors (`purple_200`, `teal_200`, etc.).
  - `res/values/strings.xml`: Only `<string name="app_name">Abhyaas</string>`.
  - `res/values/themes.xml`: Simple `<style name="Theme.Abhyaas" parent="android:Theme.Material.Light.NoActionBar" />`.
  - `res/drawable`: Only launcher icons (`ic_launcher_background.xml`, `ic_launcher_foreground.xml`). No custom vector drawables.
- **Current Screens Structure**:
  - `ui/navigation/Screen.kt`: Sealed class with `Home ("home")`, `Tests ("tests")`, `Pass ("pass")`, `Updates ("updates")`.
  - `ui/screens/MainScreen.kt`: Scaffold with bottom `NavigationBar` and `NavHost` switching among the 4 tabs.
  - `ui/screens/home/HomeScreen.kt`: Partial implementation with `PassBanner`, category grid cards ("Study Notes", "Previous Year Papers", etc.), dark header with dropdown "Name of Exam".
  - `ui/screens/tests/TestsScreen.kt`: Placeholder `Box { Text("Tests Screen") }`.
  - `ui/screens/pass/PassScreen.kt`: Placeholder `Box { Text("Pass Screen") }`.
  - `ui/screens/updates/UpdatesScreen.kt`: Placeholder `Box { Text("Updates Screen") }`.

### 1.4 Build Verification Results
- Command: `.\gradlew.bat assembleDebug`
  - Exit code: `0`
  - Duration: `4s`
  - Output summary: 36 actionable tasks up-to-date, configuration cache reused, build clean.
- Command: `.\gradlew.bat compileDebugKotlin`
  - Exit code: `0`
  - Duration: `6s`
  - Output summary: 6 actionable tasks up-to-date, configuration cache stored, 0 compilation warnings or errors.

---

## 2. Logic Chain

1. **Gradle Build Health**:
   - Observation 1.1 & 1.4 show that Gradle 9.6.0, AGP 9.4.1, and Kotlin 2.2.10 with Compose compiler plugin are correctly matched and configured.
   - Running `./gradlew.bat assembleDebug` and `./gradlew.bat compileDebugKotlin` succeeded cleanly with exit code 0.
   - Therefore, the project build chain is completely functional, and future UI code can be compiled immediately without build system triage.

2. **Compose Dependency Sufficiency**:
   - `androidx.navigation:navigation-compose:2.7.7` and `androidx.compose.material:material-icons-extended:1.6.8` are already present in `app/build.gradle.kts`.
   - Material 3 components (`Scaffold`, `TopAppBar`, `NavigationBar`, `Card`, `Button`, `ModalBottomSheet`, `TabRow`) and extended icons (`Timer`, `Bookmark`, `CheckCircle`, `Close`, `Menu`, `ArrowBack`, `Analytics`) can be used directly without additional Gradle syncs.
   - While adding `lifecycle-viewmodel-compose` is standard practice, modern Compose state management (`rememberSaveable`, `derivedStateOf`, immutable state holders) can be implemented without adding new external dependencies if desired, or `lifecycle-viewmodel-compose` can be added cleanly.

3. **Theme & Color Customization Necessity**:
   - Observation 1.3 shows that `AbhyaasTheme` currently defaults `dynamicColor = true`.
   - On Android 12+ devices, this activates `dynamicLightColorScheme(context)` and overwrites custom UI colors with the device wallpaper palette.
   - Furthermore, `Color.kt` contains only boilerplate purple colors, while `HomeScreen.kt` hardcodes color literals (`0xFF12121A`, `0xFF032252`, `0xFF1060B5`, `0xFF00C853`, `0xFF6B3A8B`, etc.).
   - Therefore, creating a centralized, reusable brand color and typography definition in `ui/theme/Color.kt` and `ui/theme/Theme.kt` with `dynamicColor = false` is necessary to ensure consistent high-fidelity UI across all screens.

4. **Package Structure & Namespace Consistency**:
   - The user request mentions package patterns like `com.abhyaas.app.ui.screens`. However, Observation 1.1 reveals the existing project namespace is `com.example.abhyaas` across Gradle, Manifest, and file tree.
   - Modifying the root package namespace from `com.example.abhyaas` to `com.abhyaas.app` would require refactoring all directories, updating manifests, and risks breaking existing class bindings.
   - Therefore, keeping `com.example.abhyaas` as the root package and structuring subpackages modularly (`com.example.abhyaas.ui.*`, `com.example.abhyaas.data.*`) is the safest, cleanest, and most maintainable approach.

5. **Screen & Navigation Architecture Needs**:
   - Observation 1.3 reveals that `MainScreen.kt` has a bottom bar embedded for all 4 current routes.
   - However, the Abhyaas test preparation flow requires immersive, full-screen experiences during active tests (instructions, timer-driven question view, palette sheet, submission summary), where the bottom navigation bar must NOT be displayed.
   - Therefore, the app requires a two-tier navigation structure:
     * A Root `NavHost` managing top-level destinations (MainTabs, ActiveExamFlow, ResultDetail, SolutionReview).
     * `MainScreen` holding the bottom navigation bar for the 4 primary tabs (`Home`, `Tests`, `Pass`, `Updates`).

---

## 3. Caveats

1. **Gradle Version Catalog vs Direct Declarations**:
   - `navigation-compose` and `material-icons-extended` are declared directly as string literals in `app/build.gradle.kts`. This compiles fine and does not block development, but moving them into `libs.versions.toml` is recommended for long-term consistency.
2. **Compile SDK 37**:
   - `compileSdk = release(37)` uses the latest Android preview/API 37 SDK. The local build environment successfully compiles against it (`C:\Users\gurut\AppData\Local\Android\Sdk`), but implementers should be mindful of APIs that might behave differently on API 24 (the `minSdk`).
3. **No External Image Loading Library**:
   - Neither Coil nor Glide is in the dependencies. All icons and illustrations should either be Compose vector graphics, Material Icons, or local Android XML vector drawables.
4. **Read-Only Scope**:
   - As an explorer subagent, no source code modifications were performed during this survey.

---

## 4. Conclusion & Actionable Recommendations

### 4.1 Recommended Package Structure
Organize `app/src/main/java/com/example/abhyaas/` into the following clean architecture:

```text
com.example.abhyaas/
├── MainActivity.kt
├── data/
│   ├── model/
│   │   ├── Exam.kt                 // Exam, Subject, Section metadata
│   │   ├── Question.kt             // Question, Option, QuestionStatus enum
│   │   ├── TestAttempt.kt          // User selection state, time spent
│   │   ├── TestResult.kt           // Score, accuracy, section analytics
│   │   ├── LeaderboardEntry.kt     // Rank, user info, score
│   │   └── PassPlan.kt             // Subscription/Pass tier info
│   └── mock/
│       ├── MockExams.kt            // Hardcoded exam catalog
│       ├── MockQuestions.kt        // Realistic mock questions & solutions
│       ├── MockLeaderboard.kt      // Sample leaderboard ranking
│       └── MockPassData.kt         // Pass plans & features
├── ui/
│   ├── theme/
│   │   ├── Color.kt                // Brand palette, test status colors, gradients
│   │   ├── Theme.kt                // AbhyaasTheme (dynamicColor = false)
│   │   └── Type.kt                 // Typography tokens for timers, questions
│   ├── navigation/
│   │   ├── AppNavHost.kt           // Root navigation graph (Main, Exam, Result)
│   │   ├── Screen.kt               // Sealed route definitions with arguments
│   │   └── NavGraph.kt             // Helper extension methods
│   ├── components/
│   │   ├── CommonTopAppBar.kt      // Standard app bar with title and back
│   │   ├── TimerChip.kt            // Countdown timer UI with warning colors
│   │   ├── QuestionStatusBadge.kt  // Color-coded status circles (Answered, etc.)
│   │   ├── OptionItem.kt           // Single-select MCQ option radio card
│   │   ├── QuestionPaletteSheet.kt // Grid drawer showing 1..N question statuses
│   │   └── ExamConfirmDialog.kt    // Submit confirmation dialog
│   └── screens/
│       ├── main/
│       │   └── MainScreen.kt       // Bottom bar scaffold (Home, Tests, Pass, Updates)
│       ├── home/
│       │   ├── HomeScreen.kt       // Dashboard with category cards & banners
│       │   └── components/         // PassBanner, CategoryGridCard
│       ├── tests/
│       │   ├── TestsScreen.kt      // Tabbed test list (All, Enrolled, Free)
│       │   └── components/         // TestCard, FilterChips
│       ├── pass/
│       │   └── PassScreen.kt       // Pass plans, pricing cards, feature comparisons
│       ├── updates/
│       │   └── UpdatesScreen.kt    // Notifications, exam notices, current affairs
│       ├── exam/
│       │   ├── TestInstructionsScreen.kt // Pre-test rules, language picker
│       │   ├── ActiveTestScreen.kt       // Full-screen test taker with timer & palette
│       │   └── TestSummaryScreen.kt      // End-of-test attempt statistics preview
│       ├── result/
│       │   ├── ResultAnalysisScreen.kt   // Scorecard, accuracy gauge, rank
│       │   ├── SolutionReviewScreen.kt   // Question review with correct answer & explanation
│       │   └── LeaderboardScreen.kt      // Rankers list
│       └── drawer/
│           ├── AppDrawer.kt              // Side navigation drawer
│           └── UserSettingsScreen.kt     // Preferences & profile settings
```

### 4.2 Brand Palette & Theme Adaptation (`Color.kt`)
Replace boilerplate colors in `ui/theme/Color.kt` with the official Abhyaas brand tokens:

```kotlin
package com.example.abhyaas.ui.theme

import androidx.compose.ui.graphics.Color

// Primary Brand Colors
val BrandDarkBackground = Color(0xFF12121A)
val BrandDarkSurface = Color(0xFF1B1B26)
val BrandDarkCard = Color(0xFF222230)
val BrandNavyBlue = Color(0xFF032252)
val BrandCobaltBlue = Color(0xFF1060B5)
val BrandAccentBlue = Color(0xFF2196F3)
val BrandLightBlue = Color(0xFF82B1FF)

// Functional / MCQ Question Status Colors
val StatusAnswered = Color(0xFF00C853)         // Emerald Green (Answered)
val StatusNotAnswered = Color(0xFFE53935)      // Crimson Red (Not Answered)
val StatusMarkedReview = Color(0xFF7B1FA2)     // Royal Purple (Marked for Review)
val StatusAnsweredMarked = Color(0xFF5E35B1)   // Violet (Answered & Marked)
val StatusNotVisited = Color(0xFF9E9E9E)       // Neutral Slate Gray (Not Visited)
val StatusWarningTimer = Color(0xFFFFA000)     // Amber Warning (Timer < 5 min)

// Home Category Card Gradients
val CardStudyNotes = Color(0xFF6B3A8B)
val CardPYQ = Color(0xFF9E7C35)
val CardPractice = Color(0xFF1E4473)
val CardLiveTests = Color(0xFF4C3073)
val CardDailyClasses = Color(0xFF85353C)
val CardQuiz = Color(0xFF813259)
val CardCurrentAffairs = Color(0xFF133B6E)
val CardPassButton = Color(0xFF00C853)

// Text Colors
val TextPrimaryDark = Color(0xFFFFFFFF)
val TextSecondaryDark = Color(0xFFB0B0C0)
val TextPrimaryLight = Color(0xFF1C1B1F)
val TextSecondaryLight = Color(0xFF49454F)
```

### 4.3 Fix `Theme.kt`
In `app/src/main/java/com/example/abhyaas/ui/theme/Theme.kt`:
1. Change default `dynamicColor: Boolean = false` so custom branding is never overridden by Android 12+ wallpaper colors.
2. Provide light and dark color schemes matching `BrandNavyBlue`, `BrandAccentBlue`, and `BrandDarkBackground`.

### 4.4 Missing Dependencies (Optional Enhancement)
If view models or runtime state collection are desired:
Add to `app/build.gradle.kts`:
```kotlin
implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
```
(Note: Not strictly required if managing state with standard Compose mutable state / rememberSaveable).

---

## 5. Verification Method

1. **Gradle Build Verification**:
   Execute the following command from project root `C:\Users\gurut\AndroidStudioProjects\Abhyaas`:
   ```bash
   .\gradlew.bat assembleDebug
   ```
   **Expected Result**: Exit code 0, `BUILD SUCCESSFUL`, outputs debug APK.

2. **Kotlin Compilation Verification**:
   ```bash
   .\gradlew.bat compileDebugKotlin
   ```
   **Expected Result**: Exit code 0, 0 compiler errors.

3. **Inspection Verification**:
   - Inspect `app/build.gradle.kts` lines 54-55 to verify `navigation-compose` and `material-icons-extended` are present.
   - Inspect `app/src/main/java/com/example/abhyaas/ui/theme/Theme.kt` line 40 to verify `dynamicColor`.
   - Inspect `app/src/main/java/com/example/abhyaas/ui/screens/home/HomeScreen.kt` to observe existing home layout.
