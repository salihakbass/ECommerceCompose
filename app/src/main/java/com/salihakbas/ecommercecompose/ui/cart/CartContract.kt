package com.salihakbas.ecommercecompose.ui.cart

import com.salihakbas.ecommercecompose.domain.model.Product
import com.salihakbas.ecommercecompose.navigation.Screen

object CartContract {
    data class UiState(
        val isLoading: Boolean = false,
        val list: List<String> = emptyList(),
        val products: List<Product> = emptyList(),
        val error: String = ""
    )

    sealed class UiAction

    sealed class UiEffect
}