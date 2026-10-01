from pydantic_settings import BaseSettings
import os

class Settings(BaseSettings):
    PROJECT_NAME: str = 'Abhyaas API'
    API_V1_STR: str = '/api/v1'
    SECRET_KEY: str = 'change-in-production'
    ACCESS_TOKEN_EXPIRE_MINUTES: int = 60 * 24 * 7

    @property
    def SQLALCHEMY_DATABASE_URI(self) -> str:
        url = os.environ.get('DATABASE_URL', '')
        if url.startswith('postgres://'):
            url = url.replace('postgres://', 'postgresql://', 1)
        return url or 'sqlite:///./abhyaas_local.db'

    class Config:
        case_sensitive = True
        env_file = ".env"

settings = Settings()
