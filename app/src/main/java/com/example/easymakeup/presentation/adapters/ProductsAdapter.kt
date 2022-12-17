package com.example.easymakeup.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.easymakeup.databinding.ProductItemBinding
import com.example.easymakeup.domain.model.Product

class ProductsAdapter : RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder>() {

    private val callback = object : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, callback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder =
        ProductsViewHolder(
            ProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        val product = differ.currentList[position]
        holder.binding.tvProductTitle.text = product.title
        holder.binding.tvProductPrice.text = "$${product.price}"
        holder.binding.tvDiscount.text = "${product.price}%"
    }

    override fun getItemCount(): Int = differ.currentList.size

    inner class ProductsViewHolder(
        val binding: ProductItemBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                clickListener?.let {
                    it(binding.root, differ.currentList[adapterPosition])
                }
            }
        }
    }

    private var clickListener: ((View, Product) -> Unit)? = null

    fun setClickListener(clickListener: (View, Product) -> Unit) {
        this.clickListener = clickListener
    }
}