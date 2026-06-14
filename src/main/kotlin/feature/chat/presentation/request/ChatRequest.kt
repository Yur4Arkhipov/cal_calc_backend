package com.jacqulin.feature.chat.presentation.request

import kotlinx.serialization.Serializable

@Serializable
data class ChatRequest(
    val message: String
)