package com.qwert2603.eten.domain.model

import java.util.*

data class Dish(
    val uuid: String = UUID.randomUUID().toString(),
    val name: String,
    val time: Date,
    val products: List<WeightedProduct>,
) {
    val totalWeight = products.sumByDouble { it.weight }
    val totalCalories = products.sumByDouble { it.calories }
    val calorie = totalCalories / totalWeight
}