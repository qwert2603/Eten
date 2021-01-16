package com.qwert2603.eten.data.db.mapper

import com.qwert2603.eten.data.db.table.MealPartTable
import com.qwert2603.eten.domain.model.*

fun WeightedMealPart.toMealPartTable(containerId: String) = MealPartTable(
    containerId = containerId,
    uuid = uuid,
    weight = weight,
    productUuid = (this as? WeightedProduct)?.product?.uuid,
    dishUuid = (this as? WeightedDish)?.dish?.uuid,
)

fun MealPartTable.toWeightedMealPart(mealPart: MealPart): WeightedMealPart = when (mealPart) {
    is Product -> WeightedProduct(
        uuid = uuid,
        product = mealPart,
        weight = weight
    )
    is Dish -> WeightedDish(
        uuid = uuid,
        dish = mealPart,
        weight = weight
    )
}