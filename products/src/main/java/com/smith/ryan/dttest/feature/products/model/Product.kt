package com.smith.ryan.dttest.feature.products.model

import android.os.Parcelable
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml
import kotlinx.android.parcel.Parcelize

@Xml(name = "ad")
@Parcelize
data class Product (

    @PropertyElement(name = "appId")
    var appId: String?,

    @PropertyElement(name = "averageRatingImageURL")
    var averageRatingImageUrl: String?,

    @PropertyElement(name = "bidRate")
    var bidRate: Double?,

    @PropertyElement(name = "billingTypeId")
    var billingTypeId: String?,

    @PropertyElement(name = "callToAction")
    var callToAction: String?,

    @PropertyElement(name = "campaignDisplayOrder")
    var campaignDisplayOrder: Int?,

    @PropertyElement(name = "campaignId")
    var campaignId: String?,

    @PropertyElement(name = "campaignTypeId")
    var campaignTypeId: String?,

    @PropertyElement(name = "categoryName")
    var categoryName: String?,

    @PropertyElement(name = "clickProxyURL")
    var clickProxyUrl: String?,

    @PropertyElement(name = "creativeId")
    var creativeId: String?,

    @PropertyElement(name = "homeScreen")
    var homeScreen: Boolean?,

    @PropertyElement(name = "impressionTrackingURL")
    var impressionTrackingUrl: String?,

    @PropertyElement(name = "isRandomPick")
    var isRandomPick: Boolean?,

    @PropertyElement(name = "numberOfRatings")
    var numberOfRatings: String?,

    @PropertyElement(name = "productDescription")
    var productDescription: String?,

    @PropertyElement(name = "productId")
    var productId: String?,

    @PropertyElement(name = "productName")
    var productName: String?,

    @PropertyElement(name = "productThumbnail")
    var productThumbnail: String?,

    @PropertyElement(name = "rating")
    var rating: Double?,

    @PropertyElement(name = "numberOfDownloads")
    var numberOfDownloads: String?,

    @PropertyElement(name = "tstiEligible")
    var tstiEligible: Boolean?,

    @PropertyElement(name = "stiEnabled")
    var stiEnabled: Boolean?
): Parcelable