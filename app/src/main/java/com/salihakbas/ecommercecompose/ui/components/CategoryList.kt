package com.salihakbas.ecommercecompose.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.salihakbas.ecommercecompose.data.source.remote.Category
import com.salihakbas.ecommercecompose.ui.home.HomeContract

@Composable
fun CategoryList(
    uiState: HomeContract.UiState,
    modifier: Modifier = Modifier,
    onCategoryClick: (String?) -> Unit
) {
    val categories = listOf(Category("Tümü")) + uiState.categoryList
    var selectedTabIndex by remember { mutableStateOf(0) }
    ScrollableTabRow(
        selectedTabIndex = selectedTabIndex,
        edgePadding = 2.dp,
        modifier = modifier
    ) {

        categories.forEachIndexed { index, category ->
            Tab(
                selected = index == selectedTabIndex,
                onClick = {
                    selectedTabIndex = index
                    onCategoryClick(category.name)
                },
                text = {
                    Text(
                        text = category.name,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (index == selectedTabIndex) Color.White else Color.Gray,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(vertical = 8.dp, horizontal = 4.dp)
                            .background(
                                color = if (index == selectedTabIndex) Color.Black else Color.Transparent,
                                shape = RoundedCornerShape(24.dp)
                            )
                            .padding(horizontal = 18.dp, vertical = 8.dp)
                    )
                }
            )
        }
    }
}