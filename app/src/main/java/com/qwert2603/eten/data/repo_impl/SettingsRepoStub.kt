package com.qwert2603.eten.data.repo_impl

import com.qwert2603.eten.domain.meals.MealsInteractor
import com.qwert2603.eten.domain.repo.SettingsRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

object SettingsRepoStub : SettingsRepo {
    private val dailyLimitCalories = MutableStateFlow(MealsInteractor.DEFAULT_DAILY_LIMIT_CALORIES)

    override fun dailyLimitCaloriesUpdates(): Flow<Double> = dailyLimitCalories

    override suspend fun saveDailyLimitCalories(limit: Double) {
        dailyLimitCalories.value = limit
    }
}