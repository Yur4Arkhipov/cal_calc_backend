package com.jacqulin.client.ai

interface AiClient {
    suspend fun sendMessage(text: String): String
}