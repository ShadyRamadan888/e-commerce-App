package com.example.domain.usecase

import com.example.domain.repo.DefaultRepo

class CategoriesUseCase(private val defaultRepo: DefaultRepo) {
    suspend operator fun invoke() = defaultRepo.getAllCategories()
}