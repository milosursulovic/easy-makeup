package com.example.easymakeup.data.db

import com.example.easymakeup.domain.model.Product

class ProductsDb {
    fun getProducts(): List<Product> =
        listOf(
            Product("Product 1", 35.50f, 35),
            Product("Product 2", 35.50f, 25),
            Product("Product 3", 35.50f, 40)
        )
}