
plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(ktorLibs.plugins.ktor)
    alias(libs.plugins.kotlin.serialization)
}

group = "com.jacqulin"
version = "1.0.0-SNAPSHOT"

application {
    mainClass = "io.ktor.server.netty.EngineMain"
}

kotlin {
    jvmToolchain(21)
}
dependencies {
    // server
    implementation(ktorLibs.server.contentNegotiation)
    implementation(ktorLibs.server.config.yaml)
    implementation(ktorLibs.server.core)
    implementation(ktorLibs.server.netty)

    // client
    implementation(ktorLibs.client.core)
    implementation(ktorLibs.client.cio)
    implementation(ktorLibs.client.contentNegotiation)

    // logback
    implementation(libs.logback.classic)

    // Serialization
    implementation(libs.kotlinx.serialization.json)
    implementation(ktorLibs.serialization.kotlinx.json)

    // Koin
    implementation(libs.koin.ktor)

//    implementation(libs.exposed.core)
//    implementation(libs.exposed.dao)
//    implementation(libs.exposed.sql)
//    implementation(libs.exposed.migration)
//    implementation(libs.exposed.jdbc)
//    implementation(libs.exposed.r2dbc)
//    implementation(libs.h2)
//    implementation(libs.postgresql)
//    implementation(libs.hikari.core)
//    implementation(libs.ktor.server.cors) maybe del
//    testImplementation(libs.kotlin.test.junit)
    testImplementation(kotlin("test"))
    testImplementation(ktorLibs.server.testHost)
}
