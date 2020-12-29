package com.qwert2603.eten.domain.repo

interface DumpRepo {
    suspend fun getDump(): String
    suspend fun restoreDump(dump: String)
}