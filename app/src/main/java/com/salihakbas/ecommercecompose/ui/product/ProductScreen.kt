package com.salihakbas.ecommercecompose.ui.product

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.salihakbas.ecommercecompose.ui.components.EmptyScreen
import com.salihakbas.ecommercecompose.ui.components.LoadingBar
import com.salihakbas.ecommercecompose.ui.product.ProductContract.UiAction
import com.salihakbas.ecommercecompose.ui.product.ProductContract.UiEffect
import com.salihakbas.ecommercecompose.ui.product.ProductContract.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow


@Composable
fun ProductScreen(
    uiState: UiState,
    uiEffect: Flow<UiEffect>,
    onAction: (UiAction) -> Unit
) {
    when {
        uiState.isLoading -> LoadingBar()
        uiState.list.isNotEmpty() -> EmptyScreen()
        else -> ProductContent(
            uiState = uiState,
            onAction = onAction
        )
    }
}

@Composable
fun ProductContent(uiState: UiState, onAction: (UiAction) -> Unit) {

}

@Preview(showBackground = true)
@Composable
fun ProductScreenPreview(
    @PreviewParameter(ProductScreenPreviewProvider::class) uiState: UiState,
) {
    ProductScreen(
        uiState = uiState,
        uiEffect = emptyFlow(),
        onAction = {},
    )
}
