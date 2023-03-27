package com.example.data.repo

import com.example.data.remote.ApiService
import com.example.domain.model.Data
import com.example.domain.model.Root
import com.example.domain.repo.HomeRepo

class HomeRepoImpl(private val apiService: ApiService):HomeRepo {
    override suspend fun getProducts(): Root = apiService.getHomeData("en")
    override suspend fun getAllCategories(): Root =apiService.getAllCategories("en")
}