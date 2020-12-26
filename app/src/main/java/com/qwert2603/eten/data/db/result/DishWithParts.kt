package com.qwert2603.eten.data.db.result

import androidx.room.Embedded
import androidx.room.Relation
import com.qwert2603.eten.data.db.table.DishTable
import com.qwert2603.eten.data.db.table.MealPartTable

class DishWithParts(
    @Embedded val dishTable: DishTable,
    @Relation(
        parentColumn = "uuid",
        entityColumn = "containerId",
    ) val parts: List<MealPartTable>,
)