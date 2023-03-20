package com.example.domain.usecase

import com.example.domain.repo.ProductRepo

class GetMainProducts(private val productRepo: ProductRepo) {

    suspend operator fun invoke() = productRepo.getProductsFromRemote()
}