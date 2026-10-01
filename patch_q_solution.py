import io

with io.open(r"app\src\main\java\com\example\abhyaas\ui\screens\result\QuestionSolutionView.kt", "r", encoding="utf-8") as f:
    content = f.read()

old_choice = """                // Options List
                val userChoice = userReattemptAnswers[currentQuestion.id]
                val isForcedSolution = forceSolutionRevealed[currentQuestion.id] ?: false"""

new_choice = """                // Options List
                val userChoice = if (isReattemptMode) {
                    userReattemptAnswers[currentQuestion.id]
                } else {
                    testResult?.userAnswers?.get(currentQuestion.id)
                }
                val isForcedSolution = forceSolutionRevealed[currentQuestion.id] ?: false"""

content = content.replace(old_choice, new_choice)

with io.open(r"app\src\main\java\com\example\abhyaas\ui\screens\result\QuestionSolutionView.kt", "w", encoding="utf-8") as f:
    f.write(content)

print("Question Solution View patched.")
