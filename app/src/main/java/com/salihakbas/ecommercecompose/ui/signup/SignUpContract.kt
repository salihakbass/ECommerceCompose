package com.salihakbas.ecommercecompose.ui.signup

object SignUpContract {
    data class UiState(
        val isLoading: Boolean = false,
        val list: List<String> = emptyList(),
    )

    sealed class UiAction

    sealed class UiEffect
}