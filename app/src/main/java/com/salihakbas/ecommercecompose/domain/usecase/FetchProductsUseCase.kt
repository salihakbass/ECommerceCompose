package com.salihakbas.ecommercecompose.domain.usecase

import com.salihakbas.ecommercecompose.common.Resource
import com.salihakbas.ecommercecompose.domain.model.Product
import com.salihakbas.ecommercecompose.domain.repository.MainRepository
import javax.inject.Inject

class FetchProductsUseCase @Inject constructor(
    private val repository: MainRepository
) {
    suspend operator fun invoke(): Resource<List<Product>> {
        return try {
            val products = repository.fetchProducts()
            Resource.Success(products)
        } catch (e: Exception) {
            Resource.Error("Ürünler çekilirken hata oluştu: ${e.message}")
        }
    }
}