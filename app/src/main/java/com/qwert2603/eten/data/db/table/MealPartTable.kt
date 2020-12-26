package com.qwert2603.eten.data.db.table

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class MealPartTable(
    val containerId: String?,
    @PrimaryKey val uuid: String,
    val weight: Double,
    val productUuid: String?,
    val dishUuid: String?,
) {
    init {
        check(listOfNotNull(productUuid, dishUuid).size == 1)
    }
}