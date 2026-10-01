# -*- coding: utf-8 -*-
import os

base = r"C:\Users\gurut\AndroidStudioProjects\Abhyaas\app\src\main\java\com\example\abhyaas"
def modify(rel, func):
    path = os.path.join(base, rel)
    with open(path, 'r', encoding='utf-8') as f:
        old = f.read()
    new = func(old)
    if old != new:
        with open(path, 'w', encoding='utf-8') as f:
            f.write(new)
        print(f"Updated {rel}")

def f_appnavhost(c):
    c = c.replace('import com.example.abhyaas.ui.screens.pass.PassScreen\n', '')
    c = c.replace('onUnlockClick = {\n                    navController.navigate(Screen.Pass.route)\n                }', '')
    c = c.replace('onGetPassClick = {\n                    navController.navigate(Screen.Pass.route)\n                }', '')
    c = c.replace(',\n                onUnlockClick = {\n                    navController.navigate(Screen.Pass.route)\n                }', '')
    c = c.replace(',\n                onGetPassClick = {\n                    navController.navigate(Screen.Pass.route)\n                }', '')
    c = c.replace('// Extra routes for Pass and Empty State from outside BottomNav\n        composable(Screen.Pass.route) {\n            PassScreen()\n        }', '// Extra routes for Pass and Empty State from outside BottomNav')
    return c
modify(r"ui\navigation\AppNavHost.kt", f_appnavhost)

def f_screen(c):
    c = c.replace('object Pass : Screen("pass", "Pass", Icons.Filled.CardMembership)', '')
    c = c.replace('BottomNavItem(Screen.Pass, "Pass", Icons.Filled.CardMembership),', '')
    return c
modify(r"ui\navigation\Screen.kt", f_screen)

def f_appdrawer(c):
    c = c.replace('''DrawerNavigationItem(
                    label = "Pass",
                    icon = Icons.Default.CardMembership,
                    isSelected = currentRoute == "pass",
                    subtitle = "ABHYAS Pass - Unlimited",
                    onClick = {
                        onCloseDrawer()
                        onNavigateToRoute("pass")
                    }
                )''', '')
    return c
modify(r"ui\screens\main\AppDrawer.kt", f_appdrawer)

def f_mainscreen_ui(c):
    c = c.replace('Screen.Pass,', '')
    c = c.replace('composable(Screen.Pass.route) { PassScreen() }', '')
    return c
modify(r"ui\screens\MainScreen.kt", f_mainscreen_ui)

def f_mainscreen_main(c):
    c = c.replace('import com.example.abhyaas.ui.screens.pass.PassScreen\n', '')
    c = c.replace('composable(Screen.Pass.route) {\n            PassScreen()\n        }', '')
    c = c.replace('BottomNavItem("Pass", Screen.Pass.route, Icons.Default.CardMembership),', '')
    c = c.replace('onPassClick = {\n                        navController.navigate("pass")\n                    },', '')
    c = c.replace('onPassClick = {\n                        navController.navigate(Screen.Pass.route)\n                    },', '')
    return c
modify(r"ui\screens\main\MainScreen.kt", f_mainscreen_main)

def f_home(c):
    c = c.replace('onPassClick: () -> Unit = {},', '')
    c = c.replace('onClick = onPassClick,', 'onClick = {},')
    old_banner = '''// Pass Hero Banner
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .clickable { onPassClick() },
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(PassBannerBrush)
                ) {
                    // Geometric shapes overlay
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .offset(x = 30.dp, y = (-20).dp)
                            .size(100.dp)
                            .background(Color.White.copy(alpha = 0.1f), CircleShape)
                    )
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .offset(x = (-20).dp, y = 30.dp)
                            .size(80.dp)
                            .background(Color.White.copy(alpha = 0.1f), CircleShape)
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 20.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Surface(
                                shape = RoundedCornerShape(4.dp),
                                color = Color.White.copy(alpha = 0.25f)
                            ) {
                                Text(
                                    text = "PASS",
                                    color = Color.White,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Black,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                    letterSpacing = 1.sp
                                )
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "One Pass for All Exams",
                                color = Color.White,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Black
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "Mock Tests, PYQs & more",
                                color = Color.White.copy(alpha = 0.9f),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }

                        Button(
                            onClick = { onPassClick() },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White,
                                contentColor = BrandPrimary
                            ),
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            Text(
                                text = "Get Pass ✨",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }'''
            
    new_banner = '''// Free Content Banner
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = DarkSurface),
                border = BorderStroke(1.dp, BrandPrimary.copy(alpha = 0.3f))
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "📚 All content is FREE — Study, Practice, Excel!",
                        color = Color.White,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }'''
    
    c = c.replace(old_banner, new_banner)
    return c
modify(r"ui\screens\home\HomeScreen.kt", f_home)

def f_tsd(c):
    c = c.replace('onUnlockClick: () -> Unit = {}', '')
    c = c.replace('onUnlockClick: () -> Unit = {},', '')
    c = c.replace('onFolderClick: (folderId: String) -> Unit = {},\n', 'onFolderClick: (folderId: String) -> Unit = {}\n')
    c = c.replace('onFolderClick: (folderId: String, subCategory: String) -> Unit = { _, _ -> },\n', 'onFolderClick: (folderId: String, subCategory: String) -> Unit = { _, _ -> }\n')
    
    old_click = '''onClick = {
                                if (uiState.isUnlocked || folder.title.contains("Free", ignoreCase = true)) {
                                    onFolderClick(folder.id, series.title)
                                } else {
                                    onUnlockClick()
                                }
                            }'''
    new_click = '''onClick = { onFolderClick(folder.id, series.title) }'''
    c = c.replace(old_click, new_click)
    
    old_sticky = '''// Sticky Unlock Button
        if (!uiState.isUnlocked) {
            StickyBottomButton(
                text = "Unlock Test Series",
                subtext = "Get Abhyas Pass to access 500+ mock tests",
                onClick = onUnlockClick
            )
        }'''
    c = c.replace(old_sticky, '')
    return c
modify(r"ui\screens\tests\TestSeriesDetailScreen.kt", f_tsd)

def f_tls(c):
    c = c.replace('onUnlockClick: () -> Unit = {}', '')
    c = c.replace('onUnlockClick: () -> Unit = {},', '')
    c = c.replace('onViewResultClick: (testId: String) -> Unit = {} = {},\n', 'onViewResultClick: (testId: String) -> Unit = {},\n')
    c = c.replace('onShareClick: (testTitle: String) -> Unit = {},\n', 'onShareClick: (testTitle: String) -> Unit = {}\n')
    
    old_click = '''val canAttempt = test.isFree || uiState.isUnlocked
                        
                        TestCard(
                            title = test.title,
                            subtitle = "${test.questions} Qs • ${test.durationMins} mins • ${test.maxMarks} Marks",
                            badgeText = if (test.isFree) "FREE" else if (!uiState.isUnlocked) "LOCKED" else null,
                            badgeColor = if (test.isFree) CtaGreen else if (!uiState.isUnlocked) Color.Gray else Color.Transparent,
                            onClick = {
                                if (canAttempt) {
                                    onStartTestClick(test.id)
                                } else {
                                    onUnlockClick()
                                }
                            }
                        ) {
                            if (!canAttempt) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(Color.Black.copy(alpha = 0.4f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Lock,
                                        contentDescription = "Locked",
                                        tint = Color.White,
                                        modifier = Modifier.size(32.dp)
                                    )
                                }
                            }
                        }'''
    new_click = '''val canAttempt = true
                        
                        TestCard(
                            title = test.title,
                            subtitle = "${test.questions} Qs • ${test.durationMins} mins • ${test.maxMarks} Marks",
                            badgeText = null,
                            badgeColor = Color.Transparent,
                            onClick = { onStartTestClick(test.id) }
                        ) {
                        }'''
    c = c.replace(old_click, new_click)
    
    old_sticky = '''// Sticky Unlock Button
        if (uiState.tests.any { !it.isFree } && !uiState.isUnlocked) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(DarkBackground)
                    .border(1.dp, DarkBorderSubtle, RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Button(
                    onClick = onUnlockClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = CtaGreen,
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = "Unlock Test Series",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }'''
    c = c.replace(old_sticky, '')
    return c
modify(r"ui\screens\tests\TestListScreen.kt", f_tls)

def f_tis(c):
    c = c.replace('onGetPassClick: () -> Unit = {}', '')
    c = c.replace('onGetPassClick: () -> Unit = {},', '')
    c = c.replace('onAgreeAndContinue: (testId: String, lang: String) -> Unit = { _, _ -> } = {},\n', 'onAgreeAndContinue: (testId: String, lang: String) -> Unit = { _, _ -> }\n')
    
    old_banner = '''// Pass Banner
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onGetPassClick() },
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(PassBannerBrush)
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Abhyas PASS",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                        Text(
                            text = "Unlock all tests & PYQs",
                            color = Color.White.copy(alpha = 0.8f),
                            fontSize = 12.sp
                        )
                    }
                    Button(
                        onClick = onGetPassClick,
                        shape = RoundedCornerShape(6.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = CtaGreen,
                            contentColor = Color.White
                        ),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                        modifier = Modifier.height(32.dp)
                    ) {
                        Text("Get Pass", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))'''
    c = c.replace(old_banner, '')
    return c
modify(r"ui\screens\exam\TestInstructionsScreen.kt", f_tis)

def f_smls(c):
    c = c.replace('onUnlockClick: () -> Unit = {}', '')
    c = c.replace('onUnlockClick: () -> Unit = {},', '')
    c = c.replace('onMaterialClick: (materialId: String, seriesId: String) -> Unit = { _, _ -> },\n', 'onMaterialClick: (materialId: String, seriesId: String) -> Unit = { _, _ -> }\n')
    
    old_click = '''onClick = {
                                if (material.isFree) {
                                    onMaterialClick(material.id, seriesId)
                                } else {
                                    onUnlockClick()
                                }
                            }'''
    new_click = '''onClick = { onMaterialClick(material.id, seriesId) }'''
    c = c.replace(old_click, new_click)
    
    old_locked = '''if (!material.isFree) {
                                    Surface(
                                        color = Color.Gray,
                                        modifier = Modifier.padding(top = 8.dp),
                                        shape = RoundedCornerShape(4.dp)
                                    ) {
                                        Text(
                                            text = "LOCKED",
                                            color = Color.White,
                                            fontSize = 9.sp,
                                            fontWeight = FontWeight.Black,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }
                                }'''
    c = c.replace(old_locked, '')
    
    old_lock_icon = '''// Lock Icon Overlay
                            if (!material.isFree) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(Color.Black.copy(alpha = 0.4f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Lock,
                                        contentDescription = "Locked",
                                        tint = Color.White,
                                        modifier = Modifier.size(32.dp)
                                    )
                                }
                            }'''
    c = c.replace(old_lock_icon, '')
    
    old_lazy = '''LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 80.dp, top = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {'''
    new_lazy = '''LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 80.dp, top = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    text = "Tap any note to open it. To add your PDFs, upload them to Google Drive and share the link.",
                    color = TextSecondaryDark,
                    fontSize = 13.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }'''
    c = c.replace(old_lazy, new_lazy)
    return c
modify(r"ui\screens\study\StudyMaterialListScreen.kt", f_smls)

def f_pdf(c):
    c = c.replace('val googleDocsUrl = "https://docs.google.com/gview?embedded=true&url=$pdfUrl"', '''val finalUrl = if (pdfUrl.contains("drive.google.com")) {
        pdfUrl
    } else {
        "https://docs.google.com/viewer?embedded=true&url=$pdfUrl"
    }''')
    c = c.replace('loadUrl(googleDocsUrl)', 'loadUrl(finalUrl)')
    c = c.replace('settings.javaScriptEnabled = true', 'settings.javaScriptEnabled = true\n                    settings.domStorageEnabled = true')
    return c
modify(r"ui\screens\study\PdfViewerScreen.kt", f_pdf)

def f_repo(c):
    c = c.replace('isFree = false', 'isFree = true')
    c = c.replace('val studyMaterials = listOf(', '// TODO: Replace PLACEHOLDER_* with real Google Drive file IDs after uploading PDFs\n    val studyMaterials = listOf(')
    c = c.replace('pdfUrl = "https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf"', 'pdfUrl = "https://drive.google.com/file/d/PLACEHOLDER_MPLRC/preview"')
    c = c.replace('pdfUrl = "https://unec.edu.az/application/uploads/2014/12/pdf-sample.pdf"', 'pdfUrl = "https://drive.google.com/file/d/PLACEHOLDER_REASONING/preview"')
    c = c.replace('pdfUrl = "https://www.orimi.com/pdf-test.pdf"', 'pdfUrl = "https://drive.google.com/file/d/PLACEHOLDER_MPGK/preview"')
    # replace all other pdfUrls with generic just in case
    import re
    c = re.sub(r'pdfUrl = "(?!https://drive\.google)[^"]+"', 'pdfUrl = "https://drive.google.com/file/d/PLACEHOLDER_UNKNOWN/preview"', c)
    return c
modify(r"data\mock\MockStudyMaterialRepository.kt", f_repo)

def f_tests_updates(c):
    c = c.replace('onPassClick: () -> Unit = {},', '')
    old_tests_banner = '''// Hero Banner Carousel Card (matching tests tab.png)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(PassBannerBrush)
                    .border(1.dp, BrandPrimaryLight.copy(alpha = 0.5f), RoundedCornerShape(16.dp))
                    .padding(18.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = BrandPrimaryLight.copy(alpha = 0.3f),
                            border = androidx.compose.foundation.BorderStroke(1.dp, BrandAccentCyan)
                        ) {
                            Text(
                                text = "FEATURED EXAM",
                                color = BrandAccentCyan,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                            )
                        }

                        Text(
                            text = featuredSeries?.examDates?.let { "Exam: $it" } ?: "Exam: 2026",
                            color = Color.White.copy(alpha = 0.85f),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = topExamName,
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Black
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "100+ Free Mock Tests & PYQs",
                        color = Color.White.copy(alpha = 0.85f),
                        fontSize = 13.sp
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Button(
                        onClick = { onTestSeriesClick(activeSeriesId) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = BrandPrimary
                        ),
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                        modifier = Modifier.height(36.dp)
                    ) {
                        Text(
                            text = "Start Practicing",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))'''
    c = c.replace(old_tests_banner, '')
    
    old_updates_banner = '''// Pass Banner
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onPassClick() },
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.Transparent)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(14.dp))
                            .background(PassBannerBrush)
                            .padding(18.dp)
                    ) {
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.CardMembership, null, tint = Color.White)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Abhyas PASS",
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp
                                )
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "Get unlimited access to all Test Series & updates.",
                                color = Color.White.copy(alpha = 0.8f),
                                fontSize = 12.sp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))'''
    c = c.replace(old_updates_banner, '')
    return c

modify(r"ui\screens\tests\TestsScreen.kt", f_tests_updates)
modify(r"ui\screens\updates\UpdatesScreen.kt", f_tests_updates)
