package com.example.domain.repo

import com.example.domain.model.CatProduct
import com.example.domain.model.ProductRoot
import com.example.domain.model.Root
import kotlinx.coroutines.flow.Flow

interface DefaultRepo {

    suspend fun getHomeData(): Flow<Root>
    suspend fun getAllCategories(): Root
    suspend fun getProductById(id:Int): ProductRoot
    suspend fun getCategoryProducts(category_id:Int): CatProduct
}