package com.example.abhyaas.data.mock

import com.example.abhyaas.data.model.StudyMaterial

object MockStudyMaterialRepository {
    fun getMaterialsForSeries(seriesId: String): List<StudyMaterial> = listOf(
        // MPLRC 1959
        StudyMaterial(
            id = "sm_mplrc_1",
            title = "Adhiniyam",
            description = "Revenue law and Adhiniyam for Nayab Tehsildar exam.",
            subject = "MPLRC 1959",
            pdfUrl = "https://drive.google.com/file/d/1otlModfBD5Gv3HmZ6WGeexC9nuNHsazq/preview",
            pdfSize = "3.2 MB",
            pageCount = 48,
            seriesId = seriesId,
            isFree = true
        ),
        StudyMaterial(
            id = "sm_mplrc_2",
            title = "MPLRC 1959 Amended",
            description = "Amended rules of MPLRC 1959.",
            subject = "MPLRC 1959",
            pdfUrl = "https://drive.google.com/file/d/1MeBhkxJYVH078aO2IgSORT0OtRvCF3c4/preview",
            pdfSize = "3.2 MB",
            pageCount = 48,
            seriesId = seriesId,
            isFree = true
        ),
        StudyMaterial(
            id = "sm_mplrc_3",
            title = "MPLRC 2018 - Seemankan Rules (Hindi)",
            description = "Seemankan rules under MPLRC 2018.",
            subject = "MPLRC 1959",
            pdfUrl = "https://drive.google.com/file/d/10odZo24YebrsnjYcx3JUkMNNPUW4DGuG/preview",
            pdfSize = "1.5 MB",
            pageCount = 20,
            seriesId = seriesId,
            isFree = true
        ),
        StudyMaterial(
            id = "sm_mplrc_4",
            title = "MPLRC 2019 - Prakriya Rules (Hindi)",
            description = "Prakriya guidelines for revenue officers.",
            subject = "MPLRC 1959",
            pdfUrl = "https://drive.google.com/file/d/1gd4XmzhbXViIBw74wqKQSC9L32TcdXOw/preview",
            pdfSize = "1.5 MB",
            pageCount = 20,
            seriesId = seriesId,
            isFree = true
        ),
        StudyMaterial(
            id = "sm_mplrc_5",
            title = "MPLRC 2020 - Bantwara Rules (Hindi)",
            description = "Land division and bantwara regulations.",
            subject = "MPLRC 1959",
            pdfUrl = "https://drive.google.com/file/d/1vVlb6w1Nhgqcr-uir5AUiVAafHw-aRqB/preview",
            pdfSize = "1.5 MB",
            pageCount = 20,
            seriesId = seriesId,
            isFree = true
        ),
        StudyMaterial(
            id = "sm_mplrc_6",
            title = "MPLRC 2020 - Dakhal Rahit Bhumi Rules (Hindi)",
            description = "Rules concerning unoccupied land.",
            subject = "MPLRC 1959",
            pdfUrl = "https://drive.google.com/file/d/1dy9mkPulOuJ3Sw1oN3tEYzI37kfKVRtJ/preview",
            pdfSize = "1.5 MB",
            pageCount = 20,
            seriesId = seriesId,
            isFree = true
        ),
        StudyMaterial(
            id = "sm_mplrc_7",
            title = "MPLRC 2020 - Vividh Rules (Hindi)",
            description = "Miscellaneous rules under MPLRC 2020.",
            subject = "MPLRC 1959",
            pdfUrl = "https://drive.google.com/file/d/1NhGZL9rN2emAVBU5IPwnFbA-8VUwuhgx/preview",
            pdfSize = "1.5 MB",
            pageCount = 20,
            seriesId = seriesId,
            isFree = true
        ),
        // RBC / Revenue
        StudyMaterial(
            id = "sm_rbc_1",
            title = "RBC 6-4 Crop Loss Amendment",
            description = "Amendment regarding crop loss evaluation.",
            subject = "RBC / Revenue",
            pdfUrl = "https://drive.google.com/file/d/10QU-q7YREhWP5_AETfbSvvuwgxqXsEkw/preview",
            pdfSize = "0.8 MB",
            pageCount = 12,
            seriesId = seriesId,
            isFree = true
        ),
        StudyMaterial(
            id = "sm_rbc_2",
            title = "RBC 6-4 Kela Hetu",
            description = "Specific revenue board circular for banana crop loss.",
            subject = "RBC / Revenue",
            pdfUrl = "https://drive.google.com/file/d/1xnxiJFit-k3qAxABM2AqKZe-k8wo58JO/preview",
            pdfSize = "0.8 MB",
            pageCount = 12,
            seriesId = seriesId,
            isFree = true
        ),
        StudyMaterial(
            id = "sm_rbc_3",
            title = "RBC Sanshodhan 1.2.23",
            description = "Revenue board circular amendments as of Feb 2023.",
            subject = "RBC / Revenue",
            pdfUrl = "https://drive.google.com/file/d/1mw6obVfd1c0t_o9nkjB_5QuzroAKPchA/preview",
            pdfSize = "0.8 MB",
            pageCount = 12,
            seriesId = seriesId,
            isFree = true
        ),
        StudyMaterial(
            id = "sm_rbc_4",
            title = "RBC 4-1 MP Nazul Bhumi Nirdesh",
            description = "Directives regarding Nazul lands in MP.",
            subject = "RBC / Revenue",
            pdfUrl = "https://drive.google.com/file/d/1AzzZHQtoqXys0H86yAiWfD3Kroz7wc1u/preview",
            pdfSize = "0.8 MB",
            pageCount = 12,
            seriesId = seriesId,
            isFree = true
        ),
        StudyMaterial(
            id = "sm_rbc_5",
            title = "RBC Section 6 Krmank 4",
            description = "Section 6 guidelines of Revenue Board Circulars.",
            subject = "RBC / Revenue",
            pdfUrl = "https://drive.google.com/file/d/1S74w9Sl643ZWTkZc1K44D7E6SnphSnr3/preview",
            pdfSize = "0.8 MB",
            pageCount = 12,
            seriesId = seriesId,
            isFree = true
        ),
        // Govt. Service Rules
        StudyMaterial(
            id = "sm_govt_1",
            title = "Gazette Niyamawali",
            description = "Official gazette notifications and rules.",
            subject = "Govt. Service Rules",
            pdfUrl = "https://drive.google.com/file/d/1TRznu0ZEPoN0HGPl2qx51VsBoivIpLod/preview",
            pdfSize = "1.2 MB",
            pageCount = 18,
            seriesId = seriesId,
            isFree = true
        ),
        StudyMaterial(
            id = "sm_govt_2",
            title = "MPPSC Rules 1966 (Vargikaran, Niyantran, Appeal)",
            description = "Rules for classification, control, and appeal.",
            subject = "Govt. Service Rules",
            pdfUrl = "https://drive.google.com/file/d/13tUtstaiC0fH_kyGaYc0DZS7K6mPt1g0/preview",
            pdfSize = "1.2 MB",
            pageCount = 18,
            seriesId = seriesId,
            isFree = true
        ),
        StudyMaterial(
            id = "sm_govt_3",
            title = "Order Rule 2019 - Nagadikaran",
            description = "Government orders on cash management.",
            subject = "Govt. Service Rules",
            pdfUrl = "https://drive.google.com/file/d/1aVOJm894AFLdnCobSbdiULpiXHa_ACH_/preview",
            pdfSize = "1.2 MB",
            pageCount = 18,
            seriesId = seriesId,
            isFree = true
        ),
        StudyMaterial(
            id = "sm_govt_4",
            title = "Suspension Period Over 1 Year - Rules",
            description = "Rules covering suspension exceeding one year.",
            subject = "Govt. Service Rules",
            pdfUrl = "https://drive.google.com/file/d/1lMIwrYJSVn0Jx646Lnz9gmDCfV8yr197/preview",
            pdfSize = "1.2 MB",
            pageCount = 18,
            seriesId = seriesId,
            isFree = true
        ),
        StudyMaterial(
            id = "sm_govt_5",
            title = "Revenue Dept. Leave Rules 2026",
            description = "Leave guidelines for Revenue Department officials.",
            subject = "Govt. Service Rules",
            pdfUrl = "https://drive.google.com/file/d/1_uYSb8mfjmLKT0zxljPhwB_D6RGMadRk/preview",
            pdfSize = "1.2 MB",
            pageCount = 18,
            seriesId = seriesId,
            isFree = true
        ),
        // Computer
        StudyMaterial(
            id = "sm_comp_1",
            title = "Basic Computer Part 1",
            description = "Fundamentals of computer knowledge for departmental exams.",
            subject = "Computer",
            pdfUrl = "https://drive.google.com/file/d/1b2lVTBOfFST6_NczcHFaDkEj4PznUskd/preview",
            pdfSize = "2.1 MB",
            pageCount = 30,
            seriesId = seriesId,
            isFree = true
        ),
        StudyMaterial(
            id = "sm_comp_2",
            title = "Basic Computer Part 2",
            description = "Advanced topics in basic computer knowledge.",
            subject = "Computer",
            pdfUrl = "https://drive.google.com/file/d/1SWdrbjfh6eYw1c3FjqXN9rwIclgoBxme/preview",
            pdfSize = "2.1 MB",
            pageCount = 30,
            seriesId = seriesId,
            isFree = true
        ),
        // General Reference
        StudyMaterial(
            id = "sm_gen_1",
            title = "General Reference Index",
            description = "Comprehensive index of study materials.",
            subject = "General Reference",
            pdfUrl = "https://drive.google.com/file/d/1Tolmqx2Pmx0LWQ3QaRbw7E6HZMbNpwh_/preview",
            pdfSize = "0.4 MB",
            pageCount = 6,
            seriesId = seriesId,
            isFree = true
        )
    )
}
