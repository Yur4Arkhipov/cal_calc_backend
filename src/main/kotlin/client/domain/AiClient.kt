package com.jacqulin.client.domain

interface AiClient {
    suspend fun analyzeNutrition(text: String): String
}