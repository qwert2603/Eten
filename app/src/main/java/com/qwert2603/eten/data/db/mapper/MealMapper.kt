package com.qwert2603.eten.data.db.mapper

import com.qwert2603.eten.data.db.table.MealTable
import com.qwert2603.eten.domain.model.Meal
import com.qwert2603.eten.domain.model.PartsList

fun Meal.toMealTable() = MealTable(
    uuid = uuid,
    time = time,
)

fun MealTable.toMeal(partsList: PartsList) = Meal(
    uuid = uuid,
    time = time,
    partsList = partsList,
)