package com.salihakbas.ecommercecompose.ui.product

import com.salihakbas.ecommercecompose.domain.model.Product

object ProductContract {
    data class UiState(
        val isLoading: Boolean = false,
        val list: List<String> = emptyList(),
        val productList: List<Product> = emptyList()
    )

    sealed class UiAction

    sealed class UiEffect {
        data object NavigateBack : UiEffect()
        data class NavigateDetail(val id: Int) : UiEffect()
    }
}