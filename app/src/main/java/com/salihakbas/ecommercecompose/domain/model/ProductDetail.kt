package com.salihakbas.ecommercecompose.domain.model

data class ProductDetail(
    val id: Int,
    val store: String?,
    val title: String?,
    val price: Double?,
    val salePrice: Double?,
    val description: String?,
    val category: String?,
    val categoryImage: String?,
    val imageOne: String?,
    val imageTwo: String?,
    val imageThree: String?,
    val rate: Double?,
    val count: Int?,
    val saleState: Boolean?
)

fun ProductDetail.toProduct(): Product {
    return Product(
        id = id,
        store = store ?: "",
        title = title ?: "Unknown",
        price = price ?: 0.0,
        salePrice = salePrice ?: 0.0,
        description = description ?: "",
        category = category ?: "",
        categoryImage = categoryImage ?: "",
        imageOne = imageOne ?: "",
        imageTwo = imageTwo ?: "",
        imageThree = imageThree ?: "",
        rate = rate ?: 0.0,
        count = count ?: 0,
        saleState = saleState ?: false
    )
}