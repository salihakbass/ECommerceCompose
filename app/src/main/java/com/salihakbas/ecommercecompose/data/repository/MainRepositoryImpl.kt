package com.salihakbas.ecommercecompose.data.repository

import android.util.Log
import com.salihakbas.ecommercecompose.data.source.local.MainDao
import com.salihakbas.ecommercecompose.data.source.remote.MainService
import com.salihakbas.ecommercecompose.data.source.remote.model.response.CategoryResponse
import com.salihakbas.ecommercecompose.domain.model.Product
import com.salihakbas.ecommercecompose.domain.repository.MainRepository
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val mainService: MainService,
    private val mainDao: MainDao
) : MainRepository {

    override suspend fun fetchProducts(): List<Product> {
        val response = mainService.getProducts()
        Log.d("API Products Response", response.products.toString())
        return response.products
    }

    override suspend fun fetchCategories(): CategoryResponse {
        return mainService.getCategories()
    }


}