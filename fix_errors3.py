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

def fix_tsd(c):
    c = c.replace('onFolderClick: (folderId: String) -> Unit = {} = {}', 'onFolderClick: (folderId: String) -> Unit = {}')
    c = re.sub(r'else\s*\{\s*onUnlockClick\(\)\s*\}', '', c)
    return c
modify(r"ui\screens\tests\TestSeriesDetailScreen.kt", fix_tsd)

def fix_tls(c):
    c = re.sub(r'else\s*\{\s*onUnlockClick\(\)\s*\}', '', c)
    return c
modify(r"ui\screens\tests\TestListScreen.kt", fix_tls)

def fix_home(c):
    c = re.sub(r'onPassClick\(\)', '', c)
    c = re.sub(r'\.clickable \{.*?onPassClick.*?\}', '', c)
    return c
modify(r"ui\screens\home\HomeScreen.kt", fix_home)

def fix_main(c):
    c = c.replace('import com.example.abhyaas.ui.screens.pass.PassScreen', '')
    c = re.sub(r'composable\(Screen\.Pass\.route\)\s*\{\s*PassScreen\(\)\s*\}', '', c)
    c = re.sub(r'onPassClick\s*=\s*\{\s*navController\.navigate\([^\)]+\)\s*\},', '', c)
    c = re.sub(r'onPassClick\s*=\s*\{\s*navController\.navigate\([^\)]+\)\s*\}', '', c)
    return c
modify(r"ui\screens\main\MainScreen.kt", fix_main)

def fix_tis(c):
    c = re.sub(r'onGetPassClick\(\)', '', c)
    return c
modify(r"ui\screens\exam\TestInstructionsScreen.kt", fix_tis)
