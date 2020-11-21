package com.qwert2603.eten.domain.model

import java.util.*

data class Product(
    val uuid: String = UUID.randomUUID().toString(),
    val name: String,
    val calorie: Double,
) {
    val caloriePer100g = calorie * 100.0
}