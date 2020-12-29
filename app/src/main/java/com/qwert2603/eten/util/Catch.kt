package com.qwert2603.eten.util

import kotlin.properties.Delegates.notNull

object Catch {
    private var sendLog by notNull<(Throwable) -> Unit>()

    fun init(
        log: (Throwable) -> Unit,
    ) {
        sendLog = log
    }

    fun log(t: Throwable) {
        sendLog(t)
    }

    fun log(s: String) {
        sendLog(Exception(s))
    }
}