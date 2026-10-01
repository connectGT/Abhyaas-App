package com.example.abhyaas.data.repository.impl

import com.example.abhyaas.data.model.*
import com.example.abhyaas.data.repository.QuestionRepository
import kotlinx.coroutines.delay

class MockQuestionRepositoryImpl : QuestionRepository {
    private val allQuestions = mutableListOf<Question>()

    init {

        allQuestions.add(Question(
            id = 1,
            sectionId = "sec_a",
            questionNumber = 1,
            statementText = """Q1. Identify the next number in the series: 2, 6, 12, 20, ?""",
            statementTextHindi = """प्रश्न 1. श्रृंखला में अगली संख्या पहचानें: 2, 6, 12, 20, ?""",
            options = listOf(Option(1, "24"), Option(2, "28"), Option(3, "30"), Option(4, "32")),
            correctOptionIndex = 2,
            explanation = """Solution: The series follows the pattern: 1x2=2, 2x3=6, 3x4=12, 4x5=20. The next term is 5x6=30.""",
            explanationHindi = """समाधान: यह श्रृंखला इस पैटर्न का पालन करती है: 1x2=2, 2x3=6, 3x4=12, 4x5=20। अगला पद 5x6=30 है।
Expert Advice: Master basic multiplication series and differences between consecutive numbers for quick logical reasoning.
विशेषज्ञ की सलाह: त्वरित तार्किक तर्क के लिए बुनियादी गुणा श्रृंखला और लगातार संख्याओं के बीच के अंतर में महारत हासिल करें।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 0,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 2,
            sectionId = "sec_a",
            questionNumber = 2,
            statementText = """Q2. Who is known to have founded the historical city of Gwalior?""",
            statementTextHindi = """प्रश्न 2. ऐतिहासिक शहर ग्वालियर की स्थापना किसने की थी?""",
            options = listOf(Option(1, "Raja Man Singh Tomar"), Option(2, "Suraj Sen"), Option(3, "Mahadji Scindia"), Option(4, "Tatya Tope")),
            correctOptionIndex = 1,
            explanation = """Solution: Gwalior was founded by King Suraj Sen in the 8th century, named after the sage Gwalipa who cured his leprosy.""",
            explanationHindi = """समाधान: ग्वालियर की स्थापना 8वीं शताब्दी में राजा सूरज सेन ने की थी, जिसका नाम ऋषि ग्वालिपा के नाम पर रखा गया था जिन्होंने उनके कुष्ठ रोग को ठीक किया था।
Expert Advice: Local history of MP, especially concerning historical capitals like Gwalior, Indore, and Bhopal, is crucial for Paper 1.
विशेषज्ञ की सलाह: एमपी का स्थानीय इतिहास, विशेष रूप से ग्वालियर, इंदौर और भोपाल जैसी ऐतिहासिक राजधानियों के संबंध में, पेपर 1 के लिए महत्वपूर्ण है।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 7,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 3,
            sectionId = "sec_a",
            questionNumber = 3,
            statementText = """Q3. Which of the following Sandhi (Join) is present in the word 'Suryoday' (सूर्योदय)?""",
            statementTextHindi = """प्रश्न 3. 'सूर्योदय' शब्द में निम्नलिखित में से कौन सी संधि है?""",
            options = listOf(Option(1, "Dirgha Sandhi"), Option(2, "Guna Sandhi"), Option(3, "Vriddhi Sandhi"), Option(4, "Yan Sandhi")),
            correctOptionIndex = 1,
            explanation = """Solution: Suryoday is formed by Surya + Uday (अ + उ = ओ), which is a classic example of Guna Sandhi.""",
            explanationHindi = """समाधान: सूर्योदय, सूर्य + उदय (अ + उ = ओ) से बना है, जो गुण संधि का एक उत्कृष्ट उदाहरण है।
Expert Advice: Practice the basic rules of Swar Sandhi (Vowel Sandhi) as at least 1-2 questions are standard in General Hindi.
विशेषज्ञ की सलाह: स्वर संधि के बुनियादी नियमों का अभ्यास करें क्योंकि सामान्य हिंदी में कम से कम 1-2 प्रश्न मानक होते हैं।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 14,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 4,
            sectionId = "sec_a",
            questionNumber = 4,
            statementText = """Q4. Choose the correct preposition: 'She is very good ___ mathematics.'""",
            statementTextHindi = """प्रश्न 4. सही पूर्वसर्ग (preposition) चुनें: 'She is very good ___ mathematics.'""",
            options = listOf(Option(1, "in"), Option(2, "with"), Option(3, "at"), Option(4, "about")),
            correctOptionIndex = 2,
            explanation = """Solution: The correct preposition to use with 'good' when talking about skills or abilities is 'at' (good at mathematics).""",
            explanationHindi = """समाधान: कौशल या क्षमताओं के बारे में बात करते समय 'good' के साथ उपयोग करने के लिए सही प्रीपोज़िशन 'at' है (good at mathematics)।
Expert Advice: Prepositions with fixed adjectives (good at, afraid of, interested in) are highly tested in MP exams.
विशेषज्ञ की सलाह: निश्चित विशेषणों (good at, afraid of, interested in) वाले पूर्वसर्गों का एमपी परीक्षाओं में अत्यधिक परीक्षण किया जाता है।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 21,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 5,
            sectionId = "sec_a",
            questionNumber = 5,
            statementText = """Q5. If a shopkeeper sells an item for Rs 120 and makes a 20% profit, what was the cost price of the item?""",
            statementTextHindi = """प्रश्न 5. यदि कोई दुकानदार किसी वस्तु को 120 रुपये में बेचता है और 20% का लाभ कमाता है, तो वस्तु का क्रय मूल्य क्या था?""",
            options = listOf(Option(1, "Rs 96"), Option(2, "Rs 100"), Option(3, "Rs 108"), Option(4, "Rs 144")),
            correctOptionIndex = 1,
            explanation = """Solution: Selling Price (SP) = Cost Price (CP) + 20% of CP. 120 = 1.2 * CP. Therefore, CP = 120 / 1.2 = 100.""",
            explanationHindi = """समाधान: विक्रय मूल्य (SP) = क्रय मूल्य (CP) + CP का 20%। 120 = 1.2 * CP. इसलिए, CP = 120 / 1.2 = 100.
Expert Advice: Memorize fraction equivalents of percentages (20% = 1/5) to solve Profit & Loss questions mentally.
विशेषज्ञ की सलाह: लाभ और हानि के प्रश्नों को मानसिक रूप से हल करने के लिए प्रतिशत के अंश समतुल्य (20% = 1/5) याद रखें।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 28,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 6,
            sectionId = "sec_a",
            questionNumber = 6,
            statementText = """Q6. Which vitamin is scientifically known as Ascorbic Acid?""",
            statementTextHindi = """प्रश्न 6. किस विटामिन को वैज्ञानिक रूप से एस्कॉर्बिक एसिड के रूप में जाना जाता है?""",
            options = listOf(Option(1, "Vitamin A"), Option(2, "Vitamin B12"), Option(3, "Vitamin C"), Option(4, "Vitamin D")),
            correctOptionIndex = 2,
            explanation = """Solution: Vitamin C is known as Ascorbic Acid. Its deficiency causes Scurvy.""",
            explanationHindi = """समाधान: विटामिन C को एस्कॉर्बिक एसिड के रूप में जाना जाता है। इसकी कमी से स्कर्वी रोग होता है।
Expert Advice: Learn the chemical names and deficiency diseases for all essential vitamins (A, B-complex, C, D, E, K).
विशेषज्ञ की सलाह: सभी आवश्यक विटामिनों (A, B-कॉम्प्लेक्स, C, D, E, K) के रासायनिक नाम और कमी से होने वाले रोगों को याद करें।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 35,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 7,
            sectionId = "sec_a",
            questionNumber = 7,
            statementText = """Q7. In computer terminology, what is the full form of RAM?""",
            statementTextHindi = """प्रश्न 7. कंप्यूटर शब्दावली में RAM का पूर्ण रूप क्या है?""",
            options = listOf(Option(1, "Read Access Memory"), Option(2, "Random Access Memory"), Option(3, "Run Access Memory"), Option(4, "Random Arithmetic Memory")),
            correctOptionIndex = 1,
            explanation = """Solution: RAM stands for Random Access Memory. It is the primary volatile memory used by the computer to store data temporarily.""",
            explanationHindi = """समाधान: RAM का मतलब रैंडम एक्सेस मेमोरी है। यह कंप्यूटर द्वारा डेटा को अस्थायी रूप से संग्रहीत करने के लिए उपयोग की जाने वाली प्राथमिक अस्थिर (volatile) मेमोरी है।
Expert Advice: Basic hardware components and their abbreviations are a staple of the General Computer Knowledge section.
विशेषज्ञ की सलाह: बुनियादी हार्डवेयर घटक और उनके संक्षिप्त नाम सामान्य कंप्यूटर ज्ञान अनुभाग का एक प्रमुख हिस्सा हैं।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 42,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 8,
            sectionId = "sec_a",
            questionNumber = 8,
            statementText = """Q8. Under the MP Civil Services (Leave) Rules, 1977, what is the maximum number of days of Earned Leave (EL) that can be accumulated by a government servant?""",
            statementTextHindi = """प्रश्न 8. म.प्र. सिविल सेवा (अवकाश) नियम, 1977 के तहत, किसी सरकारी सेवक द्वारा अधिकतम कितने दिनों का अर्जित अवकाश (EL) संचित किया जा सकता है?""",
            options = listOf(Option(1, "180 days"), Option(2, "240 days"), Option(3, "300 days"), Option(4, "365 days")),
            correctOptionIndex = 2,
            explanation = """Solution: A state government employee in MP can accumulate a maximum of 300 days of Earned Leave in their service tenure, which can be encashed at retirement.""",
            explanationHindi = """समाधान: एमपी में एक राज्य सरकार का कर्मचारी अपने सेवा कार्यकाल में अधिकतम 300 दिनों का अर्जित अवकाश संचित कर सकता है, जिसे सेवानिवृत्ति पर भुनाया जा सकता है।
Expert Advice: Read the MP Leave Rules carefully, particularly regarding EL, Casual Leave (CL), and Maternity/Paternity leave durations.
विशेषज्ञ की सलाह: म.प्र. अवकाश नियमों को ध्यान से पढ़ें, विशेष रूप से EL, आकस्मिक अवकाश (CL), और मातृत्व/पितृत्व अवकाश की अवधि के संबंध में।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 49,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 9,
            sectionId = "sec_a",
            questionNumber = 9,
            statementText = """Q9. According to MP Govt Service Rules, female government employees are entitled to maternity leave for how many days?""",
            statementTextHindi = """प्रश्न 9. म.प्र. सरकारी सेवा नियमों के अनुसार, महिला सरकारी कर्मचारी कितने दिनों के मातृत्व अवकाश की हकदार हैं?""",
            options = listOf(Option(1, "90 days"), Option(2, "135 days"), Option(3, "180 days"), Option(4, "240 days")),
            correctOptionIndex = 2,
            explanation = """Solution: Female government servants in MP are entitled to 180 days of maternity leave up to two living children.""",
            explanationHindi = """समाधान: म.प्र. में महिला सरकारी कर्मचारी दो जीवित बच्चों तक 180 दिनों के मातृत्व अवकाश की हकदार हैं।
Expert Advice: Leave policies for female employees and child care leave (CCL) are frequently asked in the Govt Service Rules paper.
विशेषज्ञ की सलाह: महिला कर्मचारियों के लिए अवकाश नीतियां और चाइल्ड केयर लीव (CCL) अक्सर सरकारी सेवा नियम के पेपर में पूछे जाते हैं।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 56,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 10,
            sectionId = "sec_a",
            questionNumber = 10,
            statementText = """Q10. Under the MP Civil Services (Conduct) Rules 1965, can a government servant take part in political elections?""",
            statementTextHindi = """प्रश्न 10. म.प्र. सिविल सेवा (आचरण) नियम 1965 के तहत, क्या कोई सरकारी कर्मचारी राजनीतिक चुनावों में भाग ले सकता है?""",
            options = listOf(Option(1, "Yes, with permission"), Option(2, "Yes, during leave"), Option(3, "No, it is completely prohibited"), Option(4, "Yes, if fighting as independent")),
            correctOptionIndex = 2,
            explanation = """Solution: Rule 5 of the MP Civil Services (Conduct) Rules strictly prohibits any government servant from taking part in politics or elections.""",
            explanationHindi = """समाधान: म.प्र. सिविल सेवा (आचरण) नियम का नियम 5 किसी भी सरकारी सेवक को राजनीति या चुनाव में भाग लेने से सख्ती से रोकता है।
Expert Advice: The Conduct Rules clearly outline prohibitions regarding politics, press, and unauthorized communication. Review Rule 5 and Rule 7 closely.
विशेषज्ञ की सलाह: आचरण नियम राजनीति, प्रेस और अनधिकृत संचार के संबंध में प्रतिबंधों को स्पष्ट रूप से रेखांकित करते हैं। नियम 5 और नियम 7 की बारीकी से समीक्षा करें।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 63,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 11,
            sectionId = "sec_a",
            questionNumber = 11,
            statementText = """Q11. Dearness Allowance (DA) is provided to government employees primarily to compensate for?""",
            statementTextHindi = """प्रश्न 11. महंगाई भत्ता (DA) सरकारी कर्मचारियों को मुख्य रूप से किसकी भरपाई के लिए प्रदान किया जाता है?""",
            options = listOf(Option(1, "Travel expenses"), Option(2, "Housing costs"), Option(3, "Medical expenses"), Option(4, "Inflation / Cost of living")),
            correctOptionIndex = 3,
            explanation = """Solution: Dearness Allowance is calculated as a percentage of basic salary to mitigate the impact of inflation on employees.""",
            explanationHindi = """समाधान: महंगाई भत्ते की गणना कर्मचारियों पर मुद्रास्फीति के प्रभाव को कम करने के लिए मूल वेतन के प्रतिशत के रूप में की जाती है।
Expert Advice: Basic understanding of Salary structures (Basic, DA, HRA) is a crucial part of the 30-mark Govt Service Rules section.
विशेषज्ञ की सलाह: वेतन संरचनाओं (Basic, DA, HRA) की बुनियादी समझ 30-अंकों वाले सरकारी सेवा नियम अनुभाग का एक महत्वपूर्ण हिस्सा है।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 70,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 12,
            sectionId = "sec_a",
            questionNumber = 12,
            statementText = """Q12. In the context of MP Revenue Terminology, what does the term 'Khasra' signify?""",
            statementTextHindi = """प्रश्न 12. म.प्र. राजस्व शब्दावली के संदर्भ में, 'खसरा' शब्द का क्या अर्थ है?""",
            options = listOf(Option(1, "A tax collection receipt"), Option(2, "A village map"), Option(3, "A primary agricultural record containing plot details"), Option(4, "A type of land revenue tax")),
            correctOptionIndex = 2,
            explanation = """Solution: Khasra is a primary land record document that contains details of the land parcel, crop grown, soil type, and the cultivator's name.""",
            explanationHindi = """समाधान: खसरा एक प्राथमिक भूमि रिकॉर्ड दस्तावेज़ है जिसमें भूमि पार्सल, उगाई गई फसल, मिट्टी के प्रकार और कृषक के नाम का विवरण होता है।
Expert Advice: Differentiate clearly between Khasra (plot details), Khatauni (holding details of a person), and Shajra (village map).
विशेषज्ञ की सलाह: खसरा (भूखंड विवरण), खतौनी (किसी व्यक्ति के जोत का विवरण), और शजरा (गाँव का नक्शा) के बीच स्पष्ट रूप से अंतर करें।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 77,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 13,
            sectionId = "sec_a",
            questionNumber = 13,
            statementText = """Q13. What is meant by 'Nazul' land in the revenue department?""",
            statementTextHindi = """प्रश्न 13. राजस्व विभाग में 'नज़ूल' भूमि से क्या तात्पर्य है?""",
            options = listOf(Option(1, "Private agricultural land"), Option(2, "Forest department land"), Option(3, "State-owned land situated within or near urban areas"), Option(4, "Irrigated land")),
            correctOptionIndex = 2,
            explanation = """Solution: Nazul lands are state-owned plots situated within municipal/urban limits often leased out for residential or commercial purposes.""",
            explanationHindi = """समाधान: नज़ूल भूमि नगरपालिका/शहरी सीमा के भीतर स्थित राज्य के स्वामित्व वाले भूखंड हैं जिन्हें अक्सर आवासीय या व्यावसायिक उद्देश्यों के लिए पट्टे पर दिया जाता है।
Expert Advice: Nazul rules and leasing provisions are essential parts of the Revenue Book Circular (RBC).
विशेषज्ञ की सलाह: नज़ूल नियम और पट्टे के प्रावधान राजस्व पुस्तक परिपत्र (RBC) के आवश्यक भाग हैं।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 84,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 14,
            sectionId = "sec_a",
            questionNumber = 14,
            statementText = """Q14. Under which section of the MP Land Revenue Code, 1959 is the 'Board of Revenue' established?""",
            statementTextHindi = """प्रश्न 14. म.प्र. भू-राजस्व संहिता, 1959 की किस धारा के तहत 'राजस्व मंडल' की स्थापना की गई है?""",
            options = listOf(Option(1, "Section 2"), Option(2, "Section 3"), Option(3, "Section 11"), Option(4, "Section 158")),
            correctOptionIndex = 1,
            explanation = """Solution: Section 3 of the MPLRC, 1959 provisions for the constitution of the Board of Revenue, the highest revenue court in the state.""",
            explanationHindi = """समाधान: MPLRC, 1959 की धारा 3 राजस्व मंडल के गठन का प्रावधान करती है, जो राज्य का सर्वोच्च राजस्व न्यायालय है।
Expert Advice: Memorize the key sections of MPLRC dealing with authorities (Sec 11) and Bhumiswami rights (Sec 158).
विशेषज्ञ की सलाह: अधिकारियों (धारा 11) और भूमिस्वामी अधिकारों (धारा 158) से निपटने वाली MPLRC की प्रमुख धाराओं को याद रखें।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 91,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 15,
            sectionId = "sec_a",
            questionNumber = 15,
            statementText = """Q15. Where is the principal seat or headquarters of the Board of Revenue in Madhya Pradesh located?""",
            statementTextHindi = """प्रश्न 15. मध्य प्रदेश में राजस्व मंडल का प्रमुख स्थान या मुख्यालय कहाँ स्थित है?""",
            options = listOf(Option(1, "Bhopal"), Option(2, "Indore"), Option(3, "Gwalior"), Option(4, "Jabalpur")),
            correctOptionIndex = 2,
            explanation = """Solution: The Board of Revenue (Rajya Swamandal) of Madhya Pradesh is headquartered in Gwalior.""",
            explanationHindi = """समाधान: मध्य प्रदेश के राजस्व मंडल का मुख्यालय ग्वालियर में है।
Expert Advice: While the political capital is Bhopal, key institutions like the High Court (Jabalpur) and Board of Revenue (Gwalior) are elsewhere.
विशेषज्ञ की सलाह: जबकि राजनीतिक राजधानी भोपाल है, उच्च न्यायालय (जबलपुर) और राजस्व मंडल (ग्वालियर) जैसे प्रमुख संस्थान अन्य स्थानों पर हैं।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 98,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 16,
            sectionId = "sec_a",
            questionNumber = 16,
            statementText = """Q16. The 'Bhu-Adhikar Pustika' (Kisan Book) is provided to a Bhumiswami under which section of MPLRC 1959?""",
            statementTextHindi = """प्रश्न 16. MPLRC 1959 की किस धारा के तहत एक भूमिस्वामी को 'भू-अधिकार पुस्तिका' (किसान बुक) प्रदान की जाती है?""",
            options = listOf(Option(1, "Section 108"), Option(2, "Section 114"), Option(3, "Section 158"), Option(4, "Section 242")),
            correctOptionIndex = 1,
            explanation = """Solution: Section 114 of the code mandates the provision of Bhu-Adhikar Pustika to every Bhumiswami containing details of their holdings.""",
            explanationHindi = """समाधान: संहिता की धारा 114 प्रत्येक भूमिस्वामी को भू-अधिकार पुस्तिका के प्रावधान को अनिवार्य करती है जिसमें उनकी जोत का विवरण होता है।
Expert Advice: Sections relating to Record of Rights (Sec 108) and Bhu-Adhikar Pustika (Sec 114) are very high-yield for exams.
विशेषज्ञ की सलाह: अधिकारों के अभिलेख (धारा 108) और भू-अधिकार पुस्तिका (धारा 114) से संबंधित धाराएं परीक्षाओं के लिए बहुत महत्वपूर्ण हैं।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 5,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 17,
            sectionId = "sec_a",
            questionNumber = 17,
            statementText = """Q17. What does the term 'Wajib-ul-arz' refer to under Section 242 of the MPLRC 1959?""",
            statementTextHindi = """प्रश्न 17. MPLRC 1959 की धारा 242 के तहत 'वाजिब-उल-अर्ज़' शब्द का क्या अर्थ है?""",
            options = listOf(Option(1, "Record of customs in a village"), Option(2, "Tax receipt"), Option(3, "Land transfer deed"), Option(4, "Dispute register")),
            correctOptionIndex = 0,
            explanation = """Solution: Wajib-ul-arz is a record prepared by the Sub-Divisional Officer detailing the customs existing in a village.""",
            explanationHindi = """समाधान: वाजिब-उल-अर्ज़ अनुविभागीय अधिकारी द्वारा तैयार किया गया एक रिकॉर्ड है जिसमें गाँव में मौजूद रिवाजों का विवरण होता है।
Expert Advice: Traditional revenue terms from the Mughal/British era are preserved in MPLRC and must be memorized.
विशेषज्ञ की सलाह: मुगल/ब्रिटिश काल के पारंपरिक राजस्व शब्द MPLRC में संरक्षित हैं और इन्हें याद किया जाना चाहिए।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 12,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 18,
            sectionId = "sec_a",
            questionNumber = 18,
            statementText = """Q18. Which part of the Revenue Book Circular (RBC) deals exclusively with providing relief in case of natural calamities?""",
            statementTextHindi = """प्रश्न 18. राजस्व पुस्तक परिपत्र (RBC) का कौन सा भाग विशेष रूप से प्राकृतिक आपदाओं के मामले में राहत प्रदान करने से संबंधित है?""",
            options = listOf(Option(1, "RBC 6-4"), Option(2, "RBC 4-1"), Option(3, "RBC 3-2"), Option(4, "RBC 5-3")),
            correctOptionIndex = 0,
            explanation = """Solution: RBC 6-4 contains provisions and guidelines for financial assistance to citizens affected by natural disasters like drought, flood, or fire.""",
            explanationHindi = """समाधान: RBC 6-4 में सूखा, बाढ़ या आग जैसी प्राकृतिक आपदाओं से प्रभावित नागरिकों को वित्तीय सहायता के प्रावधान और दिशानिर्देश शामिल हैं।
Expert Advice: RBC 6-4 is heavily utilized by Patwaris and Revenue Inspectors in the field. Questions on this are almost guaranteed.
विशेषज्ञ की सलाह: RBC 6-4 का उपयोग मैदान में पटवारियों और राजस्व निरीक्षकों द्वारा भारी मात्रा में किया जाता है। इस पर प्रश्न लगभग तय हैं।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 19,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 19,
            sectionId = "sec_a",
            questionNumber = 19,
            statementText = """Q19. According to the Land Records Manual, who is the field-level official primarily responsible for maintaining the village map (Naksha)?""",
            statementTextHindi = """प्रश्न 19. भू-अभिलेख नियमावली के अनुसार, गाँव के नक्शे (नक्शा) को बनाए रखने के लिए मुख्य रूप से कौन सा मैदानी स्तर का अधिकारी जिम्मेदार है?""",
            options = listOf(Option(1, "Tehsildar"), Option(2, "Revenue Inspector (RI)"), Option(3, "Patwari"), Option(4, "Sub-Divisional Officer (SDO)")),
            correctOptionIndex = 2,
            explanation = """Solution: The Patwari (Halka Patwari) is the foundational revenue official responsible for ground-level land record maintenance and crop inspection.""",
            explanationHindi = """समाधान: पटवारी (हल्का पटवारी) जमीनी स्तर पर भूमि रिकॉर्ड के रखरखाव और फसल निरीक्षण के लिए जिम्मेदार आधारभूत राजस्व अधिकारी है।
Expert Advice: Understand the hierarchy of the Revenue Department: Patwari -> RI -> Naib Tehsildar -> Tehsildar -> SDO -> Collector.
विशेषज्ञ की सलाह: राजस्व विभाग के पदानुक्रम को समझें: पटवारी -> आरआई -> नायब तहसीलदार -> तहसीलदार -> एसडीओ -> कलेक्टर।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 26,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 20,
            sectionId = "sec_a",
            questionNumber = 20,
            statementText = """Q20. What is the primary function of the 'Girdawari' process in MP Revenue operations?""",
            statementTextHindi = """प्रश्न 20. म.प्र. राजस्व संचालन में 'गिरदावरी' प्रक्रिया का प्राथमिक कार्य क्या है?""",
            options = listOf(Option(1, "Tax collection"), Option(2, "Crop inspection and recording"), Option(3, "Land division"), Option(4, "Village election")),
            correctOptionIndex = 1,
            explanation = """Solution: Girdawari is the periodic crop inspection conducted by the Patwari to record which crops are sown in which parcels of land.""",
            explanationHindi = """समाधान: गिरदावरी पटवारी द्वारा किया जाने वाला आवधिक फसल निरीक्षण है जो यह रिकॉर्ड करता है कि भूमि के किस पार्सल में कौन सी फसल बोई गई है।
Expert Advice: The timings of Kharif, Rabi, and Zaid Girdawari as outlined in the Land Records Manual should be known.
विशेषज्ञ की सलाह: भू-अभिलेख नियमावली में उल्लिखित खरीफ, रबी और जायद गिरदावरी के समय का ज्ञान होना चाहिए।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 33,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 21,
            sectionId = "sec_a",
            questionNumber = 21,
            statementText = """Q21. Identify the next number in the series: 2, 6, 12, 20, ?""",
            statementTextHindi = """प्रश्न 21. श्रृंखला में अगली संख्या पहचानें: 2, 6, 12, 20, ?""",
            options = listOf(Option(1, "24"), Option(2, "28"), Option(3, "30"), Option(4, "32")),
            correctOptionIndex = 2,
            explanation = """Solution: The series follows the pattern: 1x2=2, 2x3=6, 3x4=12, 4x5=20. The next term is 5x6=30.""",
            explanationHindi = """समाधान: यह श्रृंखला इस पैटर्न का पालन करती है: 1x2=2, 2x3=6, 3x4=12, 4x5=20। अगला पद 5x6=30 है।
Expert Advice: Master basic multiplication series and differences between consecutive numbers for quick logical reasoning.
विशेषज्ञ की सलाह: त्वरित तार्किक तर्क के लिए बुनियादी गुणा श्रृंखला और लगातार संख्याओं के बीच के अंतर में महारत हासिल करें।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 40,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 22,
            sectionId = "sec_a",
            questionNumber = 22,
            statementText = """Q22. Who is known to have founded the historical city of Gwalior?""",
            statementTextHindi = """प्रश्न 22. ऐतिहासिक शहर ग्वालियर की स्थापना किसने की थी?""",
            options = listOf(Option(1, "Raja Man Singh Tomar"), Option(2, "Suraj Sen"), Option(3, "Mahadji Scindia"), Option(4, "Tatya Tope")),
            correctOptionIndex = 1,
            explanation = """Solution: Gwalior was founded by King Suraj Sen in the 8th century, named after the sage Gwalipa who cured his leprosy.""",
            explanationHindi = """समाधान: ग्वालियर की स्थापना 8वीं शताब्दी में राजा सूरज सेन ने की थी, जिसका नाम ऋषि ग्वालिपा के नाम पर रखा गया था जिन्होंने उनके कुष्ठ रोग को ठीक किया था।
Expert Advice: Local history of MP, especially concerning historical capitals like Gwalior, Indore, and Bhopal, is crucial for Paper 1.
विशेषज्ञ की सलाह: एमपी का स्थानीय इतिहास, विशेष रूप से ग्वालियर, इंदौर और भोपाल जैसी ऐतिहासिक राजधानियों के संबंध में, पेपर 1 के लिए महत्वपूर्ण है।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 47,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 23,
            sectionId = "sec_a",
            questionNumber = 23,
            statementText = """Q23. Which of the following Sandhi (Join) is present in the word 'Suryoday' (सूर्योदय)?""",
            statementTextHindi = """प्रश्न 23. 'सूर्योदय' शब्द में निम्नलिखित में से कौन सी संधि है?""",
            options = listOf(Option(1, "Dirgha Sandhi"), Option(2, "Guna Sandhi"), Option(3, "Vriddhi Sandhi"), Option(4, "Yan Sandhi")),
            correctOptionIndex = 1,
            explanation = """Solution: Suryoday is formed by Surya + Uday (अ + उ = ओ), which is a classic example of Guna Sandhi.""",
            explanationHindi = """समाधान: सूर्योदय, सूर्य + उदय (अ + उ = ओ) से बना है, जो गुण संधि का एक उत्कृष्ट उदाहरण है।
Expert Advice: Practice the basic rules of Swar Sandhi (Vowel Sandhi) as at least 1-2 questions are standard in General Hindi.
विशेषज्ञ की सलाह: स्वर संधि के बुनियादी नियमों का अभ्यास करें क्योंकि सामान्य हिंदी में कम से कम 1-2 प्रश्न मानक होते हैं।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 54,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 24,
            sectionId = "sec_a",
            questionNumber = 24,
            statementText = """Q24. Choose the correct preposition: 'She is very good ___ mathematics.'""",
            statementTextHindi = """प्रश्न 24. सही पूर्वसर्ग (preposition) चुनें: 'She is very good ___ mathematics.'""",
            options = listOf(Option(1, "in"), Option(2, "with"), Option(3, "at"), Option(4, "about")),
            correctOptionIndex = 2,
            explanation = """Solution: The correct preposition to use with 'good' when talking about skills or abilities is 'at' (good at mathematics).""",
            explanationHindi = """समाधान: कौशल या क्षमताओं के बारे में बात करते समय 'good' के साथ उपयोग करने के लिए सही प्रीपोज़िशन 'at' है (good at mathematics)।
Expert Advice: Prepositions with fixed adjectives (good at, afraid of, interested in) are highly tested in MP exams.
विशेषज्ञ की सलाह: निश्चित विशेषणों (good at, afraid of, interested in) वाले पूर्वसर्गों का एमपी परीक्षाओं में अत्यधिक परीक्षण किया जाता है।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 61,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 25,
            sectionId = "sec_a",
            questionNumber = 25,
            statementText = """Q25. If a shopkeeper sells an item for Rs 120 and makes a 20% profit, what was the cost price of the item?""",
            statementTextHindi = """प्रश्न 25. यदि कोई दुकानदार किसी वस्तु को 120 रुपये में बेचता है और 20% का लाभ कमाता है, तो वस्तु का क्रय मूल्य क्या था?""",
            options = listOf(Option(1, "Rs 96"), Option(2, "Rs 100"), Option(3, "Rs 108"), Option(4, "Rs 144")),
            correctOptionIndex = 1,
            explanation = """Solution: Selling Price (SP) = Cost Price (CP) + 20% of CP. 120 = 1.2 * CP. Therefore, CP = 120 / 1.2 = 100.""",
            explanationHindi = """समाधान: विक्रय मूल्य (SP) = क्रय मूल्य (CP) + CP का 20%। 120 = 1.2 * CP. इसलिए, CP = 120 / 1.2 = 100.
Expert Advice: Memorize fraction equivalents of percentages (20% = 1/5) to solve Profit & Loss questions mentally.
विशेषज्ञ की सलाह: लाभ और हानि के प्रश्नों को मानसिक रूप से हल करने के लिए प्रतिशत के अंश समतुल्य (20% = 1/5) याद रखें।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 68,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 26,
            sectionId = "sec_a",
            questionNumber = 26,
            statementText = """Q26. Which vitamin is scientifically known as Ascorbic Acid?""",
            statementTextHindi = """प्रश्न 26. किस विटामिन को वैज्ञानिक रूप से एस्कॉर्बिक एसिड के रूप में जाना जाता है?""",
            options = listOf(Option(1, "Vitamin A"), Option(2, "Vitamin B12"), Option(3, "Vitamin C"), Option(4, "Vitamin D")),
            correctOptionIndex = 2,
            explanation = """Solution: Vitamin C is known as Ascorbic Acid. Its deficiency causes Scurvy.""",
            explanationHindi = """समाधान: विटामिन C को एस्कॉर्बिक एसिड के रूप में जाना जाता है। इसकी कमी से स्कर्वी रोग होता है।
Expert Advice: Learn the chemical names and deficiency diseases for all essential vitamins (A, B-complex, C, D, E, K).
विशेषज्ञ की सलाह: सभी आवश्यक विटामिनों (A, B-कॉम्प्लेक्स, C, D, E, K) के रासायनिक नाम और कमी से होने वाले रोगों को याद करें।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 75,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 27,
            sectionId = "sec_a",
            questionNumber = 27,
            statementText = """Q27. In computer terminology, what is the full form of RAM?""",
            statementTextHindi = """प्रश्न 27. कंप्यूटर शब्दावली में RAM का पूर्ण रूप क्या है?""",
            options = listOf(Option(1, "Read Access Memory"), Option(2, "Random Access Memory"), Option(3, "Run Access Memory"), Option(4, "Random Arithmetic Memory")),
            correctOptionIndex = 1,
            explanation = """Solution: RAM stands for Random Access Memory. It is the primary volatile memory used by the computer to store data temporarily.""",
            explanationHindi = """समाधान: RAM का मतलब रैंडम एक्सेस मेमोरी है। यह कंप्यूटर द्वारा डेटा को अस्थायी रूप से संग्रहीत करने के लिए उपयोग की जाने वाली प्राथमिक अस्थिर (volatile) मेमोरी है।
Expert Advice: Basic hardware components and their abbreviations are a staple of the General Computer Knowledge section.
विशेषज्ञ की सलाह: बुनियादी हार्डवेयर घटक और उनके संक्षिप्त नाम सामान्य कंप्यूटर ज्ञान अनुभाग का एक प्रमुख हिस्सा हैं।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 82,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 28,
            sectionId = "sec_a",
            questionNumber = 28,
            statementText = """Q28. Under the MP Civil Services (Leave) Rules, 1977, what is the maximum number of days of Earned Leave (EL) that can be accumulated by a government servant?""",
            statementTextHindi = """प्रश्न 28. म.प्र. सिविल सेवा (अवकाश) नियम, 1977 के तहत, किसी सरकारी सेवक द्वारा अधिकतम कितने दिनों का अर्जित अवकाश (EL) संचित किया जा सकता है?""",
            options = listOf(Option(1, "180 days"), Option(2, "240 days"), Option(3, "300 days"), Option(4, "365 days")),
            correctOptionIndex = 2,
            explanation = """Solution: A state government employee in MP can accumulate a maximum of 300 days of Earned Leave in their service tenure, which can be encashed at retirement.""",
            explanationHindi = """समाधान: एमपी में एक राज्य सरकार का कर्मचारी अपने सेवा कार्यकाल में अधिकतम 300 दिनों का अर्जित अवकाश संचित कर सकता है, जिसे सेवानिवृत्ति पर भुनाया जा सकता है।
Expert Advice: Read the MP Leave Rules carefully, particularly regarding EL, Casual Leave (CL), and Maternity/Paternity leave durations.
विशेषज्ञ की सलाह: म.प्र. अवकाश नियमों को ध्यान से पढ़ें, विशेष रूप से EL, आकस्मिक अवकाश (CL), और मातृत्व/पितृत्व अवकाश की अवधि के संबंध में।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 89,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 29,
            sectionId = "sec_a",
            questionNumber = 29,
            statementText = """Q29. According to MP Govt Service Rules, female government employees are entitled to maternity leave for how many days?""",
            statementTextHindi = """प्रश्न 29. म.प्र. सरकारी सेवा नियमों के अनुसार, महिला सरकारी कर्मचारी कितने दिनों के मातृत्व अवकाश की हकदार हैं?""",
            options = listOf(Option(1, "90 days"), Option(2, "135 days"), Option(3, "180 days"), Option(4, "240 days")),
            correctOptionIndex = 2,
            explanation = """Solution: Female government servants in MP are entitled to 180 days of maternity leave up to two living children.""",
            explanationHindi = """समाधान: म.प्र. में महिला सरकारी कर्मचारी दो जीवित बच्चों तक 180 दिनों के मातृत्व अवकाश की हकदार हैं।
Expert Advice: Leave policies for female employees and child care leave (CCL) are frequently asked in the Govt Service Rules paper.
विशेषज्ञ की सलाह: महिला कर्मचारियों के लिए अवकाश नीतियां और चाइल्ड केयर लीव (CCL) अक्सर सरकारी सेवा नियम के पेपर में पूछे जाते हैं।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 96,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 30,
            sectionId = "sec_a",
            questionNumber = 30,
            statementText = """Q30. Under the MP Civil Services (Conduct) Rules 1965, can a government servant take part in political elections?""",
            statementTextHindi = """प्रश्न 30. म.प्र. सिविल सेवा (आचरण) नियम 1965 के तहत, क्या कोई सरकारी कर्मचारी राजनीतिक चुनावों में भाग ले सकता है?""",
            options = listOf(Option(1, "Yes, with permission"), Option(2, "Yes, during leave"), Option(3, "No, it is completely prohibited"), Option(4, "Yes, if fighting as independent")),
            correctOptionIndex = 2,
            explanation = """Solution: Rule 5 of the MP Civil Services (Conduct) Rules strictly prohibits any government servant from taking part in politics or elections.""",
            explanationHindi = """समाधान: म.प्र. सिविल सेवा (आचरण) नियम का नियम 5 किसी भी सरकारी सेवक को राजनीति या चुनाव में भाग लेने से सख्ती से रोकता है।
Expert Advice: The Conduct Rules clearly outline prohibitions regarding politics, press, and unauthorized communication. Review Rule 5 and Rule 7 closely.
विशेषज्ञ की सलाह: आचरण नियम राजनीति, प्रेस और अनधिकृत संचार के संबंध में प्रतिबंधों को स्पष्ट रूप से रेखांकित करते हैं। नियम 5 और नियम 7 की बारीकी से समीक्षा करें।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 3,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 31,
            sectionId = "sec_a",
            questionNumber = 31,
            statementText = """Q31. Dearness Allowance (DA) is provided to government employees primarily to compensate for?""",
            statementTextHindi = """प्रश्न 31. महंगाई भत्ता (DA) सरकारी कर्मचारियों को मुख्य रूप से किसकी भरपाई के लिए प्रदान किया जाता है?""",
            options = listOf(Option(1, "Travel expenses"), Option(2, "Housing costs"), Option(3, "Medical expenses"), Option(4, "Inflation / Cost of living")),
            correctOptionIndex = 3,
            explanation = """Solution: Dearness Allowance is calculated as a percentage of basic salary to mitigate the impact of inflation on employees.""",
            explanationHindi = """समाधान: महंगाई भत्ते की गणना कर्मचारियों पर मुद्रास्फीति के प्रभाव को कम करने के लिए मूल वेतन के प्रतिशत के रूप में की जाती है।
Expert Advice: Basic understanding of Salary structures (Basic, DA, HRA) is a crucial part of the 30-mark Govt Service Rules section.
विशेषज्ञ की सलाह: वेतन संरचनाओं (Basic, DA, HRA) की बुनियादी समझ 30-अंकों वाले सरकारी सेवा नियम अनुभाग का एक महत्वपूर्ण हिस्सा है।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 10,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 32,
            sectionId = "sec_a",
            questionNumber = 32,
            statementText = """Q32. In the context of MP Revenue Terminology, what does the term 'Khasra' signify?""",
            statementTextHindi = """प्रश्न 32. म.प्र. राजस्व शब्दावली के संदर्भ में, 'खसरा' शब्द का क्या अर्थ है?""",
            options = listOf(Option(1, "A tax collection receipt"), Option(2, "A village map"), Option(3, "A primary agricultural record containing plot details"), Option(4, "A type of land revenue tax")),
            correctOptionIndex = 2,
            explanation = """Solution: Khasra is a primary land record document that contains details of the land parcel, crop grown, soil type, and the cultivator's name.""",
            explanationHindi = """समाधान: खसरा एक प्राथमिक भूमि रिकॉर्ड दस्तावेज़ है जिसमें भूमि पार्सल, उगाई गई फसल, मिट्टी के प्रकार और कृषक के नाम का विवरण होता है।
Expert Advice: Differentiate clearly between Khasra (plot details), Khatauni (holding details of a person), and Shajra (village map).
विशेषज्ञ की सलाह: खसरा (भूखंड विवरण), खतौनी (किसी व्यक्ति के जोत का विवरण), और शजरा (गाँव का नक्शा) के बीच स्पष्ट रूप से अंतर करें।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 17,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 33,
            sectionId = "sec_a",
            questionNumber = 33,
            statementText = """Q33. What is meant by 'Nazul' land in the revenue department?""",
            statementTextHindi = """प्रश्न 33. राजस्व विभाग में 'नज़ूल' भूमि से क्या तात्पर्य है?""",
            options = listOf(Option(1, "Private agricultural land"), Option(2, "Forest department land"), Option(3, "State-owned land situated within or near urban areas"), Option(4, "Irrigated land")),
            correctOptionIndex = 2,
            explanation = """Solution: Nazul lands are state-owned plots situated within municipal/urban limits often leased out for residential or commercial purposes.""",
            explanationHindi = """समाधान: नज़ूल भूमि नगरपालिका/शहरी सीमा के भीतर स्थित राज्य के स्वामित्व वाले भूखंड हैं जिन्हें अक्सर आवासीय या व्यावसायिक उद्देश्यों के लिए पट्टे पर दिया जाता है।
Expert Advice: Nazul rules and leasing provisions are essential parts of the Revenue Book Circular (RBC).
विशेषज्ञ की सलाह: नज़ूल नियम और पट्टे के प्रावधान राजस्व पुस्तक परिपत्र (RBC) के आवश्यक भाग हैं।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 24,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 34,
            sectionId = "sec_a",
            questionNumber = 34,
            statementText = """Q34. Under which section of the MP Land Revenue Code, 1959 is the 'Board of Revenue' established?""",
            statementTextHindi = """प्रश्न 34. म.प्र. भू-राजस्व संहिता, 1959 की किस धारा के तहत 'राजस्व मंडल' की स्थापना की गई है?""",
            options = listOf(Option(1, "Section 2"), Option(2, "Section 3"), Option(3, "Section 11"), Option(4, "Section 158")),
            correctOptionIndex = 1,
            explanation = """Solution: Section 3 of the MPLRC, 1959 provisions for the constitution of the Board of Revenue, the highest revenue court in the state.""",
            explanationHindi = """समाधान: MPLRC, 1959 की धारा 3 राजस्व मंडल के गठन का प्रावधान करती है, जो राज्य का सर्वोच्च राजस्व न्यायालय है।
Expert Advice: Memorize the key sections of MPLRC dealing with authorities (Sec 11) and Bhumiswami rights (Sec 158).
विशेषज्ञ की सलाह: अधिकारियों (धारा 11) और भूमिस्वामी अधिकारों (धारा 158) से निपटने वाली MPLRC की प्रमुख धाराओं को याद रखें।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 31,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 35,
            sectionId = "sec_a",
            questionNumber = 35,
            statementText = """Q35. Where is the principal seat or headquarters of the Board of Revenue in Madhya Pradesh located?""",
            statementTextHindi = """प्रश्न 35. मध्य प्रदेश में राजस्व मंडल का प्रमुख स्थान या मुख्यालय कहाँ स्थित है?""",
            options = listOf(Option(1, "Bhopal"), Option(2, "Indore"), Option(3, "Gwalior"), Option(4, "Jabalpur")),
            correctOptionIndex = 2,
            explanation = """Solution: The Board of Revenue (Rajya Swamandal) of Madhya Pradesh is headquartered in Gwalior.""",
            explanationHindi = """समाधान: मध्य प्रदेश के राजस्व मंडल का मुख्यालय ग्वालियर में है।
Expert Advice: While the political capital is Bhopal, key institutions like the High Court (Jabalpur) and Board of Revenue (Gwalior) are elsewhere.
विशेषज्ञ की सलाह: जबकि राजनीतिक राजधानी भोपाल है, उच्च न्यायालय (जबलपुर) और राजस्व मंडल (ग्वालियर) जैसे प्रमुख संस्थान अन्य स्थानों पर हैं।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 38,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 36,
            sectionId = "sec_a",
            questionNumber = 36,
            statementText = """Q36. The 'Bhu-Adhikar Pustika' (Kisan Book) is provided to a Bhumiswami under which section of MPLRC 1959?""",
            statementTextHindi = """प्रश्न 36. MPLRC 1959 की किस धारा के तहत एक भूमिस्वामी को 'भू-अधिकार पुस्तिका' (किसान बुक) प्रदान की जाती है?""",
            options = listOf(Option(1, "Section 108"), Option(2, "Section 114"), Option(3, "Section 158"), Option(4, "Section 242")),
            correctOptionIndex = 1,
            explanation = """Solution: Section 114 of the code mandates the provision of Bhu-Adhikar Pustika to every Bhumiswami containing details of their holdings.""",
            explanationHindi = """समाधान: संहिता की धारा 114 प्रत्येक भूमिस्वामी को भू-अधिकार पुस्तिका के प्रावधान को अनिवार्य करती है जिसमें उनकी जोत का विवरण होता है।
Expert Advice: Sections relating to Record of Rights (Sec 108) and Bhu-Adhikar Pustika (Sec 114) are very high-yield for exams.
विशेषज्ञ की सलाह: अधिकारों के अभिलेख (धारा 108) और भू-अधिकार पुस्तिका (धारा 114) से संबंधित धाराएं परीक्षाओं के लिए बहुत महत्वपूर्ण हैं।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 45,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 37,
            sectionId = "sec_a",
            questionNumber = 37,
            statementText = """Q37. What does the term 'Wajib-ul-arz' refer to under Section 242 of the MPLRC 1959?""",
            statementTextHindi = """प्रश्न 37. MPLRC 1959 की धारा 242 के तहत 'वाजिब-उल-अर्ज़' शब्द का क्या अर्थ है?""",
            options = listOf(Option(1, "Record of customs in a village"), Option(2, "Tax receipt"), Option(3, "Land transfer deed"), Option(4, "Dispute register")),
            correctOptionIndex = 0,
            explanation = """Solution: Wajib-ul-arz is a record prepared by the Sub-Divisional Officer detailing the customs existing in a village.""",
            explanationHindi = """समाधान: वाजिब-उल-अर्ज़ अनुविभागीय अधिकारी द्वारा तैयार किया गया एक रिकॉर्ड है जिसमें गाँव में मौजूद रिवाजों का विवरण होता है।
Expert Advice: Traditional revenue terms from the Mughal/British era are preserved in MPLRC and must be memorized.
विशेषज्ञ की सलाह: मुगल/ब्रिटिश काल के पारंपरिक राजस्व शब्द MPLRC में संरक्षित हैं और इन्हें याद किया जाना चाहिए।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 52,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 38,
            sectionId = "sec_a",
            questionNumber = 38,
            statementText = """Q38. Which part of the Revenue Book Circular (RBC) deals exclusively with providing relief in case of natural calamities?""",
            statementTextHindi = """प्रश्न 38. राजस्व पुस्तक परिपत्र (RBC) का कौन सा भाग विशेष रूप से प्राकृतिक आपदाओं के मामले में राहत प्रदान करने से संबंधित है?""",
            options = listOf(Option(1, "RBC 6-4"), Option(2, "RBC 4-1"), Option(3, "RBC 3-2"), Option(4, "RBC 5-3")),
            correctOptionIndex = 0,
            explanation = """Solution: RBC 6-4 contains provisions and guidelines for financial assistance to citizens affected by natural disasters like drought, flood, or fire.""",
            explanationHindi = """समाधान: RBC 6-4 में सूखा, बाढ़ या आग जैसी प्राकृतिक आपदाओं से प्रभावित नागरिकों को वित्तीय सहायता के प्रावधान और दिशानिर्देश शामिल हैं।
Expert Advice: RBC 6-4 is heavily utilized by Patwaris and Revenue Inspectors in the field. Questions on this are almost guaranteed.
विशेषज्ञ की सलाह: RBC 6-4 का उपयोग मैदान में पटवारियों और राजस्व निरीक्षकों द्वारा भारी मात्रा में किया जाता है। इस पर प्रश्न लगभग तय हैं।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 59,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 39,
            sectionId = "sec_a",
            questionNumber = 39,
            statementText = """Q39. According to the Land Records Manual, who is the field-level official primarily responsible for maintaining the village map (Naksha)?""",
            statementTextHindi = """प्रश्न 39. भू-अभिलेख नियमावली के अनुसार, गाँव के नक्शे (नक्शा) को बनाए रखने के लिए मुख्य रूप से कौन सा मैदानी स्तर का अधिकारी जिम्मेदार है?""",
            options = listOf(Option(1, "Tehsildar"), Option(2, "Revenue Inspector (RI)"), Option(3, "Patwari"), Option(4, "Sub-Divisional Officer (SDO)")),
            correctOptionIndex = 2,
            explanation = """Solution: The Patwari (Halka Patwari) is the foundational revenue official responsible for ground-level land record maintenance and crop inspection.""",
            explanationHindi = """समाधान: पटवारी (हल्का पटवारी) जमीनी स्तर पर भूमि रिकॉर्ड के रखरखाव और फसल निरीक्षण के लिए जिम्मेदार आधारभूत राजस्व अधिकारी है।
Expert Advice: Understand the hierarchy of the Revenue Department: Patwari -> RI -> Naib Tehsildar -> Tehsildar -> SDO -> Collector.
विशेषज्ञ की सलाह: राजस्व विभाग के पदानुक्रम को समझें: पटवारी -> आरआई -> नायब तहसीलदार -> तहसीलदार -> एसडीओ -> कलेक्टर।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 66,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 40,
            sectionId = "sec_a",
            questionNumber = 40,
            statementText = """Q40. What is the primary function of the 'Girdawari' process in MP Revenue operations?""",
            statementTextHindi = """प्रश्न 40. म.प्र. राजस्व संचालन में 'गिरदावरी' प्रक्रिया का प्राथमिक कार्य क्या है?""",
            options = listOf(Option(1, "Tax collection"), Option(2, "Crop inspection and recording"), Option(3, "Land division"), Option(4, "Village election")),
            correctOptionIndex = 1,
            explanation = """Solution: Girdawari is the periodic crop inspection conducted by the Patwari to record which crops are sown in which parcels of land.""",
            explanationHindi = """समाधान: गिरदावरी पटवारी द्वारा किया जाने वाला आवधिक फसल निरीक्षण है जो यह रिकॉर्ड करता है कि भूमि के किस पार्सल में कौन सी फसल बोई गई है।
Expert Advice: The timings of Kharif, Rabi, and Zaid Girdawari as outlined in the Land Records Manual should be known.
विशेषज्ञ की सलाह: भू-अभिलेख नियमावली में उल्लिखित खरीफ, रबी और जायद गिरदावरी के समय का ज्ञान होना चाहिए।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 73,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 41,
            sectionId = "sec_a",
            questionNumber = 41,
            statementText = """Q41. Identify the next number in the series: 2, 6, 12, 20, ?""",
            statementTextHindi = """प्रश्न 41. श्रृंखला में अगली संख्या पहचानें: 2, 6, 12, 20, ?""",
            options = listOf(Option(1, "24"), Option(2, "28"), Option(3, "30"), Option(4, "32")),
            correctOptionIndex = 2,
            explanation = """Solution: The series follows the pattern: 1x2=2, 2x3=6, 3x4=12, 4x5=20. The next term is 5x6=30.""",
            explanationHindi = """समाधान: यह श्रृंखला इस पैटर्न का पालन करती है: 1x2=2, 2x3=6, 3x4=12, 4x5=20। अगला पद 5x6=30 है।
Expert Advice: Master basic multiplication series and differences between consecutive numbers for quick logical reasoning.
विशेषज्ञ की सलाह: त्वरित तार्किक तर्क के लिए बुनियादी गुणा श्रृंखला और लगातार संख्याओं के बीच के अंतर में महारत हासिल करें।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 80,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 42,
            sectionId = "sec_a",
            questionNumber = 42,
            statementText = """Q42. Who is known to have founded the historical city of Gwalior?""",
            statementTextHindi = """प्रश्न 42. ऐतिहासिक शहर ग्वालियर की स्थापना किसने की थी?""",
            options = listOf(Option(1, "Raja Man Singh Tomar"), Option(2, "Suraj Sen"), Option(3, "Mahadji Scindia"), Option(4, "Tatya Tope")),
            correctOptionIndex = 1,
            explanation = """Solution: Gwalior was founded by King Suraj Sen in the 8th century, named after the sage Gwalipa who cured his leprosy.""",
            explanationHindi = """समाधान: ग्वालियर की स्थापना 8वीं शताब्दी में राजा सूरज सेन ने की थी, जिसका नाम ऋषि ग्वालिपा के नाम पर रखा गया था जिन्होंने उनके कुष्ठ रोग को ठीक किया था।
Expert Advice: Local history of MP, especially concerning historical capitals like Gwalior, Indore, and Bhopal, is crucial for Paper 1.
विशेषज्ञ की सलाह: एमपी का स्थानीय इतिहास, विशेष रूप से ग्वालियर, इंदौर और भोपाल जैसी ऐतिहासिक राजधानियों के संबंध में, पेपर 1 के लिए महत्वपूर्ण है।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 87,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 43,
            sectionId = "sec_a",
            questionNumber = 43,
            statementText = """Q43. Which of the following Sandhi (Join) is present in the word 'Suryoday' (सूर्योदय)?""",
            statementTextHindi = """प्रश्न 43. 'सूर्योदय' शब्द में निम्नलिखित में से कौन सी संधि है?""",
            options = listOf(Option(1, "Dirgha Sandhi"), Option(2, "Guna Sandhi"), Option(3, "Vriddhi Sandhi"), Option(4, "Yan Sandhi")),
            correctOptionIndex = 1,
            explanation = """Solution: Suryoday is formed by Surya + Uday (अ + उ = ओ), which is a classic example of Guna Sandhi.""",
            explanationHindi = """समाधान: सूर्योदय, सूर्य + उदय (अ + उ = ओ) से बना है, जो गुण संधि का एक उत्कृष्ट उदाहरण है।
Expert Advice: Practice the basic rules of Swar Sandhi (Vowel Sandhi) as at least 1-2 questions are standard in General Hindi.
विशेषज्ञ की सलाह: स्वर संधि के बुनियादी नियमों का अभ्यास करें क्योंकि सामान्य हिंदी में कम से कम 1-2 प्रश्न मानक होते हैं।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 94,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 44,
            sectionId = "sec_a",
            questionNumber = 44,
            statementText = """Q44. Choose the correct preposition: 'She is very good ___ mathematics.'""",
            statementTextHindi = """प्रश्न 44. सही पूर्वसर्ग (preposition) चुनें: 'She is very good ___ mathematics.'""",
            options = listOf(Option(1, "in"), Option(2, "with"), Option(3, "at"), Option(4, "about")),
            correctOptionIndex = 2,
            explanation = """Solution: The correct preposition to use with 'good' when talking about skills or abilities is 'at' (good at mathematics).""",
            explanationHindi = """समाधान: कौशल या क्षमताओं के बारे में बात करते समय 'good' के साथ उपयोग करने के लिए सही प्रीपोज़िशन 'at' है (good at mathematics)।
Expert Advice: Prepositions with fixed adjectives (good at, afraid of, interested in) are highly tested in MP exams.
विशेषज्ञ की सलाह: निश्चित विशेषणों (good at, afraid of, interested in) वाले पूर्वसर्गों का एमपी परीक्षाओं में अत्यधिक परीक्षण किया जाता है।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 1,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 45,
            sectionId = "sec_a",
            questionNumber = 45,
            statementText = """Q45. If a shopkeeper sells an item for Rs 120 and makes a 20% profit, what was the cost price of the item?""",
            statementTextHindi = """प्रश्न 45. यदि कोई दुकानदार किसी वस्तु को 120 रुपये में बेचता है और 20% का लाभ कमाता है, तो वस्तु का क्रय मूल्य क्या था?""",
            options = listOf(Option(1, "Rs 96"), Option(2, "Rs 100"), Option(3, "Rs 108"), Option(4, "Rs 144")),
            correctOptionIndex = 1,
            explanation = """Solution: Selling Price (SP) = Cost Price (CP) + 20% of CP. 120 = 1.2 * CP. Therefore, CP = 120 / 1.2 = 100.""",
            explanationHindi = """समाधान: विक्रय मूल्य (SP) = क्रय मूल्य (CP) + CP का 20%। 120 = 1.2 * CP. इसलिए, CP = 120 / 1.2 = 100.
Expert Advice: Memorize fraction equivalents of percentages (20% = 1/5) to solve Profit & Loss questions mentally.
विशेषज्ञ की सलाह: लाभ और हानि के प्रश्नों को मानसिक रूप से हल करने के लिए प्रतिशत के अंश समतुल्य (20% = 1/5) याद रखें।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 8,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 46,
            sectionId = "sec_a",
            questionNumber = 46,
            statementText = """Q46. Which vitamin is scientifically known as Ascorbic Acid?""",
            statementTextHindi = """प्रश्न 46. किस विटामिन को वैज्ञानिक रूप से एस्कॉर्बिक एसिड के रूप में जाना जाता है?""",
            options = listOf(Option(1, "Vitamin A"), Option(2, "Vitamin B12"), Option(3, "Vitamin C"), Option(4, "Vitamin D")),
            correctOptionIndex = 2,
            explanation = """Solution: Vitamin C is known as Ascorbic Acid. Its deficiency causes Scurvy.""",
            explanationHindi = """समाधान: विटामिन C को एस्कॉर्बिक एसिड के रूप में जाना जाता है। इसकी कमी से स्कर्वी रोग होता है।
Expert Advice: Learn the chemical names and deficiency diseases for all essential vitamins (A, B-complex, C, D, E, K).
विशेषज्ञ की सलाह: सभी आवश्यक विटामिनों (A, B-कॉम्प्लेक्स, C, D, E, K) के रासायनिक नाम और कमी से होने वाले रोगों को याद करें।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 15,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 47,
            sectionId = "sec_a",
            questionNumber = 47,
            statementText = """Q47. In computer terminology, what is the full form of RAM?""",
            statementTextHindi = """प्रश्न 47. कंप्यूटर शब्दावली में RAM का पूर्ण रूप क्या है?""",
            options = listOf(Option(1, "Read Access Memory"), Option(2, "Random Access Memory"), Option(3, "Run Access Memory"), Option(4, "Random Arithmetic Memory")),
            correctOptionIndex = 1,
            explanation = """Solution: RAM stands for Random Access Memory. It is the primary volatile memory used by the computer to store data temporarily.""",
            explanationHindi = """समाधान: RAM का मतलब रैंडम एक्सेस मेमोरी है। यह कंप्यूटर द्वारा डेटा को अस्थायी रूप से संग्रहीत करने के लिए उपयोग की जाने वाली प्राथमिक अस्थिर (volatile) मेमोरी है।
Expert Advice: Basic hardware components and their abbreviations are a staple of the General Computer Knowledge section.
विशेषज्ञ की सलाह: बुनियादी हार्डवेयर घटक और उनके संक्षिप्त नाम सामान्य कंप्यूटर ज्ञान अनुभाग का एक प्रमुख हिस्सा हैं।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 22,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 48,
            sectionId = "sec_a",
            questionNumber = 48,
            statementText = """Q48. Under the MP Civil Services (Leave) Rules, 1977, what is the maximum number of days of Earned Leave (EL) that can be accumulated by a government servant?""",
            statementTextHindi = """प्रश्न 48. म.प्र. सिविल सेवा (अवकाश) नियम, 1977 के तहत, किसी सरकारी सेवक द्वारा अधिकतम कितने दिनों का अर्जित अवकाश (EL) संचित किया जा सकता है?""",
            options = listOf(Option(1, "180 days"), Option(2, "240 days"), Option(3, "300 days"), Option(4, "365 days")),
            correctOptionIndex = 2,
            explanation = """Solution: A state government employee in MP can accumulate a maximum of 300 days of Earned Leave in their service tenure, which can be encashed at retirement.""",
            explanationHindi = """समाधान: एमपी में एक राज्य सरकार का कर्मचारी अपने सेवा कार्यकाल में अधिकतम 300 दिनों का अर्जित अवकाश संचित कर सकता है, जिसे सेवानिवृत्ति पर भुनाया जा सकता है।
Expert Advice: Read the MP Leave Rules carefully, particularly regarding EL, Casual Leave (CL), and Maternity/Paternity leave durations.
विशेषज्ञ की सलाह: म.प्र. अवकाश नियमों को ध्यान से पढ़ें, विशेष रूप से EL, आकस्मिक अवकाश (CL), और मातृत्व/पितृत्व अवकाश की अवधि के संबंध में।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 29,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 49,
            sectionId = "sec_a",
            questionNumber = 49,
            statementText = """Q49. According to MP Govt Service Rules, female government employees are entitled to maternity leave for how many days?""",
            statementTextHindi = """प्रश्न 49. म.प्र. सरकारी सेवा नियमों के अनुसार, महिला सरकारी कर्मचारी कितने दिनों के मातृत्व अवकाश की हकदार हैं?""",
            options = listOf(Option(1, "90 days"), Option(2, "135 days"), Option(3, "180 days"), Option(4, "240 days")),
            correctOptionIndex = 2,
            explanation = """Solution: Female government servants in MP are entitled to 180 days of maternity leave up to two living children.""",
            explanationHindi = """समाधान: म.प्र. में महिला सरकारी कर्मचारी दो जीवित बच्चों तक 180 दिनों के मातृत्व अवकाश की हकदार हैं।
Expert Advice: Leave policies for female employees and child care leave (CCL) are frequently asked in the Govt Service Rules paper.
विशेषज्ञ की सलाह: महिला कर्मचारियों के लिए अवकाश नीतियां और चाइल्ड केयर लीव (CCL) अक्सर सरकारी सेवा नियम के पेपर में पूछे जाते हैं।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 36,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 50,
            sectionId = "sec_a",
            questionNumber = 50,
            statementText = """Q50. Under the MP Civil Services (Conduct) Rules 1965, can a government servant take part in political elections?""",
            statementTextHindi = """प्रश्न 50. म.प्र. सिविल सेवा (आचरण) नियम 1965 के तहत, क्या कोई सरकारी कर्मचारी राजनीतिक चुनावों में भाग ले सकता है?""",
            options = listOf(Option(1, "Yes, with permission"), Option(2, "Yes, during leave"), Option(3, "No, it is completely prohibited"), Option(4, "Yes, if fighting as independent")),
            correctOptionIndex = 2,
            explanation = """Solution: Rule 5 of the MP Civil Services (Conduct) Rules strictly prohibits any government servant from taking part in politics or elections.""",
            explanationHindi = """समाधान: म.प्र. सिविल सेवा (आचरण) नियम का नियम 5 किसी भी सरकारी सेवक को राजनीति या चुनाव में भाग लेने से सख्ती से रोकता है।
Expert Advice: The Conduct Rules clearly outline prohibitions regarding politics, press, and unauthorized communication. Review Rule 5 and Rule 7 closely.
विशेषज्ञ की सलाह: आचरण नियम राजनीति, प्रेस और अनधिकृत संचार के संबंध में प्रतिबंधों को स्पष्ट रूप से रेखांकित करते हैं। नियम 5 और नियम 7 की बारीकी से समीक्षा करें।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 43,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 51,
            sectionId = "sec_a",
            questionNumber = 51,
            statementText = """Q51. Dearness Allowance (DA) is provided to government employees primarily to compensate for?""",
            statementTextHindi = """प्रश्न 51. महंगाई भत्ता (DA) सरकारी कर्मचारियों को मुख्य रूप से किसकी भरपाई के लिए प्रदान किया जाता है?""",
            options = listOf(Option(1, "Travel expenses"), Option(2, "Housing costs"), Option(3, "Medical expenses"), Option(4, "Inflation / Cost of living")),
            correctOptionIndex = 3,
            explanation = """Solution: Dearness Allowance is calculated as a percentage of basic salary to mitigate the impact of inflation on employees.""",
            explanationHindi = """समाधान: महंगाई भत्ते की गणना कर्मचारियों पर मुद्रास्फीति के प्रभाव को कम करने के लिए मूल वेतन के प्रतिशत के रूप में की जाती है।
Expert Advice: Basic understanding of Salary structures (Basic, DA, HRA) is a crucial part of the 30-mark Govt Service Rules section.
विशेषज्ञ की सलाह: वेतन संरचनाओं (Basic, DA, HRA) की बुनियादी समझ 30-अंकों वाले सरकारी सेवा नियम अनुभाग का एक महत्वपूर्ण हिस्सा है।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 50,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 52,
            sectionId = "sec_a",
            questionNumber = 52,
            statementText = """Q52. In the context of MP Revenue Terminology, what does the term 'Khasra' signify?""",
            statementTextHindi = """प्रश्न 52. म.प्र. राजस्व शब्दावली के संदर्भ में, 'खसरा' शब्द का क्या अर्थ है?""",
            options = listOf(Option(1, "A tax collection receipt"), Option(2, "A village map"), Option(3, "A primary agricultural record containing plot details"), Option(4, "A type of land revenue tax")),
            correctOptionIndex = 2,
            explanation = """Solution: Khasra is a primary land record document that contains details of the land parcel, crop grown, soil type, and the cultivator's name.""",
            explanationHindi = """समाधान: खसरा एक प्राथमिक भूमि रिकॉर्ड दस्तावेज़ है जिसमें भूमि पार्सल, उगाई गई फसल, मिट्टी के प्रकार और कृषक के नाम का विवरण होता है।
Expert Advice: Differentiate clearly between Khasra (plot details), Khatauni (holding details of a person), and Shajra (village map).
विशेषज्ञ की सलाह: खसरा (भूखंड विवरण), खतौनी (किसी व्यक्ति के जोत का विवरण), और शजरा (गाँव का नक्शा) के बीच स्पष्ट रूप से अंतर करें।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 57,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 53,
            sectionId = "sec_a",
            questionNumber = 53,
            statementText = """Q53. What is meant by 'Nazul' land in the revenue department?""",
            statementTextHindi = """प्रश्न 53. राजस्व विभाग में 'नज़ूल' भूमि से क्या तात्पर्य है?""",
            options = listOf(Option(1, "Private agricultural land"), Option(2, "Forest department land"), Option(3, "State-owned land situated within or near urban areas"), Option(4, "Irrigated land")),
            correctOptionIndex = 2,
            explanation = """Solution: Nazul lands are state-owned plots situated within municipal/urban limits often leased out for residential or commercial purposes.""",
            explanationHindi = """समाधान: नज़ूल भूमि नगरपालिका/शहरी सीमा के भीतर स्थित राज्य के स्वामित्व वाले भूखंड हैं जिन्हें अक्सर आवासीय या व्यावसायिक उद्देश्यों के लिए पट्टे पर दिया जाता है।
Expert Advice: Nazul rules and leasing provisions are essential parts of the Revenue Book Circular (RBC).
विशेषज्ञ की सलाह: नज़ूल नियम और पट्टे के प्रावधान राजस्व पुस्तक परिपत्र (RBC) के आवश्यक भाग हैं।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 64,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 54,
            sectionId = "sec_a",
            questionNumber = 54,
            statementText = """Q54. Under which section of the MP Land Revenue Code, 1959 is the 'Board of Revenue' established?""",
            statementTextHindi = """प्रश्न 54. म.प्र. भू-राजस्व संहिता, 1959 की किस धारा के तहत 'राजस्व मंडल' की स्थापना की गई है?""",
            options = listOf(Option(1, "Section 2"), Option(2, "Section 3"), Option(3, "Section 11"), Option(4, "Section 158")),
            correctOptionIndex = 1,
            explanation = """Solution: Section 3 of the MPLRC, 1959 provisions for the constitution of the Board of Revenue, the highest revenue court in the state.""",
            explanationHindi = """समाधान: MPLRC, 1959 की धारा 3 राजस्व मंडल के गठन का प्रावधान करती है, जो राज्य का सर्वोच्च राजस्व न्यायालय है।
Expert Advice: Memorize the key sections of MPLRC dealing with authorities (Sec 11) and Bhumiswami rights (Sec 158).
विशेषज्ञ की सलाह: अधिकारियों (धारा 11) और भूमिस्वामी अधिकारों (धारा 158) से निपटने वाली MPLRC की प्रमुख धाराओं को याद रखें।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 71,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 55,
            sectionId = "sec_a",
            questionNumber = 55,
            statementText = """Q55. Where is the principal seat or headquarters of the Board of Revenue in Madhya Pradesh located?""",
            statementTextHindi = """प्रश्न 55. मध्य प्रदेश में राजस्व मंडल का प्रमुख स्थान या मुख्यालय कहाँ स्थित है?""",
            options = listOf(Option(1, "Bhopal"), Option(2, "Indore"), Option(3, "Gwalior"), Option(4, "Jabalpur")),
            correctOptionIndex = 2,
            explanation = """Solution: The Board of Revenue (Rajya Swamandal) of Madhya Pradesh is headquartered in Gwalior.""",
            explanationHindi = """समाधान: मध्य प्रदेश के राजस्व मंडल का मुख्यालय ग्वालियर में है।
Expert Advice: While the political capital is Bhopal, key institutions like the High Court (Jabalpur) and Board of Revenue (Gwalior) are elsewhere.
विशेषज्ञ की सलाह: जबकि राजनीतिक राजधानी भोपाल है, उच्च न्यायालय (जबलपुर) और राजस्व मंडल (ग्वालियर) जैसे प्रमुख संस्थान अन्य स्थानों पर हैं।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 78,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 56,
            sectionId = "sec_a",
            questionNumber = 56,
            statementText = """Q56. The 'Bhu-Adhikar Pustika' (Kisan Book) is provided to a Bhumiswami under which section of MPLRC 1959?""",
            statementTextHindi = """प्रश्न 56. MPLRC 1959 की किस धारा के तहत एक भूमिस्वामी को 'भू-अधिकार पुस्तिका' (किसान बुक) प्रदान की जाती है?""",
            options = listOf(Option(1, "Section 108"), Option(2, "Section 114"), Option(3, "Section 158"), Option(4, "Section 242")),
            correctOptionIndex = 1,
            explanation = """Solution: Section 114 of the code mandates the provision of Bhu-Adhikar Pustika to every Bhumiswami containing details of their holdings.""",
            explanationHindi = """समाधान: संहिता की धारा 114 प्रत्येक भूमिस्वामी को भू-अधिकार पुस्तिका के प्रावधान को अनिवार्य करती है जिसमें उनकी जोत का विवरण होता है।
Expert Advice: Sections relating to Record of Rights (Sec 108) and Bhu-Adhikar Pustika (Sec 114) are very high-yield for exams.
विशेषज्ञ की सलाह: अधिकारों के अभिलेख (धारा 108) और भू-अधिकार पुस्तिका (धारा 114) से संबंधित धाराएं परीक्षाओं के लिए बहुत महत्वपूर्ण हैं।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 85,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 57,
            sectionId = "sec_a",
            questionNumber = 57,
            statementText = """Q57. What does the term 'Wajib-ul-arz' refer to under Section 242 of the MPLRC 1959?""",
            statementTextHindi = """प्रश्न 57. MPLRC 1959 की धारा 242 के तहत 'वाजिब-उल-अर्ज़' शब्द का क्या अर्थ है?""",
            options = listOf(Option(1, "Record of customs in a village"), Option(2, "Tax receipt"), Option(3, "Land transfer deed"), Option(4, "Dispute register")),
            correctOptionIndex = 0,
            explanation = """Solution: Wajib-ul-arz is a record prepared by the Sub-Divisional Officer detailing the customs existing in a village.""",
            explanationHindi = """समाधान: वाजिब-उल-अर्ज़ अनुविभागीय अधिकारी द्वारा तैयार किया गया एक रिकॉर्ड है जिसमें गाँव में मौजूद रिवाजों का विवरण होता है।
Expert Advice: Traditional revenue terms from the Mughal/British era are preserved in MPLRC and must be memorized.
विशेषज्ञ की सलाह: मुगल/ब्रिटिश काल के पारंपरिक राजस्व शब्द MPLRC में संरक्षित हैं और इन्हें याद किया जाना चाहिए।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 92,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 58,
            sectionId = "sec_a",
            questionNumber = 58,
            statementText = """Q58. Which part of the Revenue Book Circular (RBC) deals exclusively with providing relief in case of natural calamities?""",
            statementTextHindi = """प्रश्न 58. राजस्व पुस्तक परिपत्र (RBC) का कौन सा भाग विशेष रूप से प्राकृतिक आपदाओं के मामले में राहत प्रदान करने से संबंधित है?""",
            options = listOf(Option(1, "RBC 6-4"), Option(2, "RBC 4-1"), Option(3, "RBC 3-2"), Option(4, "RBC 5-3")),
            correctOptionIndex = 0,
            explanation = """Solution: RBC 6-4 contains provisions and guidelines for financial assistance to citizens affected by natural disasters like drought, flood, or fire.""",
            explanationHindi = """समाधान: RBC 6-4 में सूखा, बाढ़ या आग जैसी प्राकृतिक आपदाओं से प्रभावित नागरिकों को वित्तीय सहायता के प्रावधान और दिशानिर्देश शामिल हैं।
Expert Advice: RBC 6-4 is heavily utilized by Patwaris and Revenue Inspectors in the field. Questions on this are almost guaranteed.
विशेषज्ञ की सलाह: RBC 6-4 का उपयोग मैदान में पटवारियों और राजस्व निरीक्षकों द्वारा भारी मात्रा में किया जाता है। इस पर प्रश्न लगभग तय हैं।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 99,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 59,
            sectionId = "sec_a",
            questionNumber = 59,
            statementText = """Q59. According to the Land Records Manual, who is the field-level official primarily responsible for maintaining the village map (Naksha)?""",
            statementTextHindi = """प्रश्न 59. भू-अभिलेख नियमावली के अनुसार, गाँव के नक्शे (नक्शा) को बनाए रखने के लिए मुख्य रूप से कौन सा मैदानी स्तर का अधिकारी जिम्मेदार है?""",
            options = listOf(Option(1, "Tehsildar"), Option(2, "Revenue Inspector (RI)"), Option(3, "Patwari"), Option(4, "Sub-Divisional Officer (SDO)")),
            correctOptionIndex = 2,
            explanation = """Solution: The Patwari (Halka Patwari) is the foundational revenue official responsible for ground-level land record maintenance and crop inspection.""",
            explanationHindi = """समाधान: पटवारी (हल्का पटवारी) जमीनी स्तर पर भूमि रिकॉर्ड के रखरखाव और फसल निरीक्षण के लिए जिम्मेदार आधारभूत राजस्व अधिकारी है।
Expert Advice: Understand the hierarchy of the Revenue Department: Patwari -> RI -> Naib Tehsildar -> Tehsildar -> SDO -> Collector.
विशेषज्ञ की सलाह: राजस्व विभाग के पदानुक्रम को समझें: पटवारी -> आरआई -> नायब तहसीलदार -> तहसीलदार -> एसडीओ -> कलेक्टर।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 6,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 60,
            sectionId = "sec_a",
            questionNumber = 60,
            statementText = """Q60. What is the primary function of the 'Girdawari' process in MP Revenue operations?""",
            statementTextHindi = """प्रश्न 60. म.प्र. राजस्व संचालन में 'गिरदावरी' प्रक्रिया का प्राथमिक कार्य क्या है?""",
            options = listOf(Option(1, "Tax collection"), Option(2, "Crop inspection and recording"), Option(3, "Land division"), Option(4, "Village election")),
            correctOptionIndex = 1,
            explanation = """Solution: Girdawari is the periodic crop inspection conducted by the Patwari to record which crops are sown in which parcels of land.""",
            explanationHindi = """समाधान: गिरदावरी पटवारी द्वारा किया जाने वाला आवधिक फसल निरीक्षण है जो यह रिकॉर्ड करता है कि भूमि के किस पार्सल में कौन सी फसल बोई गई है।
Expert Advice: The timings of Kharif, Rabi, and Zaid Girdawari as outlined in the Land Records Manual should be known.
विशेषज्ञ की सलाह: भू-अभिलेख नियमावली में उल्लिखित खरीफ, रबी और जायद गिरदावरी के समय का ज्ञान होना चाहिए।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 13,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 61,
            sectionId = "sec_a",
            questionNumber = 61,
            statementText = """Q61. Identify the next number in the series: 2, 6, 12, 20, ?""",
            statementTextHindi = """प्रश्न 61. श्रृंखला में अगली संख्या पहचानें: 2, 6, 12, 20, ?""",
            options = listOf(Option(1, "24"), Option(2, "28"), Option(3, "30"), Option(4, "32")),
            correctOptionIndex = 2,
            explanation = """Solution: The series follows the pattern: 1x2=2, 2x3=6, 3x4=12, 4x5=20. The next term is 5x6=30.""",
            explanationHindi = """समाधान: यह श्रृंखला इस पैटर्न का पालन करती है: 1x2=2, 2x3=6, 3x4=12, 4x5=20। अगला पद 5x6=30 है।
Expert Advice: Master basic multiplication series and differences between consecutive numbers for quick logical reasoning.
विशेषज्ञ की सलाह: त्वरित तार्किक तर्क के लिए बुनियादी गुणा श्रृंखला और लगातार संख्याओं के बीच के अंतर में महारत हासिल करें।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 20,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 62,
            sectionId = "sec_a",
            questionNumber = 62,
            statementText = """Q62. Who is known to have founded the historical city of Gwalior?""",
            statementTextHindi = """प्रश्न 62. ऐतिहासिक शहर ग्वालियर की स्थापना किसने की थी?""",
            options = listOf(Option(1, "Raja Man Singh Tomar"), Option(2, "Suraj Sen"), Option(3, "Mahadji Scindia"), Option(4, "Tatya Tope")),
            correctOptionIndex = 1,
            explanation = """Solution: Gwalior was founded by King Suraj Sen in the 8th century, named after the sage Gwalipa who cured his leprosy.""",
            explanationHindi = """समाधान: ग्वालियर की स्थापना 8वीं शताब्दी में राजा सूरज सेन ने की थी, जिसका नाम ऋषि ग्वालिपा के नाम पर रखा गया था जिन्होंने उनके कुष्ठ रोग को ठीक किया था।
Expert Advice: Local history of MP, especially concerning historical capitals like Gwalior, Indore, and Bhopal, is crucial for Paper 1.
विशेषज्ञ की सलाह: एमपी का स्थानीय इतिहास, विशेष रूप से ग्वालियर, इंदौर और भोपाल जैसी ऐतिहासिक राजधानियों के संबंध में, पेपर 1 के लिए महत्वपूर्ण है।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 27,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 63,
            sectionId = "sec_a",
            questionNumber = 63,
            statementText = """Q63. Which of the following Sandhi (Join) is present in the word 'Suryoday' (सूर्योदय)?""",
            statementTextHindi = """प्रश्न 63. 'सूर्योदय' शब्द में निम्नलिखित में से कौन सी संधि है?""",
            options = listOf(Option(1, "Dirgha Sandhi"), Option(2, "Guna Sandhi"), Option(3, "Vriddhi Sandhi"), Option(4, "Yan Sandhi")),
            correctOptionIndex = 1,
            explanation = """Solution: Suryoday is formed by Surya + Uday (अ + उ = ओ), which is a classic example of Guna Sandhi.""",
            explanationHindi = """समाधान: सूर्योदय, सूर्य + उदय (अ + उ = ओ) से बना है, जो गुण संधि का एक उत्कृष्ट उदाहरण है।
Expert Advice: Practice the basic rules of Swar Sandhi (Vowel Sandhi) as at least 1-2 questions are standard in General Hindi.
विशेषज्ञ की सलाह: स्वर संधि के बुनियादी नियमों का अभ्यास करें क्योंकि सामान्य हिंदी में कम से कम 1-2 प्रश्न मानक होते हैं।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 34,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 64,
            sectionId = "sec_a",
            questionNumber = 64,
            statementText = """Q64. Choose the correct preposition: 'She is very good ___ mathematics.'""",
            statementTextHindi = """प्रश्न 64. सही पूर्वसर्ग (preposition) चुनें: 'She is very good ___ mathematics.'""",
            options = listOf(Option(1, "in"), Option(2, "with"), Option(3, "at"), Option(4, "about")),
            correctOptionIndex = 2,
            explanation = """Solution: The correct preposition to use with 'good' when talking about skills or abilities is 'at' (good at mathematics).""",
            explanationHindi = """समाधान: कौशल या क्षमताओं के बारे में बात करते समय 'good' के साथ उपयोग करने के लिए सही प्रीपोज़िशन 'at' है (good at mathematics)।
Expert Advice: Prepositions with fixed adjectives (good at, afraid of, interested in) are highly tested in MP exams.
विशेषज्ञ की सलाह: निश्चित विशेषणों (good at, afraid of, interested in) वाले पूर्वसर्गों का एमपी परीक्षाओं में अत्यधिक परीक्षण किया जाता है।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 41,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 65,
            sectionId = "sec_a",
            questionNumber = 65,
            statementText = """Q65. If a shopkeeper sells an item for Rs 120 and makes a 20% profit, what was the cost price of the item?""",
            statementTextHindi = """प्रश्न 65. यदि कोई दुकानदार किसी वस्तु को 120 रुपये में बेचता है और 20% का लाभ कमाता है, तो वस्तु का क्रय मूल्य क्या था?""",
            options = listOf(Option(1, "Rs 96"), Option(2, "Rs 100"), Option(3, "Rs 108"), Option(4, "Rs 144")),
            correctOptionIndex = 1,
            explanation = """Solution: Selling Price (SP) = Cost Price (CP) + 20% of CP. 120 = 1.2 * CP. Therefore, CP = 120 / 1.2 = 100.""",
            explanationHindi = """समाधान: विक्रय मूल्य (SP) = क्रय मूल्य (CP) + CP का 20%। 120 = 1.2 * CP. इसलिए, CP = 120 / 1.2 = 100.
Expert Advice: Memorize fraction equivalents of percentages (20% = 1/5) to solve Profit & Loss questions mentally.
विशेषज्ञ की सलाह: लाभ और हानि के प्रश्नों को मानसिक रूप से हल करने के लिए प्रतिशत के अंश समतुल्य (20% = 1/5) याद रखें।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 48,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 66,
            sectionId = "sec_a",
            questionNumber = 66,
            statementText = """Q66. Which vitamin is scientifically known as Ascorbic Acid?""",
            statementTextHindi = """प्रश्न 66. किस विटामिन को वैज्ञानिक रूप से एस्कॉर्बिक एसिड के रूप में जाना जाता है?""",
            options = listOf(Option(1, "Vitamin A"), Option(2, "Vitamin B12"), Option(3, "Vitamin C"), Option(4, "Vitamin D")),
            correctOptionIndex = 2,
            explanation = """Solution: Vitamin C is known as Ascorbic Acid. Its deficiency causes Scurvy.""",
            explanationHindi = """समाधान: विटामिन C को एस्कॉर्बिक एसिड के रूप में जाना जाता है। इसकी कमी से स्कर्वी रोग होता है।
Expert Advice: Learn the chemical names and deficiency diseases for all essential vitamins (A, B-complex, C, D, E, K).
विशेषज्ञ की सलाह: सभी आवश्यक विटामिनों (A, B-कॉम्प्लेक्स, C, D, E, K) के रासायनिक नाम और कमी से होने वाले रोगों को याद करें।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 55,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 67,
            sectionId = "sec_a",
            questionNumber = 67,
            statementText = """Q67. In computer terminology, what is the full form of RAM?""",
            statementTextHindi = """प्रश्न 67. कंप्यूटर शब्दावली में RAM का पूर्ण रूप क्या है?""",
            options = listOf(Option(1, "Read Access Memory"), Option(2, "Random Access Memory"), Option(3, "Run Access Memory"), Option(4, "Random Arithmetic Memory")),
            correctOptionIndex = 1,
            explanation = """Solution: RAM stands for Random Access Memory. It is the primary volatile memory used by the computer to store data temporarily.""",
            explanationHindi = """समाधान: RAM का मतलब रैंडम एक्सेस मेमोरी है। यह कंप्यूटर द्वारा डेटा को अस्थायी रूप से संग्रहीत करने के लिए उपयोग की जाने वाली प्राथमिक अस्थिर (volatile) मेमोरी है।
Expert Advice: Basic hardware components and their abbreviations are a staple of the General Computer Knowledge section.
विशेषज्ञ की सलाह: बुनियादी हार्डवेयर घटक और उनके संक्षिप्त नाम सामान्य कंप्यूटर ज्ञान अनुभाग का एक प्रमुख हिस्सा हैं।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 62,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 68,
            sectionId = "sec_a",
            questionNumber = 68,
            statementText = """Q68. Under the MP Civil Services (Leave) Rules, 1977, what is the maximum number of days of Earned Leave (EL) that can be accumulated by a government servant?""",
            statementTextHindi = """प्रश्न 68. म.प्र. सिविल सेवा (अवकाश) नियम, 1977 के तहत, किसी सरकारी सेवक द्वारा अधिकतम कितने दिनों का अर्जित अवकाश (EL) संचित किया जा सकता है?""",
            options = listOf(Option(1, "180 days"), Option(2, "240 days"), Option(3, "300 days"), Option(4, "365 days")),
            correctOptionIndex = 2,
            explanation = """Solution: A state government employee in MP can accumulate a maximum of 300 days of Earned Leave in their service tenure, which can be encashed at retirement.""",
            explanationHindi = """समाधान: एमपी में एक राज्य सरकार का कर्मचारी अपने सेवा कार्यकाल में अधिकतम 300 दिनों का अर्जित अवकाश संचित कर सकता है, जिसे सेवानिवृत्ति पर भुनाया जा सकता है।
Expert Advice: Read the MP Leave Rules carefully, particularly regarding EL, Casual Leave (CL), and Maternity/Paternity leave durations.
विशेषज्ञ की सलाह: म.प्र. अवकाश नियमों को ध्यान से पढ़ें, विशेष रूप से EL, आकस्मिक अवकाश (CL), और मातृत्व/पितृत्व अवकाश की अवधि के संबंध में।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 69,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 69,
            sectionId = "sec_a",
            questionNumber = 69,
            statementText = """Q69. According to MP Govt Service Rules, female government employees are entitled to maternity leave for how many days?""",
            statementTextHindi = """प्रश्न 69. म.प्र. सरकारी सेवा नियमों के अनुसार, महिला सरकारी कर्मचारी कितने दिनों के मातृत्व अवकाश की हकदार हैं?""",
            options = listOf(Option(1, "90 days"), Option(2, "135 days"), Option(3, "180 days"), Option(4, "240 days")),
            correctOptionIndex = 2,
            explanation = """Solution: Female government servants in MP are entitled to 180 days of maternity leave up to two living children.""",
            explanationHindi = """समाधान: म.प्र. में महिला सरकारी कर्मचारी दो जीवित बच्चों तक 180 दिनों के मातृत्व अवकाश की हकदार हैं।
Expert Advice: Leave policies for female employees and child care leave (CCL) are frequently asked in the Govt Service Rules paper.
विशेषज्ञ की सलाह: महिला कर्मचारियों के लिए अवकाश नीतियां और चाइल्ड केयर लीव (CCL) अक्सर सरकारी सेवा नियम के पेपर में पूछे जाते हैं।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 76,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 70,
            sectionId = "sec_a",
            questionNumber = 70,
            statementText = """Q70. Under the MP Civil Services (Conduct) Rules 1965, can a government servant take part in political elections?""",
            statementTextHindi = """प्रश्न 70. म.प्र. सिविल सेवा (आचरण) नियम 1965 के तहत, क्या कोई सरकारी कर्मचारी राजनीतिक चुनावों में भाग ले सकता है?""",
            options = listOf(Option(1, "Yes, with permission"), Option(2, "Yes, during leave"), Option(3, "No, it is completely prohibited"), Option(4, "Yes, if fighting as independent")),
            correctOptionIndex = 2,
            explanation = """Solution: Rule 5 of the MP Civil Services (Conduct) Rules strictly prohibits any government servant from taking part in politics or elections.""",
            explanationHindi = """समाधान: म.प्र. सिविल सेवा (आचरण) नियम का नियम 5 किसी भी सरकारी सेवक को राजनीति या चुनाव में भाग लेने से सख्ती से रोकता है।
Expert Advice: The Conduct Rules clearly outline prohibitions regarding politics, press, and unauthorized communication. Review Rule 5 and Rule 7 closely.
विशेषज्ञ की सलाह: आचरण नियम राजनीति, प्रेस और अनधिकृत संचार के संबंध में प्रतिबंधों को स्पष्ट रूप से रेखांकित करते हैं। नियम 5 और नियम 7 की बारीकी से समीक्षा करें।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 83,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 71,
            sectionId = "sec_a",
            questionNumber = 71,
            statementText = """Q71. Dearness Allowance (DA) is provided to government employees primarily to compensate for?""",
            statementTextHindi = """प्रश्न 71. महंगाई भत्ता (DA) सरकारी कर्मचारियों को मुख्य रूप से किसकी भरपाई के लिए प्रदान किया जाता है?""",
            options = listOf(Option(1, "Travel expenses"), Option(2, "Housing costs"), Option(3, "Medical expenses"), Option(4, "Inflation / Cost of living")),
            correctOptionIndex = 3,
            explanation = """Solution: Dearness Allowance is calculated as a percentage of basic salary to mitigate the impact of inflation on employees.""",
            explanationHindi = """समाधान: महंगाई भत्ते की गणना कर्मचारियों पर मुद्रास्फीति के प्रभाव को कम करने के लिए मूल वेतन के प्रतिशत के रूप में की जाती है।
Expert Advice: Basic understanding of Salary structures (Basic, DA, HRA) is a crucial part of the 30-mark Govt Service Rules section.
विशेषज्ञ की सलाह: वेतन संरचनाओं (Basic, DA, HRA) की बुनियादी समझ 30-अंकों वाले सरकारी सेवा नियम अनुभाग का एक महत्वपूर्ण हिस्सा है।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 90,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 72,
            sectionId = "sec_a",
            questionNumber = 72,
            statementText = """Q72. In the context of MP Revenue Terminology, what does the term 'Khasra' signify?""",
            statementTextHindi = """प्रश्न 72. म.प्र. राजस्व शब्दावली के संदर्भ में, 'खसरा' शब्द का क्या अर्थ है?""",
            options = listOf(Option(1, "A tax collection receipt"), Option(2, "A village map"), Option(3, "A primary agricultural record containing plot details"), Option(4, "A type of land revenue tax")),
            correctOptionIndex = 2,
            explanation = """Solution: Khasra is a primary land record document that contains details of the land parcel, crop grown, soil type, and the cultivator's name.""",
            explanationHindi = """समाधान: खसरा एक प्राथमिक भूमि रिकॉर्ड दस्तावेज़ है जिसमें भूमि पार्सल, उगाई गई फसल, मिट्टी के प्रकार और कृषक के नाम का विवरण होता है।
Expert Advice: Differentiate clearly between Khasra (plot details), Khatauni (holding details of a person), and Shajra (village map).
विशेषज्ञ की सलाह: खसरा (भूखंड विवरण), खतौनी (किसी व्यक्ति के जोत का विवरण), और शजरा (गाँव का नक्शा) के बीच स्पष्ट रूप से अंतर करें।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 97,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 73,
            sectionId = "sec_a",
            questionNumber = 73,
            statementText = """Q73. What is meant by 'Nazul' land in the revenue department?""",
            statementTextHindi = """प्रश्न 73. राजस्व विभाग में 'नज़ूल' भूमि से क्या तात्पर्य है?""",
            options = listOf(Option(1, "Private agricultural land"), Option(2, "Forest department land"), Option(3, "State-owned land situated within or near urban areas"), Option(4, "Irrigated land")),
            correctOptionIndex = 2,
            explanation = """Solution: Nazul lands are state-owned plots situated within municipal/urban limits often leased out for residential or commercial purposes.""",
            explanationHindi = """समाधान: नज़ूल भूमि नगरपालिका/शहरी सीमा के भीतर स्थित राज्य के स्वामित्व वाले भूखंड हैं जिन्हें अक्सर आवासीय या व्यावसायिक उद्देश्यों के लिए पट्टे पर दिया जाता है।
Expert Advice: Nazul rules and leasing provisions are essential parts of the Revenue Book Circular (RBC).
विशेषज्ञ की सलाह: नज़ूल नियम और पट्टे के प्रावधान राजस्व पुस्तक परिपत्र (RBC) के आवश्यक भाग हैं।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 4,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 74,
            sectionId = "sec_a",
            questionNumber = 74,
            statementText = """Q74. Under which section of the MP Land Revenue Code, 1959 is the 'Board of Revenue' established?""",
            statementTextHindi = """प्रश्न 74. म.प्र. भू-राजस्व संहिता, 1959 की किस धारा के तहत 'राजस्व मंडल' की स्थापना की गई है?""",
            options = listOf(Option(1, "Section 2"), Option(2, "Section 3"), Option(3, "Section 11"), Option(4, "Section 158")),
            correctOptionIndex = 1,
            explanation = """Solution: Section 3 of the MPLRC, 1959 provisions for the constitution of the Board of Revenue, the highest revenue court in the state.""",
            explanationHindi = """समाधान: MPLRC, 1959 की धारा 3 राजस्व मंडल के गठन का प्रावधान करती है, जो राज्य का सर्वोच्च राजस्व न्यायालय है।
Expert Advice: Memorize the key sections of MPLRC dealing with authorities (Sec 11) and Bhumiswami rights (Sec 158).
विशेषज्ञ की सलाह: अधिकारियों (धारा 11) और भूमिस्वामी अधिकारों (धारा 158) से निपटने वाली MPLRC की प्रमुख धाराओं को याद रखें।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 11,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 75,
            sectionId = "sec_a",
            questionNumber = 75,
            statementText = """Q75. Where is the principal seat or headquarters of the Board of Revenue in Madhya Pradesh located?""",
            statementTextHindi = """प्रश्न 75. मध्य प्रदेश में राजस्व मंडल का प्रमुख स्थान या मुख्यालय कहाँ स्थित है?""",
            options = listOf(Option(1, "Bhopal"), Option(2, "Indore"), Option(3, "Gwalior"), Option(4, "Jabalpur")),
            correctOptionIndex = 2,
            explanation = """Solution: The Board of Revenue (Rajya Swamandal) of Madhya Pradesh is headquartered in Gwalior.""",
            explanationHindi = """समाधान: मध्य प्रदेश के राजस्व मंडल का मुख्यालय ग्वालियर में है।
Expert Advice: While the political capital is Bhopal, key institutions like the High Court (Jabalpur) and Board of Revenue (Gwalior) are elsewhere.
विशेषज्ञ की सलाह: जबकि राजनीतिक राजधानी भोपाल है, उच्च न्यायालय (जबलपुर) और राजस्व मंडल (ग्वालियर) जैसे प्रमुख संस्थान अन्य स्थानों पर हैं।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 18,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 76,
            sectionId = "sec_a",
            questionNumber = 76,
            statementText = """Q76. The 'Bhu-Adhikar Pustika' (Kisan Book) is provided to a Bhumiswami under which section of MPLRC 1959?""",
            statementTextHindi = """प्रश्न 76. MPLRC 1959 की किस धारा के तहत एक भूमिस्वामी को 'भू-अधिकार पुस्तिका' (किसान बुक) प्रदान की जाती है?""",
            options = listOf(Option(1, "Section 108"), Option(2, "Section 114"), Option(3, "Section 158"), Option(4, "Section 242")),
            correctOptionIndex = 1,
            explanation = """Solution: Section 114 of the code mandates the provision of Bhu-Adhikar Pustika to every Bhumiswami containing details of their holdings.""",
            explanationHindi = """समाधान: संहिता की धारा 114 प्रत्येक भूमिस्वामी को भू-अधिकार पुस्तिका के प्रावधान को अनिवार्य करती है जिसमें उनकी जोत का विवरण होता है।
Expert Advice: Sections relating to Record of Rights (Sec 108) and Bhu-Adhikar Pustika (Sec 114) are very high-yield for exams.
विशेषज्ञ की सलाह: अधिकारों के अभिलेख (धारा 108) और भू-अधिकार पुस्तिका (धारा 114) से संबंधित धाराएं परीक्षाओं के लिए बहुत महत्वपूर्ण हैं।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 25,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 77,
            sectionId = "sec_a",
            questionNumber = 77,
            statementText = """Q77. What does the term 'Wajib-ul-arz' refer to under Section 242 of the MPLRC 1959?""",
            statementTextHindi = """प्रश्न 77. MPLRC 1959 की धारा 242 के तहत 'वाजिब-उल-अर्ज़' शब्द का क्या अर्थ है?""",
            options = listOf(Option(1, "Record of customs in a village"), Option(2, "Tax receipt"), Option(3, "Land transfer deed"), Option(4, "Dispute register")),
            correctOptionIndex = 0,
            explanation = """Solution: Wajib-ul-arz is a record prepared by the Sub-Divisional Officer detailing the customs existing in a village.""",
            explanationHindi = """समाधान: वाजिब-उल-अर्ज़ अनुविभागीय अधिकारी द्वारा तैयार किया गया एक रिकॉर्ड है जिसमें गाँव में मौजूद रिवाजों का विवरण होता है।
Expert Advice: Traditional revenue terms from the Mughal/British era are preserved in MPLRC and must be memorized.
विशेषज्ञ की सलाह: मुगल/ब्रिटिश काल के पारंपरिक राजस्व शब्द MPLRC में संरक्षित हैं और इन्हें याद किया जाना चाहिए।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 32,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 78,
            sectionId = "sec_a",
            questionNumber = 78,
            statementText = """Q78. Which part of the Revenue Book Circular (RBC) deals exclusively with providing relief in case of natural calamities?""",
            statementTextHindi = """प्रश्न 78. राजस्व पुस्तक परिपत्र (RBC) का कौन सा भाग विशेष रूप से प्राकृतिक आपदाओं के मामले में राहत प्रदान करने से संबंधित है?""",
            options = listOf(Option(1, "RBC 6-4"), Option(2, "RBC 4-1"), Option(3, "RBC 3-2"), Option(4, "RBC 5-3")),
            correctOptionIndex = 0,
            explanation = """Solution: RBC 6-4 contains provisions and guidelines for financial assistance to citizens affected by natural disasters like drought, flood, or fire.""",
            explanationHindi = """समाधान: RBC 6-4 में सूखा, बाढ़ या आग जैसी प्राकृतिक आपदाओं से प्रभावित नागरिकों को वित्तीय सहायता के प्रावधान और दिशानिर्देश शामिल हैं।
Expert Advice: RBC 6-4 is heavily utilized by Patwaris and Revenue Inspectors in the field. Questions on this are almost guaranteed.
विशेषज्ञ की सलाह: RBC 6-4 का उपयोग मैदान में पटवारियों और राजस्व निरीक्षकों द्वारा भारी मात्रा में किया जाता है। इस पर प्रश्न लगभग तय हैं।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 39,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 79,
            sectionId = "sec_a",
            questionNumber = 79,
            statementText = """Q79. According to the Land Records Manual, who is the field-level official primarily responsible for maintaining the village map (Naksha)?""",
            statementTextHindi = """प्रश्न 79. भू-अभिलेख नियमावली के अनुसार, गाँव के नक्शे (नक्शा) को बनाए रखने के लिए मुख्य रूप से कौन सा मैदानी स्तर का अधिकारी जिम्मेदार है?""",
            options = listOf(Option(1, "Tehsildar"), Option(2, "Revenue Inspector (RI)"), Option(3, "Patwari"), Option(4, "Sub-Divisional Officer (SDO)")),
            correctOptionIndex = 2,
            explanation = """Solution: The Patwari (Halka Patwari) is the foundational revenue official responsible for ground-level land record maintenance and crop inspection.""",
            explanationHindi = """समाधान: पटवारी (हल्का पटवारी) जमीनी स्तर पर भूमि रिकॉर्ड के रखरखाव और फसल निरीक्षण के लिए जिम्मेदार आधारभूत राजस्व अधिकारी है।
Expert Advice: Understand the hierarchy of the Revenue Department: Patwari -> RI -> Naib Tehsildar -> Tehsildar -> SDO -> Collector.
विशेषज्ञ की सलाह: राजस्व विभाग के पदानुक्रम को समझें: पटवारी -> आरआई -> नायब तहसीलदार -> तहसीलदार -> एसडीओ -> कलेक्टर।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 46,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 80,
            sectionId = "sec_a",
            questionNumber = 80,
            statementText = """Q80. What is the primary function of the 'Girdawari' process in MP Revenue operations?""",
            statementTextHindi = """प्रश्न 80. म.प्र. राजस्व संचालन में 'गिरदावरी' प्रक्रिया का प्राथमिक कार्य क्या है?""",
            options = listOf(Option(1, "Tax collection"), Option(2, "Crop inspection and recording"), Option(3, "Land division"), Option(4, "Village election")),
            correctOptionIndex = 1,
            explanation = """Solution: Girdawari is the periodic crop inspection conducted by the Patwari to record which crops are sown in which parcels of land.""",
            explanationHindi = """समाधान: गिरदावरी पटवारी द्वारा किया जाने वाला आवधिक फसल निरीक्षण है जो यह रिकॉर्ड करता है कि भूमि के किस पार्सल में कौन सी फसल बोई गई है।
Expert Advice: The timings of Kharif, Rabi, and Zaid Girdawari as outlined in the Land Records Manual should be known.
विशेषज्ञ की सलाह: भू-अभिलेख नियमावली में उल्लिखित खरीफ, रबी और जायद गिरदावरी के समय का ज्ञान होना चाहिए।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 53,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 81,
            sectionId = "sec_a",
            questionNumber = 81,
            statementText = """Q81. Identify the next number in the series: 2, 6, 12, 20, ?""",
            statementTextHindi = """प्रश्न 81. श्रृंखला में अगली संख्या पहचानें: 2, 6, 12, 20, ?""",
            options = listOf(Option(1, "24"), Option(2, "28"), Option(3, "30"), Option(4, "32")),
            correctOptionIndex = 2,
            explanation = """Solution: The series follows the pattern: 1x2=2, 2x3=6, 3x4=12, 4x5=20. The next term is 5x6=30.""",
            explanationHindi = """समाधान: यह श्रृंखला इस पैटर्न का पालन करती है: 1x2=2, 2x3=6, 3x4=12, 4x5=20। अगला पद 5x6=30 है।
Expert Advice: Master basic multiplication series and differences between consecutive numbers for quick logical reasoning.
विशेषज्ञ की सलाह: त्वरित तार्किक तर्क के लिए बुनियादी गुणा श्रृंखला और लगातार संख्याओं के बीच के अंतर में महारत हासिल करें।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 60,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 82,
            sectionId = "sec_a",
            questionNumber = 82,
            statementText = """Q82. Who is known to have founded the historical city of Gwalior?""",
            statementTextHindi = """प्रश्न 82. ऐतिहासिक शहर ग्वालियर की स्थापना किसने की थी?""",
            options = listOf(Option(1, "Raja Man Singh Tomar"), Option(2, "Suraj Sen"), Option(3, "Mahadji Scindia"), Option(4, "Tatya Tope")),
            correctOptionIndex = 1,
            explanation = """Solution: Gwalior was founded by King Suraj Sen in the 8th century, named after the sage Gwalipa who cured his leprosy.""",
            explanationHindi = """समाधान: ग्वालियर की स्थापना 8वीं शताब्दी में राजा सूरज सेन ने की थी, जिसका नाम ऋषि ग्वालिपा के नाम पर रखा गया था जिन्होंने उनके कुष्ठ रोग को ठीक किया था।
Expert Advice: Local history of MP, especially concerning historical capitals like Gwalior, Indore, and Bhopal, is crucial for Paper 1.
विशेषज्ञ की सलाह: एमपी का स्थानीय इतिहास, विशेष रूप से ग्वालियर, इंदौर और भोपाल जैसी ऐतिहासिक राजधानियों के संबंध में, पेपर 1 के लिए महत्वपूर्ण है।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 67,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 83,
            sectionId = "sec_a",
            questionNumber = 83,
            statementText = """Q83. Which of the following Sandhi (Join) is present in the word 'Suryoday' (सूर्योदय)?""",
            statementTextHindi = """प्रश्न 83. 'सूर्योदय' शब्द में निम्नलिखित में से कौन सी संधि है?""",
            options = listOf(Option(1, "Dirgha Sandhi"), Option(2, "Guna Sandhi"), Option(3, "Vriddhi Sandhi"), Option(4, "Yan Sandhi")),
            correctOptionIndex = 1,
            explanation = """Solution: Suryoday is formed by Surya + Uday (अ + उ = ओ), which is a classic example of Guna Sandhi.""",
            explanationHindi = """समाधान: सूर्योदय, सूर्य + उदय (अ + उ = ओ) से बना है, जो गुण संधि का एक उत्कृष्ट उदाहरण है।
Expert Advice: Practice the basic rules of Swar Sandhi (Vowel Sandhi) as at least 1-2 questions are standard in General Hindi.
विशेषज्ञ की सलाह: स्वर संधि के बुनियादी नियमों का अभ्यास करें क्योंकि सामान्य हिंदी में कम से कम 1-2 प्रश्न मानक होते हैं।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 74,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 84,
            sectionId = "sec_a",
            questionNumber = 84,
            statementText = """Q84. Choose the correct preposition: 'She is very good ___ mathematics.'""",
            statementTextHindi = """प्रश्न 84. सही पूर्वसर्ग (preposition) चुनें: 'She is very good ___ mathematics.'""",
            options = listOf(Option(1, "in"), Option(2, "with"), Option(3, "at"), Option(4, "about")),
            correctOptionIndex = 2,
            explanation = """Solution: The correct preposition to use with 'good' when talking about skills or abilities is 'at' (good at mathematics).""",
            explanationHindi = """समाधान: कौशल या क्षमताओं के बारे में बात करते समय 'good' के साथ उपयोग करने के लिए सही प्रीपोज़िशन 'at' है (good at mathematics)।
Expert Advice: Prepositions with fixed adjectives (good at, afraid of, interested in) are highly tested in MP exams.
विशेषज्ञ की सलाह: निश्चित विशेषणों (good at, afraid of, interested in) वाले पूर्वसर्गों का एमपी परीक्षाओं में अत्यधिक परीक्षण किया जाता है।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 81,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 85,
            sectionId = "sec_a",
            questionNumber = 85,
            statementText = """Q85. If a shopkeeper sells an item for Rs 120 and makes a 20% profit, what was the cost price of the item?""",
            statementTextHindi = """प्रश्न 85. यदि कोई दुकानदार किसी वस्तु को 120 रुपये में बेचता है और 20% का लाभ कमाता है, तो वस्तु का क्रय मूल्य क्या था?""",
            options = listOf(Option(1, "Rs 96"), Option(2, "Rs 100"), Option(3, "Rs 108"), Option(4, "Rs 144")),
            correctOptionIndex = 1,
            explanation = """Solution: Selling Price (SP) = Cost Price (CP) + 20% of CP. 120 = 1.2 * CP. Therefore, CP = 120 / 1.2 = 100.""",
            explanationHindi = """समाधान: विक्रय मूल्य (SP) = क्रय मूल्य (CP) + CP का 20%। 120 = 1.2 * CP. इसलिए, CP = 120 / 1.2 = 100.
Expert Advice: Memorize fraction equivalents of percentages (20% = 1/5) to solve Profit & Loss questions mentally.
विशेषज्ञ की सलाह: लाभ और हानि के प्रश्नों को मानसिक रूप से हल करने के लिए प्रतिशत के अंश समतुल्य (20% = 1/5) याद रखें।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 88,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 86,
            sectionId = "sec_a",
            questionNumber = 86,
            statementText = """Q86. Which vitamin is scientifically known as Ascorbic Acid?""",
            statementTextHindi = """प्रश्न 86. किस विटामिन को वैज्ञानिक रूप से एस्कॉर्बिक एसिड के रूप में जाना जाता है?""",
            options = listOf(Option(1, "Vitamin A"), Option(2, "Vitamin B12"), Option(3, "Vitamin C"), Option(4, "Vitamin D")),
            correctOptionIndex = 2,
            explanation = """Solution: Vitamin C is known as Ascorbic Acid. Its deficiency causes Scurvy.""",
            explanationHindi = """समाधान: विटामिन C को एस्कॉर्बिक एसिड के रूप में जाना जाता है। इसकी कमी से स्कर्वी रोग होता है।
Expert Advice: Learn the chemical names and deficiency diseases for all essential vitamins (A, B-complex, C, D, E, K).
विशेषज्ञ की सलाह: सभी आवश्यक विटामिनों (A, B-कॉम्प्लेक्स, C, D, E, K) के रासायनिक नाम और कमी से होने वाले रोगों को याद करें।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 95,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 87,
            sectionId = "sec_a",
            questionNumber = 87,
            statementText = """Q87. In computer terminology, what is the full form of RAM?""",
            statementTextHindi = """प्रश्न 87. कंप्यूटर शब्दावली में RAM का पूर्ण रूप क्या है?""",
            options = listOf(Option(1, "Read Access Memory"), Option(2, "Random Access Memory"), Option(3, "Run Access Memory"), Option(4, "Random Arithmetic Memory")),
            correctOptionIndex = 1,
            explanation = """Solution: RAM stands for Random Access Memory. It is the primary volatile memory used by the computer to store data temporarily.""",
            explanationHindi = """समाधान: RAM का मतलब रैंडम एक्सेस मेमोरी है। यह कंप्यूटर द्वारा डेटा को अस्थायी रूप से संग्रहीत करने के लिए उपयोग की जाने वाली प्राथमिक अस्थिर (volatile) मेमोरी है।
Expert Advice: Basic hardware components and their abbreviations are a staple of the General Computer Knowledge section.
विशेषज्ञ की सलाह: बुनियादी हार्डवेयर घटक और उनके संक्षिप्त नाम सामान्य कंप्यूटर ज्ञान अनुभाग का एक प्रमुख हिस्सा हैं।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 2,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 88,
            sectionId = "sec_a",
            questionNumber = 88,
            statementText = """Q88. Under the MP Civil Services (Leave) Rules, 1977, what is the maximum number of days of Earned Leave (EL) that can be accumulated by a government servant?""",
            statementTextHindi = """प्रश्न 88. म.प्र. सिविल सेवा (अवकाश) नियम, 1977 के तहत, किसी सरकारी सेवक द्वारा अधिकतम कितने दिनों का अर्जित अवकाश (EL) संचित किया जा सकता है?""",
            options = listOf(Option(1, "180 days"), Option(2, "240 days"), Option(3, "300 days"), Option(4, "365 days")),
            correctOptionIndex = 2,
            explanation = """Solution: A state government employee in MP can accumulate a maximum of 300 days of Earned Leave in their service tenure, which can be encashed at retirement.""",
            explanationHindi = """समाधान: एमपी में एक राज्य सरकार का कर्मचारी अपने सेवा कार्यकाल में अधिकतम 300 दिनों का अर्जित अवकाश संचित कर सकता है, जिसे सेवानिवृत्ति पर भुनाया जा सकता है।
Expert Advice: Read the MP Leave Rules carefully, particularly regarding EL, Casual Leave (CL), and Maternity/Paternity leave durations.
विशेषज्ञ की सलाह: म.प्र. अवकाश नियमों को ध्यान से पढ़ें, विशेष रूप से EL, आकस्मिक अवकाश (CL), और मातृत्व/पितृत्व अवकाश की अवधि के संबंध में।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 9,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 89,
            sectionId = "sec_a",
            questionNumber = 89,
            statementText = """Q89. According to MP Govt Service Rules, female government employees are entitled to maternity leave for how many days?""",
            statementTextHindi = """प्रश्न 89. म.प्र. सरकारी सेवा नियमों के अनुसार, महिला सरकारी कर्मचारी कितने दिनों के मातृत्व अवकाश की हकदार हैं?""",
            options = listOf(Option(1, "90 days"), Option(2, "135 days"), Option(3, "180 days"), Option(4, "240 days")),
            correctOptionIndex = 2,
            explanation = """Solution: Female government servants in MP are entitled to 180 days of maternity leave up to two living children.""",
            explanationHindi = """समाधान: म.प्र. में महिला सरकारी कर्मचारी दो जीवित बच्चों तक 180 दिनों के मातृत्व अवकाश की हकदार हैं।
Expert Advice: Leave policies for female employees and child care leave (CCL) are frequently asked in the Govt Service Rules paper.
विशेषज्ञ की सलाह: महिला कर्मचारियों के लिए अवकाश नीतियां और चाइल्ड केयर लीव (CCL) अक्सर सरकारी सेवा नियम के पेपर में पूछे जाते हैं।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 16,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 90,
            sectionId = "sec_a",
            questionNumber = 90,
            statementText = """Q90. Under the MP Civil Services (Conduct) Rules 1965, can a government servant take part in political elections?""",
            statementTextHindi = """प्रश्न 90. म.प्र. सिविल सेवा (आचरण) नियम 1965 के तहत, क्या कोई सरकारी कर्मचारी राजनीतिक चुनावों में भाग ले सकता है?""",
            options = listOf(Option(1, "Yes, with permission"), Option(2, "Yes, during leave"), Option(3, "No, it is completely prohibited"), Option(4, "Yes, if fighting as independent")),
            correctOptionIndex = 2,
            explanation = """Solution: Rule 5 of the MP Civil Services (Conduct) Rules strictly prohibits any government servant from taking part in politics or elections.""",
            explanationHindi = """समाधान: म.प्र. सिविल सेवा (आचरण) नियम का नियम 5 किसी भी सरकारी सेवक को राजनीति या चुनाव में भाग लेने से सख्ती से रोकता है।
Expert Advice: The Conduct Rules clearly outline prohibitions regarding politics, press, and unauthorized communication. Review Rule 5 and Rule 7 closely.
विशेषज्ञ की सलाह: आचरण नियम राजनीति, प्रेस और अनधिकृत संचार के संबंध में प्रतिबंधों को स्पष्ट रूप से रेखांकित करते हैं। नियम 5 और नियम 7 की बारीकी से समीक्षा करें।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 23,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 91,
            sectionId = "sec_a",
            questionNumber = 91,
            statementText = """Q91. Dearness Allowance (DA) is provided to government employees primarily to compensate for?""",
            statementTextHindi = """प्रश्न 91. महंगाई भत्ता (DA) सरकारी कर्मचारियों को मुख्य रूप से किसकी भरपाई के लिए प्रदान किया जाता है?""",
            options = listOf(Option(1, "Travel expenses"), Option(2, "Housing costs"), Option(3, "Medical expenses"), Option(4, "Inflation / Cost of living")),
            correctOptionIndex = 3,
            explanation = """Solution: Dearness Allowance is calculated as a percentage of basic salary to mitigate the impact of inflation on employees.""",
            explanationHindi = """समाधान: महंगाई भत्ते की गणना कर्मचारियों पर मुद्रास्फीति के प्रभाव को कम करने के लिए मूल वेतन के प्रतिशत के रूप में की जाती है।
Expert Advice: Basic understanding of Salary structures (Basic, DA, HRA) is a crucial part of the 30-mark Govt Service Rules section.
विशेषज्ञ की सलाह: वेतन संरचनाओं (Basic, DA, HRA) की बुनियादी समझ 30-अंकों वाले सरकारी सेवा नियम अनुभाग का एक महत्वपूर्ण हिस्सा है।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 30,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 92,
            sectionId = "sec_a",
            questionNumber = 92,
            statementText = """Q92. In the context of MP Revenue Terminology, what does the term 'Khasra' signify?""",
            statementTextHindi = """प्रश्न 92. म.प्र. राजस्व शब्दावली के संदर्भ में, 'खसरा' शब्द का क्या अर्थ है?""",
            options = listOf(Option(1, "A tax collection receipt"), Option(2, "A village map"), Option(3, "A primary agricultural record containing plot details"), Option(4, "A type of land revenue tax")),
            correctOptionIndex = 2,
            explanation = """Solution: Khasra is a primary land record document that contains details of the land parcel, crop grown, soil type, and the cultivator's name.""",
            explanationHindi = """समाधान: खसरा एक प्राथमिक भूमि रिकॉर्ड दस्तावेज़ है जिसमें भूमि पार्सल, उगाई गई फसल, मिट्टी के प्रकार और कृषक के नाम का विवरण होता है।
Expert Advice: Differentiate clearly between Khasra (plot details), Khatauni (holding details of a person), and Shajra (village map).
विशेषज्ञ की सलाह: खसरा (भूखंड विवरण), खतौनी (किसी व्यक्ति के जोत का विवरण), और शजरा (गाँव का नक्शा) के बीच स्पष्ट रूप से अंतर करें।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 37,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 93,
            sectionId = "sec_a",
            questionNumber = 93,
            statementText = """Q93. What is meant by 'Nazul' land in the revenue department?""",
            statementTextHindi = """प्रश्न 93. राजस्व विभाग में 'नज़ूल' भूमि से क्या तात्पर्य है?""",
            options = listOf(Option(1, "Private agricultural land"), Option(2, "Forest department land"), Option(3, "State-owned land situated within or near urban areas"), Option(4, "Irrigated land")),
            correctOptionIndex = 2,
            explanation = """Solution: Nazul lands are state-owned plots situated within municipal/urban limits often leased out for residential or commercial purposes.""",
            explanationHindi = """समाधान: नज़ूल भूमि नगरपालिका/शहरी सीमा के भीतर स्थित राज्य के स्वामित्व वाले भूखंड हैं जिन्हें अक्सर आवासीय या व्यावसायिक उद्देश्यों के लिए पट्टे पर दिया जाता है।
Expert Advice: Nazul rules and leasing provisions are essential parts of the Revenue Book Circular (RBC).
विशेषज्ञ की सलाह: नज़ूल नियम और पट्टे के प्रावधान राजस्व पुस्तक परिपत्र (RBC) के आवश्यक भाग हैं।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 44,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 94,
            sectionId = "sec_a",
            questionNumber = 94,
            statementText = """Q94. Under which section of the MP Land Revenue Code, 1959 is the 'Board of Revenue' established?""",
            statementTextHindi = """प्रश्न 94. म.प्र. भू-राजस्व संहिता, 1959 की किस धारा के तहत 'राजस्व मंडल' की स्थापना की गई है?""",
            options = listOf(Option(1, "Section 2"), Option(2, "Section 3"), Option(3, "Section 11"), Option(4, "Section 158")),
            correctOptionIndex = 1,
            explanation = """Solution: Section 3 of the MPLRC, 1959 provisions for the constitution of the Board of Revenue, the highest revenue court in the state.""",
            explanationHindi = """समाधान: MPLRC, 1959 की धारा 3 राजस्व मंडल के गठन का प्रावधान करती है, जो राज्य का सर्वोच्च राजस्व न्यायालय है।
Expert Advice: Memorize the key sections of MPLRC dealing with authorities (Sec 11) and Bhumiswami rights (Sec 158).
विशेषज्ञ की सलाह: अधिकारियों (धारा 11) और भूमिस्वामी अधिकारों (धारा 158) से निपटने वाली MPLRC की प्रमुख धाराओं को याद रखें।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 51,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 95,
            sectionId = "sec_a",
            questionNumber = 95,
            statementText = """Q95. Where is the principal seat or headquarters of the Board of Revenue in Madhya Pradesh located?""",
            statementTextHindi = """प्रश्न 95. मध्य प्रदेश में राजस्व मंडल का प्रमुख स्थान या मुख्यालय कहाँ स्थित है?""",
            options = listOf(Option(1, "Bhopal"), Option(2, "Indore"), Option(3, "Gwalior"), Option(4, "Jabalpur")),
            correctOptionIndex = 2,
            explanation = """Solution: The Board of Revenue (Rajya Swamandal) of Madhya Pradesh is headquartered in Gwalior.""",
            explanationHindi = """समाधान: मध्य प्रदेश के राजस्व मंडल का मुख्यालय ग्वालियर में है।
Expert Advice: While the political capital is Bhopal, key institutions like the High Court (Jabalpur) and Board of Revenue (Gwalior) are elsewhere.
विशेषज्ञ की सलाह: जबकि राजनीतिक राजधानी भोपाल है, उच्च न्यायालय (जबलपुर) और राजस्व मंडल (ग्वालियर) जैसे प्रमुख संस्थान अन्य स्थानों पर हैं।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 58,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 96,
            sectionId = "sec_a",
            questionNumber = 96,
            statementText = """Q96. The 'Bhu-Adhikar Pustika' (Kisan Book) is provided to a Bhumiswami under which section of MPLRC 1959?""",
            statementTextHindi = """प्रश्न 96. MPLRC 1959 की किस धारा के तहत एक भूमिस्वामी को 'भू-अधिकार पुस्तिका' (किसान बुक) प्रदान की जाती है?""",
            options = listOf(Option(1, "Section 108"), Option(2, "Section 114"), Option(3, "Section 158"), Option(4, "Section 242")),
            correctOptionIndex = 1,
            explanation = """Solution: Section 114 of the code mandates the provision of Bhu-Adhikar Pustika to every Bhumiswami containing details of their holdings.""",
            explanationHindi = """समाधान: संहिता की धारा 114 प्रत्येक भूमिस्वामी को भू-अधिकार पुस्तिका के प्रावधान को अनिवार्य करती है जिसमें उनकी जोत का विवरण होता है।
Expert Advice: Sections relating to Record of Rights (Sec 108) and Bhu-Adhikar Pustika (Sec 114) are very high-yield for exams.
विशेषज्ञ की सलाह: अधिकारों के अभिलेख (धारा 108) और भू-अधिकार पुस्तिका (धारा 114) से संबंधित धाराएं परीक्षाओं के लिए बहुत महत्वपूर्ण हैं।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 65,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 97,
            sectionId = "sec_a",
            questionNumber = 97,
            statementText = """Q97. What does the term 'Wajib-ul-arz' refer to under Section 242 of the MPLRC 1959?""",
            statementTextHindi = """प्रश्न 97. MPLRC 1959 की धारा 242 के तहत 'वाजिब-उल-अर्ज़' शब्द का क्या अर्थ है?""",
            options = listOf(Option(1, "Record of customs in a village"), Option(2, "Tax receipt"), Option(3, "Land transfer deed"), Option(4, "Dispute register")),
            correctOptionIndex = 0,
            explanation = """Solution: Wajib-ul-arz is a record prepared by the Sub-Divisional Officer detailing the customs existing in a village.""",
            explanationHindi = """समाधान: वाजिब-उल-अर्ज़ अनुविभागीय अधिकारी द्वारा तैयार किया गया एक रिकॉर्ड है जिसमें गाँव में मौजूद रिवाजों का विवरण होता है।
Expert Advice: Traditional revenue terms from the Mughal/British era are preserved in MPLRC and must be memorized.
विशेषज्ञ की सलाह: मुगल/ब्रिटिश काल के पारंपरिक राजस्व शब्द MPLRC में संरक्षित हैं और इन्हें याद किया जाना चाहिए।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 72,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 98,
            sectionId = "sec_a",
            questionNumber = 98,
            statementText = """Q98. Which part of the Revenue Book Circular (RBC) deals exclusively with providing relief in case of natural calamities?""",
            statementTextHindi = """प्रश्न 98. राजस्व पुस्तक परिपत्र (RBC) का कौन सा भाग विशेष रूप से प्राकृतिक आपदाओं के मामले में राहत प्रदान करने से संबंधित है?""",
            options = listOf(Option(1, "RBC 6-4"), Option(2, "RBC 4-1"), Option(3, "RBC 3-2"), Option(4, "RBC 5-3")),
            correctOptionIndex = 0,
            explanation = """Solution: RBC 6-4 contains provisions and guidelines for financial assistance to citizens affected by natural disasters like drought, flood, or fire.""",
            explanationHindi = """समाधान: RBC 6-4 में सूखा, बाढ़ या आग जैसी प्राकृतिक आपदाओं से प्रभावित नागरिकों को वित्तीय सहायता के प्रावधान और दिशानिर्देश शामिल हैं।
Expert Advice: RBC 6-4 is heavily utilized by Patwaris and Revenue Inspectors in the field. Questions on this are almost guaranteed.
विशेषज्ञ की सलाह: RBC 6-4 का उपयोग मैदान में पटवारियों और राजस्व निरीक्षकों द्वारा भारी मात्रा में किया जाता है। इस पर प्रश्न लगभग तय हैं।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 79,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 99,
            sectionId = "sec_a",
            questionNumber = 99,
            statementText = """Q99. According to the Land Records Manual, who is the field-level official primarily responsible for maintaining the village map (Naksha)?""",
            statementTextHindi = """प्रश्न 99. भू-अभिलेख नियमावली के अनुसार, गाँव के नक्शे (नक्शा) को बनाए रखने के लिए मुख्य रूप से कौन सा मैदानी स्तर का अधिकारी जिम्मेदार है?""",
            options = listOf(Option(1, "Tehsildar"), Option(2, "Revenue Inspector (RI)"), Option(3, "Patwari"), Option(4, "Sub-Divisional Officer (SDO)")),
            correctOptionIndex = 2,
            explanation = """Solution: The Patwari (Halka Patwari) is the foundational revenue official responsible for ground-level land record maintenance and crop inspection.""",
            explanationHindi = """समाधान: पटवारी (हल्का पटवारी) जमीनी स्तर पर भूमि रिकॉर्ड के रखरखाव और फसल निरीक्षण के लिए जिम्मेदार आधारभूत राजस्व अधिकारी है।
Expert Advice: Understand the hierarchy of the Revenue Department: Patwari -> RI -> Naib Tehsildar -> Tehsildar -> SDO -> Collector.
विशेषज्ञ की सलाह: राजस्व विभाग के पदानुक्रम को समझें: पटवारी -> आरआई -> नायब तहसीलदार -> तहसीलदार -> एसडीओ -> कलेक्टर।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 86,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 100,
            sectionId = "sec_a",
            questionNumber = 100,
            statementText = """Q100. What is the primary function of the 'Girdawari' process in MP Revenue operations?""",
            statementTextHindi = """प्रश्न 100. म.प्र. राजस्व संचालन में 'गिरदावरी' प्रक्रिया का प्राथमिक कार्य क्या है?""",
            options = listOf(Option(1, "Tax collection"), Option(2, "Crop inspection and recording"), Option(3, "Land division"), Option(4, "Village election")),
            correctOptionIndex = 1,
            explanation = """Solution: Girdawari is the periodic crop inspection conducted by the Patwari to record which crops are sown in which parcels of land.""",
            explanationHindi = """समाधान: गिरदावरी पटवारी द्वारा किया जाने वाला आवधिक फसल निरीक्षण है जो यह रिकॉर्ड करता है कि भूमि के किस पार्सल में कौन सी फसल बोई गई है।
Expert Advice: The timings of Kharif, Rabi, and Zaid Girdawari as outlined in the Land Records Manual should be known.
विशेषज्ञ की सलाह: भू-अभिलेख नियमावली में उल्लिखित खरीफ, रबी और जायद गिरदावरी के समय का ज्ञान होना चाहिए।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 93,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 101,
            sectionId = "sec_a",
            questionNumber = 101,
            statementText = """Q101. Identify the next number in the series: 2, 6, 12, 20, ?""",
            statementTextHindi = """प्रश्न 101. श्रृंखला में अगली संख्या पहचानें: 2, 6, 12, 20, ?""",
            options = listOf(Option(1, "24"), Option(2, "28"), Option(3, "30"), Option(4, "32")),
            correctOptionIndex = 2,
            explanation = """Solution: The series follows the pattern: 1x2=2, 2x3=6, 3x4=12, 4x5=20. The next term is 5x6=30.""",
            explanationHindi = """समाधान: यह श्रृंखला इस पैटर्न का पालन करती है: 1x2=2, 2x3=6, 3x4=12, 4x5=20। अगला पद 5x6=30 है।
Expert Advice: Master basic multiplication series and differences between consecutive numbers for quick logical reasoning.
विशेषज्ञ की सलाह: त्वरित तार्किक तर्क के लिए बुनियादी गुणा श्रृंखला और लगातार संख्याओं के बीच के अंतर में महारत हासिल करें।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 0,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 102,
            sectionId = "sec_a",
            questionNumber = 102,
            statementText = """Q102. Who is known to have founded the historical city of Gwalior?""",
            statementTextHindi = """प्रश्न 102. ऐतिहासिक शहर ग्वालियर की स्थापना किसने की थी?""",
            options = listOf(Option(1, "Raja Man Singh Tomar"), Option(2, "Suraj Sen"), Option(3, "Mahadji Scindia"), Option(4, "Tatya Tope")),
            correctOptionIndex = 1,
            explanation = """Solution: Gwalior was founded by King Suraj Sen in the 8th century, named after the sage Gwalipa who cured his leprosy.""",
            explanationHindi = """समाधान: ग्वालियर की स्थापना 8वीं शताब्दी में राजा सूरज सेन ने की थी, जिसका नाम ऋषि ग्वालिपा के नाम पर रखा गया था जिन्होंने उनके कुष्ठ रोग को ठीक किया था।
Expert Advice: Local history of MP, especially concerning historical capitals like Gwalior, Indore, and Bhopal, is crucial for Paper 1.
विशेषज्ञ की सलाह: एमपी का स्थानीय इतिहास, विशेष रूप से ग्वालियर, इंदौर और भोपाल जैसी ऐतिहासिक राजधानियों के संबंध में, पेपर 1 के लिए महत्वपूर्ण है।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 7,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 103,
            sectionId = "sec_a",
            questionNumber = 103,
            statementText = """Q103. Which of the following Sandhi (Join) is present in the word 'Suryoday' (सूर्योदय)?""",
            statementTextHindi = """प्रश्न 103. 'सूर्योदय' शब्द में निम्नलिखित में से कौन सी संधि है?""",
            options = listOf(Option(1, "Dirgha Sandhi"), Option(2, "Guna Sandhi"), Option(3, "Vriddhi Sandhi"), Option(4, "Yan Sandhi")),
            correctOptionIndex = 1,
            explanation = """Solution: Suryoday is formed by Surya + Uday (अ + उ = ओ), which is a classic example of Guna Sandhi.""",
            explanationHindi = """समाधान: सूर्योदय, सूर्य + उदय (अ + उ = ओ) से बना है, जो गुण संधि का एक उत्कृष्ट उदाहरण है।
Expert Advice: Practice the basic rules of Swar Sandhi (Vowel Sandhi) as at least 1-2 questions are standard in General Hindi.
विशेषज्ञ की सलाह: स्वर संधि के बुनियादी नियमों का अभ्यास करें क्योंकि सामान्य हिंदी में कम से कम 1-2 प्रश्न मानक होते हैं।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 14,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 104,
            sectionId = "sec_a",
            questionNumber = 104,
            statementText = """Q104. Choose the correct preposition: 'She is very good ___ mathematics.'""",
            statementTextHindi = """प्रश्न 104. सही पूर्वसर्ग (preposition) चुनें: 'She is very good ___ mathematics.'""",
            options = listOf(Option(1, "in"), Option(2, "with"), Option(3, "at"), Option(4, "about")),
            correctOptionIndex = 2,
            explanation = """Solution: The correct preposition to use with 'good' when talking about skills or abilities is 'at' (good at mathematics).""",
            explanationHindi = """समाधान: कौशल या क्षमताओं के बारे में बात करते समय 'good' के साथ उपयोग करने के लिए सही प्रीपोज़िशन 'at' है (good at mathematics)।
Expert Advice: Prepositions with fixed adjectives (good at, afraid of, interested in) are highly tested in MP exams.
विशेषज्ञ की सलाह: निश्चित विशेषणों (good at, afraid of, interested in) वाले पूर्वसर्गों का एमपी परीक्षाओं में अत्यधिक परीक्षण किया जाता है।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 21,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 105,
            sectionId = "sec_a",
            questionNumber = 105,
            statementText = """Q105. If a shopkeeper sells an item for Rs 120 and makes a 20% profit, what was the cost price of the item?""",
            statementTextHindi = """प्रश्न 105. यदि कोई दुकानदार किसी वस्तु को 120 रुपये में बेचता है और 20% का लाभ कमाता है, तो वस्तु का क्रय मूल्य क्या था?""",
            options = listOf(Option(1, "Rs 96"), Option(2, "Rs 100"), Option(3, "Rs 108"), Option(4, "Rs 144")),
            correctOptionIndex = 1,
            explanation = """Solution: Selling Price (SP) = Cost Price (CP) + 20% of CP. 120 = 1.2 * CP. Therefore, CP = 120 / 1.2 = 100.""",
            explanationHindi = """समाधान: विक्रय मूल्य (SP) = क्रय मूल्य (CP) + CP का 20%। 120 = 1.2 * CP. इसलिए, CP = 120 / 1.2 = 100.
Expert Advice: Memorize fraction equivalents of percentages (20% = 1/5) to solve Profit & Loss questions mentally.
विशेषज्ञ की सलाह: लाभ और हानि के प्रश्नों को मानसिक रूप से हल करने के लिए प्रतिशत के अंश समतुल्य (20% = 1/5) याद रखें।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 28,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 106,
            sectionId = "sec_a",
            questionNumber = 106,
            statementText = """Q106. Which vitamin is scientifically known as Ascorbic Acid?""",
            statementTextHindi = """प्रश्न 106. किस विटामिन को वैज्ञानिक रूप से एस्कॉर्बिक एसिड के रूप में जाना जाता है?""",
            options = listOf(Option(1, "Vitamin A"), Option(2, "Vitamin B12"), Option(3, "Vitamin C"), Option(4, "Vitamin D")),
            correctOptionIndex = 2,
            explanation = """Solution: Vitamin C is known as Ascorbic Acid. Its deficiency causes Scurvy.""",
            explanationHindi = """समाधान: विटामिन C को एस्कॉर्बिक एसिड के रूप में जाना जाता है। इसकी कमी से स्कर्वी रोग होता है।
Expert Advice: Learn the chemical names and deficiency diseases for all essential vitamins (A, B-complex, C, D, E, K).
विशेषज्ञ की सलाह: सभी आवश्यक विटामिनों (A, B-कॉम्प्लेक्स, C, D, E, K) के रासायनिक नाम और कमी से होने वाले रोगों को याद करें।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 35,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 107,
            sectionId = "sec_a",
            questionNumber = 107,
            statementText = """Q107. In computer terminology, what is the full form of RAM?""",
            statementTextHindi = """प्रश्न 107. कंप्यूटर शब्दावली में RAM का पूर्ण रूप क्या है?""",
            options = listOf(Option(1, "Read Access Memory"), Option(2, "Random Access Memory"), Option(3, "Run Access Memory"), Option(4, "Random Arithmetic Memory")),
            correctOptionIndex = 1,
            explanation = """Solution: RAM stands for Random Access Memory. It is the primary volatile memory used by the computer to store data temporarily.""",
            explanationHindi = """समाधान: RAM का मतलब रैंडम एक्सेस मेमोरी है। यह कंप्यूटर द्वारा डेटा को अस्थायी रूप से संग्रहीत करने के लिए उपयोग की जाने वाली प्राथमिक अस्थिर (volatile) मेमोरी है।
Expert Advice: Basic hardware components and their abbreviations are a staple of the General Computer Knowledge section.
विशेषज्ञ की सलाह: बुनियादी हार्डवेयर घटक और उनके संक्षिप्त नाम सामान्य कंप्यूटर ज्ञान अनुभाग का एक प्रमुख हिस्सा हैं।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 42,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 108,
            sectionId = "sec_a",
            questionNumber = 108,
            statementText = """Q108. Under the MP Civil Services (Leave) Rules, 1977, what is the maximum number of days of Earned Leave (EL) that can be accumulated by a government servant?""",
            statementTextHindi = """प्रश्न 108. म.प्र. सिविल सेवा (अवकाश) नियम, 1977 के तहत, किसी सरकारी सेवक द्वारा अधिकतम कितने दिनों का अर्जित अवकाश (EL) संचित किया जा सकता है?""",
            options = listOf(Option(1, "180 days"), Option(2, "240 days"), Option(3, "300 days"), Option(4, "365 days")),
            correctOptionIndex = 2,
            explanation = """Solution: A state government employee in MP can accumulate a maximum of 300 days of Earned Leave in their service tenure, which can be encashed at retirement.""",
            explanationHindi = """समाधान: एमपी में एक राज्य सरकार का कर्मचारी अपने सेवा कार्यकाल में अधिकतम 300 दिनों का अर्जित अवकाश संचित कर सकता है, जिसे सेवानिवृत्ति पर भुनाया जा सकता है।
Expert Advice: Read the MP Leave Rules carefully, particularly regarding EL, Casual Leave (CL), and Maternity/Paternity leave durations.
विशेषज्ञ की सलाह: म.प्र. अवकाश नियमों को ध्यान से पढ़ें, विशेष रूप से EL, आकस्मिक अवकाश (CL), और मातृत्व/पितृत्व अवकाश की अवधि के संबंध में।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 49,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 109,
            sectionId = "sec_a",
            questionNumber = 109,
            statementText = """Q109. According to MP Govt Service Rules, female government employees are entitled to maternity leave for how many days?""",
            statementTextHindi = """प्रश्न 109. म.प्र. सरकारी सेवा नियमों के अनुसार, महिला सरकारी कर्मचारी कितने दिनों के मातृत्व अवकाश की हकदार हैं?""",
            options = listOf(Option(1, "90 days"), Option(2, "135 days"), Option(3, "180 days"), Option(4, "240 days")),
            correctOptionIndex = 2,
            explanation = """Solution: Female government servants in MP are entitled to 180 days of maternity leave up to two living children.""",
            explanationHindi = """समाधान: म.प्र. में महिला सरकारी कर्मचारी दो जीवित बच्चों तक 180 दिनों के मातृत्व अवकाश की हकदार हैं।
Expert Advice: Leave policies for female employees and child care leave (CCL) are frequently asked in the Govt Service Rules paper.
विशेषज्ञ की सलाह: महिला कर्मचारियों के लिए अवकाश नीतियां और चाइल्ड केयर लीव (CCL) अक्सर सरकारी सेवा नियम के पेपर में पूछे जाते हैं।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 56,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 110,
            sectionId = "sec_a",
            questionNumber = 110,
            statementText = """Q110. Under the MP Civil Services (Conduct) Rules 1965, can a government servant take part in political elections?""",
            statementTextHindi = """प्रश्न 110. म.प्र. सिविल सेवा (आचरण) नियम 1965 के तहत, क्या कोई सरकारी कर्मचारी राजनीतिक चुनावों में भाग ले सकता है?""",
            options = listOf(Option(1, "Yes, with permission"), Option(2, "Yes, during leave"), Option(3, "No, it is completely prohibited"), Option(4, "Yes, if fighting as independent")),
            correctOptionIndex = 2,
            explanation = """Solution: Rule 5 of the MP Civil Services (Conduct) Rules strictly prohibits any government servant from taking part in politics or elections.""",
            explanationHindi = """समाधान: म.प्र. सिविल सेवा (आचरण) नियम का नियम 5 किसी भी सरकारी सेवक को राजनीति या चुनाव में भाग लेने से सख्ती से रोकता है।
Expert Advice: The Conduct Rules clearly outline prohibitions regarding politics, press, and unauthorized communication. Review Rule 5 and Rule 7 closely.
विशेषज्ञ की सलाह: आचरण नियम राजनीति, प्रेस और अनधिकृत संचार के संबंध में प्रतिबंधों को स्पष्ट रूप से रेखांकित करते हैं। नियम 5 और नियम 7 की बारीकी से समीक्षा करें।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 63,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 111,
            sectionId = "sec_a",
            questionNumber = 111,
            statementText = """Q111. Dearness Allowance (DA) is provided to government employees primarily to compensate for?""",
            statementTextHindi = """प्रश्न 111. महंगाई भत्ता (DA) सरकारी कर्मचारियों को मुख्य रूप से किसकी भरपाई के लिए प्रदान किया जाता है?""",
            options = listOf(Option(1, "Travel expenses"), Option(2, "Housing costs"), Option(3, "Medical expenses"), Option(4, "Inflation / Cost of living")),
            correctOptionIndex = 3,
            explanation = """Solution: Dearness Allowance is calculated as a percentage of basic salary to mitigate the impact of inflation on employees.""",
            explanationHindi = """समाधान: महंगाई भत्ते की गणना कर्मचारियों पर मुद्रास्फीति के प्रभाव को कम करने के लिए मूल वेतन के प्रतिशत के रूप में की जाती है।
Expert Advice: Basic understanding of Salary structures (Basic, DA, HRA) is a crucial part of the 30-mark Govt Service Rules section.
विशेषज्ञ की सलाह: वेतन संरचनाओं (Basic, DA, HRA) की बुनियादी समझ 30-अंकों वाले सरकारी सेवा नियम अनुभाग का एक महत्वपूर्ण हिस्सा है।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 70,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 112,
            sectionId = "sec_a",
            questionNumber = 112,
            statementText = """Q112. In the context of MP Revenue Terminology, what does the term 'Khasra' signify?""",
            statementTextHindi = """प्रश्न 112. म.प्र. राजस्व शब्दावली के संदर्भ में, 'खसरा' शब्द का क्या अर्थ है?""",
            options = listOf(Option(1, "A tax collection receipt"), Option(2, "A village map"), Option(3, "A primary agricultural record containing plot details"), Option(4, "A type of land revenue tax")),
            correctOptionIndex = 2,
            explanation = """Solution: Khasra is a primary land record document that contains details of the land parcel, crop grown, soil type, and the cultivator's name.""",
            explanationHindi = """समाधान: खसरा एक प्राथमिक भूमि रिकॉर्ड दस्तावेज़ है जिसमें भूमि पार्सल, उगाई गई फसल, मिट्टी के प्रकार और कृषक के नाम का विवरण होता है।
Expert Advice: Differentiate clearly between Khasra (plot details), Khatauni (holding details of a person), and Shajra (village map).
विशेषज्ञ की सलाह: खसरा (भूखंड विवरण), खतौनी (किसी व्यक्ति के जोत का विवरण), और शजरा (गाँव का नक्शा) के बीच स्पष्ट रूप से अंतर करें।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 77,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 113,
            sectionId = "sec_a",
            questionNumber = 113,
            statementText = """Q113. What is meant by 'Nazul' land in the revenue department?""",
            statementTextHindi = """प्रश्न 113. राजस्व विभाग में 'नज़ूल' भूमि से क्या तात्पर्य है?""",
            options = listOf(Option(1, "Private agricultural land"), Option(2, "Forest department land"), Option(3, "State-owned land situated within or near urban areas"), Option(4, "Irrigated land")),
            correctOptionIndex = 2,
            explanation = """Solution: Nazul lands are state-owned plots situated within municipal/urban limits often leased out for residential or commercial purposes.""",
            explanationHindi = """समाधान: नज़ूल भूमि नगरपालिका/शहरी सीमा के भीतर स्थित राज्य के स्वामित्व वाले भूखंड हैं जिन्हें अक्सर आवासीय या व्यावसायिक उद्देश्यों के लिए पट्टे पर दिया जाता है।
Expert Advice: Nazul rules and leasing provisions are essential parts of the Revenue Book Circular (RBC).
विशेषज्ञ की सलाह: नज़ूल नियम और पट्टे के प्रावधान राजस्व पुस्तक परिपत्र (RBC) के आवश्यक भाग हैं।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 84,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 114,
            sectionId = "sec_a",
            questionNumber = 114,
            statementText = """Q114. Under which section of the MP Land Revenue Code, 1959 is the 'Board of Revenue' established?""",
            statementTextHindi = """प्रश्न 114. म.प्र. भू-राजस्व संहिता, 1959 की किस धारा के तहत 'राजस्व मंडल' की स्थापना की गई है?""",
            options = listOf(Option(1, "Section 2"), Option(2, "Section 3"), Option(3, "Section 11"), Option(4, "Section 158")),
            correctOptionIndex = 1,
            explanation = """Solution: Section 3 of the MPLRC, 1959 provisions for the constitution of the Board of Revenue, the highest revenue court in the state.""",
            explanationHindi = """समाधान: MPLRC, 1959 की धारा 3 राजस्व मंडल के गठन का प्रावधान करती है, जो राज्य का सर्वोच्च राजस्व न्यायालय है।
Expert Advice: Memorize the key sections of MPLRC dealing with authorities (Sec 11) and Bhumiswami rights (Sec 158).
विशेषज्ञ की सलाह: अधिकारियों (धारा 11) और भूमिस्वामी अधिकारों (धारा 158) से निपटने वाली MPLRC की प्रमुख धाराओं को याद रखें।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 91,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 115,
            sectionId = "sec_a",
            questionNumber = 115,
            statementText = """Q115. Where is the principal seat or headquarters of the Board of Revenue in Madhya Pradesh located?""",
            statementTextHindi = """प्रश्न 115. मध्य प्रदेश में राजस्व मंडल का प्रमुख स्थान या मुख्यालय कहाँ स्थित है?""",
            options = listOf(Option(1, "Bhopal"), Option(2, "Indore"), Option(3, "Gwalior"), Option(4, "Jabalpur")),
            correctOptionIndex = 2,
            explanation = """Solution: The Board of Revenue (Rajya Swamandal) of Madhya Pradesh is headquartered in Gwalior.""",
            explanationHindi = """समाधान: मध्य प्रदेश के राजस्व मंडल का मुख्यालय ग्वालियर में है।
Expert Advice: While the political capital is Bhopal, key institutions like the High Court (Jabalpur) and Board of Revenue (Gwalior) are elsewhere.
विशेषज्ञ की सलाह: जबकि राजनीतिक राजधानी भोपाल है, उच्च न्यायालय (जबलपुर) और राजस्व मंडल (ग्वालियर) जैसे प्रमुख संस्थान अन्य स्थानों पर हैं।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 98,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 116,
            sectionId = "sec_a",
            questionNumber = 116,
            statementText = """Q116. The 'Bhu-Adhikar Pustika' (Kisan Book) is provided to a Bhumiswami under which section of MPLRC 1959?""",
            statementTextHindi = """प्रश्न 116. MPLRC 1959 की किस धारा के तहत एक भूमिस्वामी को 'भू-अधिकार पुस्तिका' (किसान बुक) प्रदान की जाती है?""",
            options = listOf(Option(1, "Section 108"), Option(2, "Section 114"), Option(3, "Section 158"), Option(4, "Section 242")),
            correctOptionIndex = 1,
            explanation = """Solution: Section 114 of the code mandates the provision of Bhu-Adhikar Pustika to every Bhumiswami containing details of their holdings.""",
            explanationHindi = """समाधान: संहिता की धारा 114 प्रत्येक भूमिस्वामी को भू-अधिकार पुस्तिका के प्रावधान को अनिवार्य करती है जिसमें उनकी जोत का विवरण होता है।
Expert Advice: Sections relating to Record of Rights (Sec 108) and Bhu-Adhikar Pustika (Sec 114) are very high-yield for exams.
विशेषज्ञ की सलाह: अधिकारों के अभिलेख (धारा 108) और भू-अधिकार पुस्तिका (धारा 114) से संबंधित धाराएं परीक्षाओं के लिए बहुत महत्वपूर्ण हैं।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 5,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 117,
            sectionId = "sec_a",
            questionNumber = 117,
            statementText = """Q117. What does the term 'Wajib-ul-arz' refer to under Section 242 of the MPLRC 1959?""",
            statementTextHindi = """प्रश्न 117. MPLRC 1959 की धारा 242 के तहत 'वाजिब-उल-अर्ज़' शब्द का क्या अर्थ है?""",
            options = listOf(Option(1, "Record of customs in a village"), Option(2, "Tax receipt"), Option(3, "Land transfer deed"), Option(4, "Dispute register")),
            correctOptionIndex = 0,
            explanation = """Solution: Wajib-ul-arz is a record prepared by the Sub-Divisional Officer detailing the customs existing in a village.""",
            explanationHindi = """समाधान: वाजिब-उल-अर्ज़ अनुविभागीय अधिकारी द्वारा तैयार किया गया एक रिकॉर्ड है जिसमें गाँव में मौजूद रिवाजों का विवरण होता है।
Expert Advice: Traditional revenue terms from the Mughal/British era are preserved in MPLRC and must be memorized.
विशेषज्ञ की सलाह: मुगल/ब्रिटिश काल के पारंपरिक राजस्व शब्द MPLRC में संरक्षित हैं और इन्हें याद किया जाना चाहिए।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 12,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 118,
            sectionId = "sec_a",
            questionNumber = 118,
            statementText = """Q118. Which part of the Revenue Book Circular (RBC) deals exclusively with providing relief in case of natural calamities?""",
            statementTextHindi = """प्रश्न 118. राजस्व पुस्तक परिपत्र (RBC) का कौन सा भाग विशेष रूप से प्राकृतिक आपदाओं के मामले में राहत प्रदान करने से संबंधित है?""",
            options = listOf(Option(1, "RBC 6-4"), Option(2, "RBC 4-1"), Option(3, "RBC 3-2"), Option(4, "RBC 5-3")),
            correctOptionIndex = 0,
            explanation = """Solution: RBC 6-4 contains provisions and guidelines for financial assistance to citizens affected by natural disasters like drought, flood, or fire.""",
            explanationHindi = """समाधान: RBC 6-4 में सूखा, बाढ़ या आग जैसी प्राकृतिक आपदाओं से प्रभावित नागरिकों को वित्तीय सहायता के प्रावधान और दिशानिर्देश शामिल हैं।
Expert Advice: RBC 6-4 is heavily utilized by Patwaris and Revenue Inspectors in the field. Questions on this are almost guaranteed.
विशेषज्ञ की सलाह: RBC 6-4 का उपयोग मैदान में पटवारियों और राजस्व निरीक्षकों द्वारा भारी मात्रा में किया जाता है। इस पर प्रश्न लगभग तय हैं।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 19,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 119,
            sectionId = "sec_a",
            questionNumber = 119,
            statementText = """Q119. According to the Land Records Manual, who is the field-level official primarily responsible for maintaining the village map (Naksha)?""",
            statementTextHindi = """प्रश्न 119. भू-अभिलेख नियमावली के अनुसार, गाँव के नक्शे (नक्शा) को बनाए रखने के लिए मुख्य रूप से कौन सा मैदानी स्तर का अधिकारी जिम्मेदार है?""",
            options = listOf(Option(1, "Tehsildar"), Option(2, "Revenue Inspector (RI)"), Option(3, "Patwari"), Option(4, "Sub-Divisional Officer (SDO)")),
            correctOptionIndex = 2,
            explanation = """Solution: The Patwari (Halka Patwari) is the foundational revenue official responsible for ground-level land record maintenance and crop inspection.""",
            explanationHindi = """समाधान: पटवारी (हल्का पटवारी) जमीनी स्तर पर भूमि रिकॉर्ड के रखरखाव और फसल निरीक्षण के लिए जिम्मेदार आधारभूत राजस्व अधिकारी है।
Expert Advice: Understand the hierarchy of the Revenue Department: Patwari -> RI -> Naib Tehsildar -> Tehsildar -> SDO -> Collector.
विशेषज्ञ की सलाह: राजस्व विभाग के पदानुक्रम को समझें: पटवारी -> आरआई -> नायब तहसीलदार -> तहसीलदार -> एसडीओ -> कलेक्टर।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 26,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 120,
            sectionId = "sec_a",
            questionNumber = 120,
            statementText = """Q120. What is the primary function of the 'Girdawari' process in MP Revenue operations?""",
            statementTextHindi = """प्रश्न 120. म.प्र. राजस्व संचालन में 'गिरदावरी' प्रक्रिया का प्राथमिक कार्य क्या है?""",
            options = listOf(Option(1, "Tax collection"), Option(2, "Crop inspection and recording"), Option(3, "Land division"), Option(4, "Village election")),
            correctOptionIndex = 1,
            explanation = """Solution: Girdawari is the periodic crop inspection conducted by the Patwari to record which crops are sown in which parcels of land.""",
            explanationHindi = """समाधान: गिरदावरी पटवारी द्वारा किया जाने वाला आवधिक फसल निरीक्षण है जो यह रिकॉर्ड करता है कि भूमि के किस पार्सल में कौन सी फसल बोई गई है।
Expert Advice: The timings of Kharif, Rabi, and Zaid Girdawari as outlined in the Land Records Manual should be known.
विशेषज्ञ की सलाह: भू-अभिलेख नियमावली में उल्लिखित खरीफ, रबी और जायद गिरदावरी के समय का ज्ञान होना चाहिए।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 33,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 121,
            sectionId = "sec_a",
            questionNumber = 121,
            statementText = """Q121. Identify the next number in the series: 2, 6, 12, 20, ?""",
            statementTextHindi = """प्रश्न 121. श्रृंखला में अगली संख्या पहचानें: 2, 6, 12, 20, ?""",
            options = listOf(Option(1, "24"), Option(2, "28"), Option(3, "30"), Option(4, "32")),
            correctOptionIndex = 2,
            explanation = """Solution: The series follows the pattern: 1x2=2, 2x3=6, 3x4=12, 4x5=20. The next term is 5x6=30.""",
            explanationHindi = """समाधान: यह श्रृंखला इस पैटर्न का पालन करती है: 1x2=2, 2x3=6, 3x4=12, 4x5=20। अगला पद 5x6=30 है।
Expert Advice: Master basic multiplication series and differences between consecutive numbers for quick logical reasoning.
विशेषज्ञ की सलाह: त्वरित तार्किक तर्क के लिए बुनियादी गुणा श्रृंखला और लगातार संख्याओं के बीच के अंतर में महारत हासिल करें।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 40,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 122,
            sectionId = "sec_a",
            questionNumber = 122,
            statementText = """Q122. Who is known to have founded the historical city of Gwalior?""",
            statementTextHindi = """प्रश्न 122. ऐतिहासिक शहर ग्वालियर की स्थापना किसने की थी?""",
            options = listOf(Option(1, "Raja Man Singh Tomar"), Option(2, "Suraj Sen"), Option(3, "Mahadji Scindia"), Option(4, "Tatya Tope")),
            correctOptionIndex = 1,
            explanation = """Solution: Gwalior was founded by King Suraj Sen in the 8th century, named after the sage Gwalipa who cured his leprosy.""",
            explanationHindi = """समाधान: ग्वालियर की स्थापना 8वीं शताब्दी में राजा सूरज सेन ने की थी, जिसका नाम ऋषि ग्वालिपा के नाम पर रखा गया था जिन्होंने उनके कुष्ठ रोग को ठीक किया था।
Expert Advice: Local history of MP, especially concerning historical capitals like Gwalior, Indore, and Bhopal, is crucial for Paper 1.
विशेषज्ञ की सलाह: एमपी का स्थानीय इतिहास, विशेष रूप से ग्वालियर, इंदौर और भोपाल जैसी ऐतिहासिक राजधानियों के संबंध में, पेपर 1 के लिए महत्वपूर्ण है।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 47,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 123,
            sectionId = "sec_a",
            questionNumber = 123,
            statementText = """Q123. Which of the following Sandhi (Join) is present in the word 'Suryoday' (सूर्योदय)?""",
            statementTextHindi = """प्रश्न 123. 'सूर्योदय' शब्द में निम्नलिखित में से कौन सी संधि है?""",
            options = listOf(Option(1, "Dirgha Sandhi"), Option(2, "Guna Sandhi"), Option(3, "Vriddhi Sandhi"), Option(4, "Yan Sandhi")),
            correctOptionIndex = 1,
            explanation = """Solution: Suryoday is formed by Surya + Uday (अ + उ = ओ), which is a classic example of Guna Sandhi.""",
            explanationHindi = """समाधान: सूर्योदय, सूर्य + उदय (अ + उ = ओ) से बना है, जो गुण संधि का एक उत्कृष्ट उदाहरण है।
Expert Advice: Practice the basic rules of Swar Sandhi (Vowel Sandhi) as at least 1-2 questions are standard in General Hindi.
विशेषज्ञ की सलाह: स्वर संधि के बुनियादी नियमों का अभ्यास करें क्योंकि सामान्य हिंदी में कम से कम 1-2 प्रश्न मानक होते हैं।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 54,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 124,
            sectionId = "sec_a",
            questionNumber = 124,
            statementText = """Q124. Choose the correct preposition: 'She is very good ___ mathematics.'""",
            statementTextHindi = """प्रश्न 124. सही पूर्वसर्ग (preposition) चुनें: 'She is very good ___ mathematics.'""",
            options = listOf(Option(1, "in"), Option(2, "with"), Option(3, "at"), Option(4, "about")),
            correctOptionIndex = 2,
            explanation = """Solution: The correct preposition to use with 'good' when talking about skills or abilities is 'at' (good at mathematics).""",
            explanationHindi = """समाधान: कौशल या क्षमताओं के बारे में बात करते समय 'good' के साथ उपयोग करने के लिए सही प्रीपोज़िशन 'at' है (good at mathematics)।
Expert Advice: Prepositions with fixed adjectives (good at, afraid of, interested in) are highly tested in MP exams.
विशेषज्ञ की सलाह: निश्चित विशेषणों (good at, afraid of, interested in) वाले पूर्वसर्गों का एमपी परीक्षाओं में अत्यधिक परीक्षण किया जाता है।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 61,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 125,
            sectionId = "sec_a",
            questionNumber = 125,
            statementText = """Q125. If a shopkeeper sells an item for Rs 120 and makes a 20% profit, what was the cost price of the item?""",
            statementTextHindi = """प्रश्न 125. यदि कोई दुकानदार किसी वस्तु को 120 रुपये में बेचता है और 20% का लाभ कमाता है, तो वस्तु का क्रय मूल्य क्या था?""",
            options = listOf(Option(1, "Rs 96"), Option(2, "Rs 100"), Option(3, "Rs 108"), Option(4, "Rs 144")),
            correctOptionIndex = 1,
            explanation = """Solution: Selling Price (SP) = Cost Price (CP) + 20% of CP. 120 = 1.2 * CP. Therefore, CP = 120 / 1.2 = 100.""",
            explanationHindi = """समाधान: विक्रय मूल्य (SP) = क्रय मूल्य (CP) + CP का 20%। 120 = 1.2 * CP. इसलिए, CP = 120 / 1.2 = 100.
Expert Advice: Memorize fraction equivalents of percentages (20% = 1/5) to solve Profit & Loss questions mentally.
विशेषज्ञ की सलाह: लाभ और हानि के प्रश्नों को मानसिक रूप से हल करने के लिए प्रतिशत के अंश समतुल्य (20% = 1/5) याद रखें।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 68,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 126,
            sectionId = "sec_a",
            questionNumber = 126,
            statementText = """Q126. Which vitamin is scientifically known as Ascorbic Acid?""",
            statementTextHindi = """प्रश्न 126. किस विटामिन को वैज्ञानिक रूप से एस्कॉर्बिक एसिड के रूप में जाना जाता है?""",
            options = listOf(Option(1, "Vitamin A"), Option(2, "Vitamin B12"), Option(3, "Vitamin C"), Option(4, "Vitamin D")),
            correctOptionIndex = 2,
            explanation = """Solution: Vitamin C is known as Ascorbic Acid. Its deficiency causes Scurvy.""",
            explanationHindi = """समाधान: विटामिन C को एस्कॉर्बिक एसिड के रूप में जाना जाता है। इसकी कमी से स्कर्वी रोग होता है।
Expert Advice: Learn the chemical names and deficiency diseases for all essential vitamins (A, B-complex, C, D, E, K).
विशेषज्ञ की सलाह: सभी आवश्यक विटामिनों (A, B-कॉम्प्लेक्स, C, D, E, K) के रासायनिक नाम और कमी से होने वाले रोगों को याद करें।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 75,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 127,
            sectionId = "sec_a",
            questionNumber = 127,
            statementText = """Q127. In computer terminology, what is the full form of RAM?""",
            statementTextHindi = """प्रश्न 127. कंप्यूटर शब्दावली में RAM का पूर्ण रूप क्या है?""",
            options = listOf(Option(1, "Read Access Memory"), Option(2, "Random Access Memory"), Option(3, "Run Access Memory"), Option(4, "Random Arithmetic Memory")),
            correctOptionIndex = 1,
            explanation = """Solution: RAM stands for Random Access Memory. It is the primary volatile memory used by the computer to store data temporarily.""",
            explanationHindi = """समाधान: RAM का मतलब रैंडम एक्सेस मेमोरी है। यह कंप्यूटर द्वारा डेटा को अस्थायी रूप से संग्रहीत करने के लिए उपयोग की जाने वाली प्राथमिक अस्थिर (volatile) मेमोरी है।
Expert Advice: Basic hardware components and their abbreviations are a staple of the General Computer Knowledge section.
विशेषज्ञ की सलाह: बुनियादी हार्डवेयर घटक और उनके संक्षिप्त नाम सामान्य कंप्यूटर ज्ञान अनुभाग का एक प्रमुख हिस्सा हैं।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 82,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 128,
            sectionId = "sec_a",
            questionNumber = 128,
            statementText = """Q128. Under the MP Civil Services (Leave) Rules, 1977, what is the maximum number of days of Earned Leave (EL) that can be accumulated by a government servant?""",
            statementTextHindi = """प्रश्न 128. म.प्र. सिविल सेवा (अवकाश) नियम, 1977 के तहत, किसी सरकारी सेवक द्वारा अधिकतम कितने दिनों का अर्जित अवकाश (EL) संचित किया जा सकता है?""",
            options = listOf(Option(1, "180 days"), Option(2, "240 days"), Option(3, "300 days"), Option(4, "365 days")),
            correctOptionIndex = 2,
            explanation = """Solution: A state government employee in MP can accumulate a maximum of 300 days of Earned Leave in their service tenure, which can be encashed at retirement.""",
            explanationHindi = """समाधान: एमपी में एक राज्य सरकार का कर्मचारी अपने सेवा कार्यकाल में अधिकतम 300 दिनों का अर्जित अवकाश संचित कर सकता है, जिसे सेवानिवृत्ति पर भुनाया जा सकता है।
Expert Advice: Read the MP Leave Rules carefully, particularly regarding EL, Casual Leave (CL), and Maternity/Paternity leave durations.
विशेषज्ञ की सलाह: म.प्र. अवकाश नियमों को ध्यान से पढ़ें, विशेष रूप से EL, आकस्मिक अवकाश (CL), और मातृत्व/पितृत्व अवकाश की अवधि के संबंध में।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 89,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 129,
            sectionId = "sec_a",
            questionNumber = 129,
            statementText = """Q129. According to MP Govt Service Rules, female government employees are entitled to maternity leave for how many days?""",
            statementTextHindi = """प्रश्न 129. म.प्र. सरकारी सेवा नियमों के अनुसार, महिला सरकारी कर्मचारी कितने दिनों के मातृत्व अवकाश की हकदार हैं?""",
            options = listOf(Option(1, "90 days"), Option(2, "135 days"), Option(3, "180 days"), Option(4, "240 days")),
            correctOptionIndex = 2,
            explanation = """Solution: Female government servants in MP are entitled to 180 days of maternity leave up to two living children.""",
            explanationHindi = """समाधान: म.प्र. में महिला सरकारी कर्मचारी दो जीवित बच्चों तक 180 दिनों के मातृत्व अवकाश की हकदार हैं।
Expert Advice: Leave policies for female employees and child care leave (CCL) are frequently asked in the Govt Service Rules paper.
विशेषज्ञ की सलाह: महिला कर्मचारियों के लिए अवकाश नीतियां और चाइल्ड केयर लीव (CCL) अक्सर सरकारी सेवा नियम के पेपर में पूछे जाते हैं।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 96,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 130,
            sectionId = "sec_a",
            questionNumber = 130,
            statementText = """Q130. Under the MP Civil Services (Conduct) Rules 1965, can a government servant take part in political elections?""",
            statementTextHindi = """प्रश्न 130. म.प्र. सिविल सेवा (आचरण) नियम 1965 के तहत, क्या कोई सरकारी कर्मचारी राजनीतिक चुनावों में भाग ले सकता है?""",
            options = listOf(Option(1, "Yes, with permission"), Option(2, "Yes, during leave"), Option(3, "No, it is completely prohibited"), Option(4, "Yes, if fighting as independent")),
            correctOptionIndex = 2,
            explanation = """Solution: Rule 5 of the MP Civil Services (Conduct) Rules strictly prohibits any government servant from taking part in politics or elections.""",
            explanationHindi = """समाधान: म.प्र. सिविल सेवा (आचरण) नियम का नियम 5 किसी भी सरकारी सेवक को राजनीति या चुनाव में भाग लेने से सख्ती से रोकता है।
Expert Advice: The Conduct Rules clearly outline prohibitions regarding politics, press, and unauthorized communication. Review Rule 5 and Rule 7 closely.
विशेषज्ञ की सलाह: आचरण नियम राजनीति, प्रेस और अनधिकृत संचार के संबंध में प्रतिबंधों को स्पष्ट रूप से रेखांकित करते हैं। नियम 5 और नियम 7 की बारीकी से समीक्षा करें।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 3,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 131,
            sectionId = "sec_a",
            questionNumber = 131,
            statementText = """Q131. Dearness Allowance (DA) is provided to government employees primarily to compensate for?""",
            statementTextHindi = """प्रश्न 131. महंगाई भत्ता (DA) सरकारी कर्मचारियों को मुख्य रूप से किसकी भरपाई के लिए प्रदान किया जाता है?""",
            options = listOf(Option(1, "Travel expenses"), Option(2, "Housing costs"), Option(3, "Medical expenses"), Option(4, "Inflation / Cost of living")),
            correctOptionIndex = 3,
            explanation = """Solution: Dearness Allowance is calculated as a percentage of basic salary to mitigate the impact of inflation on employees.""",
            explanationHindi = """समाधान: महंगाई भत्ते की गणना कर्मचारियों पर मुद्रास्फीति के प्रभाव को कम करने के लिए मूल वेतन के प्रतिशत के रूप में की जाती है।
Expert Advice: Basic understanding of Salary structures (Basic, DA, HRA) is a crucial part of the 30-mark Govt Service Rules section.
विशेषज्ञ की सलाह: वेतन संरचनाओं (Basic, DA, HRA) की बुनियादी समझ 30-अंकों वाले सरकारी सेवा नियम अनुभाग का एक महत्वपूर्ण हिस्सा है।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 10,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 132,
            sectionId = "sec_a",
            questionNumber = 132,
            statementText = """Q132. In the context of MP Revenue Terminology, what does the term 'Khasra' signify?""",
            statementTextHindi = """प्रश्न 132. म.प्र. राजस्व शब्दावली के संदर्भ में, 'खसरा' शब्द का क्या अर्थ है?""",
            options = listOf(Option(1, "A tax collection receipt"), Option(2, "A village map"), Option(3, "A primary agricultural record containing plot details"), Option(4, "A type of land revenue tax")),
            correctOptionIndex = 2,
            explanation = """Solution: Khasra is a primary land record document that contains details of the land parcel, crop grown, soil type, and the cultivator's name.""",
            explanationHindi = """समाधान: खसरा एक प्राथमिक भूमि रिकॉर्ड दस्तावेज़ है जिसमें भूमि पार्सल, उगाई गई फसल, मिट्टी के प्रकार और कृषक के नाम का विवरण होता है।
Expert Advice: Differentiate clearly between Khasra (plot details), Khatauni (holding details of a person), and Shajra (village map).
विशेषज्ञ की सलाह: खसरा (भूखंड विवरण), खतौनी (किसी व्यक्ति के जोत का विवरण), और शजरा (गाँव का नक्शा) के बीच स्पष्ट रूप से अंतर करें।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 17,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 133,
            sectionId = "sec_a",
            questionNumber = 133,
            statementText = """Q133. What is meant by 'Nazul' land in the revenue department?""",
            statementTextHindi = """प्रश्न 133. राजस्व विभाग में 'नज़ूल' भूमि से क्या तात्पर्य है?""",
            options = listOf(Option(1, "Private agricultural land"), Option(2, "Forest department land"), Option(3, "State-owned land situated within or near urban areas"), Option(4, "Irrigated land")),
            correctOptionIndex = 2,
            explanation = """Solution: Nazul lands are state-owned plots situated within municipal/urban limits often leased out for residential or commercial purposes.""",
            explanationHindi = """समाधान: नज़ूल भूमि नगरपालिका/शहरी सीमा के भीतर स्थित राज्य के स्वामित्व वाले भूखंड हैं जिन्हें अक्सर आवासीय या व्यावसायिक उद्देश्यों के लिए पट्टे पर दिया जाता है।
Expert Advice: Nazul rules and leasing provisions are essential parts of the Revenue Book Circular (RBC).
विशेषज्ञ की सलाह: नज़ूल नियम और पट्टे के प्रावधान राजस्व पुस्तक परिपत्र (RBC) के आवश्यक भाग हैं।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 24,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 134,
            sectionId = "sec_a",
            questionNumber = 134,
            statementText = """Q134. Under which section of the MP Land Revenue Code, 1959 is the 'Board of Revenue' established?""",
            statementTextHindi = """प्रश्न 134. म.प्र. भू-राजस्व संहिता, 1959 की किस धारा के तहत 'राजस्व मंडल' की स्थापना की गई है?""",
            options = listOf(Option(1, "Section 2"), Option(2, "Section 3"), Option(3, "Section 11"), Option(4, "Section 158")),
            correctOptionIndex = 1,
            explanation = """Solution: Section 3 of the MPLRC, 1959 provisions for the constitution of the Board of Revenue, the highest revenue court in the state.""",
            explanationHindi = """समाधान: MPLRC, 1959 की धारा 3 राजस्व मंडल के गठन का प्रावधान करती है, जो राज्य का सर्वोच्च राजस्व न्यायालय है।
Expert Advice: Memorize the key sections of MPLRC dealing with authorities (Sec 11) and Bhumiswami rights (Sec 158).
विशेषज्ञ की सलाह: अधिकारियों (धारा 11) और भूमिस्वामी अधिकारों (धारा 158) से निपटने वाली MPLRC की प्रमुख धाराओं को याद रखें।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 31,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 135,
            sectionId = "sec_a",
            questionNumber = 135,
            statementText = """Q135. Where is the principal seat or headquarters of the Board of Revenue in Madhya Pradesh located?""",
            statementTextHindi = """प्रश्न 135. मध्य प्रदेश में राजस्व मंडल का प्रमुख स्थान या मुख्यालय कहाँ स्थित है?""",
            options = listOf(Option(1, "Bhopal"), Option(2, "Indore"), Option(3, "Gwalior"), Option(4, "Jabalpur")),
            correctOptionIndex = 2,
            explanation = """Solution: The Board of Revenue (Rajya Swamandal) of Madhya Pradesh is headquartered in Gwalior.""",
            explanationHindi = """समाधान: मध्य प्रदेश के राजस्व मंडल का मुख्यालय ग्वालियर में है।
Expert Advice: While the political capital is Bhopal, key institutions like the High Court (Jabalpur) and Board of Revenue (Gwalior) are elsewhere.
विशेषज्ञ की सलाह: जबकि राजनीतिक राजधानी भोपाल है, उच्च न्यायालय (जबलपुर) और राजस्व मंडल (ग्वालियर) जैसे प्रमुख संस्थान अन्य स्थानों पर हैं।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 38,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 136,
            sectionId = "sec_a",
            questionNumber = 136,
            statementText = """Q136. The 'Bhu-Adhikar Pustika' (Kisan Book) is provided to a Bhumiswami under which section of MPLRC 1959?""",
            statementTextHindi = """प्रश्न 136. MPLRC 1959 की किस धारा के तहत एक भूमिस्वामी को 'भू-अधिकार पुस्तिका' (किसान बुक) प्रदान की जाती है?""",
            options = listOf(Option(1, "Section 108"), Option(2, "Section 114"), Option(3, "Section 158"), Option(4, "Section 242")),
            correctOptionIndex = 1,
            explanation = """Solution: Section 114 of the code mandates the provision of Bhu-Adhikar Pustika to every Bhumiswami containing details of their holdings.""",
            explanationHindi = """समाधान: संहिता की धारा 114 प्रत्येक भूमिस्वामी को भू-अधिकार पुस्तिका के प्रावधान को अनिवार्य करती है जिसमें उनकी जोत का विवरण होता है।
Expert Advice: Sections relating to Record of Rights (Sec 108) and Bhu-Adhikar Pustika (Sec 114) are very high-yield for exams.
विशेषज्ञ की सलाह: अधिकारों के अभिलेख (धारा 108) और भू-अधिकार पुस्तिका (धारा 114) से संबंधित धाराएं परीक्षाओं के लिए बहुत महत्वपूर्ण हैं।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 45,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 137,
            sectionId = "sec_a",
            questionNumber = 137,
            statementText = """Q137. What does the term 'Wajib-ul-arz' refer to under Section 242 of the MPLRC 1959?""",
            statementTextHindi = """प्रश्न 137. MPLRC 1959 की धारा 242 के तहत 'वाजिब-उल-अर्ज़' शब्द का क्या अर्थ है?""",
            options = listOf(Option(1, "Record of customs in a village"), Option(2, "Tax receipt"), Option(3, "Land transfer deed"), Option(4, "Dispute register")),
            correctOptionIndex = 0,
            explanation = """Solution: Wajib-ul-arz is a record prepared by the Sub-Divisional Officer detailing the customs existing in a village.""",
            explanationHindi = """समाधान: वाजिब-उल-अर्ज़ अनुविभागीय अधिकारी द्वारा तैयार किया गया एक रिकॉर्ड है जिसमें गाँव में मौजूद रिवाजों का विवरण होता है।
Expert Advice: Traditional revenue terms from the Mughal/British era are preserved in MPLRC and must be memorized.
विशेषज्ञ की सलाह: मुगल/ब्रिटिश काल के पारंपरिक राजस्व शब्द MPLRC में संरक्षित हैं और इन्हें याद किया जाना चाहिए।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 52,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 138,
            sectionId = "sec_a",
            questionNumber = 138,
            statementText = """Q138. Which part of the Revenue Book Circular (RBC) deals exclusively with providing relief in case of natural calamities?""",
            statementTextHindi = """प्रश्न 138. राजस्व पुस्तक परिपत्र (RBC) का कौन सा भाग विशेष रूप से प्राकृतिक आपदाओं के मामले में राहत प्रदान करने से संबंधित है?""",
            options = listOf(Option(1, "RBC 6-4"), Option(2, "RBC 4-1"), Option(3, "RBC 3-2"), Option(4, "RBC 5-3")),
            correctOptionIndex = 0,
            explanation = """Solution: RBC 6-4 contains provisions and guidelines for financial assistance to citizens affected by natural disasters like drought, flood, or fire.""",
            explanationHindi = """समाधान: RBC 6-4 में सूखा, बाढ़ या आग जैसी प्राकृतिक आपदाओं से प्रभावित नागरिकों को वित्तीय सहायता के प्रावधान और दिशानिर्देश शामिल हैं।
Expert Advice: RBC 6-4 is heavily utilized by Patwaris and Revenue Inspectors in the field. Questions on this are almost guaranteed.
विशेषज्ञ की सलाह: RBC 6-4 का उपयोग मैदान में पटवारियों और राजस्व निरीक्षकों द्वारा भारी मात्रा में किया जाता है। इस पर प्रश्न लगभग तय हैं।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 59,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 139,
            sectionId = "sec_a",
            questionNumber = 139,
            statementText = """Q139. According to the Land Records Manual, who is the field-level official primarily responsible for maintaining the village map (Naksha)?""",
            statementTextHindi = """प्रश्न 139. भू-अभिलेख नियमावली के अनुसार, गाँव के नक्शे (नक्शा) को बनाए रखने के लिए मुख्य रूप से कौन सा मैदानी स्तर का अधिकारी जिम्मेदार है?""",
            options = listOf(Option(1, "Tehsildar"), Option(2, "Revenue Inspector (RI)"), Option(3, "Patwari"), Option(4, "Sub-Divisional Officer (SDO)")),
            correctOptionIndex = 2,
            explanation = """Solution: The Patwari (Halka Patwari) is the foundational revenue official responsible for ground-level land record maintenance and crop inspection.""",
            explanationHindi = """समाधान: पटवारी (हल्का पटवारी) जमीनी स्तर पर भूमि रिकॉर्ड के रखरखाव और फसल निरीक्षण के लिए जिम्मेदार आधारभूत राजस्व अधिकारी है।
Expert Advice: Understand the hierarchy of the Revenue Department: Patwari -> RI -> Naib Tehsildar -> Tehsildar -> SDO -> Collector.
विशेषज्ञ की सलाह: राजस्व विभाग के पदानुक्रम को समझें: पटवारी -> आरआई -> नायब तहसीलदार -> तहसीलदार -> एसडीओ -> कलेक्टर।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 66,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 140,
            sectionId = "sec_a",
            questionNumber = 140,
            statementText = """Q140. What is the primary function of the 'Girdawari' process in MP Revenue operations?""",
            statementTextHindi = """प्रश्न 140. म.प्र. राजस्व संचालन में 'गिरदावरी' प्रक्रिया का प्राथमिक कार्य क्या है?""",
            options = listOf(Option(1, "Tax collection"), Option(2, "Crop inspection and recording"), Option(3, "Land division"), Option(4, "Village election")),
            correctOptionIndex = 1,
            explanation = """Solution: Girdawari is the periodic crop inspection conducted by the Patwari to record which crops are sown in which parcels of land.""",
            explanationHindi = """समाधान: गिरदावरी पटवारी द्वारा किया जाने वाला आवधिक फसल निरीक्षण है जो यह रिकॉर्ड करता है कि भूमि के किस पार्सल में कौन सी फसल बोई गई है।
Expert Advice: The timings of Kharif, Rabi, and Zaid Girdawari as outlined in the Land Records Manual should be known.
विशेषज्ञ की सलाह: भू-अभिलेख नियमावली में उल्लिखित खरीफ, रबी और जायद गिरदावरी के समय का ज्ञान होना चाहिए।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 73,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 141,
            sectionId = "sec_a",
            questionNumber = 141,
            statementText = """Q141. Identify the next number in the series: 2, 6, 12, 20, ?""",
            statementTextHindi = """प्रश्न 141. श्रृंखला में अगली संख्या पहचानें: 2, 6, 12, 20, ?""",
            options = listOf(Option(1, "24"), Option(2, "28"), Option(3, "30"), Option(4, "32")),
            correctOptionIndex = 2,
            explanation = """Solution: The series follows the pattern: 1x2=2, 2x3=6, 3x4=12, 4x5=20. The next term is 5x6=30.""",
            explanationHindi = """समाधान: यह श्रृंखला इस पैटर्न का पालन करती है: 1x2=2, 2x3=6, 3x4=12, 4x5=20। अगला पद 5x6=30 है।
Expert Advice: Master basic multiplication series and differences between consecutive numbers for quick logical reasoning.
विशेषज्ञ की सलाह: त्वरित तार्किक तर्क के लिए बुनियादी गुणा श्रृंखला और लगातार संख्याओं के बीच के अंतर में महारत हासिल करें।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 80,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 142,
            sectionId = "sec_a",
            questionNumber = 142,
            statementText = """Q142. Who is known to have founded the historical city of Gwalior?""",
            statementTextHindi = """प्रश्न 142. ऐतिहासिक शहर ग्वालियर की स्थापना किसने की थी?""",
            options = listOf(Option(1, "Raja Man Singh Tomar"), Option(2, "Suraj Sen"), Option(3, "Mahadji Scindia"), Option(4, "Tatya Tope")),
            correctOptionIndex = 1,
            explanation = """Solution: Gwalior was founded by King Suraj Sen in the 8th century, named after the sage Gwalipa who cured his leprosy.""",
            explanationHindi = """समाधान: ग्वालियर की स्थापना 8वीं शताब्दी में राजा सूरज सेन ने की थी, जिसका नाम ऋषि ग्वालिपा के नाम पर रखा गया था जिन्होंने उनके कुष्ठ रोग को ठीक किया था।
Expert Advice: Local history of MP, especially concerning historical capitals like Gwalior, Indore, and Bhopal, is crucial for Paper 1.
विशेषज्ञ की सलाह: एमपी का स्थानीय इतिहास, विशेष रूप से ग्वालियर, इंदौर और भोपाल जैसी ऐतिहासिक राजधानियों के संबंध में, पेपर 1 के लिए महत्वपूर्ण है।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 87,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 143,
            sectionId = "sec_a",
            questionNumber = 143,
            statementText = """Q143. Which of the following Sandhi (Join) is present in the word 'Suryoday' (सूर्योदय)?""",
            statementTextHindi = """प्रश्न 143. 'सूर्योदय' शब्द में निम्नलिखित में से कौन सी संधि है?""",
            options = listOf(Option(1, "Dirgha Sandhi"), Option(2, "Guna Sandhi"), Option(3, "Vriddhi Sandhi"), Option(4, "Yan Sandhi")),
            correctOptionIndex = 1,
            explanation = """Solution: Suryoday is formed by Surya + Uday (अ + उ = ओ), which is a classic example of Guna Sandhi.""",
            explanationHindi = """समाधान: सूर्योदय, सूर्य + उदय (अ + उ = ओ) से बना है, जो गुण संधि का एक उत्कृष्ट उदाहरण है।
Expert Advice: Practice the basic rules of Swar Sandhi (Vowel Sandhi) as at least 1-2 questions are standard in General Hindi.
विशेषज्ञ की सलाह: स्वर संधि के बुनियादी नियमों का अभ्यास करें क्योंकि सामान्य हिंदी में कम से कम 1-2 प्रश्न मानक होते हैं।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 94,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 144,
            sectionId = "sec_a",
            questionNumber = 144,
            statementText = """Q144. Choose the correct preposition: 'She is very good ___ mathematics.'""",
            statementTextHindi = """प्रश्न 144. सही पूर्वसर्ग (preposition) चुनें: 'She is very good ___ mathematics.'""",
            options = listOf(Option(1, "in"), Option(2, "with"), Option(3, "at"), Option(4, "about")),
            correctOptionIndex = 2,
            explanation = """Solution: The correct preposition to use with 'good' when talking about skills or abilities is 'at' (good at mathematics).""",
            explanationHindi = """समाधान: कौशल या क्षमताओं के बारे में बात करते समय 'good' के साथ उपयोग करने के लिए सही प्रीपोज़िशन 'at' है (good at mathematics)।
Expert Advice: Prepositions with fixed adjectives (good at, afraid of, interested in) are highly tested in MP exams.
विशेषज्ञ की सलाह: निश्चित विशेषणों (good at, afraid of, interested in) वाले पूर्वसर्गों का एमपी परीक्षाओं में अत्यधिक परीक्षण किया जाता है।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 1,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 145,
            sectionId = "sec_a",
            questionNumber = 145,
            statementText = """Q145. If a shopkeeper sells an item for Rs 120 and makes a 20% profit, what was the cost price of the item?""",
            statementTextHindi = """प्रश्न 145. यदि कोई दुकानदार किसी वस्तु को 120 रुपये में बेचता है और 20% का लाभ कमाता है, तो वस्तु का क्रय मूल्य क्या था?""",
            options = listOf(Option(1, "Rs 96"), Option(2, "Rs 100"), Option(3, "Rs 108"), Option(4, "Rs 144")),
            correctOptionIndex = 1,
            explanation = """Solution: Selling Price (SP) = Cost Price (CP) + 20% of CP. 120 = 1.2 * CP. Therefore, CP = 120 / 1.2 = 100.""",
            explanationHindi = """समाधान: विक्रय मूल्य (SP) = क्रय मूल्य (CP) + CP का 20%। 120 = 1.2 * CP. इसलिए, CP = 120 / 1.2 = 100.
Expert Advice: Memorize fraction equivalents of percentages (20% = 1/5) to solve Profit & Loss questions mentally.
विशेषज्ञ की सलाह: लाभ और हानि के प्रश्नों को मानसिक रूप से हल करने के लिए प्रतिशत के अंश समतुल्य (20% = 1/5) याद रखें।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 8,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 146,
            sectionId = "sec_a",
            questionNumber = 146,
            statementText = """Q146. Which vitamin is scientifically known as Ascorbic Acid?""",
            statementTextHindi = """प्रश्न 146. किस विटामिन को वैज्ञानिक रूप से एस्कॉर्बिक एसिड के रूप में जाना जाता है?""",
            options = listOf(Option(1, "Vitamin A"), Option(2, "Vitamin B12"), Option(3, "Vitamin C"), Option(4, "Vitamin D")),
            correctOptionIndex = 2,
            explanation = """Solution: Vitamin C is known as Ascorbic Acid. Its deficiency causes Scurvy.""",
            explanationHindi = """समाधान: विटामिन C को एस्कॉर्बिक एसिड के रूप में जाना जाता है। इसकी कमी से स्कर्वी रोग होता है।
Expert Advice: Learn the chemical names and deficiency diseases for all essential vitamins (A, B-complex, C, D, E, K).
विशेषज्ञ की सलाह: सभी आवश्यक विटामिनों (A, B-कॉम्प्लेक्स, C, D, E, K) के रासायनिक नाम और कमी से होने वाले रोगों को याद करें।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 15,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 147,
            sectionId = "sec_a",
            questionNumber = 147,
            statementText = """Q147. In computer terminology, what is the full form of RAM?""",
            statementTextHindi = """प्रश्न 147. कंप्यूटर शब्दावली में RAM का पूर्ण रूप क्या है?""",
            options = listOf(Option(1, "Read Access Memory"), Option(2, "Random Access Memory"), Option(3, "Run Access Memory"), Option(4, "Random Arithmetic Memory")),
            correctOptionIndex = 1,
            explanation = """Solution: RAM stands for Random Access Memory. It is the primary volatile memory used by the computer to store data temporarily.""",
            explanationHindi = """समाधान: RAM का मतलब रैंडम एक्सेस मेमोरी है। यह कंप्यूटर द्वारा डेटा को अस्थायी रूप से संग्रहीत करने के लिए उपयोग की जाने वाली प्राथमिक अस्थिर (volatile) मेमोरी है।
Expert Advice: Basic hardware components and their abbreviations are a staple of the General Computer Knowledge section.
विशेषज्ञ की सलाह: बुनियादी हार्डवेयर घटक और उनके संक्षिप्त नाम सामान्य कंप्यूटर ज्ञान अनुभाग का एक प्रमुख हिस्सा हैं।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 22,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 148,
            sectionId = "sec_a",
            questionNumber = 148,
            statementText = """Q148. Under the MP Civil Services (Leave) Rules, 1977, what is the maximum number of days of Earned Leave (EL) that can be accumulated by a government servant?""",
            statementTextHindi = """प्रश्न 148. म.प्र. सिविल सेवा (अवकाश) नियम, 1977 के तहत, किसी सरकारी सेवक द्वारा अधिकतम कितने दिनों का अर्जित अवकाश (EL) संचित किया जा सकता है?""",
            options = listOf(Option(1, "180 days"), Option(2, "240 days"), Option(3, "300 days"), Option(4, "365 days")),
            correctOptionIndex = 2,
            explanation = """Solution: A state government employee in MP can accumulate a maximum of 300 days of Earned Leave in their service tenure, which can be encashed at retirement.""",
            explanationHindi = """समाधान: एमपी में एक राज्य सरकार का कर्मचारी अपने सेवा कार्यकाल में अधिकतम 300 दिनों का अर्जित अवकाश संचित कर सकता है, जिसे सेवानिवृत्ति पर भुनाया जा सकता है।
Expert Advice: Read the MP Leave Rules carefully, particularly regarding EL, Casual Leave (CL), and Maternity/Paternity leave durations.
विशेषज्ञ की सलाह: म.प्र. अवकाश नियमों को ध्यान से पढ़ें, विशेष रूप से EL, आकस्मिक अवकाश (CL), और मातृत्व/पितृत्व अवकाश की अवधि के संबंध में।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 29,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 149,
            sectionId = "sec_a",
            questionNumber = 149,
            statementText = """Q149. According to MP Govt Service Rules, female government employees are entitled to maternity leave for how many days?""",
            statementTextHindi = """प्रश्न 149. म.प्र. सरकारी सेवा नियमों के अनुसार, महिला सरकारी कर्मचारी कितने दिनों के मातृत्व अवकाश की हकदार हैं?""",
            options = listOf(Option(1, "90 days"), Option(2, "135 days"), Option(3, "180 days"), Option(4, "240 days")),
            correctOptionIndex = 2,
            explanation = """Solution: Female government servants in MP are entitled to 180 days of maternity leave up to two living children.""",
            explanationHindi = """समाधान: म.प्र. में महिला सरकारी कर्मचारी दो जीवित बच्चों तक 180 दिनों के मातृत्व अवकाश की हकदार हैं।
Expert Advice: Leave policies for female employees and child care leave (CCL) are frequently asked in the Govt Service Rules paper.
विशेषज्ञ की सलाह: महिला कर्मचारियों के लिए अवकाश नीतियां और चाइल्ड केयर लीव (CCL) अक्सर सरकारी सेवा नियम के पेपर में पूछे जाते हैं।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 36,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 150,
            sectionId = "sec_a",
            questionNumber = 150,
            statementText = """Q150. Under the MP Civil Services (Conduct) Rules 1965, can a government servant take part in political elections?""",
            statementTextHindi = """प्रश्न 150. म.प्र. सिविल सेवा (आचरण) नियम 1965 के तहत, क्या कोई सरकारी कर्मचारी राजनीतिक चुनावों में भाग ले सकता है?""",
            options = listOf(Option(1, "Yes, with permission"), Option(2, "Yes, during leave"), Option(3, "No, it is completely prohibited"), Option(4, "Yes, if fighting as independent")),
            correctOptionIndex = 2,
            explanation = """Solution: Rule 5 of the MP Civil Services (Conduct) Rules strictly prohibits any government servant from taking part in politics or elections.""",
            explanationHindi = """समाधान: म.प्र. सिविल सेवा (आचरण) नियम का नियम 5 किसी भी सरकारी सेवक को राजनीति या चुनाव में भाग लेने से सख्ती से रोकता है।
Expert Advice: The Conduct Rules clearly outline prohibitions regarding politics, press, and unauthorized communication. Review Rule 5 and Rule 7 closely.
विशेषज्ञ की सलाह: आचरण नियम राजनीति, प्रेस और अनधिकृत संचार के संबंध में प्रतिबंधों को स्पष्ट रूप से रेखांकित करते हैं। नियम 5 और नियम 7 की बारीकी से समीक्षा करें।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 43,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 151,
            sectionId = "sec_a",
            questionNumber = 151,
            statementText = """Q151. Dearness Allowance (DA) is provided to government employees primarily to compensate for?""",
            statementTextHindi = """प्रश्न 151. महंगाई भत्ता (DA) सरकारी कर्मचारियों को मुख्य रूप से किसकी भरपाई के लिए प्रदान किया जाता है?""",
            options = listOf(Option(1, "Travel expenses"), Option(2, "Housing costs"), Option(3, "Medical expenses"), Option(4, "Inflation / Cost of living")),
            correctOptionIndex = 3,
            explanation = """Solution: Dearness Allowance is calculated as a percentage of basic salary to mitigate the impact of inflation on employees.""",
            explanationHindi = """समाधान: महंगाई भत्ते की गणना कर्मचारियों पर मुद्रास्फीति के प्रभाव को कम करने के लिए मूल वेतन के प्रतिशत के रूप में की जाती है।
Expert Advice: Basic understanding of Salary structures (Basic, DA, HRA) is a crucial part of the 30-mark Govt Service Rules section.
विशेषज्ञ की सलाह: वेतन संरचनाओं (Basic, DA, HRA) की बुनियादी समझ 30-अंकों वाले सरकारी सेवा नियम अनुभाग का एक महत्वपूर्ण हिस्सा है।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 50,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 152,
            sectionId = "sec_a",
            questionNumber = 152,
            statementText = """Q152. In the context of MP Revenue Terminology, what does the term 'Khasra' signify?""",
            statementTextHindi = """प्रश्न 152. म.प्र. राजस्व शब्दावली के संदर्भ में, 'खसरा' शब्द का क्या अर्थ है?""",
            options = listOf(Option(1, "A tax collection receipt"), Option(2, "A village map"), Option(3, "A primary agricultural record containing plot details"), Option(4, "A type of land revenue tax")),
            correctOptionIndex = 2,
            explanation = """Solution: Khasra is a primary land record document that contains details of the land parcel, crop grown, soil type, and the cultivator's name.""",
            explanationHindi = """समाधान: खसरा एक प्राथमिक भूमि रिकॉर्ड दस्तावेज़ है जिसमें भूमि पार्सल, उगाई गई फसल, मिट्टी के प्रकार और कृषक के नाम का विवरण होता है।
Expert Advice: Differentiate clearly between Khasra (plot details), Khatauni (holding details of a person), and Shajra (village map).
विशेषज्ञ की सलाह: खसरा (भूखंड विवरण), खतौनी (किसी व्यक्ति के जोत का विवरण), और शजरा (गाँव का नक्शा) के बीच स्पष्ट रूप से अंतर करें।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 57,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 153,
            sectionId = "sec_a",
            questionNumber = 153,
            statementText = """Q153. What is meant by 'Nazul' land in the revenue department?""",
            statementTextHindi = """प्रश्न 153. राजस्व विभाग में 'नज़ूल' भूमि से क्या तात्पर्य है?""",
            options = listOf(Option(1, "Private agricultural land"), Option(2, "Forest department land"), Option(3, "State-owned land situated within or near urban areas"), Option(4, "Irrigated land")),
            correctOptionIndex = 2,
            explanation = """Solution: Nazul lands are state-owned plots situated within municipal/urban limits often leased out for residential or commercial purposes.""",
            explanationHindi = """समाधान: नज़ूल भूमि नगरपालिका/शहरी सीमा के भीतर स्थित राज्य के स्वामित्व वाले भूखंड हैं जिन्हें अक्सर आवासीय या व्यावसायिक उद्देश्यों के लिए पट्टे पर दिया जाता है।
Expert Advice: Nazul rules and leasing provisions are essential parts of the Revenue Book Circular (RBC).
विशेषज्ञ की सलाह: नज़ूल नियम और पट्टे के प्रावधान राजस्व पुस्तक परिपत्र (RBC) के आवश्यक भाग हैं।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 64,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 154,
            sectionId = "sec_a",
            questionNumber = 154,
            statementText = """Q154. Under which section of the MP Land Revenue Code, 1959 is the 'Board of Revenue' established?""",
            statementTextHindi = """प्रश्न 154. म.प्र. भू-राजस्व संहिता, 1959 की किस धारा के तहत 'राजस्व मंडल' की स्थापना की गई है?""",
            options = listOf(Option(1, "Section 2"), Option(2, "Section 3"), Option(3, "Section 11"), Option(4, "Section 158")),
            correctOptionIndex = 1,
            explanation = """Solution: Section 3 of the MPLRC, 1959 provisions for the constitution of the Board of Revenue, the highest revenue court in the state.""",
            explanationHindi = """समाधान: MPLRC, 1959 की धारा 3 राजस्व मंडल के गठन का प्रावधान करती है, जो राज्य का सर्वोच्च राजस्व न्यायालय है।
Expert Advice: Memorize the key sections of MPLRC dealing with authorities (Sec 11) and Bhumiswami rights (Sec 158).
विशेषज्ञ की सलाह: अधिकारियों (धारा 11) और भूमिस्वामी अधिकारों (धारा 158) से निपटने वाली MPLRC की प्रमुख धाराओं को याद रखें।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 71,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 155,
            sectionId = "sec_a",
            questionNumber = 155,
            statementText = """Q155. Where is the principal seat or headquarters of the Board of Revenue in Madhya Pradesh located?""",
            statementTextHindi = """प्रश्न 155. मध्य प्रदेश में राजस्व मंडल का प्रमुख स्थान या मुख्यालय कहाँ स्थित है?""",
            options = listOf(Option(1, "Bhopal"), Option(2, "Indore"), Option(3, "Gwalior"), Option(4, "Jabalpur")),
            correctOptionIndex = 2,
            explanation = """Solution: The Board of Revenue (Rajya Swamandal) of Madhya Pradesh is headquartered in Gwalior.""",
            explanationHindi = """समाधान: मध्य प्रदेश के राजस्व मंडल का मुख्यालय ग्वालियर में है।
Expert Advice: While the political capital is Bhopal, key institutions like the High Court (Jabalpur) and Board of Revenue (Gwalior) are elsewhere.
विशेषज्ञ की सलाह: जबकि राजनीतिक राजधानी भोपाल है, उच्च न्यायालय (जबलपुर) और राजस्व मंडल (ग्वालियर) जैसे प्रमुख संस्थान अन्य स्थानों पर हैं।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 78,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 156,
            sectionId = "sec_a",
            questionNumber = 156,
            statementText = """Q156. The 'Bhu-Adhikar Pustika' (Kisan Book) is provided to a Bhumiswami under which section of MPLRC 1959?""",
            statementTextHindi = """प्रश्न 156. MPLRC 1959 की किस धारा के तहत एक भूमिस्वामी को 'भू-अधिकार पुस्तिका' (किसान बुक) प्रदान की जाती है?""",
            options = listOf(Option(1, "Section 108"), Option(2, "Section 114"), Option(3, "Section 158"), Option(4, "Section 242")),
            correctOptionIndex = 1,
            explanation = """Solution: Section 114 of the code mandates the provision of Bhu-Adhikar Pustika to every Bhumiswami containing details of their holdings.""",
            explanationHindi = """समाधान: संहिता की धारा 114 प्रत्येक भूमिस्वामी को भू-अधिकार पुस्तिका के प्रावधान को अनिवार्य करती है जिसमें उनकी जोत का विवरण होता है।
Expert Advice: Sections relating to Record of Rights (Sec 108) and Bhu-Adhikar Pustika (Sec 114) are very high-yield for exams.
विशेषज्ञ की सलाह: अधिकारों के अभिलेख (धारा 108) और भू-अधिकार पुस्तिका (धारा 114) से संबंधित धाराएं परीक्षाओं के लिए बहुत महत्वपूर्ण हैं।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 85,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 157,
            sectionId = "sec_a",
            questionNumber = 157,
            statementText = """Q157. What does the term 'Wajib-ul-arz' refer to under Section 242 of the MPLRC 1959?""",
            statementTextHindi = """प्रश्न 157. MPLRC 1959 की धारा 242 के तहत 'वाजिब-उल-अर्ज़' शब्द का क्या अर्थ है?""",
            options = listOf(Option(1, "Record of customs in a village"), Option(2, "Tax receipt"), Option(3, "Land transfer deed"), Option(4, "Dispute register")),
            correctOptionIndex = 0,
            explanation = """Solution: Wajib-ul-arz is a record prepared by the Sub-Divisional Officer detailing the customs existing in a village.""",
            explanationHindi = """समाधान: वाजिब-उल-अर्ज़ अनुविभागीय अधिकारी द्वारा तैयार किया गया एक रिकॉर्ड है जिसमें गाँव में मौजूद रिवाजों का विवरण होता है।
Expert Advice: Traditional revenue terms from the Mughal/British era are preserved in MPLRC and must be memorized.
विशेषज्ञ की सलाह: मुगल/ब्रिटिश काल के पारंपरिक राजस्व शब्द MPLRC में संरक्षित हैं और इन्हें याद किया जाना चाहिए।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 92,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 158,
            sectionId = "sec_a",
            questionNumber = 158,
            statementText = """Q158. Which part of the Revenue Book Circular (RBC) deals exclusively with providing relief in case of natural calamities?""",
            statementTextHindi = """प्रश्न 158. राजस्व पुस्तक परिपत्र (RBC) का कौन सा भाग विशेष रूप से प्राकृतिक आपदाओं के मामले में राहत प्रदान करने से संबंधित है?""",
            options = listOf(Option(1, "RBC 6-4"), Option(2, "RBC 4-1"), Option(3, "RBC 3-2"), Option(4, "RBC 5-3")),
            correctOptionIndex = 0,
            explanation = """Solution: RBC 6-4 contains provisions and guidelines for financial assistance to citizens affected by natural disasters like drought, flood, or fire.""",
            explanationHindi = """समाधान: RBC 6-4 में सूखा, बाढ़ या आग जैसी प्राकृतिक आपदाओं से प्रभावित नागरिकों को वित्तीय सहायता के प्रावधान और दिशानिर्देश शामिल हैं।
Expert Advice: RBC 6-4 is heavily utilized by Patwaris and Revenue Inspectors in the field. Questions on this are almost guaranteed.
विशेषज्ञ की सलाह: RBC 6-4 का उपयोग मैदान में पटवारियों और राजस्व निरीक्षकों द्वारा भारी मात्रा में किया जाता है। इस पर प्रश्न लगभग तय हैं।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 99,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 159,
            sectionId = "sec_a",
            questionNumber = 159,
            statementText = """Q159. According to the Land Records Manual, who is the field-level official primarily responsible for maintaining the village map (Naksha)?""",
            statementTextHindi = """प्रश्न 159. भू-अभिलेख नियमावली के अनुसार, गाँव के नक्शे (नक्शा) को बनाए रखने के लिए मुख्य रूप से कौन सा मैदानी स्तर का अधिकारी जिम्मेदार है?""",
            options = listOf(Option(1, "Tehsildar"), Option(2, "Revenue Inspector (RI)"), Option(3, "Patwari"), Option(4, "Sub-Divisional Officer (SDO)")),
            correctOptionIndex = 2,
            explanation = """Solution: The Patwari (Halka Patwari) is the foundational revenue official responsible for ground-level land record maintenance and crop inspection.""",
            explanationHindi = """समाधान: पटवारी (हल्का पटवारी) जमीनी स्तर पर भूमि रिकॉर्ड के रखरखाव और फसल निरीक्षण के लिए जिम्मेदार आधारभूत राजस्व अधिकारी है।
Expert Advice: Understand the hierarchy of the Revenue Department: Patwari -> RI -> Naib Tehsildar -> Tehsildar -> SDO -> Collector.
विशेषज्ञ की सलाह: राजस्व विभाग के पदानुक्रम को समझें: पटवारी -> आरआई -> नायब तहसीलदार -> तहसीलदार -> एसडीओ -> कलेक्टर।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 6,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 160,
            sectionId = "sec_a",
            questionNumber = 160,
            statementText = """Q160. What is the primary function of the 'Girdawari' process in MP Revenue operations?""",
            statementTextHindi = """प्रश्न 160. म.प्र. राजस्व संचालन में 'गिरदावरी' प्रक्रिया का प्राथमिक कार्य क्या है?""",
            options = listOf(Option(1, "Tax collection"), Option(2, "Crop inspection and recording"), Option(3, "Land division"), Option(4, "Village election")),
            correctOptionIndex = 1,
            explanation = """Solution: Girdawari is the periodic crop inspection conducted by the Patwari to record which crops are sown in which parcels of land.""",
            explanationHindi = """समाधान: गिरदावरी पटवारी द्वारा किया जाने वाला आवधिक फसल निरीक्षण है जो यह रिकॉर्ड करता है कि भूमि के किस पार्सल में कौन सी फसल बोई गई है।
Expert Advice: The timings of Kharif, Rabi, and Zaid Girdawari as outlined in the Land Records Manual should be known.
विशेषज्ञ की सलाह: भू-अभिलेख नियमावली में उल्लिखित खरीफ, रबी और जायद गिरदावरी के समय का ज्ञान होना चाहिए।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 13,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 161,
            sectionId = "sec_a",
            questionNumber = 161,
            statementText = """Q161. Identify the next number in the series: 2, 6, 12, 20, ?""",
            statementTextHindi = """प्रश्न 161. श्रृंखला में अगली संख्या पहचानें: 2, 6, 12, 20, ?""",
            options = listOf(Option(1, "24"), Option(2, "28"), Option(3, "30"), Option(4, "32")),
            correctOptionIndex = 2,
            explanation = """Solution: The series follows the pattern: 1x2=2, 2x3=6, 3x4=12, 4x5=20. The next term is 5x6=30.""",
            explanationHindi = """समाधान: यह श्रृंखला इस पैटर्न का पालन करती है: 1x2=2, 2x3=6, 3x4=12, 4x5=20। अगला पद 5x6=30 है।
Expert Advice: Master basic multiplication series and differences between consecutive numbers for quick logical reasoning.
विशेषज्ञ की सलाह: त्वरित तार्किक तर्क के लिए बुनियादी गुणा श्रृंखला और लगातार संख्याओं के बीच के अंतर में महारत हासिल करें।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 20,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 162,
            sectionId = "sec_a",
            questionNumber = 162,
            statementText = """Q162. Who is known to have founded the historical city of Gwalior?""",
            statementTextHindi = """प्रश्न 162. ऐतिहासिक शहर ग्वालियर की स्थापना किसने की थी?""",
            options = listOf(Option(1, "Raja Man Singh Tomar"), Option(2, "Suraj Sen"), Option(3, "Mahadji Scindia"), Option(4, "Tatya Tope")),
            correctOptionIndex = 1,
            explanation = """Solution: Gwalior was founded by King Suraj Sen in the 8th century, named after the sage Gwalipa who cured his leprosy.""",
            explanationHindi = """समाधान: ग्वालियर की स्थापना 8वीं शताब्दी में राजा सूरज सेन ने की थी, जिसका नाम ऋषि ग्वालिपा के नाम पर रखा गया था जिन्होंने उनके कुष्ठ रोग को ठीक किया था।
Expert Advice: Local history of MP, especially concerning historical capitals like Gwalior, Indore, and Bhopal, is crucial for Paper 1.
विशेषज्ञ की सलाह: एमपी का स्थानीय इतिहास, विशेष रूप से ग्वालियर, इंदौर और भोपाल जैसी ऐतिहासिक राजधानियों के संबंध में, पेपर 1 के लिए महत्वपूर्ण है।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 27,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 163,
            sectionId = "sec_a",
            questionNumber = 163,
            statementText = """Q163. Which of the following Sandhi (Join) is present in the word 'Suryoday' (सूर्योदय)?""",
            statementTextHindi = """प्रश्न 163. 'सूर्योदय' शब्द में निम्नलिखित में से कौन सी संधि है?""",
            options = listOf(Option(1, "Dirgha Sandhi"), Option(2, "Guna Sandhi"), Option(3, "Vriddhi Sandhi"), Option(4, "Yan Sandhi")),
            correctOptionIndex = 1,
            explanation = """Solution: Suryoday is formed by Surya + Uday (अ + उ = ओ), which is a classic example of Guna Sandhi.""",
            explanationHindi = """समाधान: सूर्योदय, सूर्य + उदय (अ + उ = ओ) से बना है, जो गुण संधि का एक उत्कृष्ट उदाहरण है।
Expert Advice: Practice the basic rules of Swar Sandhi (Vowel Sandhi) as at least 1-2 questions are standard in General Hindi.
विशेषज्ञ की सलाह: स्वर संधि के बुनियादी नियमों का अभ्यास करें क्योंकि सामान्य हिंदी में कम से कम 1-2 प्रश्न मानक होते हैं।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 34,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 164,
            sectionId = "sec_a",
            questionNumber = 164,
            statementText = """Q164. Choose the correct preposition: 'She is very good ___ mathematics.'""",
            statementTextHindi = """प्रश्न 164. सही पूर्वसर्ग (preposition) चुनें: 'She is very good ___ mathematics.'""",
            options = listOf(Option(1, "in"), Option(2, "with"), Option(3, "at"), Option(4, "about")),
            correctOptionIndex = 2,
            explanation = """Solution: The correct preposition to use with 'good' when talking about skills or abilities is 'at' (good at mathematics).""",
            explanationHindi = """समाधान: कौशल या क्षमताओं के बारे में बात करते समय 'good' के साथ उपयोग करने के लिए सही प्रीपोज़िशन 'at' है (good at mathematics)।
Expert Advice: Prepositions with fixed adjectives (good at, afraid of, interested in) are highly tested in MP exams.
विशेषज्ञ की सलाह: निश्चित विशेषणों (good at, afraid of, interested in) वाले पूर्वसर्गों का एमपी परीक्षाओं में अत्यधिक परीक्षण किया जाता है।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 41,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 165,
            sectionId = "sec_a",
            questionNumber = 165,
            statementText = """Q165. If a shopkeeper sells an item for Rs 120 and makes a 20% profit, what was the cost price of the item?""",
            statementTextHindi = """प्रश्न 165. यदि कोई दुकानदार किसी वस्तु को 120 रुपये में बेचता है और 20% का लाभ कमाता है, तो वस्तु का क्रय मूल्य क्या था?""",
            options = listOf(Option(1, "Rs 96"), Option(2, "Rs 100"), Option(3, "Rs 108"), Option(4, "Rs 144")),
            correctOptionIndex = 1,
            explanation = """Solution: Selling Price (SP) = Cost Price (CP) + 20% of CP. 120 = 1.2 * CP. Therefore, CP = 120 / 1.2 = 100.""",
            explanationHindi = """समाधान: विक्रय मूल्य (SP) = क्रय मूल्य (CP) + CP का 20%। 120 = 1.2 * CP. इसलिए, CP = 120 / 1.2 = 100.
Expert Advice: Memorize fraction equivalents of percentages (20% = 1/5) to solve Profit & Loss questions mentally.
विशेषज्ञ की सलाह: लाभ और हानि के प्रश्नों को मानसिक रूप से हल करने के लिए प्रतिशत के अंश समतुल्य (20% = 1/5) याद रखें।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 48,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 166,
            sectionId = "sec_a",
            questionNumber = 166,
            statementText = """Q166. Which vitamin is scientifically known as Ascorbic Acid?""",
            statementTextHindi = """प्रश्न 166. किस विटामिन को वैज्ञानिक रूप से एस्कॉर्बिक एसिड के रूप में जाना जाता है?""",
            options = listOf(Option(1, "Vitamin A"), Option(2, "Vitamin B12"), Option(3, "Vitamin C"), Option(4, "Vitamin D")),
            correctOptionIndex = 2,
            explanation = """Solution: Vitamin C is known as Ascorbic Acid. Its deficiency causes Scurvy.""",
            explanationHindi = """समाधान: विटामिन C को एस्कॉर्बिक एसिड के रूप में जाना जाता है। इसकी कमी से स्कर्वी रोग होता है।
Expert Advice: Learn the chemical names and deficiency diseases for all essential vitamins (A, B-complex, C, D, E, K).
विशेषज्ञ की सलाह: सभी आवश्यक विटामिनों (A, B-कॉम्प्लेक्स, C, D, E, K) के रासायनिक नाम और कमी से होने वाले रोगों को याद करें।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 55,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 167,
            sectionId = "sec_a",
            questionNumber = 167,
            statementText = """Q167. In computer terminology, what is the full form of RAM?""",
            statementTextHindi = """प्रश्न 167. कंप्यूटर शब्दावली में RAM का पूर्ण रूप क्या है?""",
            options = listOf(Option(1, "Read Access Memory"), Option(2, "Random Access Memory"), Option(3, "Run Access Memory"), Option(4, "Random Arithmetic Memory")),
            correctOptionIndex = 1,
            explanation = """Solution: RAM stands for Random Access Memory. It is the primary volatile memory used by the computer to store data temporarily.""",
            explanationHindi = """समाधान: RAM का मतलब रैंडम एक्सेस मेमोरी है। यह कंप्यूटर द्वारा डेटा को अस्थायी रूप से संग्रहीत करने के लिए उपयोग की जाने वाली प्राथमिक अस्थिर (volatile) मेमोरी है।
Expert Advice: Basic hardware components and their abbreviations are a staple of the General Computer Knowledge section.
विशेषज्ञ की सलाह: बुनियादी हार्डवेयर घटक और उनके संक्षिप्त नाम सामान्य कंप्यूटर ज्ञान अनुभाग का एक प्रमुख हिस्सा हैं।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 62,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 168,
            sectionId = "sec_a",
            questionNumber = 168,
            statementText = """Q168. Under the MP Civil Services (Leave) Rules, 1977, what is the maximum number of days of Earned Leave (EL) that can be accumulated by a government servant?""",
            statementTextHindi = """प्रश्न 168. म.प्र. सिविल सेवा (अवकाश) नियम, 1977 के तहत, किसी सरकारी सेवक द्वारा अधिकतम कितने दिनों का अर्जित अवकाश (EL) संचित किया जा सकता है?""",
            options = listOf(Option(1, "180 days"), Option(2, "240 days"), Option(3, "300 days"), Option(4, "365 days")),
            correctOptionIndex = 2,
            explanation = """Solution: A state government employee in MP can accumulate a maximum of 300 days of Earned Leave in their service tenure, which can be encashed at retirement.""",
            explanationHindi = """समाधान: एमपी में एक राज्य सरकार का कर्मचारी अपने सेवा कार्यकाल में अधिकतम 300 दिनों का अर्जित अवकाश संचित कर सकता है, जिसे सेवानिवृत्ति पर भुनाया जा सकता है।
Expert Advice: Read the MP Leave Rules carefully, particularly regarding EL, Casual Leave (CL), and Maternity/Paternity leave durations.
विशेषज्ञ की सलाह: म.प्र. अवकाश नियमों को ध्यान से पढ़ें, विशेष रूप से EL, आकस्मिक अवकाश (CL), और मातृत्व/पितृत्व अवकाश की अवधि के संबंध में।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 69,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 169,
            sectionId = "sec_a",
            questionNumber = 169,
            statementText = """Q169. According to MP Govt Service Rules, female government employees are entitled to maternity leave for how many days?""",
            statementTextHindi = """प्रश्न 169. म.प्र. सरकारी सेवा नियमों के अनुसार, महिला सरकारी कर्मचारी कितने दिनों के मातृत्व अवकाश की हकदार हैं?""",
            options = listOf(Option(1, "90 days"), Option(2, "135 days"), Option(3, "180 days"), Option(4, "240 days")),
            correctOptionIndex = 2,
            explanation = """Solution: Female government servants in MP are entitled to 180 days of maternity leave up to two living children.""",
            explanationHindi = """समाधान: म.प्र. में महिला सरकारी कर्मचारी दो जीवित बच्चों तक 180 दिनों के मातृत्व अवकाश की हकदार हैं।
Expert Advice: Leave policies for female employees and child care leave (CCL) are frequently asked in the Govt Service Rules paper.
विशेषज्ञ की सलाह: महिला कर्मचारियों के लिए अवकाश नीतियां और चाइल्ड केयर लीव (CCL) अक्सर सरकारी सेवा नियम के पेपर में पूछे जाते हैं।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 76,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 170,
            sectionId = "sec_a",
            questionNumber = 170,
            statementText = """Q170. Under the MP Civil Services (Conduct) Rules 1965, can a government servant take part in political elections?""",
            statementTextHindi = """प्रश्न 170. म.प्र. सिविल सेवा (आचरण) नियम 1965 के तहत, क्या कोई सरकारी कर्मचारी राजनीतिक चुनावों में भाग ले सकता है?""",
            options = listOf(Option(1, "Yes, with permission"), Option(2, "Yes, during leave"), Option(3, "No, it is completely prohibited"), Option(4, "Yes, if fighting as independent")),
            correctOptionIndex = 2,
            explanation = """Solution: Rule 5 of the MP Civil Services (Conduct) Rules strictly prohibits any government servant from taking part in politics or elections.""",
            explanationHindi = """समाधान: म.प्र. सिविल सेवा (आचरण) नियम का नियम 5 किसी भी सरकारी सेवक को राजनीति या चुनाव में भाग लेने से सख्ती से रोकता है।
Expert Advice: The Conduct Rules clearly outline prohibitions regarding politics, press, and unauthorized communication. Review Rule 5 and Rule 7 closely.
विशेषज्ञ की सलाह: आचरण नियम राजनीति, प्रेस और अनधिकृत संचार के संबंध में प्रतिबंधों को स्पष्ट रूप से रेखांकित करते हैं। नियम 5 और नियम 7 की बारीकी से समीक्षा करें।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 83,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 171,
            sectionId = "sec_a",
            questionNumber = 171,
            statementText = """Q171. Dearness Allowance (DA) is provided to government employees primarily to compensate for?""",
            statementTextHindi = """प्रश्न 171. महंगाई भत्ता (DA) सरकारी कर्मचारियों को मुख्य रूप से किसकी भरपाई के लिए प्रदान किया जाता है?""",
            options = listOf(Option(1, "Travel expenses"), Option(2, "Housing costs"), Option(3, "Medical expenses"), Option(4, "Inflation / Cost of living")),
            correctOptionIndex = 3,
            explanation = """Solution: Dearness Allowance is calculated as a percentage of basic salary to mitigate the impact of inflation on employees.""",
            explanationHindi = """समाधान: महंगाई भत्ते की गणना कर्मचारियों पर मुद्रास्फीति के प्रभाव को कम करने के लिए मूल वेतन के प्रतिशत के रूप में की जाती है।
Expert Advice: Basic understanding of Salary structures (Basic, DA, HRA) is a crucial part of the 30-mark Govt Service Rules section.
विशेषज्ञ की सलाह: वेतन संरचनाओं (Basic, DA, HRA) की बुनियादी समझ 30-अंकों वाले सरकारी सेवा नियम अनुभाग का एक महत्वपूर्ण हिस्सा है।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 90,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 172,
            sectionId = "sec_a",
            questionNumber = 172,
            statementText = """Q172. In the context of MP Revenue Terminology, what does the term 'Khasra' signify?""",
            statementTextHindi = """प्रश्न 172. म.प्र. राजस्व शब्दावली के संदर्भ में, 'खसरा' शब्द का क्या अर्थ है?""",
            options = listOf(Option(1, "A tax collection receipt"), Option(2, "A village map"), Option(3, "A primary agricultural record containing plot details"), Option(4, "A type of land revenue tax")),
            correctOptionIndex = 2,
            explanation = """Solution: Khasra is a primary land record document that contains details of the land parcel, crop grown, soil type, and the cultivator's name.""",
            explanationHindi = """समाधान: खसरा एक प्राथमिक भूमि रिकॉर्ड दस्तावेज़ है जिसमें भूमि पार्सल, उगाई गई फसल, मिट्टी के प्रकार और कृषक के नाम का विवरण होता है।
Expert Advice: Differentiate clearly between Khasra (plot details), Khatauni (holding details of a person), and Shajra (village map).
विशेषज्ञ की सलाह: खसरा (भूखंड विवरण), खतौनी (किसी व्यक्ति के जोत का विवरण), और शजरा (गाँव का नक्शा) के बीच स्पष्ट रूप से अंतर करें।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 97,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 173,
            sectionId = "sec_a",
            questionNumber = 173,
            statementText = """Q173. What is meant by 'Nazul' land in the revenue department?""",
            statementTextHindi = """प्रश्न 173. राजस्व विभाग में 'नज़ूल' भूमि से क्या तात्पर्य है?""",
            options = listOf(Option(1, "Private agricultural land"), Option(2, "Forest department land"), Option(3, "State-owned land situated within or near urban areas"), Option(4, "Irrigated land")),
            correctOptionIndex = 2,
            explanation = """Solution: Nazul lands are state-owned plots situated within municipal/urban limits often leased out for residential or commercial purposes.""",
            explanationHindi = """समाधान: नज़ूल भूमि नगरपालिका/शहरी सीमा के भीतर स्थित राज्य के स्वामित्व वाले भूखंड हैं जिन्हें अक्सर आवासीय या व्यावसायिक उद्देश्यों के लिए पट्टे पर दिया जाता है।
Expert Advice: Nazul rules and leasing provisions are essential parts of the Revenue Book Circular (RBC).
विशेषज्ञ की सलाह: नज़ूल नियम और पट्टे के प्रावधान राजस्व पुस्तक परिपत्र (RBC) के आवश्यक भाग हैं।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 4,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 174,
            sectionId = "sec_a",
            questionNumber = 174,
            statementText = """Q174. Under which section of the MP Land Revenue Code, 1959 is the 'Board of Revenue' established?""",
            statementTextHindi = """प्रश्न 174. म.प्र. भू-राजस्व संहिता, 1959 की किस धारा के तहत 'राजस्व मंडल' की स्थापना की गई है?""",
            options = listOf(Option(1, "Section 2"), Option(2, "Section 3"), Option(3, "Section 11"), Option(4, "Section 158")),
            correctOptionIndex = 1,
            explanation = """Solution: Section 3 of the MPLRC, 1959 provisions for the constitution of the Board of Revenue, the highest revenue court in the state.""",
            explanationHindi = """समाधान: MPLRC, 1959 की धारा 3 राजस्व मंडल के गठन का प्रावधान करती है, जो राज्य का सर्वोच्च राजस्व न्यायालय है।
Expert Advice: Memorize the key sections of MPLRC dealing with authorities (Sec 11) and Bhumiswami rights (Sec 158).
विशेषज्ञ की सलाह: अधिकारियों (धारा 11) और भूमिस्वामी अधिकारों (धारा 158) से निपटने वाली MPLRC की प्रमुख धाराओं को याद रखें।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 11,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 175,
            sectionId = "sec_a",
            questionNumber = 175,
            statementText = """Q175. Where is the principal seat or headquarters of the Board of Revenue in Madhya Pradesh located?""",
            statementTextHindi = """प्रश्न 175. मध्य प्रदेश में राजस्व मंडल का प्रमुख स्थान या मुख्यालय कहाँ स्थित है?""",
            options = listOf(Option(1, "Bhopal"), Option(2, "Indore"), Option(3, "Gwalior"), Option(4, "Jabalpur")),
            correctOptionIndex = 2,
            explanation = """Solution: The Board of Revenue (Rajya Swamandal) of Madhya Pradesh is headquartered in Gwalior.""",
            explanationHindi = """समाधान: मध्य प्रदेश के राजस्व मंडल का मुख्यालय ग्वालियर में है।
Expert Advice: While the political capital is Bhopal, key institutions like the High Court (Jabalpur) and Board of Revenue (Gwalior) are elsewhere.
विशेषज्ञ की सलाह: जबकि राजनीतिक राजधानी भोपाल है, उच्च न्यायालय (जबलपुर) और राजस्व मंडल (ग्वालियर) जैसे प्रमुख संस्थान अन्य स्थानों पर हैं।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 18,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 176,
            sectionId = "sec_a",
            questionNumber = 176,
            statementText = """Q176. The 'Bhu-Adhikar Pustika' (Kisan Book) is provided to a Bhumiswami under which section of MPLRC 1959?""",
            statementTextHindi = """प्रश्न 176. MPLRC 1959 की किस धारा के तहत एक भूमिस्वामी को 'भू-अधिकार पुस्तिका' (किसान बुक) प्रदान की जाती है?""",
            options = listOf(Option(1, "Section 108"), Option(2, "Section 114"), Option(3, "Section 158"), Option(4, "Section 242")),
            correctOptionIndex = 1,
            explanation = """Solution: Section 114 of the code mandates the provision of Bhu-Adhikar Pustika to every Bhumiswami containing details of their holdings.""",
            explanationHindi = """समाधान: संहिता की धारा 114 प्रत्येक भूमिस्वामी को भू-अधिकार पुस्तिका के प्रावधान को अनिवार्य करती है जिसमें उनकी जोत का विवरण होता है।
Expert Advice: Sections relating to Record of Rights (Sec 108) and Bhu-Adhikar Pustika (Sec 114) are very high-yield for exams.
विशेषज्ञ की सलाह: अधिकारों के अभिलेख (धारा 108) और भू-अधिकार पुस्तिका (धारा 114) से संबंधित धाराएं परीक्षाओं के लिए बहुत महत्वपूर्ण हैं।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 25,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 177,
            sectionId = "sec_a",
            questionNumber = 177,
            statementText = """Q177. What does the term 'Wajib-ul-arz' refer to under Section 242 of the MPLRC 1959?""",
            statementTextHindi = """प्रश्न 177. MPLRC 1959 की धारा 242 के तहत 'वाजिब-उल-अर्ज़' शब्द का क्या अर्थ है?""",
            options = listOf(Option(1, "Record of customs in a village"), Option(2, "Tax receipt"), Option(3, "Land transfer deed"), Option(4, "Dispute register")),
            correctOptionIndex = 0,
            explanation = """Solution: Wajib-ul-arz is a record prepared by the Sub-Divisional Officer detailing the customs existing in a village.""",
            explanationHindi = """समाधान: वाजिब-उल-अर्ज़ अनुविभागीय अधिकारी द्वारा तैयार किया गया एक रिकॉर्ड है जिसमें गाँव में मौजूद रिवाजों का विवरण होता है।
Expert Advice: Traditional revenue terms from the Mughal/British era are preserved in MPLRC and must be memorized.
विशेषज्ञ की सलाह: मुगल/ब्रिटिश काल के पारंपरिक राजस्व शब्द MPLRC में संरक्षित हैं और इन्हें याद किया जाना चाहिए।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 32,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 178,
            sectionId = "sec_a",
            questionNumber = 178,
            statementText = """Q178. Which part of the Revenue Book Circular (RBC) deals exclusively with providing relief in case of natural calamities?""",
            statementTextHindi = """प्रश्न 178. राजस्व पुस्तक परिपत्र (RBC) का कौन सा भाग विशेष रूप से प्राकृतिक आपदाओं के मामले में राहत प्रदान करने से संबंधित है?""",
            options = listOf(Option(1, "RBC 6-4"), Option(2, "RBC 4-1"), Option(3, "RBC 3-2"), Option(4, "RBC 5-3")),
            correctOptionIndex = 0,
            explanation = """Solution: RBC 6-4 contains provisions and guidelines for financial assistance to citizens affected by natural disasters like drought, flood, or fire.""",
            explanationHindi = """समाधान: RBC 6-4 में सूखा, बाढ़ या आग जैसी प्राकृतिक आपदाओं से प्रभावित नागरिकों को वित्तीय सहायता के प्रावधान और दिशानिर्देश शामिल हैं।
Expert Advice: RBC 6-4 is heavily utilized by Patwaris and Revenue Inspectors in the field. Questions on this are almost guaranteed.
विशेषज्ञ की सलाह: RBC 6-4 का उपयोग मैदान में पटवारियों और राजस्व निरीक्षकों द्वारा भारी मात्रा में किया जाता है। इस पर प्रश्न लगभग तय हैं।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 39,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 179,
            sectionId = "sec_a",
            questionNumber = 179,
            statementText = """Q179. According to the Land Records Manual, who is the field-level official primarily responsible for maintaining the village map (Naksha)?""",
            statementTextHindi = """प्रश्न 179. भू-अभिलेख नियमावली के अनुसार, गाँव के नक्शे (नक्शा) को बनाए रखने के लिए मुख्य रूप से कौन सा मैदानी स्तर का अधिकारी जिम्मेदार है?""",
            options = listOf(Option(1, "Tehsildar"), Option(2, "Revenue Inspector (RI)"), Option(3, "Patwari"), Option(4, "Sub-Divisional Officer (SDO)")),
            correctOptionIndex = 2,
            explanation = """Solution: The Patwari (Halka Patwari) is the foundational revenue official responsible for ground-level land record maintenance and crop inspection.""",
            explanationHindi = """समाधान: पटवारी (हल्का पटवारी) जमीनी स्तर पर भूमि रिकॉर्ड के रखरखाव और फसल निरीक्षण के लिए जिम्मेदार आधारभूत राजस्व अधिकारी है।
Expert Advice: Understand the hierarchy of the Revenue Department: Patwari -> RI -> Naib Tehsildar -> Tehsildar -> SDO -> Collector.
विशेषज्ञ की सलाह: राजस्व विभाग के पदानुक्रम को समझें: पटवारी -> आरआई -> नायब तहसीलदार -> तहसीलदार -> एसडीओ -> कलेक्टर।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 46,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 180,
            sectionId = "sec_a",
            questionNumber = 180,
            statementText = """Q180. What is the primary function of the 'Girdawari' process in MP Revenue operations?""",
            statementTextHindi = """प्रश्न 180. म.प्र. राजस्व संचालन में 'गिरदावरी' प्रक्रिया का प्राथमिक कार्य क्या है?""",
            options = listOf(Option(1, "Tax collection"), Option(2, "Crop inspection and recording"), Option(3, "Land division"), Option(4, "Village election")),
            correctOptionIndex = 1,
            explanation = """Solution: Girdawari is the periodic crop inspection conducted by the Patwari to record which crops are sown in which parcels of land.""",
            explanationHindi = """समाधान: गिरदावरी पटवारी द्वारा किया जाने वाला आवधिक फसल निरीक्षण है जो यह रिकॉर्ड करता है कि भूमि के किस पार्सल में कौन सी फसल बोई गई है।
Expert Advice: The timings of Kharif, Rabi, and Zaid Girdawari as outlined in the Land Records Manual should be known.
विशेषज्ञ की सलाह: भू-अभिलेख नियमावली में उल्लिखित खरीफ, रबी और जायद गिरदावरी के समय का ज्ञान होना चाहिए।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 53,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 181,
            sectionId = "sec_a",
            questionNumber = 181,
            statementText = """Q181. Identify the next number in the series: 2, 6, 12, 20, ?""",
            statementTextHindi = """प्रश्न 181. श्रृंखला में अगली संख्या पहचानें: 2, 6, 12, 20, ?""",
            options = listOf(Option(1, "24"), Option(2, "28"), Option(3, "30"), Option(4, "32")),
            correctOptionIndex = 2,
            explanation = """Solution: The series follows the pattern: 1x2=2, 2x3=6, 3x4=12, 4x5=20. The next term is 5x6=30.""",
            explanationHindi = """समाधान: यह श्रृंखला इस पैटर्न का पालन करती है: 1x2=2, 2x3=6, 3x4=12, 4x5=20। अगला पद 5x6=30 है।
Expert Advice: Master basic multiplication series and differences between consecutive numbers for quick logical reasoning.
विशेषज्ञ की सलाह: त्वरित तार्किक तर्क के लिए बुनियादी गुणा श्रृंखला और लगातार संख्याओं के बीच के अंतर में महारत हासिल करें।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 60,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 182,
            sectionId = "sec_a",
            questionNumber = 182,
            statementText = """Q182. Who is known to have founded the historical city of Gwalior?""",
            statementTextHindi = """प्रश्न 182. ऐतिहासिक शहर ग्वालियर की स्थापना किसने की थी?""",
            options = listOf(Option(1, "Raja Man Singh Tomar"), Option(2, "Suraj Sen"), Option(3, "Mahadji Scindia"), Option(4, "Tatya Tope")),
            correctOptionIndex = 1,
            explanation = """Solution: Gwalior was founded by King Suraj Sen in the 8th century, named after the sage Gwalipa who cured his leprosy.""",
            explanationHindi = """समाधान: ग्वालियर की स्थापना 8वीं शताब्दी में राजा सूरज सेन ने की थी, जिसका नाम ऋषि ग्वालिपा के नाम पर रखा गया था जिन्होंने उनके कुष्ठ रोग को ठीक किया था।
Expert Advice: Local history of MP, especially concerning historical capitals like Gwalior, Indore, and Bhopal, is crucial for Paper 1.
विशेषज्ञ की सलाह: एमपी का स्थानीय इतिहास, विशेष रूप से ग्वालियर, इंदौर और भोपाल जैसी ऐतिहासिक राजधानियों के संबंध में, पेपर 1 के लिए महत्वपूर्ण है।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 67,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 183,
            sectionId = "sec_a",
            questionNumber = 183,
            statementText = """Q183. Which of the following Sandhi (Join) is present in the word 'Suryoday' (सूर्योदय)?""",
            statementTextHindi = """प्रश्न 183. 'सूर्योदय' शब्द में निम्नलिखित में से कौन सी संधि है?""",
            options = listOf(Option(1, "Dirgha Sandhi"), Option(2, "Guna Sandhi"), Option(3, "Vriddhi Sandhi"), Option(4, "Yan Sandhi")),
            correctOptionIndex = 1,
            explanation = """Solution: Suryoday is formed by Surya + Uday (अ + उ = ओ), which is a classic example of Guna Sandhi.""",
            explanationHindi = """समाधान: सूर्योदय, सूर्य + उदय (अ + उ = ओ) से बना है, जो गुण संधि का एक उत्कृष्ट उदाहरण है।
Expert Advice: Practice the basic rules of Swar Sandhi (Vowel Sandhi) as at least 1-2 questions are standard in General Hindi.
विशेषज्ञ की सलाह: स्वर संधि के बुनियादी नियमों का अभ्यास करें क्योंकि सामान्य हिंदी में कम से कम 1-2 प्रश्न मानक होते हैं।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 74,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 184,
            sectionId = "sec_a",
            questionNumber = 184,
            statementText = """Q184. Choose the correct preposition: 'She is very good ___ mathematics.'""",
            statementTextHindi = """प्रश्न 184. सही पूर्वसर्ग (preposition) चुनें: 'She is very good ___ mathematics.'""",
            options = listOf(Option(1, "in"), Option(2, "with"), Option(3, "at"), Option(4, "about")),
            correctOptionIndex = 2,
            explanation = """Solution: The correct preposition to use with 'good' when talking about skills or abilities is 'at' (good at mathematics).""",
            explanationHindi = """समाधान: कौशल या क्षमताओं के बारे में बात करते समय 'good' के साथ उपयोग करने के लिए सही प्रीपोज़िशन 'at' है (good at mathematics)।
Expert Advice: Prepositions with fixed adjectives (good at, afraid of, interested in) are highly tested in MP exams.
विशेषज्ञ की सलाह: निश्चित विशेषणों (good at, afraid of, interested in) वाले पूर्वसर्गों का एमपी परीक्षाओं में अत्यधिक परीक्षण किया जाता है।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 81,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 185,
            sectionId = "sec_a",
            questionNumber = 185,
            statementText = """Q185. If a shopkeeper sells an item for Rs 120 and makes a 20% profit, what was the cost price of the item?""",
            statementTextHindi = """प्रश्न 185. यदि कोई दुकानदार किसी वस्तु को 120 रुपये में बेचता है और 20% का लाभ कमाता है, तो वस्तु का क्रय मूल्य क्या था?""",
            options = listOf(Option(1, "Rs 96"), Option(2, "Rs 100"), Option(3, "Rs 108"), Option(4, "Rs 144")),
            correctOptionIndex = 1,
            explanation = """Solution: Selling Price (SP) = Cost Price (CP) + 20% of CP. 120 = 1.2 * CP. Therefore, CP = 120 / 1.2 = 100.""",
            explanationHindi = """समाधान: विक्रय मूल्य (SP) = क्रय मूल्य (CP) + CP का 20%। 120 = 1.2 * CP. इसलिए, CP = 120 / 1.2 = 100.
Expert Advice: Memorize fraction equivalents of percentages (20% = 1/5) to solve Profit & Loss questions mentally.
विशेषज्ञ की सलाह: लाभ और हानि के प्रश्नों को मानसिक रूप से हल करने के लिए प्रतिशत के अंश समतुल्य (20% = 1/5) याद रखें।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 88,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 186,
            sectionId = "sec_a",
            questionNumber = 186,
            statementText = """Q186. Which vitamin is scientifically known as Ascorbic Acid?""",
            statementTextHindi = """प्रश्न 186. किस विटामिन को वैज्ञानिक रूप से एस्कॉर्बिक एसिड के रूप में जाना जाता है?""",
            options = listOf(Option(1, "Vitamin A"), Option(2, "Vitamin B12"), Option(3, "Vitamin C"), Option(4, "Vitamin D")),
            correctOptionIndex = 2,
            explanation = """Solution: Vitamin C is known as Ascorbic Acid. Its deficiency causes Scurvy.""",
            explanationHindi = """समाधान: विटामिन C को एस्कॉर्बिक एसिड के रूप में जाना जाता है। इसकी कमी से स्कर्वी रोग होता है।
Expert Advice: Learn the chemical names and deficiency diseases for all essential vitamins (A, B-complex, C, D, E, K).
विशेषज्ञ की सलाह: सभी आवश्यक विटामिनों (A, B-कॉम्प्लेक्स, C, D, E, K) के रासायनिक नाम और कमी से होने वाले रोगों को याद करें।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 95,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 187,
            sectionId = "sec_a",
            questionNumber = 187,
            statementText = """Q187. In computer terminology, what is the full form of RAM?""",
            statementTextHindi = """प्रश्न 187. कंप्यूटर शब्दावली में RAM का पूर्ण रूप क्या है?""",
            options = listOf(Option(1, "Read Access Memory"), Option(2, "Random Access Memory"), Option(3, "Run Access Memory"), Option(4, "Random Arithmetic Memory")),
            correctOptionIndex = 1,
            explanation = """Solution: RAM stands for Random Access Memory. It is the primary volatile memory used by the computer to store data temporarily.""",
            explanationHindi = """समाधान: RAM का मतलब रैंडम एक्सेस मेमोरी है। यह कंप्यूटर द्वारा डेटा को अस्थायी रूप से संग्रहीत करने के लिए उपयोग की जाने वाली प्राथमिक अस्थिर (volatile) मेमोरी है।
Expert Advice: Basic hardware components and their abbreviations are a staple of the General Computer Knowledge section.
विशेषज्ञ की सलाह: बुनियादी हार्डवेयर घटक और उनके संक्षिप्त नाम सामान्य कंप्यूटर ज्ञान अनुभाग का एक प्रमुख हिस्सा हैं।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 2,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 188,
            sectionId = "sec_a",
            questionNumber = 188,
            statementText = """Q188. Under the MP Civil Services (Leave) Rules, 1977, what is the maximum number of days of Earned Leave (EL) that can be accumulated by a government servant?""",
            statementTextHindi = """प्रश्न 188. म.प्र. सिविल सेवा (अवकाश) नियम, 1977 के तहत, किसी सरकारी सेवक द्वारा अधिकतम कितने दिनों का अर्जित अवकाश (EL) संचित किया जा सकता है?""",
            options = listOf(Option(1, "180 days"), Option(2, "240 days"), Option(3, "300 days"), Option(4, "365 days")),
            correctOptionIndex = 2,
            explanation = """Solution: A state government employee in MP can accumulate a maximum of 300 days of Earned Leave in their service tenure, which can be encashed at retirement.""",
            explanationHindi = """समाधान: एमपी में एक राज्य सरकार का कर्मचारी अपने सेवा कार्यकाल में अधिकतम 300 दिनों का अर्जित अवकाश संचित कर सकता है, जिसे सेवानिवृत्ति पर भुनाया जा सकता है।
Expert Advice: Read the MP Leave Rules carefully, particularly regarding EL, Casual Leave (CL), and Maternity/Paternity leave durations.
विशेषज्ञ की सलाह: म.प्र. अवकाश नियमों को ध्यान से पढ़ें, विशेष रूप से EL, आकस्मिक अवकाश (CL), और मातृत्व/पितृत्व अवकाश की अवधि के संबंध में।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 9,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 189,
            sectionId = "sec_a",
            questionNumber = 189,
            statementText = """Q189. According to MP Govt Service Rules, female government employees are entitled to maternity leave for how many days?""",
            statementTextHindi = """प्रश्न 189. म.प्र. सरकारी सेवा नियमों के अनुसार, महिला सरकारी कर्मचारी कितने दिनों के मातृत्व अवकाश की हकदार हैं?""",
            options = listOf(Option(1, "90 days"), Option(2, "135 days"), Option(3, "180 days"), Option(4, "240 days")),
            correctOptionIndex = 2,
            explanation = """Solution: Female government servants in MP are entitled to 180 days of maternity leave up to two living children.""",
            explanationHindi = """समाधान: म.प्र. में महिला सरकारी कर्मचारी दो जीवित बच्चों तक 180 दिनों के मातृत्व अवकाश की हकदार हैं।
Expert Advice: Leave policies for female employees and child care leave (CCL) are frequently asked in the Govt Service Rules paper.
विशेषज्ञ की सलाह: महिला कर्मचारियों के लिए अवकाश नीतियां और चाइल्ड केयर लीव (CCL) अक्सर सरकारी सेवा नियम के पेपर में पूछे जाते हैं।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 16,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 190,
            sectionId = "sec_a",
            questionNumber = 190,
            statementText = """Q190. Under the MP Civil Services (Conduct) Rules 1965, can a government servant take part in political elections?""",
            statementTextHindi = """प्रश्न 190. म.प्र. सिविल सेवा (आचरण) नियम 1965 के तहत, क्या कोई सरकारी कर्मचारी राजनीतिक चुनावों में भाग ले सकता है?""",
            options = listOf(Option(1, "Yes, with permission"), Option(2, "Yes, during leave"), Option(3, "No, it is completely prohibited"), Option(4, "Yes, if fighting as independent")),
            correctOptionIndex = 2,
            explanation = """Solution: Rule 5 of the MP Civil Services (Conduct) Rules strictly prohibits any government servant from taking part in politics or elections.""",
            explanationHindi = """समाधान: म.प्र. सिविल सेवा (आचरण) नियम का नियम 5 किसी भी सरकारी सेवक को राजनीति या चुनाव में भाग लेने से सख्ती से रोकता है।
Expert Advice: The Conduct Rules clearly outline prohibitions regarding politics, press, and unauthorized communication. Review Rule 5 and Rule 7 closely.
विशेषज्ञ की सलाह: आचरण नियम राजनीति, प्रेस और अनधिकृत संचार के संबंध में प्रतिबंधों को स्पष्ट रूप से रेखांकित करते हैं। नियम 5 और नियम 7 की बारीकी से समीक्षा करें।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 23,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 191,
            sectionId = "sec_a",
            questionNumber = 191,
            statementText = """Q191. Dearness Allowance (DA) is provided to government employees primarily to compensate for?""",
            statementTextHindi = """प्रश्न 191. महंगाई भत्ता (DA) सरकारी कर्मचारियों को मुख्य रूप से किसकी भरपाई के लिए प्रदान किया जाता है?""",
            options = listOf(Option(1, "Travel expenses"), Option(2, "Housing costs"), Option(3, "Medical expenses"), Option(4, "Inflation / Cost of living")),
            correctOptionIndex = 3,
            explanation = """Solution: Dearness Allowance is calculated as a percentage of basic salary to mitigate the impact of inflation on employees.""",
            explanationHindi = """समाधान: महंगाई भत्ते की गणना कर्मचारियों पर मुद्रास्फीति के प्रभाव को कम करने के लिए मूल वेतन के प्रतिशत के रूप में की जाती है।
Expert Advice: Basic understanding of Salary structures (Basic, DA, HRA) is a crucial part of the 30-mark Govt Service Rules section.
विशेषज्ञ की सलाह: वेतन संरचनाओं (Basic, DA, HRA) की बुनियादी समझ 30-अंकों वाले सरकारी सेवा नियम अनुभाग का एक महत्वपूर्ण हिस्सा है।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 30,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 192,
            sectionId = "sec_a",
            questionNumber = 192,
            statementText = """Q192. In the context of MP Revenue Terminology, what does the term 'Khasra' signify?""",
            statementTextHindi = """प्रश्न 192. म.प्र. राजस्व शब्दावली के संदर्भ में, 'खसरा' शब्द का क्या अर्थ है?""",
            options = listOf(Option(1, "A tax collection receipt"), Option(2, "A village map"), Option(3, "A primary agricultural record containing plot details"), Option(4, "A type of land revenue tax")),
            correctOptionIndex = 2,
            explanation = """Solution: Khasra is a primary land record document that contains details of the land parcel, crop grown, soil type, and the cultivator's name.""",
            explanationHindi = """समाधान: खसरा एक प्राथमिक भूमि रिकॉर्ड दस्तावेज़ है जिसमें भूमि पार्सल, उगाई गई फसल, मिट्टी के प्रकार और कृषक के नाम का विवरण होता है।
Expert Advice: Differentiate clearly between Khasra (plot details), Khatauni (holding details of a person), and Shajra (village map).
विशेषज्ञ की सलाह: खसरा (भूखंड विवरण), खतौनी (किसी व्यक्ति के जोत का विवरण), और शजरा (गाँव का नक्शा) के बीच स्पष्ट रूप से अंतर करें।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 37,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 193,
            sectionId = "sec_a",
            questionNumber = 193,
            statementText = """Q193. What is meant by 'Nazul' land in the revenue department?""",
            statementTextHindi = """प्रश्न 193. राजस्व विभाग में 'नज़ूल' भूमि से क्या तात्पर्य है?""",
            options = listOf(Option(1, "Private agricultural land"), Option(2, "Forest department land"), Option(3, "State-owned land situated within or near urban areas"), Option(4, "Irrigated land")),
            correctOptionIndex = 2,
            explanation = """Solution: Nazul lands are state-owned plots situated within municipal/urban limits often leased out for residential or commercial purposes.""",
            explanationHindi = """समाधान: नज़ूल भूमि नगरपालिका/शहरी सीमा के भीतर स्थित राज्य के स्वामित्व वाले भूखंड हैं जिन्हें अक्सर आवासीय या व्यावसायिक उद्देश्यों के लिए पट्टे पर दिया जाता है।
Expert Advice: Nazul rules and leasing provisions are essential parts of the Revenue Book Circular (RBC).
विशेषज्ञ की सलाह: नज़ूल नियम और पट्टे के प्रावधान राजस्व पुस्तक परिपत्र (RBC) के आवश्यक भाग हैं।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 44,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 194,
            sectionId = "sec_a",
            questionNumber = 194,
            statementText = """Q194. Under which section of the MP Land Revenue Code, 1959 is the 'Board of Revenue' established?""",
            statementTextHindi = """प्रश्न 194. म.प्र. भू-राजस्व संहिता, 1959 की किस धारा के तहत 'राजस्व मंडल' की स्थापना की गई है?""",
            options = listOf(Option(1, "Section 2"), Option(2, "Section 3"), Option(3, "Section 11"), Option(4, "Section 158")),
            correctOptionIndex = 1,
            explanation = """Solution: Section 3 of the MPLRC, 1959 provisions for the constitution of the Board of Revenue, the highest revenue court in the state.""",
            explanationHindi = """समाधान: MPLRC, 1959 की धारा 3 राजस्व मंडल के गठन का प्रावधान करती है, जो राज्य का सर्वोच्च राजस्व न्यायालय है।
Expert Advice: Memorize the key sections of MPLRC dealing with authorities (Sec 11) and Bhumiswami rights (Sec 158).
विशेषज्ञ की सलाह: अधिकारियों (धारा 11) और भूमिस्वामी अधिकारों (धारा 158) से निपटने वाली MPLRC की प्रमुख धाराओं को याद रखें।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 51,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 195,
            sectionId = "sec_a",
            questionNumber = 195,
            statementText = """Q195. Where is the principal seat or headquarters of the Board of Revenue in Madhya Pradesh located?""",
            statementTextHindi = """प्रश्न 195. मध्य प्रदेश में राजस्व मंडल का प्रमुख स्थान या मुख्यालय कहाँ स्थित है?""",
            options = listOf(Option(1, "Bhopal"), Option(2, "Indore"), Option(3, "Gwalior"), Option(4, "Jabalpur")),
            correctOptionIndex = 2,
            explanation = """Solution: The Board of Revenue (Rajya Swamandal) of Madhya Pradesh is headquartered in Gwalior.""",
            explanationHindi = """समाधान: मध्य प्रदेश के राजस्व मंडल का मुख्यालय ग्वालियर में है।
Expert Advice: While the political capital is Bhopal, key institutions like the High Court (Jabalpur) and Board of Revenue (Gwalior) are elsewhere.
विशेषज्ञ की सलाह: जबकि राजनीतिक राजधानी भोपाल है, उच्च न्यायालय (जबलपुर) और राजस्व मंडल (ग्वालियर) जैसे प्रमुख संस्थान अन्य स्थानों पर हैं।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 58,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 196,
            sectionId = "sec_a",
            questionNumber = 196,
            statementText = """Q196. The 'Bhu-Adhikar Pustika' (Kisan Book) is provided to a Bhumiswami under which section of MPLRC 1959?""",
            statementTextHindi = """प्रश्न 196. MPLRC 1959 की किस धारा के तहत एक भूमिस्वामी को 'भू-अधिकार पुस्तिका' (किसान बुक) प्रदान की जाती है?""",
            options = listOf(Option(1, "Section 108"), Option(2, "Section 114"), Option(3, "Section 158"), Option(4, "Section 242")),
            correctOptionIndex = 1,
            explanation = """Solution: Section 114 of the code mandates the provision of Bhu-Adhikar Pustika to every Bhumiswami containing details of their holdings.""",
            explanationHindi = """समाधान: संहिता की धारा 114 प्रत्येक भूमिस्वामी को भू-अधिकार पुस्तिका के प्रावधान को अनिवार्य करती है जिसमें उनकी जोत का विवरण होता है।
Expert Advice: Sections relating to Record of Rights (Sec 108) and Bhu-Adhikar Pustika (Sec 114) are very high-yield for exams.
विशेषज्ञ की सलाह: अधिकारों के अभिलेख (धारा 108) और भू-अधिकार पुस्तिका (धारा 114) से संबंधित धाराएं परीक्षाओं के लिए बहुत महत्वपूर्ण हैं।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 65,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 197,
            sectionId = "sec_a",
            questionNumber = 197,
            statementText = """Q197. What does the term 'Wajib-ul-arz' refer to under Section 242 of the MPLRC 1959?""",
            statementTextHindi = """प्रश्न 197. MPLRC 1959 की धारा 242 के तहत 'वाजिब-उल-अर्ज़' शब्द का क्या अर्थ है?""",
            options = listOf(Option(1, "Record of customs in a village"), Option(2, "Tax receipt"), Option(3, "Land transfer deed"), Option(4, "Dispute register")),
            correctOptionIndex = 0,
            explanation = """Solution: Wajib-ul-arz is a record prepared by the Sub-Divisional Officer detailing the customs existing in a village.""",
            explanationHindi = """समाधान: वाजिब-उल-अर्ज़ अनुविभागीय अधिकारी द्वारा तैयार किया गया एक रिकॉर्ड है जिसमें गाँव में मौजूद रिवाजों का विवरण होता है।
Expert Advice: Traditional revenue terms from the Mughal/British era are preserved in MPLRC and must be memorized.
विशेषज्ञ की सलाह: मुगल/ब्रिटिश काल के पारंपरिक राजस्व शब्द MPLRC में संरक्षित हैं और इन्हें याद किया जाना चाहिए।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 72,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 198,
            sectionId = "sec_a",
            questionNumber = 198,
            statementText = """Q198. Which part of the Revenue Book Circular (RBC) deals exclusively with providing relief in case of natural calamities?""",
            statementTextHindi = """प्रश्न 198. राजस्व पुस्तक परिपत्र (RBC) का कौन सा भाग विशेष रूप से प्राकृतिक आपदाओं के मामले में राहत प्रदान करने से संबंधित है?""",
            options = listOf(Option(1, "RBC 6-4"), Option(2, "RBC 4-1"), Option(3, "RBC 3-2"), Option(4, "RBC 5-3")),
            correctOptionIndex = 0,
            explanation = """Solution: RBC 6-4 contains provisions and guidelines for financial assistance to citizens affected by natural disasters like drought, flood, or fire.""",
            explanationHindi = """समाधान: RBC 6-4 में सूखा, बाढ़ या आग जैसी प्राकृतिक आपदाओं से प्रभावित नागरिकों को वित्तीय सहायता के प्रावधान और दिशानिर्देश शामिल हैं।
Expert Advice: RBC 6-4 is heavily utilized by Patwaris and Revenue Inspectors in the field. Questions on this are almost guaranteed.
विशेषज्ञ की सलाह: RBC 6-4 का उपयोग मैदान में पटवारियों और राजस्व निरीक्षकों द्वारा भारी मात्रा में किया जाता है। इस पर प्रश्न लगभग तय हैं।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 79,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 199,
            sectionId = "sec_a",
            questionNumber = 199,
            statementText = """Q199. According to the Land Records Manual, who is the field-level official primarily responsible for maintaining the village map (Naksha)?""",
            statementTextHindi = """प्रश्न 199. भू-अभिलेख नियमावली के अनुसार, गाँव के नक्शे (नक्शा) को बनाए रखने के लिए मुख्य रूप से कौन सा मैदानी स्तर का अधिकारी जिम्मेदार है?""",
            options = listOf(Option(1, "Tehsildar"), Option(2, "Revenue Inspector (RI)"), Option(3, "Patwari"), Option(4, "Sub-Divisional Officer (SDO)")),
            correctOptionIndex = 2,
            explanation = """Solution: The Patwari (Halka Patwari) is the foundational revenue official responsible for ground-level land record maintenance and crop inspection.""",
            explanationHindi = """समाधान: पटवारी (हल्का पटवारी) जमीनी स्तर पर भूमि रिकॉर्ड के रखरखाव और फसल निरीक्षण के लिए जिम्मेदार आधारभूत राजस्व अधिकारी है।
Expert Advice: Understand the hierarchy of the Revenue Department: Patwari -> RI -> Naib Tehsildar -> Tehsildar -> SDO -> Collector.
विशेषज्ञ की सलाह: राजस्व विभाग के पदानुक्रम को समझें: पटवारी -> आरआई -> नायब तहसीलदार -> तहसीलदार -> एसडीओ -> कलेक्टर।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 86,
            averageTimeSeconds = 15
        ))

        allQuestions.add(Question(
            id = 200,
            sectionId = "sec_a",
            questionNumber = 200,
            statementText = """Q200. What is the primary function of the 'Girdawari' process in MP Revenue operations?""",
            statementTextHindi = """प्रश्न 200. म.प्र. राजस्व संचालन में 'गिरदावरी' प्रक्रिया का प्राथमिक कार्य क्या है?""",
            options = listOf(Option(1, "Tax collection"), Option(2, "Crop inspection and recording"), Option(3, "Land division"), Option(4, "Village election")),
            correctOptionIndex = 1,
            explanation = """Solution: Girdawari is the periodic crop inspection conducted by the Patwari to record which crops are sown in which parcels of land.""",
            explanationHindi = """समाधान: गिरदावरी पटवारी द्वारा किया जाने वाला आवधिक फसल निरीक्षण है जो यह रिकॉर्ड करता है कि भूमि के किस पार्सल में कौन सी फसल बोई गई है।
Expert Advice: The timings of Kharif, Rabi, and Zaid Girdawari as outlined in the Land Records Manual should be known.
विशेषज्ञ की सलाह: भू-अभिलेख नियमावली में उल्लिखित खरीफ, रबी और जायद गिरदावरी के समय का ज्ञान होना चाहिए।""",
            topic = "General",
            subject = "Paper 1",
            percentGotRight = 93,
            averageTimeSeconds = 15
        ))

    }

    override suspend fun getQuestionsForTest(testId: String): Result<List<Question>> {
        delay(300)
        return Result.success(allQuestions)
    }

    override suspend fun getBookmarkedQuestions(): Result<List<Int>> {
        return Result.success(listOf(2, 4))
    }

    override suspend fun bookmarkQuestion(questionId: Int, bookmarked: Boolean): Result<Unit> {
        delay(300)
        return Result.success(Unit)
    }
}
