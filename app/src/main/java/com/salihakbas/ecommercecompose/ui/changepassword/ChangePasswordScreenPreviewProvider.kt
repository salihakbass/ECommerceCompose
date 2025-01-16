package com.salihakbas.ecommercecompose.ui.changepassword

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

class ChangePasswordScreenPreviewProvider :
    PreviewParameterProvider<ChangePasswordContract.UiState> {
    override val values: Sequence<ChangePasswordContract.UiState>
        get() = sequenceOf(
            ChangePasswordContract.UiState(
                isLoading = true,
                list = emptyList(),
            ),
            ChangePasswordContract.UiState(
                isLoading = false,
                list = emptyList(),
            ),
            ChangePasswordContract.UiState(
                isLoading = false,
                list = listOf("Item 1", "Item 2", "Item 3")
            ),
        )
}