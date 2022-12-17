package com.example.easymakeup.presentation.di

import android.app.Application
import com.karumi.dexter.Dexter
import com.karumi.dexter.DexterBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DexterModule {
    @Provides
    @Singleton
    fun providesDexter(app: Application): DexterBuilder.Permission =
        Dexter.withContext(app)
}