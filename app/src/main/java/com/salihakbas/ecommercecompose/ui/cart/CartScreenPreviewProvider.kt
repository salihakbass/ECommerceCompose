package com.salihakbas.ecommercecompose.ui.cart

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

class CartScreenPreviewProvider : PreviewParameterProvider<CartContract.UiState> {
    override val values: Sequence<CartContract.UiState>
        get() = sequenceOf(
            CartContract.UiState(
                isLoading = true,
                list = emptyList(),
            ),
            CartContract.UiState(
                isLoading = false,
                list = emptyList(),
            ),
            CartContract.UiState(
                isLoading = false,
                list = listOf("Item 1", "Item 2", "Item 3")
            ),
        )
}