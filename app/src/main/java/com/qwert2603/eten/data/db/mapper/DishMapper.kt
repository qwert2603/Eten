package com.qwert2603.eten.data.db.mapper

import com.qwert2603.eten.data.db.table.DishTable
import com.qwert2603.eten.domain.model.Dish
import com.qwert2603.eten.domain.model.PartsList

fun Dish.toDishTable() = DishTable(
    uuid = uuid,
    name = name,
    time = time,
)

fun DishTable.toDish(partsList: PartsList) = Dish(
    uuid = uuid,
    name = name,
    time = time,
    partsList = partsList,
)
