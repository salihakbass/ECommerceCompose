package com.salihakbas.ecommercecompose.ui.changepassword

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.salihakbas.ecommercecompose.R
import com.salihakbas.ecommercecompose.common.collectWithLifecycle
import com.salihakbas.ecommercecompose.ui.changepassword.ChangePasswordContract.UiAction
import com.salihakbas.ecommercecompose.ui.changepassword.ChangePasswordContract.UiEffect
import com.salihakbas.ecommercecompose.ui.changepassword.ChangePasswordContract.UiState
import com.salihakbas.ecommercecompose.ui.components.EmptyScreen
import com.salihakbas.ecommercecompose.ui.components.LoadingBar
import com.salihakbas.ecommercecompose.ui.components.PasswordRequirements
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Composable
fun ChangePasswordScreen(
    uiState: UiState,
    uiEffect: Flow<UiEffect>,
    onAction: (UiAction) -> Unit,
    navigateToProfile: () -> Unit,
) {
    val context = LocalContext.current
    uiEffect.collectWithLifecycle { effect ->
        when (effect) {
            is UiEffect.ShowToast -> {
                Toast.makeText(context,
                    context.getString(R.string.password_changed_text), Toast.LENGTH_SHORT).show()
            }

            is UiEffect.ShowErrorToast -> {
                Toast.makeText(context,
                    context.getString(R.string.password_error_text), Toast.LENGTH_SHORT).show()
            }

            is UiEffect.NavigateToProfile -> {
                navigateToProfile()
            }
        }
    }
    when {
        uiState.isLoading -> LoadingBar()
        uiState.list.isNotEmpty() -> EmptyScreen()
        else -> ChangePasswordContent(uiState, onAction)
    }
}

@Composable
fun ChangePasswordContent(uiState: UiState, onAction: (UiAction) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 132.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.create_new_password_text),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(R.string.welcome_create_new_password_text),
            textAlign = TextAlign.Center,
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier.padding(horizontal = 24.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = uiState.oldPassword,
            onValueChange = { onAction(UiAction.ChangeOldPassword(it)) },
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 24.dp)
                .fillMaxWidth(),
            label = {
                Text(
                    text = stringResource(R.string.old_password_text),
                    color = Color.Gray
                )
            },
            shape = RoundedCornerShape(16.dp)
        )

        OutlinedTextField(
            value = uiState.newPassword,
            onValueChange = { onAction(UiAction.ChangeNewPassword(it)) },
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 8.dp)
                .fillMaxWidth(),
            label = {
                Text(
                    text = stringResource(R.string.create_new_password_text),
                    color = Color.Gray
                )
            },
            shape = RoundedCornerShape(16.dp),
            isError = uiState.errorState?.passwordError == true
        )

        OutlinedTextField(
            value = uiState.newPasswordConfirm,
            onValueChange = { onAction(UiAction.ChangeNewPasswordConfirm(it)) },
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 8.dp)
                .fillMaxWidth(),
            label = {
                Text(
                    text = stringResource(R.string.confirm_new_password_text),
                    color = Color.Gray
                )
            },
            shape = RoundedCornerShape(16.dp),
            isError = uiState.errorState?.confirmPasswordError == true
        )

        PasswordRequirements(
            isPasswordLongEnough = uiState.isPasswordLongEnough,
            hasLetter = uiState.hasLetter,
            hasPassword = uiState.hasNumber,
            isPasswordMatching = uiState.isPasswordMatching
        )

        Button(
            onClick = {onAction(UiAction.ChangePassword)},
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
                text = stringResource(R.string.create_password_button_text),
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChangePasswordScreenPreview(
    @PreviewParameter(ChangePasswordScreenPreviewProvider::class) uiState: UiState,
) {
    ChangePasswordScreen(
        uiState = uiState,
        uiEffect = emptyFlow(),
        onAction = {},
        navigateToProfile = {}
    )
}