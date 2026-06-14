package com.jacqulin.feature.chat.domain.usecase

import com.jacqulin.feature.chat.domain.repository.ChatRepository

class SendMessageUseCase(
    private val repository: ChatRepository
) {
    suspend operator fun invoke(message: String): String {
        return repository.ask(message)
    }
}