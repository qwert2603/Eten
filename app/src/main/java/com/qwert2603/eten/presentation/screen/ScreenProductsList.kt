package com.qwert2603.eten.presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.viewModel
import com.qwert2603.eten.presentation.EtenViewModel
import com.qwert2603.eten.presentation.list_item.ItemProduct

@Composable
fun ScreenProductsList(
    navigateToEditProduct: (uuid: String) -> Unit,
) {
    val vm = viewModel<EtenViewModel>()
    val products = vm.productsUpdates.collectAsState(initial = emptyList())
    // todo: scrollbars
    LazyColumnFor(
        items = products.value,
        contentPadding = PaddingValues(bottom = 96.dp)
    ) {
        ItemProduct(product = it, onClick = { navigateToEditProduct(it.uuid) })
        Divider()
    }
}