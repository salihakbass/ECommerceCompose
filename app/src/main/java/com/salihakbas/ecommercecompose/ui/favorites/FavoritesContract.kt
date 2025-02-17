package com.salihakbas.ecommercecompose.ui.favorites

import com.salihakbas.ecommercecompose.data.model.FavoriteProduct

object FavoritesContract {
    data class UiState(
        val isLoading: Boolean = false,
        val list: List<String> = emptyList(),
        val favoriteProducts: List<FavoriteProduct> = emptyList(),
    )

    sealed class UiAction

    sealed class UiEffect
}