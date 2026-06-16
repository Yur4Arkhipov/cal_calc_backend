package com.jacqulin.feature.usage.data.repository

import com.jacqulin.feature.usage.data.db.AiUsageTable
import com.jacqulin.feature.usage.data.db.dbQuery
import com.jacqulin.feature.usage.domain.repository.UsageRepository
import org.jetbrains.exposed.v1.core.and
import org.jetbrains.exposed.v1.jdbc.insert
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.jetbrains.exposed.v1.jdbc.update
import java.time.LocalDate

class UsageRepositoryImpl : UsageRepository {

    override suspend fun getTodayCount(
        deviceId: String
    ): Int = dbQuery {
        AiUsageTable
            .selectAll()
            .where {
                (AiUsageTable.deviceId eq deviceId) and
                (AiUsageTable.usageDate eq LocalDate.now())
            }
            .singleOrNull()
            ?.get(AiUsageTable.requestCount)
            ?: 0
    }

    override suspend fun incrementTodayCount(
        deviceId: String
    ) {
        dbQuery {
            val today = LocalDate.now()
            val existing = AiUsageTable
                .selectAll()
                .where {
                    (AiUsageTable.deviceId eq deviceId) and
                            (AiUsageTable.usageDate eq today)
                }
                .singleOrNull()
            if (existing == null) {
                AiUsageTable.insert {
                    it[AiUsageTable.deviceId] = deviceId
                    it[usageDate] = today
                    it[requestCount] = 1
                }
            } else {
                AiUsageTable.update({
                    (AiUsageTable.deviceId eq deviceId) and
                            (AiUsageTable.usageDate eq today)
                }) {
                    it[requestCount] =
                        existing[AiUsageTable.requestCount] + 1
                }
            }
        }
    }
}