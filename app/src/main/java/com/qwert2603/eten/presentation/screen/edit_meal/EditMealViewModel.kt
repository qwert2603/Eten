package com.qwert2603.eten.presentation.screen.edit_meal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qwert2603.eten.data.repo_impl.EtenRepoImpl
import com.qwert2603.eten.domain.model.Dish
import com.qwert2603.eten.domain.model.Product
import com.qwert2603.eten.domain.repo.EtenRepo
import com.qwert2603.eten.presentation.edit_meal_parts.toCreatingMealPart
import com.qwert2603.eten.util.randomUUID
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class EditMealViewModel(
    private val etenRepo: EtenRepo = EtenRepoImpl,
) : ViewModel() {

    val creatingMeal = MutableStateFlow<CreatingMeal?>(null)

    fun loadMeal(uuid: String?) {
        if (creatingMeal.value != null) return
        Timber.d("loadMeal $uuid")
        viewModelScope.launch {
            creatingMeal.value = uuid
                ?.let { etenRepo.getMeal(it) }
                ?.let { meal ->
                    CreatingMeal(
                        uuid = meal.uuid,
                        time = meal.time,
                        parts = meal.partsList.map { it.toCreatingMealPart() })
                }
                ?: CreatingMeal(randomUUID(), null, emptyList())
        }
    }

    fun onMealChange(creatingMeal: CreatingMeal) {
        this.creatingMeal.value = creatingMeal
    }

    fun saveMeal() = viewModelScope.launch {
        val value = creatingMeal.value ?: return@launch
        withContext(NonCancellable) {
            etenRepo.saveMeal(value.toMeal())
        }
    }

    suspend fun searchProducts(query: String): List<Product> = etenRepo.productsUpdates()
        .first()
        .filter { it.name.contains(query, ignoreCase = true) }

    suspend fun searchDishes(query: String): List<Dish> = etenRepo.dishesUpdates()
        .first()
        .filter { it.name.contains(query, ignoreCase = true) }
}