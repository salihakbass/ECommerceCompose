package com.salihakbas.ecommercecompose.domain.usecase

import com.salihakbas.ecommercecompose.common.Resource
import com.salihakbas.ecommercecompose.domain.model.Product
import com.salihakbas.ecommercecompose.domain.repository.MainRepository
import javax.inject.Inject

class SearchProductsUseCase @Inject constructor(
    private val repository: MainRepository

) {
    suspend operator fun invoke(query: String): Resource<List<Product>> {
        return try {
            val response = repository.searchProducts(query)
            if (response.products.isNotEmpty()) {
                Resource.Success(response.products)
            } else {
                Resource.Error("No products found!")
            }
        } catch (e: Exception) {
            Resource.Error("An error occurred while searching for products: ${e.message}")
        }
    }
}