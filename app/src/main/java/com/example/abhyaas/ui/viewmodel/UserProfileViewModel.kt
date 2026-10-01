package com.example.abhyaas.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.abhyaas.AbhyaasApplication
import com.example.abhyaas.data.model.PreparationDataPoint
import com.example.abhyaas.data.model.UserProfile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class UserProfileUiState(
    val isLoading: Boolean = true,
    val profile: UserProfile? = null,
    val selectedTrendMetric: String = "Questions",
    val trendDataPoints: List<PreparationDataPoint> = emptyList(),
    val isSaving: Boolean = false,
    val saveSuccess: Boolean = false,
    val error: String? = null
)

class UserProfileViewModel : ViewModel() {
    private val userRepo = AbhyaasApplication.instance.userRepository

    private val _uiState = MutableStateFlow(UserProfileUiState())
    val uiState: StateFlow<UserProfileUiState> = _uiState.asStateFlow()

    init {
        loadProfile()
    }

    fun loadProfile() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            val profileResult = userRepo.getUserProfile()
            val pointsResult = userRepo.getPreparationDataPoints(_uiState.value.selectedTrendMetric)
            _uiState.value = UserProfileUiState(
                isLoading = false,
                profile = profileResult.getOrNull(),
                selectedTrendMetric = _uiState.value.selectedTrendMetric,
                trendDataPoints = pointsResult.getOrDefault(emptyList()),
                error = profileResult.exceptionOrNull()?.message
            )
        }
    }

    fun selectTrendMetric(metric: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(selectedTrendMetric = metric)
            userRepo.getPreparationDataPoints(metric)
                .onSuccess { points ->
                    _uiState.value = _uiState.value.copy(trendDataPoints = points)
                }
        }
    }

    fun updateProfile(updated: UserProfile, onComplete: ((Boolean) -> Unit)? = null) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isSaving = true, saveSuccess = false, error = null)
            userRepo.updateUserProfile(updated)
                .onSuccess { newProfile ->
                    _uiState.value = _uiState.value.copy(
                        isSaving = false,
                        saveSuccess = true,
                        profile = newProfile
                    )
                    onComplete?.invoke(true)
                }
                .onFailure { e ->
                    _uiState.value = _uiState.value.copy(
                        isSaving = false,
                        saveSuccess = false,
                        error = e.message
                    )
                    onComplete?.invoke(false)
                }
        }
    }
}
