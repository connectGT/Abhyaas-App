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

def f_home(c):
    c = c.replace('onPassClick: () -> Unit = {},', '')
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
            }

            Spacer(modifier = Modifier.height(20.dp))'''
            
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
                        text = "📚 All content is FREE - Study, Practice, Excel!",
                        color = Color.White,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))'''
    
    c = c.replace(old_banner, new_banner)
    return c
modify(r"ui\screens\home\HomeScreen.kt", f_home)

def f_tsd(c):
    c = c.replace('onUnlockClick: () -> Unit = {}', '')
    # Remove comma if it exists alone on the line before
    c = c.replace(',\n    onFolderClick: (folderId: String) -> Unit = {}', '\n    onFolderClick: (folderId: String) -> Unit = {}')
    
    c = c.replace('if (uiState.isUnlocked || folder.title.contains("Free", ignoreCase = true)) {', '')
    c = c.replace('onFolderClick(folder.id)', 'onFolderClick(folder.id)')
    c = c.replace('} else {', '')
    c = c.replace('onUnlockClick()', '')
    c = c.replace('}', '}')
    # actually let's do a smart replace for the click block
    old_click = '''onClick = {
                                if (uiState.isUnlocked || folder.title.contains("Free", ignoreCase = true)) {
                                    onFolderClick(folder.id)
                                } else {
                                    onUnlockClick()
                                }
                            }'''
    new_click = '''onClick = { onFolderClick(folder.id) }'''
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
    c = c.replace('onUnlockClick: () -> Unit = {},', '')
    
    old_click = '''val canAttempt = test.isFree || uiState.isUnlocked
                        
                        TestCard(
                            title = test.title,
                            subtitle = "${test.questions} Qs • ${test.durationMins} mins • ${test.maxMarks} Marks",
                            badgeText = if (test.isFree) "FREE" else if (!uiState.isUnlocked) "LOCKED" else null,
                            badgeColor = if (test.isFree) CtaGreen else if (!uiState.isUnlocked) Color.Gray else Color.Transparent,
                            onClick = {
                                if (canAttempt) {
                                    onTestClick(test.id)
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
                            onClick = { onTestClick(test.id) }
                        ) {
                        }'''
    c = c.replace(old_click, new_click)
    
    old_sticky = '''// Sticky Unlock Button
        if (uiState.tests.any { !it.isFree } && !uiState.isUnlocked) {
            StickyBottomButton(
                text = "Unlock Test Series",
                subtext = "Get Abhyas Pass to attempt all locked tests",
                onClick = onUnlockClick
            )
        }'''
    c = c.replace(old_sticky, '')
    return c
modify(r"ui\screens\tests\TestListScreen.kt", f_tls)

def f_tis(c):
    c = c.replace('onGetPassClick: () -> Unit = {},', '')
    
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
                        onClick = { onGetPassClick() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = BrandPrimary
                        ),
                        shape = RoundedCornerShape(8.dp),
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
    c = c.replace(',\n    onMaterialClick: (materialId: String) -> Unit = {}', '\n    onMaterialClick: (materialId: String) -> Unit = {}')
    
    old_click = '''onClick = {
                                if (material.isFree) {
                                    onMaterialClick(material.id)
                                } else {
                                    onUnlockClick()
                                }
                            }'''
    new_click = '''onClick = { onMaterialClick(material.id) }'''
    c = c.replace(old_click, new_click)
    
    old_locked = '''if (!material.isFree) {
                                    Container(
                                        color = Color.Gray,
                                        modifier = Modifier.padding(top = 8.dp)
                                    ) {
                                        Text(text = "LOCKED", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
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
            contentPadding = PaddingValues(bottom = 80.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {'''
    new_lazy = '''LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 80.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    text = "Tap any note to open it. To add your PDFs, upload them to Google Drive and share the link.",
                    color = TextSecondaryDark,
                    fontSize = 13.sp,
                    modifier = Modifier.padding(bottom = 16.dp, start = 20.dp, end = 20.dp)
                )
            }'''
    c = c.replace(old_lazy, new_lazy)
    return c
modify(r"ui\screens\study\StudyMaterialListScreen.kt", f_smls)