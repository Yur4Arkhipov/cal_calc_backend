package com.jacqulin.feature.usage.domain.repository

interface UsageRepository {
    suspend fun getTodayCount(userId: Int): Int
    suspend fun incrementTodayCount(userId: Int)
}