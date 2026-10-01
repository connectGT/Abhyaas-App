package com.example.abhyaas.ui.screens.exam

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.abhyaas.ui.components.QuestionBadgeStatus
import com.example.abhyaas.ui.components.QuestionStatusBadge
import com.example.abhyaas.ui.theme.BrandPrimary

/**
 * Question Status Symbols Guide Dialog matching symbol meaning.jpeg and symbol meaning 2.jpeg.
 * Details the 8 palette symbols, status tags, and action buttons during active exams.
 */
@Composable
fun SymbolsGuideDialog(
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .fillMaxHeight(0.90f),
            shape = RoundedCornerShape(16.dp),
            color = Color(0xFF1E242C),
            border = BorderStroke(1.dp, Color(0xFF334155))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                // Header notice
                Text(
                    text = "Below are the symbols you'll see throughout your test. Please review their meanings before starting the test.",
                    color = Color.White,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    lineHeight = 22.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )

                HorizontalDivider(color = Color(0xFF334155), thickness = 1.dp)

                // Table Header
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Symbol",
                        color = Color(0xFF94A3B8),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.width(105.dp)
                    )
                    Text(
                        text = "Description",
                        color = Color(0xFF94A3B8),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f)
                    )
                }

                HorizontalDivider(color = Color(0xFF334155), thickness = 1.dp)

                // Scrollable Table of 8 items
                val scrollState = rememberScrollState()
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(scrollState)
                        .padding(vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Item 1: Unattempted (Blue Square)
                    GuideRow(
                        symbolContent = {
                            QuestionStatusBadge(
                                questionNumber = 12,
                                status = QuestionBadgeStatus.UNANSWERED,
                                size = 38.dp
                            )
                        },
                        description = "You have not yet attempted this Q"
                    )

                    HorizontalDivider(color = Color(0xFF263242), thickness = 0.5.dp)

                    // Item 2: Answered (Green Square)
                    GuideRow(
                        symbolContent = {
                            QuestionStatusBadge(
                                questionNumber = 13,
                                status = QuestionBadgeStatus.ANSWERED,
                                size = 38.dp
                            )
                        },
                        description = "You have answered this Q"
                    )

                    HorizontalDivider(color = Color(0xFF263242), thickness = 0.5.dp)

                    // Item 3: Marked for review without answering (Coral Tag)
                    GuideRow(
                        symbolContent = {
                            QuestionStatusBadge(
                                questionNumber = 14,
                                status = QuestionBadgeStatus.MARKED_FOR_REVIEW,
                                size = 38.dp
                            )
                        },
                        description = "You have not answered this Q, but marked it for review later, if time permits"
                    )

                    HorizontalDivider(color = Color(0xFF263242), thickness = 0.5.dp)

                    // Item 4: Answered and marked for review (Yellow Tag)
                    GuideRow(
                        symbolContent = {
                            QuestionStatusBadge(
                                questionNumber = 15,
                                status = QuestionBadgeStatus.ANSWERED_AND_MARKED,
                                size = 38.dp
                            )
                        },
                        description = "You have answered the Q, but marked it for review later, if time permits"
                    )

                    HorizontalDivider(color = Color(0xFF263242), thickness = 0.5.dp)

                    // Item 5: Save & Next (Filled Blue Button)
                    GuideRow(
                        symbolContent = {
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = BrandPrimary,
                                modifier = Modifier.width(100.dp)
                            ) {
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp)
                                ) {
                                    Text(
                                        text = "Save & Next",
                                        color = Color.White,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        },
                        description = "Tapping will take you to next Q"
                    )

                    HorizontalDivider(color = Color(0xFF263242), thickness = 0.5.dp)

                    // Item 6: Previous (Outlined Blue Button)
                    GuideRow(
                        symbolContent = {
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = Color.Transparent,
                                border = BorderStroke(1.dp, BrandPrimary),
                                modifier = Modifier.width(90.dp)
                            ) {
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp)
                                ) {
                                    Text(
                                        text = "Previous",
                                        color = Color(0xFF38BDF8),
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        },
                        description = "Tapping will take you to previous Q"
                    )

                    HorizontalDivider(color = Color(0xFF263242), thickness = 0.5.dp)

                    // Item 7: Mark For Review (Outlined Blue Button)
                    GuideRow(
                        symbolContent = {
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = Color.Transparent,
                                border = BorderStroke(1.dp, BrandPrimary),
                                modifier = Modifier.width(100.dp)
                            ) {
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 6.dp)
                                ) {
                                    Text(
                                        text = "Mark For\nReview",
                                        color = Color(0xFF38BDF8),
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        },
                        description = "By tapping this, you can mark the Q for review later. Please note that if you answer the Q and mark for review, the Q will be treated as answered and evaluated even if you do not review it"
                    )

                    HorizontalDivider(color = Color(0xFF263242), thickness = 0.5.dp)

                    // Item 8: Unmark Review (Filled Blue Button)
                    GuideRow(
                        symbolContent = {
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = Color(0xFF1D4ED8),
                                modifier = Modifier.width(100.dp)
                            ) {
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 8.dp)
                                ) {
                                    Text(
                                        text = "Unmark Review",
                                        color = Color.White,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        },
                        description = "By tapping this, you can unmark the Q for review"
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Bottom CTA: Back to Test
                Button(
                    onClick = onDismiss,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = BrandPrimary,
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = "Back to Test",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
private fun GuideRow(
    symbolContent: @Composable () -> Unit,
    description: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.width(105.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            symbolContent()
        }
        Text(
            text = description,
            color = Color(0xFFCBD5E1),
            fontSize = 13.sp,
            lineHeight = 18.sp,
            modifier = Modifier.weight(1f)
        )
    }
}
