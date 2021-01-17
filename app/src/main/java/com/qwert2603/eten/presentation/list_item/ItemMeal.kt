package com.qwert2603.eten.presentation.list_item

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.qwert2603.eten.R
import com.qwert2603.eten.domain.model.Meal
import com.qwert2603.eten.domain.model.calories
import com.qwert2603.eten.domain.model.name
import com.qwert2603.eten.domain.model.weight
import com.qwert2603.eten.util.formatTitle
import com.qwert2603.eten.util.formatTotalCalories
import com.qwert2603.eten.util.formatWeight

@Composable
fun ItemMeal(
    meal: Meal,
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
                .weight(1f)
        ) {
            Text(meal.formatTitle())

            meal.partsList.forEachIndexed { index, volumedPart ->
                val formattedName = volumedPart.name?.let { "$it: " } ?: ""
                val formattedCalories = volumedPart.calories.formatTotalCalories()
                val formattedWeight = volumedPart.weight?.let { "${it.formatWeight()}, " } ?: ""
                Text(
                    "${index + 1}. $formattedName$formattedWeight$formattedCalories",
                    modifier = Modifier.padding(start = 4.dp),
                )
            }

            val totalWeight = meal.partsList.weight.formatWeight()
            val totalCalories = meal.partsList.calories.formatTotalCalories()
            Text(
                "${stringResource(R.string.common_total)}: $totalWeight, $totalCalories",
                fontWeight = FontWeight.Bold,
            )
        }

        IconButton(
            onClick = onDeleteClick,
            modifier = Modifier.padding(start = 12.dp),
        ) {
            Icon(Icons.Default.Delete)
        }
    }
}