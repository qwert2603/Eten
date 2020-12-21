package com.qwert2603.eten.data.repo_impl

import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import com.qwert2603.eten.EtenApplication
import com.qwert2603.eten.domain.meals.MealsInteractor
import com.qwert2603.eten.domain.repo.SettingsRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// todo: remove stub repos from constructors
object SettingsRepoImpl/*(
    context: Context = EtenApplication.APP,
)*/ : SettingsRepo {

    private val dataStore = EtenApplication.APP.createDataStore("eten")

    private val dailyLimitCalories = preferencesKey<Double>("dailyLimitCalories")

    override fun dailyLimitCaloriesUpdates(): Flow<Double> = dataStore.data
        .map { it[dailyLimitCalories] ?: MealsInteractor.DEFAULT_DAILY_LIMIT_CALORIES }

    override suspend fun saveDailyLimitCalories(limit: Double) {
        dataStore.edit {
            it[dailyLimitCalories] = limit
        }
    }
}