package com.jacqulin.plugins

import com.jacqulin.client.ai.AiUnavailableException
import com.jacqulin.client.ai.InvalidAiResponseException
import com.jacqulin.dto.ErrorResponse
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.response.respond

fun Application.configureStatusPages() {
    install(StatusPages) {
        exception<AiUnavailableException> { call, cause ->
            call.application.environment.log.error("AI service unavailable", cause)
            call.respond(
                HttpStatusCode.BadGateway,
                ErrorResponse(message = "AI service unavailable"
                )
            )
        }

        exception<InvalidAiResponseException> { call, cause ->
            call.application.environment.log.error("Invalid AI response", cause)
            call.respond(
                HttpStatusCode.InternalServerError,
                ErrorResponse(message = "Invalid AI response")
            )
        }

        exception<Throwable> { call, cause ->
            call.application.environment.log.error("Unhandled exception", cause)
            call.respond(
                HttpStatusCode.InternalServerError,
                ErrorResponse(message = "Internal server error")
            )
        }
    }
}