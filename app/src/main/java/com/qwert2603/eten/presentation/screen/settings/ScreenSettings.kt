package com.qwert2603.eten.presentation.screen.settings

import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.viewModel
import com.qwert2603.eten.R
import com.qwert2603.eten.util.toEditingInt
import com.qwert2603.eten.util.toEditingString

@Composable
fun ScreenSettings(
    navigateUp: () -> Unit,
) {
    val vm = viewModel<SettingsViewModel>()
    val settingsModel by vm.settingsModel.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.settings_screen_title)) },
                navigationIcon = {
                    IconButton(onClick = navigateUp) {
                        Icon(Icons.Default.ArrowBack)
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            vm.onSaveClick()
                            navigateUp()
                        },
                        enabled = settingsModel.canSave(),
                    ) {
                        Icon(vectorResource(R.drawable.ic_save))
                    }
                },
            )
        }
    ) {
        ScrollableColumn(
            contentPadding = PaddingValues(12.dp),
        ) {
            TextField(
                value = settingsModel.dailyLimitCalories.toEditingString(),
                onValueChange = {
                    val limit = it.toEditingInt(maxNumbers = 5)
                    vm.onDailyLimitCaloriesChange(limit)
                },
                placeholder = { Text(stringResource(R.string.settings_daily_calories_limit)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                trailingIcon = { Text(stringResource(R.string.symbol_calorie)) },
            )
        }
    }
}