package com.salihakbas.ecommercecompose.ui.discount

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.salihakbas.ecommercecompose.ui.cart.CartScreenPreviewProvider
import com.salihakbas.ecommercecompose.ui.components.EmptyScreen
import com.salihakbas.ecommercecompose.ui.components.LoadingBar
import com.salihakbas.ecommercecompose.ui.discount.DiscountContract.UiAction
import com.salihakbas.ecommercecompose.ui.discount.DiscountContract.UiEffect
import com.salihakbas.ecommercecompose.ui.discount.DiscountContract.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Composable
fun DiscountScreen(
    uiState: UiState,
    uiEffect: Flow<UiEffect>,
    onAction: (UiAction) -> Unit,
) {
    when {
        uiState.isLoading -> LoadingBar()
        uiState.list.isNotEmpty() -> EmptyScreen()
        else -> DiscountContent()
    }
}

@Composable
fun DiscountContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

    }
}

@Preview(showBackground = true)
@Composable
fun DiscountScreenPreview(
    @PreviewParameter(CartScreenPreviewProvider::class) uiState: UiState,
) {
    DiscountScreen(
        uiState = uiState,
        uiEffect = emptyFlow(),
        onAction = {},
    )
}