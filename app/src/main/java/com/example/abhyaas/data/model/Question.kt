package com.example.abhyaas.data.model

data class Option(
    val id: Int,
    val text: String,
    val textHindi: String? = null
)

enum class QuestionStatus {
    NOT_VISITED,            // Not visited yet
    UNANSWERED,             // Visited but no option selected (Blue square)
    ANSWERED,               // Option selected and saved (Green square)
    MARKED_FOR_REVIEW,      // Marked without selection (Coral/Red tag with arrow)
    ANSWERED_AND_MARKED     // Option selected and marked for review (Amber tag with arrow)
}

data class Question(
    val id: Int,
    val sectionId: String,
    val questionNumber: Int,
    val directionText: String? = null,
    val directionTextHindi: String? = null,
    val statementText: String,
    val statementTextHindi: String? = null,
    val options: List<Option>,
    val correctOptionIndex: Int,
    val explanation: String,
    val explanationHindi: String? = null,
    val positiveMarks: Float = 2.0f,
    val negativeMarks: Float = 0.5f,
    val topic: String = "General",
    val subject: String = "Reasoning",
    val percentGotRight: Int = 64,
    val averageTimeSeconds: Int = 45,
    val isBookmarked: Boolean = false
)
