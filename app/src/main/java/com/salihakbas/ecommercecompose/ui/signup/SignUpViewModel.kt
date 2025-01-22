package com.salihakbas.ecommercecompose.ui.signup

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.FirebaseDatabase
import com.salihakbas.ecommercecompose.common.Resource
import com.salihakbas.ecommercecompose.domain.repository.FirebaseAuthRepository
import com.salihakbas.ecommercecompose.ui.signup.SignUpContract.UiAction
import com.salihakbas.ecommercecompose.ui.signup.SignUpContract.UiEffect
import com.salihakbas.ecommercecompose.ui.signup.SignUpContract.UiState
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
class SignUpViewModel @Inject constructor(
    private val authRepository: FirebaseAuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _uiEffect by lazy { Channel<UiEffect>() }
    val uiEffect: Flow<UiEffect> by lazy { _uiEffect.receiveAsFlow() }

    fun onAction(uiAction: UiAction) {
        when (uiAction) {
            is UiAction.OnEmailChange -> updateUiState { copy(email = uiAction.email) }
            is UiAction.OnPasswordChange -> {
                updateUiState { copy(password = uiAction.password) }
                validatePassword(uiAction.password, uiState.value.confirmPassword)
            }
            is UiAction.OnConfirmPasswordChange -> {
                updateUiState { copy(confirmPassword = uiAction.confirmPassword) }
                validatePassword(uiAction.confirmPassword, uiState.value.password)
            }
            is UiAction.OnNameChange -> updateUiState { copy(name = uiAction.name) }
            is UiAction.OnSurnameChange -> updateUiState { copy(surname = uiAction.surname) }
            is UiAction.SignUpClick -> signUp()
            is UiAction.OnCheckboxToggle -> updateUiState { copy(isCheckboxChecked = uiAction.isCheckboxChecked) }
            is UiAction.SignInClicked -> navigateToSignIn()
        }
    }

    private fun navigateToSignIn() = viewModelScope.launch {
        emitUiEffect(UiEffect.NavigateToSignIn)
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

    private fun signUp() = viewModelScope.launch {
        val updatedState = uiState.value.checkError()
        updateUiState { checkError() }

        if (uiState.value.errorState?.hasError() == true) {
            emitUiEffect(UiEffect.ShowSignUpToast)
            return@launch
        }

        if (!updatedState.isSignUpValid()) {
            emitUiEffect(UiEffect.ShowSignUpToast)
            return@launch
        }

        when (val result = authRepository.createUserWithEmailAndPassword(
            name = uiState.value.name,
            surname = uiState.value.surname,
            email = uiState.value.email,
            password = uiState.value.password
        )) {
            is Resource.Success -> {
                saveUserToRealtimeDatabase(result.data, uiState.value.name, uiState.value.surname)
                emitUiEffect(UiEffect.NavigateToSignIn)
            }

            is Resource.Error -> {

            }
        }
    }

    private fun saveUserToRealtimeDatabase(userId: String, name: String, surname: String) {
        val database = FirebaseDatabase.getInstance().reference
        val userMap = mapOf(
            "name" to name,
            "surname" to surname
        )

        database.child("users").child(userId).setValue(userMap)
            .addOnSuccessListener {
                Log.d("RealtimeDatabase", "Kullanıcı başarıyla kaydedildi.")
            }
            .addOnFailureListener { e ->
                Log.e("RealtimeDatabase", "Hata oluştu: ${e.message}")
            }
    }

    private fun updateUiState(block: UiState.() -> UiState) {
        _uiState.update(block)
    }

    private suspend fun emitUiEffect(uiEffect: UiEffect) {
        _uiEffect.send(uiEffect)
    }
}