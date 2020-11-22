package com.qwert2603.eten.util

import kotlin.math.roundToInt

fun Double.format(): String = roundToInt().toString() //String.format("%.2f", this)

val <T> T.allCases get() = this