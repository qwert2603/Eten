package com.qwert2603.eten.data.db.mapper

import com.qwert2603.eten.data.db.table.MealPartTable
import com.qwert2603.eten.domain.model.Dish
import com.qwert2603.eten.domain.model.MealPart
import com.qwert2603.eten.domain.model.Product
import com.qwert2603.eten.domain.model.WeightedMealPart

fun WeightedMealPart.toMealPartTable(containerId: String) = MealPartTable(
    containerId = containerId,
    uuid = uuid,
    weight = weight,
    productUuid = (mealPart as? Product)?.uuid,
    dishUuid = (mealPart as? Dish)?.uuid,
)

fun MealPartTable.toWeightedMealPart(mealPart: MealPart) = WeightedMealPart(
    uuid = uuid,
    weight = weight,
    mealPart = mealPart,
)