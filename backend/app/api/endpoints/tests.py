from fastapi import APIRouter

router = APIRouter()

@router.get("/{testId}")
def get_test(testId: str):
    return {"id": testId, "title": "Mock Test"}
