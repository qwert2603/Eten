package com.qwert2603.eten.presentation.screen.settings

import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.viewModel
import com.qwert2603.eten.R
import com.qwert2603.eten.util.toEditingInt
import com.qwert2603.eten.util.toEditingString

@Composable
fun ScreenSettings() {
    val vm = viewModel<SettingsViewModel>()

    val dailyLimitCalories by vm.dailyLimitCaloriesUpdates.collectAsState()

    Scaffold(
        topBar = {
            //todo: navigate up
            TopAppBar(
                title = { Text(stringResource(R.string.settings_screen_title)) }
            )
            //todo: "save" button
        }
    ) {
        ScrollableColumn(
            contentPadding = PaddingValues(12.dp),
        ) {
            TextField(
                value = dailyLimitCalories.toInt().toEditingString(),
                onValueChange = {
                    val limit = it.toEditingInt(maxNumbers = 5).toDouble()
                    vm.onLimitChanged(limit)
                },
                placeholder = { Text(stringResource(R.string.settings_daily_calories_limit)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                trailingIcon = { Text(stringResource(R.string.symbol_calorie)) },
            )
        }
    }
}