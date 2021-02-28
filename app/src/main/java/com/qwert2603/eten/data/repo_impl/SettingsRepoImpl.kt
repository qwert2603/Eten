package com.qwert2603.eten.data.repo_impl

import android.content.Context
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.qwert2603.eten.EtenApplication
import com.qwert2603.eten.domain.meals.MealsInteractor
import com.qwert2603.eten.domain.repo.SettingsRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("eten")

// todo: remove stub repos from constructors
object SettingsRepoImpl/*(
    context: Context = EtenApplication.APP,
)*/ : SettingsRepo {

    private val dataStore = EtenApplication.APP.dataStore

    private val dailyLimitCalories = doublePreferencesKey("dailyLimitCalories")

    override fun dailyLimitCaloriesUpdates(): Flow<Double> = dataStore.data
        .map { it[dailyLimitCalories] ?: MealsInteractor.DEFAULT_DAILY_LIMIT_CALORIES }

    override suspend fun saveDailyLimitCalories(limit: Double) {
        dataStore.edit {
            it[dailyLimitCalories] = limit
        }
    }
}