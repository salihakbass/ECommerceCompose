package com.salihakbas.ecommercecompose.domain.usecase

import com.salihakbas.ecommercecompose.common.Resource
import com.salihakbas.ecommercecompose.data.source.remote.model.response.BaseResponse
import com.salihakbas.ecommercecompose.domain.repository.MainRepository
import javax.inject.Inject

class DeleteFromCartUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {
    suspend operator fun invoke(id: Int,userId: String): Resource<BaseResponse> {
        return try {
            val response = mainRepository.deleteFromCart(id,userId)
            if (response.status == 200) {
                Resource.Success(response)
            } else {
                Resource.Error(response.message ?: "Failed to delete from cart")
            }
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "An error occurred")
        }
    }
}