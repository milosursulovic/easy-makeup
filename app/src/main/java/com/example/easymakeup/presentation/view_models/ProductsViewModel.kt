package com.example.easymakeup.presentation.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.easymakeup.domain.use_cases.GetProductsUseCase
import com.example.easymakeup.presentation.products.ProductEvent
import com.example.easymakeup.presentation.products.ProductsState
import com.example.easymakeup.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase
) : ViewModel() {

    private var state = ProductsState()

    fun triggerEvent(event: ProductEvent) {
        when (event) {
            is ProductEvent.Init -> {
                getProducts()
            }
        }
    }

    private var stateChangeListener: ((ProductsState) -> Unit)? = null

    fun setStateChangeListener(stateChangeListener: (ProductsState) -> Unit) {
        this.stateChangeListener = stateChangeListener
    }

    private fun getProducts() {
        viewModelScope.launch {
            getProductsUseCase()
                .collect { result ->
                    when (result) {
                        is Resource.Loading -> {
                            state = state.copy(isLoading = true)
                            stateChangeListener?.let {
                                it(state)
                            }
                        }
                        is Resource.Success -> {
                            result.data?.let { products ->
                                state = state.copy(products = products, isLoading = false)
                            }
                            stateChangeListener?.let {
                                it(state)
                            }
                        }
                        is Resource.Error -> Unit
                    }
                }
        }
    }
}