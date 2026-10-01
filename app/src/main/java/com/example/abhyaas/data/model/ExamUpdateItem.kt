package com.example.abhyaas.data.model

enum class UpdateCategory(val displayName: String) {
    ALL("All"),
    NOTIFICATIONS("Notifications"),
    ADMIT_CARD("Admit Card"),
    RESULTS("Results"),
    SYLLABUS("Syllabus"),
    EXAM_DATES("Exam Dates"),
    OFFICIAL_PDFS("Official PDFs")
}

data class ExamUpdateItem(
    val id: String,
    val title: String,
    val description: String,
    val date: String,
    val category: UpdateCategory,
    val isPinned: Boolean = false,
    val pdfSize: String? = null,
    val actionText: String = "Download PDF"
)
