package com.jacqulin.di

import com.jacqulin.client.domain.AiClient
import com.jacqulin.client.ai.YandexAiClient
import com.jacqulin.config.AiConfig
import com.jacqulin.feature.chat.data.repository.ChatRepositoryImpl
import com.jacqulin.feature.chat.domain.repository.ChatRepository
import com.jacqulin.feature.chat.domain.usecase.SendMessageUseCase
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.config.ApplicationConfig
import kotlinx.serialization.json.Json
import org.koin.dsl.module

fun appModule(
    config: ApplicationConfig
) = module {

    single {
        HttpClient(CIO) {
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                    }
                )
            }
        }
    }

    single<AiClient> {
        YandexAiClient(
            client = get(),
            config = get()
        )
    }

    single<ChatRepository> {
        ChatRepositoryImpl(
            aiClient = get()
        )
    }

    factory {
        SendMessageUseCase(
            repository = get()
        )
    }

    single {
        AiConfig(
            apiKey = config.property("yandex.apiKey").getString(),
            model = config.property("yandex.model").getString()
        )
    }
}