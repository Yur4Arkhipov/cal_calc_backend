package com.jacqulin.client.domain

import com.jacqulin.domain.Nutrition

interface AiClient {
    suspend fun analyzeText(text: String): String
    suspend fun analyzeImage(strBase64: String): String
    suspend fun refineMeal(currentMeal: Nutrition, userPrompt: String): String
}