package com.qwert2603.eten.data.db.mapper

import com.qwert2603.eten.data.db.result.EtenTables
import com.qwert2603.eten.data.db.table.MealPartTable
import com.qwert2603.eten.domain.model.Dish
import com.qwert2603.eten.domain.model.EtenState
import com.qwert2603.eten.domain.model.MealPart
import com.qwert2603.eten.domain.model.WeightedMealPart

private class EtenStateMapper(
    etenTables: EtenTables,
) {
    private val allParts =
        (etenTables.dishTables.map { it.parts } + etenTables.mealTables.map { it.parts })
            .flatten()
    private val usedProducts = allParts.mapNotNullTo(HashSet()) { it.productUuid }
    private val usedDishes = allParts.mapNotNullTo(HashSet()) { it.dishUuid }

    private val removableProductsUuids = etenTables.productTables
        .filterNot { it.uuid in usedProducts }
        .mapTo(HashSet()) { it.uuid }
    private val removableDishesUuids = etenTables.dishTables
        .filterNot { it.dishTable.uuid in usedDishes }
        .mapTo(HashSet()) { it.dishTable.uuid }

    private val products = etenTables.productTables
        .map { it.toProduct() }
        .associateBy { it.uuid }

    private val dishTablesByUuid = etenTables.dishTables
        .associateBy { it.dishTable.uuid }

    private val dishes = mutableMapOf<String, Dish>()

    private fun MealPartTable.toWeightedMealPart(): WeightedMealPart {
        val mealPart: MealPart = when {
            productUuid != null -> products.getValue(productUuid)
            dishUuid != null -> getDish(dishUuid)
            else -> null!!
        }
        return toWeightedMealPart(mealPart)
    }

    private fun getDish(uuid: String): Dish = dishes.getOrPut(uuid) {
        val dishWithParts = dishTablesByUuid.getValue(uuid)
        val weightedMealPartsList = dishWithParts.parts.map { it.toWeightedMealPart() }
        dishWithParts.dishTable.toDish(weightedMealPartsList)
    }

    init {
        etenTables.dishTables.forEach { getDish(it.dishTable.uuid) }
    }

    private val meals = etenTables.mealTables
        .map { mealWithParts ->
            val weightedMealPartsList = mealWithParts.parts.map { it.toWeightedMealPart() }
            val rawCaloriesList = mealWithParts.rawCalories.map { it.toCalories() }
            val partsList = weightedMealPartsList + rawCaloriesList
            mealWithParts.mealTable.toMeal(partsList)
        }
        .associateBy { it.uuid }

    val etenState = EtenState(
        products = products,
        dishes = dishes,
        meals = meals,
        removableProductsUuids = removableProductsUuids,
        removableDishesUuids = removableDishesUuids,
    )

}

fun EtenTables.toEtenState(): EtenState = EtenStateMapper(this).etenState
