package com.example.abhyaas.ui.screens.result

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.abhyaas.data.model.SectionResult
import com.example.abhyaas.data.model.TestSection
import com.example.abhyaas.ui.theme.DarkBorderSubtle
import com.example.abhyaas.ui.theme.DarkSurface

/**
 * Section statistics model representing the counts displayed in the drawer:
 * Bookmarked (cyan), Answered (green), Marked/Incorrect (red), Unattempted (gray).
 */
data class SectionStats(
    val sectionId: String,
    val sectionTitle: String,
    val bookmarkedCount: Int = 0,
    val answeredCount: Int = 0,
    val incorrectCount: Int = 0,
    val unattemptedCount: Int = 25
)

/**
 * SolutionsSectionDrawerContent matching test attempt summary after test.jpeg.
 * Displays all 4 exam sections with their respective attempt breakdown indicators.
 */
@Composable
fun SolutionsSectionDrawerContent(
    sections: List<TestSection>,
    sectionBreakdowns: List<SectionResult> = emptyList(),
    selectedSectionId: String = "sec_a",
    onSectionSelected: (sectionId: String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(DarkSurface)
            .padding(vertical = 12.dp)
    ) {
        sections.forEachIndexed { index, section ->
            val result = sectionBreakdowns.find { it.sectionId == section.id }
            val bookmarkedCount = section.questions.count { it.isBookmarked }
            val answeredCount = result?.correctCount ?: 0
            val incorrectCount = result?.incorrectCount ?: 0
            val unattemptedCount = result?.unattemptedCount ?: section.questions.size

            SectionDrawerItem(
                title = section.title,
                isSelected = section.id == selectedSectionId,
                bookmarkedCount = bookmarkedCount,
                answeredCount = answeredCount,
                incorrectCount = incorrectCount,
                unattemptedCount = unattemptedCount,
                onClick = { onSectionSelected(section.id) }
            )

            if (index < sections.size - 1) {
                HorizontalDivider(
                    color = DarkBorderSubtle,
                    thickness = 1.dp,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                )
            }
        }
    }
}

@Composable
fun SectionDrawerItem(
    title: String,
    isSelected: Boolean,
    bookmarkedCount: Int,
    answeredCount: Int,
    incorrectCount: Int,
    unattemptedCount: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 20.dp, vertical = 14.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                fontSize = 17.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.SemiBold,
                color = if (isSelected) Color.White else Color(0xFFE2E8F0)
            )
            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = "Expand Section",
                tint = Color(0xFF94A3B8),
                modifier = Modifier.size(22.dp)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Bookmarked (Cyan ribbon)
            DrawerMetricBadge(
                icon = Icons.Default.Bookmark,
                iconTint = Color(0xFF00C2FF),
                count = bookmarkedCount
            )

            // Answered / Correct (Green circle)
            DrawerMetricBadge(
                icon = Icons.Default.CheckCircle,
                iconTint = Color(0xFF10B981),
                count = answeredCount
            )

            // Marked / Incorrect (Red circle)
            DrawerMetricBadge(
                icon = Icons.Default.Cancel,
                iconTint = Color(0xFFEF4444),
                count = incorrectCount
            )

            // Unattempted (Gray circle)
            DrawerMetricBadge(
                icon = Icons.AutoMirrored.Filled.Help,
                iconTint = Color(0xFF64748B),
                count = unattemptedCount
            )
        }
    }
}

@Composable
private fun DrawerMetricBadge(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconTint: Color,
    count: Int
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = iconTint,
            modifier = Modifier.size(18.dp)
        )
        Text(
            text = count.toString(),
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFFCBD5E1)
        )
    }
}

/**
 * Modal BottomSheet wrapper for the Solutions Section Switcher matching test attempt summary after test.jpeg.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SolutionsSectionBottomSheet(
    sections: List<TestSection>,
    sectionBreakdowns: List<SectionResult> = emptyList(),
    selectedSectionId: String = "sec_a",
    onSectionSelected: (sectionId: String) -> Unit = {},
    onDismissRequest: () -> Unit = {}
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        containerColor = DarkSurface,
        dragHandle = {
            Box(
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .width(40.dp)
                    .height(4.dp)
                    .background(Color(0xFF475569), RoundedCornerShape(2.dp))
            )
        }
    ) {
        SolutionsSectionDrawerContent(
            sections = sections,
            sectionBreakdowns = sectionBreakdowns,
            selectedSectionId = selectedSectionId,
            onSectionSelected = { sectionId ->
                onSectionSelected(sectionId)
                onDismissRequest()
            },
            modifier = Modifier.padding(bottom = 24.dp)
        )
    }
}
