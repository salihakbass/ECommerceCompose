package com.salihakbas.ecommercecompose.domain.repository

import com.salihakbas.ecommercecompose.common.Resource
import com.salihakbas.ecommercecompose.data.source.remote.model.response.CategoryResponse
import com.salihakbas.ecommercecompose.domain.model.Product

interface MainRepository {
    suspend fun fetchProducts(): List<Product>
    suspend fun fetchCategories(): CategoryResponse

}
