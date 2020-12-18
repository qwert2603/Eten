package com.qwert2603.eten.presentation.screen.edit_meal

import com.qwert2603.eten.domain.model.Meal
import com.qwert2603.eten.domain.model.PartsList
import com.qwert2603.eten.presentation.edit_meal_parts.CreatingMealPart
import com.qwert2603.eten.util.timeNow
import kotlinx.datetime.LocalDateTime

data class CreatingMeal(
    val uuid: String,
    val time: LocalDateTime?,
    val parts: List<CreatingMealPart>,
) {
    fun isValid() = parts.isNotEmpty() && parts.all { it.isValid() }

    fun toMeal() = Meal(
        uuid = uuid,
        time = time ?: timeNow(),
        partsList = PartsList(parts.map { it.toWeightedMealPart() }),
    )
}