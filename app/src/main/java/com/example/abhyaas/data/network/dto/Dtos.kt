package com.example.abhyaas.data.network.dto

import com.example.abhyaas.data.model.*
import com.google.gson.annotations.SerializedName

// ─── Base ───────────────────────────────────────────────────────────────────

data class BaseResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String = ""
)

// ─── Auth ───────────────────────────────────────────────────────────────────

data class SendOtpRequest(
    @SerializedName("mobile") val mobile: String,
    @SerializedName("country_code") val countryCode: String = "+91"
)

data class VerifyOtpRequest(
    @SerializedName("mobile") val mobile: String,
    @SerializedName("otp") val otp: String
)

data class AuthResponse(
    @SerializedName("token") val token: String,
    @SerializedName("user_id") val userId: String,
    @SerializedName("is_new_user") val isNewUser: Boolean
)

// ─── User Profile & Trends ──────────────────────────────────────────────────

data class UserProfileDto(
    @SerializedName("id") val id: String = "",
    @SerializedName("name") val name: String = "",
    @SerializedName("mobile") val mobile: String = "",
    @SerializedName("email") val email: String = "",
    @SerializedName("date_of_birth") val dateOfBirth: String = "",
    @SerializedName("category") val category: String = "",
    @SerializedName("pin_code") val pinCode: String = "",
    @SerializedName("education") val education: String = "",
    @SerializedName("avatar_url") val avatarUrl: String = "",
    @SerializedName("target_exam") val targetExam: String = "",
    @SerializedName("rank") val rank: Int = 0,
    @SerializedName("total_tests_attempted") val totalTestsAttempted: Int = 0,
    @SerializedName("accuracy") val accuracy: Float = 0f
)

data class UpdateProfileRequest(
    @SerializedName("name") val name: String,
    @SerializedName("date_of_birth") val dateOfBirth: String,
    @SerializedName("category") val category: String,
    @SerializedName("pin_code") val pinCode: String,
    @SerializedName("education") val education: String,
    @SerializedName("target_exam") val targetExam: String
)

data class PreparationDataPointDto(
    @SerializedName("label") val label: String = "",
    @SerializedName("value") val value: Float = 0f,
    @SerializedName("period") val period: String? = null,
    @SerializedName("date_label") val date_label: String? = null,
    @SerializedName("questions_count") val questions_count: Int? = null,
    @SerializedName("accuracy_percent") val accuracy_percent: Int? = null,
    @SerializedName("time_spent_minutes") val time_spent_minutes: Int? = null
)

// ─── Home Categories ────────────────────────────────────────────────────────

data class HomeCategoryDto(
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("subtitle") val subtitle: String? = null,
    @SerializedName("icon_name") val icon_name: String? = null,
    @SerializedName("test_count") val test_count: Int? = null,
    @SerializedName("badge") val badge: String? = null,
    @SerializedName("gradient_start_hex") val gradient_start_hex: Long? = null,
    @SerializedName("gradient_end_hex") val gradient_end_hex: Long? = null
)

// ─── Exam & Test Series ─────────────────────────────────────────────────────

data class ExamDto(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("abbreviation") val abbreviation: String,
    @SerializedName("description") val description: String = "",
    @SerializedName("total_vacancies") val totalVacancies: String = "",
    @SerializedName("exam_dates") val examDates: String = ""
)

data class FolderDto(
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("subtitle") val subtitle: String = "",
    @SerializedName("free_tests_badge") val freeTestsBadge: String? = null,
    @SerializedName("is_live") val isLive: Boolean = false,
    @SerializedName("is_pyq") val isPYQ: Boolean = false
)

typealias TestSeriesFolderDto = FolderDto

data class TestSeriesDto(
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("subtitle") val subtitle: String = "",
    @SerializedName("category_id") val categoryId: String = "",
    @SerializedName("total_tests") val totalTests: Int = 0,
    @SerializedName("full_tests_count") val fullTestsCount: Int = 0,
    @SerializedName("pyq_count") val pyqCount: Int = 0,
    @SerializedName("attempted_count") val attemptedCount: Int = 0,
    @SerializedName("vacancies") val vacancies: String = "",
    @SerializedName("exam_dates") val examDates: String = "",
    @SerializedName("is_enrolled") val isEnrolled: Boolean = false,
    @SerializedName("mock_folders") val mockFolders: List<FolderDto> = emptyList(),
    @SerializedName("pyp_folders") val pypFolders: List<FolderDto> = emptyList(),
    @SerializedName("notes_folders") val notes_folders: List<TestSeriesFolderDto>? = emptyList()
)

// ─── Test & Question ────────────────────────────────────────────────────────

data class TestDto(
    @SerializedName("id") val id: String,
    @SerializedName("series_id") val seriesId: String = "",
    @SerializedName("title") val title: String,
    @SerializedName("sub_category") val subCategory: String = "",
    @SerializedName("duration_minutes") val durationMinutes: Int = 90,
    @SerializedName("total_questions") val totalQuestions: Int = 100,
    @SerializedName("total_marks") val totalMarks: Float = 100f,
    @SerializedName("is_free") val isFree: Boolean = true
)

data class QuestionDto(
    @SerializedName("id") val id: Int,
    @SerializedName("section_id") val sectionId: String,
    @SerializedName("question_number") val questionNumber: Int,
    @SerializedName("statement") val statement: String,
    @SerializedName("statement_hindi") val statementHindi: String? = null,
    @SerializedName("options") val options: List<OptionDto>,
    @SerializedName("correct_option_index") val correctOptionIndex: Int,
    @SerializedName("explanation") val explanation: String = "",
    @SerializedName("topic") val topic: String = "",
    @SerializedName("subject") val subject: String = ""
)

data class OptionDto(
    @SerializedName("id") val id: Int,
    @SerializedName("text") val text: String,
    @SerializedName("text_hindi") val textHindi: String? = null
)

// ─── Result & Leaderboard ───────────────────────────────────────────────────

data class SectionResultDto(
    @SerializedName("section_id") val sectionId: String,
    @SerializedName("section_name") val sectionName: String,
    @SerializedName("score") val score: Float = 0f,
    @SerializedName("correct_count") val correctCount: Int = 0,
    @SerializedName("incorrect_count") val incorrectCount: Int = 0,
    @SerializedName("unattempted_count") val unattemptedCount: Int = 0,
    @SerializedName("marked_count") val markedCount: Int = 0,
    @SerializedName("accuracy") val accuracy: Float = 0f,
    @SerializedName("time_taken_seconds") val timeTakenSeconds: Long = 0L,
    @SerializedName("total_questions") val totalQuestions: Int = 25
)

data class SubmitTestRequest(
    @SerializedName("test_id") val testId: String,
    @SerializedName("answers") val answers: Map<Int, Int>,
    @SerializedName("time_taken_seconds") val timeTakenSeconds: Long
)

data class TestResultDto(
    @SerializedName("attempt_id") val attemptId: String,
    @SerializedName("test_id") val testId: String,
    @SerializedName("test_title") val testTitle: String = "",
    @SerializedName("score") val score: Float = 0f,
    @SerializedName("total_marks") val totalMarks: Float = 100f,
    @SerializedName("rank") val rank: Int = 0,
    @SerializedName("total_candidates") val totalCandidates: Int = 0,
    @SerializedName("percentile") val percentile: Float = 0f,
    @SerializedName("accuracy") val accuracy: Float = 0f,
    @SerializedName("correct_count") val correctCount: Int = 0,
    @SerializedName("incorrect_count") val incorrectCount: Int = 0,
    @SerializedName("unattempted_count") val unattemptedCount: Int = 0,
    @SerializedName("cutoff_marks") val cutoffMarks: String = "",
    @SerializedName("average_score") val averageScore: Float = 0f,
    @SerializedName("attempt_date") val attemptDate: String = "",
    @SerializedName("section_breakdowns") val section_breakdowns: List<SectionResultDto>? = null
)

data class LeaderboardEntryDto(
    @SerializedName("rank") val rank: Int,
    @SerializedName("user_id") val userId: String,
    @SerializedName("name") val name: String,
    @SerializedName("score") val score: Float,
    @SerializedName("accuracy") val accuracy: Float,
    @SerializedName("avatar_url") val avatarUrl: String? = null
)

// ─── Updates ────────────────────────────────────────────────────────────────

data class UpdateItemDto(
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("date") val date: String,
    @SerializedName("category") val category: String,
    @SerializedName("is_pinned") val isPinned: Boolean = false,
    @SerializedName("pdf_size") val pdfSize: String? = null,
    @SerializedName("pdf_url") val pdfUrl: String? = null,
    @SerializedName("action_text") val actionText: String = "View Details"
)

// ─── Progress ───────────────────────────────────────────────────────────────

data class UserProgressDto(
    @SerializedName("user_id") val userId: String = "",
    @SerializedName("tests_attempted") val testsAttempted: Int = 0,
    @SerializedName("tests_completed") val testsCompleted: Int = 0,
    @SerializedName("average_score") val averageScore: Float = 0f,
    @SerializedName("total_time_seconds") val totalTimeSeconds: Long = 0L,
    @SerializedName("bookmarked_question_ids") val bookmarkedQuestionIds: List<Int> = emptyList()
)

// ─── Domain Mappers (toDomain & toModel) ────────────────────────────────────

fun HomeCategoryDto.toDomain(): HomeCategoryItem = HomeCategoryItem(
    id = id,
    title = title,
    subtitle = subtitle ?: "",
    iconName = icon_name ?: "",
    badge = badge,
    gradientStartColorHex = gradient_start_hex ?: 0xFF2563EB,
    gradientEndColorHex = gradient_end_hex ?: 0xFF1D4ED8
)
fun HomeCategoryDto.toModel(): HomeCategoryItem = toDomain()

fun PreparationDataPointDto.toDomain(): PreparationDataPoint = PreparationDataPoint(
    dateLabel = date_label ?: label.ifBlank { "Date" },
    questionsCount = questions_count ?: value.toInt(),
    accuracyPercent = accuracy_percent ?: 75,
    timeSpentMinutes = time_spent_minutes ?: 45
)
fun PreparationDataPointDto.toModel(): PreparationDataPoint = toDomain()

fun FolderDto.toDomain(): TestSeriesFolder = TestSeriesFolder(
    id = id,
    title = title,
    testCountText = subtitle,
    freeTestsBadge = freeTestsBadge,
    isLive = isLive,
    isPYQ = isPYQ
)
fun FolderDto.toModel(): TestSeriesFolder = toDomain()

fun TestSeriesDto.toDomain(): TestSeries = TestSeries(
    id = id,
    title = title,
    subtitle = subtitle,
    categoryId = categoryId,
    totalTests = totalTests,
    fullTestsCount = fullTestsCount,
    pyqCount = pyqCount,
    attemptedCount = attemptedCount,
    vacancies = vacancies,
    examDates = examDates,
    isEnrolled = isEnrolled,
    mockFolders = mockFolders.map { it.toDomain() },
    pypFolders = pypFolders.map { it.toDomain() },
    studyNotesFolders = notes_folders?.map { it.toDomain() } ?: emptyList()
)
fun TestSeriesDto.toModel(): TestSeries = toDomain()

fun TestDto.toDomain(fallbackSeriesId: String = ""): Test = Test(
    id = id,
    seriesId = if (seriesId.isNotBlank()) seriesId else fallbackSeriesId,
    title = title,
    subCategory = subCategory,
    durationMinutes = durationMinutes,
    totalQuestions = totalQuestions,
    totalMarks = totalMarks,
    isFree = isFree,
    sections = emptyList()
)
fun TestDto.toModel(fallbackSeriesId: String = ""): Test = toDomain(fallbackSeriesId)

fun QuestionDto.toDomain(): Question = Question(
    id = id,
    sectionId = sectionId,
    questionNumber = questionNumber,
    statementText = statement,
    statementTextHindi = statementHindi,
    options = options.map { it.toDomain() },
    correctOptionIndex = correctOptionIndex,
    explanation = explanation,
    topic = topic,
    subject = subject
)
fun QuestionDto.toModel(): Question = toDomain()

fun OptionDto.toDomain(): Option = Option(
    id = id,
    text = text,
    textHindi = textHindi
)
fun OptionDto.toModel(): Option = toDomain()

fun SectionResultDto.toDomain(): SectionResult = SectionResult(
    sectionId = sectionId,
    sectionName = sectionName,
    score = score,
    correctCount = correctCount,
    incorrectCount = incorrectCount,
    unattemptedCount = unattemptedCount,
    markedCount = markedCount,
    accuracy = accuracy,
    timeTakenSeconds = timeTakenSeconds,
    totalQuestions = totalQuestions
)
fun SectionResultDto.toModel(): SectionResult = toDomain()

fun TestResultDto.toDomain(): TestResult = TestResult(
    attemptId = attemptId,
    testId = testId,
    testTitle = testTitle,
    score = score,
    totalMarks = totalMarks,
    rank = rank,
    totalCandidates = totalCandidates,
    percentile = percentile,
    accuracy = accuracy,
    correctCount = correctCount,
    incorrectCount = incorrectCount,
    unattemptedCount = unattemptedCount,
    cutoffMarks = cutoffMarks,
    averageScore = averageScore,
    bestScore = totalMarks,
    sectionBreakdowns = section_breakdowns?.map { it.toDomain() } ?: emptyList(),
    attemptDate = attemptDate
)
fun TestResultDto.toModel(): TestResult = toDomain()

fun LeaderboardEntryDto.toDomain(): LeaderboardEntry = LeaderboardEntry(
    rank = rank,
    userName = name,
    avatarUrl = avatarUrl,
    score = score,
    accuracy = accuracy
)
fun LeaderboardEntryDto.toModel(): LeaderboardEntry = toDomain()

fun UpdateItemDto.toDomain(): ExamUpdateItem = ExamUpdateItem(
    id = id,
    title = title,
    description = description,
    date = date,
    category = try {
        UpdateCategory.valueOf(category.uppercase())
    } catch (_: Exception) {
        UpdateCategory.ALL
    },
    isPinned = isPinned,
    pdfSize = pdfSize,
    actionText = actionText
)
fun UpdateItemDto.toModel(): ExamUpdateItem = toDomain()

fun UserProfileDto.toDomain(): UserProfile = UserProfile(
    id = id,
    name = name.ifBlank { "Aspirant" },
    fullName = name.ifBlank { "Aspirant" },
    mobileNumber = mobile.ifBlank { "+91 98765 43210" },
    email = email.ifBlank { "aspirant@abhyaas.edu" },
    dateOfBirth = dateOfBirth.ifBlank { "15/08/2000" },
    category = category.ifBlank { "General" },
    pinCode = pinCode.ifBlank { "462001" },
    education = education.ifBlank { "Graduation" },
    educationQualification = education.ifBlank { "Graduation" },
    avatarUrl = avatarUrl.ifEmpty { null },
    targetExam = targetExam.ifEmpty { null },
    rank = rank,
    totalTestsAttempted = totalTestsAttempted,
    accuracy = accuracy
)
fun UserProfileDto.toModel(): UserProfile = toDomain()
