package com.qwert2603.eten.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.qwert2603.eten.data.db.result.Dump
import com.qwert2603.eten.data.db.table.DishTable
import com.qwert2603.eten.data.db.table.MealPartTable
import com.qwert2603.eten.data.db.table.MealTable
import com.qwert2603.eten.data.db.table.ProductTable

@Dao
interface DumpDao {

    @Query("SELECT * FROM ProductTable")
    suspend fun getAllProducts(): List<ProductTable>

    @Query("SELECT * FROM MealPartTable")
    suspend fun getAllMealParts(): List<MealPartTable>

    @Query("SELECT * FROM DishTable")
    suspend fun getAllDishes(): List<DishTable>

    @Query("SELECT * FROM MealTable")
    suspend fun getAllMeals(): List<MealTable>

    @Query("DELETE FROM ProductTable")
    suspend fun deleteAllProducts()

    @Query("DELETE FROM MealPartTable")
    suspend fun deleteAllMealParts()

    @Query("DELETE FROM DishTable")
    suspend fun deleteAllDishes()

    @Query("DELETE FROM MealTable")
    suspend fun deleteAllMeals()

    @Insert
    suspend fun saveProducts(productTables: List<ProductTable>)

    @Insert
    suspend fun saveParts(mealPartTables: List<MealPartTable>)

    @Insert
    suspend fun saveDishes(dishTables: List<DishTable>)

    @Insert
    suspend fun saveMeals(mealTables: List<MealTable>)

    @Transaction
    suspend fun getDump() = Dump(
        productTables = getAllProducts(),
        mealPartTables = getAllMealParts(),
        dishTables = getAllDishes(),
        mealTables = getAllMeals(),
    )

    @Transaction
    suspend fun restoreEtenState(dump: Dump) {
        // todo: check integrity.

        deleteAllMeals()
        deleteAllDishes()
        deleteAllMealParts()
        deleteAllProducts()

        saveProducts(dump.productTables)
        saveParts(dump.mealPartTables)
        saveDishes(dump.dishTables)
        saveMeals(dump.mealTables)
    }
}