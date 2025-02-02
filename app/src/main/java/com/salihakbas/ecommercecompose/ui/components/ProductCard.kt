package com.salihakbas.ecommercecompose.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.salihakbas.ecommercecompose.R
import com.salihakbas.ecommercecompose.domain.model.Product

@Composable
fun ProductCard(product: Product) {
    val discountPercentage = if (product.salePrice != null) {
        ((product.price - product.salePrice) / product.price * 100).toInt()
    } else {
        0
    }

    Column(
        modifier = Modifier.padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.size(130.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current).data(product.imageOne).build(),
                contentDescription = product.title,
                modifier = Modifier.matchParentSize()
            )
            if (product.saleState) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .clip(RoundedCornerShape(32.dp))
                        .padding(4.dp)
                        .background(Color.Red.copy(alpha = 0.8f))
                        .padding(horizontal = 8.dp, vertical = 4.dp)

                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_discount),
                            tint = Color.White,
                            contentDescription = "Discount",
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = "%${discountPercentage} İndirim",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
        }

        Text(
            text = product.title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            maxLines = 2,
            color = Color.Gray,
        )
        Spacer(modifier = Modifier.height(8.dp))

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
                style = TextStyle(
                    textDecoration = TextDecoration.LineThrough
                )
            )


        }
    }

}

@Composable
fun ProductGrid(product: List<Product>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2)
    ) {
        items(product) { product ->
            ProductCard(product = product)
        }
    }
}