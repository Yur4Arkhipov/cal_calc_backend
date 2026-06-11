package com.jacqulin

import com.jacqulin.dto.ChatRequest
import com.jacqulin.dto.ChatResponse
import io.ktor.server.application.*
import io.ktor.server.request.receive
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello, World!")
        }
        post("/chat") {
            val request = call.receive<ChatRequest>()
            call.respond(
                ChatResponse(
                    response = "Сервер получил: ${request.message}"
                )
            )
        }
    }
}