with open(r"app\src\main\java\com\example\abhyaas\ui\screens\exam\TestInstructionsScreen.kt", "r", encoding="utf-8") as f:
    content = f.read()

# Fix bottomBar
content = content.replace(
    "bottomBar = {\n            Column(\n                modifier = Modifier\n                    .fillMaxWidth()\n                    .background(DarkBackground)\n                    .border(1.dp, DarkBorderSubtle, RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))\n                    .padding(horizontal = 16.dp, vertical = 12.dp),",
    "bottomBar = {\n            Column(\n                modifier = Modifier\n                    .fillMaxWidth()\n                    .background(DarkBackground)\n                    .border(1.dp, DarkBorderSubtle, RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))\n                    .navigationBarsPadding()\n                    .padding(horizontal = 16.dp, vertical = 12.dp),"
)

with open(r"app\src\main\java\com\example\abhyaas\ui\screens\exam\TestInstructionsScreen.kt", "w", encoding="utf-8") as f:
    f.write(content)
print("Insets fixed!")
