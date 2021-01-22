package com.qwert2603.eten.data.repo_impl

import com.qwert2603.eten.data.db.mapper.*
import com.qwert2603.eten.di.DI
import com.qwert2603.eten.domain.model.*
import com.qwert2603.eten.domain.repo.EtenRepo
import com.qwert2603.eten.util.Catch
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import timber.log.Timber
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

// todo: DI.
object EtenRepoImpl : EtenRepo {

    private val etenDao = DI.db.etenDao()

    private val dbUpdated = Channel<Unit>(Channel.CONFLATED).also { it.offer(Unit) }
    fun onDbUpdated() {
        dbUpdated.offer(Unit)
    }

    @OptIn(ExperimentalTime::class)
    private val etenState: Flow<EtenState> = dbUpdated
        .receiveAsFlow()
        .map {
            measureTimedValue { etenDao.getEtenTables() }
                .also { Timber.d("getEtenTables ${it.duration.inMilliseconds}") }
                .value
                .toEtenState()
        }
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
        onDbUpdated()
    }

    override suspend fun removeProduct(uuid: String) {
        val removed = etenDao.removeProductWithCheck(uuid)
        if (!removed) Catch.log("Product was NOT removed!")
        onDbUpdated()
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
            mealPartTables = dish.partsList.map { it.toMealPartTable(dish.uuid) },
            rawCaloriesTables = emptyList(),
        )
        onDbUpdated()
    }

    override suspend fun removeDish(uuid: String) {
        val removed = etenDao.removeDishWithParts(uuid)
        if (!removed) Catch.log("Dish was NOT removed!")
        onDbUpdated()
    }

    override fun mealsUpdates(): Flow<List<Meal>> = etenState
        .map { state -> state.meals.values.sortedByDescending { it.time } }

    override suspend fun getMeal(uuid: String): Meal? = etenState.first().meals[uuid]

    override suspend fun saveMeal(meal: Meal) {
        etenDao.saveMeal(
            mealTable = meal.toMealTable(),
            mealPartTables = meal.partsList
                .filterIsInstance<WeightedMealPart>()
                .map { it.toMealPartTable(meal.uuid) },
            rawCaloriesTables = meal.partsList
                .filterIsInstance<RawCalories>()
                .map { it.toRawCaloriesTable(meal.uuid) },
        )
        onDbUpdated()
    }

    override suspend fun removeMeal(uuid: String) {
        etenDao.removeMealWithParts(uuid)
        onDbUpdated()
    }
}