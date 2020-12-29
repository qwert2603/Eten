package com.qwert2603.eten

interface Env {
    val isDebug: Boolean
}

// todo: features.
object E {
    val env: Env = object : Env {
        override val isDebug = BuildConfig.DEBUG
    }
}