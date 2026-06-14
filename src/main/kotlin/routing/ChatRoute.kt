package com.jacqulin.routing

import com.jacqulin.domain.Nutrition
import com.jacqulin.feature.chat.domain.usecase.SendMessageUseCase
import com.jacqulin.feature.chat.presentation.request.ChatRequest
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json
import org.koin.ktor.ext.inject

fun Route.chatRoutes() {
    val sendMessageUseCase by inject<SendMessageUseCase>()

    post("/chat") {
        val request = call.receive<ChatRequest>()
        val answer = sendMessageUseCase(request.message)
        val nutrition = Json.decodeFromString<Nutrition>(answer)

        call.respond(nutrition)
    }
}