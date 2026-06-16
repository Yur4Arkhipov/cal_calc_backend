package com.jacqulin.feature.chat.presentation.response

import com.jacqulin.domain.Nutrition
import kotlinx.serialization.Serializable

@Serializable
data class AnalyzeResponse(
    val response: Nutrition
)