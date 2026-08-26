package com.jacqulin.feature.usage.data.db

import org.jetbrains.exposed.v1.core.Table
import org.jetbrains.exposed.v1.javatime.date

object AiUsageTable : Table("ai_usage") {
    val userId = reference(
        name = "user_id",
        refColumn = UserTable.id
    )
    val usageDate = date("usage_date")
    val requestCount = integer("request_count")
    override val primaryKey = PrimaryKey(userId, usageDate)
}