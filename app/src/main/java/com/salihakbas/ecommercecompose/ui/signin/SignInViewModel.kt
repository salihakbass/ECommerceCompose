package com.salihakbas.ecommercecompose.ui.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.salihakbas.ecommercecompose.common.Resource
import com.salihakbas.ecommercecompose.domain.repository.FirebaseAuthRepository
import com.salihakbas.ecommercecompose.ui.signin.SignInContract.UiAction
import com.salihakbas.ecommercecompose.ui.signin.SignInContract.UiEffect
import com.salihakbas.ecommercecompose.ui.signin.SignInContract.UiState
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
class SignInViewModel @Inject constructor(
    private val authRepository: FirebaseAuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _uiEffect by lazy { Channel<UiEffect>() }
    val uiEffect: Flow<UiEffect> by lazy { _uiEffect.receiveAsFlow() }

    fun onAction(uiAction: UiAction) {
        when (uiAction) {
            is UiAction.OnEmailChange -> updateUiState { copy(email = uiAction.email) }
            is UiAction.OnPasswordChange -> updateUiState { copy(password = uiAction.password) }
            UiAction.GoogleSignInClicked -> googleSignIn()
            UiAction.SignInClicked -> signIn()
            UiAction.ForgotPasswordClicked -> forgotPassword()
            UiAction.SignUpClicked -> navigateToSignUp()
            is UiAction.OnCheckboxToggle -> updateUiState { copy(isCheckboxChecked = uiAction.isCheckboxChecked) }
        }
    }

    private fun navigateToSignUp() = viewModelScope.launch {
        emitUiEffect(UiEffect.NavigateToSignUp)
    }

    private fun forgotPassword() = viewModelScope.launch {
        emitUiEffect(UiEffect.NavigateToForgotPassword)
    }

    private fun signIn() = viewModelScope.launch {
        when (val result = authRepository.signInWithEmailAndPassword(
            email = uiState.value.email,
            password = uiState.value.password
        )) {
            is Resource.Success -> {
                emitUiEffect(UiEffect.NavigateToHome)
            }

            is Resource.Error -> {
                emitUiEffect(UiEffect.ShowToast)
            }
        }
    }

    private fun googleSignIn() {

    }

    private fun updateUiState(block: UiState.() -> UiState) {
        _uiState.update(block)
    }

    private suspend fun emitUiEffect(uiEffect: UiEffect) {
        _uiEffect.send(uiEffect)
    }
}