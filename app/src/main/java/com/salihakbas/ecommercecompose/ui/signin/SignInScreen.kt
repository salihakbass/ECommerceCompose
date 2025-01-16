package com.salihakbas.ecommercecompose.ui.signin

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.sp
import com.salihakbas.ecommercecompose.ui.components.EmptyScreen
import com.salihakbas.ecommercecompose.ui.components.LoadingBar
import com.salihakbas.ecommercecompose.ui.signin.SignInContract.UiAction
import com.salihakbas.ecommercecompose.ui.signin.SignInContract.UiEffect
import com.salihakbas.ecommercecompose.ui.signin.SignInContract.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Composable
fun SignInScreen(
    uiState: UiState,
    uiEffect: Flow<UiEffect>,
    onAction: (UiAction) -> Unit,
) {
    when {
        uiState.isLoading -> LoadingBar()
        uiState.list.isNotEmpty() -> EmptyScreen()
        else -> SignInContent()
    }
}

@Composable
fun SignInContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "SignIn Content",
            fontSize = 24.sp,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SignInScreenPreview(
    @PreviewParameter(SignInScreenPreviewProvider::class) uiState: UiState,
) {
    SignInScreen(
        uiState = uiState,
        uiEffect = emptyFlow(),
        onAction = {},
    )
}