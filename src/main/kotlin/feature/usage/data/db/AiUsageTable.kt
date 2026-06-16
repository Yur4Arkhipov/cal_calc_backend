package com.jacqulin.feature.usage.data.db

import org.jetbrains.exposed.v1.core.Table
import org.jetbrains.exposed.v1.javatime.date

object AiUsageTable : Table("ai_usage") {
    val deviceId = varchar("device_id", 255)
    val usageDate = date("usage_date")
    val requestCount = integer("request_count")
    override val primaryKey = PrimaryKey(deviceId, usageDate)
}