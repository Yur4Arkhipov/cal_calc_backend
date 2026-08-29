package com.jacqulin.feature.chat.domain.repository

import com.jacqulin.domain.Nutrition

interface NutritionRepository {
    suspend fun analyzeText(message: String): String
    suspend fun analyzeImage(strBase64: String): String
    suspend fun refineMeal(currentMeal: Nutrition, userPrompt: String): String
}