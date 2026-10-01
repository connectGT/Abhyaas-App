package com.example.abhyaas.data.repository

import com.example.abhyaas.data.model.PreparationDataPoint
import com.example.abhyaas.data.model.UserProfile

interface UserRepository {
    suspend fun getUserProfile(): Result<UserProfile>
    suspend fun updateUserProfile(profile: UserProfile): Result<UserProfile>
    suspend fun getPreparationDataPoints(metric: String = "Questions"): Result<List<PreparationDataPoint>> =
        Result.success(com.example.abhyaas.data.mock.MockUserRepository.getPreparationDataPoints(metric))
    suspend fun sendOtp(mobile: String): Result<Unit>
    suspend fun verifyOtp(mobile: String, otp: String): Result<Boolean>
    fun isLoggedIn(): Boolean
    fun logout()
}
