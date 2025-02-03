package com.salihakbas.ecommercecompose.domain.usecase

import com.salihakbas.ecommercecompose.common.Resource
import com.salihakbas.ecommercecompose.domain.model.Category
import com.salihakbas.ecommercecompose.domain.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchCategoriesUseCase @Inject constructor(
    private val repository: MainRepository
) {
    suspend operator fun invoke(): Flow<Resource<List<Category>>> {
        return flow {
            try {
                val response = repository.fetchCategories()
                val distinctCategories = response.categories.distinctBy { category ->
                    category.name
                }
                emit(Resource.Success(data = distinctCategories))
            } catch (e: Exception) {
                emit(Resource.Error(message = e.localizedMessage ?: "Unknown error!"))
            }
        }
    }
}