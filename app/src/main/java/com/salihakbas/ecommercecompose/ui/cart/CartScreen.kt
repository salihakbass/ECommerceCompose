package com.salihakbas.ecommercecompose.ui.cart

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import com.salihakbas.ecommercecompose.domain.model.Product
import com.salihakbas.ecommercecompose.ui.cart.CartContract.UiAction
import com.salihakbas.ecommercecompose.ui.cart.CartContract.UiEffect
import com.salihakbas.ecommercecompose.ui.cart.CartContract.UiState
import com.salihakbas.ecommercecompose.ui.components.EmptyScreen
import com.salihakbas.ecommercecompose.ui.components.LoadingBar
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Composable
fun CartScreen(
    uiState: UiState,
    uiEffect: Flow<UiEffect>,
    onAction: (UiAction) -> Unit
) {
    val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
    when {
        uiState.isLoading -> LoadingBar()
        uiState.list.isNotEmpty() -> EmptyScreen()
        else -> CartContent(
            uiState,
            onAction,
            userId
        )
    }
}

@Composable
fun CartContent(
    uiState: UiState,
    onAction: (UiAction) -> Unit,
    userId: String

) {
    LazyColumn {
        items(uiState.products) { product ->
            CartCard(product, onAction,userId)
        }
    }
    Button(onClick = { onAction(UiAction.ClearCart(userId)) }) {
        Text(
            text = "Clear Cart"
        )
    }
}

@Composable
fun CartCard(
    product: Product,
    onAction: (UiAction) -> Unit,
    userId: String
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = product.title
        )
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = null,
            modifier = Modifier.size(36.dp)
                .clickable {
                    onAction(UiAction.DeleteFromCart(product.id,userId))
                }
        )


    }
}

@Preview(showBackground = true)
@Composable
fun CartScreenPreview(
    @PreviewParameter(CartScreenPreviewProvider::class) uiState: UiState,
) {
    CartScreen(
        uiState = uiState,
        uiEffect = emptyFlow(),
        onAction = {}
    )
}