package com.qwert2603.eten.presentation.screen.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.qwert2603.eten.R
import com.qwert2603.eten.util.noContentDescription
import com.qwert2603.eten.util.toEditingInt
import com.qwert2603.eten.util.toEditingString

@Composable
fun ScreenSettings(
    navigateUp: () -> Unit,
    navigateToDump: () -> Unit,
) {
    val vm = viewModel<SettingsViewModel>()
    val settingsModel by vm.settingsModel.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.settings_screen_title)) },
                navigationIcon = {
                    IconButton(onClick = navigateUp) {
                        Icon(Icons.Default.ArrowBack, noContentDescription)
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
                        Icon(painterResource(R.drawable.ic_save), noContentDescription)
                    }
                },
            )
        }
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(12.dp)
        ) {
            TextField(
                value = settingsModel.dailyLimitCalories.toEditingString(),
                onValueChange = {
                    val limit = it.toEditingInt(maxNumbers = 5)
                    vm.onDailyLimitCaloriesChange(limit)
                },
                label = { Text(stringResource(R.string.settings_daily_calories_limit)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                trailingIcon = { Text(stringResource(R.string.symbol_calorie)) },
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = navigateToDump) {
                Text(stringResource(R.string.dump_screen_title))
            }
        }
    }
}