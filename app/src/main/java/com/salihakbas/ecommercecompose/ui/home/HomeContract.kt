package com.salihakbas.ecommercecompose.ui.home

import com.salihakbas.ecommercecompose.domain.model.Category
import com.salihakbas.ecommercecompose.domain.model.Product

object HomeContract {
    data class UiState(
        val isLoading: Boolean = false,
        val list: List<String> = emptyList(),
        val userName: String = "",
        val userSurname: String = "",
        val productList: List<Product> = emptyList(),
        val allProducts: List<Product> = emptyList(),
        val categoryList: List<Category> = emptyList(),
        val categoryProducts: List<Product> = emptyList(),
        val errorMessage: String? = null,
    )

    sealed class UiAction

    sealed class UiEffect {
        data object NavigateToSearch : UiEffect()
        data object NavigateToDiscount : UiEffect()
        data object NavigateToProducts : UiEffect()
        data class ProductClick(val id: Int) : UiEffect()
        data class OnCategoryClick(val categoryName: String) : UiEffect()
    }
}