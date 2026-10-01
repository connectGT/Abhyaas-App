package com.example.abhyaas.data.mock

import com.example.abhyaas.data.model.Option
import com.example.abhyaas.data.model.Question
import com.example.abhyaas.data.model.TestSection

object MockQuestionRepository {

    val sampleGIQuestions: List<Question> = listOf(
        Question(
            id = 1, sectionId = "sec_a", questionNumber = 1,
            directionText = "A statement is followed by two courses of action numbered I and II...",
            directionTextHindi = "एक कथन के बाद दो कार्रवाइयां I और II दी गई हैं...",
            statementText = "There was a spurt in criminal activities in the city...",
            statementTextHindi = "हाल ही में त्योहारी सीजन के दौरान शहर में आपराधिक गतिविधियों में तेजी आई।",
            options = listOf(
                Option(1, "I and III follow", "I और III अनुसरण करते हैं"),
                Option(2, "I and II follow", "I और II अनुसरण करते हैं"),
                Option(3, "II and III follow", "II और III अनुसरण करते हैं"),
                Option(4, "All follow", "सभी अनुसरण करते हैं")
            ),
            correctOptionIndex = 1,
            explanation = "Courses of action I and II...",
            explanationHindi = "कार्रवाई I और II...",
            positiveMarks = 2.0f, negativeMarks = 0.5f,
            topic = "Statement & Courses of Action", subject = "General Intelligence",
            percentGotRight = 64, averageTimeSeconds = 45
        ),
        Question(
            id = 2, sectionId = "sec_a", questionNumber = 2,
            statementText = "26 + 342 - 19 x 73 ÷ 14",
            options = listOf(Option(1, "481"), Option(2, "409"), Option(3, "524"), Option(4, "510")),
            correctOptionIndex = 1, explanation = "Replacing signs according to BODMAS rule and solving step by step.", topic = "Mathematical Operations", subject = "General Intelligence", percentGotRight = 72, averageTimeSeconds = 38
        )
    )

    val sampleGAQuestions: List<Question> = listOf(
        Question(
            id = 26, sectionId = "sec_b", questionNumber = 26,
            statementText = "Under the Madhya Pradesh Land Revenue Code, 1959 (भू-राजस्व संहिता), which revenue officer is the primary authority responsible for maintaining the Khasra and Field Map (भू-नक्शा)?",
            options = listOf(Option(1, "Tehsildar", "तहसीलदार"), Option(2, "Patwari", "पटवारी"), Option(3, "Revenue Inspector", "राजस्व निरीक्षक"), Option(4, "SDO")),
            correctOptionIndex = 1, explanation = "Patwari is responsible.", topic = "MP Land Revenue Code", subject = "General Awareness", percentGotRight = 78, averageTimeSeconds = 25
        ),
        Question(
            id = 27, sectionId = "sec_b", questionNumber = 27,
            statementText = "Which Article of the Constitution of India provides for the establishment and constitution of the Finance Commission?",
            options = listOf(Option(1, "Article 280", "अनुच्छेद 280"), Option(2, "Article 324"), Option(3, "Article 312"), Option(4, "Article 110")),
            correctOptionIndex = 0, explanation = "Article 280", topic = "Indian Polity", subject = "General Awareness", percentGotRight = 89, averageTimeSeconds = 20
        )
    )

    val sampleENQuestions: List<Question> = listOf(
        Question(
            id = 99, sectionId = "sec_d", questionNumber = 24,
            statementText = "An imaginary ideal society",
            options = listOf(Option(1, "Utopia"), Option(2, "Dystopia"), Option(3, "Oasis"), Option(4, "Paradise")),
            correctOptionIndex = 0, explanation = "Utopia is the correct answer for an imaginary ideal society.", topic = "Vocabulary", subject = "English Language", percentGotRight = 70, averageTimeSeconds = 20
        ),
        Question(
            id = 100, sectionId = "sec_d", questionNumber = 25,
            statementText = "Piece of cake",
            options = listOf(Option(1, "Something that is very easy to accomplish"), Option(2, "A difficult task"), Option(3, "A dessert"), Option(4, "None")),
            correctOptionIndex = 0, explanation = "This is a common English idiom meaning something is easy.", topic = "Idioms", subject = "English Language", percentGotRight = 80, averageTimeSeconds = 15
        )
    )

    fun getSectionsForTest(testId: String): List<TestSection> {
        val sectionsConfig = listOf(
            Triple("sec_a", "PART - A", "General Intelligence"),
            Triple("sec_b", "PART - B", "General Awareness"),
            Triple("sec_c", "PART - C", "Quantitative Aptitude"),
            Triple("sec_d", "PART - D", "English Language")
        )

        var questionIdCounter = 1
        return sectionsConfig.map { (secId, partName, title) ->
            val questions = (1..25).map { qIndexInSec ->
                val qNumber = (sectionsConfig.indexOfFirst { it.first == secId } * 25) + qIndexInSec
                val correctIndex = (qNumber % 4)
                val id = questionIdCounter++
                
                when (id) {
                    1 -> sampleGIQuestions[0]
                    2 -> sampleGIQuestions[1]
                    26 -> sampleGAQuestions[0]
                    27 -> sampleGAQuestions[1]
                    99 -> sampleENQuestions[0]
                    100 -> sampleENQuestions[1]
                    else -> Question(
                        id = id,
                        sectionId = secId,
                        questionNumber = qNumber,
                        statementText = "Question $qNumber: Test question in $title on topic #${(qNumber % 5) + 1}",
                        statementTextHindi = "प्रश्न $qNumber: $title में विषय #${(qNumber % 5) + 1} पर परीक्षण प्रश्न",
                        options = listOf(
                            Option(1, "Option A for Q$qNumber", "विकल्प क"),
                            Option(2, "Option B for Q$qNumber", "विकल्प ख"),
                            Option(3, "Option C for Q$qNumber", "विकल्प ग"),
                            Option(4, "Option D for Q$qNumber", "विकल्प घ")
                        ),
                        correctOptionIndex = correctIndex,
                        explanation = "Detailed solution for question $qNumber in $title. Correct option is ${correctIndex + 1}.",
                        explanationHindi = "प्रश्न $qNumber का विस्तृत हल। सही विकल्प ${correctIndex + 1} है।",
                        positiveMarks = 2.0f,
                        negativeMarks = 0.5f,
                        topic = "Topic ${(qNumber % 5) + 1}",
                        subject = title,
                        percentGotRight = 60 + (qNumber % 30),
                        averageTimeSeconds = 30 + (qNumber % 25)
                    )
                }
            }
            TestSection(
                id = secId,
                partName = partName,
                title = title,
                titleHindi = when (secId) {
                    "sec_a" -> "सामान्य बुद्धिमत्ता"
                    "sec_b" -> "सामान्य जागरूकता"
                    "sec_c" -> "मात्रात्मक योग्यता"
                    else -> "अंग्रेजी भाषा"
                },
                questions = questions
            )
        }
    }

    fun getQuestionsForTest(testId: String): List<Question> {
        return getSectionsForTest(testId).flatMap { it.questions }
    }

    fun getQuestionById(id: Int): Question? {
        if (id !in 1..100) return null
        return getQuestionsForTest("default").find { it.id == id }
    }
    
    // Kept to avoid breaking MockQuestionRepositoryImpl.kt if it uses it directly (even though we saw it didn't)
    fun getTehsildarSections(): List<TestSection> = emptyList()
    fun getMpsebSections(): List<TestSection> = emptyList()
    fun getTehsildarQuestions(): List<Question> = emptyList()
    fun getMpsebQuestions(): List<Question> = emptyList()
}
