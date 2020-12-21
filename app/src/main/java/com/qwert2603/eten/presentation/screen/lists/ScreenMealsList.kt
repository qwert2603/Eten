package com.qwert2603.eten.presentation.screen.lists

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.viewModel
import com.qwert2603.eten.domain.model.Meal
import com.qwert2603.eten.presentation.EtenViewModel
import com.qwert2603.eten.presentation.list_item.ItemEtenDay
import com.qwert2603.eten.presentation.screen.delete.DialogDeleteMeal

@Composable
fun ScreenMealsList(
    navigateToEditMeal: (uuid: String) -> Unit,
) {
    val vm = viewModel<EtenViewModel>()
    val meals = vm.etenDaysUpdates.collectAsState(initial = emptyList())
    var mealToDelete by remember { mutableStateOf<Meal?>(null) }

    LazyColumn(
        contentPadding = PaddingValues(bottom = 112.dp),
    ) {
        items(meals.value) { etenDay ->
            ItemEtenDay(
                etenDay = etenDay,
                onMealClick = { navigateToEditMeal(it.uuid) },
                onDeleteMealClick = { mealToDelete = it },
            )
            Divider(thickness = 2.dp)
        }
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