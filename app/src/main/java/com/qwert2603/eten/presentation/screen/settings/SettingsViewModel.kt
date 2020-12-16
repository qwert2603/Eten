package com.qwert2603.eten.presentation.screen.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qwert2603.eten.data.repo_impl.SettingsRepoStub
import com.qwert2603.eten.domain.repo.SettingsRepo
import kotlinx.coroutines.flow.*

class SettingsViewModel(
    private val settingsRepo: SettingsRepo = SettingsRepoStub
) : ViewModel() {

    val dailyLimitCaloriesUpdates = settingsRepo.dailyLimitCaloriesUpdates()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), 0.0)

    fun onLimitChanged(limit: Double) {
        settingsRepo.saveDailyLimitCalories(limit)
    }
}