package com.salihakbas.ecommercecompose.data.repository

import com.salihakbas.ecommercecompose.data.model.FavoriteProduct
import com.salihakbas.ecommercecompose.data.source.local.MainDao
import com.salihakbas.ecommercecompose.domain.model.Product
import javax.inject.Inject

class FavoriteRepository @Inject constructor(
    private val dao: MainDao
) {
    suspend fun addFavoriteProduct(product: Product) {
        val favoriteProduct = FavoriteProduct(
            id = product.id,
            title = product.title,
            price = product.price,
            salePrice = product.salePrice,
            store = product.store,
            description = product.description,
            category = product.category,
            categoryImage = product.categoryImage,
            imageOne = product.imageOne,
            imageTwo = product.imageTwo,
            imageThree = product.imageThree,
            rate = product.rate,
            count = product.count,
            saleState = product.saleState,
        )
        dao.insertFavoriteProduct(favoriteProduct)
    }

    suspend fun removeFavoriteProduct(productId: Int) {
        dao.removeFavoriteProduct(productId)
    }

    suspend fun isProductFavorite(productId: Int) : Boolean{
        return dao.isFavoriteProduct(productId)
    }

    suspend fun getAllFavorites() : List<FavoriteProduct> {
        return dao.getAllFavoriteProducts()
    }
}