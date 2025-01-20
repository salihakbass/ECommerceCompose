package com.salihakbas.ecommercecompose.ui.forgotpassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.salihakbas.ecommercecompose.common.Resource
import com.salihakbas.ecommercecompose.domain.repository.FirebaseAuthRepository
import com.salihakbas.ecommercecompose.ui.forgotpassword.ForgotPasswordContract.UiAction
import com.salihakbas.ecommercecompose.ui.forgotpassword.ForgotPasswordContract.UiEffect

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
class ForgotPasswordViewModel @Inject constructor(
    private val authRepository: FirebaseAuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ForgotPasswordContract.UiState())
    val uiState: StateFlow<ForgotPasswordContract.UiState> = _uiState.asStateFlow()

    private val _uiEffect by lazy { Channel<UiEffect>() }
    val uiEffect: Flow<UiEffect> by lazy { _uiEffect.receiveAsFlow() }

    fun onAction(uiAction: UiAction) {
        when (uiAction) {
            is UiAction.OnEmailChange -> updateUiState { copy(email = uiAction.email) }
            UiAction.SendCodeClicked -> sendCode()
        }
    }

    private fun sendCode() = viewModelScope.launch {
        updateUiState { copy(isLoading = true, errorMessage = null) }
        when (val result = authRepository.sendPasswordResetEmail(uiState.value.email)) {
            is Resource.Success -> {
                emitUiEffect(UiEffect.NavigateToVerifyCode)
            }
            is Resource.Error -> {
                emitUiEffect(
                    UiEffect.ShowToast(
                        result.message
                    )
                )
            }
        }
        updateUiState { copy(isLoading = false) }
    }

    private fun updateUiState(block: ForgotPasswordContract.UiState.() -> ForgotPasswordContract.UiState) {
        _uiState.update(block)
    }

    private suspend fun emitUiEffect(uiEffect: UiEffect) {
        _uiEffect.send(uiEffect)
    }
}