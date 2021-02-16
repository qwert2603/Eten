package com.qwert2603.eten.presentation.screen.lists

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.viewModel
import com.qwert2603.eten.R
import com.qwert2603.eten.domain.model.EtenDay
import com.qwert2603.eten.domain.model.Meal
import com.qwert2603.eten.presentation.EtenViewModel
import com.qwert2603.eten.presentation.list_item.ItemEtenDay
import com.qwert2603.eten.presentation.screen.delete.DialogDeleteMeal
import com.qwert2603.eten.util.format
import com.qwert2603.eten.view.SnackbarHandler
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn

@Composable
fun ScreenMealsList(
    navigateToEditMeal: (uuid: String) -> Unit,
    snackbarHandler: SnackbarHandler,
) {
    val vm = viewModel<EtenViewModel>()
    val etenDaysState by vm.etenDaysUpdates.collectAsState(initial = null)
    var mealToDelete by remember { mutableStateOf<Meal?>(null) }

    val etenDays = etenDaysState ?: return

    if (etenDays.isEmpty()) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize(),
        ) {
            Text(
                stringResource(R.string.meals_list_is_empty),
                style = TextStyle(fontSize = 26.sp),
                textAlign = TextAlign.Center,
                modifier = Modifier,
            )
        }
    }

    // todo: may be better.
    val key = { etenDay: EtenDay -> etenDay.day.atStartOfDayIn(TimeZone.UTC).epochSeconds }
    LazyColumn(
        contentPadding = PaddingValues(bottom = 112.dp),
    ) {
        items(etenDays, key = key) { etenDay ->
            ItemEtenDay(
                etenDay = etenDay,
                onMealClick = { navigateToEditMeal(it.uuid) },
                onDeleteMealClick = { mealToDelete = it },
            )
        }
    }

    mealToDelete?.also { meal ->
        val snackbarMessage = stringResource(R.string.meal_deleted)
        val snackbarAction = stringResource(R.string.common_cancel)
        DialogDeleteMeal(
            meal = meal,
            onDelete = {
                mealToDelete = null
                vm.deleteMeal(meal.uuid)
                snackbarHandler.show(
                    message = snackbarMessage,
                    action = snackbarAction,
                    onClick = { vm.onRestoreMealClick(meal) }
                )
            },
            onCancel = { mealToDelete = null },
        )
    }
}