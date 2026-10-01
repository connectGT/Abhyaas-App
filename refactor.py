# -*- coding: utf-8 -*-
import os
import re

base_dir = r"C:\Users\gurut\AndroidStudioProjects\Abhyaas\app\src\main\java\com\example\abhyaas"

def read_file(path):
    with open(path, 'r', encoding='utf-8') as f:
        return f.read()

def write_file(path, content):
    with open(path, 'w', encoding='utf-8') as f:
        f.write(content)

def modify(rel_path, func):
    path = os.path.join(base_dir, rel_path)
    if os.path.exists(path):
        old = read_file(path)
        new = func(old)
        if old != new:
            write_file(path, new)
            print(f"Updated {rel_path}")
        else:
            print(f"No changes {rel_path}")

# 1. HomeScreen.kt
def fix_home(c):
    c = re.sub(r',\s*onPassClick\s*:\s*\(\)\s*->\s*Unit', '', c)
    c = re.sub(r'// Pass Hero Banner.*?// Section Header: "What are you looking for"', '''// Free Content Banner
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

            // Section Header: "What are you looking for"''', c, flags=re.DOTALL)
    return c
modify(r"ui\screens\home\HomeScreen.kt", fix_home)

# 2. TestSeriesDetailScreen.kt
def fix_tsd(c):
    c = re.sub(r',\s*onUnlockClick\s*:\s*\(\)\s*->\s*Unit', '', c)
    c = re.sub(r'// Sticky Unlock Button[\s\S]*?Button.*?\}\s*\}', '', c) 
    c = re.sub(r'onClick = \{\s*if \(uiState\.isUnlocked \|\| folder\.title\.contains\("Free", ignoreCase = true\)\) \{\s*onFolderClick\(folder\.id\)\s*\} else \{\s*onUnlockClick\(\)\s*\}\s*\}', 'onClick = { onFolderClick(folder.id) }', c)
    return c
modify(r"ui\screens\tests\TestSeriesDetailScreen.kt", fix_tsd)

# 3. TestListScreen.kt
def fix_tls(c):
    c = re.sub(r',\s*onUnlockClick\s*:\s*\(\)\s*->\s*Unit', '', c)
    c = re.sub(r'val canAttempt = test\.isFree \|\| uiState\.isUnlocked', 'val canAttempt = true', c)
    c = re.sub(r'onClick = \{\s*if \(canAttempt\) \{\s*onTestClick\(test\.id\)\s*\} else \{\s*onUnlockClick\(\)\s*\}\s*\}', 'onClick = { onTestClick(test.id) }', c)
    c = re.sub(r'if \(!canAttempt\) \{\s*Box.*?Icons\.Default\.Lock.*?\}\s*\}', '', c, flags=re.DOTALL)
    c = re.sub(r'// Sticky Unlock Button.*?if \(uiState\.tests\.any \{ !it\.isFree \} && !uiState\.isUnlocked\) \{.*?StickyBottomButton.*?\}\s*\}', '}', c, flags=re.DOTALL)
    return c
modify(r"ui\screens\tests\TestListScreen.kt", fix_tls)

# 4. TestInstructionsScreen.kt
def fix_tis(c):
    c = re.sub(r',\s*onGetPassClick\s*:\s*\(\)\s*->\s*Unit', '', c)
    c = re.sub(r'// Pass Banner.*?Card\(.*?onGetPassClick\(\).*?\}\s*\}\s*Spacer\(modifier = Modifier\.height\(16\.dp\)\)', '', c, flags=re.DOTALL)
    return c
modify(r"ui\screens\exam\TestInstructionsScreen.kt", fix_tis)

# 5. StudyMaterialListScreen.kt
def fix_smls(c):
    c = re.sub(r',\s*onUnlockClick\s*:\s*\(\)\s*->\s*Unit', '', c)
    c = re.sub(r'onClick = \{\s*if \(material\.isFree\) \{\s*onMaterialClick\(material\.id\)\s*\} else \{\s*onUnlockClick\(\)\s*\}\s*\}', 'onClick = { onMaterialClick(material.id) }', c)
    c = re.sub(r'if \(!material\.isFree\) \{\s*Container\(.*?Text\(text = "LOCKED".*?\}\s*\}', '', c, flags=re.DOTALL)
    c = re.sub(r'// Lock Icon Overlay\s*if \(!material\.isFree\) \{.*?\}\s*\}', '}', c, flags=re.DOTALL)
    c = re.sub(r'LazyColumn\((.*?)\) \{', r'LazyColumn(\1) {\n            item {\n                Text(\n                    text = "Tap any note to open it. To add your PDFs, upload them to Google Drive and share the link.",\n                    color = TextSecondaryDark,\n                    fontSize = 13.sp,\n                    modifier = Modifier.padding(bottom = 16.dp, start = 20.dp, end = 20.dp)\n                )\n            }', c, count=1)
    return c
modify(r"ui\screens\study\StudyMaterialListScreen.kt", fix_smls)

# 6. MockStudyMaterialRepository.kt
def fix_msmr(c):
    c = re.sub(r'isFree = false', 'isFree = true', c)
    c = re.sub(r'val studyMaterials = listOf\(', '// TODO: Replace PLACEHOLDER_* with real Google Drive file IDs after uploading PDFs\n    val studyMaterials = listOf(', c)
    
    # Specific replacements based on id
    c = re.sub(r'(id = "sm_1".*?pdfUrl = )"[^"]+"', r'\1"https://drive.google.com/file/d/PLACEHOLDER_MPLRC/preview"', c, flags=re.DOTALL)
    c = re.sub(r'(id = "sm_2".*?pdfUrl = )"[^"]+"', r'\1"https://drive.google.com/file/d/PLACEHOLDER_REASONING/preview"', c, flags=re.DOTALL)
    c = re.sub(r'(id = "sm_3".*?pdfUrl = )"[^"]+"', r'\1"https://drive.google.com/file/d/PLACEHOLDER_MPGK/preview"', c, flags=re.DOTALL)
    c = re.sub(r'(id = "sm_4".*?pdfUrl = )"[^"]+"', r'\1"https://drive.google.com/file/d/PLACEHOLDER_LANDRECORDS/preview"', c, flags=re.DOTALL)
    c = re.sub(r'(id = "sm_5".*?pdfUrl = )"[^"]+"', r'\1"https://drive.google.com/file/d/PLACEHOLDER_MATHSCIENCE/preview"', c, flags=re.DOTALL)
    return c
modify(r"data\mock\MockStudyMaterialRepository.kt", fix_msmr)

# 7. AppDrawer.kt
def fix_drawer(c):
    c = re.sub(r'DrawerItem\(\s*icon = Icons\.Default\.CardMembership,\s*label = "Pass",\s*isSelected = currentRoute == Screen\.Pass\.route,\s*onClick = \{ onNavigate\(Screen\.Pass\.route\) \}\s*\)\s*Spacer\(modifier = Modifier\.height\(8\.dp\)\)', '', c)
    return c
modify(r"ui\screens\main\AppDrawer.kt", fix_drawer)

# 8. MainScreen.kt
def fix_main(c):
    c = re.sub(r'import com\.example\.abhyaas\.ui\.screens\.pass\.PassScreen\n', '', c)
    c = re.sub(r'composable\(Screen\.Pass\.route\) \{\s*PassScreen\(\)\s*\}', '', c)
    c = re.sub(r'BottomNavItem\("Pass", Screen\.Pass\.route, Icons\.Default\.CardMembership\),', '', c)
    c = re.sub(r'onPassClick = \{[^{}]*\},?', '', c, flags=re.DOTALL)
    return c
modify(r"ui\screens\main\MainScreen.kt", fix_main)

# 9. TestsScreen.kt and UpdatesScreen.kt
def fix_tests_updates(c):
    c = re.sub(r',\s*onPassClick\s*:\s*\(\)\s*->\s*Unit', '', c)
    c = re.sub(r'// Pass Banner.*?Card\(.*?onPassClick\(\).*?\}\s*\}\s*Spacer\(modifier = Modifier\.height\(24\.dp\)\)', '', c, flags=re.DOTALL)
    return c
modify(r"ui\screens\tests\TestsScreen.kt", fix_tests_updates)
modify(r"ui\screens\updates\UpdatesScreen.kt", fix_tests_updates)

# 10. AppNavHost.kt
def fix_navhost(c):
    c = re.sub(r',\s*onUnlockClick = \{ navController\.navigate\(Screen\.Pass\.route\) \}', '', c)
    c = re.sub(r',\s*onPassClick = \{ navController\.navigate\(Screen\.Pass\.route\) \}', '', c)
    c = re.sub(r',\s*onGetPassClick = \{ navController\.navigate\(Screen\.Pass\.route\) \}', '', c)
    c = re.sub(r',\s*onUnlockClick = \{\}', '', c)
    
    c = re.sub(r'import com\.example\.abhyaas\.ui\.screens\.pass\.PassScreen\n', '', c)
    c = re.sub(r'composable\(Screen\.Pass\.route\) \{\s*PassScreen\(\)\s*\}', '', c)
    return c
modify(r"ui\navigation\AppNavHost.kt", fix_navhost)

# 11. PdfViewerScreen.kt
def fix_pdfviewer(c):
    c = re.sub(r'val googleDocsUrl = "https://docs\.google\.com/gview\?embedded=true&url=\$pdfUrl"', '''val finalUrl = if (pdfUrl.contains("drive.google.com")) {
        pdfUrl
    } else {
        "https://docs.google.com/viewer?embedded=true&url=$pdfUrl"
    }''', c)
    c = re.sub(r'loadUrl\(googleDocsUrl\)', 'loadUrl(finalUrl)', c)
    c = re.sub(r'settings\.javaScriptEnabled = true', 'settings.javaScriptEnabled = true\n                    settings.domStorageEnabled = true', c)
    return c
modify(r"ui\screens\study\PdfViewerScreen.kt", fix_pdfviewer)

# Also fix Screen.kt to remove Pass
def fix_screen(c):
    c = re.sub(r'object Pass : Screen\("pass"\)', '', c)
    return c
modify(r"ui\navigation\Screen.kt", fix_screen)
