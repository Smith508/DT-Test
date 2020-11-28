package com.smith.ryan.dttest.feature.products.ui.recycler

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.smith.ryan.dttest.feature.core.extensions.removeAllAmpersands
import com.smith.ryan.dttest.feature.products.R
import com.smith.ryan.dttest.feature.products.model.Product
import com.smith.ryan.dttest.feature.products.ui.ProductsViewModel

class ProductsAdapter(private val viewModel: ProductsViewModel): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var productList: MutableList<Product>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsItemViewHolder {

        return ProductsItemViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_product, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val hold = holder as ProductsItemViewHolder

        productList?.let {

            hold.apply {

                it[position].let { data ->
                    nameTextView.text = data.productName?.removeAllAmpersands()

                    Glide.with(holder.itemView.context)
                        .load(data.productThumbnail)
                        .optionalCenterCrop()
                        .into(holder.thumbnailImageView)

                    Glide.with(holder.itemView.context)
                        .load(data.averageRatingImageUrl)
                        .optionalCenterInside()
                        .into(holder.ratingImageView)

                    categoryTextView.text = data.categoryName
                    ratingTextView.text = data.rating.toString()

                    data
                }

                itemView.setOnClickListener{

                    productList?.let {
                        viewModel.onProductClicked(it[adapterPosition])
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int = productList?.size ?: 0

    fun update(products: List<Product>) {

        productList = products as MutableList<Product>
        notifyDataSetChanged()
    }

    fun clear() {
        productList?.clear()
    }
}