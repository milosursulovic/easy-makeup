package com.example.easymakeup.domain.use_cases

import com.example.easymakeup.domain.model.Product
import com.example.easymakeup.domain.repository.ProductsRepository

class GetProductsUseCase(
    private val repository: ProductsRepository
) {
    operator fun invoke(): List<Product> = repository.getProducts()
}