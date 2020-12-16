package com.qwert2603.eten.presentation.screen.edit_meal

import androidx.annotation.StringRes
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedTask
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.viewModel
import com.qwert2603.eten.R
import com.qwert2603.eten.presentation.edit_meal_parts.EditMealPartsList

@Composable
fun ScreenEditMeal(mealUuid: String?, navigateUp: () -> Unit) {
    val vm = viewModel<EditMealViewModel>()
    LaunchedTask { vm.loadMeal(mealUuid) }
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
            EditMealPartsList(
                parts = meal.parts,
                onPartsChange = { vm.onMealChange(meal.copy(parts = it)) },
                searchProducts = vm::searchProducts,
                searchDishes = vm::searchDishes,
            )
        }
    }
}