package com.salihakbas.ecommercecompose.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.salihakbas.ecommercecompose.common.Resource
import com.salihakbas.ecommercecompose.domain.usecase.GetProductDetailUseCase
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
    private val getProductDetailUseCase: GetProductDetailUseCase
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
    }

    private fun getProductDetail(productId: Int) = viewModelScope.launch {
        when (val resource = getProductDetailUseCase(productId)) {
            is Resource.Success -> {
                updateUiState { copy(product = resource.data) }
            }

            is Resource.Error -> {
                updateUiState { copy(error = resource.message) }
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