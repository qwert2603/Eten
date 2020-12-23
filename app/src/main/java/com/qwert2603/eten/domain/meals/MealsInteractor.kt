package com.qwert2603.eten.domain.meals

import com.qwert2603.eten.data.repo_impl.EtenRepoImpl
import com.qwert2603.eten.data.repo_impl.SettingsRepoImpl
import com.qwert2603.eten.domain.model.EtenDay
import com.qwert2603.eten.domain.model.Meal
import com.qwert2603.eten.domain.repo.EtenRepo
import com.qwert2603.eten.domain.repo.SettingsRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

// todo: remove from constructors by default
class MealsInteractor(
    private val etenRepo: EtenRepo = EtenRepoImpl,
    private val settingsRepo: SettingsRepo = SettingsRepoImpl,
) {
    companion object {
        const val DEFAULT_DAILY_LIMIT_CALORIES = 2_500.0
    }

    fun etenDaysUpdates(): Flow<List<EtenDay>> = etenRepo.mealsUpdates()
        .combine(settingsRepo.dailyLimitCaloriesUpdates()) { meals, dailyLimitCalories ->
            toMealListItems(meals, dailyLimitCalories)
        }

    private fun toMealListItems(
        meals: List<Meal>,
        dailyLimitCalories: Double,
    ): List<EtenDay> = meals
        .groupBy { it.time.date }
        .toList()
        .map { (day, meals) ->
            EtenDay(
                day = day,
                meals = meals.sortedByDescending { it.time },
                caloriesLimit = dailyLimitCalories,
            )
        }
        .sortedByDescending { it.day }
}