import io
import re

with io.open(r"app\src\main\java\com\example\abhyaas\ui\screens\result\AnalysisTab.kt", "r", encoding="utf-8") as f:
    content = f.read()

content = content.replace('String.format("%.1f", testResult.percentile)', '"%.1f".format(testResult.percentile)')
content = content.replace('String.format("%.1f", testResult.averageScore)', '"%.1f".format(testResult.averageScore)')

with io.open(r"app\src\main\java\com\example\abhyaas\ui\screens\result\AnalysisTab.kt", "w", encoding="utf-8") as f:
    f.write(content)

print("Format patched.")
