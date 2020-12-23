package com.qwert2603.eten.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.qwert2603.eten.R
import com.qwert2603.eten.domain.model.Dish
import com.qwert2603.eten.domain.model.Meal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.*
import kotlinx.datetime.TimeZone
import java.util.*
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

val <T> T.allCases get() = this

fun LocalDate.format(): String = this.toString()

fun LocalDateTime.format(): String = "${this.date.format()} ${this.formatTime()}"

fun LocalDateTime.formatTime(): String = this.toString().substring(11, 16)

fun Int.toPointedString() = toLong().toPointedString()

fun Long.toPointedString(): String {
    val negative = this < 0
    val absString = this.absoluteValue.toString().reversed()
    val stringBuilder = StringBuilder()
    absString.forEachIndexed { index, c ->
        stringBuilder.append(c)
        if (index % 3 == 2 && index != absString.lastIndex) stringBuilder.append('.')
    }
    if (negative) stringBuilder.append('-')
    return stringBuilder.reverse().toString()
}

@Composable
fun Double.formatWeight() = "${roundToInt()}${stringResource(R.string.symbol_grams)}"

@Composable
fun Double.formatCalorie() = "${roundToInt()} ${stringResource(R.string.symbol_calorie)}"

@Composable
fun Double.formatTotalCalories() =
    "${roundToInt()}${stringResource(R.string.symbol_total_calories)}"

@Composable
fun Meal.formatTitle() = stringResource(R.string.meal_title_format, time.formatTime())

@Composable
fun Dish.formatTitle() = stringResource(R.string.dish_title_format, name, time.format())

fun randomUUID() = UUID.randomUUID().toString() // todo: remove import java.util.*

fun timeNow() = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()) // todo: ?

fun Int.toEditingString(): String = takeIf { it != 0 }?.toString() ?: ""
fun String.toEditingInt(maxNumbers: Int = 4) = take(maxNumbers).toIntOrNull() ?: 0

fun <T, R> Flow<List<T>>.mapList(mapper: (T) -> R): Flow<List<R>> = map { list -> list.map(mapper) }