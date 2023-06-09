package com.example.data.remote

import com.example.domain.model.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("home")
    suspend fun getHomeData(@Header("lang") lang: String): Response<Root>

    @GET("categories")
    suspend fun getAllCategories(@Header("lang") lang: String): Response<Root>

    @GET("categories/44")
    suspend fun getSpecificCategory(@Header("lang") lang: String): Root

    @GET("products/{id}")
    suspend fun getProductById(@Header("lang") lang: String, @Path("id") id: Int): ProductRoot

    @GET("products")
    suspend fun getCategoryProducts(
        @Header("lang") lang: String,
        @Query("category_id") category_id: Int
    ): CatProduct

    //Carts
    @POST("carts")
    suspend fun addRemoveCart(@Header("lang") lang: String,@Body sendId: AddRemoveCartRoot): MyResponse<GetCarts>
}