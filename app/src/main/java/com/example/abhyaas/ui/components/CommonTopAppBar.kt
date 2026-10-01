package com.example.abhyaas.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

enum class NavIconType {
    None,
    Back,
    Menu,
    Close,
    Pause
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonTopAppBar(
    title: String,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    navIconType: NavIconType = NavIconType.Back,
    onNavClick: () -> Unit = {},
    showSearch: Boolean = false,
    onSearchClick: () -> Unit = {},
    showAvatar: Boolean = false,
    onAvatarClick: () -> Unit = {},
    showLanguageToggle: Boolean = false,
    currentLanguage: String = "EN",
    onLanguageToggleClick: () -> Unit = {},
    showPaletteTrigger: Boolean = false,
    onPaletteTriggerClick: () -> Unit = {},
    containerColor: Color = Color(0xFF0F172A),
    contentColor: Color = Color.White,
    customTitleContent: (@Composable () -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {}
) {
    TopAppBar(
        modifier = modifier,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = containerColor,
            titleContentColor = contentColor,
            navigationIconContentColor = contentColor,
            actionIconContentColor = contentColor
        ),
        navigationIcon = {
            when (navIconType) {
                NavIconType.Back -> {
                    IconButton(onClick = onNavClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Navigate Back",
                            tint = contentColor
                        )
                    }
                }
                NavIconType.Menu -> {
                    IconButton(onClick = onNavClick) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Open Drawer Menu",
                            tint = contentColor
                        )
                    }
                }
                NavIconType.Close -> {
                    IconButton(onClick = onNavClick) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = contentColor
                        )
                    }
                }
                NavIconType.Pause -> {
                    IconButton(onClick = onNavClick) {
                        Icon(
                            imageVector = Icons.Default.Pause,
                            contentDescription = "Pause Exam",
                            tint = contentColor
                        )
                    }
                }
                NavIconType.None -> Unit
            }
        },
        title = {
            if (customTitleContent != null) {
                customTitleContent()
            } else {
                Column {
                    Text(
                        text = title,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = contentColor,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    if (subtitle != null) {
                        Text(
                            text = subtitle,
                            fontSize = 12.sp,
                            color = contentColor.copy(alpha = 0.7f),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        },
        actions = {
            if (showLanguageToggle) {
                LanguageTogglePill(
                    language = currentLanguage,
                    onClick = onLanguageToggleClick
                )
                Spacer(modifier = Modifier.width(4.dp))
            }
            if (showSearch) {
                IconButton(onClick = onSearchClick) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = contentColor
                    )
                }
            }
            if (showPaletteTrigger) {
                IconButton(onClick = onPaletteTriggerClick) {
                    Icon(
                        imageVector = Icons.Default.GridView,
                        contentDescription = "Question Palette",
                        tint = contentColor
                    )
                }
            }
            if (showAvatar) {
                Box(
                    modifier = Modifier
                        .padding(end = 12.dp)
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF2563EB))
                        .clickable(onClick = onAvatarClick),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "User Profile",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
            actions()
        }
    )
}

/**
 * Dropdown exam title item used in Home and Main tabs: "ABHYAS | SSC CGL ▾"
 */
@Composable
fun ExamTitleDropdown(
    brandName: String = "ABHYAS",
    examName: String = "SSC CGL",
    onExamClick: () -> Unit = {}
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable(onClick = onExamClick)
    ) {
        Text(
            text = brandName,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 18.sp,
            color = Color.White
        )
        Spacer(modifier = Modifier.width(8.dp))
        Box(
            modifier = Modifier
                .height(16.dp)
                .width(1.dp)
                .background(Color(0xFF64748B))
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = examName,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFFCBD5E1),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Icon(
            imageVector = Icons.Default.KeyboardArrowDown,
            contentDescription = "Select Exam",
            tint = Color(0xFF94A3B8),
            modifier = Modifier.size(18.dp)
        )
    }
}

/**
 * Bilingual toggle pill: "[E / अ]" or "[🌐 EN ▾]"
 */
@Composable
fun LanguageTogglePill(
    language: String = "EN",
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        color = Color(0xFF1E293B),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF334155)),
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Translate,
                contentDescription = "Language",
                tint = Color(0xFF38BDF8),
                modifier = Modifier.size(14.dp)
            )
            Text(
                text = language,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = null,
                tint = Color(0xFF94A3B8),
                modifier = Modifier.size(12.dp)
            )
        }
    }
}
