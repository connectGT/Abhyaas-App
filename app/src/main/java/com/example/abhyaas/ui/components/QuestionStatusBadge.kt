package com.example.abhyaas.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.abhyaas.data.model.QuestionStatus

/**
 * Question status enum matching the official symbols in symbol meaning.jpeg
 */
enum class QuestionBadgeStatus {
    NOT_VISITED,            // Gray square
    UNANSWERED,             // Solid blue square (12)
    ANSWERED,               // Solid green square (13)
    MARKED_FOR_REVIEW,      // Coral/Red ribbon with triangular tag pointer (14 ▲)
    ANSWERED_AND_MARKED     // Amber/Yellow ribbon with triangular tag pointer (15 ▲)
}

fun QuestionStatus.toBadgeStatus(): QuestionBadgeStatus = when (this) {
    QuestionStatus.NOT_VISITED -> QuestionBadgeStatus.NOT_VISITED
    QuestionStatus.UNANSWERED -> QuestionBadgeStatus.UNANSWERED
    QuestionStatus.ANSWERED -> QuestionBadgeStatus.ANSWERED
    QuestionStatus.MARKED_FOR_REVIEW -> QuestionBadgeStatus.MARKED_FOR_REVIEW
    QuestionStatus.ANSWERED_AND_MARKED -> QuestionBadgeStatus.ANSWERED_AND_MARKED
}

/**
 * Custom ribbon shape with a bottom triangular notch/tag pointer matching symbol meaning.jpeg
 */
val RibbonTagShape = GenericShape { size, _ ->
    val r = 8f
    val w = size.width
    val h = size.height
    val pointerH = h * 0.18f
    val bodyH = h - pointerH

    // Top-left round corner
    moveTo(0f, r)
    quadraticTo(0f, 0f, r, 0f)
    // Top edge
    lineTo(w - r, 0f)
    // Top-right round corner
    quadraticTo(w, 0f, w, r)
    // Right edge
    lineTo(w, bodyH)
    // Bottom-right edge to center pointer
    lineTo(w * 0.5f, h)
    // Center pointer to bottom-left edge
    lineTo(0f, bodyH)
    // Left edge back to top
    close()
}

@Composable
fun QuestionStatusBadge(
    questionNumber: Int,
    status: QuestionBadgeStatus,
    modifier: Modifier = Modifier,
    isCurrent: Boolean = false,
    size: Dp = 40.dp,
    onClick: () -> Unit = {}
) {
    val backgroundColor = when (status) {
        QuestionBadgeStatus.NOT_VISITED -> Color(0xFF334155)
        QuestionBadgeStatus.UNANSWERED -> Color(0xFF2563EB)         // Blue square
        QuestionBadgeStatus.ANSWERED -> Color(0xFF10B981)           // Green square
        QuestionBadgeStatus.MARKED_FOR_REVIEW -> Color(0xFFEF4444)  // Coral/Red ribbon
        QuestionBadgeStatus.ANSWERED_AND_MARKED -> Color(0xFFF59E0B) // Amber/Yellow ribbon
    }

    val isRibbon = status == QuestionBadgeStatus.MARKED_FOR_REVIEW ||
            status == QuestionBadgeStatus.ANSWERED_AND_MARKED

    val badgeShape = if (isRibbon) RibbonTagShape else RoundedCornerShape(8.dp)

    val currentBorderModifier = if (isCurrent) {
        Modifier.border(2.dp, Color(0xFF38BDF8), badgeShape)
    } else {
        Modifier
    }

    Box(
        modifier = modifier
            .size(size)
            .then(currentBorderModifier)
            .clip(badgeShape)
            .background(backgroundColor)
            .clickable(onClick = onClick),
        contentAlignment = if (isRibbon) Alignment.TopCenter else Alignment.Center
    ) {
        Text(
            text = questionNumber.toString(),
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = if (size > 36.dp) 14.sp else 12.sp,
            modifier = if (isRibbon) Modifier.padding(top = (size * 0.15f)) else Modifier
        )
    }
}

@Composable
fun QuestionStatusBadge(
    questionNumber: Int,
    status: QuestionStatus,
    modifier: Modifier = Modifier,
    isCurrent: Boolean = false,
    size: Dp = 40.dp,
    onClick: () -> Unit = {}
) {
    QuestionStatusBadge(
        questionNumber = questionNumber,
        status = status.toBadgeStatus(),
        modifier = modifier,
        isCurrent = isCurrent,
        size = size,
        onClick = onClick
    )
}

/**
 * Question Status Legend item used in SymbolsGuideDialog (matching symbol meaning.jpeg)
 */
@Composable
fun QuestionStatusLegendItem(
    status: QuestionBadgeStatus,
    sampleNumber: Int,
    title: String,
    description: String? = null,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        QuestionStatusBadge(
            questionNumber = sampleNumber,
            status = status,
            size = 38.dp
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                color = Color.White
            )
            if (description != null) {
                Text(
                    text = description,
                    fontSize = 12.sp,
                    color = Color(0xFF94A3B8)
                )
            }
        }
    }
}
