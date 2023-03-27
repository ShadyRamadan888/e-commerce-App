package com.example.e_commerceapp.di

import com.example.domain.repo.HomeRepo
import com.example.domain.usecase.GetCategoriesNames
import com.example.domain.usecase.HomeUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideHomeData(homeRepo: HomeRepo):HomeUseCase{
        return HomeUseCase(homeRepo)
    }
    @Provides
    fun provideCategories(homeRepo: HomeRepo):GetCategoriesNames{
        return GetCategoriesNames(homeRepo)
    }
}