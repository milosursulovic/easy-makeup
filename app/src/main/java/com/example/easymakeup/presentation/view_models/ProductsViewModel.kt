package com.example.easymakeup.presentation.view_models

import androidx.lifecycle.ViewModel
import com.example.easymakeup.domain.use_cases.GetProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase
) : ViewModel() {
    fun getProducts() = getProductsUseCase()
}