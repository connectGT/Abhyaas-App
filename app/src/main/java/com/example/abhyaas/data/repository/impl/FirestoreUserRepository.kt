package com.example.abhyaas.data.repository.impl

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await

data class UserProfile(
    val uid: String = "",
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val createdAt: Long = System.currentTimeMillis(),
    val testAttempts: Int = 0,
    val isPremium: Boolean = false
)

class FirestoreUserRepository {
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private val usersCollection = db.collection("users")

    suspend fun createOrUpdateUserProfile(name: String = "") {
        val user = auth.currentUser ?: return
        val profile = UserProfile(
            uid = user.uid,
            name = name.ifEmpty { user.displayName ?: "" },
            email = user.email ?: "",
            phone = user.phoneNumber ?: "",
            createdAt = System.currentTimeMillis()
        )
        usersCollection.document(user.uid).set(profile, SetOptions.merge()).await()
    }

    suspend fun getUserProfile(): UserProfile? {
        val uid = auth.currentUser?.uid ?: return null
        return usersCollection.document(uid).get().await().toObject(UserProfile::class.java)
    }

    suspend fun saveTestResult(testId: String, score: Float, correctCount: Int, totalQuestions: Int) {
        val uid = auth.currentUser?.uid ?: return
        val result = hashMapOf(
            "testId" to testId,
            "score" to score,
            "correctCount" to correctCount,
            "totalQuestions" to totalQuestions,
            "attemptedAt" to System.currentTimeMillis()
        )
        usersCollection.document(uid)
            .collection("testResults")
            .document(testId)
            .set(result).await()
        // Increment attempt count
        usersCollection.document(uid)
            .update("testAttempts", com.google.firebase.firestore.FieldValue.increment(1)).await()
    }
}
