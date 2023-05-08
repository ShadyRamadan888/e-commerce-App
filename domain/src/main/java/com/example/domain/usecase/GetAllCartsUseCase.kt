package com.example.domain.usecase

import android.content.Context
import com.example.domain.repo.DefaultRepo

class GetAllCartsUseCase(private val defaultRepo: DefaultRepo) {
    suspend operator fun invoke(context: Context) = defaultRepo.getAllCarts(context)
}