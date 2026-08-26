package com.jacqulin.feature.usage.domain.model

import com.jacqulin.feature.usage.config.SubscriptionType

data class User(
    val id: Int,
    val deviceId: String,
    val subscriptionType: SubscriptionType
)