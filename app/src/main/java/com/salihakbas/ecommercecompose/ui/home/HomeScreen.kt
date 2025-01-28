package com.salihakbas.ecommercecompose.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.salihakbas.ecommercecompose.R
import com.salihakbas.ecommercecompose.common.collectWithLifecycle
import com.salihakbas.ecommercecompose.domain.model.Product
import com.salihakbas.ecommercecompose.ui.components.CategoryList
import com.salihakbas.ecommercecompose.ui.components.EmptyScreen
import com.salihakbas.ecommercecompose.ui.components.LoadingBar
import com.salihakbas.ecommercecompose.ui.components.ProductCard
import com.salihakbas.ecommercecompose.ui.home.HomeContract.UiAction
import com.salihakbas.ecommercecompose.ui.home.HomeContract.UiEffect
import com.salihakbas.ecommercecompose.ui.home.HomeContract.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Composable
fun HomeScreen(
    uiState: UiState,
    uiEffect: Flow<UiEffect>,
    onAction: (UiAction) -> Unit,
    userId: String,
    onCategoryClick: (String?) -> Unit,
    navigateToSearch: () -> Unit,
    navigateToDiscount: () -> Unit
) {
    uiEffect.collectWithLifecycle { effect ->
        when (effect) {
            is UiEffect.NavigateToSearch -> navigateToSearch()
            is UiEffect.NavigateToDiscount -> navigateToDiscount()
        }
    }
    when {
        uiState.isLoading -> LoadingBar()
        uiState.list.isNotEmpty() -> EmptyScreen()
        else -> HomeContent(
            uiState = uiState,
            onCategoryClick = onCategoryClick,
            onAction = onAction,
            navigateToSearch = navigateToSearch,
            navigateToDiscount = navigateToDiscount
        )
    }
}

@Composable
fun HomeContent(
    uiState: UiState,
    onCategoryClick: (String?) -> Unit,
    onAction: (UiAction) -> Unit,
    navigateToSearch: () -> Unit,
    navigateToDiscount: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = stringResource(R.string.welcome_text, uiState.userName),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(start = 8.dp, top = 16.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
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
                    navigateToSearch()
                }
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null
            )
            Text(
                text = stringResource(R.string.search)
            )
        }
        DiscountedCard(navigateToDiscount)
        Text(
            text = stringResource(R.string.categories_text),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 8.dp, top = 16.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        CategoryList(
            uiState = uiState,
            onCategoryClick = onCategoryClick
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (uiState.productList.isNotEmpty()) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(8.dp)
            ) {
                items(uiState.productList) { product ->
                    ProductCard(product = product)
                }
            }
        } else {
            Text(
                text = stringResource(R.string.there_is_no_product_text),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun DiscountedProductCard(product: Product) {
    Column(
        modifier = Modifier.padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current).data(product.imageOne).build(),
            contentDescription = product.title,
            modifier = Modifier.size(120.dp)
        )
        Text(
            text = product.title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = if (product.saleState) "₺${product.salePrice}" else "₺${product.price}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray
            )
            if (product.saleState) {
                Text(
                    text = "₺${product.price}",
                    color = Color.Red,
                    style = TextStyle(
                        textDecoration = TextDecoration.LineThrough
                    )
                )

            }
        }
    }
}

@Composable
fun DiscountedCard(
    navigateToDiscount: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(16.dp)
            .clip(shape = RoundedCornerShape(32.dp))
            .background(colorResource(R.color.dark_gray))
            .clickable {
                navigateToDiscount()
            },

        ) {
        Text(
            text = "%50'ye varan\n indirim fırsatlarını\n kaçırma!",
            fontSize = 24.sp,
            color = Color.White,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(16.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.mouse),
            contentDescription = "Sale",
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 24.dp)
                .rotate(-25f)

        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview(
    @PreviewParameter(HomeScreenPreviewProvider::class) uiState: UiState,
) {
    HomeScreen(
        uiState = uiState,
        uiEffect = emptyFlow(),
        onAction = {},
        userId = "userId",
        onCategoryClick = {},
        navigateToSearch = {},
        navigateToDiscount = {}
    )
}