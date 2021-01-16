package com.qwert2603.eten.data.db.result

import com.qwert2603.eten.data.db.table.*

class Dump(
    val productTables: List<ProductTable>,
    val mealPartTables: List<MealPartTable>,
    val rawCaloriesTables: List<RawCaloriesTable>,
    val dishTables: List<DishTable>,
    val mealTables: List<MealTable>,
)