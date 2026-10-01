from fastapi import APIRouter

router = APIRouter()

@router.get("/profile")
def get_profile():
    return {"id": "user123", "name": "Aspirant"}
