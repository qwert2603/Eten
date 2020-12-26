package com.qwert2603.eten.data.db.dao

import androidx.room.*
import com.qwert2603.eten.data.db.result.DishWithParts
import com.qwert2603.eten.data.db.result.EtenTables
import com.qwert2603.eten.data.db.result.MealWithParts
import com.qwert2603.eten.data.db.table.DishTable
import com.qwert2603.eten.data.db.table.MealPartTable
import com.qwert2603.eten.data.db.table.MealTable
import com.qwert2603.eten.data.db.table.ProductTable
import kotlinx.coroutines.flow.Flow
import timber.log.Timber

@Dao
interface EtenDao {

    @Query("SELECT * FROM ProductTable")
    fun getAllProducts(): List<ProductTable>

    @Transaction
    @Query("SELECT * FROM DishTable")
    fun getAllDishes(): List<DishWithParts>

    @Transaction
    @Query("SELECT * FROM MealTable")
    fun getAllMeals(): List<MealWithParts>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveProduct(productTable: ProductTable)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveParts(mealPartTables: List<MealPartTable>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveDish(dishTable: DishTable)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMeal(mealTable: MealTable)

    @Query("DELETE FROM MealTable WHERE uuid=:uuid")
    suspend fun removeMeal(uuid: String)

    @Query("DELETE FROM MealPartTable WHERE containerId=:containerUuid")
    suspend fun removeParts(containerUuid: String)

    class Ignored(val ignored: Int)

    @Query("SELECT 1918 as ignored FROM ProductTable, MealPartTable, DishTable, MealTable")
    fun observeUpdates(): Flow<Ignored>

    @Transaction
    fun getEtenTables() = EtenTables(
        productTables = getAllProducts(),
        dishTables = getAllDishes(),
        mealTables = getAllMeals(),
    )

    @Transaction
    suspend fun saveDish(dishTable: DishTable, mealPartTables: List<MealPartTable>) {
        saveDish(dishTable)
        removeParts(dishTable.uuid)
        saveParts(mealPartTables)
    }

    @Transaction
    suspend fun saveMeal(mealTable: MealTable, mealPartTables: List<MealPartTable>) {
        saveMeal(mealTable)
        removeParts(mealTable.uuid)
        saveParts(mealPartTables)
    }

    @Transaction
    suspend fun removeMealWithParts(uuid: String) {
        removeMeal(uuid)
        removeParts(uuid)
    }
}


