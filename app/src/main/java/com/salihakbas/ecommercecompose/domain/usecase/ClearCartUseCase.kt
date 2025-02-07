package com.salihakbas.ecommercecompose.domain.usecase

import com.salihakbas.ecommercecompose.common.Resource
import com.salihakbas.ecommercecompose.data.source.remote.model.response.BaseResponse
import com.salihakbas.ecommercecompose.domain.repository.MainRepository
import javax.inject.Inject

class ClearCartUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {
    suspend operator fun invoke(userId: String): Resource<BaseResponse> {
        return try {
            val response = mainRepository.clearCart(userId)
            if (response.status == 200) {
                Resource.Success(response)
            } else {
                Resource.Error(response.message ?: "Failed to clear cart")
            }
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "An error occurred")

        }
    }
}