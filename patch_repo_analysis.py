import io
import re

with io.open(r"app\src\main\java\com\example\abhyaas\data\repository\impl\MockTestResultRepositoryImpl.kt", "r", encoding="utf-8") as f:
    content = f.read()

old_submit = """        val baseResult = MockExamRepository.getPreviousAttemptResult(testId)
        val newResult = baseResult.copy(
            score = score,
            correctCount = correctCount,
            incorrectCount = incorrectCount,
            unattemptedCount = unattemptedCount,
            accuracy = accuracy,
            totalMarks = (totalQ * 2).toFloat(),
            userAnswers = answers
        )"""

new_submit = """        val baseResult = MockExamRepository.getPreviousAttemptResult(testId)
        val maxScore = (totalQ * 2).toFloat()
        
        // Dynamically compute mock percentiles, rank, and metrics based on actual score!
        val rawPercentage = if (maxScore > 0) (score / maxScore) * 100f else 0f
        
        // Dynamic rank between 1 and 24964 based on percentage (if 100%, rank 1)
        val totalCandidates = 24964
        val computedRank = if (rawPercentage >= 99f) 1 else {
            val offset = (100f - rawPercentage) / 100f
            (offset * totalCandidates).toInt().coerceIn(1, totalCandidates)
        }
        
        val computedPercentile = if (totalCandidates > 1) {
            ((totalCandidates - computedRank).toFloat() / (totalCandidates - 1)) * 100f
        } else 100f
        
        val avgScore = maxScore * 0.45f
        val bstScore = maxScore * 0.95f
        
        val newResult = baseResult.copy(
            score = score,
            correctCount = correctCount,
            incorrectCount = incorrectCount,
            unattemptedCount = unattemptedCount,
            accuracy = accuracy,
            totalMarks = maxScore,
            userAnswers = answers,
            rank = computedRank,
            totalCandidates = totalCandidates,
            percentile = computedPercentile,
            averageScore = avgScore,
            bestScore = bstScore
        )"""

content = content.replace(old_submit, new_submit)

with io.open(r"app\src\main\java\com\example\abhyaas\data\repository\impl\MockTestResultRepositoryImpl.kt", "w", encoding="utf-8") as f:
    f.write(content)

print("Repository analysis patched.")
