import io

with io.open(r"app\src\main\java\com\example\abhyaas\data\repository\impl\MockTestResultRepositoryImpl.kt", "r", encoding="utf-8") as f:
    content = f.read()

content = content.replace(
    """        val newResult = baseResult.copy(
            score = score,
            correctCount = correctCount,
            incorrectCount = incorrectCount,
            unattemptedCount = unattemptedCount,
            accuracy = accuracy,
            totalMarks = (totalQ * 2).toFloat()
        )""",
    """        val newResult = baseResult.copy(
            score = score,
            correctCount = correctCount,
            incorrectCount = incorrectCount,
            unattemptedCount = unattemptedCount,
            accuracy = accuracy,
            totalMarks = (totalQ * 2).toFloat(),
            userAnswers = answers
        )"""
)

with io.open(r"app\src\main\java\com\example\abhyaas\data\repository\impl\MockTestResultRepositoryImpl.kt", "w", encoding="utf-8") as f:
    f.write(content)

print("Submit test patched.")
