package com.salihakbas.ecommercecompose.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.salihakbas.ecommercecompose.R


@Composable
fun SearchTextField(
    query: String,
    onQueryChange: (String) -> Unit,
    focusRequester: FocusRequester
) {

    OutlinedTextField(
        value = query,
        onValueChange = { onQueryChange(it) },
        placeholder = { Text(text = stringResource(R.string.search)) },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search Icon") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .focusRequester(focusRequester),
        singleLine = true,
        enabled = true,
        shape = RoundedCornerShape(16.dp)
    )


}

@Preview(showBackground = true)
@Composable
fun SearchViewPreview() {

}





