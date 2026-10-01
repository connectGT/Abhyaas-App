package com.example.abhyaas.data.repository.impl

import com.example.abhyaas.data.mock.MockUpdatesRepository
import com.example.abhyaas.data.model.ExamUpdateItem
import com.example.abhyaas.data.model.UpdateCategory
import com.example.abhyaas.data.network.ApiService
import com.example.abhyaas.data.network.RetrofitClient
import com.example.abhyaas.data.network.dto.toDomain
import com.example.abhyaas.data.repository.UpdatesRepository

class RemoteUpdatesRepositoryImpl(
    private val api: ApiService = RetrofitClient.apiService
) : UpdatesRepository {

    override suspend fun getUpdates(category: UpdateCategory): Result<List<ExamUpdateItem>> = runCatching {
        try {
            val categoryQuery = if (category == UpdateCategory.ALL) null else category.name.lowercase()
            val response = api.getUpdates(categoryQuery)
            if (response.isSuccessful && !response.body().isNullOrEmpty()) {
                response.body()!!.map { it.toDomain() }
            } else {
                MockUpdatesRepository.getUpdatesByCategory(category)
            }
        } catch (_: Exception) {
            MockUpdatesRepository.getUpdatesByCategory(category)
        }
    }.recover { MockUpdatesRepository.getUpdatesByCategory(category) }
}
