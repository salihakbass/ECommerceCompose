package com.salihakbas.ecommercecompose.ui.forgotpassword

object ForgotPasswordContract {
    data class UiState(
        val isLoading: Boolean = false,
        val email: String = "",
        val errorMessage: String? = null,
        val list: List<String> = emptyList(),
    )

    sealed class UiAction {
        data class OnEmailChange(val email: String) : UiAction()
        data object SendCodeClicked : UiAction()
    }

    sealed class UiEffect {
        data object NavigateToVerifyCode : UiEffect()
        data class ShowToast(val message: String) : UiEffect()

    }
}