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

def clean(c):
    c = re.sub(r',\s*onUnlockClick:\s*\(\)\s*->\s*Unit\s*=\s*\{\}', '', c)
    c = re.sub(r'onUnlockClick:\s*\(\)\s*->\s*Unit\s*=\s*\{\},\s*', '', c)
    c = re.sub(r',\s*onGetPassClick:\s*\(\)\s*->\s*Unit\s*=\s*\{\}', '', c)
    c = re.sub(r'onGetPassClick:\s*\(\)\s*->\s*Unit\s*=\s*\{\},\s*', '', c)
    c = re.sub(r',\s*onPassClick:\s*\(\)\s*->\s*Unit\s*=\s*\{\}', '', c)
    c = re.sub(r'onPassClick:\s*\(\)\s*->\s*Unit\s*=\s*\{\},\s*', '', c)
    c = re.sub(r'onUnlockClick\(\)', '{}', c)
    c = re.sub(r'onGetPassClick\(\)', '{}', c)
    c = re.sub(r'onPassClick\(\)', '{}', c)
    c = re.sub(r'onClick\s*=\s*onPassClick', 'onClick = {}', c)
    c = re.sub(r'onClick\s*=\s*onGetPassClick', 'onClick = {}', c)
    c = re.sub(r'onClick\s*=\s*onUnlockClick', 'onClick = {}', c)
    return c

modify(r"ui\screens\tests\TestListScreen.kt", clean)
modify(r"ui\screens\tests\TestSeriesDetailScreen.kt", clean)
modify(r"ui\screens\exam\TestInstructionsScreen.kt", clean)
modify(r"ui\screens\home\HomeScreen.kt", clean)
modify(r"ui\screens\main\MainScreen.kt", clean)
modify(r"ui\navigation\AppNavHost.kt", clean)