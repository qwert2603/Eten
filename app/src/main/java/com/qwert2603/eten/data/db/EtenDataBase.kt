package com.qwert2603.eten.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.qwert2603.eten.data.db.convert.LocalDateTimeConverter
import com.qwert2603.eten.data.db.dao.EtenDao
import com.qwert2603.eten.data.db.table.DishTable
import com.qwert2603.eten.data.db.table.MealPartTable
import com.qwert2603.eten.data.db.table.MealTable
import com.qwert2603.eten.data.db.table.ProductTable

@Database(
    entities = [
        ProductTable::class,
        DishTable::class,
        MealTable::class,
        MealPartTable::class,
    ],
    version = 1,
)
@TypeConverters(
    LocalDateTimeConverter::class,
)
abstract class EtenDataBase : RoomDatabase() {
    abstract fun etenDao(): EtenDao
}