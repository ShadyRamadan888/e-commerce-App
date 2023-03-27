package com.example.domain.usecase

import com.example.domain.repo.HomeRepo

class GetCategoriesNames(private val homeRepo: HomeRepo) {
    suspend operator fun invoke() = homeRepo.getAllCategories()
}