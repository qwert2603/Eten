package com.qwert2603.eten.data.db.result

import androidx.room.Embedded
import androidx.room.Relation
import com.qwert2603.eten.data.db.table.MealPartTable
import com.qwert2603.eten.data.db.table.MealTable
import com.qwert2603.eten.data.db.table.RawCaloriesTable

class MealWithParts(
    @Embedded val mealTable: MealTable,
    @Relation(
        parentColumn = "uuid",
        entityColumn = "containerId",
    ) val parts: List<MealPartTable>,
    @Relation(
        parentColumn = "uuid",
        entityColumn = "containerId",
    ) val rawCalories: List<RawCaloriesTable>,
)