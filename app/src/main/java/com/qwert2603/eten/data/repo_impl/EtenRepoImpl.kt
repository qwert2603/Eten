package com.qwert2603.eten.data.repo_impl

import com.qwert2603.eten.data.db.mapper.*
import com.qwert2603.eten.di.DI
import com.qwert2603.eten.domain.model.*
import com.qwert2603.eten.domain.repo.EtenRepo
import com.qwert2603.eten.util.Catch
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlin.coroutines.EmptyCoroutineContext

// todo: DI.
object EtenRepoImpl : EtenRepo {

    private val etenDao = DI.db.etenDao()

    private val etenState: Flow<EtenState> = etenDao.observeUpdates()
        .map { etenDao.getEtenTables().toEtenState() }
        .shareIn(
            scope = CoroutineScope(EmptyCoroutineContext),
            started = SharingStarted.Eagerly,
            replay = 1,
        )

    override fun productsUpdates(): Flow<ProductsUpdate> = etenState
        .map { state ->
            ProductsUpdate(
                products = state.products.values.sortedBy { it.name },
                removableProductsUuids = state.removableProductsUuids,
            )
        }

    override suspend fun getProduct(uuid: String): Product? = etenState.first().products[uuid]

    override suspend fun saveProduct(product: Product) {
        etenDao.saveProduct(product.toProductTable())
    }

    override suspend fun removeProduct(uuid: String) {
        val removed = etenDao.removeProductWithCheck(uuid)
        if (!removed) Catch.log("Product was NOT removed!")
    }

    override fun dishesUpdates(): Flow<DishesUpdate> = etenState
        .map { state ->
            DishesUpdate(
                dishes = state.dishes.values.sortedByDescending { it.time },
                removableDishesUuids = state.removableDishesUuids,
            )
        }

    override suspend fun getDish(uuid: String): Dish? = etenState.first().dishes[uuid]

    override suspend fun saveDish(dish: Dish) {
        etenDao.saveDish(
            dishTable = dish.toDishTable(),
            mealPartTables = dish.partsList.map { it.toMealPartTable(dish.uuid) }
        )
    }

    override suspend fun removeDish(uuid: String) {
        val removed = etenDao.removeDishWithParts(uuid)
        if (!removed) Catch.log("Dish was NOT removed!")
    }

    override fun mealsUpdates(): Flow<List<Meal>> = etenState
        .map { state -> state.meals.values.sortedByDescending { it.time } }

    override suspend fun getMeal(uuid: String): Meal? = etenState.first().meals[uuid]

    override suspend fun saveMeal(meal: Meal) {
        etenDao.saveMeal(
            mealTable = meal.toMealTable(),
            mealPartTables = meal.partsList.map { it.toMealPartTable(meal.uuid) }
        )
    }

    override suspend fun removeMeal(uuid: String) {
        etenDao.removeMealWithParts(uuid)
    }
}