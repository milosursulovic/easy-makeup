package com.example.easymakeup.domain.repository

import com.example.easymakeup.domain.model.Product

interface ProductsRepository {
    fun getProducts(): List<Product>
}