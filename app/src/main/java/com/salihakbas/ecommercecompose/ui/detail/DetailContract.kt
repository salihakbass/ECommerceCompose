package com.salihakbas.ecommercecompose.ui.detail

import com.salihakbas.ecommercecompose.domain.model.Product
import com.salihakbas.ecommercecompose.domain.model.ProductDetail

object DetailContract {
    data class UiState(
        val isLoading: Boolean = false,
        val list: List<String> = emptyList(),
        val error: String = "",
        val product: ProductDetail? = null,
        val similarProducts: List<Product> = emptyList(),
        val isFavorite: Boolean = false,
        val favoriteProduct : Product? = null
    )

    sealed class UiAction {
        data class AddToCart(val userId: String, val productId: Int) : UiAction()
        data object ToggleFavorite : UiAction()
    }

    sealed class UiEffect {
        data object NavigateBack : UiEffect()
        data object NavigateSearch : UiEffect()
    }
}