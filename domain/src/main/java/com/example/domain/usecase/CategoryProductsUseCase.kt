package com.example.domain.usecase

import com.example.domain.repo.DefaultRepo

class CategoryProductsUseCase(private val defaultRepo: DefaultRepo) {

    suspend operator fun invoke(category_id:Int) = defaultRepo.getCategoryProducts(category_id)
}