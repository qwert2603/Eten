package com.qwert2603.eten.presentation.edit_meal_parts

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.qwert2603.eten.R
import com.qwert2603.eten.domain.model.Dish
import com.qwert2603.eten.domain.model.Product
import com.qwert2603.eten.util.*
import com.qwert2603.eten.view.AutocompleteTextField

@Composable
fun EditMealPart(
    creatingMealPart: CreatingMealPart,
    onPartChange: (CreatingMealPart) -> Unit,
    onDeleteClick: () -> Unit,
    searchProducts: suspend (String) -> List<Product>,
    searchDishes: suspend (String) -> List<Dish>,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
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
                    searchItems = searchProducts,
                    renderItem = { Text(it.name) },
                    itemToString = { it.name },
                    onItemSelected = { selectedProduct ->
                        onPartChange(creatingMealPart.copy(product = selectedProduct))
                    },
//                    toggleModifier = toggleModifier
                )
                is CreatingWeightedDish -> AutocompleteTextField(
                    selectedItem = creatingMealPart.dish,
                    searchItems = searchDishes,
                    renderItem = { Text(it.name) },
                    itemToString = { it.name },
                    onItemSelected = { selectedDish ->
                        onPartChange(creatingMealPart.copy(dish = selectedDish))
                    },
//                    toggleModifier = toggleModifier
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
                    onPartChange(
                        when (creatingMealPart) {
                            is CreatingWeightedProduct -> creatingMealPart.copy(weight = weight)
                            is CreatingWeightedDish -> creatingMealPart.copy(weight = weight)
                        }
                    )
                },
                placeholder = { Text(stringResource(R.string.common_weight)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
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
                onClick = onDeleteClick,
                modifier = Modifier.padding(start = 8.dp),
            ) {
                Icon(Icons.Default.Delete)
            }
        }
    }
}