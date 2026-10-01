package com.example.abhyaas

import android.app.Application
import com.example.abhyaas.data.repository.*
import com.example.abhyaas.data.repository.impl.*

/**
 * Application-level dependency container.
 * Repositories are backed by Retrofit with graceful Mock fallback.
 */
class AbhyaasApplication : Application() {
    val examRepository: ExamRepository by lazy { RemoteExamRepositoryImpl() }
    val questionRepository: QuestionRepository by lazy { RemoteQuestionRepositoryImpl() }
    val testResultRepository: TestResultRepository by lazy { MockTestResultRepositoryImpl() }
    val updatesRepository: UpdatesRepository by lazy { RemoteUpdatesRepositoryImpl() }
    val userRepository: UserRepository by lazy { RemoteUserRepositoryImpl() }

    companion object {
        lateinit var instance: AbhyaasApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}
