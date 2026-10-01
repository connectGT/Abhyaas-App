package com.example.abhyaas.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.abhyaas.AbhyaasApplication
import com.example.abhyaas.data.model.TestSeries
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class TestsUiState(
    val isLoading: Boolean = true,
    val featuredSeries: TestSeries? = null,
    val enrolledSeries: List<TestSeries> = emptyList(),
    val otherSeries: List<TestSeries> = emptyList(),
    val testSeriesList: List<TestSeries> = emptyList(),
    val error: String? = null
)

class TestsViewModel : ViewModel() {
    private val examRepo = AbhyaasApplication.instance.examRepository
    private val _uiState = MutableStateFlow(TestsUiState())
    val uiState: StateFlow<TestsUiState> = _uiState.asStateFlow()

    init {
        loadTestSeries()
    }

    fun loadTestSeries() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            examRepo.getTestSeriesList()
                .onSuccess { list ->
                    val featured = list.firstOrNull()
                    val enrolled = list.filter { it.isEnrolled }
                    val other = list.filter { !it.isEnrolled }
                    _uiState.value = TestsUiState(
                        isLoading = false,
                        featuredSeries = featured,
                        enrolledSeries = if (enrolled.isNotEmpty()) enrolled else list,
                        otherSeries = other,
                        testSeriesList = list
                    )
                }
                .onFailure { e ->
                    _uiState.value = TestsUiState(
                        isLoading = false,
                        error = e.message
                    )
                }
        }
    }

    fun refresh() {
        loadTestSeries()
    }
}
