package com.salihakbas.ecommercecompose.ui.profile

object ProfileContract {
    data class UiState(
        val isLoading: Boolean = false,
        val list: List<String> = emptyList(),
    )

    sealed class UiAction {
        data object SignOutClicked : UiAction()
        data object ChangePasswordClicked : UiAction()
    }

    sealed class UiEffect {
        data object NavigateToSignIn : UiEffect()
        data object NavigateToChangePassword : UiEffect()
    }
}