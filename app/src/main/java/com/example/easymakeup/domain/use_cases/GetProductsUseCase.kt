package com.example.easymakeup.domain.use_cases

import com.example.easymakeup.domain.model.Product
import com.example.easymakeup.domain.repository.ProductsRepository
import com.example.easymakeup.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetProductsUseCase(
    private val repository: ProductsRepository
) {
    suspend operator fun invoke(): Flow<Resource<List<Product>>> = flow {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(repository.getProducts()))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unknown error happened"))
        }
    }
}