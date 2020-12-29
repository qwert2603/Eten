package com.qwert2603.eten.data.repo_impl

import com.qwert2603.eten.data.dump.SerializableDump
import com.qwert2603.eten.data.dump.toDump
import com.qwert2603.eten.data.dump.toSerializableDump
import com.qwert2603.eten.di.DI
import com.qwert2603.eten.domain.repo.DumpRepo
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object DumpRepoImpl : DumpRepo {

    private val dumpDao = DI.db.dumpDao()

    override suspend fun getDump(): String = dumpDao.getDump()
        .toSerializableDump()
        .let { Json.encodeToString(it) }

    override suspend fun restoreDump(dump: String) {
        val serializableDump = Json.decodeFromString<SerializableDump>(dump)
        val dbDump = serializableDump.toDump()
        dumpDao.restoreEtenState(dbDump)
    }
}