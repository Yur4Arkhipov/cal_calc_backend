package com.jacqulin.plugins

import com.jacqulin.routing.nutritionRoutes
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello, World!")
        }
        nutritionRoutes()
    }
}