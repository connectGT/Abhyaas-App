# -*- coding: utf-8 -*-
import os

base = r"C:\Users\gurut\AndroidStudioProjects\Abhyaas\app\src\main\java\com\example\abhyaas"
def del_line(rel, line_no):
    path = os.path.join(base, rel)
    with open(path, 'r', encoding='utf-8') as f:
        lines = f.readlines()
    if 0 <= line_no - 1 < len(lines):
        lines[line_no - 1] = "\n"
    with open(path, 'w', encoding='utf-8') as f:
        f.writelines(lines)

del_line(r"ui\navigation\AppNavHost.kt", 154) # No value passed for onUnlockClick -> maybe delete the comma on line 153? Actually I will just replace the file contents safely.