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

def fix_mainscreen1(c):
    c = c.replace('Screen.Pass,', '')
    c = c.replace('composable(Screen.Pass.route) { PassScreen() }', '')
    return c
modify(r"ui\screens\MainScreen.kt", fix_mainscreen1)

def fix_mainscreen2(c):
    c = c.replace('Screen.Pass,', '')
    c = c.replace('composable(Screen.Pass.route) { PassScreen() }', '')
    c = c.replace('BottomNavItem(Screen.Pass.route,', '//') # just disable it
    return c
modify(r"ui\screens\main\MainScreen.kt", fix_mainscreen2)

def fix_tls(c):
    c = re.sub(r'Box\([^)]*\)\s*\{\s*Button\([^)]*onUnlockClick[^)]*\)\s*\{\s*Text\([^)]*Unlock Test Series[^)]*\)\s*\}\s*\}', '', c)
    return c
modify(r"ui\screens\tests\TestListScreen.kt", fix_tls)

def fix_tsd(c):
    c = re.sub(r'Box\([^)]*\)\s*\{\s*Button\([^)]*onUnlockClick[^)]*\)\s*\{\s*Text\([^)]*Unlock Test Series[^)]*\)\s*\}\s*\}', '', c)
    # also fix the syntax error on line 49
    c = c.replace('onFolderClick: (folderId: String) -> Unit = {} = {}', 'onFolderClick: (folderId: String) -> Unit = {}')
    return c
modify(r"ui\screens\tests\TestSeriesDetailScreen.kt", fix_tsd)

def fix_tis(c):
    # just replace onClick = onGetPassClick with onClick = {}
    c = c.replace('onClick = onGetPassClick', 'onClick = {}')
    return c
modify(r"ui\screens\exam\TestInstructionsScreen.kt", fix_tis)

def fix_home(c):
    c = c.replace('onClick = onPassClick', 'onClick = {}')
    return c
modify(r"ui\screens\home\HomeScreen.kt", fix_home)

def fix_navhost(c):
    # fix missing onUnlockClick on line 154
    c = c.replace('onMaterialClick = { materialId, sId ->', 'onUnlockClick = {},\nonMaterialClick = { materialId, sId ->')
    return c
modify(r"ui\navigation\AppNavHost.kt", fix_navhost)
