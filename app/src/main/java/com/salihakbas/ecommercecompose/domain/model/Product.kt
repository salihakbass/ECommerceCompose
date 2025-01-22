package com.salihakbas.ecommercecompose.domain.model

data class Product(
    val id: Int,
    val store: String?,
    val title: String,
    val price: Double,
    val salePrice: Double?,
    val description: String,
    val category: String,
    val categoryImage: String?,
    val imageOne: String?,
    val imageTwo: String?,
    val imageThree: String?,
    val rate: Double,
    val count: Int,
    val saleState: Boolean
)