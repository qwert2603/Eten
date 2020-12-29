package com.qwert2603.eten.domain.repo

import java.io.File

interface DumpRepo {
    suspend fun getDump(): File

    /**
     * @return true, if success
     */
    suspend fun restoreDump(dump: String): Boolean
}