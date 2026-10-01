import io

with io.open(r"app\src\main\java\com\example\abhyaas\ui\screens\tests\TestListScreen.kt", "r", encoding="utf-8") as f:
    content = f.read()

# Replace hardcoded SSC prefix
old_prefix = """            // Test Title
            Column {
                Text(
                    text = "SSC Selection Post (Phase 14):",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = test.title,
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }"""

new_prefix = """            // Test Title
            Column {
                Text(
                    text = test.title,
                    color = Color.White,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
            }"""

content = content.replace(old_prefix, new_prefix)

with io.open(r"app\src\main\java\com\example\abhyaas\ui\screens\tests\TestListScreen.kt", "w", encoding="utf-8") as f:
    f.write(content)

print("TestListScreen SSC text patched.")
