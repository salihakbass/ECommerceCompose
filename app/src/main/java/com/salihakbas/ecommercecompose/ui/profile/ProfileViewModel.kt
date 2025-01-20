package com.salihakbas.ecommercecompose.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.salihakbas.ecommercecompose.common.Resource
import com.salihakbas.ecommercecompose.domain.repository.FirebaseAuthRepository
import com.salihakbas.ecommercecompose.ui.profile.ProfileContract.UiAction
import com.salihakbas.ecommercecompose.ui.profile.ProfileContract.UiEffect
import com.salihakbas.ecommercecompose.ui.profile.ProfileContract.UiState
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
class ProfileViewModel @Inject constructor(
    private val authRepository: FirebaseAuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _uiEffect by lazy { Channel<UiEffect>() }
    val uiEffect: Flow<UiEffect> by lazy { _uiEffect.receiveAsFlow() }

    fun onAction(uiAction: UiAction) {
        when (uiAction) {
            UiAction.SignOutClicked -> signOut()
            UiAction.ChangePasswordClicked -> changePassword()
        }
    }

    private fun changePassword() = viewModelScope.launch {
        emitUiEffect(UiEffect.NavigateToChangePassword)
    }

    private fun signOut() = viewModelScope.launch {
        when (val result = authRepository.signOut()) {
            is Resource.Success -> {
                emitUiEffect(UiEffect.NavigateToSignIn)
            }

            is Resource.Error -> {
                // Handle error
            }
        }
    }

    private fun updateUiState(block: UiState.() -> UiState) {
        _uiState.update(block)
    }

    private suspend fun emitUiEffect(uiEffect: UiEffect) {
        _uiEffect.send(uiEffect)
    }
}