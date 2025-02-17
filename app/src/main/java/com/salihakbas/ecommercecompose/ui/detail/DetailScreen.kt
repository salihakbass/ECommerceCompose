package com.salihakbas.ecommercecompose.ui.detail

import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.salihakbas.ecommercecompose.R
import com.salihakbas.ecommercecompose.common.collectWithLifecycle
import com.salihakbas.ecommercecompose.domain.model.Product
import com.salihakbas.ecommercecompose.navigation.Screen
import com.salihakbas.ecommercecompose.ui.detail.DetailContract.UiAction
import com.salihakbas.ecommercecompose.ui.detail.DetailContract.UiEffect
import com.salihakbas.ecommercecompose.ui.detail.DetailContract.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Composable
fun DetailScreen(
    uiState: UiState,
    uiEffect: Flow<UiEffect>,
    onAction: (UiAction) -> Unit,
    navController: NavController,
    onBackClick: () -> Unit,
    navigateToSearch: () -> Unit,
    userId: String
) {
    uiEffect.collectWithLifecycle { effect ->
        when (effect) {
            UiEffect.NavigateBack -> {
                onBackClick()
            }

            UiEffect.NavigateSearch -> {
                navigateToSearch()
            }
        }
    }
    Scaffold(
        topBar = {
            TopBar(
                onBackClick = { onBackClick() },
                onSearchClick = { navigateToSearch() }
            )
        },
        bottomBar = {
            BottomBar(
                priceText = uiState.product?.price.toString(),
                salePriceText = uiState.product?.salePrice.toString(),
                uiState = uiState,
                addToCartClick = {
                    uiState.product?.let { product ->
                        onAction(UiAction.AddToCart(userId, product.id))
                    }
                }
            )
        }
    ) { paddingValues ->
        DetailContent(
            uiState,
            Modifier.padding(paddingValues),
            navController,
            onAction
        )
    }
}

@Composable
fun DetailContent(
    uiState: UiState,
    modifier: Modifier = Modifier,
    navController: NavController,
    onAction: (UiAction) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current).data(uiState.product?.imageOne)
                .build(),
            contentDescription = uiState.product?.title,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .size(300.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = uiState.product?.title ?: "",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            IconButton(onClick = { onAction(UiAction.ToggleFavorite) }) {
                Icon(
                    imageVector = if (uiState.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = null,
                    modifier = Modifier.size(36.dp),
                    tint = if (uiState.isFavorite) Color.Red else Color.Gray
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = uiState.product?.description ?: "",
            fontSize = 18.sp,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Benzer Ürünler", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        LazyRow(modifier = Modifier.fillMaxWidth()) {
            items(uiState.similarProducts) { product ->
                SimilarProductItem(
                    product,
                    navController
                )
            }
        }
    }
}

@Composable
fun TopBar(
    onBackClick: () -> Unit,
    onSearchClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBackClick) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = null,
                modifier = Modifier.size(36.dp)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .clip(shape = RoundedCornerShape(16.dp))
                .border(
                    width = 1.dp,
                    color = Color.Black,
                    shape = RoundedCornerShape(16.dp)
                )
                .clickable {
                    onSearchClick()
                }
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = stringResource(R.string.search)
            )
        }
    }
}

@Composable
fun BottomBar(
    priceText: String,
    salePriceText: String,
    uiState: UiState,
    addToCartClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Fiyat",
                fontSize = 16.sp,
                color = Color.DarkGray
            )
            Text(
                if (uiState.product?.saleState == true) "₺${salePriceText}" else "₺${priceText}",
                fontSize = 24.sp
            )

            if (uiState.product?.saleState == true) {
                Text(
                    text = "₺${priceText}",
                    fontSize = 20.sp,
                    textDecoration = TextDecoration.LineThrough,
                    color = Color.Red
                )
            }
        }

        Button(
            onClick = addToCartClick,
            colors = ButtonColors(
                containerColor = Color.Black,
                contentColor = Color.White,
                disabledContainerColor = Color.Gray,
                disabledContentColor = Color.DarkGray
            ),
            modifier = Modifier.height(60.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ShoppingCart,
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Sepete Ekle",
                fontSize = 20.sp
            )
        }
    }
}

@Composable
fun SimilarProductItem(
    product: Product,
    navController: NavController
) {
    Column(
        modifier = Modifier
            .width(150.dp)
            .padding(8.dp)
            .clip(RoundedCornerShape(8.dp))
            .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
            .clickable {
                navController.navigate("${Screen.getRoute(Screen.Detail(product.id))}/${product.id}")
            }
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current).data(product.imageOne)
                .build(),
            contentDescription = product.title,
            modifier = Modifier.size(120.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = product.title,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}


@Preview(showBackground = true)
@Composable
fun DetailScreenPreview(
    @PreviewParameter(DetailScreenPreviewProvider::class) uiState: UiState,
) {
    DetailScreen(
        uiState = uiState,
        uiEffect = emptyFlow(),
        onAction = {},
        navController = NavController(LocalContext.current),
        onBackClick = {},
        navigateToSearch = {},
        userId = ""


    )
}