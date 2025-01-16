package com.salihakbas.ecommercecompose.ui.forgotpassword

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

class ForgotPasswordScreenPreviewProvider :
    PreviewParameterProvider<ForgotPasswordContract.UiState> {
    override val values: Sequence<ForgotPasswordContract.UiState>
        get() = sequenceOf(
            ForgotPasswordContract.UiState(
                isLoading = true,
                list = emptyList(),
            ),
            ForgotPasswordContract.UiState(
                isLoading = false,
                list = emptyList(),
            ),
            ForgotPasswordContract.UiState(
                isLoading = false,
                list = listOf("Item 1", "Item 2", "Item 3")
            ),
        )
}