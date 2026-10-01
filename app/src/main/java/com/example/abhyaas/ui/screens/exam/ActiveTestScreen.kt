package com.example.abhyaas.ui.screens.exam

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.abhyaas.data.model.Question
import com.example.abhyaas.data.model.QuestionStatus
import com.example.abhyaas.data.model.defaultTestInstructions
import com.example.abhyaas.ui.components.OptionCard
import com.example.abhyaas.ui.components.OptionCardState
import com.example.abhyaas.ui.components.TimerChip
import com.example.abhyaas.ui.components.formatTimerSeconds
import com.example.abhyaas.ui.theme.BrandPrimary
import com.example.abhyaas.ui.viewmodel.ActiveTestViewModel
import kotlinx.coroutines.delay

/**
 * Full-screen Active Test Taking Screen matching qs on test timer.jpeg and on test options.jpeg.
 * Includes live countdown timer, bilingual switcher, per-question timer, question palette,
 * section switching tabs, bookmarking, and responsive option selection.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActiveTestScreen(
    testId: String = "nayab_tehsildar_2026_mock_01",
    initialLanguage: String = "English",
    onBackClick: () -> Unit = {},
    onSubmitTest: (testId: String) -> Unit = {},
    viewModel: ActiveTestViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(testId, initialLanguage) {
        viewModel.loadTest(testId, initialLanguage)
    }

    val test = uiState.test
    val allQuestions: List<Question> = uiState.questions

    if (uiState.isLoading && uiState.test == null) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF0B111A)),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = BrandPrimary)
        }
        return
    }

    if (allQuestions.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF0B111A)),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = BrandPrimary)
        }
        return
    }

    // Mark first question as visited/unanswered if not visited yet
    LaunchedEffect(uiState.questions) {
        if (uiState.questions.isNotEmpty()) {
            val firstId = uiState.questions.first().id
            if (uiState.questionStatuses[firstId] == null || uiState.questionStatuses[firstId] == QuestionStatus.NOT_VISITED) {
                viewModel.navigateToQuestion(0)
            }
        }
    }

    // Safe non-null unwrap — guaranteed by loading guards above
    val safeTest = test ?: return

    // Dialog & Sheet State
    var showPaletteSheet by remember { mutableStateOf(false) }
    var showSymbolsDialog by remember { mutableStateOf(false) }
    var showInstructionsDialog by remember { mutableStateOf(false) }
    var showSubmitDialog by remember { mutableStateOf(false) }
    var showPauseDialog by remember { mutableStateOf(false) }
    var showReportDialog by remember { mutableStateOf(false) }

    // Interactive States
    val perQuestionSeconds = remember { mutableStateMapOf<Int, Long>() }
    val favoriteQuestions = remember { mutableStateSetOf<Int>() }

    // Live countdown ticker & per-question timer
    LaunchedEffect(uiState.isPaused, uiState.isSubmitted) {
        while (!uiState.isPaused && !uiState.isSubmitted && uiState.timeRemainingSeconds > 0) {
            delay(1000L)
            viewModel.tickTimer()
            val currentQ = allQuestions.getOrNull(uiState.currentQuestionIndex)
            if (currentQ != null) {
                val currentSpent = perQuestionSeconds[currentQ.id] ?: 0L
                perQuestionSeconds[currentQ.id] = currentSpent + 1L
            }
        }
        if (uiState.timeRemainingSeconds <= 0 && !uiState.isSubmitted) {
            showSubmitDialog = true
        }
    }

    // Auto navigate to result when submitted
    LaunchedEffect(uiState.isSubmitted) {
        if (uiState.isSubmitted) {
            onSubmitTest(safeTest.id)
        }
    }

    val currentQuestion = allQuestions.getOrNull(uiState.currentQuestionIndex) ?: allQuestions.first()

    // Determine current section index
    val currentSectionIndex = remember(uiState.currentQuestionIndex, safeTest.sections) {
        val idx = safeTest.sections.indexOfFirst { it.id == currentQuestion.sectionId }
        if (idx != -1) idx else 0
    }

    fun advanceToNextQuestion() {
        if (uiState.currentQuestionIndex < allQuestions.size - 1) {
            viewModel.navigateToQuestion(uiState.currentQuestionIndex + 1)
        }
    }

    fun jumpToGlobalIndex(index: Int) {
        if (index in allQuestions.indices) {
            viewModel.navigateToQuestion(index)
        }
    }

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF0F172A))
                    .statusBarsPadding()
            ) {
                // Top Bar: [Pause] [TimerChip] [Test Title] [E/अ] [Palette ≡]
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Pause Button
                    Box(
                        modifier = Modifier
                            .size(34.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF1E293B))
                            .border(1.dp, Color(0xFF334155), CircleShape)
                            .clickable {
                                viewModel.togglePause()
                                showPauseDialog = true
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Pause,
                            contentDescription = "Pause Test",
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    // Monospace Countdown Timer Chip (< 5 mins triggers red warning)
                    TimerChip(
                        remainingSeconds = uiState.timeRemainingSeconds.toLong(),
                        warningThresholdSeconds = 300L,
                        showHours = true,
                        modifier = Modifier
                    )

                    // Test Title
                    Text(
                        text = safeTest.title,
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
                    )

                    // Bilingual Toggle [E/अ]
                    Surface(
                        onClick = { viewModel.toggleLanguage() },
                        shape = RoundedCornerShape(6.dp),
                        color = if (uiState.isHindi) BrandPrimary else Color(0xFF1E293B),
                        border = BorderStroke(1.dp, if (uiState.isHindi) BrandPrimary else Color(0xFF475569))
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = "E",
                                color = if (!uiState.isHindi) Color(0xFF38BDF8) else Color.White.copy(alpha = 0.7f),
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            )
                            Text(
                                text = "अ",
                                color = if (uiState.isHindi) Color.White else Color(0xFF94A3B8),
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            )
                        }
                    }

                    // Question Palette Toggle Button (≡)
                    IconButton(
                        onClick = { showPaletteSheet = true },
                        modifier = Modifier.size(34.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Question Palette",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }

                // Section Tabs Row (4 Sections: PART - A, B, C, D)
                ScrollableTabRow(
                    selectedTabIndex = currentSectionIndex,
                    containerColor = Color(0xFF0F172A),
                    contentColor = Color.White,
                    edgePadding = 12.dp,
                    indicator = { tabPositions ->
                        if (currentSectionIndex in tabPositions.indices) {
                            TabRowDefaults.SecondaryIndicator(
                                modifier = Modifier.tabIndicatorOffset(tabPositions[currentSectionIndex]),
                                color = Color.White,
                                height = 2.dp
                            )
                        }
                    },
                    divider = {
                        HorizontalDivider(color = Color(0xFF1E293B), thickness = 1.dp)
                    }
                ) {
                    safeTest.sections.forEachIndexed { index, section ->
                        val isSelected = index == currentSectionIndex
                        val tabTitle = if (uiState.isHindi && section.titleHindi != null) {
                            "${section.partName} (${section.titleHindi})"
                        } else {
                            "${section.partName} (${section.title})"
                        }
                        Tab(
                            selected = isSelected,
                            onClick = {
                                val firstSecQ = section.questions.firstOrNull()
                                if (firstSecQ != null) {
                                    val globalIdx = allQuestions.indexOfFirst { it.id == firstSecQ.id }
                                    if (globalIdx != -1) {
                                        jumpToGlobalIndex(globalIdx)
                                    }
                                }
                            },
                            text = {
                                Text(
                                    text = tabTitle,
                                    fontSize = 13.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                    color = if (isSelected) Color.White else Color(0xFF94A3B8)
                                )
                            }
                        )
                    }
                }

                // Sub-Header Row: [Total Questions Answered: X] [Last 15 Mins]
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 14.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val answeredCount = allQuestions.count { q ->
                        val st = uiState.questionStatuses[q.id]
                        st == QuestionStatus.ANSWERED || st == QuestionStatus.ANSWERED_AND_MARKED
                    }

                    // Total Questions Answered Pill
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color(0xFF161F2E))
                            .border(1.dp, Color(0xFF334155), RoundedCornerShape(16.dp))
                            .padding(horizontal = 10.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = "Total Questions Answered:",
                            color = Color(0xFFCBD5E1),
                            fontSize = 11.5.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Box(
                            modifier = Modifier
                                .size(20.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFD97706)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = answeredCount.toString(),
                                color = Color.White,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    // Last 15 Mins Warning Pill
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = Color(0xFF451A1A),
                        border = BorderStroke(1.dp, Color(0xFFEF4444).copy(alpha = 0.6f))
                    ) {
                        Text(
                            text = "Last 15 Mins",
                            color = Color(0xFFEF4444),
                            fontSize = 11.5.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }
                }

                HorizontalDivider(color = Color(0xFF1E293B), thickness = 1.dp)
            }
        },
        bottomBar = {
            // Bottom Action Bar: [Mark For Review] [Save & Next]
            Surface(
                color = Color(0xFF0F172A),
                border = BorderStroke(1.dp, Color(0xFF1E293B)),
                modifier = Modifier.fillMaxWidth().navigationBarsPadding()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Outlined Blue Button: "Mark For Review"
                    OutlinedButton(
                        onClick = {
                            viewModel.markForReview(currentQuestion.id)
                            advanceToNextQuestion()
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(46.dp),
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(1.2.dp, BrandPrimary),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Color(0xFF38BDF8)
                        )
                    ) {
                        Text(
                            text = "Mark For Review",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    // Filled Blue Button: "Save & Next"
                    Button(
                        onClick = {
                            if (uiState.currentQuestionIndex == allQuestions.size - 1) {
                                showSubmitDialog = true
                            } else {
                                advanceToNextQuestion()
                            }
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(46.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = BrandPrimary,
                            contentColor = Color.White
                        )
                    ) {
                        Text(
                            text = "Save & Next",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        },
        containerColor = Color(0xFF0B111A)
    ) { innerPadding ->
        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(scrollState)
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            // Question Header Row: [Q# Box] | [Clock 00:08] ... [!] [Bookmark] [Star]
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Question Number Badge
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(BrandPrimary),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = currentQuestion.questionNumber.toString(),
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                // Vertical Divider
                Box(
                    modifier = Modifier
                        .width(1.dp)
                        .height(20.dp)
                        .background(Color(0xFF475569))
                )

                Spacer(modifier = Modifier.width(8.dp))

                // Per-Question Timer
                val timeSpent = perQuestionSeconds[currentQuestion.id] ?: 0L
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.AccessTime,
                        contentDescription = "Question Time",
                        tint = Color(0xFF94A3B8),
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = formatTimerSeconds(timeSpent, showHours = false),
                        color = Color(0xFF94A3B8),
                        fontFamily = FontFamily.Monospace,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                // Report Warning Icon (!)
                IconButton(
                    onClick = { showReportDialog = true },
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Warning,
                        contentDescription = "Report Question",
                        tint = Color(0xFF94A3B8),
                        modifier = Modifier.size(20.dp)
                    )
                }

                // Bookmark Ribbon Icon
                val isBookmarked = uiState.bookmarkedQuestions.contains(currentQuestion.id)
                IconButton(
                    onClick = {
                        viewModel.toggleBookmark(currentQuestion.id)
                    },
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = if (isBookmarked) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
                        contentDescription = "Bookmark",
                        tint = if (isBookmarked) Color(0xFF38BDF8) else Color(0xFF94A3B8),
                        modifier = Modifier.size(22.dp)
                    )
                }

                // Star Favorite Icon
                val isFavorite = favoriteQuestions.contains(currentQuestion.id)
                IconButton(
                    onClick = {
                        if (isFavorite) {
                            favoriteQuestions.remove(currentQuestion.id)
                        } else {
                            favoriteQuestions.add(currentQuestion.id)
                        }
                    },
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Filled.Star else Icons.Outlined.StarBorder,
                        contentDescription = "Favorite",
                        tint = if (isFavorite) Color(0xFFF59E0B) else Color(0xFF94A3B8),
                        modifier = Modifier.size(22.dp)
                    )
                }
            }

            HorizontalDivider(color = Color(0xFF1E293B), thickness = 1.dp)

            Spacer(modifier = Modifier.height(14.dp))

            // Direction Text (e.g. syllogisms, courses of action)
            val directionText = if (uiState.isHindi && currentQuestion.directionTextHindi != null) {
                currentQuestion.directionTextHindi
            } else {
                currentQuestion.directionText
            }

            if (!directionText.isNullOrBlank()) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = if (uiState.isHindi) "निर्देश:" else "Direction:",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = directionText,
                        color = Color(0xFFCBD5E1),
                        fontSize = 13.5.sp,
                        fontStyle = FontStyle.Italic,
                        lineHeight = 20.sp
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }

            // Statement / Problem Text
            val statementText = if (uiState.isHindi && currentQuestion.statementTextHindi != null) {
                currentQuestion.statementTextHindi
            } else {
                currentQuestion.statementText
            }

            Text(
                text = statementText,
                color = Color.White,
                fontSize = 14.5.sp,
                fontWeight = FontWeight.Normal,
                lineHeight = 22.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            // 4 Selectable Option Cards using OptionCard component
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                currentQuestion.options.forEachIndexed { index, option ->
                    val isSelected = uiState.selectedAnswers[currentQuestion.id] == index
                    val optionText = if (uiState.isHindi && option.textHindi != null) {
                        option.textHindi
                    } else {
                        option.text
                    }

                    OptionCard(
                        optionIndex = index,
                        text = optionText,
                        state = if (isSelected) OptionCardState.SELECTED else OptionCardState.DEFAULT,
                        onClick = {
                            if (isSelected) {
                                viewModel.clearAnswer(currentQuestion.id)
                            } else {
                                viewModel.selectAnswer(currentQuestion.id, index)
                            }
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }

    // Question Palette Drawer / BottomSheet
    if (showPaletteSheet) {
        QuestionPaletteSheet(
            sections = safeTest.sections,
            selectedSectionIndex = currentSectionIndex,
            currentQuestionId = currentQuestion.id,
            questionStatusMap = uiState.questionStatuses,
            onSelectSection = { secIdx ->
                val firstQ = safeTest.sections.getOrNull(secIdx)?.questions?.firstOrNull()
                if (firstQ != null) {
                    val globalIdx = allQuestions.indexOfFirst { it.id == firstQ.id }
                    if (globalIdx != -1) {
                        jumpToGlobalIndex(globalIdx)
                    }
                }
            },
            onSelectQuestion = { selectedQ ->
                val globalIdx = allQuestions.indexOfFirst { it.id == selectedQ.id }
                if (globalIdx != -1) {
                    jumpToGlobalIndex(globalIdx)
                }
            },
            onSymbolsClick = {
                showSymbolsDialog = true
            },
            onInstructionsClick = {
                showInstructionsDialog = true
            },
            onSubmitSection = {
                // Advance to next section or prompt submit
                if (currentSectionIndex < safeTest.sections.size - 1) {
                    val nextSec = safeTest.sections[currentSectionIndex + 1]
                    val firstQ = nextSec.questions.firstOrNull()
                    if (firstQ != null) {
                        val globalIdx = allQuestions.indexOfFirst { it.id == firstQ.id }
                        if (globalIdx != -1) {
                            jumpToGlobalIndex(globalIdx)
                        }
                    }
                    showPaletteSheet = false
                } else {
                    showPaletteSheet = false
                    showSubmitDialog = true
                }
            },
            onSubmitTest = {
                showPaletteSheet = false
                showSubmitDialog = true
            },
            onDismiss = { showPaletteSheet = false }
        )
    }

    // Symbols Guide Dialog
    if (showSymbolsDialog) {
        SymbolsGuideDialog(
            onDismiss = { showSymbolsDialog = false }
        )
    }

    // Exam Submission Confirmation Dialog
    if (showSubmitDialog) {
        val answeredCount = allQuestions.count { q ->
            uiState.questionStatuses[q.id] == QuestionStatus.ANSWERED
        }
        val answeredAndMarkedCount = allQuestions.count { q ->
            uiState.questionStatuses[q.id] == QuestionStatus.ANSWERED_AND_MARKED
        }
        val markedCount = allQuestions.count { q ->
            uiState.questionStatuses[q.id] == QuestionStatus.MARKED_FOR_REVIEW
        }
        val unansweredCount = allQuestions.size - (answeredCount + answeredAndMarkedCount + markedCount)

        SubmitConfirmDialog(
            testTitle = safeTest.title,
            answeredCount = answeredCount,
            unansweredCount = unansweredCount.coerceAtLeast(0),
            markedForReviewCount = markedCount,
            answeredAndMarkedCount = answeredAndMarkedCount,
            remainingTimeFormatted = formatTimerSeconds(uiState.timeRemainingSeconds.toLong(), showHours = true),
            onDismiss = { showSubmitDialog = false },
            onConfirmSubmit = {
                showSubmitDialog = false
                viewModel.submitTest {
                    onSubmitTest(safeTest.id)
                }
            }
        )
    }

    // Pause Dialog
    if (showPauseDialog) {
        AlertDialog(
            onDismissRequest = {
                if (uiState.isPaused) viewModel.togglePause()
                showPauseDialog = false
            },
            title = {
                Text("Examination Paused", color = Color.White, fontWeight = FontWeight.Bold)
            },
            text = {
                Text(
                    "The countdown timer has been paused. You can resume your test when you are ready.",
                    color = Color(0xFFCBD5E1),
                    fontSize = 14.sp
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (uiState.isPaused) viewModel.togglePause()
                        showPauseDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = BrandPrimary)
                ) {
                    Text("Resume Test", fontWeight = FontWeight.Bold)
                }
            },
            containerColor = Color(0xFF1E242C)
        )
    }

    // Instructions Dialog
    if (showInstructionsDialog) {
        AlertDialog(
            onDismissRequest = { showInstructionsDialog = false },
            title = {
                Text("Test Instructions", color = Color.White, fontWeight = FontWeight.Bold)
            },
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 300.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    defaultTestInstructions.forEach { rule ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Text("• ", color = Color(0xFF38BDF8), fontWeight = FontWeight.Bold)
                            Text(rule, color = Color(0xFFCBD5E1), fontSize = 13.sp, lineHeight = 18.sp)
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = { showInstructionsDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = BrandPrimary)
                ) {
                    Text("Close", fontWeight = FontWeight.Bold)
                }
            },
            containerColor = Color(0xFF1E242C)
        )
    }

    // Report Question Dialog
    if (showReportDialog) {
        AlertDialog(
            onDismissRequest = { showReportDialog = false },
            title = {
                Text("Report Question #${currentQuestion.questionNumber}", color = Color.White, fontWeight = FontWeight.Bold)
            },
            text = {
                Text(
                    "Issue has been flagged for question verification by our subject matter experts.",
                    color = Color(0xFFCBD5E1),
                    fontSize = 14.sp
                )
            },
            confirmButton = {
                Button(
                    onClick = { showReportDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = BrandPrimary)
                ) {
                    Text("OK", fontWeight = FontWeight.Bold)
                }
            },
            containerColor = Color(0xFF1E242C)
        )
    }
}
