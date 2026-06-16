package com.jacqulin.feature.usage.domain.usecase

import com.jacqulin.feature.usage.domain.repository.UsageRepository

class CheckUsageLimitUseCase(
    private val repository: UsageRepository
) {
    suspend operator fun invoke(deviceId: String): Boolean {
        val count = repository.getTodayCount(deviceId)
        return count < 20
    }
}