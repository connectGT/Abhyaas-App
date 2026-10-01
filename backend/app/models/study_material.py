from sqlalchemy import Column, String, Integer, Boolean
from app.db.session import Base

class StudyMaterial(Base):
    __tablename__ = "study_materials"
    id = Column(String, primary_key=True, index=True)
    title = Column(String, nullable=False)
    description = Column(String)
    subject = Column(String)
    pdf_url = Column(String, nullable=False)  # Cloudflare R2 URL
    pdf_size = Column(String)
    page_count = Column(Integer, default=0)
    series_id = Column(String, nullable=False, index=True)
    is_free = Column(Boolean, default=True)
