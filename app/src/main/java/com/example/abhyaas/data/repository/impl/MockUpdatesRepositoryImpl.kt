package com.example.abhyaas.data.repository.impl

import com.example.abhyaas.data.mock.MockUpdatesRepository
import com.example.abhyaas.data.model.ExamUpdateItem
import com.example.abhyaas.data.model.UpdateCategory
import com.example.abhyaas.data.repository.UpdatesRepository
import kotlinx.coroutines.delay

class MockUpdatesRepositoryImpl : UpdatesRepository {
    override suspend fun getUpdates(category: UpdateCategory): Result<List<ExamUpdateItem>> {
        delay(300)
        return Result.success(MockUpdatesRepository.getUpdatesByCategory(category))
    }
}
