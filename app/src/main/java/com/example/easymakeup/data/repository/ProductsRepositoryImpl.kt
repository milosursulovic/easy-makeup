package com.example.easymakeup.data.repository

import com.example.easymakeup.data.db.ProductsDb
import com.example.easymakeup.domain.model.Product
import com.example.easymakeup.domain.repository.ProductsRepository

class ProductsRepositoryImpl(
    private val db: ProductsDb
) : ProductsRepository {
    override fun getProducts(): List<Product> = db.getProducts()
}