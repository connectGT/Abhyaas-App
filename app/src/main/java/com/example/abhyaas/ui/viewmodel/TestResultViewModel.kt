package com.example.abhyaas.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.abhyaas.AbhyaasApplication
import com.example.abhyaas.data.model.LeaderboardEntry
import com.example.abhyaas.data.model.Test
import com.example.abhyaas.data.model.TestResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class TestResultUiState(
    val isLoading: Boolean = true,
    val test: Test? = null,
    val result: TestResult? = null,
    val leaderboard: List<LeaderboardEntry> = emptyList(),
    val selectedTabIndex: Int = 0,
    val selectedFilter: String = "All",
    val isHindi: Boolean = false,
    val error: String? = null
)

class TestResultViewModel : ViewModel() {
    private val examRepo = AbhyaasApplication.instance.examRepository
    private val resultRepo = AbhyaasApplication.instance.testResultRepository

    private val _uiState = MutableStateFlow(TestResultUiState())
    val uiState: StateFlow<TestResultUiState> = _uiState.asStateFlow()

    fun loadResult(testId: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            val testResult = examRepo.getTestById(testId)
            val resultResult = resultRepo.getTestResult(testId)
            val leaderboardResult = resultRepo.getLeaderboard(testId)

            _uiState.value = TestResultUiState(
                isLoading = false,
                test = testResult.getOrNull(),
                result = resultResult.getOrNull(),
                leaderboard = leaderboardResult.getOrDefault(emptyList()),
                selectedTabIndex = _uiState.value.selectedTabIndex,
                selectedFilter = _uiState.value.selectedFilter,
                isHindi = _uiState.value.isHindi,
                error = resultResult.exceptionOrNull()?.message
            )
        }
    }

    fun selectTab(index: Int) {
        _uiState.value = _uiState.value.copy(selectedTabIndex = index)
    }

    fun setFilter(filter: String) {
        _uiState.value = _uiState.value.copy(selectedFilter = filter)
    }

    fun toggleLanguage() {
        _uiState.value = _uiState.value.copy(isHindi = !_uiState.value.isHindi)
    }
}
