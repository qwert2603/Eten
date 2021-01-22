package com.qwert2603.eten.data.db.result

import com.qwert2603.eten.data.db.table.ProductTable

class EtenTables(
    val productTables: List<ProductTable>,
    val dishesWithParts: List<DishWithParts>,
    val mealsWithParts: List<MealWithParts>,
)