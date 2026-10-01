# -*- coding: utf-8 -*-
import os
import re

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

def f_drawer(c):
    return re.sub(r'DrawerNavigationItem\(\s*label = "Pass",\s*icon = Icons\.Default\.CardMembership,\s*isSelected = currentRoute == "pass",\s*subtitle = "ABHYAS Pass - Unlimited",\s*onClick = \{\s*onCloseDrawer\(\)\s*onNavigateToRoute\("pass"\)\s*\}\s*\)', '', c)
modify(r"ui\screens\main\AppDrawer.kt", f_drawer)

def f_screen(c):
    return re.sub(r'\s*object Pass : Screen\("pass", "Pass", Icons\.Filled\.CardMembership\)', '', c)
modify(r"ui\navigation\Screen.kt", f_screen)

def f_screen2(c):
    return re.sub(r'\s*BottomNavItem\(Screen\.Pass, "Pass", Icons\.Filled\.CardMembership\),', '', c)
modify(r"ui\navigation\Screen.kt", f_screen2)

def f_msmr(c):
    c = c.replace('isFree = false', 'isFree = true')
    c = c.replace('val studyMaterials = listOf(', '// TODO: Replace PLACEHOLDER_* with real Google Drive file IDs after uploading PDFs\n    val studyMaterials = listOf(')
    c = re.sub(r'(id = "sm_1".*?pdfUrl = )"[^"]+"', r'\1"https://drive.google.com/file/d/PLACEHOLDER_MPLRC/preview"', c, flags=re.DOTALL)
    c = re.sub(r'(id = "sm_2".*?pdfUrl = )"[^"]+"', r'\1"https://drive.google.com/file/d/PLACEHOLDER_REASONING/preview"', c, flags=re.DOTALL)
    c = re.sub(r'(id = "sm_3".*?pdfUrl = )"[^"]+"', r'\1"https://drive.google.com/file/d/PLACEHOLDER_MPGK/preview"', c, flags=re.DOTALL)
    c = re.sub(r'(id = "sm_4".*?pdfUrl = )"[^"]+"', r'\1"https://drive.google.com/file/d/PLACEHOLDER_LANDRECORDS/preview"', c, flags=re.DOTALL)
    c = re.sub(r'(id = "sm_5".*?pdfUrl = )"[^"]+"', r'\1"https://drive.google.com/file/d/PLACEHOLDER_MATHSCIENCE/preview"', c, flags=re.DOTALL)
    return c
modify(r"data\mock\MockStudyMaterialRepository.kt", f_msmr)

def f_pdfviewer(c):
    c = c.replace('val googleDocsUrl = "https://docs.google.com/gview?embedded=true&url=$pdfUrl"', '''val finalUrl = if (pdfUrl.contains("drive.google.com")) {
        pdfUrl
    } else {
        "https://docs.google.com/viewer?embedded=true&url=$pdfUrl"
    }''')
    c = c.replace('loadUrl(googleDocsUrl)', 'loadUrl(finalUrl)')
    c = c.replace('settings.javaScriptEnabled = true', 'settings.javaScriptEnabled = true\n                    settings.domStorageEnabled = true')
    return c
modify(r"ui\screens\study\PdfViewerScreen.kt", f_pdfviewer)

def f_tests(c):
    c = c.replace('onPassClick: () -> Unit = {},', '')
    old_banner = '''// Hero Banner Carousel Card (matching tests tab.png)
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
            }'''
    return c.replace(old_banner, '')
modify(r"ui\screens\tests\TestsScreen.kt", f_tests)

def f_updates(c):
    c = c.replace('onPassClick: () -> Unit = {},', '')
    old_banner = '''// Pass Banner
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
    return c.replace(old_banner, '')
modify(r"ui\screens\updates\UpdatesScreen.kt", f_updates)

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