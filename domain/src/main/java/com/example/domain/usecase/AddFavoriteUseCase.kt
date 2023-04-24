package com.example.domain.usecase

import com.example.domain.model.AddRemoveFavOrCart
import com.example.domain.repo.DefaultRepo
import javax.inject.Inject

class AddFavoriteUseCase @Inject constructor(private val defaultRepo: DefaultRepo) {

    suspend operator fun invoke(fav:AddRemoveFavOrCart) = defaultRepo.addFavoriteById(fav)
}