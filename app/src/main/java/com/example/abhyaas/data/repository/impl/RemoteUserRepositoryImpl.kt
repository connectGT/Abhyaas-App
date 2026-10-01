package com.example.abhyaas.data.repository.impl

import com.example.abhyaas.data.mock.MockUserRepository
import com.example.abhyaas.data.model.PreparationDataPoint
import com.example.abhyaas.data.model.UserProfile
import com.example.abhyaas.data.repository.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await

class RemoteUserRepositoryImpl : UserRepository {
    private val db by lazy { FirebaseFirestore.getInstance() }
    private val auth by lazy { FirebaseAuth.getInstance() }
    private val usersCollection get() = db.collection("users")

    override suspend fun getUserProfile(): Result<UserProfile> = runCatching {
        val uid = auth.currentUser?.uid
        if (uid != null) {
            val doc = usersCollection.document(uid).get().await()
            if (doc.exists()) {
                val data = doc.data
                return@runCatching UserProfile(
                    name = data?.get("name") as? String ?: "",
                    dateOfBirth = data?.get("dateOfBirth") as? String ?: "",
                    category = data?.get("category") as? String ?: "",
                    pinCode = data?.get("pinCode") as? String ?: "",
                    educationQualification = data?.get("education") as? String ?: "",
                    targetExam = data?.get("targetExam") as? String ?: ""
                )
            }
        }
        MockUserRepository.getUserProfile()
    }.recoverCatching { MockUserRepository.getUserProfile() }

    override suspend fun updateUserProfile(profile: UserProfile): Result<UserProfile> = runCatching {
        val uid = auth.currentUser?.uid
        if (uid != null) {
            val data = hashMapOf(
                "name" to profile.name,
                "dateOfBirth" to (profile.dateOfBirth ?: ""),
                "category" to (profile.category ?: ""),
                "pinCode" to (profile.pinCode ?: ""),
                "education" to (profile.educationQualification ?: ""),
                "targetExam" to (profile.targetExam ?: "")
            )
            usersCollection.document(uid).set(data, SetOptions.merge()).await()
            return@runCatching profile
        }
        MockUserRepository.updateUserProfile(profile)
    }.recoverCatching { MockUserRepository.updateUserProfile(profile) }

    override suspend fun getPreparationDataPoints(metric: String): Result<List<PreparationDataPoint>> = 
        Result.success(MockUserRepository.getPreparationDataPoints(metric))

    override suspend fun sendOtp(mobile: String): Result<Unit> = Result.success(Unit)
    override suspend fun verifyOtp(mobile: String, otp: String): Result<Boolean> = Result.success(true)

    override fun isLoggedIn(): Boolean = runCatching { auth.currentUser != null }.getOrDefault(false)
    override fun logout() {
        runCatching { auth.signOut() }
    }
}
