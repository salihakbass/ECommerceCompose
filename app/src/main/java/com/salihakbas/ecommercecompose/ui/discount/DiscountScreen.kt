package com.salihakbas.ecommercecompose.ui.discount

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.salihakbas.ecommercecompose.R
import com.salihakbas.ecommercecompose.common.collectWithLifecycle
import com.salihakbas.ecommercecompose.domain.model.Product
import com.salihakbas.ecommercecompose.ui.components.EmptyScreen
import com.salihakbas.ecommercecompose.ui.components.LoadingBar
import com.salihakbas.ecommercecompose.ui.discount.DiscountContract.UiAction
import com.salihakbas.ecommercecompose.ui.discount.DiscountContract.UiEffect
import com.salihakbas.ecommercecompose.ui.discount.DiscountContract.UiState
import kotlinx.coroutines.flow.Flow

@Composable
fun DiscountScreen(
    uiState: UiState,
    uiEffect: Flow<UiEffect>,
    onAction: (UiAction) -> Unit,
    navigateBack: () -> Unit,
    navigateToDetail: (Int) -> Unit
) {
    uiEffect.collectWithLifecycle { effect ->
        when (effect) {
            is UiEffect.NavigateBack -> navigateBack()
            is UiEffect.NavigateToDetail -> navigateToDetail(effect.id)
        }
    }
    when {
        uiState.isLoading -> LoadingBar()
        uiState.list.isNotEmpty() -> EmptyScreen()
        else -> DiscountContent(
            uiState = uiState,
            navigateBack = navigateBack,
            navigateToDetail = navigateToDetail
        )
    }
}

@Composable
fun DiscountContent(
    uiState: UiState,
    navigateBack: () -> Unit,
    navigateToDetail: (Int) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(24.dp)
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
                text = stringResource(R.string.discount_chance_text),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp)
            )
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(uiState.discountList) { product ->
                ProductItem(
                    product,
                    navigateToDetail = navigateToDetail
                )
            }
        }
    }

}

@Composable
fun ProductItem(
    product: Product,
    navigateToDetail: (Int) -> Unit
) {
    val discountPercentage = if (product.salePrice != null) {
        ((product.price - product.salePrice) / product.price * 100).toInt()
    } else {
        0
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 8.dp)
            .clickable {
                navigateToDetail(product.id)
            }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current).data(product.imageOne)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier
                        .size(150.dp)
                        .padding(8.dp),
                    contentScale = androidx.compose.ui.layout.ContentScale.Crop
                )
                Column {
                    Text(
                        text = product.title,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(bottom = 4.dp),
                        maxLines = 1,
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "₺ ${product.salePrice}",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Icon(
                            painter = painterResource(R.drawable.ic_discount),
                            contentDescription = stringResource(R.string.discount_icon_cd),
                            tint = Color.Red
                        )
                        Text(
                            text = stringResource(R.string.discount_text, discountPercentage),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Red
                        )
                    }
                    Text(
                        text = "₺ ${product.price}",
                        fontSize = 18.sp,
                        color = Color.Gray,
                        textDecoration = TextDecoration.LineThrough


                    )

                }

            }

        }
    }
}
