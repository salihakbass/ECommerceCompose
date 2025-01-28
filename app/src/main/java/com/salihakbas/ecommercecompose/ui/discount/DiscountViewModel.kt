package com.salihakbas.ecommercecompose.ui.discount

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.salihakbas.ecommercecompose.common.Resource
import com.salihakbas.ecommercecompose.domain.usecase.FetchProductsUseCase
import com.salihakbas.ecommercecompose.ui.discount.DiscountContract.UiAction
import com.salihakbas.ecommercecompose.ui.discount.DiscountContract.UiEffect
import com.salihakbas.ecommercecompose.ui.discount.DiscountContract.UiState
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
class DiscountViewModel @Inject constructor(
    private val fetchProductsUseCase: FetchProductsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _uiEffect by lazy { Channel<UiEffect>() }
    val uiEffect: Flow<UiEffect> by lazy { _uiEffect.receiveAsFlow() }

    init {
        fetchDiscountProducts()
    }

    private fun fetchDiscountProducts() = viewModelScope.launch {
        updateUiState { copy(isLoading = true) }
        when (val result = fetchProductsUseCase()) {
            is Resource.Success -> {
                val filteredProducts = result.data.filter { it.saleState }
                updateUiState {
                    copy(
                        isLoading = false,
                        discountList = filteredProducts
                    )
                }
            }
            is Resource.Error -> {
                updateUiState { copy(isLoading = false) }
            }
        }
    }

    fun onAction(uiAction: UiAction) {
    }

    private fun updateUiState(block: UiState.() -> UiState) {
        _uiState.update(block)
    }

    private suspend fun emitUiEffect(uiEffect: UiEffect) {
        _uiEffect.send(uiEffect)
    }
}