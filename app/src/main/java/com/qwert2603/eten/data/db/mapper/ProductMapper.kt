package com.qwert2603.eten.data.db.mapper

import com.qwert2603.eten.data.db.table.ProductTable
import com.qwert2603.eten.domain.model.Product

fun Product.toProductTable() = ProductTable(
    uuid = uuid,
    name = name,
    calorie = calorie,
)

fun ProductTable.toProduct() = Product(
    uuid = uuid,
    name = name,
    calorie = calorie,
)