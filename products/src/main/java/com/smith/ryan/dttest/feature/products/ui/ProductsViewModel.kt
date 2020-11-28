package com.smith.ryan.dttest.feature.products.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.smith.ryan.dttest.feature.core.connectivity.ConnectivityUtils
import com.smith.ryan.dttest.feature.products.io.ProductsRepository
import com.smith.ryan.dttest.feature.products.model.Product
import kotlinx.coroutines.launch

class ProductsViewModel(app: Application): AndroidViewModel(app) {

    private val productRepo: ProductsRepository by lazy { ProductsRepository() }

    /**
     *  MutableLiveData pair of a list of Products and Boolean to be modified only by the VM.
     *  UI will only have access to the public LiveData representation.
     *
     *  The Boolean should be set to false if the result is from a normal fetch or set to true
     *  if the result is from a swipe to refresh.
     */
    private val mutableProducts = MutableLiveData<Pair<List<Product>, Boolean>>()

    /**
     * LiveData representation of the current pair of a list of Products and Boolean provided by
     * our data source to be exposed to the UI to observe.
     *
     * The Boolean should be set to false if the result is from a normal fetch or set to true
     *  if the result is from a swipe to refresh.
     */
    val products: LiveData<Pair<List<Product>, Boolean>> = mutableProducts

    /**
     *  MutableLiveData selected Product to be modified only by the VM.
     *  UI will only have access to the public LiveData representation.
     */
    private val mutableSelectedProduct = MutableLiveData<Product?>()

    /**
     * LiveData representation of the current selected Product from our adapter to be
     * exposed to the UI to observe.
     */
    val selectedProduct = mutableSelectedProduct as LiveData<Product?>

    init {
        Log.d("ProductsViewModel", ".TAGS: init()")
        // Fetch initial product list
        fetchProducts()
    }

    /**
     * Fetches a list of products from the data source.
     *
     * The products LiveData will be updated with the result.
     */
    fun fetchProducts() {

        if (ConnectivityUtils.isOnline(getApplication())) {

            viewModelScope.launch {
                mutableProducts.postValue(productRepo.fetchProducts() to false)
            }
        }
    }

    /**
     * Should be called when a swipe to refresh action occurs.
     */
    fun onRefresh() {

        Log.d("ProductsViewModel", ".TAGS: onRefresh()")

        viewModelScope.launch {
            mutableProducts.postValue(productRepo.fetchProducts() to true)
        }
    }

    /**
     * Call when an item has been selected.
     */
    fun onProductClicked(product: Product) {
        mutableSelectedProduct.postValue(product)
    }

    fun clear() {
        mutableSelectedProduct.postValue(null)
    }
}