package com.salihakbas.ecommercecompose.ui.home

import androidx.compose.foundation.background
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.pager.ExperimentalPagerApi
import com.salihakbas.ecommercecompose.domain.model.Product
import com.salihakbas.ecommercecompose.ui.components.CategoryList
import com.salihakbas.ecommercecompose.ui.components.EmptyScreen
import com.salihakbas.ecommercecompose.ui.components.LoadingBar
import com.salihakbas.ecommercecompose.ui.components.SearchView
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
    onCategoryClick: (String?) -> Unit
) {
    when {
        uiState.isLoading -> LoadingBar()
        uiState.list.isNotEmpty() -> EmptyScreen()
        else -> HomeContent(
            uiState = uiState,
            onCategoryClick = onCategoryClick,
            onAction = onAction
        )
    }
}

@Composable
fun HomeContent(
    uiState: UiState,
    onCategoryClick: (String?) -> Unit,
    onAction: (UiAction) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Text(
            text = "Hoşgeldin, ${uiState.userName}",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(start = 8.dp, top = 16.dp)
        )
        SearchView(
            query = uiState.query,
            onQueryChange = { onAction(UiAction.OnQueryChanged(it)) }
        )
        Text(
            text = "İndirimli Ürünler",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 8.dp, top = 16.dp)
        )
        DiscountedProductsPager(uiState.productList)
        Text(
            text = "Kategoriler",
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
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(uiState.productList) { product ->
                    ProductCard(product = product)
                }
            }
        } else {
            Text(text = "Bu kategoride ürün bulunamadı")
        }
    }
}

@Composable
fun ProductCard(product: Product) {
    Column(
        modifier = Modifier.padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current).data(product.imageOne).build(),
            contentDescription = product.title,
            modifier = Modifier.size(150.dp)
        )
        Text(
            text = product.title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            maxLines = 2
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "₺${product.price}",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        if (product.saleState) {
            Text(
                text = "₺${product.salePrice}",
            )
        }
    }
}


@Composable
fun DiscountedProductsPager(products: List<Product>) {
    val discountedProducts = products.filter { it.saleState }
    val pagerState = rememberPagerState(pageCount = {discountedProducts.size})
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth()
        ) { page ->
            val product = discountedProducts[page]
            ProductCard(product = product)
        }

        Spacer(modifier = Modifier.height(16.dp))

        DotsIndicator(
            totalDots = discountedProducts.size,
            selectedIndex = pagerState.currentPage
        )
    }
}

@Composable
fun DotsIndicator(
    totalDots: Int,
    selectedIndex: Int
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        repeat(totalDots) { index ->
            Box(
                modifier = Modifier
                    .padding(4.dp)
                    .size(if (index == selectedIndex) 12.dp else 8.dp)
                    .background(
                        color = if (index == selectedIndex) androidx.compose.ui.graphics.Color.Gray
                        else androidx.compose.ui.graphics.Color.LightGray,
                        shape = androidx.compose.foundation.shape.CircleShape
                    )
            )
        }
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
        onCategoryClick = {}
    )
}