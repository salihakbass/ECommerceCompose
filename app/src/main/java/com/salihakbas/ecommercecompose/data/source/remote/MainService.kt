package com.salihakbas.ecommercecompose.data.source.remote

import com.salihakbas.ecommercecompose.data.source.remote.model.response.CategoryResponse
import com.salihakbas.ecommercecompose.data.source.remote.model.response.ProductResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface MainService {
    @GET("get_products.php")
    suspend fun getProducts(): ProductResponse

    @GET("get_categories.php")
    suspend fun getCategories() : CategoryResponse

    @GET("search_product.php")
    suspend fun searchProducts(
        @Query("query") query: String
    ) : ProductResponse
}