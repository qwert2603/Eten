package com.qwert2603.eten.presentation.list_item

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.qwert2603.eten.R
import com.qwert2603.eten.domain.model.EtenDay
import com.qwert2603.eten.domain.model.Meal
import com.qwert2603.eten.util.addSpans
import com.qwert2603.eten.util.format
import com.qwert2603.eten.util.toPointedString
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

@Composable
fun ItemEtenDay(
    etenDay: EtenDay,
    onMealClick: (Meal) -> Unit,
    onDeleteMealClick: (Meal) -> Unit,
) {
    Card(
        elevation = 4.dp,
        modifier = Modifier.padding(
            horizontal = 12.dp,
            vertical = 6.dp,
        ),
    ) {
        val limit = etenDay.caloriesLimit.roundToInt()
        val total = etenDay.totalCalories.roundToInt()

        Column {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
            ) {
                Text(
                    etenDay.day.format(),
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                    ),
                    modifier = Modifier.weight(1f),
                )
                val formattedLimit = limit.toPointedString()
                val formattedTotal = total.toPointedString()
                Text(
                    "$formattedTotal / $formattedLimit ${stringResource(R.string.symbol_total_calories)}",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        color = colorResource(if (etenDay.totalCalories <= etenDay.caloriesLimit) {
                            R.color.limit_ok
                        } else {
                            R.color.limit_exceeded
                        }),
                        fontSize = 18.sp,
                    ),
                    modifier = Modifier.padding(start = 12.dp),
                )
            }

            val exceeded = total > limit
            Text(
                stringResource(
                    if (exceeded) R.string.eten_day_calories_exceeded else R.string.eten_day_calories_left,
                    (limit - total).absoluteValue,
                ).addSpans(SpanStyle(fontWeight = FontWeight.Bold)) { it.isDigit() },
                style = TextStyle(
                    color = colorResource(if (exceeded) R.color.limit_exceeded else R.color.limit_ok),
                    fontSize = 18.sp,
                ),
                modifier = Modifier
                    .padding(vertical = 4.dp, horizontal = 16.dp)
            )

            etenDay.meals.forEach {
                ItemMeal(
                    meal = it,
                    onClick = { onMealClick(it) },
                    onDeleteClick = { onDeleteMealClick(it) },
                )
                Divider()
            }
        }
    }
}