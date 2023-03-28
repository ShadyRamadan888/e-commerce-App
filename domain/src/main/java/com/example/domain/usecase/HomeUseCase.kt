package com.example.domain.usecase

import com.example.domain.repo.DefaultRepo

class HomeUseCase(private val defaultRepo: DefaultRepo) {
    suspend operator fun invoke() = defaultRepo.getHomeData()
}