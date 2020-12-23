package com.qwert2603.eten.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.qwert2603.eten.data.db.table.ProductTable
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Query("SELECT * FROM ProductTable ORDER BY name")
    fun observeProducts(): Flow<List<ProductTable>>

    @Query("SELECT * FROM ProductTable WHERE uuid = :uuid")
    suspend fun getProduct(uuid: String): ProductTable?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveProduct(productTable: ProductTable)
}