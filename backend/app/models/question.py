from sqlalchemy import Column, Integer, String, Float, ForeignKey, Boolean
from sqlalchemy.orm import relationship
from app.db.session import Base

class Question(Base):
    __tablename__ = "questions"
    
    id = Column(Integer, primary_key=True, index=True, autoincrement=True)
    test_id = Column(String, ForeignKey("tests.id"))
    section_id = Column(String, ForeignKey("test_sections.id"))
    
    question_number = Column(Integer, nullable=False)
    
    statement = Column(String, nullable=False)
    statement_hindi = Column(String, nullable=True)
    
    correct_option_index = Column(Integer, nullable=False) # 1, 2, 3, or 4
    
    explanation = Column(String, nullable=True)
    explanation_hindi = Column(String, nullable=True)
    
    topic = Column(String, default="General")
    subject = Column(String, default="General")
    
    options = relationship("Option", back_populates="question", cascade="all, delete-orphan")

class Option(Base):
    __tablename__ = "options"
    
    id = Column(Integer, primary_key=True, index=True, autoincrement=True)
    question_id = Column(Integer, ForeignKey("questions.id"))
    
    option_index = Column(Integer, nullable=False) # 1, 2, 3, 4
    text = Column(String, nullable=False)
    text_hindi = Column(String, nullable=True)
    
    question = relationship("Question", back_populates="options")
