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
import com.qwert2603.eten.domain.model.Product
import com.qwert2603.eten.presentation.EtenViewModel
import com.qwert2603.eten.presentation.list_item.ItemProduct
import com.qwert2603.eten.presentation.screen.delete.DialogDeleteProduct
import com.qwert2603.eten.util.noContentDescription
import com.qwert2603.eten.view.SnackbarHandler
import kotlinx.coroutines.launch

@Composable
fun ScreenProductsList(
    navigateToEditProduct: (uuid: String) -> Unit,
    snackbarHandler: SnackbarHandler,
) {
    val vm = viewModel<EtenViewModel>()
    val productsUpdateState by vm.productsUpdates.collectAsState(initial = null)
    val search by vm.productsSearch.collectAsState()
    var productToDelete by remember { mutableStateOf<Product?>(null) }
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    val productsUpdate = productsUpdateState ?: return

    fun onSearchChange(search: String) {
        vm.onSearchProductChange(search)
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

        // todo: scrollbars
        LazyColumn(
            contentPadding = PaddingValues(bottom = 112.dp),
            state = listState,
        ) {
            items(productsUpdate.products, key = Product::uuid) {
                ItemProduct(
                    product = it,
                    isDeletable = it.uuid in productsUpdate.removableProductsUuids,
                    onClick = { navigateToEditProduct(it.uuid) },
                    onDeleteClick = { productToDelete = it },
                )
                Divider()
            }
        }
    }

    productToDelete?.also { product ->
        val snackbarMessage = stringResource(R.string.product_deleted)
        val snackbarAction = stringResource(R.string.common_cancel)
        DialogDeleteProduct(
            product = product,
            onDelete = {
                productToDelete = null
                vm.deleteProduct(product.uuid)
                snackbarHandler.show(
                    message = snackbarMessage,
                    action = snackbarAction,
                    onClick = { vm.onRestoreProductClick(product) }
                )
            },
            onCancel = { productToDelete = null },
        )
    }
}