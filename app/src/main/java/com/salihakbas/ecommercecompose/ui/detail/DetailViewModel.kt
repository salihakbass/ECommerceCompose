package com.salihakbas.ecommercecompose.ui.detail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.salihakbas.ecommercecompose.common.Resource
import com.salihakbas.ecommercecompose.domain.usecase.AddToCartUseCase
import com.salihakbas.ecommercecompose.domain.usecase.GetProductDetailUseCase
import com.salihakbas.ecommercecompose.domain.usecase.GetProductsByCategoryUseCase
import com.salihakbas.ecommercecompose.ui.detail.DetailContract.UiAction
import com.salihakbas.ecommercecompose.ui.detail.DetailContract.UiEffect
import com.salihakbas.ecommercecompose.ui.detail.DetailContract.UiState
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
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getProductDetailUseCase: GetProductDetailUseCase,
    private val getProductsByCategoryUseCase: GetProductsByCategoryUseCase,
    private val addToCartUseCase: AddToCartUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _uiEffect by lazy { Channel<UiEffect>() }
    val uiEffect: Flow<UiEffect> by lazy { _uiEffect.receiveAsFlow() }

    init {
        val productId = savedStateHandle.get<Int>("productId") ?: 0
        getProductDetail(productId)
    }

    fun onAction(uiAction: UiAction) {
        when (uiAction) {
            is UiAction.AddToCart -> addToCart(uiAction.userId, uiAction.productId)
        }
    }

    private fun getProductDetail(productId: Int) = viewModelScope.launch {
        when (val resource = getProductDetailUseCase(productId)) {
            is Resource.Success -> {
                updateUiState { copy(product = resource.data) }
                resource.data.category?.let { getSimilarProducts(it) }
            }

            is Resource.Error -> {
                updateUiState { copy(error = resource.message) }
            }
        }
    }

    private fun getSimilarProducts(category: String) = viewModelScope.launch {
        val currentProductId = _uiState.value.product?.id

        when(val resource = getProductsByCategoryUseCase(category)) {
            is Resource.Success -> {
                val filteredList = resource.data.filter { it.id != currentProductId }
                updateUiState { copy(similarProducts = filteredList) }
            }
            is Resource.Error -> {
                updateUiState { copy(error = resource.message) }
            }
        }
    }

    private fun addToCart(userId: String,productId: Int) = viewModelScope.launch {
        updateUiState { copy(isLoading = true) }

        when(val resource = addToCartUseCase(userId, productId)) {
            is Resource.Success -> {
                updateUiState { copy(isLoading = false) }
                Log.d("Cart", "Product added to cart: ${resource.data}")
                Log.d("Cart", "Product added to cart: $productId")
            }

            is Resource.Error -> {
                updateUiState { copy(isLoading = false, error = resource.message) }

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