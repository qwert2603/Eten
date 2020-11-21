package com.qwert2603.eten.stub

import com.qwert2603.eten.domain.model.Meal
import com.qwert2603.eten.domain.model.Product
import java.util.*

object Stub {
    val productFish = Product(name = "Fish", calorie = 145.0)
    val productApple = Product(name = "Apple", calorie = 68.0)
    val productCheese = Product(name = "Cheese", calorie = 217.5)
    val productPorridge = Product(name = "Porridge", calorie = 88.4)

    val products = mutableListOf(
        productFish,
        productApple,
        productCheese,
        productPorridge,
    )

    fun Date.minusMinutes(minutes: Int) = Date(time - minutes * 60 * 1000L)

    val meals = mutableListOf(
        Meal(
            time = Date().minusMinutes(2), products = listOf(
                Meal.WeightedProduct(productFish, 100.0),
                Meal.WeightedProduct(productCheese, 122.8),
            )
        ),
        Meal(
            time = Date().minusMinutes(200), products = listOf(
                Meal.WeightedProduct(productApple, 203.2),
                Meal.WeightedProduct(productCheese, 42.0),
            )
        ),
        Meal(
            time = Date().minusMinutes(522), products = listOf(
                Meal.WeightedProduct(productPorridge, 126.1),
                Meal.WeightedProduct(productApple, 188.3),
            )
        ),
        Meal(
            time = Date().minusMinutes(1527), products = listOf(
                Meal.WeightedProduct(productFish, 55.0),
                Meal.WeightedProduct(productPorridge, 102.4),
            )
        ),
    )

    fun addProduct(product: Product) {
        products.add(0, product)
    }
}