# -*- coding: utf-8 -*-
import os
import re

base = r"C:\Users\gurut\AndroidStudioProjects\Abhyaas\app\src\main\java\com\example\abhyaas"
def strip_unresolved(rel):
    path = os.path.join(base, rel)
    with open(path, 'r', encoding='utf-8') as f:
        c = f.read()
    
    # Remove all references to onUnlockClick(), onGetPassClick(), onPassClick() in code body
    c = re.sub(r'onClick\s*=\s*\{\s*on[a-zA-Z]+Click\(\)\s*\}', 'onClick = {}', c)
    c = re.sub(r'onClick\s*=\s*on[a-zA-Z]+Click,?', 'onClick = {},', c)
    c = re.sub(r'onUnlockClick\(\)', '{}', c)
    c = re.sub(r'onGetPassClick\(\)', '{}', c)
    c = re.sub(r'onPassClick\(\)', '{}', c)

    # Remove all parameter definitions in signatures
    c = re.sub(r',\s*onUnlockClick\s*:\s*\(\)\s*->\s*Unit\s*=\s*\{\}', '', c)
    c = re.sub(r',\s*onGetPassClick\s*:\s*\(\)\s*->\s*Unit\s*=\s*\{\}', '', c)
    c = re.sub(r',\s*onPassClick\s*:\s*\(\)\s*->\s*Unit\s*=\s*\{\}', '', c)
    
    # And trailing comma edge cases
    c = re.sub(r'onUnlockClick\s*:\s*\(\)\s*->\s*Unit\s*=\s*\{\},\s*', '', c)
    c = re.sub(r'onGetPassClick\s*:\s*\(\)\s*->\s*Unit\s*=\s*\{\},\s*', '', c)
    c = re.sub(r'onPassClick\s*:\s*\(\)\s*->\s*Unit\s*=\s*\{\},\s*', '', c)
    
    with open(path, 'w', encoding='utf-8') as f:
        f.write(c)

files = [
    r"ui\navigation\AppNavHost.kt",
    r"ui\screens\main\MainScreen.kt",
    r"ui\screens\MainScreen.kt",
    r"ui\screens\exam\TestInstructionsScreen.kt",
    r"ui\screens\home\HomeScreen.kt",
    r"ui\screens\tests\TestListScreen.kt",
    r"ui\screens\tests\TestSeriesDetailScreen.kt",
    r"ui\screens\study\StudyMaterialListScreen.kt",
]

for f in files:
    strip_unresolved(f)
