package com.qwert2603.eten.presentation.screen.settings

data class SettingsModel(
    val dailyLimitCalories: Int,
) {
    fun canSave() = dailyLimitCalories >= 0
}