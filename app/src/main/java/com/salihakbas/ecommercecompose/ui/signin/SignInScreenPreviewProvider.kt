package com.salihakbas.ecommercecompose.ui.signin

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

class SignInScreenPreviewProvider : PreviewParameterProvider<SignInContract.UiState> {
    override val values: Sequence<SignInContract.UiState>
        get() = sequenceOf(
            SignInContract.UiState(
                isLoading = true,
                list = emptyList(),
            ),
            SignInContract.UiState(
                isLoading = false,
                list = emptyList(),
            ),
            SignInContract.UiState(
                isLoading = false,
                list = listOf("Item 1", "Item 2", "Item 3")
            ),
        )
}