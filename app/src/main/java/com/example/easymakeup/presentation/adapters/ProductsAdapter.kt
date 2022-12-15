package com.example.easymakeup.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.easymakeup.databinding.ProductItemBinding
import com.example.easymakeup.domain.model.Product

class ProductsAdapter(
    private val products: List<Product>
) : RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder =
        ProductsViewHolder(
            ProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        val product = products[position]
        holder.binding.tvProductTitle.text = product.title
        holder.binding.tvProductPrice.text = "$${product.price}"
        holder.binding.tvDiscount.text = "${product.price}%"
    }

    override fun getItemCount(): Int = products.size

    inner class ProductsViewHolder(val binding: ProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                clickListener?.let {
                    it(binding.root, products[adapterPosition])
                }
            }
        }
    }

    private var clickListener: ((View, Product) -> Unit)? = null

    fun setClickListener(clickListener: (View, Product) -> Unit) {
        this.clickListener = clickListener
    }
}