package com.example.abhyaas.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.abhyaas.AbhyaasApplication
import com.example.abhyaas.data.model.TestSeries
import com.example.abhyaas.data.model.TestSeriesFolder
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class TestSeriesDetailUiState(
    val isLoading: Boolean = true,
    val series: TestSeries? = null,
    val selectedTabIndex: Int = 0,
    val folders: List<TestSeriesFolder> = emptyList(),
    val selectedSubCategory: String = "",
    val error: String? = null
)

class TestSeriesDetailViewModel : ViewModel() {
    private val examRepo = AbhyaasApplication.instance.examRepository
    private val _uiState = MutableStateFlow(TestSeriesDetailUiState())
    val uiState: StateFlow<TestSeriesDetailUiState> = _uiState.asStateFlow()

    fun loadSeries(seriesId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                examRepo.getTestSeriesById(seriesId)
                    .onSuccess { series ->
                        val initialFolders = getFoldersForTab(series, _uiState.value.selectedTabIndex)
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                series = series,
                                folders = initialFolders,
                                selectedSubCategory = initialFolders.firstOrNull()?.title ?: ""
                            )
                        }
                    }
                    .onFailure { e ->
                        _uiState.update { it.copy(isLoading = false, error = e.message ?: "Failed to load test series") }
                    }
            } catch (e: Exception) {
                if (e is CancellationException) throw e
                _uiState.update { it.copy(isLoading = false, error = e.message ?: "An unexpected error occurred") }
            }
        }
    }

    fun selectTab(tabIndex: Int) {
        _uiState.update { state ->
            val newFolders = getFoldersForTab(state.series, tabIndex)
            state.copy(
                selectedTabIndex = tabIndex,
                folders = newFolders
            )
        }
    }

    private fun getFoldersForTab(series: TestSeries?, tabIndex: Int): List<TestSeriesFolder> {
        if (series == null) return emptyList()
        return when (tabIndex) {
            0 -> series.mockFolders
            1 -> series.pypFolders
            2 -> series.studyNotesFolders
            else -> series.mockFolders
        }
    }
}
