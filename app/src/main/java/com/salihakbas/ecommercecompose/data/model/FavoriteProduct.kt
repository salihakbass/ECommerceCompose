package com.salihakbas.ecommercecompose.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_products")
data class FavoriteProduct(
    @PrimaryKey val id: Int,
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
