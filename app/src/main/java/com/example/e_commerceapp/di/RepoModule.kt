package com.example.e_commerceapp.di

import com.example.data.remote.ApiService
import com.example.data.repo.ProductRepoImpl
import com.example.domain.repo.ProductRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object RepoModule {

    @Provides
    fun provideRepo(apiService: ApiService):ProductRepo{
        return ProductRepoImpl(apiService)
    }
}