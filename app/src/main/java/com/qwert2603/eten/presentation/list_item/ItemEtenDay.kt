package com.qwert2603.eten.presentation.list_item

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.qwert2603.eten.R
import com.qwert2603.eten.domain.model.EtenDay
import com.qwert2603.eten.domain.model.Meal
import com.qwert2603.eten.util.format

@Composable
fun ItemEtenDay(
    etenDay: EtenDay,
    onMealClick: (Meal) -> Unit,
    onDeleteMealClick: (Meal) -> Unit,
) {
    Column {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
        ) {
            Text(
                etenDay.day.format(),
                style = TextStyle(fontWeight = FontWeight.Bold),
                modifier = Modifier.weight(1f),
            )
            Text(
                "${etenDay.totalCalories} / ${etenDay.caloriesLimit} ${stringResource(R.string.symbol_total_calories)}",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    color = colorResource(if (etenDay.totalCalories <= etenDay.caloriesLimit) {
                        R.color.limit_ok
                    } else {
                        R.color.limit_exceeded
                    }),
                ),
                modifier = Modifier.padding(start = 12.dp),
            )
        }
        etenDay.meals.forEach {
            ItemMeal(
                meal = it,
                onClick = { onMealClick(it) },
                onDeleteClick = { onDeleteMealClick(it) },
            )
        }
    }
}