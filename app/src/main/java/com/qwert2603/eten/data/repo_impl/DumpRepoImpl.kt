package com.qwert2603.eten.data.repo_impl

import com.qwert2603.eten.data.dump.SerializableDump
import com.qwert2603.eten.data.dump.toDump
import com.qwert2603.eten.data.dump.toSerializableDump
import com.qwert2603.eten.di.DI
import com.qwert2603.eten.domain.repo.DumpRepo
import com.qwert2603.eten.util.Catch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import timber.log.Timber
import java.io.File

object DumpRepoImpl : DumpRepo {

    private const val DUMP_FILENAME = "eten_dump.json"

    private val dumpDir by lazy {
        File(DI.appContext.cacheDir, "dump").apply {
            mkdirs()
        }
    }
    private val dumpDao = DI.db.dumpDao()

    override suspend fun getDump(): File = dumpDao.getDump()
        .toSerializableDump()
        .let { Json.encodeToString(it) }
        .let { dumpString ->
            File(dumpDir, DUMP_FILENAME).apply {
                writeText(dumpString)
            }
        }

    override suspend fun restoreDump(dump: String): Boolean {
        return runCatching {
            Timber.d("restoreDump ${dump.length}")
            val serializableDump = Json.decodeFromString<SerializableDump>(dump)
            val dbDump = serializableDump.toDump()
            dumpDao.restoreEtenState(dbDump)
        }
            .map { true }
            .getOrElse {
                Catch.log(it)
                false
            }
    }
}