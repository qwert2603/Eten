package com.qwert2603.eten.data.db.result

import com.qwert2603.eten.data.db.table.DishTable
import com.qwert2603.eten.data.db.table.MealPartTable
import com.qwert2603.eten.data.db.table.MealTable
import com.qwert2603.eten.data.db.table.ProductTable

class Dump(
    val productTables: List<ProductTable>,
    val mealPartTables: List<MealPartTable>,
    val dishTables: List<DishTable>,
    val mealTables: List<MealTable>,
)