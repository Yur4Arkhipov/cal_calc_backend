package com.jacqulin.client.dto

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class YandexChatRequest(
    val model: String,
    val messages: List<YandexMessage>,
    val response_format: YandexResponseFormat? = null,
//    val temperature: Double = 0.3,
    val max_completion_tokens: Int = 1000
)

@Serializable
data class YandexMessage(
    val role: String,
    val content: List<YandexContent>
)

@Serializable
data class YandexContent(
    val type: String,
    val text: String? = null,
    val image_url: YandexImageUrl? = null
)

@Serializable
data class YandexImageUrl(
    val url: String
)

@Serializable
data class YandexResponseFormat(
    val type: String,
    val json_schema: YandexJsonSchema? = null
)

@Serializable
data class YandexJsonSchema(
    val name: String,
    val schema: JsonElement
)