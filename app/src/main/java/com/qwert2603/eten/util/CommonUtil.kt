package com.qwert2603.eten.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.qwert2603.eten.R
import com.qwert2603.eten.domain.model.Dish
import com.qwert2603.eten.domain.model.Meal
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

val <T> T.allCases get() = this

fun Date.format(): String = SimpleDateFormat.getDateTimeInstance().format(this)

fun Double.formatWeight() = "${roundToInt()}g"

@Composable
fun Double.formatCalorie() = "${roundToInt()} ${stringResource(R.string.symbol_calorie)}"

fun Double.formatTotalCalories() = "${roundToInt()}cal"

@Composable
fun Meal.formatTitle() = stringResource(R.string.meal_title_format, time.format())

@Composable
fun Dish.formatTitle() = stringResource(R.string.dish_title_format, name, time.format())

fun randomUUID() = UUID.randomUUID().toString()

fun timeNow() = Date()

fun Int.toEditingString() = takeIf { it != 0 }?.toString() ?: ""
fun String.toEditingInt(maxNumbers: Int = 4) = take(maxNumbers).toIntOrNull() ?: 0

fun <T> List<T>.mapIf(condition: (T) -> Boolean, mapper: (T) -> T): List<T> = this
    .map {
        if (condition(it)) {
            mapper(it)
        } else {
            it
        }
    }

inline fun <T, reified R : T> List<T>.mapIfTyped(
    condition: (T) -> Boolean,
    mapper: (R) -> T,
): List<T> = this
    .map {
        if (it is R && condition(it)) {
            mapper(it)
        } else {
            it
        }
    }