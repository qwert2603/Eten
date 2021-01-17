package com.qwert2603.eten.domain.model

import kotlinx.datetime.LocalDateTime

sealed class MealPart

// ProductByWeight
data class Product(
    val uuid: String,
    val name: String,
    val calorie: Double, // per 1g
) : MealPart() {
    val caloriePer100g by lazy { calorie * 100.0 }
}

//data class ProductByCount(
//    val uuid: String,
//    val name: String,
//    val calorie: Double,
//) : MealPart()

data class Dish(
    val uuid: String,
    val name: String,
    val time: LocalDateTime,
    val partsList: List<WeightedMealPart>,
) : MealPart() {
    val calorie = partsList.calories / partsList.weight

    val caloriePer100g by lazy { calorie * 100.0 }
}

sealed class VolumedMealPart {
    abstract val uuid: String
    abstract val calories: Double
}

data class RawCalories(
    override val uuid: String,
    val name: String?,
    override val calories: Double,
) : VolumedMealPart()

sealed class WeightedMealPart : VolumedMealPart() {
    abstract val weight: Double // in grams.
}

data class WeightedProduct(
    override val uuid: String,
    val product: Product,
    override val weight: Double,
) : WeightedMealPart() {
    override val calories = product.calorie * weight
}

data class WeightedDish(
    override val uuid: String,
    val dish: Dish,
    override val weight: Double,
) : WeightedMealPart() {
    override val calories = dish.calorie * weight
}

//data class CountedProduct(
//    val productByCount: ProductByCount,
//    val count: Int,
//) : VolumedMealPart() {
//    override val calories = productByCount.calorie * count
//}

data class Meal(
    val uuid: String,
    val name: String?,
    val time: LocalDateTime,
    val partsList: List<VolumedMealPart>,
)

val VolumedMealPart.name: String?
    get() = when (this) {
        is RawCalories -> name
        is WeightedMealPart -> name
    }

val WeightedMealPart.name: String
    get() = when (this) {
        is WeightedProduct -> product.name
        is WeightedDish -> dish.name
    }

val VolumedMealPart.weight: Double? get() = (this as? WeightedMealPart)?.weight

val List<VolumedMealPart>.calories: Double get() = sumByDouble { it.calories }
val List<VolumedMealPart>.weight: Double get() = sumByDouble { it.weight ?: 0.0 }
