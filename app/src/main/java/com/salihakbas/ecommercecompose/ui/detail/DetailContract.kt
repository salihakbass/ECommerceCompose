package com.salihakbas.ecommercecompose.ui.detail

import com.salihakbas.ecommercecompose.domain.model.Product
import com.salihakbas.ecommercecompose.domain.model.ProductDetail

object DetailContract {
    data class UiState(
        val isLoading: Boolean = false,
        val list: List<String> = emptyList(),
        val error: String = "",
        val product: ProductDetail? = null,
        val similarProducts: List<Product> = emptyList()
    )

    sealed class UiAction

    sealed class UiEffect
}