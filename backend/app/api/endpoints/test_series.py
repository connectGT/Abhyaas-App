from fastapi import APIRouter, Depends
from sqlalchemy.orm import Session
from typing import List, Optional

from app.db.session import get_db
from app.models.test import Test
from app.schemas.test import TestDto

router = APIRouter()

@router.get("/{series_id}/tests", response_model=List[TestDto])
def get_tests_for_series(series_id: str, subCategory: Optional[str] = None, db: Session = Depends(get_db)):
    query = db.query(Test).filter(Test.series_id == series_id)
    
    if subCategory:
        query = query.filter(Test.sub_category == subCategory)
        
    tests = query.all()
    
    return [
        {
            "id": t.id,
            "series_id": t.series_id or "",
            "title": t.title,
            "sub_category": t.sub_category or "",
            "duration_minutes": t.duration_minutes,
            "total_questions": t.total_questions,
            "total_marks": t.total_marks,
            "is_free": t.is_free
        }
        for t in tests
    ]
