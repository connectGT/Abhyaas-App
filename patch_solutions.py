import io
import re

with io.open(r"app\src\main\java\com\example\abhyaas\ui\screens\result\SolutionsTab.kt", "r", encoding="utf-8") as f:
    content = f.read()

old_filter = """    // Filter questions in the current section
    val filteredQuestions = remember(currentSection, selectedFilter) {
        currentSection.questions.filter { _ ->
            when (selectedFilter) {
                SolutionListFilter.ALL -> true
                SolutionListFilter.UNATTEMPTED -> true // All are unattempted in default scorecard
                SolutionListFilter.CORRECT -> false
                SolutionListFilter.INCORRECT -> false
            }
        }
    }"""

new_filter = """    // Filter questions dynamically based on testResult userAnswers
    val filteredQuestions = remember(currentSection, selectedFilter, testResult) {
        val userAnswers = testResult?.userAnswers ?: emptyMap()
        currentSection.questions.filter { question ->
            val userAnswer = userAnswers[question.id]
            when (selectedFilter) {
                SolutionListFilter.ALL -> true
                SolutionListFilter.UNATTEMPTED -> userAnswer == null
                SolutionListFilter.CORRECT -> userAnswer != null && userAnswer == question.correctOptionIndex
                SolutionListFilter.INCORRECT -> userAnswer != null && userAnswer != question.correctOptionIndex
            }
        }
    }"""

content = content.replace(old_filter, new_filter)

with io.open(r"app\src\main\java\com\example\abhyaas\ui\screens\result\SolutionsTab.kt", "w", encoding="utf-8") as f:
    f.write(content)

print("Solutions Tab filtered.")
