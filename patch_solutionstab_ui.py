import io
import re

with io.open(r"app\src\main\java\com\example\abhyaas\ui\screens\result\SolutionsTab.kt", "r", encoding="utf-8") as f:
    content = f.read()

old_items = """                items(filteredQuestions) { question ->
                    val isBookmarked = bookmarkedState[question.id] ?: false
                    QuestionPreviewCard(
                        question = question,
                        isHindi = isHindi,
                        isBookmarked = isBookmarked,
                        onBookmarkToggle = {
                            bookmarkedState[question.id] = !isBookmarked
                        },
                        onClick = {
                            selectedQuestionId = question.id
                        }
                    )
                }"""

new_items = """                items(filteredQuestions) { question ->
                    val isBookmarked = bookmarkedState[question.id] ?: false
                    val userAnswer = testResult?.userAnswers?.get(question.id)
                    QuestionPreviewCard(
                        question = question,
                        isHindi = isHindi,
                        isBookmarked = isBookmarked,
                        userAnswer = userAnswer,
                        onBookmarkToggle = {
                            bookmarkedState[question.id] = !isBookmarked
                        },
                        onClick = {
                            selectedQuestionId = question.id
                        }
                    )
                }"""

content = content.replace(old_items, new_items)

old_fun = """fun QuestionPreviewCard(
    question: Question,
    isHindi: Boolean,
    isBookmarked: Boolean,
    onBookmarkToggle: () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {"""

new_fun = """fun QuestionPreviewCard(
    question: Question,
    isHindi: Boolean,
    isBookmarked: Boolean,
    userAnswer: Int?,
    onBookmarkToggle: () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {"""

content = content.replace(old_fun, new_fun)

old_surface = """    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        color = DarkSurface,
        border = BorderStroke(1.dp, DarkBorderSubtle),
        modifier = modifier.fillMaxWidth()
    ) {"""

new_surface = """    val isCorrect = userAnswer == question.correctOptionIndex
    val isIncorrect = userAnswer != null && userAnswer != question.correctOptionIndex
    
    val borderColor = when {
        isCorrect -> Color(0xFF10B981)
        isIncorrect -> Color(0xFFEF4444)
        else -> DarkBorderSubtle
    }
    
    val circleColor = when {
        isCorrect -> Color(0xFF10B981)
        isIncorrect -> Color(0xFFEF4444)
        else -> Color(0xFFE2E8F0)
    }
    
    val circleTextColor = when {
        isCorrect, isIncorrect -> Color.White
        else -> Color(0xFF0F172A)
    }

    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        color = DarkSurface,
        border = BorderStroke(1.dp, borderColor),
        modifier = modifier.fillMaxWidth()
    ) {"""

content = content.replace(old_surface, new_surface)

old_circle = """                    // Question index circle
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFE2E8F0)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = question.questionNumber.toString(),
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF0F172A)
                        )
                    }"""

new_circle = """                    // Question index circle
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .clip(CircleShape)
                            .background(circleColor),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = question.questionNumber.toString(),
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = circleTextColor
                        )
                    }"""

content = content.replace(old_circle, new_circle)

with io.open(r"app\src\main\java\com\example\abhyaas\ui\screens\result\SolutionsTab.kt", "w", encoding="utf-8") as f:
    f.write(content)

print("SolutionsTab UI patched.")
