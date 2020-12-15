package com.qwert2603.eten

import android.app.Application
import timber.log.Timber

class EtenApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
    }
}