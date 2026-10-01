import docx
import re
import io

doc = docx.Document(r"C:\Users\gurut\Downloads\ABHYAAS App\Test\full test 1.docx")

questions = []
current_q = {}
for p in doc.paragraphs:
    text = p.text.strip()
    if not text:
        continue
    
    if text.startswith("-------------------------"):
        if current_q:
            questions.append(current_q)
            current_q = {}
        continue
        
    if re.match(r"^Q\d+\.", text):
        current_q = {'options': [], 'statement': text}
    elif text.startswith("Options"):
        current_q['parsing_options'] = True
    elif text.startswith("A)") or text.startswith("B)") or text.startswith("C)") or text.startswith("D)"):
        if 'options' in current_q:
            current_q['options'].append(text)
    elif text.startswith("Answer"):
        current_q['answer'] = text
    elif text.startswith("Solution:") or text.startswith("Expert Advice:") or "solution" in current_q:
        if 'solution' not in current_q:
            current_q['solution'] = text
        else:
            current_q['solution'] += "\n" + text
    else:
        if 'statement' in current_q and 'parsing_options' not in current_q:
            current_q['statement'] += "\n" + text

if current_q and 'statement' in current_q:
    questions.append(current_q)

kt_code = """package com.example.abhyaas.data.repository.impl

import com.example.abhyaas.data.mock.MockExamRepository
import com.example.abhyaas.data.model.*
import com.example.abhyaas.data.repository.QuestionRepository
import kotlinx.coroutines.delay

class MockQuestionRepositoryImpl : QuestionRepository {
    private val allQuestions = mutableListOf<Question>()

    init {
"""

for i, q in enumerate(questions):
    q_id = i + 1
    
    # We will use Kotlin raw strings for safety
    statement = q.get('statement', '').replace('"""', '\"\"\"')
    
    opts = q.get('options', [])
    opt_strs = []
    for o_idx, o in enumerate(opts):
        parts = o.split('|')
        o_clean = parts[0].strip()[3:].strip().replace('"', '\\"') if len(parts) > 0 else "Opt"
        opt_strs.append(f'Option({o_idx+1}, "{o_clean}")')
        
    opts_kt = "listOf(" + ", ".join(opt_strs) + ")"
    
    ans_text = q.get('answer', '')
    correct_idx = 0
    if "B)" in ans_text: correct_idx = 1
    elif "C)" in ans_text: correct_idx = 2
    elif "D)" in ans_text: correct_idx = 3
    
    sol = q.get('solution', 'Solution available.').replace('"""', '\"\"\"')
    
    kt_code += f"""
        allQuestions.add(Question(
            id = {q_id},
            sectionId = "sec_a",
            questionNumber = {q_id},
            statementText = \"\"\"{statement}\"\"\",
            options = {opts_kt},
            correctOptionIndex = {correct_idx},
            explanation = \"\"\"{sol}\"\"\",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = {(i * 7) % 100},
            averageTimeSeconds = 15
        ))
"""

kt_code += """
    }

    override suspend fun getQuestionsForTest(testId: String): Result<List<Question>> {
        delay(300)
        return Result.success(allQuestions)
    }

    override suspend fun getBookmarkedQuestions(): Result<List<Int>> {
        return Result.success(listOf(2, 4))
    }

    override suspend fun bookmarkQuestion(questionId: Int, bookmarked: Boolean): Result<Unit> {
        delay(300)
        return Result.success(Unit)
    }
}
"""

with io.open(r"app\src\main\java\com\example\abhyaas\data\repository\impl\MockQuestionRepositoryImpl.kt", 'w', encoding='utf-8') as f:
    f.write(kt_code)

print(f"Generated {len(questions)} multiline questions.")
