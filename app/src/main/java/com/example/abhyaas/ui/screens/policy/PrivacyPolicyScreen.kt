package com.example.abhyaas.ui.screens.policy

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.abhyaas.ui.components.CommonTopAppBar
import com.example.abhyaas.ui.components.NavIconType
import com.example.abhyaas.ui.theme.*

@Composable
fun PrivacyPolicyScreen(
    onNavigateBack: () -> Unit = {}
) {
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            CommonTopAppBar(
                title = "Privacy Policy",
                subtitle = "User data security & terms",
                navIconType = NavIconType.Back,
                onNavClick = onNavigateBack,
                containerColor = DarkBackgroundGradientStart
            )
        },
        containerColor = DarkBackground
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(AppBackgroundBrush)
                .padding(innerPadding)
                .verticalScroll(scrollState)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Header Card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(DarkSurface)
                    .border(1.dp, DarkBorder, RoundedCornerShape(14.dp))
                    .padding(16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(BrandPrimary.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Security,
                            contentDescription = null,
                            tint = BrandAccentCyan,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Your Privacy is Our Priority",
                            color = TextPrimaryDark,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Learn how Abhyaas protects your personal information and exam performance data.",
                            color = TextSecondaryDark,
                            fontSize = 12.sp,
                            lineHeight = 16.sp
                        )
                    }
                }
            }

            // Expandable Policy Sections
            ExpandablePolicyCard(
                title = "1. Information We Collect",
                content = "We collect information you provide directly to us during registration, such as your full name, email address, mobile number, date of birth, category, and educational qualification. When you take mock tests, we record your question selections, time spent per question, submission timestamps, and evaluation scores to generate performance insights.",
                initialExpanded = true
            )

            ExpandablePolicyCard(
                title = "2. How We Use Your Information",
                content = "Your test preparation data is used exclusively to:\n• Calculate percentile rankings and sectional accuracy.\n• Generate personalized preparation trend graphs and study recommendations.\n• Send official examination updates, admit card alerts, and test result notifications.\n• Maintain fair competition on public mock test leaderboards.",
                initialExpanded = true
            )

            ExpandablePolicyCard(
                title = "3. Data Security & Storage",
                content = "We implement robust security measures to protect your personal information against unauthorized access, alteration, or disclosure. All test session transmissions are encrypted. We do not sell, rent, or monetize your individual performance or contact records to commercial advertisers.",
                initialExpanded = false
            )

            ExpandablePolicyCard(
                title = "4. Educational Content & Copyright",
                content = "All mock test questions, previous year question explanations, study notes, and video solutions provided in Abhyaas are proprietary or licensed for educational test preparation purposes. Unauthorized copying, distribution, or reproduction of question banks is strictly prohibited.",
                initialExpanded = false
            )

            ExpandablePolicyCard(
                title = "5. User Rights & Account Deletion",
                content = "You retain full control over your profile data. You may update your profile details in User Settings or request complete erasure of your test attempt history by contacting our support team at support@abhyaas.edu. Account deletion requests are processed within 7 business days.",
                initialExpanded = false
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Footer
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Abhyaas • Version 1.0.0",
                    color = TextTertiaryDark,
                    fontSize = 12.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "support@abhyaas.edu • Last Updated: Sep 2026",
                    color = TextTertiaryDark,
                    fontSize = 11.sp
                )
            }
        }
    }
}

@Composable
fun ExpandablePolicyCard(
    title: String,
    content: String,
    initialExpanded: Boolean = false
) {
    var expanded by remember { mutableStateOf(initialExpanded) }
    val rotationAngle by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f,
        label = "chevronRotation"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .border(1.dp, DarkBorder, RoundedCornerShape(12.dp))
            .clickable { expanded = !expanded }
            .animateContentSize(),
        colors = CardDefaults.cardColors(containerColor = DarkSurface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Box(
                        modifier = Modifier
                            .width(3.dp)
                            .height(18.dp)
                            .clip(RoundedCornerShape(2.dp))
                            .background(BrandAccentCyan)
                    )
                    Text(
                        text = title,
                        color = TextPrimaryDark,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Icon(
                    imageVector = Icons.Default.ExpandMore,
                    contentDescription = if (expanded) "Collapse" else "Expand",
                    tint = TextSecondaryDark,
                    modifier = Modifier
                        .size(22.dp)
                        .rotate(rotationAngle)
                )
            }

            AnimatedVisibility(visible = expanded) {
                Column {
                    Spacer(modifier = Modifier.height(10.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(DarkBorderSubtle)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = content,
                        color = TextSecondaryDark,
                        fontSize = 13.sp,
                        lineHeight = 20.sp
                    )
                }
            }
        }
    }
}
