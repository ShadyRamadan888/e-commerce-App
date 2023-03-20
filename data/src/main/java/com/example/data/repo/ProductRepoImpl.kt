package com.example.data.repo

import com.example.data.remote.ApiService
import com.example.domain.model.ProductRoot
import com.example.domain.repo.ProductRepo

class ProductRepoImpl(private val apiService: ApiService):ProductRepo {
    override suspend fun getProductsFromRemote(): ProductRoot = apiService.getMainProducts()
}