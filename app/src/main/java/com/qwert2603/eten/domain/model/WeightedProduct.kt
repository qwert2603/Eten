package com.qwert2603.eten.domain.model

data class WeightedProduct(
    val product: Product,
    val weight: Double,
) {
    val calories = product.calorie * weight
}