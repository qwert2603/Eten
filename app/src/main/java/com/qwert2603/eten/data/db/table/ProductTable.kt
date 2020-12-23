package com.qwert2603.eten.data.db.table

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class ProductTable(
    @PrimaryKey val uuid: String,
    @ColumnInfo(index = true) val name: String,
    val calorie: Double,
)