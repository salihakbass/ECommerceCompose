package com.salihakbas.ecommercecompose.ui.product

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

class ProductScreenPreviewProvider : PreviewParameterProvider<ProductContract.UiState> {
    override val values: Sequence<ProductContract.UiState>
        get() = sequenceOf(
            ProductContract.UiState(
                isLoading = true,
                list = emptyList(),
            ),
            ProductContract.UiState(
                isLoading = false,
                list = emptyList(),
            ),
            ProductContract.UiState(
                isLoading = false,
                list = listOf("Item 1", "Item 2", "Item 3")
            ),
        )
}