from sqlalchemy import Column, Integer, String, Float, DateTime
from sqlalchemy.sql import func
from app.db.session import Base

class User(Base):
    __tablename__ = "users"
    
    id = Column(String, primary_key=True, index=True)
    name = Column(String, nullable=False, default="Aspirant")
    mobile = Column(String, unique=True, index=True, nullable=False)
    email = Column(String, unique=True, index=True, nullable=True)
    date_of_birth = Column(String, nullable=True)
    category = Column(String, nullable=True, default="General")
    pin_code = Column(String, nullable=True)
    education = Column(String, nullable=True)
    avatar_url = Column(String, nullable=True)
    target_exam = Column(String, nullable=True)
    
    # Stats
    rank = Column(Integer, default=0)
    total_tests_attempted = Column(Integer, default=0)
    accuracy = Column(Float, default=0.0)
    
    created_at = Column(DateTime(timezone=True), server_default=func.now())
    updated_at = Column(DateTime(timezone=True), onupdate=func.now())
