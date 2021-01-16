package com.qwert2603.eten.data.db.mapper

import com.qwert2603.eten.data.db.table.RawCaloriesTable
import com.qwert2603.eten.domain.model.RawCalories

fun RawCalories.toRawCaloriesTable(containerId: String) = RawCaloriesTable(
    containerId = containerId,
    uuid = uuid,
    name = name,
    calories = calories
)

fun RawCaloriesTable.toCalories() = RawCalories(
    uuid = uuid,
    name = name,
    calories = calories
)