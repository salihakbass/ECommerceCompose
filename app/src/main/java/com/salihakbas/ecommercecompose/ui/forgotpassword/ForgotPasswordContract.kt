package com.salihakbas.ecommercecompose.ui.forgotpassword

object ForgotPasswordContract {
    data class UiState(
        val isLoading: Boolean = false,
        val list: List<String> = emptyList(),
    )

    sealed class UiAction

    sealed class UiEffect
}