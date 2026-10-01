package com.example.abhyaas.data.mock

import com.example.abhyaas.data.model.LeaderboardEntry
import com.example.abhyaas.data.model.PreparationDataPoint
import com.example.abhyaas.data.model.UserProfile

object MockUserRepository {

    private var currentUser = UserProfile(
        name = "Aspirant",
        email = "aspirant@abhyaas.edu",
        mobileNumber = "+91 98765 43210",
        dateOfBirth = "15/08/2000",
        category = "General",
        pinCode = "462001",
        education = "Graduation",
        educationQualification = "Graduation",
        averageScorePercent = 55,
        totalTestsAttempted = 7,
        totalStudyTimeHours = 2
    )

    fun getUserProfile(): UserProfile = currentUser

    fun updateUserProfile(profile: UserProfile): UserProfile {
        currentUser = profile
        return currentUser
    }

    fun getPreparationDataPoints(metric: String = "Questions"): List<PreparationDataPoint> {
        return listOf(
            PreparationDataPoint(dateLabel = "May 2", questionsCount = 24, accuracyPercent = 65, timeSpentMinutes = 30),
            PreparationDataPoint(dateLabel = "May 4", questionsCount = 46, accuracyPercent = 70, timeSpentMinutes = 45),
            PreparationDataPoint(dateLabel = "May 7", questionsCount = 60, accuracyPercent = 82, timeSpentMinutes = 60),
            PreparationDataPoint(dateLabel = "May 9", questionsCount = 9, accuracyPercent = 50, timeSpentMinutes = 15),
            PreparationDataPoint(dateLabel = "May 10", questionsCount = 16, accuracyPercent = 60, timeSpentMinutes = 25),
            PreparationDataPoint(dateLabel = "Sep 27", questionsCount = 0, accuracyPercent = 0, timeSpentMinutes = 0)
        )
    }

    fun getLeaderboard(testId: String = "default"): List<LeaderboardEntry> {
        return listOf(
            LeaderboardEntry(rank = 1, userName = "Raja", score = 200.0f, maxScore = 200.0f, accuracy = 100.0f, timeTaken = "42m 10s"),
            LeaderboardEntry(rank = 2, userName = "Hemant", score = 195.0f, maxScore = 200.0f, accuracy = 97.5f, timeTaken = "45m 30s"),
            LeaderboardEntry(rank = 3, userName = "Vivek", score = 193.5f, maxScore = 200.0f, accuracy = 96.8f, timeTaken = "49m 12s"),
            LeaderboardEntry(rank = 4, userName = "Tanya", score = 191.0f, maxScore = 200.0f, accuracy = 95.5f, timeTaken = "52m 14s"),
            LeaderboardEntry(rank = 5, userName = "Gauravvv", score = 190.5f, maxScore = 200.0f, accuracy = 95.2f, timeTaken = "53m 40s"),
            LeaderboardEntry(rank = 6, userName = "Prashant Kumar Prajapat", score = 190.0f, maxScore = 200.0f, accuracy = 95.0f, timeTaken = "54m 02s"),
            LeaderboardEntry(rank = 7, userName = "Jithin Sarang J S", score = 186.0f, maxScore = 200.0f, accuracy = 93.0f, timeTaken = "55m 20s"),
            LeaderboardEntry(rank = 22789, userName = "Aspirant (You)", score = 0.0f, maxScore = 200.0f, accuracy = 0.0f, timeTaken = "00m 00s", isCurrentUser = true)
        )
    }
}
