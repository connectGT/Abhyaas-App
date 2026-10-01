package com.example.abhyaas.data.model

data class LeaderboardEntry(
    val rank: Int,
    val userName: String,
    val avatarUrl: String? = null,
    val score: Float,
    val maxScore: Float = 200.0f,
    val accuracy: Float = 95.0f,
    val timeTaken: String = "48m 20s",
    val isCurrentUser: Boolean = false
)
