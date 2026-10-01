import io

with io.open(r"app\src\main\java\com\example\abhyaas\ui\screens\tests\TestsScreen.kt", "r", encoding="utf-8") as f:
    content = f.read()

content = content.replace('"SSC SELECTION POST 2026"', '"MP Nayab Tehsildar 2026"')

with io.open(r"app\src\main\java\com\example\abhyaas\ui\screens\tests\TestsScreen.kt", "w", encoding="utf-8") as f:
    f.write(content)

print("TestsScreen patched.")
