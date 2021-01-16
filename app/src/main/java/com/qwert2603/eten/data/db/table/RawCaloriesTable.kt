package com.qwert2603.eten.data.db.table

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class RawCaloriesTable(
    val containerId: String?,
    @PrimaryKey val uuid: String,
    val name: String?,
    val calories: Double,
)