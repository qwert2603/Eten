package com.qwert2603.eten.presentation.list_item

import androidx.compose.foundation.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.qwert2603.eten.domain.model.Dish
import com.qwert2603.eten.util.formatTitle
import com.qwert2603.eten.util.formatTotalCalories
import com.qwert2603.eten.util.formatWeight

@Composable
fun ItemDish(
    dish: Dish,
    onClick: () -> Unit,
    onDeleteClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(16.dp)
            .fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier
                .weight(1f),
        ) {
            Text(dish.formatTitle())
            dish.partsList.forEachIndexed { index, weightedPart ->
                Text(
                    "${index + 1}. ${weightedPart.mealPart.name}: ${weightedPart.weight.formatWeight()}, ${weightedPart.calories.formatTotalCalories()}",
                    modifier = Modifier.padding(start = 4.dp),
                )
            }
            Text(
                "Total: ${dish.partsList.weight.formatWeight()}, ${dish.partsList.calories.formatTotalCalories()}",
                fontWeight = FontWeight.Bold,
            )
        }
        // todo: "save as product" button.
        IconButton(
            onClick = onDeleteClick,
            modifier = Modifier.padding(start = 12.dp),
        ) {
            Icon(Icons.Default.Delete)
        }
    }
}