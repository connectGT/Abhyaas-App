package com.example.abhyaas.data.repository.impl

import com.example.abhyaas.data.mock.MockExamRepository
import com.example.abhyaas.data.model.*
import com.example.abhyaas.data.repository.ExamRepository
import kotlinx.coroutines.delay

class MockExamRepositoryImpl : ExamRepository {
    override suspend fun getTestSeriesList(): Result<List<TestSeries>> {
        delay(300) // simulate network
        return Result.success(MockExamRepository.getTestSeriesList())
    }

    override suspend fun getTestSeriesById(id: String): Result<TestSeries?> {
        delay(200)
        return Result.success(MockExamRepository.getTestSeriesById(id))
    }

    override suspend fun getTestsForSubCategory(seriesId: String, subCategory: String): Result<List<Test>> {
        delay(300)
        return Result.success(MockExamRepository.getTestsForSubCategory(seriesId, subCategory))
    }

    override suspend fun getTestById(testId: String): Result<Test?> {
        delay(200)
        return Result.success(MockExamRepository.getTestById(testId))
    }

    override fun getHomeCategories(): List<HomeCategoryItem> {
        return MockExamRepository.getHomeCategories()
    }
}
