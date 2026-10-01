package com.example.abhyaas.ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import com.example.abhyaas.AbhyaasApplication
import com.example.abhyaas.data.model.UserProfile
import com.example.abhyaas.ui.components.CommonTopAppBar
import com.example.abhyaas.ui.components.NavIconType
import com.example.abhyaas.ui.theme.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserSettingScreen(
    onNavigateBack: () -> Unit = {},
    onCreateAccountSuccess: () -> Unit = {},
    onPrivacyPolicyClick: () -> Unit = {}
) {
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var mobileNumber by remember { mutableStateOf("") }
    var dateOfBirth by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("General") }
    var pinCode by remember { mutableStateOf("") }
    var selectedEducation by remember { mutableStateOf("Graduation") }

    var categoryExpanded by remember { mutableStateOf(false) }
    var educationExpanded by remember { mutableStateOf(false) }

    val categoryOptions = listOf("General", "OBC", "SC", "ST", "EWS")
    val educationOptions = listOf("10th Standard", "12th Standard", "Diploma", "Graduation", "Post Graduation")

    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()
    val userRepository = remember { AbhyaasApplication.instance.userRepository }

    fun handleSave() {
        val updated = UserProfile(
            name = fullName.ifBlank { "Aspirant" },
            email = email.ifBlank { "aspirant@abhyaas.edu" },
            mobileNumber = mobileNumber.ifBlank { "+91 98765 43210" },
            dateOfBirth = dateOfBirth.ifBlank { "15/08/2000" },
            category = selectedCategory,
            pinCode = pinCode.ifBlank { "462001" },
            education = selectedEducation,
            educationQualification = selectedEducation,
            targetExam = "Nayab Tehsildar"
        )
        scope.launch {
            userRepository.updateUserProfile(updated)
        }
        onCreateAccountSuccess()
    }

    Scaffold(
        topBar = {
            CommonTopAppBar(
                title = "Create Your Account",
                subtitle = "Join ABHYAS & start preparation",
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
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Step Progress Indicator Bar (3 Steps: 1 Active)
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = DarkSurface),
                border = androidx.compose.foundation.BorderStroke(1.dp, DarkBorder)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Step 1 of 3: Basic Profile",
                            color = BrandAccentCyan,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "33% Complete",
                            color = TextSecondaryDark,
                            fontSize = 12.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    LinearProgressIndicator(
                        progress = { 0.33f },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .clip(RoundedCornerShape(3.dp)),
                        color = BrandPrimary,
                        trackColor = DarkCardElevated
                    )
                }
            }

            // Profile Photo Upload Circle
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .size(90.dp)
                            .clip(CircleShape)
                            .background(DarkSurface)
                            .border(2.dp, BrandPrimaryLight, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Avatar",
                            tint = Color.White.copy(alpha = 0.8f),
                            modifier = Modifier.size(52.dp)
                        )
                        // Camera overlay badge
                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .clip(CircleShape)
                                .background(BrandPrimary)
                                .align(Alignment.BottomEnd)
                                .border(1.5.dp, DarkBackground, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.PhotoCamera,
                                contentDescription = "Change photo",
                                tint = Color.White,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Add Profile Photo",
                        color = BrandAccentCyan,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            // Form Section Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = DarkSurface),
                border = androidx.compose.foundation.BorderStroke(1.dp, DarkBorder)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    // Full Name *
                    OutlinedTextField(
                        value = fullName,
                        onValueChange = { fullName = it },
                        label = { Text("Full Name *", color = TextSecondaryDark) },
                        leadingIcon = {
                            Icon(Icons.Default.Badge, contentDescription = null, tint = BrandPrimaryLight)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        colors = getFormTextFieldColors()
                    )

                    // Email Address *
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email Address *", color = TextSecondaryDark) },
                        leadingIcon = {
                            Icon(Icons.Default.Email, contentDescription = null, tint = BrandPrimaryLight)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        colors = getFormTextFieldColors()
                    )

                    // Mobile Number * (with verified badge)
                    OutlinedTextField(
                        value = mobileNumber,
                        onValueChange = { mobileNumber = it },
                        label = { Text("Mobile Number *", color = TextSecondaryDark) },
                        leadingIcon = {
                            Icon(Icons.Default.Phone, contentDescription = null, tint = BrandPrimaryLight)
                        },
                        trailingIcon = {
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = StatusAnswered.copy(alpha = 0.2f),
                                border = androidx.compose.foundation.BorderStroke(1.dp, StatusAnswered)
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.CheckCircle,
                                        contentDescription = "Verified",
                                        tint = StatusAnswered,
                                        modifier = Modifier.size(12.dp)
                                    )
                                    Spacer(modifier = Modifier.width(3.dp))
                                    Text("Verified", color = StatusAnswered, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        colors = getFormTextFieldColors()
                    )

                    // Date of Birth *
                    OutlinedTextField(
                        value = dateOfBirth,
                        onValueChange = { dateOfBirth = it },
                        label = { Text("Date of Birth * (DD/MM/YYYY)", color = TextSecondaryDark) },
                        leadingIcon = {
                            Icon(Icons.Default.CalendarToday, contentDescription = null, tint = BrandPrimaryLight)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        colors = getFormTextFieldColors()
                    )

                    // Category * Dropdown
                    ExposedDropdownMenuBox(
                        expanded = categoryExpanded,
                        onExpandedChange = { categoryExpanded = !categoryExpanded }
                    ) {
                        OutlinedTextField(
                            value = selectedCategory,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Category * (for cut-off stats)", color = TextSecondaryDark) },
                            leadingIcon = {
                                Icon(Icons.Default.Category, contentDescription = null, tint = BrandPrimaryLight)
                            },
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = categoryExpanded)
                            },
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth(),
                            colors = getFormTextFieldColors()
                        )
                        ExposedDropdownMenu(
                            expanded = categoryExpanded,
                            onDismissRequest = { categoryExpanded = false },
                            modifier = Modifier.background(DarkCardElevated)
                        ) {
                            categoryOptions.forEach { opt ->
                                DropdownMenuItem(
                                    text = { Text(opt, color = TextPrimaryDark) },
                                    onClick = {
                                        selectedCategory = opt
                                        categoryExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    // Pin Code *
                    OutlinedTextField(
                        value = pinCode,
                        onValueChange = { if (it.length <= 6 && it.all { c -> c.isDigit() }) pinCode = it },
                        label = { Text("Pin Code *", color = TextSecondaryDark) },
                        leadingIcon = {
                            Icon(Icons.Default.PinDrop, contentDescription = null, tint = BrandPrimaryLight)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        colors = getFormTextFieldColors()
                    )

                    // Education Qualification * Dropdown
                    ExposedDropdownMenuBox(
                        expanded = educationExpanded,
                        onExpandedChange = { educationExpanded = !educationExpanded }
                    ) {
                        OutlinedTextField(
                            value = selectedEducation,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Educational Qualification *", color = TextSecondaryDark) },
                            leadingIcon = {
                                Icon(Icons.Default.School, contentDescription = null, tint = BrandPrimaryLight)
                            },
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = educationExpanded)
                            },
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth(),
                            colors = getFormTextFieldColors()
                        )
                        ExposedDropdownMenu(
                            expanded = educationExpanded,
                            onDismissRequest = { educationExpanded = false },
                            modifier = Modifier.background(DarkCardElevated)
                        ) {
                            educationOptions.forEach { opt ->
                                DropdownMenuItem(
                                    text = { Text(opt, color = TextPrimaryDark) },
                                    onClick = {
                                        selectedEducation = opt
                                        educationExpanded = false
                                    }
                                )
                            }
                        }
                    }
                }
            }

            // Create Account CTA Button
            Button(
                onClick = ::handleSave,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = BrandPrimary,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Create Account →",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.5.sp
                )
            }

            // Agreement footer
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "By creating an account, you agree to ABHYAS",
                    color = TextTertiaryDark,
                    fontSize = 11.sp
                )
                Text(
                    text = "Terms of Service & Privacy Policy",
                    color = BrandPrimaryLight,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.clickable(onClick = onPrivacyPolicyClick)
                )
            }
        }
    }
}

@Composable
private fun getFormTextFieldColors(): TextFieldColors = OutlinedTextFieldDefaults.colors(
    focusedContainerColor = DarkCard,
    unfocusedContainerColor = DarkCard,
    focusedBorderColor = BrandPrimary,
    unfocusedBorderColor = DarkBorder,
    focusedTextColor = TextPrimaryDark,
    unfocusedTextColor = TextPrimaryDark
)
