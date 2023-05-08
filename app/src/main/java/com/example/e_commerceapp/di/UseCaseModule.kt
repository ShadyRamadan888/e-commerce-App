package com.example.e_commerceapp.di

import com.example.domain.repo.DefaultRepo
import com.example.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideHomeData(defaultRepo: DefaultRepo): HomeUseCase {
        return HomeUseCase(defaultRepo)
    }

    @Provides
    fun provideCategories(defaultRepo: DefaultRepo): CategoriesUseCase {
        return CategoriesUseCase(defaultRepo)
    }

    @Provides
    fun provideProductById(defaultRepo: DefaultRepo): GetProductDetailsUseCase {
        return GetProductDetailsUseCase(defaultRepo)
    }

    @Provides
    fun provideCategoryProducts(defaultRepo: DefaultRepo): CategoryProductsUseCase {
        return CategoryProductsUseCase(defaultRepo)
    }

    @Provides
    fun provideGetAllFav(defaultRepo: DefaultRepo): GetAllFavoritesUseCase {
        return GetAllFavoritesUseCase(defaultRepo)
    }

    @Provides
    fun provideCartsUseCase(defaultRepo: DefaultRepo): AddCartUseCase {
        return AddCartUseCase(defaultRepo)
    }

    //carts
    @Provides
    fun provideGetAllCarts(defaultRepo: DefaultRepo): GetAllCartsUseCase {
        return GetAllCartsUseCase(defaultRepo)
    }

}