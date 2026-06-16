package com.jacqulin.feature.usage.domain.repository

interface UsageRepository {
    suspend fun getTodayCount(deviceId: String): Int
    suspend fun incrementTodayCount(deviceId: String)
}