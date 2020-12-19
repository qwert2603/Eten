package com.qwert2603.eten.domain.meals

import com.qwert2603.eten.data.repo_impl.EtenRepoStub
import com.qwert2603.eten.data.repo_impl.SettingsRepoStub
import com.qwert2603.eten.domain.model.EtenDay
import com.qwert2603.eten.domain.model.Meal
import com.qwert2603.eten.domain.repo.EtenRepo
import com.qwert2603.eten.domain.repo.SettingsRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

// todo: remove from constructors by default
class MealsInteractor(
    private val etenRepo: EtenRepo = EtenRepoStub,
    private val settingsRepo: SettingsRepo = SettingsRepoStub,
) {
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
                meals = meals.sortedBy { it.time },
                caloriesLimit = dailyLimitCalories,
            )
        }
        .sortedByDescending { it.day }
}