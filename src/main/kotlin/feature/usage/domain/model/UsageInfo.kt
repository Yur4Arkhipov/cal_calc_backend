package com.jacqulin.feature.usage.domain.model

import java.time.LocalDate

data class UsageInfo(
    val deviceId: String,
    val date: LocalDate,
    val count: Int
)
