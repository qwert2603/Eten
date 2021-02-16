package com.qwert2603.eten.presentation.screen.lists

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.qwert2603.eten.R
import com.qwert2603.eten.domain.model.Dish
import com.qwert2603.eten.presentation.EtenViewModel
import com.qwert2603.eten.presentation.list_item.ItemDish
import com.qwert2603.eten.presentation.screen.delete.DialogDeleteDish
import com.qwert2603.eten.view.SnackbarHandler

@Composable
fun ScreenDishesList(
    navigateToEditDish: (uuid: String) -> Unit,
    navigateToProductFromDish: (uuid: String) -> Unit,
    snackbarHandler: SnackbarHandler,
) {
    val vm = viewModel<EtenViewModel>()
    val dishesUpdateState by vm.dishesUpdates.collectAsState(initial = null)
    var dishToDelete by remember { mutableStateOf<Dish?>(null) }

    val dishesUpdate = dishesUpdateState ?: return

    LazyColumn(
        contentPadding = PaddingValues(bottom = 112.dp),
    ) {
        items(dishesUpdate.dishes, key = Dish::uuid) {
            ItemDish(
                dish = it,
                isDeletable = it.uuid in dishesUpdate.removableDishesUuids,
                onClick = { navigateToEditDish(it.uuid) },
                onSaveAsProductClick = { navigateToProductFromDish(it.uuid) },
                onDeleteClick = { dishToDelete = it },
            )
            Divider()
        }
    }

    dishToDelete?.also { dish ->
        val snackbarMessage = stringResource(R.string.dish_deleted)
        val snackbarAction = stringResource(R.string.common_cancel)
        DialogDeleteDish(
            dish = dish,
            onDelete = {
                dishToDelete = null
                vm.deleteDish(dish.uuid)
                snackbarHandler.show(
                    message = snackbarMessage,
                    action = snackbarAction,
                    onClick = { vm.onRestoreDishClick(dish) }
                )
            },
            onCancel = { dishToDelete = null },
        )
    }
}