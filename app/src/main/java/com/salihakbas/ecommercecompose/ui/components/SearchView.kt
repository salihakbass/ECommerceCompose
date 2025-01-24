package com.salihakbas.ecommercecompose.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.salihakbas.ecommercecompose.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchView(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearchQuery: (String) -> Unit
) {
    val colors1 = SearchBarDefaults.colors(colorResource(R.color.white))
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .background(color = colorResource(R.color.white))
    ) {
        androidx.compose.material3.SearchBar(
            inputField = {
                SearchBarDefaults.InputField(
                    query = query,
                    onQueryChange = {
                        onQueryChange(it)
                        onSearchQuery(it)
                    },
                    onSearch = {},
                    expanded = false,
                    onExpandedChange = {},
                    enabled = true,
                    placeholder = {
                        Text(
                            text = "Arama Yap..",
                            color = colorResource(R.color.black)
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search Icon",
                            tint = colorResource(R.color.black)
                        )
                    },
                    trailingIcon = null,
                    interactionSource = null,
                    )
            },
            expanded = false,
            onExpandedChange = {},
            modifier = Modifier.padding(16.dp),
            shape = SearchBarDefaults.inputFieldShape,
            colors = colors1,
            tonalElevation = SearchBarDefaults.TonalElevation,
            shadowElevation = SearchBarDefaults.ShadowElevation,
            windowInsets = SearchBarDefaults.windowInsets,
            content = @Composable {

            }
        )
    }
}