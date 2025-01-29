package com.salihakbas.ecommercecompose.ui.discount

import com.salihakbas.ecommercecompose.domain.model.Product

object DiscountContract {
    data class UiState(
        val discountList: List<Product> = emptyList(),
        val isLoading: Boolean = false,
        val list: List<String> = emptyList(),
    )

    sealed class UiAction

    sealed class UiEffect {
        data object NavigateBack : UiEffect()
    }


}