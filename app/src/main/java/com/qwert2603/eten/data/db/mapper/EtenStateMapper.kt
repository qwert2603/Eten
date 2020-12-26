package com.qwert2603.eten.data.db.mapper

import com.qwert2603.eten.data.db.EtenState
import com.qwert2603.eten.data.db.result.EtenTables
import com.qwert2603.eten.data.db.table.MealPartTable
import com.qwert2603.eten.domain.model.Dish
import com.qwert2603.eten.domain.model.MealPart
import com.qwert2603.eten.domain.model.PartsList
import com.qwert2603.eten.domain.model.WeightedMealPart

private class EtenStateMapper(
    etenTables: EtenTables,
) {
    val products = etenTables.productTables
        .map { it.toProduct() }
        .associateBy { it.uuid }

    val allParts = (etenTables.dishTables.map { it.parts } + etenTables.mealTables.map { it.parts })
        .flatten()
    val usedProducts = allParts.mapNotNullTo(HashSet()) { it.productUuid }
    val usedDishes = allParts.mapNotNullTo(HashSet()) { it.dishUuid }

    // todo: use it for "show/hide delete button"
    val deletableProducts = etenTables.productTables
        .filterNot { it.uuid in usedProducts }
    val deletableDishes = etenTables.dishTables
        .filterNot { it.dishTable.uuid in usedDishes }

    val dishTablesByUuid = etenTables.dishTables
        .associateBy { it.dishTable.uuid }

    val dishes = mutableMapOf<String, Dish>()

    fun MealPartTable.toMealPart(): WeightedMealPart {
        val mealPart: MealPart = when {
            productUuid != null -> products.getValue(productUuid)
            dishUuid != null -> getDish(dishUuid)
            else -> null!!
        }
        return toWeightedMealPart(mealPart)
    }

    fun getDish(uuid: String): Dish = dishes.getOrPut(uuid) {
        val dishWithParts = dishTablesByUuid.getValue(uuid)
        val partsList = PartsList(dishWithParts.parts.map { it.toMealPart() })
        dishWithParts.dishTable.toDish(partsList)
    }

    init {
        etenTables.dishTables.forEach { getDish(it.dishTable.uuid) }
    }

    val meals = etenTables.mealTables
        .map { mealWithParts ->
            val partsList = PartsList(mealWithParts.parts.map { it.toMealPart() })
            mealWithParts.mealTable.toMeal(partsList)
        }
        .associateBy { it.uuid }

    val etenState = EtenState(
        products = products,
        dishes = dishes,
        meals = meals,
    )

}

fun EtenTables.toEtenState(): EtenState = EtenStateMapper(this).etenState
