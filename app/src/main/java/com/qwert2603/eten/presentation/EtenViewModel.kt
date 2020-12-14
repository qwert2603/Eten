package com.qwert2603.eten.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qwert2603.eten.data.repo_impl.EtenRepoStub
import com.qwert2603.eten.domain.repo.EtenRepo
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch

class EtenViewModel(
    private val etenRepo: EtenRepo = EtenRepoStub
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

    val mealsUpdates = etenRepo.mealsUpdates()
        .shareIn(
            scope = viewModelScope,
            replay = 1,
            started = SharingStarted.WhileSubscribed(),
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
}