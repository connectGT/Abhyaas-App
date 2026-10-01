package com.example.abhyaas.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Locale

@Composable
fun TimerChip(
    remainingSeconds: Long,
    modifier: Modifier = Modifier,
    warningThresholdSeconds: Long = 300L, // 5 minutes warning state
    showHours: Boolean = true,
    label: String? = null,
    onClick: (() -> Unit)? = null
) {
    val isWarning = remainingSeconds in 1..warningThresholdSeconds
    val isExpired = remainingSeconds <= 0L

    val containerColor by animateColorAsState(
        targetValue = when {
            isExpired -> Color(0xFF7F1D1D)
            isWarning -> Color(0xFF451A1A)
            else -> Color(0xFF1E293B)
        },
        label = "timerContainerColor"
    )

    val contentColor by animateColorAsState(
        targetValue = when {
            isExpired -> Color(0xFFFCA5A5)
            isWarning -> Color(0xFFEF4444)
            else -> Color(0xFF38BDF8)
        },
        label = "timerContentColor"
    )

    val borderColor by animateColorAsState(
        targetValue = when {
            isExpired -> Color(0xFFDC2626)
            isWarning -> Color(0xFFEF4444)
            else -> Color(0xFF334155)
        },
        label = "timerBorderColor"
    )

    val formattedTime = formatTimerSeconds(remainingSeconds, showHours)

    Surface(
        onClick = { onClick?.invoke() },
        enabled = onClick != null,
        shape = RoundedCornerShape(20.dp),
        color = containerColor,
        border = BorderStroke(1.dp, borderColor),
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Icon(
                imageVector = Icons.Default.AccessTime,
                contentDescription = "Remaining Time",
                tint = contentColor,
                modifier = Modifier.size(16.dp)
            )
            if (label != null) {
                Text(
                    text = label,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    color = contentColor.copy(alpha = 0.8f)
                )
            }
            Text(
                text = formattedTime,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = if (isWarning || isExpired) contentColor else Color.White
            )
        }
    }
}

/**
 * Secondary overload taking a pre-formatted string (e.g. "00:14:54")
 */
@Composable
fun TimerChip(
    formattedTime: String,
    isWarning: Boolean,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    Surface(
        onClick = { onClick?.invoke() },
        enabled = onClick != null,
        shape = RoundedCornerShape(20.dp),
        color = if (isWarning) Color(0xFF451A1A) else Color(0xFF1E293B),
        border = BorderStroke(1.dp, if (isWarning) Color(0xFFEF4444) else Color(0xFF334155)),
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Icon(
                imageVector = Icons.Default.AccessTime,
                contentDescription = "Timer",
                tint = if (isWarning) Color(0xFFEF4444) else Color(0xFF38BDF8),
                modifier = Modifier.size(16.dp)
            )
            Text(
                text = formattedTime,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = if (isWarning) Color(0xFFEF4444) else Color.White
            )
        }
    }
}

fun formatTimerSeconds(totalSeconds: Long, showHours: Boolean = true): String {
    val nonNegative = if (totalSeconds < 0L) 0L else totalSeconds
    val hours = nonNegative / 3600
    val minutes = (nonNegative % 3600) / 60
    val seconds = nonNegative % 60

    return if (showHours || hours > 0) {
        String.format(Locale.US, "%02d:%02d:%02d", hours, minutes, seconds)
    } else {
        String.format(Locale.US, "%02d:%02d", minutes, seconds)
    }
}
