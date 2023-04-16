package com.example.domain.usecase

import com.example.domain.repo.DefaultRepo

class GetProductDetailsUseCase(private val defaultRepo: DefaultRepo) {
    suspend operator fun invoke(id:Int) = defaultRepo.getProductById(id)
}