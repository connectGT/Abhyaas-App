import io

with io.open(r"app\src\main\java\com\example\abhyaas\ui\screens\result\AnalysisTab.kt", "r", encoding="utf-8") as f:
    content = f.read()

old_cutoff = """    val cutoffText = when (selectedCategory) {
        "General" -> "Cut off: 132-135"
        "OBC" -> "Cut off: 128-132"
        "SC" -> "Cut off: 115-120"
        "ST" -> "Cut off: 108-112"
        "EWS" -> "Cut off: 125-130"
        else -> "Cut off: 132-135"
    }"""

new_cutoff = """    val max = testResult.totalMarks
    val cutoffText = when (selectedCategory) {
        "General" -> "Cut off: ${(max * 0.66).toInt()}-${(max * 0.68).toInt()}"
        "OBC" -> "Cut off: ${(max * 0.64).toInt()}-${(max * 0.66).toInt()}"
        "SC" -> "Cut off: ${(max * 0.57).toInt()}-${(max * 0.60).toInt()}"
        "ST" -> "Cut off: ${(max * 0.54).toInt()}-${(max * 0.56).toInt()}"
        "EWS" -> "Cut off: ${(max * 0.62).toInt()}-${(max * 0.65).toInt()}"
        else -> "Cut off: ${(max * 0.66).toInt()}-${(max * 0.68).toInt()}"
    }"""

content = content.replace(old_cutoff, new_cutoff)

# Let's format Percentile and Average Score nicely to 1 or 2 decimal places.
# Wait, the percentiles are already Float, so they might show like 80.44444%
# We should format them in Kotlin: "%.1f".format(testResult.percentile)

old_percentile = """                    Text(
                        text = "${testResult.percentile} %",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White
                    )"""

new_percentile = """                    Text(
                        text = "${String.format("%.1f", testResult.percentile)} %",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White
                    )"""

content = content.replace(old_percentile, new_percentile)

old_avg = """text = "Average Score: ${testResult.averageScore}   |   Best Score: ${testResult.bestScore.toInt()}","""
new_avg = """text = "Average Score: ${String.format("%.1f", testResult.averageScore)}   |   Best Score: ${testResult.bestScore.toInt()}","""
content = content.replace(old_avg, new_avg)


with io.open(r"app\src\main\java\com\example\abhyaas\ui\screens\result\AnalysisTab.kt", "w", encoding="utf-8") as f:
    f.write(content)

print("Analysis cutoff patched.")
