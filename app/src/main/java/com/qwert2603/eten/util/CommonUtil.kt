package com.qwert2603.eten.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.qwert2603.eten.R
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

val <T> T.allCases get() = this

fun Date.format(): String = SimpleDateFormat.getDateTimeInstance().format(this)

fun Double.formatWeight() = "${roundToInt()}g"

@Composable
fun Double.formatCalorie() = "${roundToInt()} ${stringResource(R.string.symbol_calorie)}"

fun Double.formatTotalCalories() = "${roundToInt()}cal"

fun randomUUID() = UUID.randomUUID().toString()