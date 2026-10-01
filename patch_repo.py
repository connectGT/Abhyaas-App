import io
import re

with io.open(r"app\src\main\java\com\example\abhyaas\data\mock\MockExamRepository.kt", "r", encoding="utf-8") as f:
    content = f.read()

# We need to replace the entire `fun getTestsForSubCategory` block.
# Let's find it.
start_idx = content.find("fun getTestsForSubCategory")
if start_idx == -1:
    print("Could not find getTestsForSubCategory")
    exit(1)

# Find the end of the function. We'll search for the next function: `fun getTestById`
end_idx = content.find("fun getTestById", start_idx)

new_func = """fun getTestsForSubCategory(seriesId: String, subCategory: String): List<Test> {
        val isTehsildar = seriesId == "nayab_tehsildar_2026"
        val sections = if (isTehsildar)
            MockQuestionRepository.getTehsildarSections()
        else
            MockQuestionRepository.getMpsebSections()

        val isPYQ = subCategory.contains("PYQ", ignoreCase = true)
        val isGK = subCategory.contains("GK", ignoreCase = true) || subCategory.contains("Gyan", ignoreCase = true)
        val isRajasva = subCategory.contains("Rajasva", ignoreCase = true)

        val prefix = when {
            isPYQ -> "Previous Year Paper"
            isGK -> "GK Sectional Test"
            isRajasva -> "Rajasva Shabdavali Test"
            else -> "Full Mock Test"
        }

        return listOf(
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
        )
    }

    """

content = content[:start_idx] + new_func + content[end_idx:]

with io.open(r"app\src\main\java\com\example\abhyaas\data\mock\MockExamRepository.kt", "w", encoding="utf-8") as f:
    f.write(content)

print("Repository patched successfully.")
