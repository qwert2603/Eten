package com.qwert2603.eten.presentation.edit_meal_parts

import com.qwert2603.eten.domain.model.Dish
import com.qwert2603.eten.domain.model.Product
import com.qwert2603.eten.domain.model.WeightedMealPart

sealed class CreatingMealPart {
    abstract val uuid: String
    abstract val calories: Double
    abstract fun isValid(): Boolean
    abstract fun toWeightedMealPart(): WeightedMealPart
}

data class CreatingWeightedProduct(
    override val uuid: String,
    val product: Product?,
    val weight: Int,
) : CreatingMealPart() {
    override val calories = (product?.calorie ?: 0.0) * weight
    override fun isValid() = product != null && weight > 0
    override fun toWeightedMealPart() = WeightedMealPart(uuid, product!!, weight.toDouble())
}

data class CreatingWeightedDish(
    override val uuid: String,
    val dish: Dish?,
    val weight: Int,
) : CreatingMealPart() {
    override val calories = (dish?.calorie ?: 0.0) * weight
    override fun isValid() = dish != null && weight > 0
    override fun toWeightedMealPart() = WeightedMealPart(uuid, dish!!, weight.toDouble())
}

fun WeightedMealPart.toCreatingMealPart(): CreatingMealPart = when (mealPart) {
    is Product -> CreatingWeightedProduct(uuid, mealPart, weight.toInt())
    is Dish -> CreatingWeightedDish(uuid, mealPart, weight.toInt())
}

val CreatingMealPart.weight: Int
    get() = when (this) {
        is CreatingWeightedProduct -> weight
        is CreatingWeightedDish -> weight
    }