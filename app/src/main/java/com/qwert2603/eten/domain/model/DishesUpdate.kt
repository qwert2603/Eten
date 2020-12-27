package com.qwert2603.eten.domain.model

data class DishesUpdate(
    val dishes: List<Dish>,
    val removableDishesUuids: Set<String>,
)