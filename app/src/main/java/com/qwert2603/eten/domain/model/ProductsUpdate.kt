package com.qwert2603.eten.domain.model

data class ProductsUpdate(
    val products: List<Product>,
    val removableProductsUuids: Set<String>,
)