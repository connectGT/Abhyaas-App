import io

with io.open(r"app\src\main\java\com\example\abhyaas\ui\screens\main\AppDrawer.kt", "r", encoding="utf-8") as f:
    content = f.read()

content = content.replace('"SSC CGL, Selection Post"', '"MPSEB, Nayab Tehsildar"')

with io.open(r"app\src\main\java\com\example\abhyaas\ui\screens\main\AppDrawer.kt", "w", encoding="utf-8") as f:
    f.write(content)

print("AppDrawer patched.")
