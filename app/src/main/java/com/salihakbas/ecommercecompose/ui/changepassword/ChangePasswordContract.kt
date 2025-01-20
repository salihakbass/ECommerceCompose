package com.salihakbas.ecommercecompose.ui.changepassword

object ChangePasswordContract {
    data class UiState(
        val isLoading: Boolean = false,
        val oldPassword: String = "",
        val newPassword: String = "",
        val newPasswordConfirm: String = "",
        val isPasswordLongEnough: Boolean = false,
        val hasNumber: Boolean = false,
        val hasLetter: Boolean = false,
        val isPasswordMatching: Boolean = false,
        val errorState: ErrorState? = null,
        val list: List<String> = emptyList(),
    ) {
        fun checkError(): UiState {
            return copy(
                errorState = ErrorState(
                    passwordError = oldPassword.isEmpty(),
                    confirmPasswordError = newPassword.isEmpty()
                )
            )
        }

        fun isSignUpValid(): Boolean {
            return isPasswordLongEnough && hasNumber && hasLetter && isPasswordMatching
        }
    }

    data class ErrorState(
        val passwordError: Boolean = false,
        val confirmPasswordError: Boolean = false
    ) {
        fun hasError(): Boolean {
            return passwordError || confirmPasswordError
        }
    }

    sealed class UiAction {
        data class ChangeOldPassword(val oldPassword: String) : UiAction()
        data class ChangeNewPassword(val newPassword: String) : UiAction()
        data class ChangeNewPasswordConfirm(val newPasswordConfirm: String) : UiAction()
        data object ChangePassword : UiAction()
    }

    sealed class UiEffect {
        data object NavigateToProfile : UiEffect()
        data object ShowErrorToast : UiEffect()
        data object ShowToast : UiEffect()
    }
}