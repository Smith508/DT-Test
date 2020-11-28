package com.smith.ryan.dttest.feature.products.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.button.MaterialButton
import com.smith.ryan.dttest.feature.core.connectivity.ConnectivityUtils
import com.smith.ryan.dttest.feature.core.extensions.removeAllAmpersands
import com.smith.ryan.dttest.feature.coreui.extensions.showLongSnackbar
import com.smith.ryan.dttest.feature.coreui.fragment.BaseFragment
import com.smith.ryan.dttest.feature.products.R
import com.smith.ryan.dttest.feature.products.model.Product
import com.smith.ryan.dttest.feature.products.ui.ProductCoordinator

class ProductDetailFragment: BaseFragment() {

    private val args: ProductDetailFragmentArgs by navArgs()
    private val coordinator: ProductCoordinator by lazy { ProductCoordinator() }

    private lateinit var product: Product
    private lateinit var ctaButton: MaterialButton

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        product = args.product

        return inflater.inflate(R.layout.fragment_product_details, container, false).apply {

            Glide.with(context)
                .load(product.productThumbnail)
                .optionalCenterCrop()
                .into(findViewById(R.id.product_details_thumbnail_image_view))
            
            findViewById<TextView>(R.id.product_details_name_text_view).text = product.productName?.removeAllAmpersands()
            findViewById<TextView>(R.id.product_details_category_text_view).text = product.categoryName?.removeAllAmpersands()
            findViewById<TextView>(R.id.product_details_rating_text_view).text = product.rating.toString()

            Glide.with(context)
                .load(product.averageRatingImageUrl)
                .optionalCenterInside()
                .into(findViewById(R.id.product_details_rating_image_view))

            findViewById<TextView>(R.id.product_details_rating_number_text_view).text = product.numberOfRatings
            findViewById<TextView>(R.id.product_details_downloads_number_text_view).text = product.numberOfDownloads
            findViewById<TextView>(R.id.product_details_product_description_text_view).text = product.productDescription?.removeAllAmpersands()

            findViewById<TextView>(R.id.product_details_app_id_text_view).text = product.appId
            findViewById<TextView>(R.id.product_details_bid_rate_text_view).text = product.bidRate.toString()
            findViewById<TextView>(R.id.product_details_home_screen_text_view).text = product.homeScreen.toString().capitalize()
            findViewById<TextView>(R.id.product_details_random_pick_text_view).text = product.isRandomPick.toString().capitalize()
            findViewById<TextView>(R.id.product_details_tsti_text_view).text = product.tstiEligible.toString().capitalize()
            findViewById<TextView>(R.id.product_details_sti_text_view).text = product.stiEnabled.toString().capitalize()

            findViewById<TextView>(R.id.product_details_campaign_display_order_text_view).text = product.campaignDisplayOrder.toString()
            findViewById<TextView>(R.id.product_details_campaign_id_text_view).text = product.campaignId
            findViewById<TextView>(R.id.product_details_campaign_type_id_text_view).text = product.campaignTypeId
            findViewById<TextView>(R.id.product_details_creative_id_text_view).text = product.creativeId
            findViewById<TextView>(R.id.product_details_billing_type_id_text_view).text = product.billingTypeId
            findViewById<TextView>(R.id.product_details_product_id_text_view).text = product.productId
            findViewById<TextView>(R.id.product_details_impression_tracking_text_view).text = product.impressionTrackingUrl?.removeAllAmpersands()

            ctaButton = findViewById<MaterialButton>(R.id.product_details_cta_button).apply {

                text = product.callToAction

                setOnClickListener { view ->

                    if (ConnectivityUtils.isOnline(context)) {

                        coordinator.viewPlayStore(this@ProductDetailFragment, product.clickProxyUrl
                            , product.appId, STORE_REQUEST_CODE)

                    } else {

                        this@ProductDetailFragment.view?.let {
                            "Connect to the internet in order to '${product.callToAction}'".showLongSnackbar(it)
                        }
                    }
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onConnectivityStatus(isConnected: Boolean) {
    }

    private companion object {
        const val STORE_REQUEST_CODE = 0x1024
    }
}