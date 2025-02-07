package com.salihakbas.ecommercecompose.ui.signin

import com.salihakbas.ecommercecompose.ui.signup.SignUpContract.UiAction

object SignInContract {
    data class UiState(
        val isLoading: Boolean = false,
        val email: String = "",
        val password: String = "",
        var visibility: Boolean = false,
        val list: List<String> = emptyList(),
        val isCheckboxChecked: Boolean = false,
        )

    sealed class UiAction {
        data class OnEmailChange(val email: String) : UiAction()
        data class OnPasswordChange(val password: String) : UiAction()
        data object SignInClicked : UiAction()
        data object GoogleSignInClicked : UiAction()
        data object ForgotPasswordClicked: UiAction()
        data object SignUpClicked: UiAction()
        data class OnCheckboxToggle(val isCheckboxChecked: Boolean): UiAction()
    }

    sealed class UiEffect {
        data object NavigateToHome : UiEffect()
        data object NavigateToSignUp : UiEffect()
        data object NavigateToForgotPassword : UiEffect()
        data object ShowToast : UiEffect()

    }
}