package com.example.abhyaas.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Article
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.abhyaas.ui.components.CategoryGradients
import com.example.abhyaas.ui.components.CategoryGridCard
import com.example.abhyaas.ui.components.CommonTopAppBar
import com.example.abhyaas.ui.components.ExamTitleDropdown
import com.example.abhyaas.ui.components.NavIconType
import com.example.abhyaas.ui.theme.*
import com.example.abhyaas.ui.viewmodel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onMenuClick: () -> Unit = {},
    onAvatarClick: () -> Unit = {},
    
    onCategoryClick: (String) -> Unit = {},
    onSearchClick: () -> Unit = {},
    viewModel: HomeViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()
    var showAiSheet by remember { mutableStateOf(false) }

    val examTitle = uiState.selectedExam.ifBlank {
        uiState.userProfile?.targetExam ?: "Nayab Tehsildar"
    }

    Scaffold(
        topBar = {
            CommonTopAppBar(
                title = "ABHYAS",
                navIconType = NavIconType.Menu,
                onNavClick = onMenuClick,
                customTitleContent = {
                    ExamTitleDropdown(
                        brandName = "ABHYAS",
                        examName = examTitle,
                        onExamClick = { /* Could open exam selection bottom sheet */ }
                    )
                },
                showSearch = true,
                onSearchClick = onSearchClick,
                showAvatar = true,
                onAvatarClick = onAvatarClick,
                containerColor = DarkBackgroundGradientStart
            )
        },
        floatingActionButton = {
            // Glowing AI Floating Action Button (home tab 1.png)
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .shadow(elevation = 12.dp, shape = CircleShape, spotColor = BrandAccentCyan)
                    .clip(CircleShape)
                    .background(BrandPrimary)
            ) {
                IconButton(
                    onClick = { showAiSheet = true },
                    modifier = Modifier.fillMaxSize()
                ) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = "AI Study Assistant",
                        tint = Color.White,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }
        },
        containerColor = DarkBackground
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(AppBackgroundBrush)
                .padding(innerPadding)
                .verticalScroll(scrollState)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(4.dp))

            
        }
    }

    // AI Study Assistant Modal Bottom Sheet
    if (showAiSheet) {
        ModalBottomSheet(
            onDismissRequest = { showAiSheet = false },
            containerColor = DarkSurface,
            contentColor = TextPrimaryDark
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(54.dp)
                        .clip(CircleShape)
                        .background(BrandPrimary.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = null,
                        tint = BrandAccentCyan,
                        modifier = Modifier.size(32.dp)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Abhyaas AI Study Assistant",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimaryDark
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Need help with a tricky reasoning problem or general knowledge concept? Ask your doubt or generate a quick topic practice test!",
                    fontSize = 13.sp,
                    color = TextSecondaryDark,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                    lineHeight = 18.sp
                )

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
                        showAiSheet = false
                        onCategoryClick("cat_practice")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = BrandPrimary)
                ) {
                    Text("Start AI Recommended Practice", fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

private fun resolveCategoryIcon(id: String, iconName: String): ImageVector {
    return when {
        id == "cat_notes" || iconName.equals("Description", ignoreCase = true) -> Icons.Default.Description
        id == "cat_pyq" || iconName.equals("HistoryEdu", ignoreCase = true) -> Icons.Default.HistoryEdu
        id == "cat_practice" || iconName.equals("Restore", ignoreCase = true) -> Icons.Default.Restore
        id == "cat_live" || iconName.equals("Assignment", ignoreCase = true) -> Icons.AutoMirrored.Filled.Assignment
        id == "cat_classes" || iconName.equals("School", ignoreCase = true) || iconName.equals("PlayCircleOutline", ignoreCase = true) -> Icons.Default.PlayCircleOutline
        id == "cat_quiz" || iconName.equals("Quiz", ignoreCase = true) -> Icons.Default.Quiz
        id == "cat_current_affairs" || iconName.equals("Article", ignoreCase = true) || iconName.equals("Public", ignoreCase = true) -> Icons.AutoMirrored.Filled.Article
        else -> Icons.Default.Folder
    }
}

private fun resolveCategoryGradient(id: String, startHex: Long, endHex: Long): List<Color> {
    return when (id) {
        "cat_notes" -> CategoryGradients.StudyNotes
        "cat_pyq" -> CategoryGradients.PYQ
        "cat_practice" -> CategoryGradients.Practice
        "cat_live" -> CategoryGradients.LiveTests
        "cat_classes" -> CategoryGradients.DailyClasses
        "cat_quiz" -> CategoryGradients.Quiz
        "cat_current_affairs" -> CategoryGradients.CurrentAffairs
        else -> listOf(Color(startHex), Color(endHex))
    }
}


