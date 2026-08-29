package com.jacqulin.feature.chat.presentation.request

import com.jacqulin.domain.Nutrition
import kotlinx.serialization.Serializable

@Serializable
data class RefineMealRequest(
    val currentMeal: Nutrition,
    val userPrompt: String
)