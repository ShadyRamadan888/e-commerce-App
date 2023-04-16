package com.example.e_commerceapp.di

import com.example.data.remote.ApiService
import com.example.data.repo.DefaultRepoImpl
import com.example.domain.repo.DefaultRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object RepoModule {

    @Provides
    fun provideHomeRepo(apiService: ApiService):DefaultRepo{
        return DefaultRepoImpl(apiService)
    }
}