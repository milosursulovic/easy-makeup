package com.example.easymakeup.presentation.di

import com.example.easymakeup.data.db.ProductsDb
import com.example.easymakeup.data.repository.ProductsRepositoryImpl
import com.example.easymakeup.domain.repository.ProductsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun providesRepository(db: ProductsDb): ProductsRepository =
        ProductsRepositoryImpl(db)
}