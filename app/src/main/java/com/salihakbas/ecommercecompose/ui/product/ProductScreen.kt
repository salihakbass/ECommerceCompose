package com.salihakbas.ecommercecompose.ui.product

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.salihakbas.ecommercecompose.common.collectWithLifecycle
import com.salihakbas.ecommercecompose.ui.components.EmptyScreen
import com.salihakbas.ecommercecompose.ui.components.LoadingBar
import com.salihakbas.ecommercecompose.ui.components.ProductGrid
import com.salihakbas.ecommercecompose.ui.product.ProductContract.UiAction
import com.salihakbas.ecommercecompose.ui.product.ProductContract.UiEffect
import com.salihakbas.ecommercecompose.ui.product.ProductContract.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow


@Composable
fun ProductScreen(
    uiState: UiState,
    uiEffect: Flow<UiEffect>,
    onAction: (UiAction) -> Unit,
    navigateBack: () -> Unit
) {
    uiEffect.collectWithLifecycle { effect ->
        when (effect) {
            is UiEffect.NavigateBack -> navigateBack()
        }

    }

    when {
        uiState.isLoading -> LoadingBar()
        uiState.list.isNotEmpty() -> EmptyScreen()
        else -> ProductContent(
            uiState = uiState,
            onAction = onAction,
            navigateBack = navigateBack
        )
    }
}

@Composable
fun ProductContent(
    uiState: UiState,
    onAction: (UiAction) -> Unit,
    navigateBack: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = null,
                modifier = Modifier
                    .size(36.dp)
                    .clickable {
                        navigateBack()
                    }
            )
            Text(
                text = "Tüm Ürünler",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }

        ProductGrid(product = uiState.productList)
    }


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
        navigateBack = {}
    )
}
