package com.example.abhyaas.data.model

data class UserProfile(
    val id: String = "",
    val name: String = "Aspirant",
    // Legacy field kept for screens that use fullName
    val fullName: String = name,
    val mobileNumber: String = "+91 98765 43210",
    val email: String = "aspirant@abhyaas.edu",
    val dateOfBirth: String? = "15/08/2000",
    val category: String? = "General",
    val pinCode: String? = "462001",
    // Legacy field for screens that use education
    val education: String = "Graduation",
    val educationQualification: String? = education,
    val avatarUrl: String? = null,
    val avatarRes: String = "avatar_default",
    val targetExam: String? = null,
    val rank: Int = 0,
    val totalTestsAttempted: Int = 7,
    val accuracy: Float = 0f,
    val averageScorePercent: Int = 55,
    val totalStudyTimeHours: Int = 2
)

data class PreparationDataPoint(
    val dateLabel: String,
    val questionsCount: Int,
    val accuracyPercent: Int = 75,
    val timeSpentMinutes: Int = 45
)
