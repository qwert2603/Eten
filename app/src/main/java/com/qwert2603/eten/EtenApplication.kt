package com.qwert2603.eten

import android.app.Application
import android.content.Context
import timber.log.Timber

class EtenApplication : Application() {
    companion object {
        lateinit var APP: Context
    }

    override fun onCreate() {
        super.onCreate()

        APP = this

        Timber.plant(Timber.DebugTree())
    }
}