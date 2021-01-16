package com.qwert2603.eten.data.db.dao

import androidx.room.*
import com.qwert2603.eten.data.db.result.DishWithParts
import com.qwert2603.eten.data.db.result.EtenTables
import com.qwert2603.eten.data.db.result.MealWithParts
import com.qwert2603.eten.data.db.table.*
import kotlinx.coroutines.flow.Flow

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
    suspend fun saveRawCalories(caloriesTables: List<RawCaloriesTable>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveDish(dishTable: DishTable)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMeal(mealTable: MealTable)

    @Query("DELETE FROM ProductTable WHERE uuid=:uuid")
    suspend fun removeProduct(uuid: String)

    @Query("DELETE FROM DishTable WHERE uuid=:uuid")
    suspend fun removeDish(uuid: String)

    @Query("DELETE FROM MealTable WHERE uuid=:uuid")
    suspend fun removeMeal(uuid: String)

    @Query("DELETE FROM MealPartTable WHERE containerId=:containerUuid")
    suspend fun removeParts(containerUuid: String)

    @Query("DELETE FROM RawCaloriesTable WHERE containerId=:containerUuid")
    suspend fun removeRawCalories(containerUuid: String)

    @Query("SELECT COUNT(uuid) FROM MealPartTable WHERE productUuid=:uuid")
    suspend fun getProductUsagesCount(uuid: String): Int

    @Query("SELECT COUNT(uuid) FROM MealPartTable WHERE dishUuid=:uuid")
    suspend fun getDishUsagesCount(uuid: String): Int

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
    suspend fun saveDish(
        dishTable: DishTable,
        mealPartTables: List<MealPartTable>,
        rawCaloriesTables: List<RawCaloriesTable>,
    ) {
        // todo: check, that all parts exist.
        saveDish(dishTable)

        removeParts(dishTable.uuid)
        saveParts(mealPartTables)

        removeRawCalories(dishTable.uuid)
        saveRawCalories(rawCaloriesTables)
    }

    @Transaction
    suspend fun saveMeal(
        mealTable: MealTable,
        mealPartTables: List<MealPartTable>,
        rawCaloriesTables: List<RawCaloriesTable>,
    ) {
        // todo: check, that all parts exist.
        saveMeal(mealTable)

        removeParts(mealTable.uuid)
        saveParts(mealPartTables)

        removeRawCalories(mealTable.uuid)
        saveRawCalories(rawCaloriesTables)
    }

    /** @return true, if removed. */
    @Transaction
    suspend fun removeProductWithCheck(uuid: String): Boolean {
        if (getProductUsagesCount(uuid) > 0) return false
        removeProduct(uuid)
        return true
    }

    /** @return true, if removed. */
    @Transaction
    suspend fun removeDishWithParts(uuid: String): Boolean {
        if (getDishUsagesCount(uuid) > 0) return false
        removeParts(uuid)
        removeRawCalories(uuid)
        removeDish(uuid)
        return true
    }

    @Transaction
    suspend fun removeMealWithParts(uuid: String) {
        removeParts(uuid)
        removeRawCalories(uuid)
        removeMeal(uuid)
    }
}


