package com.example.data.repo

import android.content.Context
import com.example.data.local.carts_room.CartsDatabase
import com.example.data.local.fav_room.FavoriteDatabase
import com.example.data.remote.ApiService
import com.example.domain.model.*
import com.example.domain.repo.DefaultRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class DefaultRepoImpl @Inject constructor(
    private val apiService: ApiService,
    private var favoriteDatabase: FavoriteDatabase? = null,
    private var cartsDatabase: CartsDatabase?=null
) : DefaultRepo {
    override suspend fun getHomeData(): Flow<Root> = flow {
        val response = apiService.getHomeData("en")
        emit(response.body()!!)
    }

    override suspend fun getAllCategories(): Flow<Root> = flow {
        val response = apiService.getAllCategories("en")
        emit(response.body()!!)
    }

    override suspend fun getProductById(id: Int): ProductRoot = apiService.getProductById("en", id)
    override suspend fun getCategoryProducts(category_id: Int): CatProduct =
        apiService.getCategoryProducts("en", category_id)

    //Carts
    override suspend fun addCart(sendId: AddRemoveCartRoot): MyResponse<GetCarts> =
        apiService.addRemoveCart("en", sendId)

    //Carts
    override suspend fun getAllCarts(context: Context): List<CartEntity> {
        cartsDatabase = CartsDatabase.getInstance(context)
        return cartsDatabase!!.cartDao().getAllCarts()
    }

    //Favorites
    override suspend fun getAllFav(context: Context): List<FavoritesEntity> {
        favoriteDatabase = FavoriteDatabase.getInstance(context)
        return favoriteDatabase!!.favDao().getAllFavorites()
    }

}