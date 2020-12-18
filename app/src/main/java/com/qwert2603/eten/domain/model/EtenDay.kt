package com.qwert2603.eten.domain.model

import kotlinx.datetime.LocalDate

data class EtenDay(
    val day: LocalDate,
    val meals: List<Meal>,
    val caloriesLimit: Double,
) {
    val totalCalories = meals.sumByDouble { it.partsList.calories }

    init {
        check(caloriesLimit >= 0.0)
    }
}

