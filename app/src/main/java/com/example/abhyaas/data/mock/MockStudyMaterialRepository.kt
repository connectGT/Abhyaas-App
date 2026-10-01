package com.example.abhyaas.data.mock

import com.example.abhyaas.data.model.StudyMaterial

object MockStudyMaterialRepository {
    fun getMaterialsForSeries(seriesId: String): List<StudyMaterial> = listOf(
        StudyMaterial(
            id = "sm_1",
            title = "MPLRC 1959 - Complete Summary",
            description = "Comprehensive notes on MP Land Revenue Code 1959 covering all key sections tested in the exam.",
            subject = "Revenue Laws",
            pdfUrl = "https://pub-placeholder.r2.dev/mplrc_summary.pdf",
            pdfSize = "1.8 MB",
            pageCount = 24,
            seriesId = seriesId,
            isFree = true
        ),
        StudyMaterial(
            id = "sm_2",
            title = "Reasoning Quick Formulas",
            description = "All important formulas and shortcuts for the Logical Ability section.",
            subject = "Reasoning",
            pdfUrl = "https://pub-placeholder.r2.dev/reasoning_formulas.pdf",
            pdfSize = "0.9 MB",
            pageCount = 12,
            seriesId = seriesId,
            isFree = true
        ),
        StudyMaterial(
            id = "sm_3",
            title = "General Knowledge - MP Special",
            description = "Madhya Pradesh specific GK notes: Geography, History, Economy and Current Affairs.",
            subject = "General Knowledge",
            pdfUrl = "https://pub-placeholder.r2.dev/mp_gk.pdf",
            pdfSize = "2.1 MB",
            pageCount = 30,
            seriesId = seriesId,
            isFree = true
        ),
        StudyMaterial(
            id = "sm_4",
            title = "Land Records Manual - Key Points",
            description = "Essential points from the Land Records Manual required for Paper II.",
            subject = "Revenue Terminology",
            pdfUrl = "https://pub-placeholder.r2.dev/land_records.pdf",
            pdfSize = "1.4 MB",
            pageCount = 18,
            seriesId = seriesId,
            isFree = false
        ),
        StudyMaterial(
            id = "sm_5",
            title = "Math & Science - Formula Sheet",
            description = "One-page formula sheet for Math and Science topics in Paper I.",
            subject = "Math & Science",
            pdfUrl = "https://pub-placeholder.r2.dev/math_science.pdf",
            pdfSize = "0.5 MB",
            pageCount = 6,
            seriesId = seriesId,
            isFree = true
        )
    )
}
