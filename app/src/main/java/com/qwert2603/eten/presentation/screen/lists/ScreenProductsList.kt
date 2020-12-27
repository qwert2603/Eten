package com.qwert2603.eten.presentation.screen.lists

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.viewModel
import com.qwert2603.eten.domain.model.Product
import com.qwert2603.eten.presentation.EtenViewModel
import com.qwert2603.eten.presentation.list_item.ItemProduct
import com.qwert2603.eten.presentation.screen.delete.DialogDeleteProduct

@Composable
fun ScreenProductsList(
    navigateToEditProduct: (uuid: String) -> Unit,
) {
    val vm = viewModel<EtenViewModel>()
    val productsUpdateState by vm.productsUpdates.collectAsState(initial = null)
    var productToDelete by remember { mutableStateOf<Product?>(null) }

    val productsUpdate = productsUpdateState ?: return

    // todo: scrollbars
    LazyColumn(
        contentPadding = PaddingValues(bottom = 112.dp),
    ) {
        items(productsUpdate.products) {
            ItemProduct(
                product = it,
                isDeletable = it.uuid in productsUpdate.removableProductsUuids,
                onClick = { navigateToEditProduct(it.uuid) },
                onDeleteClick = { productToDelete = it },
            )
            Divider()
        }
    }

    productToDelete?.also {
        DialogDeleteProduct(
            product = it,
            onDelete = {
                productToDelete = null
                vm.deleteProduct(it.uuid)
            },
            onCancel = { productToDelete = null },
        )
    }
}