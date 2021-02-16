package com.qwert2603.eten.presentation.screen.edit_product

import androidx.annotation.StringRes
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.qwert2603.eten.R
import com.qwert2603.eten.util.noContentDescription
import com.qwert2603.eten.util.toEditingInt
import com.qwert2603.eten.util.toEditingString
import timber.log.Timber

@Composable
fun ScreenEditProduct(
    editProductParam: EditProductParam,
    navigateUp: () -> Unit,
    searchCalorie: (product: String) -> Unit,
) {
    val vm = viewModel<EditProductViewModel>()
    LaunchedEffect(editProductParam) { vm.loadProduct(editProductParam) } // fixme: check: recalled after rotate device in all screens.
    val productState = vm.creatingProduct.collectAsState()
    Timber.d("editProductParam=$editProductParam product=${productState.value}")
    val product = productState.value ?: return

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    @StringRes val titleId = when (editProductParam) {
                        EditProductParam.NewProduct -> R.string.new_product_screen_title
                        is EditProductParam.EditProduct -> R.string.edit_product_screen_title
                        is EditProductParam.FromDish -> R.string.new_product_screen_title
                    }
                    Text(stringResource(titleId))
                },
                navigationIcon = {
                    IconButton(onClick = navigateUp) {
                        Icon(Icons.Default.ArrowBack, noContentDescription)
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            vm.saveProduct()
                            navigateUp()
                        },
                        enabled = product.isValid(),
                    ) {
                        Icon(painterResource(R.drawable.ic_save), noContentDescription)
                    }
                })
        }
    ) {
        ScrollableColumn {
            TextField(
                value = product.name,
                onValueChange = {
                    val name = it.take(100)
                    vm.onProductChange(product.copy(name = name))
                },
                label = { Text(stringResource(R.string.common_name)) },
                modifier = Modifier.padding(12.dp),
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                TextField(
                    value = product.caloriesPer100g.toEditingString(),
                    onValueChange = {
                        val caloriesPer100g = it.toEditingInt()
                        vm.onProductChange(product.copy(caloriesPer100g = caloriesPer100g))
                    },
                    label = { Text(stringResource(R.string.edit_product_field_calorie)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.padding(12.dp),
                    trailingIcon = { Text(stringResource(R.string.symbol_calorie)) },
                )
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(
                    onClick = { searchCalorie(product.name) },
                    enabled = product.name.isNotBlank(),
                ) {
                    Icon(painterResource(R.drawable.ic_google), noContentDescription)
                }
            }
        }
    }
}
