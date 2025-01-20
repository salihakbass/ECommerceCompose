package com.salihakbas.ecommercecompose.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.salihakbas.ecommercecompose.R

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
            isCorrect = isPasswordLongEnough
        )
        PasswordRequirement(
            text = stringResource(R.string.password_must_has_letter_text),
            isCorrect = hasLetter
        )
        PasswordRequirement(
            text = stringResource(R.string.password_must_has_number_text),
            isCorrect = hasPassword
        )
        PasswordRequirement(
            text = stringResource(R.string.password_must_match_text),
            isCorrect = isPasswordMatching
        )
    }
}

@Composable
fun PasswordRequirement(text: String, isCorrect: Boolean) {
    Text(
        text = text,
        fontSize = 12.sp,
        color = if (isCorrect) Color.Green else Color.Red,
        fontWeight = if (isCorrect) FontWeight.Bold else FontWeight.Normal,
        modifier = Modifier.padding(vertical = 4.dp)
    )
}