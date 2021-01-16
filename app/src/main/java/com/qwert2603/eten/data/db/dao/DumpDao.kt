package com.qwert2603.eten.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.qwert2603.eten.data.db.result.Dump
import com.qwert2603.eten.data.db.table.*

@Dao
interface DumpDao {

    @Query("SELECT * FROM ProductTable")
    suspend fun getAllProducts(): List<ProductTable>

    @Query("SELECT * FROM MealPartTable")
    suspend fun getAllMealParts(): List<MealPartTable>

    @Query("SELECT * FROM RawCaloriesTable")
    suspend fun getAllRawCalories(): List<RawCaloriesTable>

    @Query("SELECT * FROM DishTable")
    suspend fun getAllDishes(): List<DishTable>

    @Query("SELECT * FROM MealTable")
    suspend fun getAllMeals(): List<MealTable>

    @Query("DELETE FROM ProductTable")
    suspend fun deleteAllProducts()

    @Query("DELETE FROM MealPartTable")
    suspend fun deleteAllMealParts()

    @Query("DELETE FROM RawCaloriesTable")
    suspend fun deleteAllRawCalories()

    @Query("DELETE FROM DishTable")
    suspend fun deleteAllDishes()

    @Query("DELETE FROM MealTable")
    suspend fun deleteAllMeals()

    @Insert
    suspend fun saveProducts(productTables: List<ProductTable>)

    @Insert
    suspend fun saveParts(mealPartTables: List<MealPartTable>)

    @Insert
    suspend fun saveRawCalories(rawCaloriesTables: List<RawCaloriesTable>)

    @Insert
    suspend fun saveDishes(dishTables: List<DishTable>)

    @Insert
    suspend fun saveMeals(mealTables: List<MealTable>)

    @Transaction
    suspend fun getDump() = Dump(
        productTables = getAllProducts(),
        mealPartTables = getAllMealParts(),
        rawCaloriesTables = getAllRawCalories(),
        dishTables = getAllDishes(),
        mealTables = getAllMeals(),
    )

    @Transaction
    suspend fun restoreEtenState(dump: Dump) {
        // todo: check integrity.

        deleteAllMeals()
        deleteAllDishes()
        deleteAllMealParts()
        deleteAllRawCalories()
        deleteAllProducts()

        saveProducts(dump.productTables)
        saveRawCalories(dump.rawCaloriesTables)
        saveParts(dump.mealPartTables)
        saveDishes(dump.dishTables)
        saveMeals(dump.mealTables)
    }
}