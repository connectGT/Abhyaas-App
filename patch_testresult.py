import io

with io.open(r"app\src\main\java\com\example\abhyaas\data\model\TestResult.kt", "r", encoding="utf-8") as f:
    content = f.read()

content = content.replace(
    """    val attemptDate: String = "Sep 27, 2026"
)""",
    """    val attemptDate: String = "Sep 27, 2026",
    val userAnswers: Map<Int, Int>? = null
)"""
)

with io.open(r"app\src\main\java\com\example\abhyaas\data\model\TestResult.kt", "w", encoding="utf-8") as f:
    f.write(content)

print("TestResult patched.")
