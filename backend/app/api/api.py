from fastapi import APIRouter
from app.api.endpoints import auth, user, exams, tests, test_series, study_materials

api_router = APIRouter()
api_router.include_router(auth.router, prefix="/auth", tags=["auth"])
api_router.include_router(user.router, prefix="/user", tags=["user"])
api_router.include_router(exams.router, prefix="/exams", tags=["exams"])
api_router.include_router(test_series.router, prefix="/test-series", tags=["test-series"])
api_router.include_router(study_materials.router, prefix="/test-series", tags=["study-materials"])
api_router.include_router(tests.router, prefix="/tests", tags=["tests"])
