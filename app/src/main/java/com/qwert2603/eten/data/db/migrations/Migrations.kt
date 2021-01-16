package com.qwert2603.eten.data.db.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object Migrations {

    val from1to2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("ALTER TABLE MealTable ADD COLUMN `name` TEXT")
            database.execSQL("CREATE TABLE IF NOT EXISTS `RawCaloriesTable` (`containerId` TEXT, `uuid` TEXT NOT NULL, `name` TEXT, `calories` REAL NOT NULL, PRIMARY KEY(`uuid`))")
        }
    }

    val all = arrayOf(
        from1to2,
    )
}