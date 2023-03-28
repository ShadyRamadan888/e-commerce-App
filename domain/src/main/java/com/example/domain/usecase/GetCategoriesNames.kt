package com.example.domain.usecase

import com.example.domain.repo.DefaultRepo

class GetCategoriesNames(private val defaultRepo: DefaultRepo) {
    suspend operator fun invoke() = defaultRepo.getAllCategories()
}