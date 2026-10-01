import io
import re

with io.open(r"app\src\main\java\com\example\abhyaas\data\repository\impl\MockQuestionRepositoryImpl.kt", "r", encoding="utf-8") as f:
    content = f.read()

# Fix statementText
def repl_statement(m):
    full_text = m.group(1)
    lines = full_text.strip().split('\n')
    if len(lines) > 1:
        eng = lines[0]
        hin = '\n'.join(lines[1:])
        return 'statementText = """{}""",\n            statementTextHindi = """{}"""'.format(eng, hin)
    return m.group(0)

# Fix explanation
def repl_explanation(m):
    full_text = m.group(1)
    lines = full_text.strip().split('\n')
    if len(lines) > 1:
        eng = lines[0]
        hin = '\n'.join(lines[1:])
        return 'explanation = """{}""",\n            explanationHindi = """{}"""'.format(eng, hin)
    return m.group(0)

content = re.sub(r'statementText\s*=\s*"""(.*?)"""', repl_statement, content, flags=re.DOTALL)
content = re.sub(r'explanation\s*=\s*"""(.*?)"""', repl_explanation, content, flags=re.DOTALL)

with io.open(r"app\src\main\java\com\example\abhyaas\data\repository\impl\MockQuestionRepositoryImpl.kt", "w", encoding="utf-8") as f:
    f.write(content)

print("Bilingual separation done.")
