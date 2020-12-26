package com.qwert2603.eten.data.db

import com.qwert2603.eten.domain.model.Dish
import com.qwert2603.eten.domain.model.Meal
import com.qwert2603.eten.domain.model.Product

class EtenState(
    val products: Map<String, Product>,
    val dishes: Map<String, Dish>,
    val meals: Map<String, Meal>,
)