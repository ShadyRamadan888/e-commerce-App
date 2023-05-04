package com.example.domain.usecase

import android.content.Context
import com.example.domain.model.GetAllFav
import com.example.domain.repo.DefaultRepo
import javax.inject.Inject

class GetAllFavoritesUseCase @Inject constructor(private val defaultRepo: DefaultRepo) {
    suspend operator fun invoke(context: Context) = defaultRepo.getAllFav(context)
}