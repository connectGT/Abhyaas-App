import io

with io.open(r"app\src\main\java\com\example\abhyaas\ui\screens\exam\ActiveTestScreen.kt", "r", encoding="utf-8") as f:
    content = f.read()

old_pill = """                    // Last 15 Mins Warning Pill
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = Color(0xFF451A1A),
                        border = BorderStroke(1.dp, Color(0xFFEF4444).copy(alpha = 0.6f))
                    ) {
                        Text(
                            text = "Last 15 Mins",
                            color = Color(0xFFEF4444),
                            fontSize = 11.5.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }"""

new_pill = """                    // Dynamic Last X Mins Warning Pill
                    if (uiState.timeRemainingSeconds <= 15 * 60) {
                        val minsLeft = kotlin.math.ceil(uiState.timeRemainingSeconds / 60.0).toInt().coerceAtLeast(1)
                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = Color(0xFF451A1A),
                            border = BorderStroke(1.dp, Color(0xFFEF4444).copy(alpha = 0.6f))
                        ) {
                            Text(
                                text = "Last $minsLeft Mins",
                                color = Color(0xFFEF4444),
                                fontSize = 11.5.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                            )
                        }
                    }"""

content = content.replace(old_pill, new_pill)

with io.open(r"app\src\main\java\com\example\abhyaas\ui\screens\exam\ActiveTestScreen.kt", "w", encoding="utf-8") as f:
    f.write(content)

print("ActiveTestScreen patched.")
