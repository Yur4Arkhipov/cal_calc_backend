package com.jacqulin.feature.chat.domain.usecase

import com.jacqulin.feature.chat.domain.repository.NutritionRepository

class AnalyzeImageUseCase(
    private val repository: NutritionRepository
) {
    suspend operator fun invoke(strBase64: String): String {
        return repository.analyzeImage(strBase64)
    }
}