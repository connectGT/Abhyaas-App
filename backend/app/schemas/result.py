from pydantic import BaseModel, Field
from typing import Dict, List, Optional

class SubmitTestRequest(BaseModel):
    test_id: str = Field(..., alias="testId")
    answers: Dict[int, int]
    time_taken_seconds: int = Field(..., alias="timeTakenSeconds")

    class Config:
        populate_by_name = True

class SectionResultDto(BaseModel):
    section_id: str = Field(..., alias="sectionId")
    section_name: str = Field(..., alias="sectionName")
    score: float
    correct_count: int = Field(..., alias="correctCount")
    incorrect_count: int = Field(..., alias="incorrectCount")
    unattempted_count: int = Field(..., alias="unattemptedCount")
    marked_count: int = Field(..., alias="markedCount")
    accuracy: float
    time_taken_seconds: int = Field(..., alias="timeTakenSeconds")
    total_questions: int = Field(..., alias="totalQuestions")

    class Config:
        from_attributes = True
        populate_by_name = True

class TestResultDto(BaseModel):
    attempt_id: str = Field(..., alias="attemptId")
    test_id: str = Field(..., alias="testId")
    test_title: str = Field(..., alias="testTitle")
    score: float
    total_marks: float = Field(..., alias="totalMarks")
    rank: int
    total_candidates: int = Field(..., alias="totalCandidates")
    percentile: float
    accuracy: float
    correct_count: int = Field(..., alias="correctCount")
    incorrect_count: int = Field(..., alias="incorrectCount")
    unattempted_count: int = Field(..., alias="unattemptedCount")
    cutoff_marks: str = Field(..., alias="cutoffMarks")
    average_score: float = Field(..., alias="averageScore")
    attempt_date: str = Field(..., alias="attemptDate")
    section_breakdowns: Optional[List[SectionResultDto]] = Field(None, alias="section_breakdowns")

    class Config:
        from_attributes = True
        populate_by_name = True

class LeaderboardEntryDto(BaseModel):
    rank: int
    user_id: str = Field(..., alias="user_id")
    name: str
    score: float
    accuracy: float
    avatar_url: Optional[str] = Field(None, alias="avatar_url")

    class Config:
        from_attributes = True
        populate_by_name = True
