package com.qwert2603.eten.domain.repo

import kotlinx.coroutines.flow.Flow

interface SettingsRepo {
    fun dailyLimitCaloriesUpdates(): Flow<Double>
    suspend fun saveDailyLimitCalories(limit: Double)
}