package com.example.abhyaas.ui.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.ListAlt
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.abhyaas.data.model.UserProfile
import com.example.abhyaas.ui.theme.*
import com.example.abhyaas.ui.viewmodel.UserProfileViewModel

@Composable
fun AppDrawer(
    currentRoute: String,
    onNavigateToRoute: (String) -> Unit,
    onProfileClick: () -> Unit = {},
    onUserSettingsClick: () -> Unit,
    onPrivacyPolicyClick: () -> Unit,
    onLogoutClick: () -> Unit,
    onCloseDrawer: () -> Unit,
    viewModel: UserProfileViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val userProfile = uiState.profile ?: UserProfile()

    ModalDrawerSheet(
        drawerContainerColor = DarkSurface,
        drawerContentColor = TextPrimaryDark,
        modifier = Modifier.width(310.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(AppBackgroundBrush)
                .padding(vertical = 20.dp)
        ) {
            // Profile Header Card (matching 3 line pe dabane pr ye aata hai.png)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(DarkCard)
                    .border(1.dp, DarkBorder, RoundedCornerShape(16.dp))
                    .clickable {
                        onCloseDrawer()
                        onProfileClick()
                    }
                    .padding(16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    // Avatar
                    Box(
                        modifier = Modifier
                            .size(54.dp)
                            .clip(CircleShape)
                            .background(BrandPrimary)
                            .border(2.dp, BrandAccentCyan, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "User Avatar",
                            tint = Color.White,
                            modifier = Modifier.size(32.dp)
                        )
                    }

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = userProfile.fullName.ifBlank { "Add Your Name" },
                            color = TextPrimaryDark,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "User Settings",
                            color = BrandAccentCyan,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.clickable {
                                onCloseDrawer()
                                onUserSettingsClick()
                            }
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = userProfile.mobileNumber,
                            color = TextTertiaryDark,
                            fontSize = 11.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Navigation Items List
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 12.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                DrawerNavigationItem(
                    label = "Home",
                    icon = Icons.Default.Home,
                    isSelected = currentRoute == "home",
                    onClick = {
                        onCloseDrawer()
                        onNavigateToRoute("home")
                    }
                )
                DrawerNavigationItem(
                    label = "Pass",
                    icon = Icons.Default.CardMembership,
                    isSelected = currentRoute == "pass",
                    subtitle = "ABHYAS Pass - Unlimited",
                    onClick = {
                        onCloseDrawer()
                        onNavigateToRoute("pass")
                    }
                )
                DrawerNavigationItem(
                    label = "Test Series",
                    icon = Icons.AutoMirrored.Filled.ListAlt,
                    isSelected = currentRoute == "tests",
                    subtitle = "600+ Mock Tests & PYQs",
                    onClick = {
                        onCloseDrawer()
                        onNavigateToRoute("tests")
                    }
                )
                DrawerNavigationItem(
                    label = "Study Notes",
                    icon = Icons.AutoMirrored.Filled.MenuBook,
                    isSelected = false,
                    badgeText = "NEW",
                    onClick = {
                        onCloseDrawer()
                        onNavigateToRoute("home")
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))
                HorizontalDivider(color = DarkBorderSubtle, modifier = Modifier.padding(horizontal = 8.dp))
                Spacer(modifier = Modifier.height(8.dp))

                DrawerNavigationItem(
                    label = "Your Exams",
                    icon = Icons.Default.School,
                    isSelected = false,
                    subtitle = "SSC CGL, Selection Post",
                    onClick = {
                        onCloseDrawer()
                        onNavigateToRoute("tests")
                    }
                )
                DrawerNavigationItem(
                    label = "Updates",
                    icon = Icons.Default.Notifications,
                    isSelected = currentRoute == "updates",
                    subtitle = "Notifications & PDFs",
                    onClick = {
                        onCloseDrawer()
                        onNavigateToRoute("updates")
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))
                HorizontalDivider(color = DarkBorderSubtle, modifier = Modifier.padding(horizontal = 8.dp))
                Spacer(modifier = Modifier.height(8.dp))

                DrawerNavigationItem(
                    label = "Privacy Policy",
                    icon = Icons.Default.Security,
                    isSelected = false,
                    onClick = {
                        onCloseDrawer()
                        onPrivacyPolicyClick()
                    }
                )
                DrawerNavigationItem(
                    label = "Logout",
                    icon = Icons.AutoMirrored.Filled.ExitToApp,
                    isSelected = false,
                    iconTint = StatusNotAnswered,
                    textColor = StatusNotAnswered,
                    onClick = {
                        onCloseDrawer()
                        onLogoutClick()
                    }
                )
            }

            // Bottom Brand Footer
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "ABHYAAS v1.0.0",
                    color = TextTertiaryDark,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "By 2-Minute Education",
                    color = BrandSkyBlue.copy(alpha = 0.7f),
                    fontSize = 11.sp
                )
            }
        }
    }
}

@Composable
private fun DrawerNavigationItem(
    label: String,
    icon: ImageVector,
    isSelected: Boolean,
    subtitle: String? = null,
    badgeText: String? = null,
    iconTint: Color = if (isSelected) BrandAccentCyan else TextSecondaryDark,
    textColor: Color = if (isSelected) TextPrimaryDark else TextSecondaryDark,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        color = if (isSelected) DarkSelected else Color.Transparent,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = iconTint,
                modifier = Modifier.size(22.dp)
            )
            Spacer(modifier = Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = label,
                        color = textColor,
                        fontSize = 14.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                    )
                    if (badgeText != null) {
                        Spacer(modifier = Modifier.width(6.dp))
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(BrandPrimary)
                                .padding(horizontal = 5.dp, vertical = 1.dp)
                        ) {
                            Text(badgeText, color = Color.White, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
                if (subtitle != null) {
                    Text(
                        text = subtitle,
                        color = TextTertiaryDark,
                        fontSize = 11.sp
                    )
                }
            }
            if (isSelected) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    tint = BrandAccentCyan,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}
