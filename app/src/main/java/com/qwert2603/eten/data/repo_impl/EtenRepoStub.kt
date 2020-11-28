package com.qwert2603.eten.data.repo_impl

import com.qwert2603.eten.domain.model.*
import com.qwert2603.eten.domain.repo.EtenRepo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import java.util.*

@ExperimentalCoroutinesApi
class EtenRepoStub : EtenRepo {

    override fun productsUpdates(): Flow<List<Product>> =
        products.map { it.values.sortedBy { product -> product.name } }

    override suspend fun saveProduct(product: Product) {
        products.value = products.value + (product.uuid to product)
    }

    override suspend fun removeProduct(uuid: String) {
        products.value = products.value - uuid
    }

    override fun dishesUpdates(): Flow<List<Dish>> =
        dishes.map { it.values.sortedByDescending { dish -> dish.time } }

    override suspend fun saveDish(dish: Dish) {
        dishes.value = dishes.value + (dish.uuid to dish)
    }

    override suspend fun removeDish(uuid: String) {
        dishes.value = dishes.value - uuid
    }

    override fun mealsUpdates(): Flow<List<Meal>> =
        meals.map { it.values.sortedByDescending { meal -> meal.time } }

    override suspend fun saveMeal(meal: Meal) {
        meals.value = meals.value + (meal.uuid to meal)
    }

    override suspend fun removeMeal(uuid: String) {
        meals.value = meals.value - uuid
    }

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

    val dishSoup = Dish(
        name = "Soup",
        time = Date().minusMinutes(123),
        partsList = PartsList(
            WeightedMealPart(productChicken, 200.0),
            WeightedMealPart(productPotato, 150.0),
            WeightedMealPart(productTomato, 80.0),
            WeightedMealPart(productWater, 400.0),
        )
    )

    val dishPizza = Dish(
        name = "Pizza",
        time = Date().minusMinutes(584),
        partsList = PartsList(
            WeightedMealPart(productChicken, 120.0),
            WeightedMealPart(productDough, 150.0),
            WeightedMealPart(productCheese, 80.0),
            WeightedMealPart(productTomato, 100.0),
        )
    )

    private val products = MutableStateFlow(listOf(
        productFish,
        productApple,
        productCheese,
        productPorridge,
        productPotato,
        productTomato,
        productWater,
        productChicken,
        productDough,
        productBread,
    ).associateBy { it.uuid })

    private val dishes = MutableStateFlow(listOf(
        dishSoup,
        dishPizza,
    ).associateBy { it.uuid })

    private val meals = MutableStateFlow(listOf(
        Meal(
            time = Date().minusMinutes(2), partsList = PartsList(
                WeightedMealPart(dishPizza, 140.0),
            )
        ),
        Meal(
            time = Date().minusMinutes(8), partsList = PartsList(
                WeightedMealPart(productBread, 50.0),
                WeightedMealPart(dishSoup, 150.0),
            )
        ),
        Meal(
            time = Date().minusMinutes(22), partsList = PartsList(
                WeightedMealPart(productFish, 100.0),
                WeightedMealPart(productCheese, 122.8),
            )
        ),
        Meal(
            time = Date().minusMinutes(200), partsList = PartsList(
                WeightedMealPart(productApple, 203.2),
                WeightedMealPart(productCheese, 42.0),
            )
        ),
        Meal(
            time = Date().minusMinutes(522), partsList = PartsList(
                WeightedMealPart(productPorridge, 126.1),
                WeightedMealPart(productApple, 188.3),
            )
        ),
        Meal(
            time = Date().minusMinutes(1527), partsList = PartsList(
                WeightedMealPart(productFish, 55.0),
                WeightedMealPart(productPorridge, 102.4),
            )
        ),
    ).associateBy { it.uuid })

    private fun Date.minusMinutes(minutes: Int) = Date(time - minutes * 60 * 1000L)
}