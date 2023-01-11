package com.example.easymakeup.presentation.di

import android.app.Application
import com.example.easymakeup.domain.repository.ProductsRepository
import com.example.easymakeup.domain.use_cases.GetProductsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCasesModule {
    @Provides
    @Singleton
    fun providesGetProductsUseCase(
        app: Application,
        repository: ProductsRepository
    ): GetProductsUseCase =
        GetProductsUseCase(app, repository)
}