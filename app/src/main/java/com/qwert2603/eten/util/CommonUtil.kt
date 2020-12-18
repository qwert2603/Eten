package com.qwert2603.eten.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.qwert2603.eten.R
import com.qwert2603.eten.domain.model.Dish
import com.qwert2603.eten.domain.model.Meal
import kotlinx.datetime.*
import kotlinx.datetime.TimeZone
import java.util.*
import kotlin.math.roundToInt

val <T> T.allCases get() = this

fun LocalDate.format(): String = this.toString() // todo

fun LocalDateTime.format(): String = this.toString() // todo

@Composable
fun Double.formatWeight() = "${roundToInt()}${stringResource(R.string.symbol_grams)}"

@Composable
fun Double.formatCalorie() = "${roundToInt()} ${stringResource(R.string.symbol_calorie)}"

@Composable
fun Double.formatTotalCalories() =
    "${roundToInt()}${stringResource(R.string.symbol_total_calories)}"

@Composable
fun Meal.formatTitle() = stringResource(R.string.meal_title_format, time.format())

@Composable
fun Dish.formatTitle() = stringResource(R.string.dish_title_format, name, time.format())

fun randomUUID() = UUID.randomUUID().toString() // todo: remove import java.util.*

fun timeNow() = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()) // todo: ?

fun Int.toEditingString() = takeIf { it != 0 }?.toString() ?: ""
fun String.toEditingInt(maxNumbers: Int = 4) = take(maxNumbers).toIntOrNull() ?: 0
