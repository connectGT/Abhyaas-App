package com.example.abhyaas.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.abhyaas.AbhyaasApplication
import com.example.abhyaas.data.model.Test
import com.example.abhyaas.data.model.TestSeries
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class TestListUiState(
    val isLoading: Boolean = true,
    val seriesId: String = "",
    val subCategory: String = "",
    val series: TestSeries? = null,
    val seriesTitle: String = "Test Series",
    val seriesSubtitle: String = "Mock Test Series",
    val subTabs: List<String> = listOf("Exam Day Special", "Most Saved Qs Subjec..."),
    val selectedSubTabIndex: Int = 0,
    val tests: List<Test> = emptyList(),
    val suggestedTest: Test? = null,
    val attemptedTest: Test? = null,
    val remainingTests: List<Test> = emptyList(),
    val error: String? = null
)

class TestListViewModel : ViewModel() {
    private val examRepo = AbhyaasApplication.instance.examRepository

    private val _uiState = MutableStateFlow(TestListUiState())
    val uiState: StateFlow<TestListUiState> = _uiState.asStateFlow()

    fun loadTests(seriesId: String, subCategory: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                seriesId = seriesId,
                subCategory = subCategory,
                error = null
            )
            val series = examRepo.getTestSeriesById(seriesId).getOrNull()
            val testsResult = examRepo.getTestsForSubCategory(seriesId, subCategory)
            val tests = testsResult.getOrDefault(emptyList())

            val suggested = tests.find { it.id.contains("02") || it.id.contains("suggested") } ?: tests.firstOrNull()
            val attempted = tests.find { it.previousAttempt != null } ?: tests.getOrNull(1)
            val remaining = tests.filter { it.id != suggested?.id && it.id != attempted?.id }

            val subTabs = if (series != null && series.mockFolders.isNotEmpty()) {
                series.mockFolders.map { it.title }
            } else {
                listOf("Exam Day Special", "Most Saved Qs Subjec...")
            }
            val activeIndex = subTabs.indexOfFirst { it.equals(subCategory, ignoreCase = true) }
                .let { if (it >= 0) it else 0 }

            _uiState.value = TestListUiState(
                isLoading = false,
                seriesId = seriesId,
                subCategory = subCategory,
                series = series,
                seriesTitle = series?.title ?: "Test Series",
                seriesSubtitle = series?.subtitle ?: "Mock Test Series",
                subTabs = subTabs,
                selectedSubTabIndex = activeIndex,
                tests = tests,
                suggestedTest = suggested,
                attemptedTest = attempted,
                remainingTests = remaining,
                error = testsResult.exceptionOrNull()?.message
            )
        }
    }

    fun selectSubTab(index: Int) {
        val current = _uiState.value
        if (index in current.subTabs.indices) {
            val newSubCategory = current.subTabs[index]
            _uiState.value = current.copy(selectedSubTabIndex = index, subCategory = newSubCategory)
            loadTests(current.seriesId, newSubCategory)
        }
    }

    fun refresh() {
        val current = _uiState.value
        if (current.seriesId.isNotBlank()) {
            loadTests(current.seriesId, current.subCategory)
        }
    }
}
