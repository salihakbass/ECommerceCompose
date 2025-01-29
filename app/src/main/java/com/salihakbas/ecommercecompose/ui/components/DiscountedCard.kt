package com.salihakbas.ecommercecompose.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.salihakbas.ecommercecompose.R

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
            text = stringResource(R.string.discounted_card_text),
            fontSize = 24.sp,
            color = Color.White,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(16.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.mouse),
            contentDescription = stringResource(R.string.sale_cd),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 24.dp)
                .rotate(-25f)

        )
    }
}