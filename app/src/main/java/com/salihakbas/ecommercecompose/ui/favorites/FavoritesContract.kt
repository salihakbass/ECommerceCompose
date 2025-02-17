package com.salihakbas.ecommercecompose.ui.favorites

import com.salihakbas.ecommercecompose.data.model.FavoriteProduct

object FavoritesContract {
    data class UiState(
        val isLoading: Boolean = false,
        val list: List<String> = emptyList(),
        val favoriteProducts: List<FavoriteProduct> = emptyList(),
    )

    sealed class UiAction {
        data class RemoveFavorite(val productId: Int) : UiAction()
        data object DeleteAllFavorites : UiAction()
    }

    sealed class UiEffect {
        data class ProductClick(val id: Int) : UiEffect()
    }
}