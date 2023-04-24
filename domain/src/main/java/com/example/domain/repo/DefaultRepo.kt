package com.example.domain.repo

import com.example.domain.model.*
import kotlinx.coroutines.flow.Flow

interface DefaultRepo {

    suspend fun getHomeData(): Flow<Root>
    suspend fun getAllCategories(): Root
    suspend fun getProductById(id:Int): ProductRoot
    suspend fun getCategoryProducts(category_id:Int): CatProduct
    suspend fun addFavoriteById(fav:AddRemoveFavOrCart): AddRemoveFavRes
    suspend fun getAllFav():GetAllFav
}