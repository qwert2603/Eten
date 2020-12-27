package com.qwert2603.eten.domain.repo

import com.qwert2603.eten.domain.model.*
import kotlinx.coroutines.flow.Flow

interface EtenRepo {
    fun productsUpdates(): Flow<ProductsUpdate>
    suspend fun getProduct(uuid: String): Product?
    suspend fun saveProduct(product: Product)
    suspend fun removeProduct(uuid: String)

    fun dishesUpdates(): Flow<DishesUpdate>
    suspend fun getDish(uuid: String): Dish?
    suspend fun saveDish(dish: Dish)
    suspend fun removeDish(uuid: String)

    fun mealsUpdates(): Flow<List<Meal>>
    suspend fun getMeal(uuid: String): Meal?
    suspend fun saveMeal(meal: Meal)
    suspend fun removeMeal(uuid: String)
}