package com.qwert2603.eten.domain.model

import java.util.*

data class Meal(
    val uuid: String = UUID.randomUUID().toString(),
    val time: Date,
    val products: List<WeightedProduct>,
) {
    val totalCalories = products.sumByDouble { it.calories }

    data class WeightedProduct(
        val product: Product,
        val weight: Double,
    ) {
        val calories = product.calorie * weight
    }
}

