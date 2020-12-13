package com.qwert2603.eten.presentation.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qwert2603.eten.data.repo_impl.EtenRepoStub
import com.qwert2603.eten.domain.repo.EtenRepo
import com.qwert2603.eten.util.randomUUID
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class EditProductViewModel(
    private val etenRepo: EtenRepo = EtenRepoStub,
) : ViewModel() {

    val creatingProduct = MutableStateFlow<CreatingProduct?>(null)

    init {
        Timber.d("EditProductViewModel created")
    }

    // todo: inject uuid to constructor in all viewModels
    fun loadProduct(uuid: String?) {
        if (creatingProduct.value != null) return
        Timber.d("loadProduct $uuid")
        viewModelScope.launch {
            creatingProduct.value = uuid
                ?.let { etenRepo.getProduct(it) }
                ?.let { CreatingProduct(it.uuid, it.name, it.caloriePer100g.toInt()) }
                ?: CreatingProduct(randomUUID(), "", 0)
        }
    }

    fun onProductChange(creatingProduct: CreatingProduct) {
        this.creatingProduct.value = creatingProduct
    }

    fun saveProduct() = viewModelScope.launch {
        val value = creatingProduct.value ?: return@launch
        etenRepo.saveProduct(value.toProduct())
    }

    override fun onCleared() {
        Timber.d("EditProductViewModel onCleared")
        super.onCleared()
    }
}