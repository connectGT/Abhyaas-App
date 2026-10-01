# Technical Blueprint & Handoff: Milestone 1 Design System, Brand Colors, Typography, and Theme

**Agent**: `explorer_m1_1`  
**Working Directory**: `C:\Users\gurut\AndroidStudioProjects\Abhyaas\.agents\teamwork\explorer_m1_1`  
**Parent Agent**: `orchestrator_1` (`df63e9eb-364c-4f79-aea6-4e19a165eef7`)  
**Target Module**: `app/src/main/java/com/example/abhyaas/ui/theme/`  
**Date**: 2026-09-29  

---

## 1. Observation

### 1.1 Existing Files and Code Analysis
1. `app/src/main/java/com/example/abhyaas/ui/theme/Color.kt`:
   - Lines 1–11 contain solely default Android Studio template colors:
     ```kotlin
     val Purple80 = Color(0xFFD0BCFF)
     val PurpleGrey80 = Color(0xFFCCC2DC)
     val Pink80 = Color(0xFFEFB8C8)
     val Purple40 = Color(0xFF6650a4)
     val PurpleGrey40 = Color(0xFF625b71)
     val Pink40 = Color(0xFF7D5260)
     ```
   - No Abhyaas brand tokens, question status colors, dark midnight surfaces, or category card gradients exist in this file.

2. `app/src/main/java/com/example/abhyaas/ui/theme/Theme.kt`:
   - Lines 36–42:
     ```kotlin
     @Composable
     fun AbhyaasTheme(
         darkTheme: Boolean = isSystemInDarkTheme(),
         // Dynamic color is available on Android 12+
         dynamicColor: Boolean = true,
         content: @Composable () -> Unit
     )
     ```
   - `dynamicColor` defaults to `true`. On Android 12+ (API 31+), `dynamicDarkColorScheme(context)` and `dynamicLightColorScheme(context)` extract colors from the user's wallpaper, completely overriding the Abhyaas brand identity.
   - `DarkColorScheme` and `LightColorScheme` only use the placeholder purple colors (`Purple80`, `PurpleGrey80`, `Pink80`, `Purple40`, etc.).

3. `app/src/main/java/com/example/abhyaas/ui/theme/Type.kt`:
   - Lines 9–17:
     ```kotlin
     val Typography = Typography(
         bodyLarge = TextStyle(
             fontFamily = FontFamily.Default,
             fontWeight = FontWeight.Normal,
             fontSize = 16.sp,
             lineHeight = 24.sp,
             letterSpacing = 0.5.sp
         )
     )
     ```
   - Only `bodyLarge` is defined. There are no tokens for monospace countdown timers (`00:14:51`), question statements, option text, section headers, scorecard hero numbers, or Hindi typography.

4. Baseline Build Execution:
   - Tool command: `.\gradlew.bat compileDebugKotlin`
   - Result: Exit code `0`, `BUILD SUCCESSFUL in 1s`, 6 actionable tasks up-to-date.

### 1.2 Design Assets & Survey Mapping
From `C:\Users\gurut\Downloads\ABHYAAS App\UI UX` mockups and `spec_miner_survey_1/handoff.md`:
- `symbol meaning.jpeg` & `symbol meaning 2.jpeg`: Define the 4 discrete question palette states:
  - Unattempted: Solid Blue square (`#2563EB`)
  - Answered: Solid Green square (`#10B981`)
  - Marked for Review (Unanswered): Coral Red tag with pointer ▲ (`#EF4444`)
  - Answered & Marked for Review: Amber/Yellow tag with pointer ▲ (`#F59E0B`)
- `home tab 1.png`:
  - Pass Banner: Deep Navy (`#032252`) to Cobalt Blue (`#1060B5`) horizontal gradient, "PASS" pill (`#82B1FF`), CTA button (`#10B981`).
  - Section bar: Electric Cyan (`#00C2FF`).
  - 6 Category Gradients: Study Notes (`#6B3A8B`), PYQ (`#9E7C35`), Practice (`#1E4473`), Live Tests (`#4C3073`), Daily Live Classes (`#85353C`), Quiz (`#813259`), Current Affairs (`#133B6E`).
- `on test options.jpeg` & `qs on test timer.jpeg`:
  - Live Countdown Timer: Monospace font `00:14:51` to prevent character-width jitter.
  - Per-question timer: Monospace `00:08`.
  - Question statement: 16sp Medium text, high contrast against dark surface `#161F2E`.
  - Options: Dark selectable cards (`#1E293B`) with stroke `#2E3D52`.
- `result analysis.jpeg`:
  - Scorecard Hero: 32sp ExtraBold score `0/200`.
  - Rank: 22sp Bold `22789/24964`.
  - Cut-off text, accuracy gauge, WhatsApp challenge button (`#10B981`).
- `leaderboard.jpeg`:
  - Top 3 Podium: Gold (`#F59E0B`), Silver (`#94A3B8`), Bronze (`#B45309`).
  - Sticky user card: Periwinkle blue `#D0E2FF` / dark elevated container.

---

## 2. Logic Chain

1. **Brand Identity Preservation via `dynamicColor: Boolean = false`**:
   - As observed in Observation 1.1, `Theme.kt` currently defaults `dynamicColor = true`.
   - On Android 12+, this completely destroys the carefully crafted Abhyaas brand palette by applying dynamic Material You wallpaper tones.
   - Therefore, changing `dynamicColor: Boolean = false` by default is a strict architectural requirement.

2. **Complete Color Token Architecture in `Color.kt`**:
   - Observation 1.1 and 1.2 demonstrate that multiple distinct screens require recurring colors: brand navy/cobalt, dark backgrounds (`#0B111A`), surfaces (`#161F2E`), cards (`#1E293B`), borders (`#2E3D52`), question states (`#10B981`, `#EF4444`, `#F59E0B`, `#2563EB`, `#64748B`), and 6 category card gradients.
   - Centralizing all color tokens into `Color.kt` and creating reusable `Brush` objects ensures zero duplication and prevents hardcoded magic numbers across screens.
   - To maintain backward compatibility with any template files, aliases for `Purple80`, `PurpleGrey80`, `Pink80`, `Purple40`, `PurpleGrey40`, and `Pink40` are mapped to new tokens.

3. **Typography Tokens & Monospace Timer Stability in `Type.kt`**:
   - In active exam screens (`qs on test timer.jpeg`), countdown timers tick once every second. Proportional fonts (like Roboto/Default) have varying glyph widths for digits `0` through `9`, causing the timer chip and surrounding UI to visually shake or jitter.
   - Defining `timerLarge` and `timerMedium` with `FontFamily.Monospace` ensures fixed glyph widths and rock-solid UI stability.
   - In addition to standard Material 3 `Typography`, defining an immutable `AbhyaasCustomTypography` class and injecting it through `CompositionLocalProvider` allows clean, type-safe access (`AbhyaasTheme.typography.timerLarge`, `AbhyaasTheme.typography.questionStatement`, etc.).

4. **Unified Theme Architecture & Accessor Object in `Theme.kt`**:
   - Material 3's `ColorScheme` does not natively have fields for question statuses (`StatusAnswered`, `StatusMarkedReview`), category card gradients, or leaderboard podium colors.
   - Creating `AbhyaasCustomColors` and providing it via `LocalAbhyaasColors` alongside `LocalAbhyaasTypography` creates a cohesive design system.
   - Developers can seamlessly use both standard `MaterialTheme.colorScheme` and `AbhyaasTheme.colors.*` / `AbhyaasTheme.typography.*`.
   - Edge-to-edge system bar configuration with `WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme` ensures status bars seamlessly blend with the deep `#0B111A` background.

---

## 3. Caveats

1. **Read-Only Explorer Scope**:
   - In accordance with Teamwork Explorer protocol, no files in `app/src/` were modified by this agent.
   - Full, drop-in ready replacement files were created in `.agents/teamwork/explorer_m1_1/` (`proposed_Color.kt`, `proposed_Type.kt`, `proposed_Theme.kt`) and provided verbatim below for immediate implementation by the Worker.
2. **Font Families**:
   - Custom external fonts (e.g. Poppins or Inter TTF/OTF) are not bundled in `res/font/`. The blueprint standardizes on `FontFamily.Default` and `FontFamily.Monospace`, ensuring zero runtime missing resource exceptions and clean out-of-the-box rendering. If custom TTF fonts are added later, only `Type.kt` needs font family adjustments.
3. **Existing Hardcoded Colors in `HomeScreen.kt`**:
   - `HomeScreen.kt` currently has a few hardcoded literals (e.g., `Color(0xFF12121A)`). Replacing `Color.kt` will not break `HomeScreen.kt`, but Milestone 2 should refactor those to `MaterialTheme.colorScheme.background` and `AbhyaasTheme.colors.*`.

---

## 4. Conclusion & Drop-In Ready Implementation Blueprint

The Worker implementing Milestone 1 must replace the contents of the following 3 files in `app/src/main/java/com/example/abhyaas/ui/theme/` with the exact drop-in ready code provided below.

### 4.1 Target File: `app/src/main/java/com/example/abhyaas/ui/theme/Color.kt`
Reference: `C:\Users\gurut\AndroidStudioProjects\Abhyaas\.agents\teamwork\explorer_m1_1\proposed_Color.kt`

```kotlin
package com.example.abhyaas.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// ============================================================================
// ABHYAAS BRAND COLORS & THEME TOKENS
// ============================================================================

// 1. Primary Brand Identity Colors
val BrandNavy = Color(0xFF032252)              // Pass banner deep navy
val BrandCobalt = Color(0xFF1060B5)            // Pass banner gradient cobalt blue
val BrandPrimary = Color(0xFF2563EB)           // Vibrant royal blue (Primary CTA & buttons)
val BrandPrimaryDark = Color(0xFF1D4ED8)       // Darker active blue
val BrandPrimaryLight = Color(0xFF3B82F6)      // Lighter active blue
val BrandAccentCyan = Color(0xFF00C2FF)        // Electric cyan accent (vertical section bars, links)
val BrandSkyBlue = Color(0xFF38BDF8)           // Sky blue highlights
val BrandLightBlue = Color(0xFF82B1FF)         // Soft periwinkle "PASS" tag pill
val BrandPeriwinkle = Color(0xFFD0E2FF)        // Sticky "(You)" user card on leaderboard

// 2. Dark Surfaces & Backgrounds (Default Theme UX)
val DarkBackground = Color(0xFF0B111A)         // Deepest midnight blue background
val DarkBackgroundGradientStart = Color(0xFF0A1322)
val DarkBackgroundGradientEnd = Color(0xFF060B14)
val DarkSurface = Color(0xFF161F2E)            // Cards, drawers, bottom sheets
val DarkCard = Color(0xFF1E293B)               // Inner cards, option container
val DarkCardElevated = Color(0xFF243247)       // Elevated modal dialogs & active elements
val DarkSelected = Color(0xFF1E2C44)           // Selected drawer pill / active state
val DarkBorder = Color(0xFF2E3D52)             // Card borders & outlines
val DarkBorderSubtle = Color(0xFF1E2A3A)       // Dividers and thin separator lines

// 3. Light Surfaces & Backgrounds (High-Contrast Elements & Light Theme)
val LightBackground = Color(0xFFF8FAFC)
val LightSurface = Color(0xFFFFFFFF)
val LightSurfaceVariant = Color(0xFFF1F5F9)
val LightBorder = Color(0xFFE2E8F0)

// 4. MCQ Question Status & Active Exam Engine Palette Colors
// (Directly mapped to symbol meaning.jpeg & symbol meaning 2.jpeg)
val StatusAnswered = Color(0xFF10B981)         // Emerald Green: Answered question (Square 13)
val StatusAnsweredContainer = Color(0xFF064E3B)
val StatusNotAnswered = Color(0xFFEF4444)      // Crimson Red: Not Answered / Skipped
val StatusNotAnsweredContainer = Color(0xFF7F1D1D)
val StatusUnattempted = Color(0xFF2563EB)      // Vibrant Blue: Unattempted question (Square 12)
val StatusNotVisited = Color(0xFF64748B)       // Slate Gray: Not Visited question
val StatusMarkedReview = Color(0xFFEF4444)     // Coral Red Tag with pointer ▲ (Marked for review without answering 14)
val StatusAnsweredMarked = Color(0xFFF59E0B)   // Amber/Yellow Tag with pointer ▲ (Answered & marked for review 15)
val StatusWarningTimer = Color(0xFFF59E0B)     // Amber Warning (Timer < 15 mins)
val StatusUrgentTimer = Color(0xFFEF4444)      // Crimson Alert (Timer < 2 mins)
val StatusCorrect = Color(0xFF10B981)          // Emerald Green: Correct option highlight
val StatusIncorrect = Color(0xFFEF4444)        // Crimson Red: Incorrect option highlight

// 5. Home Category Card Colors & Gradients (home tab 1.png)
val CardStudyNotesStart = Color(0xFF6B3A8B)    // Study Notes (Purple gradient)
val CardStudyNotesEnd = Color(0xFF4A2563)
val CardPYQStart = Color(0xFF9E7C35)           // Previous Year Papers (Warm Amber/Gold)
val CardPYQEnd = Color(0xFF6D5420)
val CardPracticeStart = Color(0xFF1E4473)      // Practice Section (Deep Blue)
val CardPracticeEnd = Color(0xFF132D4F)
val CardLiveTestsStart = Color(0xFF4C3073)     // Live Tests & Quizzes (Indigo/Purple)
val CardLiveTestsEnd = Color(0xFF321E4F)
val CardDailyClassesStart = Color(0xFF85353C)  // Daily Live Classes (Crimson/Wine Red)
val CardDailyClassesEnd = Color(0xFF5E2228)
val CardQuizStart = Color(0xFF813259)          // Quiz Section (Magenta/Berry)
val CardQuizEnd = Color(0xFF571F3B)
val CardCurrentAffairsStart = Color(0xFF133B6E) // Current Affairs (Navy Blue)
val CardCurrentAffairsEnd = Color(0xFF0C274A)

// 6. Action & Subscription CTAs
val CtaGreen = Color(0xFF10B981)               // "Get Pass →" & "Unlock Test Series" CTA
val BadgeFreeGreen = Color(0xFF00C853)         // "FREE" chip badge

// 7. Leaderboard Podium Colors (leaderboard.jpeg)
val PodiumGold = Color(0xFFF59E0B)             // Rank 1 Gold badge & ring
val PodiumSilver = Color(0xFF94A3B8)           // Rank 2 Silver badge & ring
val PodiumBronze = Color(0xFFB45309)           // Rank 3 Bronze badge & ring

// 8. Text & Icon Colors
val TextPrimaryDark = Color(0xFFF8FAFC)        // High contrast readable white
val TextSecondaryDark = Color(0xFF94A3B8)      // Muted slate gray
val TextTertiaryDark = Color(0xFF64748B)       // Dim label / subtle icon tint
val TextPrimaryLight = Color(0xFF0F172A)
val TextSecondaryLight = Color(0xFF475569)

// 9. Reusable Gradient Brushes
val PassBannerBrush = Brush.horizontalGradient(listOf(BrandNavy, BrandCobalt))
val AppBackgroundBrush = Brush.verticalGradient(listOf(DarkBackgroundGradientStart, DarkBackgroundGradientEnd))
val StudyNotesBrush = Brush.linearGradient(listOf(CardStudyNotesStart, CardStudyNotesEnd))
val PYQBrush = Brush.linearGradient(listOf(CardPYQStart, CardPYQEnd))
val PracticeBrush = Brush.linearGradient(listOf(CardPracticeStart, CardPracticeEnd))
val LiveTestsBrush = Brush.linearGradient(listOf(CardLiveTestsStart, CardLiveTestsEnd))
val DailyClassesBrush = Brush.linearGradient(listOf(CardDailyClassesStart, CardDailyClassesEnd))
val QuizBrush = Brush.linearGradient(listOf(CardQuizStart, CardQuizEnd))
val CurrentAffairsBrush = Brush.linearGradient(listOf(CardCurrentAffairsStart, CardCurrentAffairsEnd))

// 10. Backward-Compatibility Template Aliases
val Purple80 = BrandLightBlue
val PurpleGrey80 = DarkBorder
val Pink80 = CardQuizStart
val Purple40 = BrandNavy
val PurpleGrey40 = DarkCard
val Pink40 = CardDailyClassesStart
```

---

### 4.2 Target File: `app/src/main/java/com/example/abhyaas/ui/theme/Type.kt`
Reference: `C:\Users\gurut\AndroidStudioProjects\Abhyaas\.agents\teamwork\explorer_m1_1\proposed_Type.kt`

```kotlin
package com.example.abhyaas.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// ============================================================================
// MATERIAL 3 STANDARD TYPOGRAPHY TOKENS
// ============================================================================

val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 32.sp,
        lineHeight = 40.sp,
        letterSpacing = (-0.5).sp
    ),
    displayMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        lineHeight = 36.sp
    ),
    displaySmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        lineHeight = 32.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        lineHeight = 28.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        lineHeight = 24.sp
    ),
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.15.sp
    ),
    titleMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 22.sp,
        letterSpacing = 0.15.sp
    ),
    titleSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp
    ),
    bodySmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.4.sp
    ),
    labelLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.5.sp
    ),
    labelMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 11.sp,
        lineHeight = 14.sp,
        letterSpacing = 0.5.sp
    )
)

// ============================================================================
// ABHYAAS CUSTOM TYPOGRAPHY TOKENS (EXAM ENGINE, SCORECARD & QUESTIONS)
// ============================================================================

@Immutable
data class AbhyaasCustomTypography(
    // 1. Monospace Timers (prevents character-width jitter during live ticking)
    val timerLarge: TextStyle,
    val timerMedium: TextStyle,
    val timerSmall: TextStyle,

    // 2. Question Statement & MCQ Options
    val questionStatement: TextStyle,
    val questionDirection: TextStyle,
    val optionText: TextStyle,
    val optionBadge: TextStyle,
    val explanationText: TextStyle,
    val hindiText: TextStyle,

    // 3. Section Titles & Navigation Headers
    val topAppBarTitle: TextStyle,
    val sectionHeader: TextStyle,
    val cardTitle: TextStyle,
    val cardSubtitle: TextStyle,

    // 4. Scorecard, Rank & Performance Analytics
    val scorecardHero: TextStyle,
    val scorecardRank: TextStyle,
    val scorecardStat: TextStyle,
    val paletteNumber: TextStyle,
    val badgeLabel: TextStyle,
    val buttonText: TextStyle
)

fun defaultAbhyaasTypography() = AbhyaasCustomTypography(
    timerLarge = TextStyle(
        fontFamily = FontFamily.Monospace,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        letterSpacing = 1.sp
    ),
    timerMedium = TextStyle(
        fontFamily = FontFamily.Monospace,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        letterSpacing = 0.5.sp
    ),
    timerSmall = TextStyle(
        fontFamily = FontFamily.Monospace,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),
    questionStatement = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 24.sp
    ),
    questionDirection = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 13.sp,
        lineHeight = 18.sp,
        fontStyle = FontStyle.Italic
    ),
    optionText = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 15.sp,
        lineHeight = 22.sp
    ),
    optionBadge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp
    ),
    explanationText = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 22.sp
    ),
    hindiText = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 15.sp,
        lineHeight = 24.sp
    ),
    topAppBarTitle = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        letterSpacing = 0.15.sp
    ),
    sectionHeader = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        letterSpacing = 0.25.sp
    ),
    cardTitle = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        lineHeight = 22.sp
    ),
    cardSubtitle = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp
    ),
    scorecardHero = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 32.sp,
        letterSpacing = (-0.5).sp
    ),
    scorecardRank = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp
    ),
    scorecardStat = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp
    ),
    paletteNumber = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 13.sp
    ),
    badgeLabel = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 11.sp,
        letterSpacing = 0.5.sp
    ),
    buttonText = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        letterSpacing = 0.5.sp
    )
)

val LocalAbhyaasTypography = staticCompositionLocalOf { defaultAbhyaasTypography() }
```

---

### 4.3 Target File: `app/src/main/java/com/example/abhyaas/ui/theme/Theme.kt`
Reference: `C:\Users\gurut\AndroidStudioProjects\Abhyaas\.agents\teamwork\explorer_m1_1\proposed_Theme.kt`

```kotlin
package com.example.abhyaas.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// ============================================================================
// MATERIAL 3 COLOR SCHEMES
// ============================================================================

private val DarkColorScheme = darkColorScheme(
    primary = BrandPrimary,
    onPrimary = Color.White,
    primaryContainer = BrandNavy,
    onPrimaryContainer = BrandLightBlue,
    secondary = BrandAccentCyan,
    onSecondary = Color.Black,
    secondaryContainer = DarkSelected,
    onSecondaryContainer = Color.White,
    tertiary = StatusAnswered,
    onTertiary = Color.White,
    tertiaryContainer = StatusAnsweredContainer,
    onTertiaryContainer = Color.White,
    background = DarkBackground,
    onBackground = TextPrimaryDark,
    surface = DarkSurface,
    onSurface = TextPrimaryDark,
    surfaceVariant = DarkCard,
    onSurfaceVariant = TextSecondaryDark,
    surfaceTint = BrandPrimary,
    outline = DarkBorder,
    outlineVariant = DarkBorderSubtle,
    error = StatusNotAnswered,
    onError = Color.White,
    errorContainer = StatusNotAnsweredContainer,
    onErrorContainer = Color.White
)

private val LightColorScheme = lightColorScheme(
    primary = BrandPrimary,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFDBEAFE),
    onPrimaryContainer = BrandNavy,
    secondary = BrandCobalt,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFE0F2FE),
    onSecondaryContainer = Color(0xFF0369A1),
    tertiary = StatusAnswered,
    onTertiary = Color.White,
    background = LightBackground,
    onBackground = TextPrimaryLight,
    surface = LightSurface,
    onSurface = TextPrimaryLight,
    surfaceVariant = LightSurfaceVariant,
    onSurfaceVariant = TextSecondaryLight,
    surfaceTint = BrandPrimary,
    outline = LightBorder,
    error = StatusNotAnswered,
    onError = Color.White
)

// ============================================================================
// ABHYAAS EXTENDED BRAND & STATUS COLOR TOKENS
// ============================================================================

@Immutable
data class AbhyaasCustomColors(
    val brandNavy: Color,
    val brandCobalt: Color,
    val brandPrimary: Color,
    val brandAccentCyan: Color,
    val brandSkyBlue: Color,
    val brandLightBlue: Color,
    val brandPeriwinkle: Color,

    val darkBackground: Color,
    val darkSurface: Color,
    val darkCard: Color,
    val darkCardElevated: Color,
    val darkSelected: Color,
    val darkBorder: Color,

    val statusAnswered: Color,
    val statusNotAnswered: Color,
    val statusUnattempted: Color,
    val statusNotVisited: Color,
    val statusMarkedReview: Color,
    val statusAnsweredMarked: Color,
    val statusWarningTimer: Color,
    val statusUrgentTimer: Color,
    val statusCorrect: Color,
    val statusIncorrect: Color,

    val cardStudyNotesStart: Color,
    val cardStudyNotesEnd: Color,
    val cardPYQStart: Color,
    val cardPYQEnd: Color,
    val cardPracticeStart: Color,
    val cardPracticeEnd: Color,
    val cardLiveTestsStart: Color,
    val cardLiveTestsEnd: Color,
    val cardDailyClassesStart: Color,
    val cardDailyClassesEnd: Color,
    val cardQuizStart: Color,
    val cardQuizEnd: Color,
    val cardCurrentAffairsStart: Color,
    val cardCurrentAffairsEnd: Color,

    val ctaGreen: Color,
    val badgeFreeGreen: Color,

    val podiumGold: Color,
    val podiumSilver: Color,
    val podiumBronze: Color,

    val textPrimary: Color,
    val textSecondary: Color,
    val textTertiary: Color
)

fun defaultAbhyaasColors(darkTheme: Boolean = true): AbhyaasCustomColors = AbhyaasCustomColors(
    brandNavy = BrandNavy,
    brandCobalt = BrandCobalt,
    brandPrimary = BrandPrimary,
    brandAccentCyan = BrandAccentCyan,
    brandSkyBlue = BrandSkyBlue,
    brandLightBlue = BrandLightBlue,
    brandPeriwinkle = BrandPeriwinkle,

    darkBackground = DarkBackground,
    darkSurface = DarkSurface,
    darkCard = DarkCard,
    darkCardElevated = DarkCardElevated,
    darkSelected = DarkSelected,
    darkBorder = DarkBorder,

    statusAnswered = StatusAnswered,
    statusNotAnswered = StatusNotAnswered,
    statusUnattempted = StatusUnattempted,
    statusNotVisited = StatusNotVisited,
    statusMarkedReview = StatusMarkedReview,
    statusAnsweredMarked = StatusAnsweredMarked,
    statusWarningTimer = StatusWarningTimer,
    statusUrgentTimer = StatusUrgentTimer,
    statusCorrect = StatusCorrect,
    statusIncorrect = StatusIncorrect,

    cardStudyNotesStart = CardStudyNotesStart,
    cardStudyNotesEnd = CardStudyNotesEnd,
    cardPYQStart = CardPYQStart,
    cardPYQEnd = CardPYQEnd,
    cardPracticeStart = CardPracticeStart,
    cardPracticeEnd = CardPracticeEnd,
    cardLiveTestsStart = CardLiveTestsStart,
    cardLiveTestsEnd = CardLiveTestsEnd,
    cardDailyClassesStart = CardDailyClassesStart,
    cardDailyClassesEnd = CardDailyClassesEnd,
    cardQuizStart = CardQuizStart,
    cardQuizEnd = CardQuizEnd,
    cardCurrentAffairsStart = CardCurrentAffairsStart,
    cardCurrentAffairsEnd = CardCurrentAffairsEnd,

    ctaGreen = CtaGreen,
    badgeFreeGreen = BadgeFreeGreen,

    podiumGold = PodiumGold,
    podiumSilver = PodiumSilver,
    podiumBronze = PodiumBronze,

    textPrimary = if (darkTheme) TextPrimaryDark else TextPrimaryLight,
    textSecondary = if (darkTheme) TextSecondaryDark else TextSecondaryLight,
    textTertiary = TextTertiaryDark
)

val LocalAbhyaasColors = staticCompositionLocalOf { defaultAbhyaasColors(true) }

// ============================================================================
// CONVENIENT THEME ACCESSOR OBJECT
// ============================================================================

object AbhyaasTheme {
    val colors: AbhyaasCustomColors
        @Composable
        @ReadOnlyComposable
        get() = LocalAbhyaasColors.current

    val typography: AbhyaasCustomTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalAbhyaasTypography.current
}

// ============================================================================
// ABHYAAS THEME COMPOSABLE
// ============================================================================

@Composable
fun AbhyaasTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is false by default to ensure Abhyaas brand identity is preserved
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as? Activity)?.window
            if (window != null) {
                window.statusBarColor = if (darkTheme) DarkBackground.toArgb() else LightBackground.toArgb()
                WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
            }
        }
    }

    CompositionLocalProvider(
        LocalAbhyaasColors provides defaultAbhyaasColors(darkTheme),
        LocalAbhyaasTypography provides defaultAbhyaasTypography()
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}
```

---

## 5. Verification Method

To independently verify the implementation once applied by the Worker:

1. **File Replacement Verification**:
   - Verify `app/src/main/java/com/example/abhyaas/ui/theme/Color.kt` contains `BrandNavy`, `StatusAnswered`, `CardStudyNotesStart`, etc.
   - Verify `app/src/main/java/com/example/abhyaas/ui/theme/Type.kt` contains `AbhyaasCustomTypography` and `defaultAbhyaasTypography()`.
   - Verify `app/src/main/java/com/example/abhyaas/ui/theme/Theme.kt` contains `dynamicColor: Boolean = false` and `AbhyaasTheme.colors`.

2. **Gradle Compilation Verification**:
   Execute the following build commands in powershell from `C:\Users\gurut\AndroidStudioProjects\Abhyaas`:
   ```powershell
   .\gradlew.bat compileDebugKotlin
   ```
   **Expected Result**:
   - Exit code: `0`
   - `BUILD SUCCESSFUL`
   - Zero compilation errors or missing symbol warnings.

3. **Assemble Verification**:
   ```powershell
   .\gradlew.bat assembleDebug
   ```
   **Expected Result**:
   - Exit code: `0`
   - Generates `app-debug.apk` without errors.

4. **Invalidation Conditions**:
   - If `dynamicColor` is left as `true`, the blueprint is violated.
   - If timer styles use proportional fonts instead of `FontFamily.Monospace`, the verification fails.
   - If legacy template aliases (`Purple80`, etc.) are removed and cause any compilation breakage, the verification fails.
