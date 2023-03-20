package com.example.domain.repo

import com.example.domain.model.ProductRoot

interface ProductRepo {

    suspend fun getProductsFromRemote():ProductRoot
}