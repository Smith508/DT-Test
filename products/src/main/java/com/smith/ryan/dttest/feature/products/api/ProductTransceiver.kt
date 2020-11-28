package com.smith.ryan.dttest.feature.products.api

import com.smith.ryan.dttest.feature.products.model.Product
import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit

/**
 * Responsible for creating and handling our ProductApi requests.
 */
class ProductTransceiver {

    private val api: ProductApi = Retrofit.Builder()
        .baseUrl(ProductApi.baseUrl)
        .addConverterFactory(TikXmlConverterFactory
            .create(TikXml.Builder()
                .exceptionOnUnreadXml(false)
                .build()))
        .client(OkHttpClient.Builder()
            .build())
        .build()
        .create(ProductApi::class.java)

    suspend fun fetchProducts(): List<Product> = api.fetchProducts().products
}