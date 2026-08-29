package com.jacqulin.domain

import kotlinx.serialization.Serializable

@Serializable
data class Nutrition(
    val name: String = "",
    val weight: Int,
    val calories: Int,
    val protein: Int,
    val fat: Int,
    val carb: Int,
    val ingredients: List<Ingredient> = emptyList()
)
