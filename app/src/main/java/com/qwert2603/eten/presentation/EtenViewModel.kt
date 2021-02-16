package com.qwert2603.eten.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qwert2603.eten.data.repo_impl.EtenRepoImpl
import com.qwert2603.eten.domain.meals.MealsInteractor
import com.qwert2603.eten.domain.model.Dish
import com.qwert2603.eten.domain.model.Meal
import com.qwert2603.eten.domain.model.Product
import com.qwert2603.eten.domain.repo.EtenRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch

class EtenViewModel(
    private val etenRepo: EtenRepo = EtenRepoImpl,
    private val mealsInteractor: MealsInteractor = MealsInteractor(),
) : ViewModel() {

    val productsSearch = MutableStateFlow("")
    val dishesSearch = MutableStateFlow("")

    val productsUpdates = etenRepo.productsUpdates()
        .combine(productsSearch) { productsUpdate, search ->
            val filteredProducts = productsUpdate.products.filter {
                it.name.contains(search, ignoreCase = true)
            }
            productsUpdate.copy(products = filteredProducts)
        }
        .shareIn(
            scope = viewModelScope,
            replay = 1,
            started = SharingStarted.WhileSubscribed(),
        )

    val dishesUpdates = etenRepo.dishesUpdates()
        .combine(dishesSearch) { dishesUpdate, search ->
            val filteredDishes = dishesUpdate.dishes.filter {
                it.name.contains(search, ignoreCase = true)
            }
            dishesUpdate.copy(dishes = filteredDishes)
        }
        .shareIn(
            scope = viewModelScope,
            replay = 1,
            started = SharingStarted.WhileSubscribed(),
        )

    val etenDaysUpdates = mealsInteractor.etenDaysUpdates()
        .shareIn(
            scope = viewModelScope,
            replay = 1,
            started = SharingStarted.Lazily,
        )

    fun deleteProduct(uuid: String) {
        viewModelScope.launch {
            etenRepo.removeProduct(uuid)
        }
    }

    fun deleteDish(uuid: String) {
        viewModelScope.launch {
            etenRepo.removeDish(uuid)
        }
    }

    fun deleteMeal(uuid: String) {
        viewModelScope.launch {
            etenRepo.removeMeal(uuid)
        }
    }

    fun onRestoreProductClick(product: Product) {
        viewModelScope.launch {
            etenRepo.saveProduct(product)
        }
    }

    fun onRestoreDishClick(dish: Dish) {
        viewModelScope.launch {
            etenRepo.saveDish(dish)
        }
    }

    fun onRestoreMealClick(meal: Meal) {
        viewModelScope.launch {
            etenRepo.saveMeal(meal)
        }
    }

    fun onSearchProductChange(search: String) {
        productsSearch.value = search
    }

    fun onSearchDishChange(search: String) {
        dishesSearch.value = search
    }
}