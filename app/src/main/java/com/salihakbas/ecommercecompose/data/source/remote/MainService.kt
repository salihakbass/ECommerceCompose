package com.salihakbas.ecommercecompose.data.source.remote

import com.salihakbas.ecommercecompose.data.source.remote.model.response.CategoryResponse
import com.salihakbas.ecommercecompose.data.source.remote.model.response.ProductListDto
import retrofit2.http.GET


interface MainService {
    @GET("get_products.php")
    suspend fun getProducts(): ProductListDto

    @GET("get_categories.php")
    suspend fun getCategories() : CategoryResponse
}