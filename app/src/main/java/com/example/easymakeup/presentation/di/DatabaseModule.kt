package com.example.easymakeup.presentation.di

import com.example.easymakeup.data.db.ProductsDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun providesDatabase(): ProductsDb =
        ProductsDb()
}