package com.example.easymakeup.presentation.di

import com.example.easymakeup.domain.model.Product
import com.example.easymakeup.presentation.adapters.ProductsAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped

@Module
@InstallIn(FragmentComponent::class)
object AdapterModule {
    @Provides
    @FragmentScoped
    fun providesAdapter(products: List<Product>): ProductsAdapter =
        ProductsAdapter(products)
}