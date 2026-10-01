from pydantic import BaseModel, Field
from typing import List, Optional

class OptionDto(BaseModel):
    id: int
    text: str
    text_hindi: Optional[str] = Field(None, alias="text_hindi")

    class Config:
        from_attributes = True

class QuestionDto(BaseModel):
    id: int
    section_id: str = Field(..., alias="section_id")
    question_number: int = Field(..., alias="question_number")
    statement: str
    statement_hindi: Optional[str] = Field(None, alias="statement_hindi")
    options: List[OptionDto]
    correct_option_index: int = Field(..., alias="correct_option_index")
    explanation: str
    topic: str
    subject: str

    class Config:
        from_attributes = True
        populate_by_name = True
