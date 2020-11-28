package com.smith.ryan.dttest.feature.products.ui.recycler

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.smith.ryan.dttest.feature.products.R

class ProductsItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    val nameTextView: TextView = itemView.findViewById(R.id.list_item_product_name_text_view)
    val thumbnailImageView: ImageView = itemView.findViewById(R.id.list_item_product_thumbnail_image_view)
    val categoryTextView: TextView = itemView.findViewById(R.id.list_item_product_category_text_view)
    val ratingTextView: TextView = itemView.findViewById(R.id.list_item_product_rating_text_view)
    val ratingImageView: ImageView = itemView.findViewById(R.id.list_item_product_rating_image_view)
}