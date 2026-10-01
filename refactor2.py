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

def fix_drawer(c):
    c = re.sub(r'DrawerItem\(\s*icon\s*=\s*Icons\.Default\.CardMembership.*?\}\s*\)\s*Spacer\(modifier = Modifier\.height\(8\.dp\)\)', '', c, flags=re.DOTALL)
    return c
modify(r"ui\screens\main\AppDrawer.kt", fix_drawer)

def fix_screen(c):
    c = re.sub(r'data object Pass : Screen\("pass"\)', '', c)
    return c
modify(r"ui\navigation\Screen.kt", fix_screen)

def fix_tests_updates(c):
    c = re.sub(r',\s*onPassClick\s*:\s*\(\)\s*->\s*Unit', '', c)
    c = re.sub(r'// Pass Banner[\s\S]*?Card\([\s\S]*?onPassClick\(\)[\s\S]*?\}\s*\}\s*Spacer\(modifier = Modifier\.height\(\d+\.dp\)\)', '', c)
    return c
modify(r"ui\screens\tests\TestsScreen.kt", fix_tests_updates)
modify(r"ui\screens\updates\UpdatesScreen.kt", fix_tests_updates)
