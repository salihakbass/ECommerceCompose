package com.salihakbas.ecommercecompose.ui.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.salihakbas.ecommercecompose.common.Resource
import com.salihakbas.ecommercecompose.domain.usecase.GetCartProductsUseCase
import com.salihakbas.ecommercecompose.ui.cart.CartContract.UiAction
import com.salihakbas.ecommercecompose.ui.cart.CartContract.UiEffect
import com.salihakbas.ecommercecompose.ui.cart.CartContract.UiState
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
class CartViewModel @Inject constructor(
    private val getCartProductsUseCase: GetCartProductsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _uiEffect by lazy { Channel<UiEffect>() }
    val uiEffect: Flow<UiEffect> by lazy { _uiEffect.receiveAsFlow() }

    fun onAction(uiAction: UiAction) {

    }

     fun getCartProducts(userId: String) = viewModelScope.launch {
        updateUiState { copy(isLoading = true) }

        when(val result = getCartProductsUseCase(userId)) {
            is Resource.Success -> {
                updateUiState { copy(products = result.data ?: emptyList(), isLoading = false) }
            }
            is Resource.Error -> {
                updateUiState { copy(error = result.message ?: "Error", isLoading = false) }
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