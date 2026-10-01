package com.example.abhyaas.data.mock

import com.example.abhyaas.data.model.ExamUpdateItem
import com.example.abhyaas.data.model.UpdateCategory

object MockUpdatesRepository {

    private val updates = listOf(
        ExamUpdateItem(
            id = "upd_1",
            title = "MP Nayab Tehsildar Departmental Exam 2026 — Notification Released",
            description = "MPPSC ने नायब तहसीलदार विभागीय परीक्षा 2026 की आधिकारिक अधिसूचना जारी की। कुल 73 पद। आवेदन की अंतिम तारीख जल्द घोषित होगी।",
            date = "28 Sep 2026",
            category = UpdateCategory.NOTIFICATIONS,
            isPinned = true,
            pdfSize = "1.8 MB",
            actionText = "Download PDF"
        ),
        ExamUpdateItem(
            id = "upd_2",
            title = "Nayab Tehsildar Exam 2026 — परीक्षा तिथि घोषित",
            description = "नायब तहसीलदार विभागीय परीक्षा नवंबर 2026 में आयोजित होगी। पूर्ण कार्यक्रम PDF में उपलब्ध है।",
            date = "26 Sep 2026",
            category = UpdateCategory.EXAM_DATES,
            isPinned = false,
            pdfSize = "850 KB",
            actionText = "Download PDF"
        ),
        ExamUpdateItem(
            id = "upd_3",
            title = "Nayab Tehsildar 2026 — परीक्षा पैटर्न और सिलेबस",
            description = "Paper 1: सामान्य ज्ञान + तर्कशक्ति (100 अंक) | Paper 2: राजस्व शब्दावली (100 अंक)। विस्तृत सिलेबस डाउनलोड करें।",
            date = "25 Sep 2026",
            category = UpdateCategory.SYLLABUS,
            isPinned = false,
            pdfSize = "1.2 MB",
            actionText = "Download PDF"
        ),

        ExamUpdateItem(
            id = "upd_5",
            title = "Nayab Tehsildar 2026 — Admit Card (जल्द आएगा)",
            description = "प्रवेश पत्र परीक्षा से 7 दिन पहले जारी किए जाएंगे। अपना रोल नंबर तैयार रखें।",
            date = "22 Sep 2026",
            category = UpdateCategory.ADMIT_CARD,
            isPinned = false,
            pdfSize = null,
            actionText = "Notify Me"
        ),
        ExamUpdateItem(
            id = "upd_6",
            title = "MPSEB 2025 — Previous Year Paper Available",
            description = "MPSEB 2025 की पिछले वर्ष की परीक्षा के प्रश्नपत्र और उत्तर कुंजी अब उपलब्ध हैं। अभ्यास के लिए डाउनलोड करें।",
            date = "20 Sep 2026",
            category = UpdateCategory.RESULTS,
            isPinned = false,
            pdfSize = "3.4 MB",
            actionText = "Download PDF"
        ),
        ExamUpdateItem(
            id = "upd_7",
            title = "MP Nayab Tehsildar — Rulebook & Study Material",
            description = "MP Nayab Tehsildar Departmental Exam का सम्पूर्ण Rulebook और राजस्व शब्दावली स्टडी मटेरियल डाउनलोड करें।",
            date = "18 Sep 2026",
            category = UpdateCategory.SYLLABUS,
            isPinned = false,
            pdfSize = "4.2 MB",
            actionText = "Download PDF"
        )
    )

    fun getAllUpdates(): List<ExamUpdateItem> = updates

    fun getUpdatesByCategory(category: UpdateCategory): List<ExamUpdateItem> {
        if (category == UpdateCategory.ALL) return updates
        return updates.filter { it.category == category }
    }
}
