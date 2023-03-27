package com.example.data.remote

import com.example.domain.model.Data
import com.example.domain.model.Root
import retrofit2.http.GET
import retrofit2.http.Header

interface ApiService {

    @GET("home")
    suspend fun getHomeData(@Header("lang") lang:String): Root

    @GET("categories")
    suspend fun getAllCategories(@Header("lang") lang:String):Root
}