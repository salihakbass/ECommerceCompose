package com.salihakbas.ecommercecompose.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.salihakbas.ecommercecompose.domain.model.Product
import com.salihakbas.ecommercecompose.ui.components.CategoryList
import com.salihakbas.ecommercecompose.ui.components.EmptyScreen
import com.salihakbas.ecommercecompose.ui.components.LoadingBar
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
    userId: String
) {
    when {
        uiState.isLoading -> LoadingBar()
        uiState.list.isNotEmpty() -> EmptyScreen()
        else -> HomeContent(
            uiState = uiState

        )
    }

    LaunchedEffect(Unit) {
        onAction(UiAction.FetchProducts)
    }
}

@Composable
fun HomeContent(
    uiState: UiState
) {

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        CategoryList(
            uiState = uiState
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
fun ProductList(products: List<Product>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(products) { product ->
            ProductCard(product)
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
        Text(text = product.title, fontSize = 18.sp)
        Text(text = "₺${product.price}", fontSize = 16.sp)
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
        userId = "userId"
    )
}