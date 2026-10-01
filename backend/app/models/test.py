from sqlalchemy import Column, Integer, String, Float, Boolean, ForeignKey, JSON
from sqlalchemy.orm import relationship
from app.db.session import Base

class TestSeries(Base):
    __tablename__ = "test_series"
    
    id = Column(String, primary_key=True, index=True)
    title = Column(String, nullable=False)
    subtitle = Column(String, nullable=True)
    category_id = Column(String, nullable=True)
    total_tests = Column(Integer, default=0)
    full_tests_count = Column(Integer, default=0)
    pyq_count = Column(Integer, default=0)
    attempted_count = Column(Integer, default=0)
    vacancies = Column(String, nullable=True)
    exam_dates = Column(String, nullable=True)
    
    # Relationships
    folders = relationship("TestSeriesFolder", back_populates="series", cascade="all, delete-orphan")
    tests = relationship("Test", back_populates="series", cascade="all, delete-orphan")

class TestSeriesFolder(Base):
    __tablename__ = "test_series_folders"
    
    id = Column(String, primary_key=True, index=True)
    series_id = Column(String, ForeignKey("test_series.id"))
    title = Column(String, nullable=False)
    subtitle = Column(String, nullable=True) # testCountText
    free_tests_badge = Column(String, nullable=True)
    is_live = Column(Boolean, default=False)
    is_pyq = Column(Boolean, default=False)
    folder_type = Column(String, default="MOCK") # MOCK, PYP, NOTES
    
    series = relationship("TestSeries", back_populates="folders")

class Test(Base):
    __tablename__ = "tests"
    
    id = Column(String, primary_key=True, index=True)
    series_id = Column(String, ForeignKey("test_series.id"))
    folder_id = Column(String, ForeignKey("test_series_folders.id"), nullable=True)
    title = Column(String, nullable=False)
    sub_category = Column(String, nullable=True)
    duration_minutes = Column(Integer, default=90)
    total_questions = Column(Integer, default=100)
    total_marks = Column(Float, default=200.0)
    is_free = Column(Boolean, default=False)
    
    # Store section names and IDs as JSON for simplicity, or in a separate table.
    # For now, separate table is cleaner.
    sections = relationship("TestSection", back_populates="test", cascade="all, delete-orphan")
    series = relationship("TestSeries", back_populates="tests")

class TestSection(Base):
    __tablename__ = "test_sections"
    
    id = Column(String, primary_key=True, index=True)
    test_id = Column(String, ForeignKey("tests.id"))
    name = Column(String, nullable=False)
    
    test = relationship("Test", back_populates="sections")
