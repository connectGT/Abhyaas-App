import io
import re

with io.open(r"app\src\main\java\com\example\abhyaas\data\mock\MockExamRepository.kt", "r", encoding="utf-8") as f:
    content = f.read()

old_list = """        return listOf(
            Test(
                id = "${seriesId}_${prefix.replace(" ", "_").lowercase()}_01",
                seriesId = seriesId,
                title = "$prefix - 01",
                subCategory = subCategory,
                durationMinutes = if (isGK || isRajasva) 30 else 90,
                totalQuestions = if (isGK || isRajasva) 50 else 100,
                totalMarks = if (isGK || isRajasva) 100.0f else 200.0f,
                isFree = true,
                sections = sections,
                previousAttempt = null
            ),
            Test(
                id = "${seriesId}_${prefix.replace(" ", "_").lowercase()}_02",
                seriesId = seriesId,
                title = "$prefix - 02",
                subCategory = subCategory,
                durationMinutes = if (isGK || isRajasva) 30 else 90,
                totalQuestions = if (isGK || isRajasva) 50 else 100,
                totalMarks = if (isGK || isRajasva) 100.0f else 200.0f,
                isFree = true,
                sections = sections,
                previousAttempt = null
            ),
            Test(
                id = "${seriesId}_${prefix.replace(" ", "_").lowercase()}_03",
                seriesId = seriesId,
                title = "$prefix - 03",
                subCategory = subCategory,
                durationMinutes = if (isGK || isRajasva) 30 else 90,
                totalQuestions = if (isGK || isRajasva) 50 else 100,
                totalMarks = if (isGK || isRajasva) 100.0f else 200.0f,
                isFree = false,
                sections = sections,
                previousAttempt = null
            )
        )"""

new_list = """        return listOf(
            Test(
                id = "${seriesId}_${prefix.replace(" ", "_").lowercase()}_01",
                seriesId = seriesId,
                title = "$prefix - 01",
                subCategory = subCategory,
                durationMinutes = if (isGK || isRajasva) 30 else 120, // 200 questions typically takes 120-150 mins
                totalQuestions = sections.sumOf { it.questions.size }, // Dynamically read the 200 questions
                totalMarks = (sections.sumOf { it.questions.size } * 2).toFloat(), // 2 marks per Q
                isFree = true,
                sections = sections,
                previousAttempt = null
            )
        )"""

content = content.replace(old_list, new_list)

with io.open(r"app\src\main\java\com\example\abhyaas\data\mock\MockExamRepository.kt", "w", encoding="utf-8") as f:
    f.write(content)

print("Repository tests patched.")
