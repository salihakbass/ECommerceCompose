package com.salihakbas.ecommercecompose.ui.discount

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

class DiscountScreenPreviewProvider : PreviewParameterProvider<DiscountContract.UiState> {
    override val values: Sequence<DiscountContract.UiState>
        get() = sequenceOf(
            DiscountContract.UiState(
                isLoading = true,
                list = emptyList(),
            ),
            DiscountContract.UiState(
                isLoading = false,
                list = emptyList(),
            ),
            DiscountContract.UiState(
                isLoading = false,
                list = listOf("Item 1", "Item 2", "Item 3")
            ),
        )
}