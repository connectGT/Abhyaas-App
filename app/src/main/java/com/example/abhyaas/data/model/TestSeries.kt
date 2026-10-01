package com.example.abhyaas.data.model

data class TestSeriesFolder(
    val id: String,
    val title: String,
    val testCountText: String,
    val freeTestsBadge: String? = null,
    val isLive: Boolean = false,
    val isPYQ: Boolean = false
)

data class HomeCategoryItem(
    val id: String,
    val title: String,
    val subtitle: String = "",
    val iconName: String = "",
    val badge: String? = null,
    val gradientStartColorHex: Long = 0xFF2563EB,
    val gradientEndColorHex: Long = 0xFF1D4ED8
)

data class TestSeries(
    val id: String,
    val title: String,
    val subtitle: String,
    val categoryId: String = "ssc_selection_post",
    val totalTests: Int = 610,
    val fullTestsCount: Int = 30,
    val pyqCount: Int = 90,
    val attemptedCount: Int = 1,
    val vacancies: String? = "3000+",
    val examDates: String? = "Sep - 2026",
    val isEnrolled: Boolean = true,
    val mockFolders: List<TestSeriesFolder> = emptyList(),
    val pypFolders: List<TestSeriesFolder> = emptyList(),
    val studyNotesFolders: List<TestSeriesFolder> = emptyList()
)
