package com.qwert2603.eten.data.db.table

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDateTime

@Entity
class DishTable(
    @PrimaryKey val uuid: String,
    val name: String,
    val time: LocalDateTime,
)

@Entity
class MealPartTable(
    @PrimaryKey val uuid: String,
    val weight: Double,
)