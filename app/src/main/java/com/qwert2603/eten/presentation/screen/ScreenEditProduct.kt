package com.qwert2603.eten.presentation.screen

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.viewModel
import com.qwert2603.eten.R
import com.qwert2603.eten.data.repo_impl.EtenRepoStub
import com.qwert2603.eten.domain.model.Product
import com.qwert2603.eten.presentation.EtenViewModel
import com.qwert2603.eten.util.randomUUID
import kotlinx.coroutines.launch

@Composable
fun ScreenEditProduct(productUuid: String?, navigateUp: () -> Unit) {
    val scope = rememberCoroutineScope()
    val vm: EtenViewModel = viewModel()

    val product by produceState<Product?>(null) { productUuid?.let { vm.getProduct(it) } }
    val name = mutableStateOf(product?.name ?: "")
    val caloriesPer100g = mutableStateOf(product?.caloriePer100g?.toInt() ?: 0)
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
                            scope.launch {
                                EtenRepoStub.saveProduct(
                                    Product(
                                        uuid = product?.uuid ?: randomUUID(),
                                        name = name.value,
                                        calorie = caloriesPer100g.value / 100.0,
                                    )
                                )
                            }
                            navigateUp()
                        },
                        enabled = name.value.isNotBlank() && caloriesPer100g.value >= 0
                    ) {
                        Icon(vectorResource(R.drawable.ic_save))
                    }
                })
        }
    ) {
        Column {
            TextField(
                value = name.value,
                onValueChange = { name.value = it.take(100).trim() },
                placeholder = { Text(stringResource(R.string.common_name)) },
                modifier = Modifier.padding(12.dp),
            )
            TextField(
                value = caloriesPer100g.value.takeIf { it != 0 }?.toString() ?: "",
                onValueChange = { caloriesPer100g.value = it.take(4).toIntOrNull() ?: 0 },
                placeholder = { Text(stringResource(R.string.edit_product_field_calorie)) },
                keyboardType = KeyboardType.Number,
                modifier = Modifier.padding(12.dp),
                trailingIcon = { Text(stringResource(R.string.symbol_calorie)) },
            )
        }
    }
}
