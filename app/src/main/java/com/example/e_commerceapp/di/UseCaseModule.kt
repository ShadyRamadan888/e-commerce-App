package com.example.e_commerceapp.di

import com.example.domain.repo.DefaultRepo
import com.example.domain.usecase.CategoriesUseCase
import com.example.domain.usecase.GetProductDetailsUseCase
import com.example.domain.usecase.HomeUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideHomeData(defaultRepo: DefaultRepo):HomeUseCase{
        return HomeUseCase(defaultRepo)
    }
    @Provides
    fun provideCategories(defaultRepo: DefaultRepo):CategoriesUseCase{
        return CategoriesUseCase(defaultRepo)
    }

    @Provides
    fun provideProductById(defaultRepo: DefaultRepo): GetProductDetailsUseCase{
        return GetProductDetailsUseCase(defaultRepo)
    }
}