package com.example.data.remote

import com.example.domain.model.ProductRoot
import retrofit2.http.GET

interface ApiService {

    @GET("products/")
    suspend fun getMainProducts(): ProductRoot
}