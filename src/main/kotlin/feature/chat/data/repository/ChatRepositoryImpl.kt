package com.jacqulin.feature.chat.data.repository

import com.jacqulin.client.domain.AiClient
import com.jacqulin.feature.chat.domain.repository.ChatRepository

class ChatRepositoryImpl(
    private val aiClient: AiClient
) : ChatRepository {

    override suspend fun ask(message: String): String {
        return aiClient.analyzeNutrition(message)
    }
}