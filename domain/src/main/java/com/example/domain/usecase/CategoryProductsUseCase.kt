package com.example.domain.usecase

import com.example.domain.repo.DefaultRepo
import javax.inject.Inject

class CategoryProductsUseCase @Inject constructor(private val defaultRepo: DefaultRepo) {

    suspend operator fun invoke(category_id:Int) = defaultRepo.getCategoryProducts(category_id)
}