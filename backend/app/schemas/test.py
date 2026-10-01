from pydantic import BaseModel, Field
from typing import Optional

class TestDto(BaseModel):
    id: str
    series_id: str = Field("", alias="series_id")
    title: str
    sub_category: str = Field("", alias="sub_category")
    duration_minutes: int = Field(90, alias="duration_minutes")
    total_questions: int = Field(100, alias="total_questions")
    total_marks: float = Field(100.0, alias="total_marks")
    is_free: bool = Field(True, alias="is_free")

    class Config:
        from_attributes = True
        populate_by_name = True
