package com.qwert2603.eten.presentation.edit_meal_parts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.qwert2603.eten.R
import com.qwert2603.eten.domain.model.Dish
import com.qwert2603.eten.domain.model.Product
import com.qwert2603.eten.util.formatTotalCalories
import com.qwert2603.eten.util.formatWeight
import com.qwert2603.eten.util.randomUUID
import com.qwert2603.eten.util.replaceIf


@Composable
fun EditVolumedMealPartsList(
    parts: List<CreatingMealPart>,
    onPartsChange: (List<CreatingMealPart>) -> Unit,
    searchProducts: suspend (String) -> List<Product>,
    searchDishes: suspend (String) -> List<Dish>,
    modifier: Modifier = Modifier,
) {
    EditMealPartsList(
        canAddCalories = true,
        parts = parts,
        onPartsChange = onPartsChange,
        searchProducts = searchProducts,
        searchDishes = searchDishes,
        modifier = modifier,
    )
}


@Composable
fun EditWeightedMealPartsList(
    parts: List<CreatingWeightedMealPart>,
    onPartsChange: (List<CreatingWeightedMealPart>) -> Unit,
    searchProducts: suspend (String) -> List<Product>,
    searchDishes: suspend (String) -> List<Dish>,
    modifier: Modifier = Modifier,
) {
    @Suppress("UNCHECKED_CAST")
    EditMealPartsList(
        canAddCalories = false,
        parts = parts,
        onPartsChange = { onPartsChange(it as List<CreatingWeightedMealPart>) },
        searchProducts = searchProducts,
        searchDishes = searchDishes,
        modifier = modifier,
    )
}

@Composable
private fun EditMealPartsList(
    canAddCalories: Boolean,
    parts: List<CreatingMealPart>,
    onPartsChange: (List<CreatingMealPart>) -> Unit,
    searchProducts: suspend (String) -> List<Product>,
    searchDishes: suspend (String) -> List<Dish>,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        parts.forEachIndexed { index, creatingMealPart ->
            EditMealPart(
                creatingMealPart = creatingMealPart,
                onPartChange = { newMealPart ->
                    onPartsChange(
                        parts.replaceIf(
                            condition = { it.uuid == creatingMealPart.uuid },
                            newItem = newMealPart,
                        )
                    )
                },
                onDeleteClick = { onPartsChange(parts.filter { it.uuid != creatingMealPart.uuid }) },
                searchProducts = searchProducts,
                searchDishes = searchDishes,
                modifier = Modifier.padding(top = if (index > 0) 12.dp else 0.dp)
            )
        }

        val totalWeightFormatted = parts.sumOf { it.weight ?: 0 }.toDouble().formatWeight()
        val totalCaloriesFormatted = parts.sumOf { it.calories }.formatTotalCalories()
        Text(
            "${stringResource(R.string.common_total)}: $totalWeightFormatted, $totalCaloriesFormatted",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 12.dp),
        )
        Button(
            onClick = {
                val creatingWeightedProduct = CreatingWeightedProduct(randomUUID(), null, 0)
                onPartsChange(parts + creatingWeightedProduct)
            },
            modifier = Modifier.padding(top = 12.dp),
        ) {
            Text(stringResource(R.string.edit_dish_button_add_product))
        }
        Button(
            onClick = {
                val creatingWeightedDish = CreatingWeightedDish(randomUUID(), null, 0)
                onPartsChange(parts + creatingWeightedDish)
            },
            modifier = Modifier.padding(top = 12.dp),
        ) {
            Text(stringResource(R.string.edit_dish_button_add_dish))
        }
        if (canAddCalories) {
            Button(
                onClick = {
                    val creatingCalories = CreatingCalories(randomUUID(), "", 0)
                    onPartsChange(parts + creatingCalories)
                },
                modifier = Modifier.padding(top = 12.dp),
            ) {
                Text(stringResource(R.string.edit_dish_button_add_calories))
            }
        }
    }
}
