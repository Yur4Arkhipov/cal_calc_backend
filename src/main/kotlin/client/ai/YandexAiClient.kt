package com.jacqulin.client.ai

import com.jacqulin.client.domain.AiClient
import com.jacqulin.client.dto.*
import com.jacqulin.config.AiConfig
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

class YandexAiClient(
    private val client: HttpClient,
    config: AiConfig
) : AiClient {
    private val model = config.model
    private val apiKey = config.apiKey

    override suspend fun analyzeNutrition(text: String): String {
        val response: YandexChatResponse = client.post("https://ai.api.cloud.yandex.net/v1/chat/completions") {
            header(
                "Authorization",
                "Api-Key $apiKey"
            )
            contentType(ContentType.Application.Json)
            setBody(
                YandexChatRequest(
                    model = model,
                    messages = listOf(
                        YandexMessage(
                            role = "system",
                            content = listOf(
                                YandexContent(
                                    type = "text",
                                    text = SystemInstructions.instructions
                                )
                            )
                        ),
                        YandexMessage(
                            role = "user",
                            content = listOf(
                                YandexContent(
                                    type = "text",
                                    text = text
                                )
                            )
                        )
                    ),
                    response_format = YandexResponseFormat(
                        type = "json_schema",
                        json_schema = YandexJsonSchema(
                            name = "nutrition",
                            schema = NutritionSchema.schema
                        )
                    )
                )
            )
        }.body()

        val content = response.choices
            .firstOrNull()
            ?.message
            ?.content
            ?: error("AI returned empty response")

        return content
    }
}