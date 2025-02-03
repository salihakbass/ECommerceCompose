package com.salihakbas.ecommercecompose.domain.usecase

import com.salihakbas.ecommercecompose.common.Resource
import com.salihakbas.ecommercecompose.domain.model.ProductDetail
import com.salihakbas.ecommercecompose.domain.repository.MainRepository
import javax.inject.Inject

class GetProductDetailUseCase @Inject constructor(
    private val repository: MainRepository
) {
    suspend operator fun invoke(productId: Int): Resource<ProductDetail> {
        return try {
            val response = repository.getProductDetail(productId)
            if (response.product != null) {
                Resource.Success(response.product)
            } else {
                Resource.Error("Product detail not found")
            }
        } catch (e: Exception) {
            Resource.Error("An error occurred while fetching product detail: ${e.message}")
        }
    }
}