package com.example.domain.usecase

import com.example.domain.repo.DefaultRepo
import javax.inject.Inject

class GetProductDetailsUseCase @Inject constructor(private val defaultRepo: DefaultRepo) {
    suspend operator fun invoke(id:Int) = defaultRepo.getProductById(id)
}