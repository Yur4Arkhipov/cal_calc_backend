package com.jacqulin.feature.usage.data.db

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.config.ApplicationConfig
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.SchemaUtils
import org.jetbrains.exposed.v1.jdbc.transactions.transaction

object DatabaseFactory {

    fun init(config: ApplicationConfig) {

        val hikariConfig = HikariConfig().apply {
            jdbcUrl = config.property("database.url").getString()
            driverClassName = config.property("database.driver").getString()
            username = config.property("database.user").getString()
            password = config.property("database.password").getString()
            maximumPoolSize = 10
            isAutoCommit = false
        }

        val dataSource = HikariDataSource(hikariConfig)

        Database.connect(dataSource)

        transaction {
            SchemaUtils.create(AiUsageTable)
        }
    }
}