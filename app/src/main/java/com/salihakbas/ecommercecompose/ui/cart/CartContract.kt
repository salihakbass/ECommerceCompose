package com.salihakbas.ecommercecompose.ui.cart

import com.salihakbas.ecommercecompose.domain.model.Product

object CartContract {
    data class UiState(
        val isLoading: Boolean = false,
        val list: List<String> = emptyList(),
        val products: List<Product> = emptyList(),
        val error: String = ""
    )

    sealed class UiAction {
        data class ClearCart(val userId: String) : UiAction()
        data class DeleteFromCart(val id: Int,val userId: String) : UiAction()
    }

    sealed class UiEffect
}