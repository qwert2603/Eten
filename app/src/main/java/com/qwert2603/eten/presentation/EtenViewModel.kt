package com.qwert2603.eten.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qwert2603.eten.data.repo_impl.EtenRepoImpl
import com.qwert2603.eten.domain.meals.MealsInteractor
import com.qwert2603.eten.domain.model.Dish
import com.qwert2603.eten.domain.model.Meal
import com.qwert2603.eten.domain.model.Product
import com.qwert2603.eten.domain.repo.EtenRepo
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch

class EtenViewModel(
    private val etenRepo: EtenRepo = EtenRepoImpl,
    private val mealsInteractor: MealsInteractor = MealsInteractor(),
) : ViewModel() {

    val productsUpdates = etenRepo.productsUpdates()
        .shareIn(
            scope = viewModelScope,
            replay = 1,
            started = SharingStarted.WhileSubscribed(),
        )

    val dishesUpdates = etenRepo.dishesUpdates()
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
}