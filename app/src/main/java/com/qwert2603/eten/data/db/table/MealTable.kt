package com.qwert2603.eten.data.db.table

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDateTime

@Entity
class MealTable(
    @PrimaryKey val uuid: String,
    val time: LocalDateTime,
)
