from fastapi import APIRouter

router = APIRouter()

@router.post("/send-otp")
def send_otp():
    return {"message": "OTP sent successfully"}

@router.post("/verify-otp")
def verify_otp():
    return {"token": "dummy-jwt-token"}
