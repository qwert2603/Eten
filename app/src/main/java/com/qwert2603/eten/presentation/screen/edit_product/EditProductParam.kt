package com.qwert2603.eten.presentation.screen.edit_product

sealed class EditProductParam {
    object NewProduct : EditProductParam()
    data class EditProduct(val productUuid: String) : EditProductParam()
    data class FromDish(val dishUuid: String) : EditProductParam()
}