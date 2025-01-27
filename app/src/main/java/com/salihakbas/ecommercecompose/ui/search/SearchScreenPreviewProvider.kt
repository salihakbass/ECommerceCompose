package com.salihakbas.ecommercecompose.ui.search

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

class SearchScreenPreviewProvider : PreviewParameterProvider<SearchContract.UiState> {
    override val values: Sequence<SearchContract.UiState>
        get() = sequenceOf(
            SearchContract.UiState(
                isLoading = true,
                list = emptyList(),
            ),
            SearchContract.UiState(
                isLoading = false,
                list = emptyList(),
            ),
            SearchContract.UiState(
                isLoading = false,
                list = listOf("Item 1", "Item 2", "Item 3")
            ),
        )
}