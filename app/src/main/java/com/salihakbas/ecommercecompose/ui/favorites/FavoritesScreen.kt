package com.salihakbas.ecommercecompose.ui.favorites

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.salihakbas.ecommercecompose.common.collectWithLifecycle
import com.salihakbas.ecommercecompose.data.model.FavoriteProduct
import com.salihakbas.ecommercecompose.ui.components.EmptyScreen
import com.salihakbas.ecommercecompose.ui.components.LoadingBar
import com.salihakbas.ecommercecompose.ui.favorites.FavoritesContract.UiAction
import com.salihakbas.ecommercecompose.ui.favorites.FavoritesContract.UiEffect
import com.salihakbas.ecommercecompose.ui.favorites.FavoritesContract.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Composable
fun FavoritesScreen(
    uiState: UiState,
    uiEffect: Flow<UiEffect>,
    onAction: (UiAction) -> Unit,
    navigateToDetail: (Int) -> Unit
) {
    uiEffect.collectWithLifecycle { effect ->
        when (effect) {
            is UiEffect.ProductClick -> {navigateToDetail(effect.id)}
        }
    }
    when {
        uiState.isLoading -> LoadingBar()
        uiState.list.isNotEmpty() -> EmptyScreen()
        else -> FavoritesContent(
            favoriteProducts = uiState.favoriteProducts,
            onAction = onAction,
            navigateToDetail = navigateToDetail
        )
    }
}

@Composable
fun FavoritesContent(
    favoriteProducts: List<FavoriteProduct>,
    onAction: (UiAction) -> Unit,
    navigateToDetail: (Int) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()
        .padding(16.dp)) {
        Text(
            text = "Favorileri Temizle",
            fontSize = 18.sp,
            color = Color.Blue,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier
                .align(Alignment.End)
                .clickable {
                    onAction(UiAction.DeleteAllFavorites)
                }
        )
    }
    LazyColumn(modifier = Modifier.fillMaxSize()
        .padding(horizontal = 4.dp, vertical = 24.dp)) {
        items(favoriteProducts) { product ->
            FavoriteProductItem(product, onAction,navigateToDetail)
        }
    }
}

@Composable
fun FavoriteProductItem(product: FavoriteProduct, onAction: (UiAction) -> Unit,navigateToDetail: (Int) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .clickable { }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current).data(product.imageOne).build(),
            contentDescription = product.title,
            modifier = Modifier.size(100.dp)
                .clickable {
                    navigateToDetail(product.id)
                }
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = product.title,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Text(
                text = if (product.saleState) "₺${product.salePrice}" else "₺${product.price}",
                fontSize = 18.sp
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
        Spacer(modifier = Modifier.weight(1f))
        IconButton(onClick = { onAction(UiAction.RemoveFavorite(product.id)) }) {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = null,
                tint = Color.Red
            )
        }
    }

}


@Preview(showBackground = true)
@Composable
fun FavoritesScreenPreview(
    @PreviewParameter(FavoritesScreenPreviewProvider::class) uiState: UiState,
) {
    FavoritesScreen(
        uiState = uiState,
        uiEffect = emptyFlow(),
        onAction = {},
        navigateToDetail = {}
    )
}