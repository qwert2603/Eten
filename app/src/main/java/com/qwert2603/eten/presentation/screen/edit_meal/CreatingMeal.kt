package com.qwert2603.eten.presentation.screen.edit_meal

import com.qwert2603.eten.domain.model.Meal
import com.qwert2603.eten.presentation.edit_meal_parts.CreatingMealPart
import com.qwert2603.eten.util.timeNow
import kotlinx.datetime.LocalDateTime

data class CreatingMeal(
    val name: String,
    val uuid: String,
    val time: LocalDateTime?,
    val parts: List<CreatingMealPart>,
) {
    fun isValid() = parts.isNotEmpty() && parts.all { it.isValid() }

    fun toMeal() = Meal(
        uuid = uuid,
        name = name.trim().takeIf { it.isNotEmpty() },
        time = time ?: timeNow(),
        partsList = parts.map { it.toVolumedMealPart() },
    )
}