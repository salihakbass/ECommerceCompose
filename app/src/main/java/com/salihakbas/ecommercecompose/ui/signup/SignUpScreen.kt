package com.salihakbas.ecommercecompose.ui.signup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.salihakbas.ecommercecompose.R
import com.salihakbas.ecommercecompose.ui.components.AnnotatedLoginAndRegisterString
import com.salihakbas.ecommercecompose.ui.components.EmptyScreen
import com.salihakbas.ecommercecompose.ui.components.GoogleButton
import com.salihakbas.ecommercecompose.ui.components.LoadingBar
import com.salihakbas.ecommercecompose.ui.signup.SignUpContract.UiAction
import com.salihakbas.ecommercecompose.ui.signup.SignUpContract.UiEffect
import com.salihakbas.ecommercecompose.ui.signup.SignUpContract.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Composable
fun SignUpScreen(
    uiState: UiState,
    uiEffect: Flow<UiEffect>,
    onAction: (UiAction) -> Unit,
) {
    when {
        uiState.isLoading -> LoadingBar()
        uiState.list.isNotEmpty() -> EmptyScreen()
        else -> SignUpContent()
    }
}

@Composable
fun SignUpContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.register_to_join_us_text),
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
                    text = stringResource(R.string.email_text),
                    color = Color.Gray
                )
            },

            modifier = Modifier
                .padding(start = 12.dp, end = 12.dp, top = 24.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        )
        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = {
                Text(
                    text = stringResource(R.string.create_password_text),
                    color = Color.Gray
                )
            },
            modifier = Modifier
                .padding(start = 12.dp, end = 12.dp, top = 8.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        )
        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = {
                Text(
                    text = stringResource(R.string.confirm_password_text),
                    color = Color.Gray
                )
            },
            modifier = Modifier
                .padding(start = 12.dp, end = 12.dp, top = 8.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Checkbox(
                checked = false,
                onCheckedChange = {}
            )
            Text(
                text = stringResource(R.string.agree_terms_text),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterVertically)
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
                text = stringResource(R.string.sign_up_text),
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
            accountText = stringResource(R.string.already_have_an_account_text),
            loginOrRegisterText = stringResource(R.string.sign_in_text)
        )


    }
}

@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview(
    @PreviewParameter(SignUpScreenPreviewProvider::class) uiState: UiState,
) {
    SignUpScreen(
        uiState = uiState,
        uiEffect = emptyFlow(),
        onAction = {},
    )
}