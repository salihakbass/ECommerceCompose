package com.salihakbas.ecommercecompose.ui.home

import com.salihakbas.ecommercecompose.data.source.remote.Category
import com.salihakbas.ecommercecompose.domain.model.Product

object HomeContract {
    data class UiState(
        val isLoading: Boolean = false,
        val list: List<String> = emptyList(),
        val userName: String = "",
        val userSurname: String = "",
        val productList: List<Product> = emptyList(),
        val categoryList:List<Category> = emptyList(),
        val errorMessage: String? = null
    )

    sealed class UiAction {

        data object FetchProducts : UiAction()
    }

    sealed class UiEffect
}