package com.example.abhyaas.data.repository

import com.example.abhyaas.data.model.*

interface ExamRepository {
    suspend fun getTestSeriesList(): Result<List<TestSeries>>
    suspend fun getTestSeriesById(id: String): Result<TestSeries?>
    suspend fun getTestsForSubCategory(seriesId: String, subCategory: String): Result<List<Test>>
    suspend fun getTestById(testId: String): Result<Test?>
    fun getHomeCategories(): List<HomeCategoryItem>
}
