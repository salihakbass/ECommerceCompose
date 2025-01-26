package com.salihakbas.ecommercecompose.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.FirebaseDatabase
import com.salihakbas.ecommercecompose.common.Resource
import com.salihakbas.ecommercecompose.domain.usecase.FetchCategoriesUseCase
import com.salihakbas.ecommercecompose.domain.usecase.FetchProductsUseCase
import com.salihakbas.ecommercecompose.domain.usecase.SearchProductsUseCase
import com.salihakbas.ecommercecompose.ui.home.HomeContract.UiAction
import com.salihakbas.ecommercecompose.ui.home.HomeContract.UiEffect
import com.salihakbas.ecommercecompose.ui.home.HomeContract.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fetchProductsUseCase: FetchProductsUseCase,
    private val fetchCategoriesUseCase: FetchCategoriesUseCase,
    private val searchProductsUseCase: SearchProductsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _uiEffect by lazy { Channel<UiEffect>() }
    val uiEffect: Flow<UiEffect> by lazy { _uiEffect.receiveAsFlow() }

    init {
        getCategories()
        fetchProducts()
    }

    fun onAction(uiAction: UiAction) {
        when (uiAction) {
            is UiAction.OnQueryChanged -> {
                updateUiState { copy(query = uiAction.query) }
                if (uiAction.query.isNotEmpty()) {
                    searchProducts(query = uiAction.query)
                } else {
                    updateUiState { copy(productList = allProducts) }
                }
            }
        }
    }

    private fun searchProducts(query: String) = viewModelScope.launch {
        when (val result = searchProductsUseCase(query)) {
            is Resource.Success -> updateUiState {
                copy(
                    isLoading = false,
                    productList = result.data
                )
            }

            is Resource.Error -> updateUiState {
                copy(
                    isLoading = false,
                    errorMessage = result.message
                )
            }
        }
    }

    private fun getCategories() = viewModelScope.launch {
        fetchCategoriesUseCase.invoke()
            .onStart { updateUiState { copy(isLoading = true) } }
            .onCompletion { updateUiState { copy(isLoading = false) } }
            .collect { result ->
                when (result) {
                    is Resource.Success -> updateUiState { copy(categoryList = result.data) }
                    is Resource.Error -> updateUiState { copy(errorMessage = result.message) }
                }
            }
    }

    fun filterProductsByCategory(categoryName: String?) {
        val allProducts = uiState.value.allProducts
        val filteredProducts = if (categoryName == null || categoryName == "Tümü") {
            allProducts
        } else {
            allProducts.filter { it.category.equals(categoryName, ignoreCase = true) }
        }
        updateUiState { copy(productList = filteredProducts) }
    }

    private fun fetchProducts() = viewModelScope.launch {
        updateUiState { copy(isLoading = true) }
        when (val result = fetchProductsUseCase()) {
            is Resource.Success -> {
                updateUiState {
                    copy(
                        isLoading = false,
                        productList = result.data,
                        allProducts = result.data
                    )
                }
            }

            is Resource.Error -> {
                updateUiState { copy(isLoading = false) }
            }
        }
    }

    fun fetchUserFromRealtimeDatabase(userId: String) {
        updateUiState { copy(isLoading = true) }

        val database = FirebaseDatabase.getInstance().reference
        database.child("users").child(userId).get()
            .addOnSuccessListener { snapshot ->
                if (snapshot.exists()) {
                    val name = snapshot.child("name").getValue(String::class.java) ?: ""
                    val surname = snapshot.child("surname").getValue(String::class.java) ?: ""

                    updateUiState {
                        copy(
                            isLoading = false,
                            userName = name,
                            userSurname = surname
                        )
                    }
                } else {
                    updateUiState { copy(isLoading = false) }
                }
            }
            .addOnFailureListener { e ->
                updateUiState { copy(isLoading = false) }
            }
    }


    private fun updateUiState(block: UiState.() -> UiState) {
        _uiState.update(block)
    }

    private suspend fun emitUiEffect(uiEffect: UiEffect) {
        _uiEffect.send(uiEffect)
    }
}