package com.salihakbas.ecommercecompose.ui.changepassword

object ChangePasswordContract {
    data class UiState(
        val isLoading: Boolean = false,
        val list: List<String> = emptyList(),
    )

    sealed class UiAction

    sealed class UiEffect
}