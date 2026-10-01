package com.example.abhyaas.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.abhyaas.AbhyaasApplication
import com.example.abhyaas.data.model.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class ActiveTestUiState(
    val isLoading: Boolean = true,
    val test: Test? = null,
    val questions: List<Question> = emptyList(),
    val currentQuestionIndex: Int = 0,
    val selectedAnswers: Map<Int, Int> = emptyMap(),
    val questionStatuses: Map<Int, QuestionStatus> = emptyMap(),
    val bookmarkedQuestions: Set<Int> = emptySet(),
    val timeRemainingSeconds: Int = 5400,
    val isPaused: Boolean = false,
    val isHindi: Boolean = false,
    val isSubmitted: Boolean = false,
    val submittedResult: TestResult? = null,
    val error: String? = null
)

class ActiveTestViewModel : ViewModel() {
    private val examRepo = AbhyaasApplication.instance.examRepository
    private val questionRepo = AbhyaasApplication.instance.questionRepository
    private val resultRepo = AbhyaasApplication.instance.testResultRepository

    private val _uiState = MutableStateFlow(ActiveTestUiState())
    val uiState: StateFlow<ActiveTestUiState> = _uiState.asStateFlow()

    private var startTimeSeconds: Long = 0L

    fun loadTest(testId: String, initialLanguage: String = "English") {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            val testResult = examRepo.getTestById(testId)
            val questionsResult = questionRepo.getQuestionsForTest(testId)
            val bookmarkedResult = questionRepo.getBookmarkedQuestions()

            val test = testResult.getOrNull()
            val questions = questionsResult.getOrDefault(emptyList())
            val initialStatuses = questions.associate {
                it.id to QuestionStatus.NOT_VISITED
            }
            startTimeSeconds = System.currentTimeMillis() / 1000
            _uiState.value = ActiveTestUiState(
                isLoading = false,
                test = test,
                questions = questions,
                timeRemainingSeconds = (test?.durationMinutes ?: 90) * 60,
                questionStatuses = initialStatuses,
                bookmarkedQuestions = bookmarkedResult.getOrDefault(emptyList()).toSet(),
                isHindi = initialLanguage == "Hindi"
            )
        }
    }

    fun selectAnswer(questionId: Int, optionIndex: Int) {
        _uiState.update { current ->
            val newAnswers = current.selectedAnswers.toMutableMap().apply { put(questionId, optionIndex) }
            val newStatuses = current.questionStatuses.toMutableMap().apply {
                put(questionId, QuestionStatus.ANSWERED)
            }
            current.copy(selectedAnswers = newAnswers, questionStatuses = newStatuses)
        }
    }

    fun clearAnswer(questionId: Int) {
        _uiState.update { current ->
            val newAnswers = current.selectedAnswers.toMutableMap().apply { remove(questionId) }
            val newStatuses = current.questionStatuses.toMutableMap().apply {
                put(questionId, QuestionStatus.NOT_VISITED) // Change UNANSWERED to NOT_VISITED to match Palette logic
            }
            current.copy(selectedAnswers = newAnswers, questionStatuses = newStatuses)
        }
    }

    fun navigateToQuestion(index: Int) {
        val current = _uiState.value
        val question = current.questions.getOrNull(index) ?: return
        val newStatuses = current.questionStatuses.toMutableMap().apply {
            if (get(question.id) == QuestionStatus.NOT_VISITED) {
                put(question.id, QuestionStatus.UNANSWERED)
            }
        }
        _uiState.value = current.copy(currentQuestionIndex = index, questionStatuses = newStatuses)
    }

    fun markForReview(questionId: Int) {
        val current = _uiState.value
        val newStatuses = current.questionStatuses.toMutableMap().apply {
            val currentStatus = get(questionId)
            put(
                questionId,
                if (currentStatus == QuestionStatus.ANSWERED)
                    QuestionStatus.ANSWERED_AND_MARKED
                else
                    QuestionStatus.MARKED_FOR_REVIEW
            )
        }
        _uiState.value = current.copy(questionStatuses = newStatuses)
    }

    fun toggleBookmark(questionId: Int) {
        val current = _uiState.value
        val isBookmarked = current.bookmarkedQuestions.contains(questionId)
        val newBookmarks = current.bookmarkedQuestions.toMutableSet().apply {
            if (isBookmarked) remove(questionId) else add(questionId)
        }
        _uiState.value = current.copy(bookmarkedQuestions = newBookmarks)
        viewModelScope.launch {
            questionRepo.bookmarkQuestion(questionId, !isBookmarked)
        }
    }

    fun toggleLanguage() {
        val current = _uiState.value
        _uiState.value = current.copy(isHindi = !current.isHindi)
    }

    fun togglePause() {
        val current = _uiState.value
        _uiState.value = current.copy(isPaused = !current.isPaused)
    }

    fun tickTimer() {
        val current = _uiState.value
        if (current.isPaused || current.isSubmitted) return
        if (current.timeRemainingSeconds > 0) {
            _uiState.value = current.copy(timeRemainingSeconds = current.timeRemainingSeconds - 1)
        } else {
            submitTest()
        }
    }

    fun submitTest(onComplete: ((TestResult?) -> Unit)? = null) {
        viewModelScope.launch {
            val current = _uiState.value
            val testId = current.test?.id ?: return@launch
            val timeTaken = (System.currentTimeMillis() / 1000) - startTimeSeconds
            val result = resultRepo.submitTest(testId, current.selectedAnswers, timeTaken).getOrNull()
            _uiState.value = current.copy(isSubmitted = true, submittedResult = result)
            onComplete?.invoke(result)
        }
    }
}
