package com.salihakbas.ecommercecompose.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.salihakbas.ecommercecompose.domain.model.Product

@Composable
fun PopularProductList(
    product: Product,
    navigateToDetail: (Int) -> Unit
) {
    Column(
        modifier = Modifier.padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current).data(product.imageOne).build(),
            contentDescription = product.title,
            modifier = Modifier
                .size(140.dp)
                .clickable {
                    navigateToDetail(product.id)
                }

        )
        Text(
            text = product.title,
            fontSize = 16.sp
        )
        Text(if (product.saleState) "₺${product.salePrice}" else "₺${product.price}")

        if (product.saleState) {
            Text(
                text = "₺${product.price}",
                fontSize = 14.sp,
                textDecoration = TextDecoration.LineThrough,
                color = Color.Red
            )
        }
    }
}

@Composable
fun PopularProductRow(
    product: List<Product>,
    navigateToDetail: (Int) -> Unit
) {
    val popularProducts = remember {
        product.shuffled().take(6)
    }
    LazyRow(
        modifier = Modifier.fillMaxWidth()
    ) {
        items(popularProducts) { product ->
            PopularProductList(
                product = product,
                navigateToDetail = navigateToDetail
            )


        }
    }
}
