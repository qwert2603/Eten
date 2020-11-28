package com.qwert2603.eten.presentation.list_item

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.qwert2603.eten.domain.model.Meal
import com.qwert2603.eten.util.format
import com.qwert2603.eten.util.formatTotalCalories
import com.qwert2603.eten.util.formatWeight

@Composable
fun ItemMeal(meal: Meal) {
    Column(
        modifier = Modifier.padding(12.dp),
    ) {
        Text(meal.time.format())
        meal.partsList.forEachIndexed { index, weightedPart ->
            Text(
                "${index + 1}. ${weightedPart.mealPart.name}: ${weightedPart.weight.formatWeight()}, ${weightedPart.calories.formatTotalCalories()}",
                modifier = Modifier.padding(start = 4.dp),
            )
        }
        Text(
            "Total: ${meal.partsList.weight.formatWeight()}, ${meal.partsList.calories.formatTotalCalories()}",
            fontWeight = FontWeight.Bold,
        )
    }
}