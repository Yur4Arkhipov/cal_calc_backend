package com.jacqulin.feature.usage.data.repository

import com.jacqulin.feature.usage.config.SubscriptionType
import com.jacqulin.feature.usage.data.db.UserTable
import com.jacqulin.feature.usage.data.db.dbQuery
import com.jacqulin.feature.usage.data.mapper.toUser
import com.jacqulin.feature.usage.domain.model.User
import com.jacqulin.feature.usage.domain.repository.UserRepository
import org.jetbrains.exposed.v1.jdbc.insert
import org.jetbrains.exposed.v1.jdbc.selectAll

class UserRepositoryImpl : UserRepository {

    override suspend fun getByDeviceId(
        deviceId: String
    ): User? = dbQuery {
        UserTable
            .selectAll()
            .where {
                UserTable.deviceId eq deviceId
            }
            .singleOrNull()
            ?.toUser()
    }

    override suspend fun create(
        deviceId: String
    ): User = dbQuery {
        val statement = UserTable.insert {
            it[UserTable.deviceId] = deviceId
            it[subscriptionType] = SubscriptionType.FREE.name
        }

        val id = statement[UserTable.id]

        User(
            id = id,
            deviceId = deviceId,
            subscriptionType = SubscriptionType.FREE
        )
    }

    override suspend fun getOrCreate(
        deviceId: String
    ): User {
        return getByDeviceId(deviceId)
            ?: create(deviceId)
    }
}