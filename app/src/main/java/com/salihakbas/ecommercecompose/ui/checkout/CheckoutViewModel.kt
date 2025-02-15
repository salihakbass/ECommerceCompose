package com.salihakbas.ecommercecompose.ui.checkout

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.salihakbas.ecommercecompose.data.model.Address
import com.salihakbas.ecommercecompose.data.source.local.MainDao
import com.salihakbas.ecommercecompose.ui.checkout.CheckoutContract.UiAction
import com.salihakbas.ecommercecompose.ui.checkout.CheckoutContract.UiEffect
import com.salihakbas.ecommercecompose.ui.checkout.CheckoutContract.UiState
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
class CheckoutViewModel @Inject constructor(
    private val mainDao: MainDao
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _uiEffect by lazy { Channel<UiEffect>() }
    val uiEffect: Flow<UiEffect> by lazy { _uiEffect.receiveAsFlow() }

    init {
        loadAddresses()
    }

    fun onAction(uiAction: UiAction) {
        when (uiAction) {
            is UiAction.AddAddress -> addAddress(uiAction.address)
            is UiAction.OnTitleChange -> updateUiState { copy(title = uiAction.title) }
            is UiAction.OnDescriptionChange -> updateUiState { copy(description = uiAction.description) }
            is UiAction.OnCityChange -> updateUiState { copy(city = uiAction.city) }
            is UiAction.OnDistrictChange -> updateUiState { copy(district = uiAction.district) }
            is UiAction.OnCodeChange -> updateUiState { copy(code = uiAction.code) }
        }
    }

    private fun addAddress(address: Address) {
        viewModelScope.launch {
            mainDao.insertAddress(address)
            val addresses = mainDao.getAllAddress()
            updateUiState { copy(address = addresses) }
        }
    }

    private fun loadAddresses() {
        viewModelScope.launch {
            val addresses = mainDao.getAllAddress()
            updateUiState { copy(address = addresses) }
        }
    }

    private fun updateUiState(block: UiState.() -> UiState) {
        _uiState.update(block)
    }

    private suspend fun emitUiEffect(uiEffect: UiEffect) {
        _uiEffect.send(uiEffect)
    }
}
