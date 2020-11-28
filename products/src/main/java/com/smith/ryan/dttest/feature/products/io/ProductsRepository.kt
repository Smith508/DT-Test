package com.smith.ryan.dttest.feature.products.io

import com.smith.ryan.dttest.feature.products.api.ProductTransceiver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProductsRepository {

    private val transceiver: ProductTransceiver by lazy { ProductTransceiver() }

    suspend fun fetchProducts() = withContext(Dispatchers.IO) {
        transceiver.fetchProducts()
    }
}