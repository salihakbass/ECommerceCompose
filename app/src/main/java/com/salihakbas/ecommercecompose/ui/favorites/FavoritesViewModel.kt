package com.salihakbas.ecommercecompose.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.salihakbas.ecommercecompose.data.repository.FavoriteRepository
import com.salihakbas.ecommercecompose.ui.favorites.FavoritesContract.UiAction
import com.salihakbas.ecommercecompose.ui.favorites.FavoritesContract.UiEffect
import com.salihakbas.ecommercecompose.ui.favorites.FavoritesContract.UiState
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
class FavoritesViewModel @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _uiEffect by lazy { Channel<UiEffect>() }
    val uiEffect: Flow<UiEffect> by lazy { _uiEffect.receiveAsFlow() }

    init {
        getFavoriteProducts()
    }

    fun onAction(uiAction: UiAction) {
        when(uiAction) {
            is UiAction.RemoveFavorite -> deleteFavoriteProduct(uiAction.productId)
            is UiAction.DeleteAllFavorites -> deleteAllFavoriteProducts()
        }
    }

    private fun getFavoriteProducts() = viewModelScope.launch {
        val favoriteProducts = favoriteRepository.getAllFavorites()
        updateUiState { copy(favoriteProducts = favoriteProducts) }
    }

    private fun deleteFavoriteProduct(productId: Int) = viewModelScope.launch {
        favoriteRepository.removeFavoriteProduct(productId)
        getFavoriteProducts()
    }

    private fun deleteAllFavoriteProducts() = viewModelScope.launch {
        favoriteRepository.deleteAllFavoriteProducts()
        getFavoriteProducts()
    }

    private fun updateUiState(block: UiState.() -> UiState) {
        _uiState.update(block)
    }

    private suspend fun emitUiEffect(uiEffect: UiEffect) {
        _uiEffect.send(uiEffect)
    }
}