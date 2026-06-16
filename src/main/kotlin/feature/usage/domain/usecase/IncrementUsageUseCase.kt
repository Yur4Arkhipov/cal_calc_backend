package com.jacqulin.feature.usage.domain.usecase

import com.jacqulin.feature.usage.domain.repository.UsageRepository

class IncrementUsageUseCase(
    private val repository: UsageRepository
) {
    suspend operator fun invoke(deviceId: String) {
        repository.incrementTodayCount(deviceId)
    }
}