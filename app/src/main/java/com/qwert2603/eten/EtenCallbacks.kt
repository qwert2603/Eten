package com.qwert2603.eten

import java.io.File

interface EtenCallbacks {
    fun sendDump(file: File)
    fun searchCalorie(product: String)
}