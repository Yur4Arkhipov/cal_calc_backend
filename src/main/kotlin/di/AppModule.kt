package com.jacqulin.di

import com.jacqulin.client.domain.AiClient
import com.jacqulin.client.ai.YandexAiClient
import com.jacqulin.config.AiConfig
import com.jacqulin.feature.chat.data.repository.NutritionRepositoryImpl
import com.jacqulin.feature.chat.domain.repository.NutritionRepository
import com.jacqulin.feature.chat.domain.usecase.AnalyzeImageUseCase
import com.jacqulin.feature.chat.domain.usecase.AnalyzeTextUseCase
import com.jacqulin.feature.chat.domain.usecase.RefineMealUseCase
import com.jacqulin.feature.usage.data.repository.UsageRepositoryImpl
import com.jacqulin.feature.usage.data.repository.UserRepositoryImpl
import com.jacqulin.feature.usage.domain.repository.UsageRepository
import com.jacqulin.feature.usage.domain.repository.UserRepository
import com.jacqulin.feature.usage.domain.usecase.CheckUsageLimitUseCase
import com.jacqulin.feature.usage.domain.usecase.IncrementUsageUseCase
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpTimeout
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
            install(HttpTimeout) {
                requestTimeoutMillis = 60_000
                connectTimeoutMillis = 30_000
                socketTimeoutMillis = 60_000
            }
        }
    }

    single<AiClient> {
        YandexAiClient(
            client = get(),
            config = get()
        )
    }

    single<NutritionRepository> {
        NutritionRepositoryImpl(
            aiClient = get()
        )
    }

    single<UsageRepository> {
        UsageRepositoryImpl()
    }

    single<UserRepository> {
        UserRepositoryImpl()
    }

    factory {
        AnalyzeTextUseCase(
            repository = get()
        )
    }

    factory {
        AnalyzeImageUseCase(
            repository = get()
        )
    }

    factory {
        RefineMealUseCase(
            repository = get()
        )
    }

    factory {
        CheckUsageLimitUseCase(
            usageRepository = get(),
            userRepository = get()
        )
    }

    factory {
        IncrementUsageUseCase(
            usageRepository = get(),
            userRepository = get()
        )
    }

    single {
        AiConfig(
            apiKey = config.property("yandex.apiKey").getString(),
            model = config.property("yandex.model").getString()
        )
    }
}