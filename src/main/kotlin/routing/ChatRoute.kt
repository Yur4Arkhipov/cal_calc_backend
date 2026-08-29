package com.jacqulin.routing

import com.jacqulin.client.ai.MissingDeviceIdException
import com.jacqulin.domain.Nutrition
import com.jacqulin.feature.chat.domain.usecase.AnalyzeImageUseCase
import com.jacqulin.feature.chat.domain.usecase.AnalyzeTextUseCase
import com.jacqulin.feature.chat.domain.usecase.RefineMealUseCase
import com.jacqulin.feature.chat.presentation.request.AnalyzeImageRequest
import com.jacqulin.feature.chat.presentation.request.AnalyzeTextRequest
import com.jacqulin.feature.chat.presentation.request.RefineMealRequest
import com.jacqulin.feature.usage.domain.usecase.CheckUsageLimitUseCase
import com.jacqulin.feature.usage.domain.usecase.IncrementUsageUseCase
import io.ktor.http.ContentType
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json
import org.koin.ktor.ext.inject

fun Route.nutritionRoutes() {
    val analyzeTextUseCase by inject<AnalyzeTextUseCase>()
    val analyzeImageUseCase by inject<AnalyzeImageUseCase>()
    val refineMealUseCase by inject<RefineMealUseCase>()
    val checkUsageLimitUseCase by inject<CheckUsageLimitUseCase>()
    val incrementUsageUseCase by inject<IncrementUsageUseCase>()

    post("/analyze-text") {
        application.environment.log.info("ANALYZE-TEXT REQUEST RECEIVED")
        val deviceId = call.request.headers["X-Device-Id"]
            ?: throw MissingDeviceIdException()

        checkUsageLimitUseCase(deviceId)

        val request = call.receive<AnalyzeTextRequest>()
        val answer = analyzeTextUseCase(request.message)
        val nutrition = Json.decodeFromString<Nutrition>(answer)

        incrementUsageUseCase(deviceId)

        call.respond(nutrition)
    }

    post("/analyze-image") {
        val deviceId = call.request.headers["X-Device-Id"]
            ?: throw MissingDeviceIdException()

        checkUsageLimitUseCase(deviceId)

        val request = call.receive<AnalyzeImageRequest>()
        val answer = analyzeImageUseCase(request.imgBase64)
        val nutrition = Json.decodeFromString<Nutrition>(answer)

        incrementUsageUseCase(deviceId)

        call.respond(nutrition)
    }

    post("refine") {
        val deviceId = call.request.headers["X-Device-Id"]
            ?: throw MissingDeviceIdException()

        checkUsageLimitUseCase(deviceId)

        val request = call.receive<RefineMealRequest>()
        val answer = refineMealUseCase(
            request.currentMeal,
            request.userPrompt
        )

        incrementUsageUseCase(deviceId)

        call.respondText(
            text = answer,
            contentType = ContentType.Application.Json
        )
    }
}