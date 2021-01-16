package com.qwert2603.eten.data.db.mapper

import com.qwert2603.eten.data.db.table.MealTable
import com.qwert2603.eten.domain.model.Meal
import com.qwert2603.eten.domain.model.VolumedMealPart

fun Meal.toMealTable() = MealTable(
    uuid = uuid,
    name = name,
    time = time,
)

fun MealTable.toMeal(partsList: List<VolumedMealPart>) = Meal(
    uuid = uuid,
    name = name,
    time = time,
    partsList = partsList,
)