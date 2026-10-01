package com.example.abhyaas.contract

class ActiveExamStateMachine(val test: Test) {

    val allQuestions: List<Question> = test.sections.flatMap { it.questions }
    private val questionStatusMap = mutableMapOf<Int, QuestionStatus>()
    private val selectedAnswersMap = mutableMapOf<Int, Int>()
    private val questionTimeSpentMap = mutableMapOf<Int, Long>()
    private val bookmarkedSet = mutableSetOf<Int>()

    var currentQuestionIndex: Int = 0
        private set

    var currentSectionIndex: Int = 0
        private set

    var remainingSeconds: Long = (test.durationMinutes * 60).toLong()
        private set

    var isPaused: Boolean = false
        private set

    var isAutoSubmitted: Boolean = false
        private set

    var isManuallySubmitted: Boolean = false
        private set

    var isPaletteOpen: Boolean = false
    var isSubmitDialogOpen: Boolean = false
    var isSymbolsDialogOpen: Boolean = false

    init {
        // Mark first question as UNANSWERED (viewed)
        if (allQuestions.isNotEmpty()) {
            questionStatusMap[allQuestions[0].id] = QuestionStatus.UNANSWERED
        }
    }

    val currentQuestion: Question
        get() = allQuestions[currentQuestionIndex]

    val currentSection: TestSection
        get() = test.sections[currentSectionIndex]

    fun getStatus(questionId: Int): QuestionStatus {
        return questionStatusMap[questionId] ?: QuestionStatus.NOT_VISITED
    }

    fun getSelectedAnswer(questionId: Int): Int? {
        return selectedAnswersMap[questionId]
    }

    fun isBookmarked(questionId: Int): Boolean {
        return bookmarkedSet.contains(questionId)
    }

    fun toggleBookmark(questionId: Int) {
        if (bookmarkedSet.contains(questionId)) {
            bookmarkedSet.remove(questionId)
        } else {
            bookmarkedSet.add(questionId)
        }
    }

    fun selectOption(optionIndex: Int) {
        val q = currentQuestion
        selectedAnswersMap[q.id] = optionIndex
    }

    fun clearOption() {
        val q = currentQuestion
        selectedAnswersMap.remove(q.id)
    }

    fun saveAndNext() {
        val q = currentQuestion
        val chosen = selectedAnswersMap[q.id]
        if (chosen != null) {
            questionStatusMap[q.id] = QuestionStatus.ANSWERED
        } else {
            questionStatusMap[q.id] = QuestionStatus.UNANSWERED
        }

        advanceToNextQuestion()
    }

    fun markForReview() {
        val q = currentQuestion
        val chosen = selectedAnswersMap[q.id]
        if (chosen != null) {
            questionStatusMap[q.id] = QuestionStatus.ANSWERED_AND_MARKED
        } else {
            questionStatusMap[q.id] = QuestionStatus.MARKED_FOR_REVIEW
        }

        advanceToNextQuestion()
    }

    fun unmarkReview() {
        val q = currentQuestion
        val chosen = selectedAnswersMap[q.id]
        if (chosen != null) {
            questionStatusMap[q.id] = QuestionStatus.ANSWERED
        } else {
            questionStatusMap[q.id] = QuestionStatus.UNANSWERED
        }
    }

    fun jumpToQuestion(index: Int) {
        require(index in allQuestions.indices) { "Invalid question index: $index" }
        currentQuestionIndex = index
        updateSectionForCurrentQuestion()
        val q = allQuestions[index]
        if (getStatus(q.id) == QuestionStatus.NOT_VISITED) {
            questionStatusMap[q.id] = QuestionStatus.UNANSWERED
        }
    }

    fun switchSection(sectionIndex: Int) {
        require(sectionIndex in test.sections.indices) { "Invalid section index: $sectionIndex" }
        currentSectionIndex = sectionIndex
        // Jump to first question of this section
        val targetSection = test.sections[sectionIndex]
        val firstQ = targetSection.questions.firstOrNull()
        if (firstQ != null) {
            val globalIdx = allQuestions.indexOfFirst { it.id == firstQ.id }
            if (globalIdx != -1) {
                jumpToQuestion(globalIdx)
            }
        }
    }

    private fun advanceToNextQuestion() {
        if (currentQuestionIndex < allQuestions.size - 1) {
            currentQuestionIndex++
            updateSectionForCurrentQuestion()
            val nextQ = allQuestions[currentQuestionIndex]
            if (getStatus(nextQ.id) == QuestionStatus.NOT_VISITED) {
                questionStatusMap[nextQ.id] = QuestionStatus.UNANSWERED
            }
        }
    }

    private fun updateSectionForCurrentQuestion() {
        val q = currentQuestion
        val secIdx = test.sections.indexOfFirst { it.id == q.sectionId }
        if (secIdx != -1) {
            currentSectionIndex = secIdx
        }
    }

    fun tick(seconds: Long = 1) {
        if (isPaused || isAutoSubmitted || isManuallySubmitted) return

        remainingSeconds = (remainingSeconds - seconds).coerceAtLeast(0)
        // Record time spent on current question
        val q = currentQuestion
        val prev = questionTimeSpentMap[q.id] ?: 0L
        questionTimeSpentMap[q.id] = prev + seconds

        if (remainingSeconds == 0L) {
            autoSubmit()
        }
    }

    fun pause() {
        isPaused = true
    }

    fun resume() {
        isPaused = false
    }

    private fun autoSubmit() {
        isAutoSubmitted = true
    }

    fun submitManually() {
        isManuallySubmitted = true
    }

    val isSubmitted: Boolean
        get() = isAutoSubmitted || isManuallySubmitted

    fun buildAttempt(attemptId: String = "attempt_${System.currentTimeMillis()}"): TestAttempt {
        val totalTime = (test.durationMinutes * 60) - remainingSeconds
        return TestAttempt(
            attemptId = attemptId,
            testId = test.id,
            completedAt = System.currentTimeMillis(),
            timeTakenSeconds = totalTime.coerceAtLeast(0L),
            selectedAnswers = selectedAnswersMap.toMap(),
            questionStatus = questionStatusMap.toMap(),
            questionTimeSpent = questionTimeSpentMap.toMap(),
            bookmarkedQuestions = bookmarkedSet.toSet()
        )
    }

    // Counts summary
    fun getSummaryCounts(): Map<QuestionStatus, Int> {
        val counts = mutableMapOf(
            QuestionStatus.NOT_VISITED to 0,
            QuestionStatus.UNANSWERED to 0,
            QuestionStatus.ANSWERED to 0,
            QuestionStatus.MARKED_FOR_REVIEW to 0,
            QuestionStatus.ANSWERED_AND_MARKED to 0
        )
        allQuestions.forEach { q ->
            val st = getStatus(q.id)
            counts[st] = (counts[st] ?: 0) + 1
        }
        return counts
    }
}

class SolutionsReviewStateMachine(
    val test: Test,
    val attempt: TestAttempt
) {
    var isReattemptModeOn: Boolean = false
        private set

    var selectedFilter: SolutionFilter = SolutionFilter.ALL
        private set

    var currentQuestionIndex: Int = 0

    val allQuestions: List<Question> = test.sections.flatMap { it.questions }

    fun toggleReattemptMode(): Boolean {
        isReattemptModeOn = !isReattemptModeOn
        return isReattemptModeOn
    }

    fun setFilter(filter: SolutionFilter) {
        selectedFilter = filter
        currentQuestionIndex = 0
    }

    fun getFilteredQuestions(): List<Question> {
        return allQuestions.filter { q ->
            val chosen = attempt.selectedAnswers[q.id]
            when (selectedFilter) {
                SolutionFilter.ALL -> true
                SolutionFilter.CORRECT -> chosen != null && chosen == q.correctOptionIndex
                SolutionFilter.INCORRECT -> chosen != null && chosen != q.correctOptionIndex
                SolutionFilter.UNATTEMPTED -> chosen == null
            }
        }
    }

    fun isSolutionVisible(questionId: Int): Boolean {
        return !isReattemptModeOn
    }
}
