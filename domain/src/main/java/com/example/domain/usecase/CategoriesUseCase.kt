package com.example.domain.usecase

import com.example.domain.repo.DefaultRepo
import javax.inject.Inject

class CategoriesUseCase @Inject constructor(private val defaultRepo: DefaultRepo) {
    suspend operator fun invoke() = defaultRepo.getAllCategories()
}