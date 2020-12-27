package com.qwert2603.eten.domain.model

class EtenState(
    val products: Map<String, Product>,
    val dishes: Map<String, Dish>,
    val meals: Map<String, Meal>,
    val removableProductsUuids: Set<String>,
    val removableDishesUuids: Set<String>,
)