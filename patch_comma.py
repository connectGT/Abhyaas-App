import io

with io.open(r"app\src\main\java\com\example\abhyaas\ui\screens\result\SolutionsTab.kt", "r", encoding="utf-8") as f:
    content = f.read()

content = content.replace("isCorrect, isIncorrect -> Color.White", "isCorrect || isIncorrect -> Color.White")

with io.open(r"app\src\main\java\com\example\abhyaas\ui\screens\result\SolutionsTab.kt", "w", encoding="utf-8") as f:
    f.write(content)

print("Comma fixed.")
