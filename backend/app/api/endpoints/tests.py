from fastapi import APIRouter, Depends, HTTPException
from sqlalchemy.orm import Session
from typing import List

from app.db.session import get_db
from app.models.test import Test
from app.models.question import Question
from app.schemas.test import TestDto
from app.schemas.question import QuestionDto

router = APIRouter()

@router.get("/{test_id}", response_model=TestDto)
def get_test(test_id: str, db: Session = Depends(get_db)):
    test = db.query(Test).filter(Test.id == test_id).first()
    if not test:
        raise HTTPException(status_code=404, detail="Test not found")
    
    # Map model to DTO format
    return {
        "id": test.id,
        "series_id": test.series_id or "",
        "title": test.title,
        "sub_category": test.sub_category or "",
        "duration_minutes": test.duration_minutes,
        "total_questions": test.total_questions,
        "total_marks": test.total_marks,
        "is_free": test.is_free
    }

@router.get("/{test_id}/questions", response_model=List[QuestionDto])
def get_questions(test_id: str, db: Session = Depends(get_db)):
    # Verify test exists
    test = db.query(Test).filter(Test.id == test_id).first()
    if not test:
        raise HTTPException(status_code=404, detail="Test not found")
        
    # Query questions with their options eager loaded (or rely on lazy load)
    questions = db.query(Question).filter(Question.test_id == test_id).order_by(Question.question_number).all()
    
    # Map to DTO
    question_dtos = []
    for q in questions:
        question_dtos.append({
            "id": q.id,
            "section_id": q.section_id,
            "question_number": q.question_number,
            "statement": q.statement,
            "statement_hindi": q.statement_hindi,
            "correct_option_index": q.correct_option_index,
            "explanation": q.explanation,
            "topic": q.topic,
            "subject": q.subject,
            "options": [
                {
                    "id": opt.id,
                    "text": opt.text,
                    "text_hindi": opt.text_hindi
                }
                for opt in q.options
            ]
        })
        
    return question_dtos
