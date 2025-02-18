package com.salihakbas.ecommercecompose.ui.signup

object SignUpContract {
    data class UiState(
        val isLoading: Boolean = false,
        val email: String = "",
        val password: String = "",
        val confirmPassword: String = "",
        val name: String = "",
        val surname: String = "",
        val errorState: ErrorState? = null,
        val isCheckboxChecked: Boolean = false,
        val isPasswordLongEnough: Boolean = false,
        val isPasswordMatching: Boolean = false,
        val list: List<String> = emptyList(),
    ) {
        fun checkError(): UiState {
            return copy(
                errorState = ErrorState(
                    emailError = email.isEmpty(),
                    passwordError = password.isEmpty(),
                    confirmPasswordError = confirmPassword.isEmpty(),
                    nameError = name.isEmpty(),
                    surnameError = surname.isEmpty()

                )
            )
        }
        fun isSignUpValid(): Boolean {
            return isPasswordLongEnough  && isPasswordMatching &&
                    email.isNotEmpty() && name.isNotEmpty() && surname.isNotEmpty() && isCheckboxChecked
        }
    }

    data class ErrorState(
        val emailError: Boolean = false,
        val passwordError: Boolean = false,
        val confirmPasswordError: Boolean = false,
        val nameError: Boolean = false,
        val surnameError: Boolean = false,
    ) {
        fun hasError(): Boolean {
            return emailError || passwordError || confirmPasswordError || nameError || surnameError
        }
    }

    sealed class UiAction {
        data class OnEmailChange(val email: String) : UiAction()
        data class OnPasswordChange(val password: String) : UiAction()
        data class OnConfirmPasswordChange(val confirmPassword: String) : UiAction()
        data class OnNameChange(val name: String) : UiAction()
        data class OnSurnameChange(val surname: String) : UiAction()
        data class OnCheckboxToggle(val isCheckboxChecked: Boolean): UiAction()
        data object SignInClicked : UiAction()
        data object SignUpClick : UiAction()
    }

    sealed class UiEffect {
        data object NavigateToSignIn : UiEffect()
        data object ShowSignUpToast: UiEffect()
    }
}