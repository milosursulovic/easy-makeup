package com.example.easymakeup.presentation.di

import com.example.easymakeup.domain.model.Product
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProductsModule {
    @Provides
    @Singleton
    fun providesProducts(): List<Product> =
        listOf(
            Product("Product 1", 35.50f, 35),
            Product("Product 2", 35.50f, 25),
            Product("Product 3", 35.50f, 40)
        )
}