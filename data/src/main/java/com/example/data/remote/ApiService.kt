package com.example.data.remote

import com.example.domain.model.Data
import com.example.domain.model.Root
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface ApiService {

    @GET("home")
    suspend fun getHomeData(@Header("lang") lang: String): Root

    @GET("categories")
    suspend fun getAllCategories(@Header("lang") lang: String): Root

    @GET("categories/44")
    suspend fun getSpecificCategory(@Header("lang") lang: String): Root
}