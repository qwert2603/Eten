package com.qwert2603.eten.presentation.screen.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qwert2603.eten.data.repo_impl.SettingsRepoImpl
import com.qwert2603.eten.domain.repo.SettingsRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val settingsRepo: SettingsRepo = SettingsRepoImpl,
) : ViewModel() {

    val settingsModel = MutableStateFlow(SettingsModel(0))

    init {
        viewModelScope.launch {
            settingsModel.value = SettingsModel(
                dailyLimitCalories = settingsRepo.dailyLimitCaloriesUpdates().first().toInt()
            )
        }
    }

    fun onDailyLimitCaloriesChange(dailyLimitCalories: Int) {
        settingsModel.value = settingsModel.value.copy(
            dailyLimitCalories = dailyLimitCalories
        )
    }

    fun onSaveClick() {
        val value = settingsModel.value
        if (!value.canSave()) return
        viewModelScope.launch {
            settingsRepo.saveDailyLimitCalories(value.dailyLimitCalories.toDouble())
        }
    }
}