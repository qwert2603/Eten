package com.qwert2603.eten.data.db.convert

import androidx.room.TypeConverter
import kotlinx.datetime.*

class LocalDateTimeConverter {

    @TypeConverter
    fun q(localDateTime: LocalDateTime): Long =
        localDateTime.toInstant(TimeZone.UTC).toEpochMilliseconds()

    @TypeConverter
    fun w(millis: Long): LocalDateTime =
        Instant.fromEpochMilliseconds(millis).toLocalDateTime(TimeZone.UTC)
}