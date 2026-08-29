package com.jacqulin.feature.chat.domain.usecase

import com.jacqulin.domain.Nutrition
import com.jacqulin.feature.chat.domain.repository.NutritionRepository

class RefineMealUseCase(
    private val repository: NutritionRepository
) {
    suspend operator fun invoke(currentMeal: Nutrition, userPrompt: String): String {
        return repository.refineMeal(currentMeal, userPrompt)
    }
}