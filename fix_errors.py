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

# Fix AppNavHost
def fix_navhost(c):
    c = re.sub(r',\s*onUnlockClick\s*=\s*\{\s*navController\.navigate\(Screen\.Pass\.route\)\s*\}', '', c)
    c = re.sub(r',\s*onGetPassClick\s*=\s*\{\s*navController\.navigate\(Screen\.Pass\.route\)\s*\}', '', c)
    return c
modify(r"ui\navigation\AppNavHost.kt", fix_navhost)

# Fix TestInstructionsScreen.kt
def fix_tis(c):
    c = re.sub(r',\s*onGetPassClick\s*:\s*\(\)\s*->\s*Unit', '', c) # this regex in previous script removed `onGetPassClick = {}` in signature if it was there? Wait, `onGetPassClick: () -> Unit = {}`
    # Let's clean the parameter list
    c = re.sub(r',\s*onGetPassClick\s*:\s*\(\)\s*->\s*Unit\s*=\s*\{\}', '', c)
    # Check if there is an unresolved reference
    c = re.sub(r'onGetPassClick\(\)', '', c)
    # And maybe I left hanging commas or broken syntax
    return c
modify(r"ui\screens\exam\TestInstructionsScreen.kt", fix_tis)

# Fix HomeScreen.kt
def fix_home(c):
    c = re.sub(r',\s*onPassClick\s*:\s*\(\)\s*->\s*Unit\s*=\s*\{\}', '', c)
    c = re.sub(r'onPassClick\(\)', '', c)
    return c
modify(r"ui\screens\home\HomeScreen.kt", fix_home)

# Fix MainScreen.kt
def fix_main(c):
    c = re.sub(r'import com\.example\.abhyaas\.ui\.screens\.pass\.PassScreen', '', c)
    c = re.sub(r',\s*onPassClick\s*=\s*\{\s*navController\.navigate\("pass"\)\s*\}', '', c)
    c = re.sub(r',\s*onPassClick\s*=\s*\{\s*navController\.navigate\(Screen\.Pass\.route\)\s*\}', '', c)
    c = re.sub(r'composable\(Screen\.Pass\.route\)\s*\{\s*PassScreen\(\)\s*\}', '', c)
    c = re.sub(r'BottomNavItem\("Pass", Screen\.Pass\.route, Icons\.Default\.CardMembership\),', '', c)
    return c
modify(r"ui\screens\main\MainScreen.kt", fix_main)

# Fix StudyMaterialListScreen.kt
def fix_study(c):
    c = re.sub(r',\s*onUnlockClick\s*:\s*\(\)\s*->\s*Unit\s*=\s*\{\}', '', c)
    c = re.sub(r'onUnlockClick\(\)', '', c)
    return c
modify(r"ui\screens\study\StudyMaterialListScreen.kt", fix_study)

# Fix TestListScreen.kt
def fix_tls(c):
    c = re.sub(r',\s*onUnlockClick\s*:\s*\(\)\s*->\s*Unit\s*=\s*\{\}', '', c)
    c = re.sub(r'onUnlockClick\(\)', '', c)
    return c
modify(r"ui\screens\tests\TestListScreen.kt", fix_tls)

# Fix TestSeriesDetailScreen.kt
def fix_tsd(c):
    c = re.sub(r',\s*onUnlockClick\s*:\s*\(\)\s*->\s*Unit\s*=\s*\{\}', '', c)
    c = re.sub(r'onUnlockClick\(\)', '', c)
    return c
modify(r"ui\screens\tests\TestSeriesDetailScreen.kt", fix_tsd)
