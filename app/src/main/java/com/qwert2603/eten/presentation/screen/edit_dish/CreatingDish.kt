package com.qwert2603.eten.presentation.screen.edit_dish

import com.qwert2603.eten.domain.model.Dish
import com.qwert2603.eten.domain.model.PartsList
import com.qwert2603.eten.domain.model.Product
import com.qwert2603.eten.domain.model.WeightedMealPart
import com.qwert2603.eten.util.randomUUID
import com.qwert2603.eten.util.timeNow
import java.util.*

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
    override fun toWeightedMealPart() = WeightedMealPart(product!!, weight.toDouble())
}

data class CreatingWeightedDish(
    override val uuid: String,
    val dish: Dish?,
    val weight: Int,
) : CreatingMealPart() {
    override val calories = (dish?.calorie ?: 0.0) * weight
    override fun isValid() = dish != null && weight > 0
    override fun toWeightedMealPart() = WeightedMealPart(dish!!, weight.toDouble())
}

fun WeightedMealPart.toCreatingMealPart() = when (mealPart) {
    is Product -> CreatingWeightedProduct(randomUUID(), mealPart, weight.toInt())
    is Dish -> CreatingWeightedDish(randomUUID(), mealPart, weight.toInt())
}

val CreatingMealPart.weight: Int
    get() = when (this) {
        is CreatingWeightedProduct -> weight
        is CreatingWeightedDish -> weight
    }

data class CreatingDish(
    val uuid: String,
    val name: String,
    val time: Date?,
    val parts: List<CreatingMealPart>,

    ) {
    fun isValid() = name.isNotBlank() && parts.isNotEmpty() && parts.all { it.isValid() }

    fun toDish() = Dish(
        uuid = uuid,
        name = name,
        time = time ?: timeNow(),
        partsList = PartsList(parts.map { it.toWeightedMealPart() }),
    )

    fun totalWeight() = parts.sumBy { it.weight }
    fun totalCalories() = parts.sumByDouble { it.calories }
}