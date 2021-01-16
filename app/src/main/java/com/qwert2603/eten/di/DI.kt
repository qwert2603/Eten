package com.qwert2603.eten.di

import androidx.room.Room
import com.qwert2603.eten.E
import com.qwert2603.eten.EtenApplication
import com.qwert2603.eten.data.db.EtenDataBase
import com.qwert2603.eten.data.db.migrations.Migrations

// TODO:
object DI {
    val appContext by lazy { EtenApplication.APP }

    val db by lazy {
        Room
            .databaseBuilder(appContext, EtenDataBase::class.java, "eten.db")
            .addMigrations(*Migrations.all)
            .also { if (E.env.isDebug) it.fallbackToDestructiveMigration() }
            .build()
    }
}