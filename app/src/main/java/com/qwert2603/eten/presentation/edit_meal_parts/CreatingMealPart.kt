package com.qwert2603.eten.presentation.edit_meal_parts

import com.qwert2603.eten.domain.model.*

sealed class CreatingMealPart {
    abstract val uuid: String
    abstract val calories: Double
    abstract fun isValid(): Boolean
    abstract fun toVolumedMealPart(): VolumedMealPart
}

data class CreatingCalories(
    override val uuid: String,
    val name: String,
    val caloriesInput: Int,
) : CreatingMealPart() {
    override val calories = caloriesInput.toDouble()
    override fun isValid() = caloriesInput > 0
    override fun toVolumedMealPart() = RawCalories(
        uuid = uuid,
        name = name.trim().takeIf { it.isNotEmpty() },
        calories = calories
    )
}

data class CreatingWeightedProduct(
    override val uuid: String,
    val product: Product?,
    val weight: Int,
) : CreatingMealPart() {
    override val calories = (product?.calorie ?: 0.0) * weight
    override fun isValid() = product != null && weight > 0
    override fun toVolumedMealPart() = WeightedProduct(uuid, product!!, weight.toDouble())
}

data class CreatingWeightedDish(
    override val uuid: String,
    val dish: Dish?,
    val weight: Int,
) : CreatingMealPart() {
    override val calories = (dish?.calorie ?: 0.0) * weight
    override fun isValid() = dish != null && weight > 0
    override fun toVolumedMealPart() = WeightedDish(uuid, dish!!, weight.toDouble())
}

fun VolumedMealPart.toCreatingMealPart(): CreatingMealPart = when (this) {
    is RawCalories -> CreatingCalories(uuid, name ?: "", calories.toInt())
    is WeightedProduct -> CreatingWeightedProduct(uuid, product, weight.toInt())
    is WeightedDish -> CreatingWeightedDish(uuid, dish, weight.toInt())
}

val CreatingMealPart.weight: Int?
    get() = when (this) {
        is CreatingCalories -> null
        is CreatingWeightedProduct -> weight
        is CreatingWeightedDish -> weight
    }