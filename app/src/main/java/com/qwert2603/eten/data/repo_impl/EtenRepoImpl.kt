package com.qwert2603.eten.data.repo_impl

import androidx.room.Room
import com.qwert2603.eten.E
import com.qwert2603.eten.EtenApplication
import com.qwert2603.eten.data.db.EtenDataBase
import com.qwert2603.eten.data.db.EtenState
import com.qwert2603.eten.data.db.mapper.*
import com.qwert2603.eten.domain.model.Dish
import com.qwert2603.eten.domain.model.Meal
import com.qwert2603.eten.domain.model.Product
import com.qwert2603.eten.domain.repo.EtenRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlin.coroutines.EmptyCoroutineContext

// todo: DI.
object EtenRepoImpl : EtenRepo {

    private val db = Room
        .databaseBuilder(EtenApplication.APP, EtenDataBase::class.java, "eten.db")
        .also { if (E.isDebug) it.fallbackToDestructiveMigration() }
        .build()

    private val etenDao = db.etenDao()

    private val etenState: Flow<EtenState> = etenDao.observeUpdates()
        .map { etenDao.getEtenTables().toEtenState() }
        .shareIn(
            scope = CoroutineScope(EmptyCoroutineContext),
            started = SharingStarted.Eagerly,
            replay = 1,
        )

    override fun productsUpdates(): Flow<List<Product>> = etenState
        .map { state -> state.products.values.sortedBy { it.name } }

    override suspend fun getProduct(uuid: String): Product? = etenState.first().products[uuid]

    override suspend fun saveProduct(product: Product) {
        etenDao.saveProduct(product.toProductTable())
    }

    override suspend fun removeProduct(uuid: String) {
        etenDao.removeProductWithCheck(uuid)
    }

    override fun dishesUpdates(): Flow<List<Dish>> = etenState
        .map { state -> state.dishes.values.sortedByDescending { it.time } }

    override suspend fun getDish(uuid: String): Dish? = etenState.first().dishes[uuid]

    override suspend fun saveDish(dish: Dish) {
        etenDao.saveDish(
            dishTable = dish.toDishTable(),
            mealPartTables = dish.partsList.map { it.toMealPartTable(dish.uuid) }
        )
    }

    override suspend fun removeDish(uuid: String) {
        etenDao.removeDishWithParts(uuid)
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