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
