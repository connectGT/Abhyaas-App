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
    
    old_click_2 = '''onClick = {
                                if (uiState.isUnlocked || folder.title.contains("Free", ignoreCase = true)) {
                                    onFolderClick(folder.id)
                                } else {
                                    onUnlockClick()
                                }
                            }'''
    new_click_2 = '''onClick = { onFolderClick(folder.id) }'''
    c = c.replace(old_click_2, new_click_2)
    
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