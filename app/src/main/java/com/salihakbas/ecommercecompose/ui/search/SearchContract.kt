package com.salihakbas.ecommercecompose.ui.search

import com.salihakbas.ecommercecompose.domain.model.Product

object SearchContract {
    data class UiState(
        val isLoading: Boolean = false,
        val list: List<String> = emptyList(),
        val suggestedProducts: List<Product> = emptyList(),
        val allProducts: List<Product> = emptyList(),
        val query: String = "",
    )

    sealed class UiAction {
        data class OnQueryChanged(val query: String) : UiAction()
    }

    sealed class UiEffect {
        data object NavigateBack : UiEffect()
    }
}