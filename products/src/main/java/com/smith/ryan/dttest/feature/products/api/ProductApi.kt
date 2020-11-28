package com.smith.ryan.dttest.feature.products.api

import com.smith.ryan.dttest.feature.products.model.ProductRoot
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * API interface responsible for Product related network requests.
 *
 * @see "http://ads.appia.com/getAds?id=236&password=OVUJ1DJN&siteId=10777&deviceId=4230&sessionId=techtestsession&totalCampaignsRequested=10"
 */
internal interface ProductApi {

    @GET("getAds")
    suspend fun fetchProducts(@Query(PATH_ID) id: String = "236",
                              @Query(PATH_PASSWORD) password: String = "OVUJ1DJN",
                              @Query(PATH_SITE_ID) siteId: String = "10777",
                              @Query(PATH_DEVICE_ID) deviceId: String = "4230",
                              @Query(PATH_SESSION_ID) sessionId: String = "techtestsession",
                              @Query(PATH_TOTAL_CAMPAIGNS_REQUESTED) totalCampaignsRequested: Int = 10,
                              @Query(PATH_INAME) iName: String = "Smith"): ProductRoot

    companion object {
        const val baseUrl = "http://ads.appia.com/"

        private const val PATH_ID = "id"
        private const val PATH_PASSWORD = "password"
        private const val PATH_SITE_ID = "siteId"
        private const val PATH_DEVICE_ID = "deviceId"
        private const val PATH_SESSION_ID = "sessionId"
        private const val PATH_TOTAL_CAMPAIGNS_REQUESTED = "totalCampaignsRequested"
        private const val PATH_INAME = "Iname"
    }
}