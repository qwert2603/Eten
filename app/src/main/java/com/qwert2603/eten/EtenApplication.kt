package com.qwert2603.eten

import android.app.Application
import android.content.Context
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.qwert2603.eten.util.Catch
import timber.log.Timber

class EtenApplication : Application() {
    companion object {
        lateinit var APP: Context
    }

    override fun onCreate() {
        super.onCreate()

        APP = this

        Catch.init(
            log = { t ->
                FirebaseCrashlytics.getInstance().recordException(t)
                Timber.e(t)
            }
        )

        Timber.plant(Timber.DebugTree())
    }
}