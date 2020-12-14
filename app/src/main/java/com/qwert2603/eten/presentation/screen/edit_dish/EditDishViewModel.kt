package com.qwert2603.eten.presentation.screen.edit_dish

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qwert2603.eten.data.repo_impl.EtenRepoStub
import com.qwert2603.eten.domain.model.Dish
import com.qwert2603.eten.domain.model.Product
import com.qwert2603.eten.domain.repo.EtenRepo
import com.qwert2603.eten.presentation.edit_meal_parts.toCreatingMealPart
import com.qwert2603.eten.util.randomUUID
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import timber.log.Timber

class EditDishViewModel(
    private val etenRepo: EtenRepo = EtenRepoStub,
) : ViewModel() {

    val creatingDish = MutableStateFlow<CreatingDish?>(null)

    fun loadDish(uuid: String?) {
        if (creatingDish.value != null) return
        Timber.d("loadDish $uuid")
        viewModelScope.launch {
            creatingDish.value = uuid
                ?.let { etenRepo.getDish(it) }
                ?.let { dish ->
                    CreatingDish(
                        uuid = dish.uuid,
                        name = dish.name,
                        time = dish.time,
                        parts = dish.partsList.map { it.toCreatingMealPart() })
                }
                ?: CreatingDish(randomUUID(), "", null, emptyList())
        }
    }

    fun onDishChange(creatingDish: CreatingDish) {
        this.creatingDish.value = creatingDish
    }

    fun saveDish() = viewModelScope.launch {
        val value = creatingDish.value ?: return@launch
        etenRepo.saveDish(value.toDish())
    }

    suspend fun searchProducts(query: String): List<Product> = etenRepo.productsUpdates()
        .first()
        .filter { it.name.contains(query, ignoreCase = true) }

    suspend fun searchDishes(query: String): List<Dish> = etenRepo.dishesUpdates()
        .first()
        .filter {
            it.name.contains(query, ignoreCase = true)
                    && it.uuid != creatingDish.value?.uuid // can't add dish to itself.
                    && !it.isWithEditingDish() // can't add dish if it contains editing dish.
        }

    private fun Dish.isWithEditingDish(): Boolean = partsList.any {
        it.mealPart is Dish
                && it.mealPart.uuid == creatingDish.value?.uuid
                && !it.mealPart.isWithEditingDish()
    }
}