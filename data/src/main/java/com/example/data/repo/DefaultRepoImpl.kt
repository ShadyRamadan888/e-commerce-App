package com.example.data.repo

import com.example.data.remote.ApiService
import com.example.domain.model.ProductRoot
import com.example.domain.model.Root
import com.example.domain.repo.DefaultRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DefaultRepoImpl(private val apiService: ApiService) : DefaultRepo {
    override suspend fun getHomeData(): Flow<Root> = flow {
        val data = apiService.getHomeData("en")
        emit(data)
    }
    override suspend fun getAllCategories(): Root = apiService.getAllCategories("en")
    override suspend fun getProductById(id: Int): ProductRoot = apiService.getProductById("en",id)
}