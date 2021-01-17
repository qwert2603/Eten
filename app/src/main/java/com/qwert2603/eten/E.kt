package com.qwert2603.eten

import java.util.*

interface Env {
    val isDebug: Boolean
    val forceLocale: Locale?
    val showUndoDeleteSnackbar: Boolean
}

// todo: features.
object E {
    val env: Env = object : Env {
        override val isDebug = BuildConfig.DEBUG
        override val forceLocale: Locale? = null
        override val showUndoDeleteSnackbar = false
    }
}