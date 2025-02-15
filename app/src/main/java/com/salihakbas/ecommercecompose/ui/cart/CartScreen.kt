package com.salihakbas.ecommercecompose.ui.cart

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.firebase.auth.FirebaseAuth
import com.salihakbas.ecommercecompose.R
import com.salihakbas.ecommercecompose.common.collectWithLifecycle
import com.salihakbas.ecommercecompose.domain.model.Product
import com.salihakbas.ecommercecompose.ui.cart.CartContract.UiAction
import com.salihakbas.ecommercecompose.ui.cart.CartContract.UiEffect
import com.salihakbas.ecommercecompose.ui.cart.CartContract.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Composable
fun CartScreen(
    uiState: UiState,
    uiEffect: Flow<UiEffect>,
    onAction: (UiAction) -> Unit,
    onBackClick: () -> Unit,
    navigateCheckout: () -> Unit
) {
    uiEffect.collectWithLifecycle { effect ->
        when(effect) {
            is UiEffect.NavigateCheckout -> navigateCheckout()
        }
    }
    val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
    Scaffold(
        topBar = {
            TopBarCart(
                onBackClick = onBackClick,
                clearCartText = stringResource(R.string.clear_cart_text),
                onClearClick = {
                    onAction(UiAction.ClearCart(userId))
                }
            )
        },
        bottomBar = {
            BottomBarCart(
                uiState = uiState,
                onCheckoutClick = navigateCheckout
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            CartContent(
                uiState,
                onAction,
                userId
            )
        }
    }
}

@Composable
fun CartContent(
    uiState: UiState,
    onAction: (UiAction) -> Unit,
    userId: String
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(uiState.products, key = { it.id }) { product ->
                CartCard(
                    product = product,
                    onAction = onAction,
                    userId = userId,
                    modifier = Modifier.animateItem(fadeInSpec = null, fadeOutSpec = null)
                )
            }
        }
    }
}

@Composable
fun CartCard(
    product: Product,
    onAction: (UiAction) -> Unit,
    userId: String,
    modifier: Modifier = Modifier
) {
    var isVisible by remember { mutableStateOf(true) }
    var showDialog by remember { mutableStateOf(false) }
    var quantity by remember { mutableStateOf(1) }

    AnimatedVisibility(
        visible = isVisible,
        exit = slideOutHorizontally(targetOffsetX = { -it }) + fadeOut()
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current).data(product.imageOne)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier.size(100.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = product.title,
                        fontSize = 20.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = product.description,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        color = Color.Gray
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        val totalPrice = if (product.saleState) {
                            (product.salePrice ?: 0.0) * quantity
                        } else {
                            product.price * quantity
                        }

                        Text(
                            text = "₺%.3f".format(totalPrice),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = null,
                            modifier = Modifier
                                .size(36.dp)
                                .clickable {
                                    showDialog = true
                                }
                        )
                    }
                    if (product.saleState) {
                        Text(
                            text = "₺${product.price}",
                            fontSize = 16.sp,
                            textDecoration = TextDecoration.LineThrough,
                            color = Color.Red
                        )
                    }
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_remove),
                            contentDescription = null,
                            modifier = Modifier
                                .clickable {
                                    if (quantity > 1) quantity-- else showDialog = true
                                }
                        )
                        Text(
                            text = "$quantity"
                        )
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null,
                            modifier = Modifier
                                .clickable {
                                    quantity++
                                }
                        )
                    }
                }
            }
        }
    }
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("${product.title} adlı ürünü silmek istediğinize emin misiniz?") },
            confirmButton = {
                Button(onClick = {
                    isVisible = false
                    onAction(UiAction.DeleteFromCart(product.id, userId))
                    showDialog = false
                }) {
                    Text(stringResource(R.string.yes_text))
                }
            },
            dismissButton = {
                Button(onClick = { showDialog = false }) {
                    Text(stringResource(R.string.no_text))
                }
            }
        )
    }
}

@Composable
fun BottomBarCart(
    uiState: UiState,
    onCheckoutClick: () -> Unit
) {
    val totalPrice = uiState.products.sumOf { product ->
        if (product.saleState) (product.salePrice ?: 0.0) else product.price
    }
    val totalPriceWithoutDiscount = uiState.products.sumOf { product ->
        product.price
    }
    val isDiscount = uiState.products.any { it.saleState }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.total_price_text),
                fontSize = 16.sp,
                color = Color.DarkGray
            )
            Text(
                text = "₺%.3f".format(totalPrice)
            )
            if (isDiscount) {
                Text(
                    text = "₺%.3f".format(totalPriceWithoutDiscount),
                    fontSize = 20.sp,
                    textDecoration = TextDecoration.LineThrough,
                    color = Color.Red
                )
            }
        }
        Button(
            onClick = onCheckoutClick,
            colors = ButtonColors(
                containerColor = Color.Black,
                contentColor = Color.White,
                disabledContainerColor = Color.Gray,
                disabledContentColor = Color.DarkGray
            ),
            modifier = Modifier.height(50.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ShoppingCart,
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = stringResource(R.string.confirm_cart_text),
                fontSize = 20.sp
            )
        }
    }
}

@Composable
fun TopBarCart(
    onBackClick: () -> Unit,
    clearCartText: String,
    onClearClick: () -> Unit
) {
    var isVisible by remember { mutableStateOf(true) }
    var showDialog by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(onClick = onBackClick) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = null,
                modifier = Modifier.size(36.dp)
            )
        }
        Text(
            text = clearCartText,
            fontSize = 18.sp,
            color = Color.Blue,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier.clickable {
                showDialog = true
            }
        )
    }
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(stringResource(R.string.are_you_sure_clear_cart_text)) },
            confirmButton = {
                Button(onClick = {
                    isVisible = false
                    onClearClick()
                    showDialog = false
                }) {
                    Text(stringResource(R.string.yes_text))
                }
            },
            dismissButton = {
                Button(onClick = { showDialog = false }) {
                    Text(stringResource(R.string.no_text))
                }
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
        onAction = {},
        onBackClick = {},
        navigateCheckout = {}
    )
}