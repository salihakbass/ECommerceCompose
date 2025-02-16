package com.salihakbas.ecommercecompose.ui.checkout

import com.salihakbas.ecommercecompose.data.model.Address
import com.salihakbas.ecommercecompose.data.model.CreditCard

object CheckoutContract {

    data class UiState(
        val isLoading: Boolean = false,
        val list: List<String> = emptyList(),
        val error: String = "",
        val totalPrice: Double = 0.0,
        val address: List<Address> = emptyList(),
        val creditCards: List<CreditCard> = emptyList(),
        val title: String = "",
        val description: String = "",
        val city: String = "",
        val district: String = "",
        val code: String = "",
        val cardName: String = "",
        val cardNumber: String = "",
        val cardDate: String = "",
        val cardCvv: String = "",
        val cardOwnerName: String = ""
    )

    sealed interface UiAction {
        data class AddAddress(val address: Address) : UiAction
        data class AddCreditCard(val creditCard: CreditCard) : UiAction
        data class OnTitleChange(val title: String) : UiAction
        data class OnDescriptionChange(val description: String) : UiAction
        data class OnCityChange(val city: String) : UiAction
        data class OnDistrictChange(val district: String) : UiAction
        data class OnCodeChange(val code: String) : UiAction
        data class OnCardNameChange(val cardName: String) : UiAction
        data class OnCardNumberChange(val cardNumber: String) : UiAction
        data class OnCardDateChange(val cardDate: String) : UiAction
        data class OnCardCvvChange(val cardCvv: String) : UiAction
        data class OnCardOwnerNameChange(val cardOwnerName: String) : UiAction
    }

    sealed interface UiEffect


}