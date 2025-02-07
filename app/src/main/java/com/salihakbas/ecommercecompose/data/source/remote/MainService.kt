package com.salihakbas.ecommercecompose.data.source.remote

import com.salihakbas.ecommercecompose.data.source.remote.model.response.BaseResponse
import com.salihakbas.ecommercecompose.data.source.remote.model.response.CategoryResponse
import com.salihakbas.ecommercecompose.data.source.remote.model.response.ProductDetailResponse
import com.salihakbas.ecommercecompose.data.source.remote.model.response.ProductResponse
import com.salihakbas.ecommercecompose.domain.model.AddToCartRequest
import com.salihakbas.ecommercecompose.domain.model.ClearCartRequest
import com.salihakbas.ecommercecompose.domain.model.DeleteFromCartRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


interface MainService {
    @GET("get_products.php")
    suspend fun getProducts(): ProductResponse

    @GET("get_categories.php")
    suspend fun getCategories(): CategoryResponse

    @GET("search_product.php")
    suspend fun searchProducts(
        @Query("query") query: String
    ): ProductResponse

    @GET("get_product_detail.php")
    suspend fun getProductDetail(
        @Query("id") id: Int
    ): ProductDetailResponse

    @GET("get_products_by_category.php")
    suspend fun getProductsByCategory(
        @Query("category") category: String
    ): ProductResponse

    @POST("add_to_cart.php")
    suspend fun addToCart(
        @Body request: AddToCartRequest
    ): BaseResponse

    @GET("get_cart_products.php")
    suspend fun getCartProducts(
        @Query("userId") userId: String
    ): ProductResponse

    @POST("clear_cart.php")
    suspend fun clearCart(
        @Body request: ClearCartRequest
    ): BaseResponse

    @POST("delete_from_cart.php")
    suspend fun deleteFromCart(
        @Body request: DeleteFromCartRequest
    ) : BaseResponse
}