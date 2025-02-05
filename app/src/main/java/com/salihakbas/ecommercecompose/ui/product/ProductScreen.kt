package com.salihakbas.ecommercecompose.ui.product

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.salihakbas.ecommercecompose.common.collectWithLifecycle
import com.salihakbas.ecommercecompose.domain.model.Product
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
    onAction: (UiAction) -> Unit,
    navigateBack: () -> Unit,
    navigateToDetail: (Int) -> Unit
) {
    uiEffect.collectWithLifecycle { effect ->
        when (effect) {
            is UiEffect.NavigateBack -> navigateBack()
            is UiEffect.NavigateDetail -> navigateToDetail(effect.id)
        }

    }

    when {
        uiState.isLoading -> LoadingBar()
        uiState.list.isNotEmpty() -> EmptyScreen()
        else -> ProductContent(
            uiState = uiState,
            onAction = onAction,
            navigateBack = navigateBack,
            navigateToDetail = navigateToDetail
        )
    }
}

@Composable
fun ProductContent(
    uiState: UiState,
    onAction: (UiAction) -> Unit,
    navigateBack: () -> Unit,
    navigateToDetail: (Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = null,
                modifier = Modifier
                    .size(36.dp)
                    .clickable {
                        navigateBack()
                    }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "İndirimli Fiyatları Kaçırma!",
                    fontSize = 24.sp
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
        }

        item {
            DiscountedProductRow(
                uiState.productList,
                navigateToDetail
            )
        }

        items(uiState.productList) { product ->
            ProductItem(
                product,
                navigateToDetail = navigateToDetail
            )
        }
    }
}

@Composable
fun ProductItem(
    product: Product,
    navigateToDetail: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
            .padding(8.dp)
            .clickable {
                navigateToDetail(product.id)
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current).data(product.imageOne).build(),
            contentDescription = null,
            modifier = Modifier.size(140.dp)
        )
        Text(
            text = product.title,
            fontSize = 16.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        Text(
            text = if (product.saleState) "₺${product.salePrice}" else "₺${product.price}",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        if (product.saleState) {
            Text(
                text = "₺${product.price}",
                color = Color.Red,
                fontSize = 20.sp,
                style = TextStyle(
                    textDecoration = TextDecoration.LineThrough
                )
            )


        }
    }
}


@Composable
fun DiscountedProductList(
    product: Product,
    navigateToDetail: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .width(150.dp)
            .padding(8.dp)
            .clip(RoundedCornerShape(8.dp))
            .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
            .padding(8.dp)
            .clickable {
                navigateToDetail(product.id)
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current).data(product.imageOne).build(),
            contentDescription = null,
            modifier = Modifier.size(140.dp)
        )
        Text(
            text = product.title,
            fontSize = 16.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        Text(
            text = "₺${product.salePrice}",
            fontSize = 18.sp,
        )

        if (product.saleState) {
            Text(
                text = "₺${product.price}",
                fontSize = 16.sp,
                textDecoration = TextDecoration.LineThrough,
                color = Color.Red
            )
        }
    }
}

@Composable
fun DiscountedProductRow(
    product: List<Product>,
    navigateToDetail: (Int) -> Unit
) {
    val discountedProducts = product.filter { it.saleState }
    LazyRow(
        modifier = Modifier.fillMaxWidth()
    ) {
        items(discountedProducts) { product ->
            DiscountedProductList(
                product = product,
                navigateToDetail
            )
        }
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
        navigateBack = {},
        navigateToDetail = {}
    )
}
