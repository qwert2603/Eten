package com.qwert2603.eten.domain.model

import java.util.*

sealed class MealPart {
    abstract val name: String
    abstract val calorie: Double // per 1g
    val caloriePer100g by lazy { calorie * 100.0 }
}

data class WeightedMealPart(
    val mealPart: MealPart,
    val weight: Double, // in grams.
) {
    init {
        check(weight > 0.0)
    }

    val calories get() = mealPart.calorie * weight
}

data class Product(
    val uuid: String = UUID.randomUUID().toString(),
    override val name: String,
    override val calorie: Double,
) : MealPart() {
    init {
        check(name.isNotBlank())
        check(calorie >= 0.0)
    }
}

data class Dish(
    val uuid: String = UUID.randomUUID().toString(),
    override val name: String,
    val time: Date,
    val partsList: PartsList,
) : MealPart() {
    init {
        check(name.isNotBlank())
    }

    override val calorie = partsList.calories / partsList.weight
}

class PartsList(vararg parts: WeightedMealPart) : List<WeightedMealPart> by listOf(*parts) {
    val weight = sumByDouble { it.weight }
    val calories = sumByDouble { it.calories }

    init {
        check(parts.isNotEmpty())
    }
}

data class Meal(
    val uuid: String = UUID.randomUUID().toString(),
    val time: Date,
    val partsList: PartsList,
)
