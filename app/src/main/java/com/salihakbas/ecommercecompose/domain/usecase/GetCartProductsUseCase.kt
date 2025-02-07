package com.salihakbas.ecommercecompose.domain.usecase

import com.salihakbas.ecommercecompose.common.Resource
import com.salihakbas.ecommercecompose.domain.model.Product
import com.salihakbas.ecommercecompose.domain.repository.MainRepository
import javax.inject.Inject

class GetCartProductsUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {
    suspend operator fun invoke(userId: String): Resource<List<Product>> {
        return try {
            val response = mainRepository.getCartProducts(userId)
            Resource.Success(response.products)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "An error occurred")
        }
    }
}