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

def fix_tis(c):
    c = c.replace('onAgreeAndContinue: (testId: String, lang: String) -> Unit = { _, _ -> } = {}', 'onAgreeAndContinue: (testId: String, lang: String) -> Unit = { _, _ -> }')
    c = re.sub(r'onGetPassClick\(\)', '', c)
    return c
modify(r"ui\screens\exam\TestInstructionsScreen.kt", fix_tis)

def fix_home(c):
    c = c.replace('onAvatarClick: () -> Unit = {} = {}', 'onAvatarClick: () -> Unit = {}')
    c = re.sub(r'onPassClick\(\)', '', c)
    c = c.replace('.clickable {  }', '')
    return c
modify(r"ui\screens\home\HomeScreen.kt", fix_home)

def fix_tls(c):
    c = c.replace('onViewResultClick: (testId: String) -> Unit = {} = {}', 'onViewResultClick: (testId: String) -> Unit = {}')
    c = re.sub(r'onUnlockClick\(\)', '', c)
    c = c.replace('.clickable {  }', '')
    return c
modify(r"ui\screens\tests\TestListScreen.kt", fix_tls)

def fix_tsd(c):
    c = c.replace('onFolderClick: (folderId: String) -> Unit = {} = {}', 'onFolderClick: (folderId: String) -> Unit = {}')
    c = re.sub(r'onUnlockClick\(\)', '', c)
    return c
modify(r"ui\screens\tests\TestSeriesDetailScreen.kt", fix_tsd)

def fix_main(c):
    c = c.replace('onPassClick = ,', '')
    c = c.replace('onPassClick = \n', '\n')
    c = re.sub(r',\s*onPassClick = \{.*?\}', '', c)
    c = c.replace('import com.example.abhyaas.ui.screens.pass.PassScreen', '')
    return c
modify(r"ui\screens\main\MainScreen.kt", fix_main)

def fix_navhost(c):
    c = re.sub(r',\s*onUnlockClick\s*=\s*\{\s*navController\.navigate\([^\)]+\)\s*\}', '', c)
    c = re.sub(r',\s*onGetPassClick\s*=\s*\{\s*navController\.navigate\([^\)]+\)\s*\}', '', c)
    c = re.sub(r',\s*onPassClick\s*=\s*\{\s*navController\.navigate\([^\)]+\)\s*\}', '', c)
    c = c.replace(',\n                onUnlockClick = {', '')
    return c
modify(r"ui\navigation\AppNavHost.kt", fix_navhost)
