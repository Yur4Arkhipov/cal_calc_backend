package com.jacqulin.feature.usage.domain.usecase

import com.jacqulin.feature.usage.domain.repository.UsageRepository
import com.jacqulin.feature.usage.domain.repository.UserRepository

class IncrementUsageUseCase(
    private val userRepository: UserRepository,
    private val usageRepository: UsageRepository
) {
    suspend operator fun invoke(deviceId: String) {
        val user = userRepository.getOrCreate(deviceId)
        usageRepository.incrementTodayCount(user.id)
    }
}