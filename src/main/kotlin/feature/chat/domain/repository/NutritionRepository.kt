package com.jacqulin.feature.chat.domain.repository


interface NutritionRepository {
    suspend fun analyzeText(message: String): String
    suspend fun analyzeImage(strBase64: String): String
}