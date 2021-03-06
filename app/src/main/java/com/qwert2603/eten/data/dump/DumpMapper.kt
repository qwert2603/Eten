package com.qwert2603.eten.data.dump

import com.qwert2603.eten.data.db.convert.LocalDateTimeConverter
import com.qwert2603.eten.data.db.result.Dump
import com.qwert2603.eten.data.db.table.*

private val localDateTimeConverter = LocalDateTimeConverter()

fun Dump.toSerializableDump() = SerializableDump(
    products = productTables.map {
        SerializableProduct(
            uuid = it.uuid,
            name = it.name,
            calorie = it.calorie,
        )
    },
    mealParts = mealPartTables.map {
        SerializableMealPart(
            containerId = it.containerId,
            uuid = it.uuid,
            weight = it.weight,
            productUuid = it.productUuid,
            dishUuid = it.dishUuid,
        )
    },
    rawCalories = rawCaloriesTables.map {
        SerializableRawCalories(
            containerId = it.containerId,
            uuid = it.uuid,
            name = it.name,
            calories = it.calories,
        )
    },
    dishes = dishTables.map {
        SerializableDish(
            uuid = it.uuid,
            name = it.name,
            time = localDateTimeConverter.convert(it.time),
        )
    },
    meals = mealTables.map {
        SerializableMeal(
            uuid = it.uuid,
            name = it.name,
            time = localDateTimeConverter.convert(it.time),
        )
    },
)

fun SerializableDump.toDump() = Dump(
    productTables = products.map {
        ProductTable(
            uuid = it.uuid,
            name = it.name,
            calorie = it.calorie,
        )
    },
    mealPartTables = mealParts.map {
        MealPartTable(
            containerId = it.containerId,
            uuid = it.uuid,
            weight = it.weight,
            productUuid = it.productUuid,
            dishUuid = it.dishUuid,
        )
    },
    rawCaloriesTables = rawCalories.map {
        RawCaloriesTable(
            containerId = it.containerId,
            uuid = it.uuid,
            name = it.name,
            calories = it.calories,
        )
    },
    dishTables = dishes.map {
        DishTable(
            uuid = it.uuid,
            name = it.name,
            time = localDateTimeConverter.convert(it.time),
        )
    },
    mealTables = meals.map {
        MealTable(
            uuid = it.uuid,
            name = it.name,
            time = localDateTimeConverter.convert(it.time),
        )
    },
)