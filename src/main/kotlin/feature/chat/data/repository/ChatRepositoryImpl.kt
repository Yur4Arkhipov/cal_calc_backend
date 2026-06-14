package com.jacqulin.feature.chat.data.repository

import com.jacqulin.client.ai.AiClient
import com.jacqulin.feature.chat.domain.repository.ChatRepository

class ChatRepositoryImpl(
    private val aiClient: AiClient
) : ChatRepository {

    override suspend fun ask(message: String): String {
        return aiClient.sendMessage(message)
    }
}