package com.qwert2603.eten.presentation.screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.viewModel
import com.qwert2603.eten.presentation.EtenViewModel
import com.qwert2603.eten.presentation.list_item.ItemDish

@Composable
fun ScreenDishesList(
    navigateToEditDish: (uuid: String) -> Unit
) {
    val vm = viewModel<EtenViewModel>()
    val dishes = vm.dishesUpdates.collectAsState(initial = emptyList())
    LazyColumnFor(
        items = dishes.value,
        contentPadding = PaddingValues(bottom = 96.dp),
    ) {
        ItemDish(dish = it, onClick = { navigateToEditDish(it.uuid) })
        Divider()
    }
}