package com.example.abhyaas.data.repository.impl

import com.example.abhyaas.data.mock.MockUserRepository
import com.example.abhyaas.data.model.UserProfile
import com.example.abhyaas.data.repository.UserRepository
import kotlinx.coroutines.delay

class MockUserRepositoryImpl : UserRepository {
    private var loggedIn = false

    override suspend fun getUserProfile(): Result<UserProfile> {
        delay(200)
        return Result.success(MockUserRepository.getUserProfile())
    }

    override suspend fun updateUserProfile(profile: UserProfile): Result<UserProfile> {
        delay(300)
        MockUserRepository.updateUserProfile(profile)
        return Result.success(profile)
    }

    override suspend fun sendOtp(mobile: String): Result<Unit> {
        delay(800) // simulate SMS API
        return Result.success(Unit)
    }

    override suspend fun verifyOtp(mobile: String, otp: String): Result<Boolean> {
        delay(600)
        // Accept any 6-digit OTP in mock mode
        val valid = otp.length == 6 && otp.all { it.isDigit() }
        if (valid) loggedIn = true
        return Result.success(valid)
    }

    override fun isLoggedIn(): Boolean = loggedIn
    override fun logout() { loggedIn = false }
}
