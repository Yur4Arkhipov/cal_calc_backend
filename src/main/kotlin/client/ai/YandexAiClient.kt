package com.jacqulin.client.ai

import com.jacqulin.client.dto.*
import com.jacqulin.config.AiConfig
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.json.*

class YandexAiClient(
    private val client: HttpClient,
    private val config: AiConfig
) : AiClient {
    private val model = config.model

    private val systemInstructions = """
        Ты профессиональный диетолог и анализатор пищи по изображению.
        
        Твоя задача:
        - определить блюдо на изображении
        - оценить примерный вес порции
        - определить калории и БЖУ
        
        ВАЖНЫЕ ПРАВИЛА:
        
        - Ответ должен быть ТОЛЬКО валидным JSON.
        - Не используй markdown.
        - Не добавляй пояснения.
        - Не добавляй текст до или после JSON.
        
        - Название блюда должно быть конкретным и понятным.
        - Если на изображении несколько продуктов — объединяй их в одно блюдо.
        - Не придумывай ингредиенты, которых нет на изображении.
        - Если состав определить невозможно — используй наиболее вероятный вариант.
        - Если размер порции неясен — предполагай среднюю порцию 250-400 г.
        - Все значения должны быть реалистичными.
        - Не завышай белки.
        - Не занижай жиры.
        - Используй средние пищевые значения для обычных домашних блюд.
        - Для промышленных продуктов используй типичные данные продукта.
        
        Формат ответа:
        
        {
          "name": "string",
          "weight": number,
          "calories": number,
          "protein": number,
          "fat": number,
          "carb": number,
        }
        
        Если на изображении нет еды или блюдо невозможно определить, ответ:
        
        {
          "name": "not_food",
          "weight": 0,
          "calories": 0,
          "protein": 0,
          "fat": 0,
          "carbs": 0,
        }
        """.trimIndent()

    private val nutritionSchema = buildJsonObject {
        put("type", "object")
        putJsonObject("properties") {
            putJsonObject("name") {
                put("type", "string")
            }
            putJsonObject("weight") {
                put("type", "integer")
            }
            putJsonObject("calories") {
                put("type", "integer")
            }
            putJsonObject("protein") {
                put("type", "integer")
            }
            putJsonObject("fat") {
                put("type", "integer")
            }
            putJsonObject("carb") {
                put("type", "integer")
            }
        }
        putJsonArray("required") {
            add("name")
            add("weight")
            add("calories")
            add("protein")
            add("fat")
            add("carb")
        }
    }

    override suspend fun sendMessage(text: String): String {
        val response: YandexChatResponse = client.post("https://ai.api.cloud.yandex.net/v1/chat/completions") {
            header(
                "Authorization",
                "Api-Key ${config.apiKey}"
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
                                    text = systemInstructions
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
                            schema = nutritionSchema
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