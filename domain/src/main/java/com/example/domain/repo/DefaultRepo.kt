package com.example.domain.repo

import com.example.domain.model.Root

interface DefaultRepo {

    suspend fun getHomeData(): Root
    suspend fun getAllCategories(): Root
}