package com.jacqulin.feature.usage.data.mapper

import com.jacqulin.feature.usage.config.SubscriptionType
import com.jacqulin.feature.usage.data.db.UserTable
import com.jacqulin.feature.usage.domain.model.User
import org.jetbrains.exposed.v1.core.ResultRow

fun ResultRow.toUser(): User {
    return User(
        id = this[UserTable.id],
        deviceId = this[UserTable.deviceId],
        subscriptionType = SubscriptionType.valueOf(
            this[UserTable.subscriptionType]
        )
    )
}