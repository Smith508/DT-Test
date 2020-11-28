package com.smith.ryan.dttest.feature.products.ui

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.smith.ryan.dttest.feature.core.extensions.removeAllAmpersands
import com.smith.ryan.dttest.feature.products.R
import com.smith.ryan.dttest.feature.products.model.Product

/**
 * Responsible for navigating through the :product feature.
 */
class ProductCoordinator {

    fun goToProductDetail(view: View, product: Product) {

        Navigation.findNavController(view).navigate(R.id.action_to_product_details
            , bundleOf("product" to product))
    }

    fun viewPlayStore(fragment: Fragment, url: String?, productAppId: String? = null, requestCode: Int) {

        // Navigate to Play Store
        try {

            url?.let {

                fragment.startActivityForResult(Intent(Intent.ACTION_VIEW, Uri.parse(it.removeAllAmpersands())), requestCode)

            } ?: run {

                productAppId?.let {
                    fragment.startActivityForResult(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$it")), requestCode)
                }
            }

        } catch (e: ActivityNotFoundException) {

            Log.e("ProductCoordinator", "viewPlayStore: ", e)
        }
    }
}