package com.jacqulin.client.ai

import com.jacqulin.client.domain.AiClient
import com.jacqulin.client.dto.*
import com.jacqulin.config.AiConfig
import com.jacqulin.domain.Nutrition
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.io.IOException
import kotlinx.serialization.json.Json

class YandexAiClient(
    private val client: HttpClient,
    config: AiConfig
) : AiClient {
    private val model = config.model
    private val apiKey = config.apiKey

    override suspend fun analyzeText(text: String): String {
        try {
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
                                        text = Instructions.instructions
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
                                schema = Schema.nutritionSchema
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

        } catch (e: ClientRequestException) {
            throw AiUnavailableException(e)
        } catch (e: ServerResponseException) {
            throw AiUnavailableException(e)
        } catch (e: IOException) {
            throw AiUnavailableException(e)
        }
    }

    override suspend fun analyzeImage(strBase64: String): String {
        try {
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
                                        text = Instructions.instructions
                                    )
                                )
                            ),
                            YandexMessage(
                                role = "user",
                                content = listOf(
                                    YandexContent(
                                        type = "image_url",
                                        image_url = YandexImageUrl(
                                            url = "data:image/jpeg;base64,$strBase64"
                                        ),
                                    )
                                )
                            )
                        ),
                        response_format = YandexResponseFormat(
                            type = "json_schema",
                            json_schema = YandexJsonSchema(
                                name = "nutrition",
                                schema = Schema.nutritionSchema
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
        } catch (e: ClientRequestException) {
            throw AiUnavailableException(e)
        } catch (e: ServerResponseException) {
            throw AiUnavailableException(e)
        } catch (e: IOException) {
            throw AiUnavailableException(e)
        }
    }

    override suspend fun refineMeal(
        currentMeal: Nutrition,
        userPrompt: String
    ): String {
        try {
            val mealJson = Json.encodeToString(currentMeal)

            val prompt = """
            You are a nutrition analysis system.
            Current meal:
            $mealJson
          
            User correction:
            $userPrompt
            
            Return updated meal as JSON.
        """.trimIndent()


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
                                        text = Instructions.instructions
                                    )
                                )
                            ),
                            YandexMessage(
                                role = "user",
                                content = listOf(
                                    YandexContent(
                                        type = "text",
                                        text = prompt
                                    )
                                )
                            )
                        ),
                        response_format = YandexResponseFormat(
                            type = "json_schema",
                            json_schema = YandexJsonSchema(
                                name = "nutrition",
                                schema = Schema.nutritionSchema
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
        } catch (e: ClientRequestException) {
            throw AiUnavailableException(e)
        } catch (e: ServerResponseException) {
            throw AiUnavailableException(e)
        } catch (e: IOException) {
            throw AiUnavailableException(e)
        }
    }
}