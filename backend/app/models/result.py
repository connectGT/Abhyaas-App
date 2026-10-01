from sqlalchemy import Column, Integer, String, Float, ForeignKey, DateTime, JSON
from sqlalchemy.sql import func
from sqlalchemy.orm import relationship
from app.db.session import Base

class TestResult(Base):
    __tablename__ = "test_results"
    
    id = Column(String, primary_key=True, index=True) # attemptId
    user_id = Column(String, ForeignKey("users.id"))
    test_id = Column(String, ForeignKey("tests.id"))
    
    score = Column(Float, default=0.0)
    total_marks = Column(Float, default=0.0)
    rank = Column(Integer, default=0)
    total_candidates = Column(Integer, default=0)
    percentile = Column(Float, default=0.0)
    accuracy = Column(Float, default=0.0)
    
    correct_count = Column(Integer, default=0)
    incorrect_count = Column(Integer, default=0)
    unattempted_count = Column(Integer, default=0)
    
    time_taken_seconds = Column(Integer, default=0)
    attempt_date = Column(DateTime(timezone=True), server_default=func.now())
    
    # Can store Map<Int, Int> as JSON for answers for simplicity
    user_answers = Column(JSON, default=dict)
    
    section_breakdowns = relationship("SectionResult", back_populates="test_result", cascade="all, delete-orphan")

class SectionResult(Base):
    __tablename__ = "section_results"
    
    id = Column(Integer, primary_key=True, index=True, autoincrement=True)
    test_result_id = Column(String, ForeignKey("test_results.id"))
    section_id = Column(String, nullable=False)
    section_name = Column(String, nullable=False)
    
    score = Column(Float, default=0.0)
    correct_count = Column(Integer, default=0)
    incorrect_count = Column(Integer, default=0)
    unattempted_count = Column(Integer, default=0)
    marked_count = Column(Integer, default=0)
    accuracy = Column(Float, default=0.0)
    time_taken_seconds = Column(Integer, default=0)
    total_questions = Column(Integer, default=0)
    
    test_result = relationship("TestResult", back_populates="section_breakdowns")
