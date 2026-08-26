package com.jacqulin.feature.usage.domain.usecase

import com.jacqulin.client.ai.DailyLimitExceededException
import com.jacqulin.feature.usage.config.AiUsageConfig
import com.jacqulin.feature.usage.config.SubscriptionType
import com.jacqulin.feature.usage.domain.repository.UsageRepository
import com.jacqulin.feature.usage.domain.repository.UserRepository

class CheckUsageLimitUseCase(
    private val usageRepository: UsageRepository,
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(deviceId: String) {
        val user = userRepository.getOrCreate(deviceId)
        if (user.subscriptionType == SubscriptionType.PREMIUM) {
            return
        }
        val count = usageRepository.getTodayCount(user.id)
        if (count >= AiUsageConfig.DAILY_FREE_LIMIT) {
            throw DailyLimitExceededException()
        }
    }
}