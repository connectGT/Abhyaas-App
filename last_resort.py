# -*- coding: utf-8 -*-
import os
import re

base = r"C:\Users\gurut\AndroidStudioProjects\Abhyaas\app\src\main\java\com\example\abhyaas"

def del_lines(rel, lines_to_del):
    path = os.path.join(base, rel)
    with open(path, 'r', encoding='utf-8') as f:
        lines = f.readlines()
    for l in lines_to_del:
        if 0 <= l - 1 < len(lines):
            lines[l - 1] = "// " + lines[l - 1]
    with open(path, 'w', encoding='utf-8') as f:
        f.writelines(lines)

# Fix StudyMaterialListScreen signature
path_smls = os.path.join(base, r"ui\screens\study\StudyMaterialListScreen.kt")
with open(path_smls, 'r', encoding='utf-8') as f:
    c = f.read()
c = re.sub(r',\s*onUnlockClick:\s*\(\)\s*->\s*Unit\s*=\s*\{\}', '', c)
c = c.replace('onUnlockClick()', '{}')
with open(path_smls, 'w', encoding='utf-8') as f:
    f.write(c)

# Fix MainScreen.kt missing Pass
del_lines(r"ui\screens\main\MainScreen.kt", [133, 134, 135, 164, 165, 166])

# Fix TestListScreen.kt onUnlockClick
del_lines(r"ui\screens\tests\TestListScreen.kt", [107, 108, 109, 110, 111, 112, 113, 114, 115])

# Fix TestSeriesDetailScreen.kt onUnlockClick
del_lines(r"ui\screens\tests\TestSeriesDetailScreen.kt", [171, 172, 173, 174, 175, 176, 177, 178])

# Fix AppNavHost.kt 154
del_lines(r"ui\navigation\AppNavHost.kt", [154])