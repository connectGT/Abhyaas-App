# -*- coding: utf-8 -*-
import os
import re

base_dir = r"C:\Users\gurut\AndroidStudioProjects\Abhyaas\app\src\main\java\com\example\abhyaas"

def modify(rel_path, func):
    path = os.path.join(base_dir, rel_path)
    if os.path.exists(path):
        with open(path, 'r', encoding='utf-8') as f:
            old = f.read()
        new = func(old)
        if old != new:
            with open(path, 'w', encoding='utf-8') as f:
                f.write(new)
            print(f"Updated {rel_path}")

def fix_home(c):
    c = re.sub(r',\s*onPassClick:\s*\(\)\s*->\s*Unit\s*=\s*\{\}', '', c)
    c = re.sub(r'// Pass Hero Banner[\s\S]*?// Section Header: "What are you looking for"', 
               '''// Free Content Banner
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

            Spacer(modifier = Modifier.height(20.dp))

            // Section Header: "What are you looking for"''', c)
    return c
modify(r"ui\screens\home\HomeScreen.kt", fix_home)

def fix_tsd(c):
    c = re.sub(r',\s*onUnlockClick:\s*\(\)\s*->\s*Unit\s*=\s*\{\}', '', c)
    c = re.sub(r'onClick = \{\s*if \(uiState\.isUnlocked \|\| folder\.title\.contains\("Free", ignoreCase = true\)\) \{\s*onFolderClick\(folder\.id\)\s*\} else \{\s*onUnlockClick\(\)\s*\}\s*\}', 'onClick = { onFolderClick(folder.id) }', c)
    c = re.sub(r'// Sticky Unlock Button[\s\S]*?if \(!uiState\.isUnlocked\)\s*\{\s*StickyBottomButton[\s\S]*?\}\s*\}\s*$', '}\n}', c)
    return c
modify(r"ui\screens\tests\TestSeriesDetailScreen.kt", fix_tsd)

def fix_tls(c):
    c = re.sub(r',\s*onUnlockClick:\s*\(\)\s*->\s*Unit\s*=\s*\{\}', '', c)
    c = re.sub(r'val canAttempt = test\.isFree \|\| uiState\.isUnlocked', 'val canAttempt = true', c)
    c = re.sub(r'onClick = \{\s*if \(canAttempt\) \{\s*onTestClick\(test\.id\)\s*\} else \{\s*onUnlockClick\(\)\s*\}\s*\}', 'onClick = { onTestClick(test.id) }', c)
    c = re.sub(r'if \(!canAttempt\)\s*\{\s*Box[\s\S]*?Icons\.Default\.Lock[\s\S]*?\}\s*\}', '', c)
    c = re.sub(r'// Sticky Unlock Button[\s\S]*?if \(uiState\.tests\.any \{ !it\.isFree \} && !uiState\.isUnlocked\)\s*\{\s*StickyBottomButton[\s\S]*?\}\s*\}\s*$', '}\n}', c)
    return c
modify(r"ui\screens\tests\TestListScreen.kt", fix_tls)

def fix_tis(c):
    c = re.sub(r',\s*onGetPassClick:\s*\(\)\s*->\s*Unit\s*=\s*\{\}', '', c)
    c = re.sub(r'// Pass Banner[\s\S]*?Card\([\s\S]*?onGetPassClick\(\)[\s\S]*?\}\s*\}\s*Spacer\(modifier = Modifier\.height\(16\.dp\)\)', '', c)
    return c
modify(r"ui\screens\exam\TestInstructionsScreen.kt", fix_tis)

def fix_smls(c):
    c = re.sub(r',\s*onUnlockClick:\s*\(\)\s*->\s*Unit\s*=\s*\{\}', '', c)
    c = re.sub(r'onClick = \{\s*if \(material\.isFree\) \{\s*onMaterialClick\(material\.id\)\s*\} else \{\s*onUnlockClick\(\)\s*\}\s*\}', 'onClick = { onMaterialClick(material.id) }', c)
    c = re.sub(r'if \(!material\.isFree\)\s*\{\s*Container[\s\S]*?Text\(text = "LOCKED"[\s\S]*?\}\s*\}', '', c)
    c = re.sub(r'// Lock Icon Overlay\s*if \(!material\.isFree\)\s*\{[\s\S]*?\}\s*\}', '}', c)
    c = re.sub(r'LazyColumn\((.*?)\)\s*\{', r'LazyColumn(\1) {\n            item {\n                Text(\n                    text = "Tap any note to open it. To add your PDFs, upload them to Google Drive and share the link.",\n                    color = TextSecondaryDark,\n                    fontSize = 13.sp,\n                    modifier = Modifier.padding(bottom = 16.dp, start = 20.dp, end = 20.dp)\n                )\n            }', c, count=1)
    return c
modify(r"ui\screens\study\StudyMaterialListScreen.kt", fix_smls)

def fix_main(c):
    c = c.replace('import com.example.abhyaas.ui.screens.pass.PassScreen\n', '')
    c = re.sub(r'composable\(Screen\.Pass\.route\)\s*\{\s*PassScreen\(\)\s*\}', '', c)
    c = re.sub(r'BottomNavItem\("Pass", Screen\.Pass\.route, Icons\.Default\.CardMembership\),\s*', '', c)
    c = re.sub(r',\s*onPassClick = \{[^\}]*\}', '', c)
    return c
modify(r"ui\screens\main\MainScreen.kt", fix_main)

def fix_navhost(c):
    c = re.sub(r',\s*onUnlockClick\s*=\s*\{\s*navController\.navigate\(Screen\.Pass\.route\)\s*\}', '', c)
    c = re.sub(r',\s*onPassClick\s*=\s*\{\s*navController\.navigate\(Screen\.Pass\.route\)\s*\}', '', c)
    c = re.sub(r',\s*onGetPassClick\s*=\s*\{\s*navController\.navigate\(Screen\.Pass\.route\)\s*\}', '', c)
    c = c.replace('import com.example.abhyaas.ui.screens.pass.PassScreen\n', '')
    c = re.sub(r'composable\(Screen\.Pass\.route\)\s*\{\s*PassScreen\(\)\s*\}', '', c)
    return c
modify(r"ui\navigation\AppNavHost.kt", fix_navhost)