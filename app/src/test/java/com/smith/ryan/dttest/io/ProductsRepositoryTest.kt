package com.smith.ryan.dttest.io

import com.smith.ryan.dttest.feature.products.api.ProductTransceiver
import com.smith.ryan.dttest.feature.products.model.Product
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ProductsRepositoryTest {

    private lateinit var transceiver: ProductTransceiver
    private lateinit var products: List<Product>

    @Before
    fun setup() {
        transceiver =
            ProductTransceiver()
        fetchProducts()
    }

    private fun fetchProducts() {
        products = runBlocking { transceiver.fetchProducts() }
    }

    @Test
    fun checkEmpty() {
        assert(products.isNotEmpty())
    }

    @Test
    fun checkProductCount() {
        assert(products.size == 10)
    }
}