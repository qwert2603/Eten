package com.qwert2603.eten.presentation.screen.edit_dish

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedTask
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.viewModel
import com.qwert2603.eten.R
import com.qwert2603.eten.util.*
import com.qwert2603.eten.view.AutocompleteTextField

inline fun <reified T : CreatingMealPart> EditDishViewModel.updatePart(
    uuid: String,
    update: (T) -> T,
) {
    val creatingDish = creatingDish.value!!
    onDishChange(
        creatingDish.copy(
            parts = creatingDish.parts.mapIfTyped<CreatingMealPart, T>(
                condition = { it.uuid == uuid },
                mapper = { update(it) },
            )
        )
    )
}

@Composable
fun ScreenEditDish(dishUuid: String?, navigateUp: () -> Unit) {
    val vm = viewModel<EditDishViewModel>()
    LaunchedTask { vm.loadDish(dishUuid) }
    val dishState = vm.creatingDish.collectAsState()
    val dish = dishState.value ?: return

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.screen_title_add_dish)) },
                navigationIcon = {
                    IconButton(onClick = navigateUp) {
                        Icon(Icons.Default.ArrowBack)
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            vm.saveDish()
                            navigateUp()
                        },
                        enabled = dish.isValid(),
                    ) {
                        Icon(vectorResource(R.drawable.ic_save))
                    }
                }
            )
        }
    ) {
        ScrollableColumn(
            contentPadding = PaddingValues(12.dp),
        ) {
            TextField(
                value = dish.name,
                onValueChange = {
                    val name = it.take(100).trim()
                    vm.onDishChange(dish.copy(name = name))
                },
                placeholder = { Text(stringResource(R.string.common_name)) },
            )
            dish.parts.forEach { creatingMealPart ->
                Column(
                    modifier = Modifier.padding(top = 12.dp),
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Image(
                            vectorResource(
                                when (creatingMealPart) {
                                    is CreatingWeightedProduct -> R.drawable.ic_product
                                    is CreatingWeightedDish -> R.drawable.ic_dish
                                }
                            )
                        )

                        val toggleModifier = Modifier
                            .padding(start = 8.dp)
                            .weight(2f)
                        when (creatingMealPart) {
                            is CreatingWeightedProduct -> AutocompleteTextField(
                                selectedItem = creatingMealPart.product,
                                searchItems = { vm.searchProducts(it) },
                                renderItem = { Text(it.name) },
                                itemToString = { it.name },
                                onItemSelected = { selectedProduct ->
                                    vm.updatePart<CreatingWeightedProduct>(creatingMealPart.uuid) {
                                        it.copy(product = selectedProduct)
                                    }
                                },
                                toggleModifier = toggleModifier
                            )
                            is CreatingWeightedDish -> AutocompleteTextField(
                                selectedItem = creatingMealPart.dish,
                                searchItems = { vm.searchDishes(it) },
                                renderItem = { Text(it.name) },
                                itemToString = { it.name },
                                onItemSelected = { selectedDish ->
                                    vm.updatePart<CreatingWeightedDish>(creatingMealPart.uuid) {
                                        it.copy(dish = selectedDish)
                                    }
                                },
                                toggleModifier = toggleModifier
                            )
                        }.allCases
                    }
                    if (creatingMealPart is CreatingWeightedDish && creatingMealPart.dish != null) {
                        Text(
                            creatingMealPart.dish.time.format(),
                            modifier = Modifier.padding(top = 4.dp, start = 32.dp),
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(top = 4.dp, start = 24.dp)
                    ) {
                        TextField(
                            value = creatingMealPart.weight.toEditingString(),
                            onValueChange = { s ->
                                val weight = s.toEditingInt()
                                vm.updatePart<CreatingMealPart>(creatingMealPart.uuid) {
                                    when (it) {
                                        is CreatingWeightedProduct -> it.copy(weight = weight)
                                        is CreatingWeightedDish -> it.copy(weight = weight)
                                    }
                                }
                            },
                            placeholder = { Text(stringResource(R.string.common_weight)) },
                            keyboardType = KeyboardType.Number,
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 8.dp),
                            trailingIcon = { Text(stringResource(R.string.symbol_grams)) },
                        )

                        Text(
                            creatingMealPart.calories.formatTotalCalories(),
                            modifier = Modifier
                                .padding(start = 8.dp),
                        )
                        IconButton(
                            onClick = {
                                vm.onDishChange(
                                    dish.copy(parts = dish.parts.filter { it.uuid != creatingMealPart.uuid })
                                )
                            },
                            modifier = Modifier.padding(start = 8.dp),
                        ) {
                            Icon(Icons.Default.Delete)
                        }
                    }
                }
            }

            val totalWeightFormatted = dish.totalWeight().toDouble().formatWeight()
            val totalCaloriesFormatted = dish.totalCalories().formatTotalCalories()
            Text(
                "${stringResource(R.string.common_total)}: $totalWeightFormatted, $totalCaloriesFormatted",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 12.dp),
            )
            Button(
                onClick = {
                    val creatingWeightedProduct = CreatingWeightedProduct(randomUUID(), null, 0)
                    vm.onDishChange(dish.copy(parts = dish.parts + creatingWeightedProduct))
                },
                modifier = Modifier.padding(top = 12.dp),
            ) {
                Text(stringResource(R.string.edit_dish_button_add_product))
            }
            Button(
                onClick = {
                    val creatingWeightedDish = CreatingWeightedDish(randomUUID(), null, 0)
                    vm.onDishChange(dish.copy(parts = dish.parts + creatingWeightedDish))
                },
                modifier = Modifier.padding(top = 12.dp),
            ) {
                Text(stringResource(R.string.edit_dish_button_add_dish))
            }
        }
    }
}