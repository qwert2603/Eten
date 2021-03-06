package com.qwert2603.eten.presentation.screen.edit_dish

import com.qwert2603.eten.domain.model.Dish
import com.qwert2603.eten.presentation.edit_meal_parts.CreatingWeightedMealPart
import com.qwert2603.eten.util.timeNow
import kotlinx.datetime.LocalDateTime

data class CreatingDish(
    val uuid: String,
    val name: String,
    val time: LocalDateTime?,
    val parts: List<CreatingWeightedMealPart>,
) {
    fun isValid() =
        name.isNotBlank()
                && parts.isNotEmpty()
                && parts.all { it.isValid() }

    fun toDish() = Dish(
        uuid = uuid,
        name = name.trim(),
        time = time ?: timeNow(),
        partsList = parts.map { it.toVolumedMealPart() },
    )
}