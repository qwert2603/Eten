package com.qwert2603.eten.domain.repo

import com.qwert2603.eten.domain.model.Dish
import com.qwert2603.eten.domain.model.Meal
import com.qwert2603.eten.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface EtenRepo {
    fun productsUpdates(): Flow<List<Product>>
    suspend fun saveProduct(product: Product)
    suspend fun removeProduct(uuid: String)

    fun dishesUpdates(): Flow<List<Dish>>
    suspend fun saveDish(dish: Dish)
    suspend fun removeDish(uuid: String)

    fun mealsUpdates(): Flow<List<Meal>>
    suspend fun saveMeal(meal: Meal)
    suspend fun removeMeal(uuid: String)
}