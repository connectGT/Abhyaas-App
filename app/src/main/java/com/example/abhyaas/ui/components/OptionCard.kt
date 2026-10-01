package com.example.abhyaas.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.abhyaas.data.model.Option

enum class OptionCardState {
    DEFAULT,        // Unselected, neutral state
    SELECTED,       // User selected during active test
    CORRECT,        // Solution view: Correct answer (green border & checkmark)
    INCORRECT,      // Solution view: User's incorrect choice (red border & cross)
    DISABLED        // Non-interactive or locked
}

@Composable
fun OptionCard(
    optionIndex: Int,       // 0-indexed (0 -> A, 1 -> B, 2 -> C, 3 -> D)
    text: String,
    modifier: Modifier = Modifier,
    textHindi: String? = null,
    state: OptionCardState = OptionCardState.DEFAULT,
    onClick: () -> Unit = {}
) {
    val optionLabel = when (optionIndex) {
        0 -> "A"
        1 -> "B"
        2 -> "C"
        3 -> "D"
        else -> (optionIndex + 1).toString()
    }

    val isSelected = state == OptionCardState.SELECTED

    val backgroundColor by animateColorAsState(
        targetValue = when (state) {
            OptionCardState.DEFAULT -> Color(0xFF161F2E)
            OptionCardState.SELECTED -> Color(0xFF1A365D)
            OptionCardState.CORRECT -> Color(0xFF064E3B)
            OptionCardState.INCORRECT -> Color(0xFF451A1A)
            OptionCardState.DISABLED -> Color(0xFF0F172A)
        },
        label = "optionBgColor"
    )

    val borderColor by animateColorAsState(
        targetValue = when (state) {
            OptionCardState.DEFAULT -> Color(0xFF2E3E56)
            OptionCardState.SELECTED -> Color(0xFF2563EB)
            OptionCardState.CORRECT -> Color(0xFF10B981)
            OptionCardState.INCORRECT -> Color(0xFFEF4444)
            OptionCardState.DISABLED -> Color(0xFF1E293B)
        },
        label = "optionBorderColor"
    )

    val labelBgColor = when (state) {
        OptionCardState.DEFAULT -> Color(0xFF1E293B)
        OptionCardState.SELECTED -> Color(0xFF2563EB)
        OptionCardState.CORRECT -> Color(0xFF10B981)
        OptionCardState.INCORRECT -> Color(0xFFEF4444)
        OptionCardState.DISABLED -> Color(0xFF334155)
    }

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .selectable(
                selected = isSelected,
                enabled = state != OptionCardState.DISABLED,
                role = Role.RadioButton,
                onClick = onClick
            ),
        shape = RoundedCornerShape(12.dp),
        color = backgroundColor,
        border = BorderStroke(if (state != OptionCardState.DEFAULT) 1.5.dp else 1.dp, borderColor)
    ) {
        Row(
            modifier = Modifier
                .padding(14.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Option Index Indicator (A, B, C, D)
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(labelBgColor),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = optionLabel,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Option Statement Text
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = text,
                    color = Color.White,
                    fontSize = 15.sp,
                    fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                    lineHeight = 20.sp
                )
                if (textHindi != null) {
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = textHindi,
                        color = Color(0xFFCBD5E1),
                        fontSize = 13.sp,
                        lineHeight = 18.sp
                    )
                }
            }

            // Trailing icon for solution verification
            when (state) {
                OptionCardState.CORRECT -> {
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF10B981)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Correct Answer",
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
                OptionCardState.INCORRECT -> {
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFEF4444)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Incorrect Option",
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
                else -> Unit
            }
        }
    }
}

@Composable
fun OptionCard(
    optionIndex: Int,
    option: Option,
    modifier: Modifier = Modifier,
    state: OptionCardState = OptionCardState.DEFAULT,
    onClick: () -> Unit = {}
) {
    OptionCard(
        optionIndex = optionIndex,
        text = option.text,
        modifier = modifier,
        textHindi = option.textHindi,
        state = state,
        onClick = onClick
    )
}
