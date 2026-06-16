package com.jacqulin.client.domain

interface AiClient {
    suspend fun analyzeText(text: String): String
    suspend fun analyzeImage(strBase64: String): String
}