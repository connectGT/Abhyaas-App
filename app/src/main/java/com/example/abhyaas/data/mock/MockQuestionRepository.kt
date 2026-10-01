package com.example.abhyaas.data.mock

import com.example.abhyaas.data.model.Option
import com.example.abhyaas.data.model.Question
import com.example.abhyaas.data.model.TestSection

object MockQuestionRepository {

    // ─── Section Definitions ──────────────────────────────────────────────────

    fun getTehsildarSections(): List<TestSection> {
        val questions = getTehsildarQuestions()
        return listOf(
            TestSection(
                id = "teh_sec_a",
                partName = "Paper 1 — Part A",
                title = "Samanya Gyan (GK)",
                titleHindi = "सामान्य ज्ञान",
                questions = questions.filter { it.sectionId == "teh_sec_a" }
            ),
            TestSection(
                id = "teh_sec_b",
                partName = "Paper 1 — Part B",
                title = "Reasoning (Tarkshakti)",
                titleHindi = "तर्कशक्ति",
                questions = questions.filter { it.sectionId == "teh_sec_b" }
            )
        )
    }

    fun getMpsebSections(): List<TestSection> {
        val questions = getMpsebQuestions()
        return listOf(
            TestSection(
                id = "mpseb_sec_a",
                partName = "Part A",
                title = "General Knowledge",
                questions = questions.filter { it.sectionId == "mpseb_sec_a" }
            ),
            TestSection(
                id = "mpseb_sec_b",
                partName = "Part B",
                title = "Reasoning",
                questions = questions.filter { it.sectionId == "mpseb_sec_b" }
            ),
            TestSection(
                id = "mpseb_sec_c",
                partName = "Part C",
                title = "Technical Knowledge",
                questions = questions.filter { it.sectionId == "mpseb_sec_c" }
            )
        )
    }

    fun getSectionsForTest(testId: String): List<TestSection> = getTehsildarSections()

    fun getQuestionsForTest(testId: String): List<Question> {
        return if (testId.contains("mpseb")) getMpsebQuestions()
        else getTehsildarQuestions()
    }

    // ─── Nayab Tehsildar Questions ────────────────────────────────────────────

    fun getTehsildarQuestions(): List<Question> {
        val list = mutableListOf<Question>()

        // Section A: Samanya Gyan (GK) — Q1 to Q50
        list.add(Question(
            id = 1, sectionId = "teh_sec_a", questionNumber = 1,
            statementText = "मध्यप्रदेश में भू-राजस्व संहिता किस वर्ष लागू हुई?",
            options = listOf(Option(1,"1959"), Option(2,"1954"), Option(3,"1972"), Option(4,"1985")),
            correctOptionIndex = 0,
            explanation = "MP Land Revenue Code (भू-राजस्व संहिता) 1959 में लागू हुई।",
            topic = "MP Land Revenue Code", subject = "Samanya Gyan (GK)", percentGotRight = 72, averageTimeSeconds = 28
        ))
        list.add(Question(
            id = 2, sectionId = "teh_sec_a", questionNumber = 2,
            statementText = "तहसीलदार की नियुक्ति किसके द्वारा की जाती है?",
            options = listOf(Option(1,"राज्यपाल"), Option(2,"कलेक्टर"), Option(3,"मुख्यमंत्री"), Option(4,"जिला पंचायत")),
            correctOptionIndex = 0,
            explanation = "तहसीलदार की नियुक्ति राज्यपाल द्वारा राजस्व सेवा नियमों के अंतर्गत की जाती है।",
            topic = "Revenue Administration", subject = "Samanya Gyan (GK)", percentGotRight = 68, averageTimeSeconds = 32
        ))
        list.add(Question(
            id = 3, sectionId = "teh_sec_a", questionNumber = 3,
            statementText = "मध्यप्रदेश की राजधानी क्या है?",
            options = listOf(Option(1,"भोपाल"), Option(2,"इंदौर"), Option(3,"जबलपुर"), Option(4,"ग्वालियर")),
            correctOptionIndex = 0,
            explanation = "1. मध्यप्रदेश की राजधानी भोपाल है।\n2. इसे झीलों की नगरी (City of Lakes) भी कहा जाता है।\n3. यह भारत के सबसे हरे-भरे शहरों में से एक है।\n\nअतः सही उत्तर विकल्प 1 (भोपाल) है।",
            topic = "MP General Knowledge", subject = "Samanya Gyan (GK)", percentGotRight = 95, averageTimeSeconds = 12
        ))
        list.add(Question(
            id = 4, sectionId = "teh_sec_a", questionNumber = 4,
            statementText = "भारतीय संविधान में भूमि अधिग्रहण किस अनुसूची में आता है?",
            options = listOf(Option(1,"सातवीं अनुसूची"), Option(2,"तीसरी अनुसूची"), Option(3,"नवीं अनुसूची"), Option(4,"पांचवीं अनुसूची")),
            correctOptionIndex = 2,
            explanation = "भूमि अधिग्रहण और भू-राजस्व संबंधी विषय संविधान की नवीं अनुसूची में सूचीबद्ध हैं।",
            topic = "Indian Constitution", subject = "Samanya Gyan (GK)", percentGotRight = 55, averageTimeSeconds = 42
        ))
        list.add(Question(
            id = 5, sectionId = "teh_sec_a", questionNumber = 5,
            statementText = "नर्मदा नदी का उद्गम स्थल कहाँ है?",
            options = listOf(Option(1,"अमरकंटक"), Option(2,"बेतवा"), Option(3,"महू"), Option(4,"रीवा")),
            correctOptionIndex = 0,
            explanation = "नर्मदा नदी का उद्गम अमरकंटक (अनूपपुर जिला, MP) से होता है।",
            topic = "MP Geography", subject = "Samanya Gyan (GK)", percentGotRight = 88, averageTimeSeconds = 18
        ))
        list.add(Question(
            id = 6, sectionId = "teh_sec_a", questionNumber = 6,
            statementText = "खसरा किससे संबंधित है?",
            options = listOf(Option(1,"भूमि अभिलेख"), Option(2,"जनगणना रिकॉर्ड"), Option(3,"पशुपालन रजिस्टर"), Option(4,"वन विभाग रिकॉर्ड")),
            correctOptionIndex = 0,
            explanation = "खसरा एक भूमि अभिलेख है जिसमें प्रत्येक खेत का विवरण होता है।",
            topic = "Revenue Records", subject = "Samanya Gyan (GK)", percentGotRight = 80, averageTimeSeconds = 25
        ))
        list.add(Question(
            id = 7, sectionId = "teh_sec_a", questionNumber = 7,
            statementText = "मध्यप्रदेश में कितने जिले हैं?",
            options = listOf(Option(1,"52"), Option(2,"48"), Option(3,"55"), Option(4,"50")),
            correctOptionIndex = 3,
            explanation = "वर्तमान में मध्यप्रदेश में 50 जिले हैं।",
            topic = "MP General Knowledge", subject = "Samanya Gyan (GK)", percentGotRight = 74, averageTimeSeconds = 20
        ))
        list.add(Question(
            id = 8, sectionId = "teh_sec_a", questionNumber = 8,
            statementText = "पटवारी का कार्यक्षेत्र किसे कहते हैं?",
            options = listOf(Option(1,"पटवारी हल्का"), Option(2,"तहसील"), Option(3,"परगना"), Option(4,"तालुका")),
            correctOptionIndex = 0,
            explanation = "पटवारी के कार्यक्षेत्र को 'पटवारी हल्का' कहा जाता है।",
            topic = "Revenue Administration", subject = "Samanya Gyan (GK)", percentGotRight = 82, averageTimeSeconds = 22
        ))
        list.add(Question(
            id = 9, sectionId = "teh_sec_a", questionNumber = 9,
            statementText = "भारत का सबसे बड़ा राज्य (क्षेत्रफल में) कौन सा है?",
            options = listOf(Option(1,"राजस्थान"), Option(2,"मध्यप्रदेश"), Option(3,"उत्तरप्रदेश"), Option(4,"महाराष्ट्र")),
            correctOptionIndex = 0,
            explanation = "क्षेत्रफल की दृष्टि से राजस्थान भारत का सबसे बड़ा राज्य है।",
            topic = "Indian Geography", subject = "Samanya Gyan (GK)", percentGotRight = 78, averageTimeSeconds = 18
        ))
        list.add(Question(
            id = 10, sectionId = "teh_sec_a", questionNumber = 10,
            statementText = "मध्यप्रदेश में 'अधिकार अभिलेख' को क्या कहते हैं?",
            options = listOf(Option(1,"खसरा/खतौनी"), Option(2,"नकल"), Option(3,"फर्द"), Option(4,"पट्टा")),
            correctOptionIndex = 0,
            explanation = "अधिकार अभिलेख को सामान्यतः खसरा (प्रत्येक खेत का विवरण) और खतौनी (व्यक्तिवार विवरण) कहते हैं।",
            topic = "Revenue Records", subject = "Samanya Gyan (GK)", percentGotRight = 76, averageTimeSeconds = 28
        ))
        list.add(Question(
            id = 11, sectionId = "teh_sec_a", questionNumber = 11,
            statementText = "मध्यप्रदेश का राज्य पशु कौन सा है?",
            options = listOf(Option(1,"बारहसिंगा"), Option(2,"चीतल"), Option(3,"तेंदुआ"), Option(4,"बाघ")),
            correctOptionIndex = 0,
            explanation = "मध्यप्रदेश का राज्य पशु बारहसिंगा (Swamp Deer) है।",
            topic = "MP State Symbols", subject = "Samanya Gyan (GK)", percentGotRight = 70, averageTimeSeconds = 22
        ))
        list.add(Question(
            id = 12, sectionId = "teh_sec_a", questionNumber = 12,
            statementText = "भारतीय संविधान का कौन सा अनुच्छेद भूमि सुधार से संबंधित है?",
            options = listOf(Option(1,"अनुच्छेद 31A"), Option(2,"अनुच्छेद 19"), Option(3,"अनुच्छेद 21"), Option(4,"अनुच्छेद 32")),
            correctOptionIndex = 0,
            explanation = "अनुच्छेद 31A भूमि अधिग्रहण और जमींदारी उन्मूलन के कानूनों को संवैधानिक संरक्षण देता है।",
            topic = "Indian Constitution", subject = "Samanya Gyan (GK)", percentGotRight = 58, averageTimeSeconds = 38
        ))
        list.add(Question(
            id = 13, sectionId = "teh_sec_a", questionNumber = 13,
            statementText = "मध्यप्रदेश का कौनसा शहर 'मिनी मुंबई' के नाम से जाना जाता है?",
            options = listOf(Option(1,"इंदौर"), Option(2,"भोपाल"), Option(3,"जबलपुर"), Option(4,"उज्जैन")),
            correctOptionIndex = 0,
            explanation = "इंदौर को व्यापार और उद्योग के केंद्र होने के कारण 'मिनी मुंबई' कहा जाता है।",
            topic = "MP General Knowledge", subject = "Samanya Gyan (GK)", percentGotRight = 85, averageTimeSeconds = 15
        ))
        list.add(Question(
            id = 14, sectionId = "teh_sec_a", questionNumber = 14,
            statementText = "भूमि अधिग्रहण किस अधिनियम के तहत किया जाता है?",
            options = listOf(Option(1,"RFCTLARR Act 2013"), Option(2,"Land Reforms Act 1950"), Option(3,"Forest Act 1927"), Option(4,"PESA Act 1996")),
            correctOptionIndex = 0,
            explanation = "Right to Fair Compensation and Transparency in Land Acquisition Act, 2013 के तहत भूमि अधिग्रहण होता है।",
            topic = "Land Acquisition", subject = "Samanya Gyan (GK)", percentGotRight = 62, averageTimeSeconds = 35
        ))
        list.add(Question(
            id = 15, sectionId = "teh_sec_a", questionNumber = 15,
            statementText = "मध्यप्रदेश का स्थापना दिवस कब मनाया जाता है?",
            options = listOf(Option(1,"1 नवंबर"), Option(2,"26 जनवरी"), Option(3,"15 अगस्त"), Option(4,"2 अक्टूबर")),
            correctOptionIndex = 0,
            explanation = "मध्यप्रदेश का स्थापना दिवस 1 नवंबर को मनाया जाता है (1956 में राज्य पुनर्गठन के बाद)।",
            topic = "MP History", subject = "Samanya Gyan (GK)", percentGotRight = 90, averageTimeSeconds = 15
        ))
        list.add(Question(
            id = 16, sectionId = "teh_sec_a", questionNumber = 16,
            statementText = "नायब तहसीलदार का तत्काल वरिष्ठ अधिकारी कौन होता है?",
            options = listOf(Option(1,"तहसीलदार"), Option(2,"कलेक्टर"), Option(3,"SDM"), Option(4,"पटवारी")),
            correctOptionIndex = 0,
            explanation = "नायब तहसीलदार का सीधा वरिष्ठ अधिकारी तहसीलदार होता है।",
            topic = "Revenue Administration", subject = "Samanya Gyan (GK)", percentGotRight = 86, averageTimeSeconds = 18
        ))
        list.add(Question(
            id = 17, sectionId = "teh_sec_a", questionNumber = 17,
            statementText = "भारत में पंचायती राज व्यवस्था को संवैधानिक दर्जा किस संशोधन द्वारा मिला?",
            options = listOf(Option(1,"73वाँ संशोधन"), Option(2,"74वाँ संशोधन"), Option(3,"44वाँ संशोधन"), Option(4,"86वाँ संशोधन")),
            correctOptionIndex = 0,
            explanation = "73वें संविधान संशोधन (1992) द्वारा पंचायती राज को संवैधानिक दर्जा मिला।",
            topic = "Indian Constitution", subject = "Samanya Gyan (GK)", percentGotRight = 80, averageTimeSeconds = 22
        ))
        list.add(Question(
            id = 18, sectionId = "teh_sec_a", questionNumber = 18,
            statementText = "मध्यप्रदेश का उच्च न्यायालय कहाँ स्थित है?",
            options = listOf(Option(1,"जबलपुर"), Option(2,"भोपाल"), Option(3,"इंदौर"), Option(4,"ग्वालियर")),
            correctOptionIndex = 0,
            explanation = "मध्यप्रदेश उच्च न्यायालय जबलपुर में स्थित है। इंदौर और ग्वालियर में खंडपीठें हैं।",
            topic = "MP General Knowledge", subject = "Samanya Gyan (GK)", percentGotRight = 88, averageTimeSeconds = 16
        ))
        list.add(Question(
            id = 19, sectionId = "teh_sec_a", questionNumber = 19,
            statementText = "भू-राजस्व (Land Revenue) का प्राथमिक आधार क्या है?",
            options = listOf(Option(1,"भूमि की उत्पादकता"), Option(2,"जनसंख्या"), Option(3,"सिंचाई"), Option(4,"फसल का बाजार मूल्य")),
            correctOptionIndex = 0,
            explanation = "भू-राजस्व का आकलन मुख्यतः भूमि की उत्पादकता (Soil Productivity) के आधार पर किया जाता है।",
            topic = "Revenue Administration", subject = "Samanya Gyan (GK)", percentGotRight = 66, averageTimeSeconds = 30
        ))
        list.add(Question(
            id = 20, sectionId = "teh_sec_a", questionNumber = 20,
            statementText = "मध्यप्रदेश का सबसे बड़ा जिला (क्षेत्रफल में) कौन सा है?",
            options = listOf(Option(1,"छिंदवाड़ा"), Option(2,"बालाघाट"), Option(3,"सागर"), Option(4,"रीवा")),
            correctOptionIndex = 0,
            explanation = "क्षेत्रफल की दृष्टि से छिंदवाड़ा मध्यप्रदेश का सबसे बड़ा जिला है।",
            topic = "MP Geography", subject = "Samanya Gyan (GK)", percentGotRight = 72, averageTimeSeconds = 25
        ))

        // Q21–Q50: Placeholder GK questions
        for (i in 21..50) {
            list.add(Question(
                id = i, sectionId = "teh_sec_a", questionNumber = i,
                statementText = "सामान्य ज्ञान प्रश्न ${i}: MP भू-राजस्व / MP GK संबंधी प्रश्न",
                options = listOf(Option(1,"विकल्प A"), Option(2,"विकल्प B"), Option(3,"विकल्प C"), Option(4,"विकल्प D")),
                correctOptionIndex = 0,
                explanation = "यह MP Nayab Tehsildar परीक्षा के GK अनुभाग का प्रश्न है।",
                topic = "General Knowledge", subject = "Samanya Gyan (GK)", percentGotRight = 70, averageTimeSeconds = 25
            ))
        }

        // Section B: Reasoning — Q51 to Q100
        list.add(Question(
            id = 51, sectionId = "teh_sec_b", questionNumber = 1,
            statementText = "यदि MANGO को OCPIQ लिखा जाए, तो APPLE को कैसे लिखेंगे?",
            options = listOf(Option(1,"CRRNF"), Option(2,"CRRNG"), Option(3,"DRRNG"), Option(4,"BQQMF")),
            correctOptionIndex = 1,
            explanation = "प्रत्येक अक्षर +2: A→C, P→R, P→R, L→N, E→G = CRRNG",
            topic = "Coding-Decoding", subject = "Reasoning (Tarkshakti)", percentGotRight = 72, averageTimeSeconds = 30
        ))
        list.add(Question(
            id = 52, sectionId = "teh_sec_b", questionNumber = 2,
            statementText = "श्रृंखला में अगला पद बताइए: 2, 6, 12, 20, 30, ?",
            options = listOf(Option(1,"42"), Option(2,"40"), Option(3,"44"), Option(4,"38")),
            correctOptionIndex = 0,
            explanation = "n(n+1) श्रृंखला: 1×2=2, 2×3=6, 3×4=12, 4×5=20, 5×6=30, 6×7=42",
            topic = "Number Series", subject = "Reasoning (Tarkshakti)", percentGotRight = 78, averageTimeSeconds = 28
        ))
        list.add(Question(
            id = 53, sectionId = "teh_sec_b", questionNumber = 3,
            statementText = "दिए गए विकल्पों में से विषम को चुनिए:\nपटवारी, तहसीलदार, कलेक्टर, मुख्यमंत्री",
            options = listOf(Option(1,"पटवारी"), Option(2,"तहसीलदार"), Option(3,"कलेक्टर"), Option(4,"मुख्यमंत्री")),
            correctOptionIndex = 3,
            explanation = "पटवारी, तहसीलदार और कलेक्टर राजस्व विभाग के अधिकारी हैं। मुख्यमंत्री एक राजनीतिक पद है।",
            topic = "Odd One Out", subject = "Reasoning (Tarkshakti)", percentGotRight = 88, averageTimeSeconds = 15
        ))
        list.add(Question(
            id = 54, sectionId = "teh_sec_b", questionNumber = 4,
            statementText = "एक व्यक्ति उत्तर में 5 किमी, फिर दाएं 3 किमी, फिर दाएं 5 किमी चलता है। वह प्रारंभिक स्थान से कितनी दूर है?",
            options = listOf(Option(1,"3 किमी"), Option(2,"5 किमी"), Option(3,"8 किमी"), Option(4,"13 किमी")),
            correctOptionIndex = 0,
            explanation = "उत्तर 5 → पूर्व 3 → दक्षिण 5। वह प्रारंभिक बिंदु से 3 किमी पूर्व में है।",
            topic = "Direction Sense", subject = "Reasoning (Tarkshakti)", percentGotRight = 76, averageTimeSeconds = 32
        ))
        list.add(Question(
            id = 55, sectionId = "teh_sec_b", questionNumber = 5,
            statementText = "यदि '+' का अर्थ '÷', '−' का अर्थ '×', '×' का अर्थ '+', '÷' का अर्थ '−' हो, तो\n16 + 4 − 3 × 2 ÷ 1 = ?",
            options = listOf(Option(1,"13"), Option(2,"14"), Option(3,"11"), Option(4,"15")),
            correctOptionIndex = 0,
            explanation = "16÷4×3+2−1 = 4×3+2−1 = 12+2−1 = 13",
            topic = "Mathematical Operations", subject = "Reasoning (Tarkshakti)", percentGotRight = 60, averageTimeSeconds = 48
        ))

        // Q56–Q100: Placeholder Reasoning questions
        for (i in 56..100) {
            list.add(Question(
                id = i, sectionId = "teh_sec_b", questionNumber = i - 50,
                statementText = "तर्कशक्ति प्रश्न ${i - 50}: MP Tehsildar Exam",
                options = listOf(Option(1,"विकल्प A"), Option(2,"विकल्प B"), Option(3,"विकल्प C"), Option(4,"विकल्प D")),
                correctOptionIndex = 0,
                explanation = "यह MP Nayab Tehsildar परीक्षा के Reasoning अनुभाग का प्रश्न है।",
                topic = "Reasoning", subject = "Reasoning (Tarkshakti)", percentGotRight = 68, averageTimeSeconds = 28
            ))
        }

        return list
    }

    // ─── MPSEB Questions ──────────────────────────────────────────────────────

    fun getMpsebQuestions(): List<Question> {
        val list = mutableListOf<Question>()

        // Section A: GK (Q1-Q25)
        list.add(Question(
            id = 1, sectionId = "mpseb_sec_a", questionNumber = 1,
            statementText = "MPSEB का पूरा नाम क्या है?",
            options = listOf(Option(1,"Madhya Pradesh State Electricity Board"), Option(2,"MP State Engineering Board"), Option(3,"MP South Electric Board"), Option(4,"None of these")),
            correctOptionIndex = 0,
            explanation = "MPSEB — Madhya Pradesh State Electricity Board (मध्यप्रदेश राज्य विद्युत मंडल)",
            topic = "MPSEB General", subject = "General Knowledge", percentGotRight = 92, averageTimeSeconds = 12
        ))
        list.add(Question(
            id = 2, sectionId = "mpseb_sec_a", questionNumber = 2,
            statementText = "विद्युत धारा (Current) की SI इकाई क्या है?",
            options = listOf(Option(1,"एम्पियर (Ampere)"), Option(2,"वोल्ट (Volt)"), Option(3,"ओम (Ohm)"), Option(4,"वाट (Watt)")),
            correctOptionIndex = 0,
            explanation = "विद्युत धारा (Current) की SI इकाई एम्पियर (A) है।",
            topic = "Basic Electricity", subject = "General Knowledge", percentGotRight = 85, averageTimeSeconds = 18
        ))
        list.add(Question(
            id = 3, sectionId = "mpseb_sec_a", questionNumber = 3,
            statementText = "ओम का नियम क्या है?",
            options = listOf(Option(1,"V = IR"), Option(2,"P = IV"), Option(3,"I = P/V"), Option(4,"R = P/I²")),
            correctOptionIndex = 0,
            explanation = "ओम के नियमानुसार V = I × R, जहाँ V = वोल्टेज, I = धारा, R = प्रतिरोध।",
            topic = "Basic Electricity", subject = "General Knowledge", percentGotRight = 88, averageTimeSeconds = 15
        ))
        list.add(Question(
            id = 4, sectionId = "mpseb_sec_a", questionNumber = 4,
            statementText = "एक 100W बल्ब को 10 घंटे जलाने पर कितनी यूनिट बिजली खर्च होगी?",
            options = listOf(Option(1,"1 यूनिट"), Option(2,"10 यूनिट"), Option(3,"0.1 यूनिट"), Option(4,"100 यूनिट")),
            correctOptionIndex = 0,
            explanation = "Energy = Power × Time = 100W × 10h = 1000 Wh = 1 kWh = 1 यूनिट",
            topic = "Electrical Calculations", subject = "General Knowledge", percentGotRight = 82, averageTimeSeconds = 22
        ))
        list.add(Question(
            id = 5, sectionId = "mpseb_sec_a", questionNumber = 5,
            statementText = "ट्रांसफार्मर किस सिद्धांत पर कार्य करता है?",
            options = listOf(Option(1,"विद्युत चुम्बकीय प्रेरण"), Option(2,"प्रकाश विद्युत प्रभाव"), Option(3,"ऊष्मा प्रभाव"), Option(4,"रासायनिक प्रभाव")),
            correctOptionIndex = 0,
            explanation = "ट्रांसफार्मर विद्युत चुम्बकीय प्रेरण (Electromagnetic Induction) के सिद्धांत पर कार्य करता है।",
            topic = "Transformers", subject = "General Knowledge", percentGotRight = 80, averageTimeSeconds = 25
        ))

        for (i in 6..25) {
            list.add(Question(
                id = i, sectionId = "mpseb_sec_a", questionNumber = i,
                statementText = "MPSEB GK Question $i",
                options = listOf(Option(1,"Option A"), Option(2,"Option B"), Option(3,"Option C"), Option(4,"Option D")),
                correctOptionIndex = 0,
                explanation = "This is MPSEB General Knowledge question $i.",
                topic = "General Knowledge", subject = "General Knowledge", percentGotRight = 72, averageTimeSeconds = 25
            ))
        }

        // Section B: Reasoning (Q26-Q50)
        for (i in 26..50) {
            list.add(Question(
                id = i, sectionId = "mpseb_sec_b", questionNumber = i - 25,
                statementText = "MPSEB Reasoning Question ${i - 25}",
                options = listOf(Option(1,"Option A"), Option(2,"Option B"), Option(3,"Option C"), Option(4,"Option D")),
                correctOptionIndex = 0,
                explanation = "This is MPSEB Reasoning question ${i - 25}.",
                topic = "Reasoning", subject = "Reasoning", percentGotRight = 68, averageTimeSeconds = 30
            ))
        }

        // Section C: Technical (Q51-Q100)
        for (i in 51..100) {
            list.add(Question(
                id = i, sectionId = "mpseb_sec_c", questionNumber = i - 50,
                statementText = "MPSEB Technical Question ${i - 50}",
                options = listOf(Option(1,"Option A"), Option(2,"Option B"), Option(3,"Option C"), Option(4,"Option D")),
                correctOptionIndex = 0,
                explanation = "This is MPSEB Technical Knowledge question ${i - 50}.",
                topic = "Technical Knowledge", subject = "Technical Knowledge", percentGotRight = 65, averageTimeSeconds = 35
            ))
        }

        return list
    }
}
