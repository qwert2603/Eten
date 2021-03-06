package com.qwert2603.eten.presentation.screen.edit_dish

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.qwert2603.eten.R
import com.qwert2603.eten.presentation.edit_meal_parts.EditWeightedMealPartsList
import com.qwert2603.eten.util.noContentDescription

@Composable
fun ScreenEditDish(dishUuid: String?, navigateUp: () -> Unit) {
    val vm = viewModel<EditDishViewModel>()
    LaunchedEffect(dishUuid) { vm.loadDish(dishUuid) }
    val dishState = vm.creatingDish.collectAsState()
    val dish = dishState.value ?: return

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    @StringRes val titleId = if (dishUuid != null) {
                        R.string.edit_dish_screen_title
                    } else {
                        R.string.new_dish_screen_title
                    }
                    Text(stringResource(titleId))
                },
                navigationIcon = {
                    IconButton(onClick = navigateUp) {
                        Icon(Icons.Default.ArrowBack, noContentDescription)
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            vm.saveDish()
                            navigateUp()
                        },
                        enabled = dish.isValid(), // todo: show validation error in all "edit" screens.
                    ) {
                        Icon(painterResource(R.drawable.ic_save), noContentDescription)
                    }
                }
            )
        }
    ) {

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(12.dp)
        ) {
            TextField(
                value = dish.name,
                onValueChange = {
                    val name = it.take(100)
                    vm.onDishChange(dish.copy(name = name))
                },
                label = { Text(stringResource(R.string.common_name)) },
            )

            EditWeightedMealPartsList(
                parts = dish.parts,
                onPartsChange = { vm.onDishChange(dish.copy(parts = it)) },
                searchProducts = vm::searchProducts,
                searchDishes = vm::searchDishes,
                modifier = Modifier.padding(top = 12.dp),
            )
        }
    }
}