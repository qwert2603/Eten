package com.qwert2603.eten.presentation.screen

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedTask
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.viewModel
import com.qwert2603.eten.R
import com.qwert2603.eten.domain.model.Product
import com.qwert2603.eten.util.toEditingInt
import com.qwert2603.eten.util.toEditingString
import timber.log.Timber

data class CreatingProduct(
    val uuid: String,
    val name: String,
    val caloriesPer100g: Int,
) {
    fun isValid() = name.isNotBlank() && caloriesPer100g >= 0.0

    fun toProduct() = Product(
        uuid = uuid,
        name = name,
        calorie = caloriesPer100g.toDouble() / 100.0
    )
}

@Composable
fun ScreenEditProduct(productUuid: String?, navigateUp: () -> Unit) {
    val vm = viewModel<EditProductViewModel>()
    LaunchedTask { vm.loadProduct(productUuid) } // fixme: recalled after rotate device in all screens.
    val productState = vm.creatingProduct.collectAsState()
    Timber.d("productUuid=$productUuid product=${productState.value}")
    val product = productState.value ?: return

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.screen_title_add_product)) },
                navigationIcon = {
                    IconButton(onClick = navigateUp) {
                        Icon(Icons.Default.ArrowBack)
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
                        Icon(vectorResource(R.drawable.ic_save))
                    }
                })
        }
    ) {
        Column {
            TextField(
                value = product.name,
                onValueChange = {
                    val name = it.take(100).trim()
                    vm.onProductChange(product.copy(name = name))
                },
                placeholder = { Text(stringResource(R.string.common_name)) },
                modifier = Modifier.padding(12.dp),
            )
            TextField(
                value = product.caloriesPer100g.toEditingString(),
                onValueChange = {
                    val caloriesPer100g = it.toEditingInt()
                    vm.onProductChange(product.copy(caloriesPer100g = caloriesPer100g))
                },
                placeholder = { Text(stringResource(R.string.edit_product_field_calorie)) },
                keyboardType = KeyboardType.Number,
                modifier = Modifier.padding(12.dp),
                trailingIcon = { Text(stringResource(R.string.symbol_calorie)) },
            )
        }
    }
}
