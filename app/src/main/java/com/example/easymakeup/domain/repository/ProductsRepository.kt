package com.example.easymakeup.domain.repository

import com.example.easymakeup.domain.model.Product

interface ProductsRepository {
    suspend fun getProducts(): List<Product>
}