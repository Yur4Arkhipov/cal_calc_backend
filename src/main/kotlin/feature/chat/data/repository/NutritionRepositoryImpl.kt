package com.jacqulin.feature.chat.data.repository

import com.jacqulin.client.domain.AiClient
import com.jacqulin.feature.chat.domain.repository.NutritionRepository

class NutritionRepositoryImpl(
    private val aiClient: AiClient
) : NutritionRepository {

    override suspend fun analyzeText(message: String): String {
        return aiClient.analyzeText(message)
    }

    override suspend fun analyzeImage(strBase64: String): String {
        return aiClient.analyzeImage(strBase64)
    }
}