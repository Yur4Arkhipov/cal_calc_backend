package com.jacqulin.dto

import kotlinx.serialization.Serializable

@Serializable
data class ChatResponse(
    val response: String
)