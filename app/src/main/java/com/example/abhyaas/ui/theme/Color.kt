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