package com.salihakbas.ecommercecompose.ui.signup

import android.widget.Toast
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.salihakbas.ecommercecompose.R
import com.salihakbas.ecommercecompose.common.collectWithLifecycle
import com.salihakbas.ecommercecompose.ui.components.EmptyScreen
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
    navigateToSignIn: () -> Unit
) {
    val context = LocalContext.current
    uiEffect.collectWithLifecycle { effect ->
        when (effect) {
            is UiEffect.NavigateToSignIn -> navigateToSignIn()

            is UiEffect.ShowSignUpToast -> {
                Toast.makeText(
                    context,
                    context.getString(R.string.sign_up_toast_text),
                    Toast.LENGTH_SHORT
                ).show()
            }

        }
    }
    when {
        uiState.isLoading -> LoadingBar()
        uiState.list.isNotEmpty() -> EmptyScreen()
        else -> SignUpContent(uiState, onAction)
    }
}

@Composable
fun SignUpContent(uiState: UiState, onAction: (UiAction) -> Unit) {
    var isVisible by remember { mutableStateOf(false) }
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
            value = uiState.name,
            onValueChange = { onAction(UiAction.OnNameChange(it)) },
            label = {
                Text(
                    text = stringResource(R.string.name_text),
                    color = Color.Gray
                )
            },
            modifier = Modifier
                .padding(start = 12.dp, end = 12.dp, top = 24.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            isError = uiState.errorState?.nameError == true
        )
        OutlinedTextField(
            value = uiState.surname,
            onValueChange = { onAction(UiAction.OnSurnameChange(it)) },
            label = {
                Text(
                    text = stringResource(R.string.surname_text),
                    color = Color.Gray
                )
            },
            modifier = Modifier
                .padding(start = 12.dp, end = 12.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            isError = uiState.errorState?.surnameError == true,

            )
        OutlinedTextField(
            value = uiState.email,
            onValueChange = { onAction(UiAction.OnEmailChange(it)) },
            label = {
                Text(
                    text = stringResource(R.string.email_text),
                    color = Color.Gray
                )
            },
            modifier = Modifier
                .padding(start = 12.dp, end = 12.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            isError = uiState.errorState?.emailError == true
        )
        OutlinedTextField(
            value = uiState.password,
            onValueChange = { onAction(UiAction.OnPasswordChange(it)) },
            label = {
                Text(
                    stringResource(R.string.create_password_text),
                    color = Color.Gray
                )
            },
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            visualTransformation = if (isVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { isVisible = !isVisible }) {
                    Icon(
                        painter = painterResource(if (isVisible) R.drawable.ic_visibility_on else R.drawable.ic_visibility_off),
                        contentDescription = null
                    )

                }
            },
            isError = uiState.errorState?.passwordError == true
        )
        OutlinedTextField(
            value = uiState.confirmPassword,
            onValueChange = { onAction(UiAction.OnConfirmPasswordChange(it)) },
            label = {
                Text(
                    stringResource(R.string.confirm_password_text),
                    color = Color.Gray
                )
            },
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            visualTransformation = if (isVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { isVisible = !isVisible }) {
                    Icon(
                        painter = painterResource(if (isVisible) R.drawable.ic_visibility_on else R.drawable.ic_visibility_off),
                        contentDescription = null
                    )

                }
            },
            isError = uiState.errorState?.confirmPasswordError == true
        )
        PasswordRequirements(
            isPasswordLongEnough = uiState.isPasswordLongEnough,
            hasLetter = uiState.hasLetter,
            hasPassword = uiState.hasNumber,
            isPasswordMatching = uiState.isPasswordMatching
        )


        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Checkbox(
                checked = uiState.isCheckboxChecked,
                onCheckedChange = { onAction(UiAction.OnCheckboxToggle(it)) }


            )
            Text(
                text = stringResource(R.string.agree_terms_text),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
        Button(
            onClick = { onAction(UiAction.SignUpClick) },
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

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = Color.Gray,
                    )
                ) {
                    append(stringResource(R.string.already_have_an_account_text))
                }
                withStyle(
                    style = SpanStyle(
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                ) {
                    append(stringResource(R.string.sign_in_text))
                }
            }
        )


    }
}

@Composable
fun PasswordRequirements(
    isPasswordLongEnough: Boolean,
    hasLetter: Boolean,
    hasPassword: Boolean,
    isPasswordMatching: Boolean
) {
    Column(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        PasswordRequirement(
            text = stringResource(R.string.password_must_six_text),
            isMet = isPasswordLongEnough
        )
        PasswordRequirement(
            text = stringResource(R.string.password_must_has_letter_text),
            isMet = hasLetter
        )
        PasswordRequirement(
            text = stringResource(R.string.password_must_has_number_text),
            isMet = hasPassword
        )
        PasswordRequirement(
            text = stringResource(R.string.password_must_match_text),
            isMet = isPasswordMatching
        )
    }
}

@Composable
fun PasswordRequirement(text: String, isMet: Boolean) {
    Text(
        text = text,
        fontSize = 12.sp,
        color = if (isMet) Color.Green else Color.Red,
        fontWeight = if (isMet) FontWeight.Bold else FontWeight.Normal,
        modifier = Modifier.padding(vertical = 4.dp)
    )
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
        navigateToSignIn = {}
    )
}