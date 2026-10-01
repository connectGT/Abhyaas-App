with open(r"app\src\main\java\com\example\abhyaas\ui\screens\exam\ActiveTestScreen.kt", "r", encoding="utf-8") as f:
    content = f.read()

# Fix topBar
content = content.replace(
    "topBar = {\n            Column(\n                modifier = Modifier\n                    .fillMaxWidth()\n                    .background(Color(0xFF0F172A))\n            ) {",
    "topBar = {\n            Column(\n                modifier = Modifier\n                    .fillMaxWidth()\n                    .background(Color(0xFF0F172A))\n                    .statusBarsPadding()\n            ) {"
)

# Fix bottomBar
content = content.replace(
    "bottomBar = {\n            // Bottom Action Bar: [Mark For Review] [Save & Next]\n            Surface(\n                color = Color(0xFF0F172A),\n                border = BorderStroke(1.dp, Color(0xFF1E293B)),\n                modifier = Modifier.fillMaxWidth()\n            )",
    "bottomBar = {\n            // Bottom Action Bar: [Mark For Review] [Save & Next]\n            Surface(\n                color = Color(0xFF0F172A),\n                border = BorderStroke(1.dp, Color(0xFF1E293B)),\n                modifier = Modifier.fillMaxWidth().navigationBarsPadding()\n            )"
)

with open(r"app\src\main\java\com\example\abhyaas\ui\screens\exam\ActiveTestScreen.kt", "w", encoding="utf-8") as f:
    f.write(content)
print("Insets fixed!")
