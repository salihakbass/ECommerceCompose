package com.salihakbas.ecommercecompose.ui.changepassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.salihakbas.ecommercecompose.common.Resource
import com.salihakbas.ecommercecompose.domain.repository.FirebaseAuthRepository
import com.salihakbas.ecommercecompose.ui.changepassword.ChangePasswordContract.UiAction
import com.salihakbas.ecommercecompose.ui.changepassword.ChangePasswordContract.UiEffect
import com.salihakbas.ecommercecompose.ui.changepassword.ChangePasswordContract.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    private val authRepository: FirebaseAuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _uiEffect by lazy { Channel<UiEffect>() }
    val uiEffect: Flow<UiEffect> by lazy { _uiEffect.receiveAsFlow() }

    fun onAction(uiAction: UiAction) {
        when (uiAction) {
            is UiAction.ChangeOldPassword -> updateUiState { copy(oldPassword = uiAction.oldPassword) }

            is UiAction.ChangeNewPassword -> {
                updateUiState { copy(newPassword = uiAction.newPassword) }
                validatePassword(uiAction.newPassword, uiState.value.newPasswordConfirm)
            }

            is UiAction.ChangeNewPasswordConfirm -> {
                updateUiState { copy(newPasswordConfirm = uiAction.newPasswordConfirm) }
                validatePassword(uiAction.newPasswordConfirm, uiState.value.newPassword)
            }

            UiAction.ChangePassword -> changePassword()
        }
    }

    private fun changePassword() = viewModelScope.launch {
        updateUiState { checkError() }
        val currentUserEmail = authRepository.getCurrentUserEmail()
        if (currentUserEmail.isNullOrEmpty()) {
            emitUiEffect(UiEffect.ShowErrorToast)
            return@launch
        }
        if (uiState.value.errorState?.hasError() == true) {
            emitUiEffect(UiEffect.ShowErrorToast)
            return@launch
        }

        if (!uiState.value.isSignUpValid()) {
            emitUiEffect(UiEffect.ShowErrorToast)
            return@launch
        }
        authRepository.changePassword(
            email = currentUserEmail,
            oldPassword = uiState.value.oldPassword,
            newPassword = uiState.value.newPassword
        ).also { result ->
            when (result) {
                is Resource.Success -> {
                    emitUiEffect(UiEffect.ShowToast)
                    emitUiEffect(UiEffect.NavigateToProfile)
                }

                is Resource.Error -> {
                    emitUiEffect(UiEffect.ShowErrorToast)
                }
            }
        }
    }

    private fun validatePassword(password: String, confirmPassword: String) {
        val containsLetter = password.any { it.isLetter() }
        val containsDigit = password.any { it.isDigit() }
        updateUiState {
            copy(
                isPasswordLongEnough = password.length >= 6,
                hasLetter = containsLetter,
                hasNumber = containsDigit,
                isPasswordMatching = password == confirmPassword
            )
        }
    }

    private fun updateUiState(block: UiState.() -> UiState) {
        _uiState.update(block)
    }

    private suspend fun emitUiEffect(uiEffect: UiEffect) {
        _uiEffect.send(uiEffect)
    }
}