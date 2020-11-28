package com.qwert2603.eten.util

import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

val <T> T.allCases get() = this

fun Date.format(): String = SimpleDateFormat.getDateTimeInstance().format(this)

fun Double.formatWeight() = "${roundToInt()}g"
fun Double.formatCalorie() = "${roundToInt()}${EtenConst.CAL_PER_100G}"
fun Double.formatTotalCalories() = "${roundToInt()}cal"