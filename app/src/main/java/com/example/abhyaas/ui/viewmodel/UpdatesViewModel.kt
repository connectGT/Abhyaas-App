package com.example.abhyaas.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.abhyaas.AbhyaasApplication
import com.example.abhyaas.data.model.ExamUpdateItem
import com.example.abhyaas.data.model.UpdateCategory
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class UpdatesUiState(
    val isLoading: Boolean = true,
    val updates: List<ExamUpdateItem> = emptyList(),
    val selectedCategory: UpdateCategory = UpdateCategory.ALL,
    val categories: List<UpdateCategory> = UpdateCategory.entries,
    val error: String? = null
)

class UpdatesViewModel : ViewModel() {
    private val updatesRepo = AbhyaasApplication.instance.updatesRepository
    private val _uiState = MutableStateFlow(UpdatesUiState())
    val uiState: StateFlow<UpdatesUiState> = _uiState.asStateFlow()

    init {
        loadUpdates()
    }

    fun loadUpdates(category: UpdateCategory = _uiState.value.selectedCategory) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, selectedCategory = category, error = null)
            updatesRepo.getUpdates(category)
                .onSuccess { updates ->
                    _uiState.value = UpdatesUiState(
                        isLoading = false,
                        updates = updates,
                        selectedCategory = category,
                        categories = UpdateCategory.entries
                    )
                }
                .onFailure { e ->
                    _uiState.value = UpdatesUiState(
                        isLoading = false,
                        selectedCategory = category,
                        error = e.message
                    )
                }
        }
    }

    fun refresh() {
        loadUpdates(_uiState.value.selectedCategory)
    }
}
