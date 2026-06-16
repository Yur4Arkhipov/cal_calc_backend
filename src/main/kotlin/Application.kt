package com.jacqulin

import com.jacqulin.di.appModule
import com.jacqulin.plugins.configureRouting
import com.jacqulin.plugins.configureSerialization
import com.jacqulin.plugins.configureStatusPages
import io.ktor.server.application.Application
import io.ktor.server.application.install
import org.koin.ktor.plugin.Koin

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {

    install(Koin) {
        modules(
            appModule(environment.config)
        )
    }

    configureRouting()
    configureSerialization()
    configureStatusPages()
}
