# -*- coding: utf-8 -*-
import os

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

del_lines(r"ui\screens\main\MainScreen.kt", [133, 134, 135, 164, 165, 166])
del_lines(r"ui\screens\tests\TestListScreen.kt", [109, 108, 110, 107, 111, 112, 113, 114])
del_lines(r"ui\screens\tests\TestSeriesDetailScreen.kt", [49, 172, 173, 174, 171, 175, 176, 177])