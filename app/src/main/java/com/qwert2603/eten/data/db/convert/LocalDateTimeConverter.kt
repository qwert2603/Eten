package com.qwert2603.eten.data.db.convert

import androidx.room.TypeConverter
import kotlinx.datetime.*

class LocalDateTimeConverter {

    @TypeConverter
    fun convert(localDateTime: LocalDateTime): Long =
        localDateTime.toInstant(TimeZone.UTC).toEpochMilliseconds()

    @TypeConverter
    fun convert(millis: Long): LocalDateTime =
        Instant.fromEpochMilliseconds(millis).toLocalDateTime(TimeZone.UTC)
}