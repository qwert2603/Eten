package com.qwert2603.eten.presentation.screen.edit_product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qwert2603.eten.data.repo_impl.EtenRepoImpl
import com.qwert2603.eten.domain.repo.EtenRepo
import com.qwert2603.eten.util.randomUUID
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class EditProductViewModel(
    private val etenRepo: EtenRepo = EtenRepoImpl,
) : ViewModel() {

    val creatingProduct = MutableStateFlow<CreatingProduct?>(null)

    init {
        Timber.d("EditProductViewModel created")
    }

    // todo: inject param to constructor in all viewModels
    fun loadProduct(editProductParam: EditProductParam) {
        if (creatingProduct.value != null) return
        Timber.d("loadProduct $editProductParam")
        viewModelScope.launch {
            val newProduct by lazy { CreatingProduct(randomUUID(), "", 0) }
            creatingProduct.value = when (editProductParam) {
                EditProductParam.NewProduct -> {
                    newProduct
                }
                is EditProductParam.EditProduct -> {
                    etenRepo.getProduct(editProductParam.productUuid)
                        ?.let { CreatingProduct(it.uuid, it.name, it.caloriePer100g.toInt()) }
                        ?: newProduct
                }
                is EditProductParam.FromDish -> {
                    etenRepo.getDish(editProductParam.dishUuid)
                        ?.let { CreatingProduct(randomUUID(), it.name, it.caloriePer100g.toInt()) }
                        ?: newProduct
                }
            }
        }
    }

    fun onProductChange(creatingProduct: CreatingProduct) {
        this.creatingProduct.value = creatingProduct
    }

    fun saveProduct() = viewModelScope.launch {
        val value = creatingProduct.value ?: return@launch
        // todo: may be better.
        withContext(NonCancellable) {
            etenRepo.saveProduct(value.toProduct())
        }
    }

    override fun onCleared() {
        Timber.d("EditProductViewModel onCleared")
        super.onCleared()
    }
}