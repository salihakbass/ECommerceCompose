package com.salihakbas.ecommercecompose.ui.forgotpassword

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.salihakbas.ecommercecompose.R
import com.salihakbas.ecommercecompose.common.collectWithLifecycle
import com.salihakbas.ecommercecompose.ui.components.EmptyScreen
import com.salihakbas.ecommercecompose.ui.components.LoadingBar
import com.salihakbas.ecommercecompose.ui.forgotpassword.ForgotPasswordContract.UiAction
import com.salihakbas.ecommercecompose.ui.forgotpassword.ForgotPasswordContract.UiEffect
import com.salihakbas.ecommercecompose.ui.forgotpassword.ForgotPasswordContract.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Composable
fun ForgotPasswordScreen(
    uiState: UiState,
    uiEffect: Flow<UiEffect>,
    onAction: (UiAction) -> Unit,
    navigateToSignIn: () -> Unit,
) {
    uiEffect.collectWithLifecycle {effect->
        when(effect){
            is UiEffect.NavigateToVerifyCode -> navigateToSignIn()
            is UiEffect.ShowToast -> {}
        }

    }
    when {
        uiState.isLoading -> LoadingBar()
        uiState.list.isNotEmpty() -> EmptyScreen()
        else -> ForgotPasswordContent(uiState, onAction)
    }
}

@Composable
fun ForgotPasswordContent(uiState: UiState, onAction: (UiAction) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize()
            .padding(top = 132.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "E-posta adresini doğrula",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
            )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Hesabınıza bağlı e-posta adresini girin ve e-posta adresinize gönderilen link üzerinden şifre sıfırlama işlemini tamamlayın.",
            textAlign = TextAlign.Center,
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier.padding(horizontal = 24.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = uiState.email,
            onValueChange = {onAction(UiAction.OnEmailChange(it))},
            label = {
                Text(
                    text = "E-posta adresinizi girin.",
                    color = Color.Gray
                )
            },

            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 24.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        )

        Button(
            onClick = {onAction(UiAction.SendCodeClicked)},
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
                text = "Devam Et",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ForgotPasswordScreenPreview(
    @PreviewParameter(ForgotPasswordScreenPreviewProvider::class) uiState: UiState,
) {
    ForgotPasswordScreen(
        uiState = uiState,
        uiEffect = emptyFlow(),
        onAction = {},
        navigateToSignIn = {}

    )
}