package com.smith.ryan.dttest.feature.products.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar
import com.smith.ryan.dttest.feature.coreui.extensions.showIndefiniteSnackbar
import com.smith.ryan.dttest.feature.coreui.fragment.BaseFragment
import com.smith.ryan.dttest.feature.products.R
import com.smith.ryan.dttest.feature.products.model.Product
import com.smith.ryan.dttest.feature.products.ui.recycler.ProductsAdapter

class ProductsFragment: BaseFragment() {

    private val viewModel: ProductsViewModel by activityViewModels()

    private val coordinator: ProductCoordinator by lazy { ProductCoordinator() }
    private lateinit var productRecycler: RecyclerView
    private lateinit var productAdapter: ProductsAdapter
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private var snackbar: Snackbar? = null

    private val refreshListener: SwipeRefreshLayout.OnRefreshListener = SwipeRefreshLayout.OnRefreshListener {
        viewModel.onRefresh()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_products, container, false).apply {

            productAdapter = ProductsAdapter(viewModel)

            productRecycler = findViewById(R.id.products_recycler_view)
            productRecycler.apply {

                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(activity)
                adapter = productAdapter
            }

            swipeRefreshLayout = findViewById(R.id.products_swipe_refresh_layout)
            swipeRefreshLayout.setOnRefreshListener(refreshListener)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Observe product list
        viewModel.products.observe(viewLifecycleOwner, Observer {

            if (it.first.isNotEmpty()) {

                // Populate the list of products
                showProducts(it.first, it.second)

            } else {
                Log.d("ProductFragment", ".TAGS: observe products: EMPTY")
            }
        })

        // Observe item selection from the adapter level
        viewModel.selectedProduct.observe(viewLifecycleOwner, Observer { product ->

            product?.let {

                view.let { view ->

                    // Navigate to Product Detail screen
                    coordinator.goToProductDetail(view, it)
                    snackbar?.dismiss()
                    snackbar = null
                    viewModel.clear()
                }
            }
        })
    }

    private fun showProducts(products: List<Product>, isRefresh: Boolean) {

        view?.let {

            if (isRefresh)  {

                productAdapter.clear()
                productAdapter.update(products)
                swipeRefreshLayout.isRefreshing = false

            } else {

                productAdapter.update(products)
            }
        }
    }

    override fun onConnectivityStatus(isConnected: Boolean) {

        view?.let {

            if (isConnected ) {

                snackbar?.let { bar -> bar.dismiss() }
                snackbar = null
                swipeRefreshLayout.setOnRefreshListener(refreshListener)
                swipeRefreshLayout.isEnabled = true
                if (productAdapter.itemCount == 0) viewModel.fetchProducts()

            } else {

                swipeRefreshLayout.setOnRefreshListener(null)
                swipeRefreshLayout.isEnabled = false
                if (productAdapter.itemCount == 0) {
                    snackbar = "Connect to the internet in order to read products".showIndefiniteSnackbar(it)
                }
            }
        }
    }
}