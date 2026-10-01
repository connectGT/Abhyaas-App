import io

with io.open(r"app\src\main\java\com\example\abhyaas\data\repository\impl\MockTestResultRepositoryImpl.kt", "r", encoding="utf-8") as f:
    content = f.read()

new_override = """
    override suspend fun getLeaderboard(testId: String): Result<List<LeaderboardEntry>> {
        delay(300)
        // Dynamic leaderboard based on "database" (in-memory lastResult)
        val result = lastResult
        if (result != null && result.testId == testId) {
            val userEntry = LeaderboardEntry(
                rank = 1,
                userName = "Aspirant (You)",
                score = result.score,
                maxScore = result.totalMarks,
                accuracy = result.accuracy,
                timeTaken = "Completed",
                isCurrentUser = true
            )
            return Result.success(listOf(userEntry))
        }
        return Result.success(emptyList())
    }
}"""

# Find the last closing brace and replace it
last_brace_idx = content.rfind("}")
if last_brace_idx != -1:
    content = content[:last_brace_idx] + new_override + content[last_brace_idx+1:]

with io.open(r"app\src\main\java\com\example\abhyaas\data\repository\impl\MockTestResultRepositoryImpl.kt", "w", encoding="utf-8") as f:
    f.write(content)

print("Leaderboard patched.")
