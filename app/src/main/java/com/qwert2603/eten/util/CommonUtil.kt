package com.qwert2603.eten.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
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

val noContentDescription: String? = null

val <T> T.allCases get() = Unit

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
fun Double.formatWeight() = "${roundToInt()} ${stringResource(R.string.symbol_grams)}"

@Composable
fun Double.formatCalorie() = "${roundToInt()} ${stringResource(R.string.symbol_calorie)}"

@Composable
fun Double.formatTotalCalories() =
    "${roundToInt()} ${stringResource(R.string.symbol_total_calories)}"

@Composable
fun Meal.formatTitle() = if (name != null) {
    stringResource(R.string.meal_title_format_with_name, name, time.formatTime())
} else {
    stringResource(R.string.meal_title_format, time.formatTime())
}

@Composable
fun Dish.formatTitle() = stringResource(R.string.dish_title_format, name, time.format())

fun randomUUID() = UUID.randomUUID().toString() // todo: remove import java.util.*

fun timeNow() = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()) // todo: ?

fun Int.toEditingString(): String = takeIf { it != 0 }?.toString() ?: ""
fun String.toEditingInt(maxNumbers: Int = 4) = take(maxNumbers).toIntOrNull() ?: 0

fun <T, R> Flow<List<T>>.mapList(mapper: (T) -> R): Flow<List<R>> = map { list -> list.map(mapper) }

fun String.addSpans(
    spanStyle: SpanStyle,
    isAddSpan: (Char) -> Boolean,
): AnnotatedString {
    val builder = AnnotatedString.Builder(this)
    var start: Int? = null
    for (index in this.indices) {
        if (isAddSpan(this[index])) {
            if (start == null) {
                start = index
            }
        } else {
            if (start != null) {
                builder.addStyle(spanStyle, start, index)
                start = null
            }
        }
    }
    if (start != null) {
        builder.addStyle(spanStyle, start, length)
    }
    return builder.toAnnotatedString()
}