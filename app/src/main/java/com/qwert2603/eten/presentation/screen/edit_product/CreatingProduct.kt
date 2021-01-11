package com.qwert2603.eten.presentation.screen.edit_product

import com.qwert2603.eten.domain.model.Product

data class CreatingProduct(
    val uuid: String,
    val name: String,
    val caloriesPer100g: Int,
) {
    fun isValid() = name.isNotBlank() && caloriesPer100g >= 0.0

    fun toProduct() = Product(
        uuid = uuid,
        name = name.trim(),
        calorie = caloriesPer100g.toDouble() / 100.0
    )
}