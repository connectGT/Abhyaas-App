package com.example.abhyaas.data.model

data class StudyMaterial(
    val id: String,
    val title: String,
    val description: String,
    val subject: String,
    val pdfUrl: String,       // Full URL to PDF on Cloudflare R2 / any CDN
    val pdfSize: String,      // e.g. "2.4 MB"
    val pageCount: Int,
    val seriesId: String,
    val isFree: Boolean = true
)
