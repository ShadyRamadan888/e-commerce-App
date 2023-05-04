package com.example.domain.usecase

import com.example.domain.model.AddRemoveCartRoot
import com.example.domain.repo.DefaultRepo
import javax.inject.Inject

class AddCartUseCase @Inject constructor(private val defaultRepo: DefaultRepo) {
    suspend operator fun invoke(sendId: AddRemoveCartRoot) = defaultRepo.addCart(sendId)
}