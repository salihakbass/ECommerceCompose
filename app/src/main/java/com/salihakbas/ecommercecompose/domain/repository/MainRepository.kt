package com.salihakbas.ecommercecompose.domain.repository

import com.salihakbas.ecommercecompose.data.source.remote.model.response.BaseResponse
import com.salihakbas.ecommercecompose.data.source.remote.model.response.CategoryResponse
import com.salihakbas.ecommercecompose.data.source.remote.model.response.ProductDetailResponse
import com.salihakbas.ecommercecompose.data.source.remote.model.response.ProductResponse
import com.salihakbas.ecommercecompose.domain.model.Product

interface MainRepository {
    suspend fun fetchProducts(): List<Product>
    suspend fun fetchCategories(): CategoryResponse
    suspend fun searchProducts(query: String): ProductResponse
    suspend fun getProductDetail(id: Int): ProductDetailResponse
    suspend fun getProductsByCategory(category: String): ProductResponse

    suspend fun addToCart(userId: String, productId: Int): BaseResponse
    suspend fun getCartProducts(userId: String): ProductResponse
    suspend fun clearCart(userId: String): BaseResponse
}
