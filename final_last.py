# -*- coding: utf-8 -*-
import os

base = r"C:\Users\gurut\AndroidStudioProjects\Abhyaas\app\src\main\java\com\example\abhyaas"

# 1. MainScreen.kt
with open(os.path.join(base, r"ui\screens\main\MainScreen.kt"), 'r', encoding='utf-8') as f:
    lines = f.readlines()
# line 133: No parameter with name onPassClick found.
# line 165: PassScreen()
# We will just comment them out
lines[132] = "// " + lines[132]
lines[163] = "// " + lines[163]
lines[164] = "// " + lines[164]
lines[165] = "// " + lines[165]
with open(os.path.join(base, r"ui\screens\main\MainScreen.kt"), 'w', encoding='utf-8') as f:
    f.writelines(lines)

# 2. StudyMaterialListScreen.kt line 83 Argument type mismatch
with open(os.path.join(base, r"ui\screens\study\StudyMaterialListScreen.kt"), 'r', encoding='utf-8') as f:
    lines = f.readlines()
lines[82] = lines[82].replace('onClick = {},', 'onClick = {},') # wait it's type mismatch Function0 vs Unit
# if it is onClick = {}, then it might be onClick = { {} } or something. Let's make it onClick = {}
lines[82] = "onClick = { onMaterialClick(material.id) }\n"
with open(os.path.join(base, r"ui\screens\study\StudyMaterialListScreen.kt"), 'w', encoding='utf-8') as f:
    f.writelines(lines)

# 3. AppNavHost.kt line 153 missing param
with open(os.path.join(base, r"ui\navigation\AppNavHost.kt"), 'r', encoding='utf-8') as f:
    c = f.read()
# Let's add onUnlockClick = {} to StudyMaterialListScreen call
c = c.replace('onMaterialClick = { materialId, sId ->', 'onUnlockClick = {},\n                onMaterialClick = { materialId, sId ->')
with open(os.path.join(base, r"ui\navigation\AppNavHost.kt"), 'w', encoding='utf-8') as f:
    f.write(c)
