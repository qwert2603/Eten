package com.qwert2603.eten.presentation.screen.lists

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.qwert2603.eten.R
import com.qwert2603.eten.domain.model.Dish
import com.qwert2603.eten.presentation.EtenViewModel
import com.qwert2603.eten.presentation.list_item.ItemDish
import com.qwert2603.eten.presentation.screen.delete.DialogDeleteDish
import com.qwert2603.eten.util.noContentDescription
import com.qwert2603.eten.view.SnackbarHandler
import kotlinx.coroutines.launch

@Composable
fun ScreenDishesList(
    navigateToEditDish: (uuid: String) -> Unit,
    navigateToProductFromDish: (uuid: String) -> Unit,
    snackbarHandler: SnackbarHandler,
) {
    val vm = viewModel<EtenViewModel>()
    val dishesUpdateState by vm.dishesUpdates.collectAsState(initial = null)
    val search by vm.dishesSearch.collectAsState()
    var dishToDelete by remember { mutableStateOf<Dish?>(null) }
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    val dishesUpdate = dishesUpdateState ?: return

    fun onSearchChange(search: String) {
        vm.onSearchDishChange(search)
        scope.launch { listState.scrollToItem(0) }
    }

    Column {
        TextField(
            value = search,
            onValueChange = { onSearchChange(it.take(100)) },
            label = { Text(stringResource(R.string.common_search)) },
            modifier = Modifier.padding(12.dp),
            trailingIcon = {
                IconButton(onClick = { onSearchChange("") }) {
                    Icon(
                        Icons.Default.Clear,
                        contentDescription = noContentDescription,
                    )
                }
            },
        )
        Divider(thickness = 2.dp)

        LazyColumn(
            contentPadding = PaddingValues(bottom = 112.dp),
            state = listState,
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