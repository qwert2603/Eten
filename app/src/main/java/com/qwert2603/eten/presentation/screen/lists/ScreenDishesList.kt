package com.qwert2603.eten.presentation.screen.lists

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.viewModel
import com.qwert2603.eten.domain.model.Dish
import com.qwert2603.eten.presentation.EtenViewModel
import com.qwert2603.eten.presentation.list_item.ItemDish
import com.qwert2603.eten.presentation.screen.delete.DialogDeleteDish

@Composable
fun ScreenDishesList(
    navigateToEditDish: (uuid: String) -> Unit,
) {
    val vm = viewModel<EtenViewModel>()
    val dishes = vm.dishesUpdates.collectAsState(initial = emptyList())
    var dishToDelete by remember { mutableStateOf<Dish?>(null) }

    LazyColumn(
        contentPadding = PaddingValues(bottom = 112.dp),
    ) {
        items(dishes.value) {
            ItemDish(
                dish = it,
                onClick = { navigateToEditDish(it.uuid) },
                onDeleteClick = { dishToDelete = it },
            )
            Divider()
        }
    }

    dishToDelete?.also {
        DialogDeleteDish(
            dish = it,
            onDelete = {
                dishToDelete = null
                vm.deleteDish(it.uuid)
            },
            onCancel = { dishToDelete = null },
        )
    }
}