package com.jacqulin.dto

import kotlinx.serialization.Serializable

@Serializable
data class ChatRequest(
    val message: String
)