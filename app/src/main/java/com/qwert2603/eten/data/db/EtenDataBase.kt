package com.qwert2603.eten.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.qwert2603.eten.data.db.dao.ProductDao
import com.qwert2603.eten.data.db.table.ProductTable

@Database(
    entities = [ProductTable::class],
    version = 1,
)
abstract class EtenDataBase : RoomDatabase() {
    abstract fun productDao(): ProductDao
}