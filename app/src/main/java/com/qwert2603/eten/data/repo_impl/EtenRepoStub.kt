package com.qwert2603.eten.data.repo_impl

import com.qwert2603.eten.domain.model.*
import com.qwert2603.eten.domain.repo.EtenRepo
import com.qwert2603.eten.util.randomUUID
import com.qwert2603.eten.util.timeNow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.*

// todo: remove stub repos from constructors
object EtenRepoStub : EtenRepo {

    override fun productsUpdates(): Flow<ProductsUpdate> = products.map {
        ProductsUpdate(
            products = it.values.sortedBy { product -> product.name },
            removableProductsUuids = emptySet(),
        )
    }

    override suspend fun getProduct(uuid: String): Product? = products.value[uuid]

    override suspend fun saveProduct(product: Product) {
        products.value = products.value + (product.uuid to product)
    }

    override suspend fun removeProduct(uuid: String) {
        products.value = products.value - uuid
    }

    override suspend fun getDish(uuid: String): Dish? = dishes.value[uuid]

    override fun dishesUpdates(): Flow<DishesUpdate> =
        dishes.map {
            DishesUpdate(
                dishes = it.values.sortedByDescending { dish -> dish.time },
                removableDishesUuids = emptySet(),
            )
        }

    override suspend fun saveDish(dish: Dish) {
        dishes.value = dishes.value + (dish.uuid to dish)
    }

    override suspend fun removeDish(uuid: String) {
        dishes.value = dishes.value - uuid
    }

    override fun mealsUpdates(): Flow<List<Meal>> =
        meals.map { it.values.sortedByDescending { meal -> meal.time } }

    override suspend fun getMeal(uuid: String): Meal? = meals.value[uuid]

    override suspend fun saveMeal(meal: Meal) {
        meals.value = meals.value + (meal.uuid to meal)
    }

    override suspend fun removeMeal(uuid: String) {
        meals.value = meals.value - uuid
    }

    private val productFish = Product(uuid = randomUUID(), name = "Fish", calorie = 1.45)
    private val productApple = Product(uuid = randomUUID(), name = "Apple", calorie = 0.68)
    private val productCheese = Product(uuid = randomUUID(), name = "Cheese", calorie = 2.175)
    private val productPorridge = Product(uuid = randomUUID(), name = "Porridge", calorie = 0.884)
    private val productPotato = Product(uuid = randomUUID(), name = "Potato", calorie = 0.61)
    private val productTomato = Product(uuid = randomUUID(), name = "Tomato", calorie = 0.583)
    private val productWater = Product(uuid = randomUUID(), name = "Water", calorie = 0.002)
    private val productChicken = Product(uuid = randomUUID(), name = "Chicken", calorie = 1.70)
    private val productDough = Product(uuid = randomUUID(), name = "Dough", calorie = 1.342)
    private val productBread = Product(uuid = randomUUID(), name = "Bread", calorie = 1.285)

    private val dishSoup = Dish(
        uuid = randomUUID(),
        name = "Soup",
        time = minusMinutes(123),
        partsList = listOf(
            WeightedProduct(uuid = randomUUID(), product = productChicken, weight = 200.0),
            WeightedProduct(uuid = randomUUID(), product = productPotato, weight = 150.0),
            WeightedProduct(uuid = randomUUID(), product = productTomato, weight = 80.0),
            WeightedProduct(uuid = randomUUID(), product = productWater, weight = 400.0),
        )
    )

    private val dishPizza = Dish(
        uuid = randomUUID(),
        name = "Pizza",
        time = minusMinutes(584),
        partsList = listOf(
            WeightedProduct(uuid = randomUUID(), product = productChicken, weight = 120.0),
            WeightedProduct(uuid = randomUUID(), product = productDough, weight = 150.0),
            WeightedProduct(uuid = randomUUID(), product = productCheese, weight = 80.0),
            WeightedProduct(uuid = randomUUID(), product = productTomato, weight = 100.0),
            WeightedDish(uuid = randomUUID(), dish = dishSoup, weight = 54.0),
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
            uuid = randomUUID(),
            name = null,
            time = minusMinutes(2), partsList = listOf(
                WeightedDish(uuid = randomUUID(), dish = dishPizza, weight = 140.0),
            )
        ),
        Meal(
            uuid = randomUUID(),
            name = "Ontbijt",
            time = minusMinutes(8), partsList = listOf(
                WeightedProduct(uuid = randomUUID(), product = productBread, weight = 50.0),
                WeightedDish(uuid = randomUUID(), dish = dishSoup, weight = 150.0),
            )
        ),
        Meal(
            uuid = randomUUID(),
            name = "Middageten",
            time = minusMinutes(22), partsList = listOf(
                WeightedProduct(uuid = randomUUID(), product = productFish, weight = 100.0),
                WeightedProduct(uuid = randomUUID(), product = productCheese, weight = 122.8),
            )
        ),
        Meal(
            uuid = randomUUID(),
            name = "Avondeten",
            time = minusMinutes(200), partsList = listOf(
                WeightedProduct(uuid = randomUUID(), product = productApple, weight = 203.2),
                WeightedProduct(uuid = randomUUID(), product = productCheese, weight = 42.0),
            )
        ),
        Meal(
            uuid = randomUUID(),
            name = null,
            time = minusMinutes(522), partsList = listOf(
                WeightedProduct(uuid = randomUUID(), product = productPorridge, weight = 126.1),
                WeightedProduct(uuid = randomUUID(), product = productApple, weight = 188.3),
            )
        ),
        Meal(
            uuid = randomUUID(),
            name = null,
            time = minusMinutes(1527), partsList = listOf(
                WeightedProduct(uuid = randomUUID(), product = productFish, weight = 55.0),
                WeightedProduct(uuid = randomUUID(), product = productPorridge, weight = 102.4),
            )
        ),
    ).associateBy { it.uuid })

    // todo: make better
    private fun minusMinutes(minutes: Int) = timeNow().toInstant(TimeZone.currentSystemDefault())
        .plus(-minutes, DateTimeUnit.MINUTE)
        .toLocalDateTime(TimeZone.currentSystemDefault())
}