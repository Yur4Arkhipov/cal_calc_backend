package com.jacqulin.feature.chat.domain.usecase

import com.jacqulin.feature.chat.domain.repository.NutritionRepository

class AnalyzeTextUseCase(
    private val repository: NutritionRepository
) {
    suspend operator fun invoke(message: String): String {
        return repository.analyzeText(message)
    }
}