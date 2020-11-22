package com.qwert2603.eten.domain.model

sealed class MealPart {
    abstract val weight: Double
    abstract val calorie: Double

    val totalCalories get() = weight * calorie
}

data class MealProduct(
    val weightedProduct: WeightedProduct,
) : MealPart() {
    override val weight = weightedProduct.weight
    override val calorie = weightedProduct.product.calorie
}

data class MealDish(
    val dish: Dish,
    override val weight: Double,
) : MealPart() {
    override val calorie = dish.calorie
}