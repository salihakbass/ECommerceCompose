package com.salihakbas.ecommercecompose.ui.signin

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.salihakbas.ecommercecompose.R
import com.salihakbas.ecommercecompose.ui.components.AnnotatedLoginAndRegisterString
import com.salihakbas.ecommercecompose.ui.components.EmptyScreen
import com.salihakbas.ecommercecompose.ui.components.GoogleButton
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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Welcome Back, Olivia",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(R.string.welcome_back_detail_text),
            fontSize = 18.sp,
            color = Color.Gray
        )
        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = {
                Text(
                    stringResource(R.string.email_text),
                    color = Color.Gray
                )
            },

            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 24.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        )
        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = {
                Text(
                    stringResource(R.string.password_text),
                    color = Color.Gray
                )
            },
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Checkbox(
                checked = false,
                onCheckedChange = {}
            )
            Text(
                text = stringResource(R.string.remember_me_text),
                modifier = Modifier.align(Alignment.CenterVertically),
                color = Color.Black
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = stringResource(R.string.forgot_password_text),
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(end = 12.dp),
                color = Color.Black
            )
        }
        Button(
            onClick = {},
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
                text = stringResource(R.string.sign_in_text),
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
        Text(
            text = "or",
            modifier = Modifier.padding(vertical = 12.dp),
            color = Color.Gray
        )
        GoogleButton(
            text = stringResource(R.string.sign_up_with_google_text),
            loadingText = stringResource(R.string.creating_account_text),
            onClicked = {},
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))

        AnnotatedLoginAndRegisterString(
            accountText = stringResource(R.string.dont_have_an_account_text),
            loginOrRegisterText = stringResource(R.string.sign_up_text)
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