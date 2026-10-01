import io
import re

with io.open(r"app\src\main\java\com\example\abhyaas\data\mock\MockExamRepository.kt", "r", encoding="utf-8") as f:
    content = f.read()

old_fallback = """    fun getPreviousAttemptResult(testId: String): TestResult {
        val isMpseb = testId.contains("mpseb")
        return TestResult(
            attemptId = "att_${testId}_01",
            testId = testId,
            testTitle = if (isMpseb)
                "MPSEB: Full Mock Test - 01"
            else
                "Nayab Tehsildar: Full Mock Test - 01",
            score = 62.0f,
            totalMarks = 100.0f,
            rank = 45,
            totalCandidates = 230,
            percentile = 80.4f,
            accuracy = 75.6f,
            correctCount = 62,
            incorrectCount = 13,
            unattemptedCount = 25,
            cutoffMarks = "55-60",
            averageScore = 51.3f,
            bestScore = 91.0f,
            attemptDate = "Sep 28, 2026",
            sectionBreakdowns = if (isMpseb) listOf(
                SectionResult("mpseb_sec_a", "General Knowledge", 18.0f, 18, 4, 3, 0, 81.8f, 720L),
                SectionResult("mpseb_sec_b", "Reasoning", 22.0f, 22, 5, 8, 0, 81.5f, 1080L),
                SectionResult("mpseb_sec_c", "Technical Knowledge", 22.0f, 22, 4, 14, 0, 84.6f, 960L)
            ) else listOf(
                SectionResult("teh_sec_a", "Samanya Gyan (GK)", 28.0f, 28, 6, 16, 0, 82.4f, 840L),
                SectionResult("teh_sec_b", "Reasoning (Tarkshakti)", 34.0f, 34, 7, 9, 0, 82.9f, 1200L)
            )
        )
    }"""

new_fallback = """    fun getPreviousAttemptResult(testId: String): TestResult {
        val test = getTestById(testId)
        val testTitle = test?.title ?: "Full Mock Test - 01"
        val totalQ = test?.totalQuestions ?: 100
        val maxScore = test?.totalMarks ?: (totalQ * 2).toFloat()
        
        // Let's create a dynamic fallback that makes mathematical sense.
        // Assume user got exactly 65% correct, 15% incorrect, 20% unattempted.
        val correctCount = (totalQ * 0.65).toInt()
        val incorrectCount = (totalQ * 0.15).toInt()
        val unattemptedCount = totalQ - correctCount - incorrectCount
        
        val score = (correctCount * 2f) - (incorrectCount * 0.5f)
        val rawPercentage = if (maxScore > 0) (score / maxScore) * 100f else 0f
        
        val totalCandidates = 24964
        val computedRank = if (rawPercentage >= 99f) 1 else {
            val offset = (100f - rawPercentage) / 100f
            (offset * totalCandidates).toInt().coerceIn(1, totalCandidates)
        }
        val computedPercentile = if (totalCandidates > 1) {
            ((totalCandidates - computedRank).toFloat() / (totalCandidates - 1)) * 100f
        } else 100f
        
        val accuracy = if (correctCount + incorrectCount > 0) (correctCount.toFloat() / (correctCount + incorrectCount)) * 100f else 0f
        
        val avgScore = maxScore * 0.45f
        val bstScore = maxScore * 0.95f
        
        return TestResult(
            attemptId = "att_${testId}_01",
            testId = testId,
            testTitle = testTitle,
            score = score,
            totalMarks = maxScore,
            rank = computedRank,
            totalCandidates = totalCandidates,
            percentile = computedPercentile,
            accuracy = accuracy,
            correctCount = correctCount,
            incorrectCount = incorrectCount,
            unattemptedCount = unattemptedCount,
            cutoffMarks = "${(maxScore * 0.66).toInt()}-${(maxScore * 0.68).toInt()}",
            averageScore = avgScore,
            bestScore = bstScore,
            attemptDate = "Today",
            sectionBreakdowns = emptyList(), // we can omit this or generate dynamically if needed
            userAnswers = emptyMap()
        )
    }"""

content = content.replace(old_fallback, new_fallback)

with io.open(r"app\src\main\java\com\example\abhyaas\data\mock\MockExamRepository.kt", "w", encoding="utf-8") as f:
    f.write(content)

print("MockExamRepository fallback patched.")
