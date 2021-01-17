package com.qwert2603.eten.presentation.screen.edit_meal

import androidx.annotation.StringRes
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.viewModel
import com.qwert2603.eten.R
import com.qwert2603.eten.presentation.edit_meal_parts.EditVolumedMealPartsList
import com.qwert2603.eten.view.AutocompleteTextField

@Composable
fun ScreenEditMeal(mealUuid: String?, navigateUp: () -> Unit) {
    val vm = viewModel<EditMealViewModel>()
    LaunchedEffect(mealUuid) { vm.loadMeal(mealUuid) }
    val mealState = vm.creatingMeal.collectAsState()
    val meal = mealState.value ?: return

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    @StringRes val titleId = if (mealUuid != null) {
                        R.string.edit_meal_screen_title
                    } else {
                        R.string.new_meal_screen_title
                    }
                    Text(stringResource(titleId))
                },
                navigationIcon = {
                    IconButton(onClick = navigateUp) {
                        Icon(Icons.Default.ArrowBack)
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            vm.saveMeal()
                            navigateUp()
                        },
                        enabled = meal.isValid(),
                    ) {
                        Icon(vectorResource(R.drawable.ic_save))
                    }
                }
            )
        }
    ) {
        ScrollableColumn(
            contentPadding = PaddingValues(12.dp),
        ) {
            AutocompleteTextField(
                fieldId = meal.uuid,
                selectedItem = meal.name,
                searchItems = vm::searchMealNames,
                renderItem = { Text(it) },
                itemToString = { it },
                onItemSelected = {},
                onQueryChanged = {
                    val name = it.take(100)
                    vm.onMealChange(meal.copy(name = name))
                },
                label = { Text(stringResource(R.string.common_name)) },
            )

            EditVolumedMealPartsList(
                parts = meal.parts,
                onPartsChange = { vm.onMealChange(meal.copy(parts = it)) },
                searchProducts = vm::searchProducts,
                searchDishes = vm::searchDishes,
                modifier = Modifier.padding(top = 12.dp),
            )
        }
    }
}