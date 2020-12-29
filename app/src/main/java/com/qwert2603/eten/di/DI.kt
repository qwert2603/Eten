package com.qwert2603.eten.di

import androidx.room.Room
import com.qwert2603.eten.E
import com.qwert2603.eten.EtenApplication
import com.qwert2603.eten.data.db.EtenDataBase

// TODO:
object DI {
    val appContext by lazy { EtenApplication.APP }

    val db by lazy {
        Room
            .databaseBuilder(appContext, EtenDataBase::class.java, "eten.db")
            .also { if (E.isDebug) it.fallbackToDestructiveMigration() }
            .build()
    }
}