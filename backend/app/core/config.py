from pydantic_settings import BaseSettings
import os

class Settings(BaseSettings):
    PROJECT_NAME: str = "Abhyaas API"
    API_V1_STR: str = "/api/v1"
    
    # Use SQLite for local development so it runs out-of-the-box
    SQLALCHEMY_DATABASE_URI: str = "sqlite:///./abhyaas_local.db"
        
    SECRET_KEY: str = "a-very-secret-key-change-in-production"
    ACCESS_TOKEN_EXPIRE_MINUTES: int = 60 * 24 * 7 # 7 days

    class Config:
        case_sensitive = True
        env_file = ".env"

settings = Settings()
