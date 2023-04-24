package com.example.domain.usecase

import com.example.domain.model.GetAllFav
import com.example.domain.repo.DefaultRepo
import javax.inject.Inject

class GetAllFavoritesUseCase @Inject constructor(private val defaultRepo: DefaultRepo) {
    suspend operator fun invoke():GetAllFav = defaultRepo.getAllFav()
}