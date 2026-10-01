package com.example.abhyaas.ui.screens.exam

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
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
import com.example.abhyaas.data.model.Test
import com.example.abhyaas.data.model.defaultTestInstructions
import com.example.abhyaas.data.model.defaultTestInstructionsHindi
import com.example.abhyaas.ui.theme.*

/**
 * Pre-Test Instructions Screen matching starting test.jpeg.
 * Displays duration, maximum marks, 7 comprehensive rules, language selector,
 * and cheating declaration before entering the active exam engine.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TestInstructionsScreen(
    testId: String = "nayab_tehsildar_2026_mock_01",
    onBackClick: () -> Unit = {},
    onAgreeAndContinue: (testId: String, lang: String) -> Unit = { _, _ -> }
    
) {
    val examRepository = remember { AbhyaasApplication.instance.examRepository }
    var test by remember { mutableStateOf<Test?>(null) }

    LaunchedEffect(testId) {
        test = examRepository.getTestById(testId).getOrNull()
    }

    val safeTest = test

    var selectedLanguage by remember { mutableStateOf("English") }
    var showLanguageSheet by remember { mutableStateOf(false) }
    var isDeclarationAccepted by remember { mutableStateOf(true) }

    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Your Tests",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick,) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = DarkBackgroundGradientStart
                )
            )
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(DarkBackground)
                    .border(1.dp, DarkBorderSubtle, RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                    .navigationBarsPadding()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Dropdown Pill: "Choose your Default Language ▾"
                Surface(
                    onClick = { showLanguageSheet = true },
                    shape = RoundedCornerShape(8.dp),
                    color = DarkSurface,
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF64748B)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 14.dp, vertical = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (selectedLanguage.isNotBlank()) "Default Language: $selectedLanguage" else "Choose your Default Language",
                            color = Color.White,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = "Select Language",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                // CTA Button: "Agree and Continue"
                Button(
                    onClick = {
                        if (isDeclarationAccepted) {
                            onAgreeAndContinue(safeTest?.id ?: testId, selectedLanguage)
                        }
                    },
                    enabled = isDeclarationAccepted,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = BrandPrimary,
                        disabledContainerColor = BrandPrimary.copy(alpha = 0.5f),
                        contentColor = Color.White,
                        disabledContentColor = Color.White.copy(alpha = 0.6f)
                    )
                ) {
                    Text(
                        text = if (selectedLanguage == "Hindi") "सहमत हूँ और आगे बढ़ें" else "Agree and Continue",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
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
        ) {
            // Pass Promo Banner (matching starting test.jpeg)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(PassBannerBrush)
                    .padding(horizontal = 16.dp, vertical = 10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = BrandLightBlue.copy(alpha = 0.25f),
                            border = androidx.compose.foundation.BorderStroke(1.dp, BrandLightBlue)
                        ) {
                            Text(
                                text = "PASS",
                                color = BrandLightBlue,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Black,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }

                        Text(
                            text = "Get Unlimited Mock Tests, PYPs & m...",
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            maxLines = 1
                        )
                    }

                    Button(
                        onClick = { onAgreeAndContinue(testId, selectedLanguage) }, shape = RoundedCornerShape(6.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = CtaGreen,
                            contentColor = Color.White
                        ),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                        modifier = Modifier.height(32.dp)
                    ) {
                        Text(
                            text = "Get Pass",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Test Title & Metadata Section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = safeTest?.title ?: "Loading...",
                    color = Color.White,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 24.sp
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Duration: ${safeTest?.durationMinutes ?: 90} Mins.",
                        color = TextSecondaryDark,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium
                    )

                    Text(
                        text = "Maximum Marks: ${safeTest?.totalMarks ?: 100}",
                        color = TextSecondaryDark,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                Spacer(modifier = Modifier.height(18.dp))

                HorizontalDivider(color = DarkBorderSubtle, thickness = 1.dp)

                Spacer(modifier = Modifier.height(16.dp))

                // Detailed Instruction Rules (1 to 6)
                val instructions = if (selectedLanguage == "Hindi") {
                    if (safeTest?.instructionsHindi?.isNotEmpty() == true) safeTest.instructionsHindi else defaultTestInstructionsHindi
                } else {
                    if (safeTest?.instructions?.isNotEmpty() == true) safeTest.instructions else defaultTestInstructions
                }

                instructions.take(6).forEach { rule ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Text(
                            text = "• ",
                            color = Color.White.copy(alpha = 0.8f),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = rule,
                            color = Color.White.copy(alpha = 0.85f),
                            fontSize = 13.5.sp,
                            lineHeight = 20.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Rule 7 with Declaration Checkbox
                val declarationText = if (instructions.size >= 7) {
                    instructions[6]
                } else {
                    if (selectedLanguage == "Hindi") {
                        "मैंने सभी निर्देशों को ध्यानपूर्वक पढ़ लिया है और उन्हें समझ लिया है। मैं इस परीक्षा में नकल न करने या अनुचित साधनों का उपयोग न करने की सहमति देता हूँ। मैं समझता/समझती हूँ कि अपने या किसी अन्य के लाभ के लिए किसी भी प्रकार के अनुचित साधनों का उपयोग करने से मुझे अयोग्य घोषित कर दिया जाएगा।"
                    } else {
                        "I have read all the instructions carefully and have understood them. I agree not to cheat or use unfair means in this examination. I understand that using unfair means of any sort for my own or someone else's advantage will lead to my disqualification."
                    }
                }

                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = DarkSurface,
                    border = androidx.compose.foundation.BorderStroke(1.dp, if (isDeclarationAccepted) BrandPrimary.copy(alpha = 0.5f) else DarkBorder),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { isDeclarationAccepted = !isDeclarationAccepted }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Checkbox(
                            checked = isDeclarationAccepted,
                            onCheckedChange = { isDeclarationAccepted = it },
                            colors = CheckboxDefaults.colors(
                                checkedColor = BrandPrimary,
                                uncheckedColor = TextSecondaryDark,
                                checkmarkColor = Color.White
                            ),
                            modifier = Modifier.size(24.dp)
                        )

                        Spacer(modifier = Modifier.width(10.dp))

                        Column {
                            Text(
                                text = if (selectedLanguage == "Hindi") "घोषणा" else "Declaration",
                                color = BrandAccentCyan,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = declarationText,
                                color = Color.White.copy(alpha = 0.9f),
                                fontSize = 12.5.sp,
                                lineHeight = 18.sp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }

    if (showLanguageSheet) {
        LanguageSelectionSheet(
            selectedLanguage = selectedLanguage,
            onLanguageSelected = { lang ->
                selectedLanguage = lang
                showLanguageSheet = false
            },
            onDismiss = { showLanguageSheet = false }
        )
    }
}



