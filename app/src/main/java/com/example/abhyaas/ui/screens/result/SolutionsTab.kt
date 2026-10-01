package com.example.abhyaas.ui.screens.result

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Layers
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.abhyaas.data.model.Question
import com.example.abhyaas.data.model.Test
import com.example.abhyaas.data.model.TestResult
import com.example.abhyaas.ui.theme.*

enum class SolutionListFilter {
    ALL,
    UNATTEMPTED,
    CORRECT,
    INCORRECT
}

@Composable
fun SolutionsTab(
    test: Test,
    testResult: TestResult? = null,
    isHindi: Boolean = false,
    modifier: Modifier = Modifier
) {
    var selectedQuestionId by rememberSaveable { mutableStateOf<Int?>(null) }
    var selectedFilter by rememberSaveable { mutableStateOf(SolutionListFilter.ALL) }
    var selectedSectionId by rememberSaveable { mutableStateOf("sec_a") }
    var isSectionDrawerOpen by rememberSaveable { mutableStateOf(false) }

    // If a question is clicked, open QuestionSolutionView
    if (selectedQuestionId != null) {
        QuestionSolutionView(
            test = test,
            initialQuestionId = selectedQuestionId ?: 1,
            testResult = testResult,
            onBackClick = { selectedQuestionId = null },
            modifier = modifier
        )
        return
    }

    val currentSection = remember(test, selectedSectionId) {
        test.sections.find { it.id == selectedSectionId } ?: test.sections.first()
    }

    // Counts for filter chips
    val allQuestions = remember(test) { test.sections.flatMap { it.questions } }
    val totalCount = allQuestions.size
    val correctCount = testResult?.correctCount ?: 0
    val incorrectCount = testResult?.incorrectCount ?: 0
    val unattemptedCount = testResult?.unattemptedCount ?: (totalCount - correctCount - incorrectCount)

    // Filter questions dynamically based on testResult userAnswers
    val filteredQuestions = remember(currentSection, selectedFilter, testResult) {
        val userAnswers = testResult?.userAnswers ?: emptyMap()
        currentSection.questions.filter { question ->
            val userAnswer = userAnswers[question.id]
            when (selectedFilter) {
                SolutionListFilter.ALL -> true
                SolutionListFilter.UNATTEMPTED -> userAnswer == null
                SolutionListFilter.CORRECT -> userAnswer != null && userAnswer == question.correctOptionIndex
                SolutionListFilter.INCORRECT -> userAnswer != null && userAnswer != question.correctOptionIndex
            }
        }
    }

    val bookmarkedState = remember {
        mutableStateMapOf<Int, Boolean>().apply {
            allQuestions.forEach { this[it.id] = it.isBookmarked }
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(DarkBackground)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(12.dp))

            // Filter Chips Row matching solutions.jpeg: All (100), Unattempted (100), Correct (0), Incorrect (0)
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                item {
                    SolutionFilterChip(
                        label = "All ($totalCount)",
                        isSelected = selectedFilter == SolutionListFilter.ALL,
                        onClick = { selectedFilter = SolutionListFilter.ALL }
                    )
                }
                item {
                    SolutionFilterChip(
                        label = "Unattempted ($unattemptedCount)",
                        isSelected = selectedFilter == SolutionListFilter.UNATTEMPTED,
                        onClick = { selectedFilter = SolutionListFilter.UNATTEMPTED }
                    )
                }
                item {
                    SolutionFilterChip(
                        label = "Correct ($correctCount)",
                        isSelected = selectedFilter == SolutionListFilter.CORRECT,
                        onClick = { selectedFilter = SolutionListFilter.CORRECT }
                    )
                }
                item {
                    SolutionFilterChip(
                        label = "Incorrect ($incorrectCount)",
                        isSelected = selectedFilter == SolutionListFilter.INCORRECT,
                        onClick = { selectedFilter = SolutionListFilter.INCORRECT }
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Section Header matching solutions.jpeg
            val sectionTitle = if (isHindi) currentSection.titleHindi ?: currentSection.title else currentSection.title
            Text(
                text = sectionTitle.uppercase(),
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                letterSpacing = 0.5.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "${currentSection.questions.size} Questions",
                fontSize = 13.sp,
                color = Color(0xFF94A3B8)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // List of Question Preview Cards
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(bottom = 80.dp) // space for floating Sections pill
            ) {
                items(filteredQuestions) { question ->
                    val isBookmarked = bookmarkedState[question.id] ?: false
                    val userAnswer = testResult?.userAnswers?.get(question.id)
                    QuestionPreviewCard(
                        question = question,
                        isHindi = isHindi,
                        isBookmarked = isBookmarked,
                        userAnswer = userAnswer,
                        onBookmarkToggle = {
                            bookmarkedState[question.id] = !isBookmarked
                        },
                        onClick = {
                            selectedQuestionId = question.id
                        }
                    )
                }
            }
        }

        // Floating "Sections" pill at bottom
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 20.dp)
        ) {
            Surface(
                onClick = { isSectionDrawerOpen = true },
                shape = RoundedCornerShape(24.dp),
                color = BrandPrimary,
                shadowElevation = 8.dp,
                modifier = Modifier.height(44.dp)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Layers,
                        contentDescription = "Sections",
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = "Sections",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }

        // Section Drawer Sheet
        if (isSectionDrawerOpen) {
            SolutionsSectionBottomSheet(
                sections = test.sections,
                sectionBreakdowns = testResult?.sectionBreakdowns ?: emptyList(),
                selectedSectionId = selectedSectionId,
                onSectionSelected = { sectionId ->
                    selectedSectionId = sectionId
                },
                onDismissRequest = {
                    isSectionDrawerOpen = false
                }
            )
        }
    }
}

@Composable
private fun SolutionFilterChip(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(20.dp),
        color = if (isSelected) BrandPrimary else Color(0xFF1E2638),
        border = BorderStroke(1.dp, if (isSelected) BrandPrimary else Color(0xFF2E384D))
    ) {
        Text(
            text = label,
            fontSize = 13.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            color = if (isSelected) Color.White else Color(0xFF94A3B8),
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 7.dp)
        )
    }
}

@Composable
fun QuestionPreviewCard(
    question: Question,
    isHindi: Boolean,
    isBookmarked: Boolean,
    userAnswer: Int?,
    onBookmarkToggle: () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isCorrect = userAnswer == question.correctOptionIndex
    val isIncorrect = userAnswer != null && userAnswer != question.correctOptionIndex
    
    val borderColor = when {
        isCorrect -> Color(0xFF10B981)
        isIncorrect -> Color(0xFFEF4444)
        else -> DarkBorderSubtle
    }
    
    val circleColor = when {
        isCorrect -> Color(0xFF10B981)
        isIncorrect -> Color(0xFFEF4444)
        else -> Color(0xFFE2E8F0)
    }
    
    val circleTextColor = when {
        isCorrect || isIncorrect -> Color.White
        else -> Color(0xFF0F172A)
    }

    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        color = DarkSurface,
        border = BorderStroke(1.dp, borderColor),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(14.dp)
        ) {
            // Header Row: Question number circle, Accuracy & Time, Bookmark
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // Question index circle
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .clip(CircleShape)
                            .background(circleColor),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = question.questionNumber.toString(),
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = circleTextColor
                        )
                    }

                    // Accuracy & Optional Time spent
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        if (question.questionNumber == 2) {
                            Icon(
                                imageVector = Icons.Default.AccessTime,
                                contentDescription = null,
                                tint = Color(0xFF94A3B8),
                                modifier = Modifier.size(14.dp)
                            )
                            Text(
                                text = "00:02  |  ",
                                fontSize = 12.sp,
                                color = Color(0xFF94A3B8)
                            )
                        }
                        Text(
                            text = "${question.percentGotRight}% got it right",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF94A3B8)
                        )
                    }
                }

                // Bookmark icon
                IconButton(
                    onClick = onBookmarkToggle,
                    modifier = Modifier.size(28.dp)
                ) {
                    Icon(
                        imageVector = if (isBookmarked) Icons.Default.Bookmark else Icons.Outlined.BookmarkBorder,
                        contentDescription = "Bookmark",
                        tint = if (isBookmarked) BrandAccentCyan else Color(0xFF94A3B8),
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Question Snippet Text (2 lines truncated)
            val snippet = if (isHindi) question.statementTextHindi ?: question.statementText else question.statementText
            Text(
                text = snippet,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = Color.White,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                lineHeight = 19.sp
            )
        }
    }
}
