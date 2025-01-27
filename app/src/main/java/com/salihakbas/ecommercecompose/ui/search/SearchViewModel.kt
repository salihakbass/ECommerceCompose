package com.salihakbas.ecommercecompose.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.salihakbas.ecommercecompose.common.Resource
import com.salihakbas.ecommercecompose.domain.usecase.FetchProductsUseCase
import com.salihakbas.ecommercecompose.domain.usecase.SearchProductsUseCase
import com.salihakbas.ecommercecompose.ui.search.SearchContract.UiAction
import com.salihakbas.ecommercecompose.ui.search.SearchContract.UiEffect
import com.salihakbas.ecommercecompose.ui.search.SearchContract.UiState
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
class SearchViewModel @Inject constructor(
    private val fetchProductsUseCase: FetchProductsUseCase,
    private val searchProductsUseCase: SearchProductsUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _uiEffect by lazy { Channel<UiEffect>() }
    val uiEffect: Flow<UiEffect> by lazy { _uiEffect.receiveAsFlow() }

    init {
        fetchProducts()
    }

    private fun fetchProducts() = viewModelScope.launch{
        updateUiState { copy(isLoading = true) }

        when(val result = fetchProductsUseCase()) {
            is Resource.Success -> {
                updateUiState { copy(isLoading = false, suggestedProducts = result.data) }
            }
            is Resource.Error -> {
                updateUiState { copy(isLoading = false) }
            }
        }
    }

    private fun searchProducts(query: String) = viewModelScope.launch {
        when(val result = searchProductsUseCase(query)) {
            is Resource.Success -> {
                updateUiState { copy(isLoading = false, allProducts = result.data) }
            }
            is Resource.Error -> {
                updateUiState { copy(isLoading = false) }
            }
        }
    }

    fun onAction(uiAction: UiAction) {
        when (uiAction) {
            is UiAction.OnQueryChanged -> {
                updateUiState { copy(query = uiAction.query) }
                if (uiAction.query.isNotEmpty()) {
                    searchProducts(query = uiAction.query)
                } else {
                    updateUiState { copy(allProducts = allProducts) }
                }
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