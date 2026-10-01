package com.example.abhyaas.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.abhyaas.AbhyaasApplication
import com.example.abhyaas.data.model.HomeCategoryItem
import com.example.abhyaas.data.model.TestSeries
import com.example.abhyaas.data.model.UserProfile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class HomeUiState(
    val isLoading: Boolean = true,
    val categories: List<HomeCategoryItem> = emptyList(),
    val testSeriesList: List<TestSeries> = emptyList(),
    val userProfile: UserProfile? = null,
    val selectedExam: String = "Nayab Tehsildar",
    val error: String? = null
)

class HomeViewModel : ViewModel() {
    private val examRepo = AbhyaasApplication.instance.examRepository
    private val userRepo = AbhyaasApplication.instance.userRepository

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            try {
                val categories = examRepo.getHomeCategories()
                val seriesResult = examRepo.getTestSeriesList()
                val profileResult = userRepo.getUserProfile()
                _uiState.value = HomeUiState(
                    isLoading = false,
                    categories = categories,
                    testSeriesList = seriesResult.getOrDefault(emptyList()),
                    userProfile = profileResult.getOrNull(),
                    selectedExam = profileResult.getOrNull()?.targetExam ?: "Nayab Tehsildar"
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isLoading = false, error = e.message)
            }
        }
    }

    fun setSelectedExam(exam: String) {
        _uiState.value = _uiState.value.copy(selectedExam = exam)
    }

    fun refresh() {
        loadData()
    }
}
