package com.qwert2603.eten.domain.model

import java.util.*

data class Meal(
    val uuid: String = UUID.randomUUID().toString(),
    val time: Date,
    val parts: List<MealPart>,
) {
    val totalWeight = parts.sumByDouble { it.weight }
    val totalCalories = parts.sumByDouble { it.totalCalories }
}

