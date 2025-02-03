package com.salihakbas.ecommercecompose.data.repository

import com.salihakbas.ecommercecompose.data.source.local.MainDao
import com.salihakbas.ecommercecompose.data.source.remote.MainService
import com.salihakbas.ecommercecompose.data.source.remote.model.response.CategoryResponse
import com.salihakbas.ecommercecompose.data.source.remote.model.response.ProductDetailResponse
import com.salihakbas.ecommercecompose.data.source.remote.model.response.ProductResponse
import com.salihakbas.ecommercecompose.domain.model.Product
import com.salihakbas.ecommercecompose.domain.repository.MainRepository
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val mainService: MainService,
    private val mainDao: MainDao
) : MainRepository {

    override suspend fun fetchProducts(): List<Product> {
        return mainService.getProducts().products
    }

    override suspend fun fetchCategories(): CategoryResponse {
        return mainService.getCategories()
    }

    override suspend fun searchProducts(query: String): ProductResponse {
        return mainService.searchProducts(query = query)
    }

    override suspend fun getProductDetail(id: Int): ProductDetailResponse {
        return mainService.getProductDetail(id)
    }


}