package com.qwert2603.eten.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.qwert2603.eten.data.db.convert.LocalDateTimeConverter
import com.qwert2603.eten.data.db.dao.DumpDao
import com.qwert2603.eten.data.db.dao.EtenDao
import com.qwert2603.eten.data.db.table.*

@Database(
    entities = [
        ProductTable::class,
        DishTable::class,
        MealTable::class,
        MealPartTable::class,
        RawCaloriesTable::class,
    ],
    version = 2,
)
@TypeConverters(
    LocalDateTimeConverter::class,
)
abstract class EtenDataBase : RoomDatabase() {
    abstract fun etenDao(): EtenDao
    abstract fun dumpDao(): DumpDao
}