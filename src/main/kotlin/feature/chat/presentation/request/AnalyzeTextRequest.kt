package com.jacqulin.feature.chat.presentation.request

import kotlinx.serialization.Serializable

@Serializable
data class AnalyzeTextRequest(
    val message: String
)