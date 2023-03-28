package com.example.data.repo

import com.example.data.remote.ApiService
import com.example.domain.model.Root
import com.example.domain.repo.DefaultRepo

class DefaultRepoImpl(private val apiService: ApiService) : DefaultRepo {
    override suspend fun getHomeData(): Root = apiService.getHomeData("en")
    override suspend fun getAllCategories(): Root = apiService.getAllCategories("en")
}