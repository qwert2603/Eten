package com.qwert2603.eten.presentation.list_item

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.qwert2603.eten.R
import com.qwert2603.eten.domain.model.Dish
import com.qwert2603.eten.domain.model.calories
import com.qwert2603.eten.domain.model.name
import com.qwert2603.eten.domain.model.weight
import com.qwert2603.eten.util.*
import com.qwert2603.eten.view.DeleteButton

@Composable
fun ItemDish(
    dish: Dish,
    isDeletable: Boolean,
    onClick: () -> Unit,
    onSaveAsProductClick: () -> Unit,
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
            Text(
                "${dish.formatTitle()} (${dish.caloriePer100g.formatCalorie()})",
                fontWeight = FontWeight.Bold,
            )
            dish.partsList.forEachIndexed { index, weightedPart ->
                Text(
                    "${index + 1}. ${weightedPart.name}: ${weightedPart.weight.formatWeight()}, ${weightedPart.calories.formatTotalCalories()}",
                    modifier = Modifier.padding(start = 4.dp),
                )
            }
            val totalWeight = dish.partsList.weight.formatWeight()
            val totalCalories = dish.partsList.calories.formatTotalCalories()
            Text(
                "${stringResource(R.string.common_total)}: $totalWeight, $totalCalories",
                fontWeight = FontWeight.Bold,
            )
        }

        IconButton(
            onClick = onSaveAsProductClick,
            modifier = Modifier.padding(start = 12.dp),
        ) {
            Icon(painterResource(R.drawable.ic_product), noContentDescription)
        }
        DeleteButton(
            isEnabled = isDeletable,
            onDeleteClick = onDeleteClick,
            modifier = Modifier.padding(start = 12.dp),
        )
    }
}