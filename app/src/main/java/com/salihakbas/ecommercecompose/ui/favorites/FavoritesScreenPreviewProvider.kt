package com.salihakbas.ecommercecompose.ui.favorites

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

class FavoritesScreenPreviewProvider : PreviewParameterProvider<FavoritesContract.UiState> {
    override val values: Sequence<FavoritesContract.UiState>
        get() = sequenceOf(
            FavoritesContract.UiState(
                isLoading = true,
                list = emptyList(),
            ),
            FavoritesContract.UiState(
                isLoading = false,
                list = emptyList(),
            ),
            FavoritesContract.UiState(
                isLoading = false,
                list = listOf("Item 1", "Item 2", "Item 3")
            ),
        )
}