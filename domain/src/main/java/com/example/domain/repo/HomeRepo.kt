package com.example.domain.repo

import com.example.domain.model.Data
import com.example.domain.model.Root

interface HomeRepo {

    suspend fun getProducts(): Root
    suspend fun getAllCategories():Root
}