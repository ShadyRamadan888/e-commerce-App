package com.example.domain.repo

import android.content.Context
import com.example.domain.model.*
import kotlinx.coroutines.flow.Flow

interface DefaultRepo {

    suspend fun getHomeData(): Flow<Root>
    suspend fun getAllCategories(): Flow<Root>
    suspend fun getProductById(id: Int): ProductRoot
    suspend fun getCategoryProducts(category_id: Int): CatProduct

    //Carts
    suspend fun addCart(sendId: AddRemoveCartRoot): MyResponse<GetCarts>

    //Favorites
    suspend fun getAllFav(context: Context): List<FavoritesEntity>
}