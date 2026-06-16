package com.jacqulin.feature.chat.presentation.request

import kotlinx.serialization.Serializable

@Serializable
data class AnalyzeImageRequest(
    val imgBase64: String
)