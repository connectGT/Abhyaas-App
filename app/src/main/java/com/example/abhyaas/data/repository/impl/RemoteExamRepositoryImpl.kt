package com.example.abhyaas.data.repository.impl

import com.example.abhyaas.data.mock.MockExamRepository as MockRepo
import com.example.abhyaas.data.model.*
import com.example.abhyaas.data.network.ApiService
import com.example.abhyaas.data.network.RetrofitClient
import com.example.abhyaas.data.network.dto.toDomain
import com.example.abhyaas.data.repository.ExamRepository

class RemoteExamRepositoryImpl(
    private val api: ApiService = RetrofitClient.apiService
) : ExamRepository {

    override suspend fun getTestSeriesList(): Result<List<TestSeries>> = runCatching {
        try {
            val response = api.getAllTestSeries()
            if (response.isSuccessful && !response.body().isNullOrEmpty()) {
                response.body()!!.map { it.toDomain() }
            } else {
                MockRepo.getTestSeriesList()
            }
        } catch (_: Exception) {
            MockRepo.getTestSeriesList()
        }
    }.recover { MockRepo.getTestSeriesList() }

    override suspend fun getTestSeriesById(id: String): Result<TestSeries?> = runCatching {
        try {
            val response = api.getTestSeriesDetail(id)
            if (response.isSuccessful && response.body() != null) {
                response.body()!!.toDomain()
            } else {
                MockRepo.getTestSeriesById(id)
            }
        } catch (_: Exception) {
            MockRepo.getTestSeriesById(id)
        }
    }.recover { MockRepo.getTestSeriesById(id) }

    override suspend fun getTestsForSubCategory(seriesId: String, subCategory: String): Result<List<Test>> = runCatching {
        try {
            val response = api.getTests(seriesId, subCategory)
            if (response.isSuccessful && !response.body().isNullOrEmpty()) {
                response.body()!!.map { it.toDomain(seriesId) }
            } else {
                MockRepo.getTestsForSubCategory(seriesId, subCategory)
            }
        } catch (_: Exception) {
            MockRepo.getTestsForSubCategory(seriesId, subCategory)
        }
    }.recover { MockRepo.getTestsForSubCategory(seriesId, subCategory) }

    override suspend fun getTestById(testId: String): Result<Test?> = runCatching {
        try {
            val response = api.getTestDetail(testId)
            if (response.isSuccessful && response.body() != null) {
                response.body()!!.toDomain("series_default")
            } else {
                MockRepo.getTestById(testId)
            }
        } catch (_: Exception) {
            MockRepo.getTestById(testId)
        }
    }.recover { MockRepo.getTestById(testId) }

    override fun getHomeCategories(): List<HomeCategoryItem> = MockRepo.getHomeCategories()
}
