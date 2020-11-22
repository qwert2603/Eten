package com.qwert2603.eten.stub

import com.qwert2603.eten.domain.model.*
import java.util.*

object Stub {
    private fun Date.minusMinutes(minutes: Int) = Date(time - minutes * 60 * 1000L)

    val productFish = Product(name = "Fish", calorie = 145.0)
    val productApple = Product(name = "Apple", calorie = 68.0)
    val productCheese = Product(name = "Cheese", calorie = 217.5)
    val productPorridge = Product(name = "Porridge", calorie = 88.4)
    val productPotato = Product(name = "Potato", calorie = 61.0)
    val productTomato = Product(name = "Tomato", calorie = 58.3)
    val productWater = Product(name = "Water", calorie = 0.2)
    val productChicken = Product(name = "Chicken", calorie = 170.7)
    val productDough = Product(name = "Dough", calorie = 134.2)
    val productBread = Product(name = "Bread", calorie = 128.5)

    val products = mutableListOf(
        productFish,
        productApple,
        productCheese,
        productPorridge,
    )

    val dishSoup = Dish(
        name = "Soup",
        time = Date().minusMinutes(123),
        products = listOf(
            WeightedProduct(productChicken, 200.0),
            WeightedProduct(productPotato, 150.0),
            WeightedProduct(productTomato, 80.0),
            WeightedProduct(productWater, 400.0),
        )
    )

    val dishPizza = Dish(
        name = "Pizza",
        time = Date().minusMinutes(584),
        products = listOf(
            WeightedProduct(productChicken, 120.0),
            WeightedProduct(productDough, 150.0),
            WeightedProduct(productCheese, 80.0),
            WeightedProduct(productTomato, 100.0),
        )
    )

    val dishes = mutableListOf(
        dishSoup,
        dishPizza,
    )

    val meals = mutableListOf(
        Meal(
            time = Date().minusMinutes(2), parts = listOf(
                MealDish(dishPizza, 140.0),
            )
        ),
        Meal(
            time = Date().minusMinutes(8), parts = listOf(
                MealProduct(WeightedProduct(productBread, 50.0)),
                MealDish(dishSoup, 150.0),
            )
        ),
        Meal(
            time = Date().minusMinutes(22), parts = listOf(
                MealProduct(WeightedProduct(productFish, 100.0)),
                MealProduct(WeightedProduct(productCheese, 122.8)),
            )
        ),
        Meal(
            time = Date().minusMinutes(200), parts = listOf(
                MealProduct(WeightedProduct(productApple, 203.2)),
                MealProduct(WeightedProduct(productCheese, 42.0)),
            )
        ),
        Meal(
            time = Date().minusMinutes(522), parts = listOf(
                MealProduct(WeightedProduct(productPorridge, 126.1)),
                MealProduct(WeightedProduct(productApple, 188.3)),
            )
        ),
        Meal(
            time = Date().minusMinutes(1527), parts = listOf(
                MealProduct(WeightedProduct(productFish, 55.0)),
                MealProduct(WeightedProduct(productPorridge, 102.4)),
            )
        ),
    )

    fun addProduct(product: Product) {
        products.add(0, product)
    }

    fun addDish(dish: Dish) {
        dishes.add(dish)
    }
}