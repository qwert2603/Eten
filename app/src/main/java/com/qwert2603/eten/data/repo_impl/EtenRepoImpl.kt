package com.qwert2603.eten.data.repo_impl

import androidx.room.Room
import com.qwert2603.eten.E
import com.qwert2603.eten.EtenApplication
import com.qwert2603.eten.data.db.EtenDataBase
import com.qwert2603.eten.data.db.mapper.toProduct
import com.qwert2603.eten.data.db.mapper.toProductTable
import com.qwert2603.eten.domain.model.Product
import com.qwert2603.eten.domain.repo.EtenRepo
import com.qwert2603.eten.util.mapList
import kotlinx.coroutines.flow.Flow

// todo
object EtenRepoImpl : EtenRepo by EtenRepoStub {
    private val db = Room
        .databaseBuilder(EtenApplication.APP, EtenDataBase::class.java, "eten.db")
        .also { if (E.isDebug) it.fallbackToDestructiveMigration() }
        .build()

    private val productDao = db.productDao()

    override fun productsUpdates(): Flow<List<Product>> = productDao.observeProducts()
        .mapList { it.toProduct() }

    override suspend fun getProduct(uuid: String): Product? = productDao.getProduct(uuid)
        ?.toProduct()

    override suspend fun saveProduct(product: Product) {
        productDao.saveProduct(product.toProductTable())
    }

    override suspend fun removeProduct(uuid: String) {
        TODO("Not yet implemented")
    }
}