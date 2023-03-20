package com.example.e_commerceapp.di

import com.example.domain.repo.ProductRepo
import com.example.domain.usecase.GetMainProducts
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideUseCase(productRepo: ProductRepo):GetMainProducts{
        return GetMainProducts(productRepo)
    }
}