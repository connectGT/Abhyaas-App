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
        
def f_tis(c):
    c = re.sub(r',\s*onGetPassClick:\s*\(\)\s*->\s*Unit\s*=\s*\{\}', '', c)
    c = re.sub(r'// Pass Banner[\s\S]*?Spacer\(modifier = Modifier\.height\(16\.dp\)\)', '', c)
    return c
modify(r"ui\screens\exam\TestInstructionsScreen.kt", f_tis)

def f_smls(c):
    c = re.sub(r',\s*onUnlockClick:\s*\(\)\s*->\s*Unit\s*=\s*\{\}', '', c)
    c = re.sub(r'onClick = \{\s*if \(material\.isFree\) \{\s*onMaterialClick\(material\.id\)\s*\} else \{\s*onUnlockClick\(\)\s*\}\s*\}', 'onClick = { onMaterialClick(material.id) }', c)
    c = re.sub(r'if \(!material\.isFree\)\s*\{\s*[^{}]*Container[\s\S]*?Text\(text = "LOCKED"[\s\S]*?\}\s*\}', '', c)
    c = re.sub(r'// Lock Icon Overlay\s*if \(!material\.isFree\)\s*\{[\s\S]*?\}\s*\}', '}', c)
    c = re.sub(r'LazyColumn\((.*?)\)\s*\{', r'LazyColumn(\1) {\n            item {\n                Text(\n                    text = "Tap any note to open it. To add your PDFs, upload them to Google Drive and share the link.",\n                    color = TextSecondaryDark,\n                    fontSize = 13.sp,\n                    modifier = Modifier.padding(bottom = 16.dp, start = 20.dp, end = 20.dp)\n                )\n            }', c, count=1)
    return c
modify(r"ui\screens\study\StudyMaterialListScreen.kt", f_smls)

def f_main(c):
    c = c.replace('import com.example.abhyaas.ui.screens.pass.PassScreen\n', '')
    c = re.sub(r'composable\(Screen\.Pass\.route\)\s*\{\s*PassScreen\(\)\s*\}', '', c)
    c = re.sub(r'BottomNavItem\("Pass", Screen\.Pass\.route, Icons\.Filled\.CardMembership\),\s*', '', c)
    c = re.sub(r',\s*onPassClick = \{[^\}]*\}', '', c)
    return c
modify(r"ui\screens\main\MainScreen.kt", f_main)

def f_navhost(c):
    c = re.sub(r',\s*onUnlockClick\s*=\s*\{\s*navController\.navigate\(Screen\.Pass\.route\)\s*\}', '', c)
    c = re.sub(r',\s*onPassClick\s*=\s*\{\s*navController\.navigate\(Screen\.Pass\.route\)\s*\}', '', c)
    c = re.sub(r',\s*onGetPassClick\s*=\s*\{\s*navController\.navigate\(Screen\.Pass\.route\)\s*\}', '', c)
    c = c.replace('import com.example.abhyaas.ui.screens.pass.PassScreen\n', '')
    c = re.sub(r'composable\(Screen\.Pass\.route\)\s*\{\s*PassScreen\(\)\s*\}', '', c)
    return c
modify(r"ui\navigation\AppNavHost.kt", f_navhost)

def f_drawer(c):
    c = re.sub(r'DrawerNavigationItem\(\s*label = "Pass",\s*icon = Icons\.Default\.CardMembership,\s*isSelected = currentRoute == "pass",\s*subtitle = "ABHYAS Pass - Unlimited",\s*onClick = \{\s*onCloseDrawer\(\)\s*onNavigateToRoute\("pass"\)\s*\}\s*\)', '', c)
    return c
modify(r"ui\screens\main\AppDrawer.kt", f_drawer)

def f_screen(c):
    c = re.sub(r'object Pass : Screen\("pass", "Pass", Icons\.Filled\.CardMembership\)', '', c)
    return c
modify(r"ui\navigation\Screen.kt", f_screen)

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
    c = re.sub(r',\s*onPassClick:\s*\(\)\s*->\s*Unit\s*=\s*\{\}', '', c)
    c = re.sub(r'// Hero Banner Carousel Card \(matching tests tab\.png\)[\s\S]*?\}\s*\}\s*Spacer\(modifier = Modifier\.height\(24\.dp\)\)', '', c)
    return c
modify(r"ui\screens\tests\TestsScreen.kt", f_tests)
modify(r"ui\screens\updates\UpdatesScreen.kt", f_tests)
