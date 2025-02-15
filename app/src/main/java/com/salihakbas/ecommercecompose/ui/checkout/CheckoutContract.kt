package com.salihakbas.ecommercecompose.ui.checkout

import com.salihakbas.ecommercecompose.data.model.Address

object CheckoutContract {

    data class UiState(
        val isLoading: Boolean = false,
        val list: List<String> = emptyList(),
        val error: String = "",
        val totalPrice: Double = 0.0,
        val address: List<Address> = emptyList(),
        val title: String = "",
        val description: String = "",
        val city: String = "",
        val district: String = "",
        val code: String = ""
    )

    sealed interface UiAction {
        data class AddAddress(val address: Address) : UiAction
        data class OnTitleChange(val title: String) : UiAction
        data class OnDescriptionChange(val description: String) : UiAction
        data class OnCityChange(val city: String) : UiAction
        data class OnDistrictChange(val district: String) : UiAction
        data class OnCodeChange(val code: String) : UiAction
    }

    sealed interface UiEffect


}