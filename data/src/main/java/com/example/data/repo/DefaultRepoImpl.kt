package com.example.data.repo

import com.example.data.remote.ApiService
import com.example.domain.model.*
import com.example.domain.repo.DefaultRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class DefaultRepoImpl @Inject constructor(private val apiService: ApiService) : DefaultRepo {
    override suspend fun getHomeData(): Flow<Root> = flow{
        val response = apiService.getHomeData("en")
        emit(response.body()!!)
    }
    override suspend fun getAllCategories(): Root = apiService.getAllCategories("en")
    override suspend fun getProductById(id: Int): ProductRoot = apiService.getProductById("en",id)
    override suspend fun getCategoryProducts(category_id: Int): CatProduct = apiService.getCategoryProducts("en",category_id)
    override suspend fun addFavoriteById(fav: AddRemoveFavOrCart): AddRemoveFavRes = apiService.addFavoriteById(fav,"en")
    override suspend fun getAllFav(): GetAllFav = apiService.getAllFavorites("en")
}