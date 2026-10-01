package com.example.abhyaas.ui.screens.exam

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.HelpOutline
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.abhyaas.data.model.Question
import com.example.abhyaas.data.model.QuestionStatus
import com.example.abhyaas.data.model.TestSection
import com.example.abhyaas.ui.components.QuestionStatusBadge
import com.example.abhyaas.ui.theme.BrandPrimary

/**
 * Question Palette Sheet matching on test summary.jpeg.
 * Provides a 6-column grid of 25 question number chips, section tabs,
 * answered/unanswered counts, and direct question jumping.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionPaletteSheet(
    sections: List<TestSection>,
    selectedSectionIndex: Int,
    currentQuestionId: Int,
    questionStatusMap: Map<Int, QuestionStatus>,
    onSelectSection: (Int) -> Unit,
    onSelectQuestion: (question: Question) -> Unit,
    onSymbolsClick: () -> Unit,
    onInstructionsClick: () -> Unit,
    onSubmitSection: () -> Unit,
    onSubmitTest: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = Color(0xFF1E242C),
        dragHandle = {
            BottomSheetDefaults.DragHandle(
                color = Color(0xFF475569)
            )
        },
        modifier = modifier
    ) {
        QuestionPaletteContent(
            sections = sections,
            selectedSectionIndex = selectedSectionIndex,
            currentQuestionId = currentQuestionId,
            questionStatusMap = questionStatusMap,
            onSelectSection = onSelectSection,
            onSelectQuestion = { q ->
                onSelectQuestion(q)
                onDismiss()
            },
            onSymbolsClick = onSymbolsClick,
            onInstructionsClick = onInstructionsClick,
            onSubmitSection = onSubmitSection,
            onSubmitTest = onSubmitTest,
            modifier = Modifier.padding(bottom = 24.dp)
        )
    }
}

@Composable
fun QuestionPaletteContent(
    sections: List<TestSection>,
    selectedSectionIndex: Int,
    currentQuestionId: Int,
    questionStatusMap: Map<Int, QuestionStatus>,
    onSelectSection: (Int) -> Unit,
    onSelectQuestion: (question: Question) -> Unit,
    onSymbolsClick: () -> Unit,
    onInstructionsClick: () -> Unit,
    onSubmitSection: () -> Unit,
    onSubmitTest: () -> Unit,
    modifier: Modifier = Modifier
) {
    val currentSection = sections.getOrNull(selectedSectionIndex) ?: sections.firstOrNull()
    val sectionQuestions = currentSection?.questions ?: emptyList()

    val answeredCount = sectionQuestions.count { q ->
        val status = questionStatusMap[q.id] ?: QuestionStatus.NOT_VISITED
        status == QuestionStatus.ANSWERED || status == QuestionStatus.ANSWERED_AND_MARKED
    }
    val unansweredCount = sectionQuestions.size - answeredCount

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        // Top Action Links: [? Symbols] | [(i) Instructions]
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .clickable(onClick = onSymbolsClick)
                    .padding(horizontal = 10.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.HelpOutline,
                    contentDescription = "Symbols Guide",
                    tint = Color(0xFF38BDF8),
                    modifier = Modifier.size(18.dp)
                )
                Text(
                    text = "Symbols",
                    color = Color(0xFF38BDF8),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Text(
                text = "|",
                color = Color(0xFF475569),
                modifier = Modifier.padding(horizontal = 12.dp)
            )

            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .clickable(onClick = onInstructionsClick)
                    .padding(horizontal = 10.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Info,
                    contentDescription = "Instructions",
                    tint = Color(0xFF38BDF8),
                    modifier = Modifier.size(18.dp)
                )
                Text(
                    text = "Instructions",
                    color = Color(0xFF38BDF8),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Section Pill Selector: PART - A, PART - B, PART - C, PART - D
        val scrollState = rememberScrollState()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(scrollState),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            sections.forEachIndexed { index, section ->
                val isSelected = index == selectedSectionIndex
                Surface(
                    onClick = { onSelectSection(index) },
                    shape = RoundedCornerShape(8.dp),
                    color = if (isSelected) BrandPrimary else Color(0xFF161F2E),
                    border = BorderStroke(1.dp, if (isSelected) BrandPrimary else Color(0xFF334155))
                ) {
                    Text(
                        text = section.partName,
                        color = if (isSelected) Color.White else Color(0xFF94A3B8),
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Section Name (e.g. "General Intelligence")
        Text(
            text = currentSection?.title ?: "Section Questions",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Status Counters Card (matching on test summary.jpeg)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(Color(0xFF161F2E))
                .border(1.dp, Color(0xFF334155), RoundedCornerShape(10.dp))
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF10B981))
                    )
                    Text(
                        text = "Answered Qs",
                        color = Color.White,
                        fontSize = 13.5.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
                Text(
                    text = answeredCount.toString(),
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }

            HorizontalDivider(color = Color(0xFF263242), thickness = 0.5.dp)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF2563EB))
                    )
                    Text(
                        text = "Unanswered Qs",
                        color = Color.White,
                        fontSize = 13.5.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
                Text(
                    text = unansweredCount.toString(),
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 6-Column Grid of 25 Question Number Badges
        LazyVerticalGrid(
            columns = GridCells.Fixed(6),
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 260.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(sectionQuestions) { question ->
                val status = questionStatusMap[question.id] ?: QuestionStatus.NOT_VISITED
                val isCurrent = question.id == currentQuestionId
                QuestionStatusBadge(
                    questionNumber = question.questionNumber,
                    status = status,
                    isCurrent = isCurrent,
                    size = 42.dp,
                    onClick = { onSelectQuestion(question) }
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Bottom Action Buttons: [SUBMIT SECTION] [SUBMIT TEST]
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = onSubmitSection,
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
                    text = "SUBMIT SECTION",
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.5.sp
                )
            }

            Button(
                onClick = onSubmitTest,
                modifier = Modifier
                    .weight(1f)
                    .height(46.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF64748B),
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "SUBMIT TEST",
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.5.sp
                )
            }
        }
    }
}
