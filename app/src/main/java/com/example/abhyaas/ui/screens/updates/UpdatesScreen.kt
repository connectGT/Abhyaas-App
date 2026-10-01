package com.example.abhyaas.ui.screens.updates

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.abhyaas.data.model.ExamUpdateItem
import com.example.abhyaas.data.model.UpdateCategory
import com.example.abhyaas.ui.components.CommonTopAppBar
import com.example.abhyaas.ui.components.NavIconType
import com.example.abhyaas.ui.theme.*
import com.example.abhyaas.ui.viewmodel.UpdatesViewModel
import kotlinx.coroutines.launch

@Composable
fun UpdatesScreen(
    onMenuClick: () -> Unit = {},
    onAvatarClick: () -> Unit = {},
    onSearchClick: () -> Unit = {},
    viewModel: UpdatesViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val filterScrollState = rememberScrollState()

    val categories = uiState.categories
    val selectedCategory = uiState.selectedCategory
    val updates = uiState.updates

    Scaffold(
        topBar = {
            CommonTopAppBar(
                title = "ABHYAS | Updates",
                subtitle = "Exam notifications & official PDFs",
                navIconType = NavIconType.Menu,
                onNavClick = onMenuClick,
                showSearch = true,
                onSearchClick = onSearchClick,
                showAvatar = true,
                onAvatarClick = onAvatarClick,
                containerColor = DarkBackgroundGradientStart
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = DarkBackground
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(AppBackgroundBrush)
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(4.dp))

                // Hero Card: "Stay Updated" (updates section.png)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(PassBannerBrush)
                        .padding(18.dp)
                ) {
                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Campaign,
                                contentDescription = null,
                                tint = BrandAccentCyan,
                                modifier = Modifier.size(24.dp)
                            )
                            Text(
                                text = "Stay Updated",
                                color = Color.White,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "Latest exam notifications, official documents, and important updates all in one place.",
                            color = Color.White.copy(alpha = 0.9f),
                            fontSize = 13.sp,
                            lineHeight = 18.sp
                        )
                    }
                }
            }

            // Horizontal Filter Chips Row
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(filterScrollState),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    categories.forEach { cat ->
                        val isSelected = selectedCategory == cat
                        Surface(
                            onClick = { viewModel.loadUpdates(cat) },
                            shape = RoundedCornerShape(20.dp),
                            color = if (isSelected) BrandPrimary else DarkSurface,
                            border = androidx.compose.foundation.BorderStroke(
                                1.dp,
                                if (isSelected) BrandPrimaryLight else DarkBorder
                            )
                        ) {
                            Text(
                                text = cat.displayName,
                                color = if (isSelected) Color.White else TextSecondaryDark,
                                fontSize = 12.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 7.dp)
                            )
                        }
                    }
                }
            }

            // Section Header: "Latest Updates"
            item {
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
                                .width(4.dp)
                                .height(18.dp)
                                .clip(RoundedCornerShape(2.dp))
                                .background(BrandAccentCyan)
                        )
                        Text(
                            text = "Latest Updates (${updates.size})",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.FilterList,
                            contentDescription = "Filter",
                            tint = BrandAccentCyan,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = "Filter ▾",
                            color = BrandAccentCyan,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }

            if (uiState.isLoading && updates.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = BrandAccentCyan)
                    }
                }
            }

            // List of Update Cards (updates section.png)
            items(updates, key = { it.id }) { item ->
                UpdateCardItem(
                    item = item,
                    onActionClick = {
                        scope.launch {
                            val msg = when {
                                item.pdfSize != null -> "Downloading ${item.title} (${item.pdfSize})..."
                                item.actionText == "Notify Me" -> "Notification alert set for ${item.title}!"
                                else -> "Opening details for ${item.title}..."
                            }
                            snackbarHostState.showSnackbar(msg)
                        }
                    }
                )
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun UpdateCardItem(
    item: ExamUpdateItem,
    onActionClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .border(1.dp, DarkBorder, RoundedCornerShape(14.dp)),
        colors = CardDefaults.cardColors(containerColor = DarkSurface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Top badges row: Category Pill + Pinned badge
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(4.dp),
                    color = BrandPrimary.copy(alpha = 0.2f),
                    border = androidx.compose.foundation.BorderStroke(1.dp, BrandPrimaryLight.copy(alpha = 0.5f))
                ) {
                    Text(
                        text = item.category.name.replace("_", " "),
                        color = BrandAccentCyan,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    if (item.isPinned) {
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = Color(0xFFB45309).copy(alpha = 0.2f),
                            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFF59E0B))
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.PushPin,
                                    contentDescription = "Pinned",
                                    tint = Color(0xFFF59E0B),
                                    modifier = Modifier.size(11.dp)
                                )
                                Spacer(modifier = Modifier.width(3.dp))
                                Text("PINNED", color = Color(0xFFF59E0B), fontSize = 9.sp, fontWeight = FontWeight.Black)
                            }
                        }
                    }

                    Text(
                        text = item.date,
                        color = TextTertiaryDark,
                        fontSize = 11.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Title
            Text(
                text = item.title,
                color = TextPrimaryDark,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Description
            Text(
                text = item.description,
                color = TextSecondaryDark,
                fontSize = 12.sp,
                lineHeight = 17.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Divider
            HorizontalDivider(color = DarkBorderSubtle)

            Spacer(modifier = Modifier.height(10.dp))

            // Bottom Action Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (item.pdfSize != null) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.PictureAsPdf,
                            contentDescription = "PDF",
                            tint = Color(0xFFEF4444),
                            modifier = Modifier.size(18.dp)
                        )
                        Text(
                            text = item.pdfSize,
                            color = TextSecondaryDark,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    Button(
                        onClick = onActionClick,
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = BrandPrimary),
                        contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Download,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(item.actionText, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                } else if (item.actionText == "Notify Me") {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.NotificationsActive,
                            contentDescription = "Alert",
                            tint = Color(0xFFF59E0B),
                            modifier = Modifier.size(18.dp)
                        )
                        Text(
                            text = "Release Alert",
                            color = TextSecondaryDark,
                            fontSize = 12.sp
                        )
                    }

                    OutlinedButton(
                        onClick = onActionClick,
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFFF59E0B)),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFF59E0B)),
                        contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.NotificationAdd,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(item.actionText, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                } else {
                    Spacer(modifier = Modifier.weight(1f))
                    TextButton(
                        onClick = onActionClick,
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = item.actionText,
                            color = BrandAccentCyan,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

