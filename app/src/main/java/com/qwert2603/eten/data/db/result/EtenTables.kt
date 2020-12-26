package com.qwert2603.eten.data.db.result

import com.qwert2603.eten.data.db.table.ProductTable

class EtenTables(
    val productTables: List<ProductTable>,
    val dishTables: List<DishWithParts>,
    val mealTables: List<MealWithParts>,
)