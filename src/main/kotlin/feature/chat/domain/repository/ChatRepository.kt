package com.jacqulin.feature.chat.domain.repository

interface ChatRepository {
    suspend fun ask(message: String): String
}