import io

with io.open(r"backend\app\api\endpoints\tests.py", "r", encoding="utf-8") as f:
    content = f.read()

new_imports = """from app.schemas.result import SubmitTestRequest, TestResultDto, LeaderboardEntryDto
from app.models.result import TestResult, SectionResult
import uuid
import datetime"""

content = content.replace("from app.schemas.question import QuestionDto", "from app.schemas.question import QuestionDto\n" + new_imports)

endpoints = """
@router.post("/{test_id}/submit", response_model=TestResultDto)
def submit_test(test_id: str, body: SubmitTestRequest, db: Session = Depends(get_db)):
    test = db.query(Test).filter(Test.id == test_id).first()
    if not test:
        raise HTTPException(status_code=404, detail="Test not found")
        
    questions = db.query(Question).filter(Question.test_id == test_id).all()
    
    correct = 0
    incorrect = 0
    score = 0.0
    
    for q in questions:
        # Pydantic dict keys are ints, but JSON keys might be strings, handle both
        user_ans = body.answers.get(q.id) or body.answers.get(str(q.id))
        if user_ans is not None:
            if user_ans == q.correct_option_index:
                correct += 1
                score += 2.0
            else:
                incorrect += 1
                score -= 0.5
                
    total_q = len(questions) or 100
    unattempted = total_q - correct - incorrect
    accuracy = (correct / (correct + incorrect) * 100) if (correct + incorrect) > 0 else 0.0
    
    max_score = total_q * 2.0
    raw_percentage = (score / max_score * 100) if max_score > 0 else 0.0
    
    total_candidates = 24964
    if raw_percentage >= 99:
        computed_rank = 1
    else:
        offset = (100.0 - raw_percentage) / 100.0
        computed_rank = int(offset * total_candidates)
        computed_rank = max(1, min(computed_rank, total_candidates))
        
    percentile = ((total_candidates - computed_rank) / (total_candidates - 1) * 100) if total_candidates > 1 else 100.0
    
    # Save to DB
    attempt_id = f"att_{uuid.uuid4().hex[:8]}"
    
    result = TestResult(
        id=attempt_id,
        user_id="user123", # Hardcoded until auth is ready
        test_id=test_id,
        score=score,
        total_marks=max_score,
        rank=computed_rank,
        total_candidates=total_candidates,
        percentile=percentile,
        accuracy=accuracy,
        correct_count=correct,
        incorrect_count=incorrect,
        unattempted_count=unattempted,
        time_taken_seconds=body.time_taken_seconds,
        user_answers=body.answers
    )
    db.add(result)
    db.commit()
    db.refresh(result)
    
    return {
        "attemptId": result.id,
        "testId": test.id,
        "testTitle": test.title,
        "score": result.score,
        "totalMarks": result.total_marks,
        "rank": result.rank,
        "totalCandidates": result.total_candidates,
        "percentile": result.percentile,
        "accuracy": result.accuracy,
        "correctCount": result.correct_count,
        "incorrectCount": result.incorrect_count,
        "unattemptedCount": result.unattempted_count,
        "cutoffMarks": f"{int(max_score * 0.66)}-{int(max_score * 0.68)}",
        "averageScore": max_score * 0.45,
        "attemptDate": "Today",
        "section_breakdowns": []
    }

@router.get("/{test_id}/result", response_model=TestResultDto)
def get_test_result(test_id: str, db: Session = Depends(get_db)):
    result = db.query(TestResult).filter(TestResult.test_id == test_id).order_by(TestResult.attempt_date.desc()).first()
    test = db.query(Test).filter(Test.id == test_id).first()
    
    if not result:
        # Return a zeroed out fallback if no result exists yet
        max_score = test.total_marks if test else 400.0
        return {
            "attemptId": "none", "testId": test_id, "testTitle": test.title if test else "Mock Test",
            "score": 0.0, "totalMarks": max_score, "rank": 24964, "totalCandidates": 24964,
            "percentile": 0.0, "accuracy": 0.0, "correctCount": 0, "incorrectCount": 0,
            "unattemptedCount": test.total_questions if test else 200,
            "cutoffMarks": f"{int(max_score * 0.66)}-{int(max_score * 0.68)}", "averageScore": max_score * 0.45,
            "attemptDate": "Never", "section_breakdowns": []
        }
        
    return {
        "attemptId": result.id,
        "testId": result.test_id,
        "testTitle": test.title if test else "Mock Test",
        "score": result.score,
        "totalMarks": result.total_marks,
        "rank": result.rank,
        "totalCandidates": result.total_candidates,
        "percentile": result.percentile,
        "accuracy": result.accuracy,
        "correctCount": result.correct_count,
        "incorrectCount": result.incorrect_count,
        "unattemptedCount": result.unattempted_count,
        "cutoffMarks": f"{int(result.total_marks * 0.66)}-{int(result.total_marks * 0.68)}",
        "averageScore": result.total_marks * 0.45,
        "attemptDate": "Today",
        "section_breakdowns": []
    }

@router.get("/{test_id}/leaderboard", response_model=List[LeaderboardEntryDto])
def get_leaderboard(test_id: str, db: Session = Depends(get_db)):
    results = db.query(TestResult).filter(TestResult.test_id == test_id).order_by(TestResult.score.desc()).limit(10).all()
    
    leaderboard = []
    for i, r in enumerate(results):
        leaderboard.append({
            "rank": r.rank, # or i + 1
            "user_id": r.user_id,
            "name": "Aspirant (You)" if r.user_id == "user123" else f"User {r.user_id}",
            "score": r.score,
            "accuracy": r.accuracy,
            "avatar_url": None
        })
        
    return leaderboard
"""

content += endpoints

with io.open(r"backend\app\api\endpoints\tests.py", "w", encoding="utf-8") as f:
    f.write(content)

print("Tests endpoint patched with submission logic.")
