package com.salihakbas.ecommercecompose.ui.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.salihakbas.ecommercecompose.R
import com.salihakbas.ecommercecompose.common.collectWithLifecycle
import com.salihakbas.ecommercecompose.ui.components.EmptyScreen
import com.salihakbas.ecommercecompose.ui.components.LoadingBar
import com.salihakbas.ecommercecompose.ui.profile.ProfileContract.UiAction
import com.salihakbas.ecommercecompose.ui.profile.ProfileContract.UiEffect
import com.salihakbas.ecommercecompose.ui.profile.ProfileContract.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Composable
fun ProfileScreen(
    uiState: UiState,
    uiEffect: Flow<UiEffect>,
    onAction: (UiAction) -> Unit,
    navigateToSignIn: () -> Unit,
    navigateToChangePassword: () -> Unit,
) {
    uiEffect.collectWithLifecycle { effect ->
        when (effect) {
            UiEffect.NavigateToSignIn -> navigateToSignIn()
            UiEffect.NavigateToChangePassword -> navigateToChangePassword()
        }

    }
    when {
        uiState.isLoading -> LoadingBar()
        uiState.list.isNotEmpty() -> EmptyScreen()
        else -> ProfileContent(uiState, onAction)
    }
}

@Composable
fun ProfileContent(uiState: UiState, onAction: (UiAction) -> Unit) {
    Column(
        modifier = Modifier
            .wrapContentSize()
            .padding(top = 64.dp, start = 16.dp, end = 16.dp)
            .statusBarsPadding(),

        ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Salih Akbaş",
                fontSize = 24.sp
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = null
            )
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {onAction(UiAction.ChangePasswordClicked)},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                colors = ButtonColors(
                    containerColor = colorResource(R.color.black),
                    contentColor = Color.White,
                    disabledContainerColor = colorResource(R.color.black),
                    disabledContentColor = Color.White
                )
            ) {
                Text(
                    text = "Şifre Değiştir",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
            Button(
                onClick = {onAction(UiAction.SignOutClicked)},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                colors = ButtonColors(
                    containerColor = colorResource(R.color.black),
                    contentColor = Color.White,
                    disabledContainerColor = colorResource(R.color.black),
                    disabledContentColor = Color.White
                )
            ) {
                Text(
                    text = "Çıkış Yap",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview(
    @PreviewParameter(ProfileScreenPreviewProvider::class) uiState: UiState,
) {
    ProfileScreen(
        uiState = uiState,
        uiEffect = emptyFlow(),
        onAction = {},
        navigateToSignIn = {},
        navigateToChangePassword = {}
    )
}