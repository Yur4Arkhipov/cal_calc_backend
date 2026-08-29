package com.jacqulin.feature.usage.data.db

import org.jetbrains.exposed.v1.core.Table

object UserTable : Table("users") {

    val id = integer("id").autoIncrement()
    val deviceId = varchar("device_id", 255).uniqueIndex()
    val subscriptionType = varchar("subscription_type", 20)

    override val primaryKey = PrimaryKey(id)
}