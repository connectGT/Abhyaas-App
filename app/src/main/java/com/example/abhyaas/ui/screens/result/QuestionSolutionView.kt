package com.example.abhyaas.ui.screens.result

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.abhyaas.data.model.Question
import com.example.abhyaas.data.model.Test
import com.example.abhyaas.data.model.TestResult
import com.example.abhyaas.ui.components.LanguageTogglePill
import com.example.abhyaas.ui.theme.*
import kotlinx.coroutines.launch

enum class SolutionFilterType(val label: String) {
    ALL("All"),
    CORRECT("Correct"),
    INCORRECT("Incorrect"),
    UNATTEMPTED("Unattempted")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionSolutionView(
    test: Test,
    initialQuestionId: Int = 1,
    testResult: TestResult? = null,
    onBackClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    var isHindi by rememberSaveable { mutableStateOf(false) }
    var isReattemptMode by rememberSaveable { mutableStateOf(false) }
    var filterType by rememberSaveable { mutableStateOf(SolutionFilterType.ALL) }
    var isFilterMenuExpanded by remember { mutableStateOf(false) }
    var isSectionMenuExpanded by remember { mutableStateOf(false) }

    val allQuestions = remember(test) { test.sections.flatMap { it.questions } }
    var currentQuestionId by rememberSaveable { mutableIntStateOf(initialQuestionId) }

    // Map of user reattempt choices: questionId -> selectedOptionIndex
    val userReattemptAnswers = remember { mutableStateMapOf<Int, Int>() }
    // Forced reveal for specific question when in reattempt mode
    val forceSolutionRevealed = remember { mutableStateMapOf<Int, Boolean>() }
    // Bookmark state map
    val bookmarkedState = remember {
        mutableStateMapOf<Int, Boolean>().apply {
            allQuestions.forEach { this[it.id] = it.isBookmarked }
        }
    }

    val currentQuestion = remember(currentQuestionId, allQuestions) {
        allQuestions.find { it.id == currentQuestionId } ?: allQuestions.first()
    }

    val currentSection = remember(currentQuestion, test) {
        test.sections.find { it.id == currentQuestion.sectionId } ?: test.sections.first()
    }

    val filteredQuestions = remember(filterType, allQuestions, testResult) {
        when (filterType) {
            SolutionFilterType.ALL -> allQuestions
            SolutionFilterType.CORRECT -> allQuestions.filter { false } // Default 0 correct in mock scorecard
            SolutionFilterType.INCORRECT -> allQuestions.filter { false }
            SolutionFilterType.UNATTEMPTED -> allQuestions // All 100 unattempted in mock scorecard
        }
    }

    val lazyListState = rememberLazyListState()

    // Scroll carousel to current question
    LaunchedEffect(currentQuestionId) {
        val index = filteredQuestions.indexOfFirst { it.id == currentQuestionId }
        if (index >= 0) {
            lazyListState.animateScrollToItem((index - 2).coerceAtLeast(0))
        }
    }

    val scrollState = rememberScrollState()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = DarkBackground,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = DarkBackgroundGradientStart,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White,
                    actionIconContentColor = Color.White
                ),
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back to Solutions"
                        )
                    }
                },
                title = {
                    Column {
                        Text(
                            text = test.title,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.clickable { isSectionMenuExpanded = true }
                        ) {
                            Text(
                                text = "All Sections",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                                color = BrandAccentCyan
                            )
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowDown,
                                contentDescription = "Sections dropdown",
                                tint = BrandAccentCyan,
                                modifier = Modifier.size(16.dp)
                            )
                            DropdownMenu(
                                expanded = isSectionMenuExpanded,
                                onDismissRequest = { isSectionMenuExpanded = false },
                                modifier = Modifier.background(DarkSurface)
                            ) {
                                DropdownMenuItem(
                                    text = { Text("All Sections", color = Color.White) },
                                    onClick = {
                                        isSectionMenuExpanded = false
                                    }
                                )
                                test.sections.forEach { section ->
                                    DropdownMenuItem(
                                        text = { Text(section.title, color = Color.White) },
                                        onClick = {
                                            isSectionMenuExpanded = false
                                            val firstQ = section.questions.firstOrNull()
                                            if (firstQ != null) {
                                                currentQuestionId = firstQ.id
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    }
                },
                actions = {
                    LanguageTogglePill(
                        language = if (isHindi) "HI" else "EN",
                        onClick = { isHindi = !isHindi }
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    IconButton(onClick = { /* Menu */ }) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Menu",
                            tint = Color.White
                        )
                    }
                }
            )
        },
        bottomBar = {
            Surface(
                color = DarkSurface,
                border = BorderStroke(1.dp, DarkBorderSubtle),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "Reattempt Mode",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White
                        )
                        Switch(
                            checked = isReattemptMode,
                            onCheckedChange = { isReattemptMode = it },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = BrandPrimary,
                                uncheckedThumbColor = Color(0xFF94A3B8),
                                uncheckedTrackColor = Color(0xFF334155)
                            )
                        )
                    }

                    // Next question circular button ->
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(BrandPrimary)
                            .clickable {
                                val currentIdx = allQuestions.indexOfFirst { it.id == currentQuestionId }
                                if (currentIdx < allQuestions.size - 1) {
                                    currentQuestionId = allQuestions[currentIdx + 1].id
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = "Next Question",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Horizontal Question Index Carousel Strip + Filters button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF131B28))
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                LazyRow(
                    state = lazyListState,
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(horizontal = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    itemsIndexed(filteredQuestions) { _, q ->
                        val isSelected = q.id == currentQuestionId
                        val qIndexInAll = allQuestions.indexOfFirst { it.id == q.id } + 1

                        Box(
                            modifier = Modifier
                                .size(38.dp)
                                .clip(CircleShape)
                                .background(if (isSelected) Color.White else Color(0xFF334155))
                                .border(
                                    width = if (isSelected) 2.dp else 0.dp,
                                    color = if (isSelected) BrandPrimary else Color.Transparent,
                                    shape = CircleShape
                                )
                                .clickable {
                                    currentQuestionId = q.id
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = qIndexInAll.toString(),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isSelected) Color(0xFF0F172A) else Color.White
                            )
                        }
                    }
                }

                // Filters Button
                Box(modifier = Modifier.padding(end = 12.dp)) {
                    Surface(
                        onClick = { isFilterMenuExpanded = true },
                        shape = RoundedCornerShape(8.dp),
                        color = Color(0xFF1E293B),
                        border = BorderStroke(1.dp, Color(0xFF334155))
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.FilterAlt,
                                contentDescription = "Filter",
                                tint = Color.White,
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = "Filters",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }

                    DropdownMenu(
                        expanded = isFilterMenuExpanded,
                        onDismissRequest = { isFilterMenuExpanded = false },
                        modifier = Modifier.background(DarkSurface)
                    ) {
                        SolutionFilterType.values().forEach { filter ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = filter.label,
                                        color = if (filter == filterType) BrandAccentCyan else Color.White,
                                        fontWeight = if (filter == filterType) FontWeight.Bold else FontWeight.Normal
                                    )
                                },
                                onClick = {
                                    filterType = filter
                                    isFilterMenuExpanded = false
                                }
                            )
                        }
                    }
                }
            }

            // Question Content Area
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(16.dp)
            ) {
                val qIndexInAll = allQuestions.indexOfFirst { it.id == currentQuestion.id } + 1

                // Question Metadata Header Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        // Question Number Chip
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF334155)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = qIndexInAll.toString(),
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }

                        // Time spent
                        Text(
                            text = "0sec",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF94A3B8)
                        )

                        // Marking Scheme (+2.0 -0.5)
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "+${currentQuestion.positiveMarks}",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF10B981)
                            )
                            Text(
                                text = "-${currentQuestion.negativeMarks}",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFEF4444)
                            )
                        }
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        IconButton(onClick = { /* Report */ }) {
                            Icon(
                                imageVector = Icons.Outlined.Warning,
                                contentDescription = "Report Question",
                                tint = Color(0xFF94A3B8),
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        val isBookmarked = bookmarkedState[currentQuestion.id] ?: false
                        IconButton(onClick = {
                            bookmarkedState[currentQuestion.id] = !isBookmarked
                        }) {
                            Icon(
                                imageVector = if (isBookmarked) Icons.Default.Bookmark else Icons.Outlined.BookmarkBorder,
                                contentDescription = "Bookmark",
                                tint = if (isBookmarked) BrandAccentCyan else Color(0xFF94A3B8),
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Direction Text if present
                val direction = if (isHindi) currentQuestion.directionTextHindi ?: currentQuestion.directionText else currentQuestion.directionText
                if (!direction.isNullOrBlank()) {
                    Text(
                        text = direction,
                        fontSize = 13.sp,
                        fontStyle = FontStyle.Italic,
                        color = Color(0xFF94A3B8),
                        lineHeight = 18.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                // Question Statement Text
                val statement = if (isHindi) currentQuestion.statementTextHindi ?: currentQuestion.statementText else currentQuestion.statementText
                Text(
                    text = statement,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White,
                    lineHeight = 22.sp
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Options List
                val userChoice = if (isReattemptMode) {
                    userReattemptAnswers[currentQuestion.id]
                } else {
                    testResult?.userAnswers?.get(currentQuestion.id)
                }
                val isForcedSolution = forceSolutionRevealed[currentQuestion.id] ?: false
                val showSolution = !isReattemptMode || isForcedSolution

                currentQuestion.options.forEachIndexed { optIndex, option ->
                    val isCorrectOption = optIndex == currentQuestion.correctOptionIndex
                    val isUserSelected = userChoice == optIndex

                    val optBgColor: Color
                    val optBorderColor: Color

                    if (showSolution) {
                        if (isCorrectOption) {
                            optBgColor = Color(0xFF0F392B)
                            optBorderColor = Color(0xFF10B981)
                        } else if (isUserSelected) {
                            optBgColor = Color(0xFF3F1B1B)
                            optBorderColor = Color(0xFFEF4444)
                        } else {
                            optBgColor = Color(0xFF161F2E)
                            optBorderColor = Color(0xFF2E3E56)
                        }
                    } else {
                        if (isUserSelected) {
                            optBgColor = Color(0xFF1A365D)
                            optBorderColor = BrandPrimary
                        } else {
                            optBgColor = Color(0xFF161F2E)
                            optBorderColor = Color(0xFF2E3E56)
                        }
                    }

                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 5.dp)
                            .clickable(enabled = isReattemptMode) {
                                userReattemptAnswers[currentQuestion.id] = optIndex
                            },
                        shape = RoundedCornerShape(12.dp),
                        color = optBgColor,
                        border = BorderStroke(if (isCorrectOption && showSolution) 1.5.dp else 1.dp, optBorderColor)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "${optIndex + 1}.",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF94A3B8),
                                modifier = Modifier.width(28.dp)
                            )
                            val optText = if (isHindi) option.textHindi ?: option.text else option.text
                            Text(
                                text = optText,
                                fontSize = 15.sp,
                                color = Color.White,
                                modifier = Modifier.weight(1f)
                            )

                            if (showSolution && isCorrectOption) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = "Correct",
                                    tint = Color(0xFF10B981),
                                    modifier = Modifier.size(20.dp)
                                )
                            } else if (showSolution && isUserSelected && !isCorrectOption) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Incorrect",
                                    tint = Color(0xFFEF4444),
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // When Reattempt Mode is ON and solution is not revealed yet
                if (isReattemptMode && !isForcedSolution) {
                    OutlinedButton(
                        onClick = {
                            forceSolutionRevealed[currentQuestion.id] = true
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(1.dp, BrandPrimary),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = BrandPrimary)
                    ) {
                        Text(
                            text = "View Solution",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Re-attempt mode is ON. Turn OFF the Re-attempt mode or re-attempt the question to see the solutions.",
                        fontSize = 12.sp,
                        color = Color(0xFF94A3B8),
                        lineHeight = 17.sp,
                        fontStyle = FontStyle.Italic
                    )
                }

                // When Solution is visible (Reattempt Mode is OFF or View Solution clicked)
                if (showSolution) {
                    Spacer(modifier = Modifier.height(12.dp))

                    // Answer summary row matching qs look after test 2.jpeg
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Correct Answer Is: ${currentQuestion.correctOptionIndex + 1}",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "${currentQuestion.percentGotRight}% got this right",
                            fontSize = 13.sp,
                            color = Color(0xFF94A3B8)
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Full SOLUTION Reasoning Section
                    Text(
                        text = "SOLUTION",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF64748B),
                        letterSpacing = 0.5.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    val explanation = if (isHindi) currentQuestion.explanationHindi ?: currentQuestion.explanation else currentQuestion.explanation

                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        color = DarkSurface,
                        border = BorderStroke(1.dp, DarkBorderSubtle)
                    ) {
                        Text(
                            text = explanation,
                            fontSize = 14.sp,
                            color = Color(0xFFE2E8F0),
                            lineHeight = 22.sp,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
}
