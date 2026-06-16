package com.jacqulin.routing

import com.jacqulin.domain.Nutrition
import com.jacqulin.feature.chat.domain.usecase.AnalyzeImageUseCase
import com.jacqulin.feature.chat.domain.usecase.AnalyzeTextUseCase
import com.jacqulin.feature.chat.presentation.request.AnalyzeImageRequest
import com.jacqulin.feature.chat.presentation.request.AnalyzeTextRequest
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json
import org.koin.ktor.ext.inject

fun Route.nutritionRoutes() {
    val analyzeTextUseCase by inject<AnalyzeTextUseCase>()
    val analyzeImageUseCase by inject<AnalyzeImageUseCase>()

    post("/analyze-text") {
        val request = call.receive<AnalyzeTextRequest>()
        val answer = analyzeTextUseCase(request.message)
        val nutrition = Json.decodeFromString<Nutrition>(answer)

        call.respond(nutrition)
    }

    post("/analyze-image") {
        val request = call.receive<AnalyzeImageRequest>()
        val answer = analyzeImageUseCase(request.imgBase64)
        val nutrition = Json.decodeFromString<Nutrition>(answer)

        call.respond(nutrition)
    }
}