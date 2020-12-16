package com.qwert2603.eten.data.repo_impl

import com.qwert2603.eten.domain.repo.SettingsRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

object SettingsRepoStub : SettingsRepo {
    private val dailyLimitCalories = MutableStateFlow(2_500.0)

    override fun dailyLimitCaloriesUpdates(): Flow<Double> = dailyLimitCalories

    override fun saveDailyLimitCalories(limit: Double) {
        dailyLimitCalories.value = limit
    }
}