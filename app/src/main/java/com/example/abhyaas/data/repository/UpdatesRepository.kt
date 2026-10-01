package com.example.abhyaas.data.repository

import com.example.abhyaas.data.model.ExamUpdateItem
import com.example.abhyaas.data.model.UpdateCategory

interface UpdatesRepository {
    suspend fun getUpdates(category: UpdateCategory = UpdateCategory.ALL): Result<List<ExamUpdateItem>>
}
