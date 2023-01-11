package com.example.easymakeup.presentation.products

import com.example.easymakeup.domain.model.Product

data class ProductsState(
    val products: List<Product> = emptyList(),
    val isLoading: Boolean = false
)