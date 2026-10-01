from fastapi import APIRouter, Depends
from sqlalchemy.orm import Session
from app.db.session import get_db
from app.models.study_material import StudyMaterial
from typing import List
from pydantic import BaseModel

class StudyMaterialDto(BaseModel):
    id: str
    title: str
    description: str
    subject: str
    pdf_url: str
    pdf_size: str
    page_count: int
    series_id: str
    is_free: bool
    class Config:
        from_attributes = True

router = APIRouter()

@router.get("/{series_id}/study-materials", response_model=List[StudyMaterialDto])
def get_study_materials(series_id: str, db: Session = Depends(get_db)):
    return db.query(StudyMaterial).filter(StudyMaterial.series_id == series_id).all()
