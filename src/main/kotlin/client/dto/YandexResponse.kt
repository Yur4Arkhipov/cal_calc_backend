package com.jacqulin.client.dto

import kotlinx.serialization.Serializable

@Serializable
data class YandexChatResponse(
    val id: String,
    val `object`: String,
    val created: Long,
    val model: String,
    val choices: List<YandexChoice>,
    val usage: YandexUsage?
)

@Serializable
data class YandexChoice(
    val index: Int,
    val message: YandexAssistantMessage,
    val finish_reason: String
)

@Serializable
data class YandexAssistantMessage(
    val role: String,
    val content: String
)

@Serializable
data class YandexUsage(
    val prompt_tokens: Int,
    val total_tokens: Int,
    val completion_tokens: Int
)