package com.qwert2603.eten.presentation.screen.lists

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.material.Divider
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.viewModel
import com.qwert2603.eten.domain.model.Meal
import com.qwert2603.eten.presentation.EtenViewModel
import com.qwert2603.eten.presentation.list_item.ItemMeal
import com.qwert2603.eten.presentation.screen.delete.DialogDeleteMeal

@Composable
fun ScreenMealsList() {
    val vm = viewModel<EtenViewModel>()
    val meals = vm.mealsUpdates.collectAsState(initial = emptyList())
    var mealToDelete by remember { mutableStateOf<Meal?>(null) }

    // todo: show calories sum by day
    LazyColumnFor(
        items = meals.value,
        contentPadding = PaddingValues(bottom = 112.dp),
    ) {
        ItemMeal(
            meal = it,
            onClick = {},
            onDeleteClick = { mealToDelete = it },
        )
        Divider()
    }

    mealToDelete?.also {
        DialogDeleteMeal(
            meal = it,
            onDelete = {
                mealToDelete = null
                vm.deleteMeal(it.uuid)
            },
            onCancel = { mealToDelete = null },
        )
    }
}